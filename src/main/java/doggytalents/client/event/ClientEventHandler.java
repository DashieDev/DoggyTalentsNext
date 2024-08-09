package doggytalents.client.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;

import doggytalents.DoggyBlocks;
import doggytalents.DoggyItems;
import doggytalents.DoggyTalentsNext;
import doggytalents.api.anim.DogAnimation;
import doggytalents.client.DoggyKeybinds;
import doggytalents.client.block.model.DogBedModel;
import doggytalents.client.entity.model.animation.DogAnimationRegistry;
import doggytalents.client.entity.model.animation.DogKeyframeAnimations;
import doggytalents.client.screen.DogNewInfoScreen.DogNewInfoScreen;
import doggytalents.client.screen.DogNewInfoScreen.store.UIActionTypes;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.TalentChangeHandlerSlice;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.client.screen.widget.DogInventoryButton;
import doggytalents.client.screen.widget.DoggySpin;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.WhistleItem.WhistleMode;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogMountData;
import doggytalents.common.network.packet.data.DogSyncData;
import doggytalents.common.network.packet.data.OpenDogScreenData;
import doggytalents.common.network.packet.data.WhistleUseData;
import doggytalents.common.util.InventoryUtil;
import doggytalents.common.util.ItemUtil;
import doggytalents.common.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import doggytalents.common.network.PacketDistributor;

import java.util.List;

public class ClientEventHandler {

    DogInventoryButton activeInventoryButton;

    public static void registerModelForBaking(final ModelEvent.RegisterAdditional event) {

        try {
            ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(DoggyBlocks.DOG_BED.get());
            ResourceLocation unbakedModelLoc = Util.getResource(resourceLocation.getNamespace(), "block/" + resourceLocation.getPath());
            event.register(ModelResourceLocation.standalone(unbakedModelLoc));
        }
        catch(Exception e) {
            DoggyTalentsNext.LOGGER.warn("Could not get base Dog Bed model. Reverting to default textures...");
            e.printStackTrace();
        }
    }

    public static void modifyBakedModels(final ModelEvent.ModifyBakingResult event) {
        try {
            var modelRegistry = event.getModels();

            ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(DoggyBlocks.DOG_BED.get());
            ResourceLocation bakedModelLoc = Util.getResource(resourceLocation.getNamespace(), "block/" + resourceLocation.getPath());

            var model = modelRegistry.get(ModelResourceLocation.standalone(bakedModelLoc));

            var modelUnbaked = (BlockModel) event.getModelBakery().topLevelModels.get(ModelResourceLocation.standalone(bakedModelLoc));

            BakedModel customModel = new DogBedModel(event.getModelBakery(), modelUnbaked, model);

            // Replace all valid block states
            DoggyBlocks.DOG_BED.get().getStateDefinition().getPossibleStates().forEach(state -> {
                modelRegistry.put(BlockModelShaper.stateToModelLocation(state), customModel);
            });

            // Replace inventory model
            modelRegistry.put(new ModelResourceLocation(resourceLocation, "inventory"), customModel);
            
        }
        catch(Exception e) {
            DoggyTalentsNext.LOGGER.warn("Error modifying baking result. Reverting to default textures...");
            e.printStackTrace();
        }
        

    }

    @SubscribeEvent
    public void onInputEvent(final MovementInputUpdateEvent event) {
        if (!event.getInput().jumping)
            return;
        var entity = event.getEntity();
        var vehicle = entity.getVehicle();
        if (!entity.isPassenger())
            return;
        if (!(vehicle instanceof Dog dog))
            return;
        if (!dog.canJump())
            return;
        dog.setJumpPower(100);
    }

    @SubscribeEvent
    public void onScreenInit(final ScreenEvent.Init.Post event) {
        if (!ConfigHandler.ClientConfig.getConfig(ConfigHandler.CLIENT.DOG_INV_BUTTON_IN_INV)) 
            return;
        Screen screen = event.getScreen();
        if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
            boolean creative = screen instanceof CreativeModeInventoryScreen;
            Minecraft mc = Minecraft.getInstance();
            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();
            int sizeX = creative ? 195 : 176;
            int sizeY = creative ? 136 : 166;
            int guiLeft = (width - sizeX) / 2;
            int guiTop = (height - sizeY) / 2;

            int x = guiLeft + (creative ? 36 : sizeX / 2 - 10);
            int y = guiTop + (creative ? 7 : 48);

            this.activeInventoryButton = new DogInventoryButton(x, y, screen);

            event.addListener(this.activeInventoryButton);
        }
        if (event.getScreen() instanceof LevelLoadingScreen) {
            spinWidget.chooseStyle();
        }
            
    }

    private DoggySpin spinWidget = new DoggySpin(0, 0, 128);
    @SubscribeEvent
    public void onScreenDrawForeground(final ScreenEvent.Render.Post event) {
        if (!ConfigHandler.CLIENT.WORD_LOAD_ICON.get())
            return;
        if (!(event.getScreen() instanceof LevelLoadingScreen))
            return;
        spinWidget.setY(event.getScreen().height - 128);
        spinWidget.render(event.getGuiGraphics(), event.getMouseX(), event.getMouseY(), event.getPartialTick());
    }

    @SubscribeEvent
    public void onKeyboardInput(InputEvent.Key event) {
        proccessWhistle(event);
    }

    public void proccessWhistle(InputEvent.Key event) {

        //Same check as IN_GAME_AND_HAS_WHISTLE.isActive()
        var mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null) return;
        var whistle = DoggyItems.WHISTLE.get();
        var whistle_stack = 
            InventoryUtil.findStackWithItemFromHands(player, whistle);
        if (whistle_stack == null) return;

        int hotkey_use = -1;
        var hotkeys_whistle = DoggyKeybinds.hotkeys_whistle;
        for (int i = 0; i < hotkeys_whistle.length; ++i) {
            if (hotkeys_whistle[i].consumeClick()) {
                hotkey_use = i;
                break;
            }
        }
        if (hotkey_use < 0) return;

        if (player.getCooldowns().isOnCooldown(whistle)) return;
        
        var tag = ItemUtil.getTag(whistle_stack);
        if (tag == null) return;
        var hotkeyarr = tag.getIntArray("hotkey_modes");
        if (hotkeyarr == null) return;
        if (hotkeyarr.length != 4) return;
        
        int mode_id = hotkeyarr[hotkey_use];
        var whistle_modes = WhistleMode.VALUES;
        if (mode_id >= whistle_modes.length) return;
        if (mode_id < 0) return;
        var useMode = whistle_modes[mode_id];

        List<Dog> dogsList = player.level().getEntitiesOfClass(
            Dog.class, 
            player.getBoundingBox().inflate(100D, 50D, 100D), 
            dog -> dog.isDoingFine() && dog.isOwnedBy(player)
        );
        whistle.useMode(useMode, dogsList, 
            player.level(), player, InteractionHand.MAIN_HAND, true);
        PacketHandler.send(PacketDistributor.SERVER.noArg(), 
            new WhistleUseData(mode_id));
    }

    public void drawSelectionBox(PoseStack matrixStackIn, Player player, float particleTicks, AABB boundingBox) {
        // RenderSystem.setShader(GameRenderer::getPositionTexShader);
        // RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        // // RenderSystem.disableAlphaTest();
        // RenderSystem.depthMask(false);
        // RenderSystem.enableBlend();
        // RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        // RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 0.7F);
        // //TODO Used when drawing outline of bounding box
        // RenderSystem.lineWidth(2.0F);


        // //RenderSystem.disableTexture();
        // Vec3 vec3d = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        // double d0 = vec3d.x();
        // double d1 = vec3d.y();
        // double d2 = vec3d.z();

        // BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        // bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        // LevelRenderer.renderLineBox(matrixStackIn, bufferbuilder, boundingBox.move(-d0, -d1, -d2), 1F, 1F, 0F, 0.8F);
        // Tesselator.getInstance().end();
        // RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 0.3F);
        // RenderSystem.depthMask(true);
        // //RenderSystem.enableTexture();
        // RenderSystem.disableBlend();
        // //RenderSystem.enableAlphaTest();
        // RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void onDogMountEvent(DogMountData data) {
        Minecraft mc = Minecraft.getInstance();
        Entity e = mc.level.getEntity(data.dogId);
        var player = mc.player;
        if (e instanceof Dog d) {
            if (data.mount && player != null) {
                d.startRiding(player);
            } else {
                d.stopRiding();
            }
        }
    }

    public static boolean vertifyBlockTexture(ResourceLocation loc) {
        var path = getAbsoluteBlockTexture(loc);
        var res = Minecraft.getInstance().getResourceManager()
            .getResource(path);
        return res.isPresent();
    }

    public static ResourceLocation getAbsoluteBlockTexture(ResourceLocation loc) {
        return Util.getResource(loc.getNamespace(), "textures/" + loc.getPath() + ".png");
    }

    public static boolean vertifyArmorTexture(ResourceLocation loc) {
        var res = Minecraft.getInstance().getResourceManager()
            .getResource(loc);
        return res.isPresent();
    }

    public static boolean shouldClientBlockPick(Dog dog) {
        var player = Minecraft.getInstance().player;
        if (player == null)
            return false;
        if (!dog.isVehicle())
            return false;
        return player.getVehicle() == dog
            || player.isShiftKeyDown();
    }

    public static float getAnimatedYRot(Dog dog) {
        if (dog.getAnim() == DogAnimation.NONE)
            return 0f;
        var anim = dog.getAnim();
        var animSeq = DogAnimationRegistry.getSequence(anim);
        var animState = dog.animationManager.animationState;
        var ret = DogKeyframeAnimations.getCurrentAnimatedYRot(dog, animSeq, animState.getAccumulatedTimeMillis(), 1);
        if (anim.rootRotaton().isPresent()) {
            var root_rotation = anim.rootRotaton().get();
            ret += root_rotation * Mth.DEG_TO_RAD;
        }
        return ret;
    }

    public static void onDogSyncedDataUpdated(DogSyncData data) {
        var mc = Minecraft.getInstance();
        var level = mc.level;
        if (level == null)
            return;
        var e = level.getEntity(data.dogId);
        if (!(e instanceof Dog dog))
            return;
        dog.dogSyncedDataManager.updateFromDataPacketFromServer(data);
    }

    public static void onDogTalentUpdated(Dog dog) {
        var mc = Minecraft.getInstance();
        var screen = mc.screen;
        if (screen == null)
            return;
        if (!(screen instanceof DogNewInfoScreen infoScr))
            return;
        if (infoScr.dog != dog)
            return;
        Store.get(infoScr).dispatch(TalentChangeHandlerSlice.class, new UIAction(UIActionTypes.Talents.TALENT_UPDATE, null));
    }

}
