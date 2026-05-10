package doggytalents.client.event;

import com.mojang.blaze3d.opengl.GlStateManager;
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
import doggytalents.client.entity.render.world.CanineTrackerLocateRenderer;
import doggytalents.client.screen.DogNewInfoScreen.DogNewInfoScreen;
import doggytalents.client.screen.DogNewInfoScreen.store.UIActionTypes;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.TalentChangeHandlerSlice;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.client.screen.widget.DogInventoryButton;
import doggytalents.client.screen.widget.DoggySpin.DoggySpin;
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
import doggytalents.common.variant.DogVariant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import doggytalents.common.network.PacketDistributor;

import java.util.List;

public class ClientEventHandler {

    public static void registerModelForBaking(final ModelEvent.RegisterStandalone event) {
        // In 26.1, standalone model registration is no longer needed.
        // The dog bed model is referenced by blockstate JSON and loaded automatically.
    }

    public static void modifyBakedModels(final ModelEvent.ModifyBakingResult event) {
        DogBedModel.clearCache();
        try {
            var bakingResult = event.getBakingResult();
            var textureGetter = event.getTextureGetter();
            var modelBakery = event.getModelBakery();

            // Get the dog bed block's identifier to locate the model
            Identifier resourceLocation = BuiltInRegistries.BLOCK.getKey(DoggyBlocks.DOG_BED.get());
            Identifier modelLocation = Util.getResource(resourceLocation.getNamespace(), "block/" + resourceLocation.getPath());

            // Get the resolved model for the dog bed so we can bake variants
            var resolvedModels = ((doggytalents.mixin.ModelBakeryMixinAccessor) modelBakery).dtn__getResolvedModels();
            var resolvedModel = resolvedModels.get(modelLocation);
            if (resolvedModel == null) {
                DoggyTalentsNext.LOGGER.warn("Could not find resolved model for dog bed: {}", modelLocation);
                return;
            }

            // Build default parts map per facing direction from the already-baked models
            var defaultParts = new java.util.EnumMap<net.minecraft.core.Direction, net.minecraft.client.renderer.block.dispatch.BlockStateModelPart>(net.minecraft.core.Direction.class);
            var dogBedBlock = DoggyBlocks.DOG_BED.get();
            net.minecraft.client.renderer.block.dispatch.BlockStateModelPart missingPart = null;

            for (var blockState : dogBedBlock.getStateDefinition().getPossibleStates()) {
                var defaultModel = bakingResult.blockStateModels().get(blockState);
                if (defaultModel == null) continue;

                // Extract the BlockStateModelPart from the default model
                // SingleVariant has a single part; collect it
                var tempParts = new java.util.ArrayList<net.minecraft.client.renderer.block.dispatch.BlockStateModelPart>();
                defaultModel.collectParts(net.minecraft.util.RandomSource.create(), tempParts);
                if (!tempParts.isEmpty()) {
                    net.minecraft.core.Direction facing = net.minecraft.core.Direction.NORTH;
                    if (blockState.hasProperty(doggytalents.common.block.DogBedBlock.FACING)) {
                        facing = blockState.getValue(doggytalents.common.block.DogBedBlock.FACING);
                    }
                    defaultParts.put(facing, tempParts.get(0));
                    if (missingPart == null) missingPart = tempParts.get(0);
                }
            }

            if (missingPart == null) {
                DoggyTalentsNext.LOGGER.warn("No default dog bed model parts found, skipping custom model setup");
                return;
            }

            // Create a ModelBaker for on-demand variant baking
            var variantBaker = DogBedModel.createVariantBaker(modelBakery, textureGetter, missingPart);

            // Create the DynamicBlockStateModel
            var dynamicModel = new DogBedModel(
                defaultParts,
                resolvedModel,
                variantBaker,
                ConfigHandler.CLIENT.MAX_DOG_BED_MODEL_CACHE.get()
            );

            // Replace all dog bed block state models with the dynamic one
            for (var blockState : dogBedBlock.getStateDefinition().getPossibleStates()) {
                bakingResult.blockStateModels().put(blockState, dynamicModel);
            }

        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.warn("Error setting up DogBed custom model. Reverting to default textures...", e);
        }
    }

    @SubscribeEvent
    public void onInputEvent(final MovementInputUpdateEvent event) {
        if (!event.getInput().keyPresses.jump())
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
        DogInventoryButton.onScreenInit(event);
        DoggySpin.onScreenInit(event);
    }

    
    @SubscribeEvent
    public void onScreenDrawForeground(final ScreenEvent.Render.Post event) {
        DoggySpin.onScreenRenderForeground(event);
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

        if (player.getCooldowns().isOnCooldown(new net.minecraft.world.item.ItemStack(whistle))) return;
        
        var tag = ItemUtil.getTag(whistle_stack);
        if (tag == null) return;
        var hotkeyarr = tag.getIntArray("hotkey_modes").orElse(null);
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
        whistle.useMode(useMode, false, dogsList, 
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
        // // Used when drawing outline of bounding box
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

    public static boolean vertifyBlockTexture(Identifier loc) {
        var path = getAbsoluteBlockTexture(loc);
        var res = Minecraft.getInstance().getResourceManager()
            .getResource(path);
        return res.isPresent();
    }

    public static Identifier getAbsoluteBlockTexture(Identifier loc) {
        return Util.getResource(loc.getNamespace(), "textures/" + loc.getPath() + ".png");
    }

    public static boolean vertifyArmorTexture(Identifier loc) {
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
        if (animSeq == null)
            return 0f;
        var animState = dog.animationManager.animationState;
        var ret = DogKeyframeAnimations.getCurrentAnimatedYRot(dog, animSeq, animState.getAccumulatedTimeMillis(), 1);
        if (anim.rootRotation().isPresent()) {
            var root_rotation = anim.rootRotation().get();
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

    @SubscribeEvent
    public void onPlayerLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        CanineTrackerLocateRenderer.onWorldLogOut();
    }

    public static String getTranslatedVariantStr(DogVariant variant) {
        var translation_key = variant.translation();
        if (I18n.exists(translation_key))
            return I18n.get(translation_key);
        return variant.id().toString();
    }

    public static boolean showWolfMountHealth() {
        if (ConfigHandler.CLIENT.HIDE_WOLF_MOUNT_STATUS.get())
            return false;
        var mc = Minecraft.getInstance();
        var game_mode = mc.gameMode;
        if (game_mode == null)
            return false;
        boolean creative_hide_condition = 
            !game_mode.canHurtPlayer()
            && ConfigHandler.CLIENT.HIDE_WOLF_MOUNT_STATUS_CREATIVE.get();
        if (creative_hide_condition)
            return false;
        
        return true;
    } 

    @SubscribeEvent
    public void onRenderGuiLayer(net.neoforged.neoforge.client.event.RenderGuiLayerEvent.Pre event) {
        if (!event.getName().equals(net.neoforged.neoforge.client.gui.VanillaGuiLayers.VEHICLE_HEALTH)) return;
        if (doggytalents.client.DTNWolfMountCustomGuiOverlay.onRenderVehicleHealth(event.getGuiGraphics(), null)) {
            event.setCanceled(true);
        }
    }

    public static boolean shouldRenderAnimDebugNametag(Dog dog) {
        var mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null)
            return false;
        if (!dog.isDogInAnimDebug())
            return false;
        var mainhand_item = player.getMainHandItem();
        if (mainhand_item == null 
            || mainhand_item.getItem() != DoggyItems.DOG_ANIM_DEBUG.get())
            return false;
        var owner_uuid = dog.getOwnerUUID();
        if (owner_uuid == null || 
            !owner_uuid.equals(player.getUUID()))
            return false;
        return true;
    }
}
