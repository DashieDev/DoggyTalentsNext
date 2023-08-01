package doggytalents.client.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;

import doggytalents.DoggyBlocks;
import doggytalents.DoggyItems;
import doggytalents.DoggyTalentsNext;
import doggytalents.client.DoggyKeybinds;
import doggytalents.client.block.model.DogBedModel;
import doggytalents.client.screen.widget.DogInventoryButton;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.WhistleItem;
import doggytalents.common.item.WhistleItem.WhistleMode;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogMountData;
import doggytalents.common.network.packet.data.OpenDogScreenData;
import doggytalents.common.network.packet.data.WhistleUseData;
import doggytalents.common.util.InventoryUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

public class ClientEventHandler {

    DogInventoryButton activeInventoryButton;

    public static void registerModelForBaking(final ModelEvent.RegisterAdditional event) {

        try {
            ResourceLocation resourceLocation = ForgeRegistries.BLOCKS.getKey(DoggyBlocks.DOG_BED.get());
            ResourceLocation unbakedModelLoc = new ResourceLocation(resourceLocation.getNamespace(), "block/" + resourceLocation.getPath());
            event.register(unbakedModelLoc);
            
        }
        catch(Exception e) {
            DoggyTalentsNext.LOGGER.warn("Could not get base Dog Bed model. Reverting to default textures...");
            e.printStackTrace();
        }
    }

    public static void modifyBakedModels(final ModelEvent.ModifyBakingResult event) {
        try {
            var modelRegistry = event.getModels();

            ResourceLocation resourceLocation = ForgeRegistries.BLOCKS.getKey(DoggyBlocks.DOG_BED.get());
            ResourceLocation bakedModelLoc = new ResourceLocation(resourceLocation.getNamespace(), "block/" + resourceLocation.getPath());

            var model = modelRegistry.get(bakedModelLoc);

            var modelUnbaked = (BlockModel) event.getModelBakery().getModel(bakedModelLoc);

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

            this.activeInventoryButton = new DogInventoryButton(x, y, screen, (btn) -> {
                PacketHandler.send(PacketDistributor.SERVER.noArg(), new OpenDogScreenData());
                btn.active = false;
            });

            event.addListener(this.activeInventoryButton);
        }
    }

    // No need more
    // @SubscribeEvent
    // public void onScreenDrawForeground(final ScreenEvent.Render event) {
    //     Screen screen = event.getScreen();
    //     if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
    //         boolean creative = screen instanceof CreativeModeInventoryScreen;
    //         var btn = this.activeInventoryButton;
    //         if (btn == null) return;
    //         //To Remove
    //         if (btn.visible && btn.isHoveredOrFocused()) {
    //             Minecraft mc = Minecraft.getInstance();
    //             int width = mc.getWindow().getGuiScaledWidth();
    //             int height = mc.getWindow().getGuiScaledHeight();
    //             int sizeX = creative ? 195 : 176;
    //             int sizeY = creative ? 136 : 166;
    //             int guiLeft = (width - sizeX) / 2;
    //             int guiTop = (height - sizeY) / 2;
    //             if (!creative) {
    //                 RecipeBookComponent recipeBook = ((InventoryScreen) screen).getRecipeBookComponent();
    //                 if (recipeBook.isVisible()) {
    //                     guiLeft += 76;
    //                 }
    //             }

    //             //event.getPoseStack().translate(-guiLeft, -guiTop, 0);
    //             //btn.renderToolTip(event.getPoseStack(), event.getMouseX(), event.getMouseY());
    //             //btn.render(event.getPoseStack(), event.getMouseX(), event.getMouseY(), event.getPartialTick());
    //             //event.getPoseStack().translate(guiLeft, guiTop, 0);
    //         }
    //     }
    // }

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
        
        var tag = whistle_stack.getTag();
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
            player.level(), player, InteractionHand.MAIN_HAND);
        PacketHandler.send(PacketDistributor.SERVER.noArg(), 
            new WhistleUseData(mode_id));
    }



// TODO Implement patrol item
//    @SubscribeEvent
//    public void onWorldRenderLast(RenderWorldLastEvent event) {
//        Minecraft mc = Minecraft.getInstance();
//        PlayerEntity player = mc.player;
//
//        if (player == null || player.getHeldItem(Hand.MAIN_HAND).getItem() != DoggyItems.PATROL.get()) {
//            return;
//        }
//
//        ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
//
//        RenderSystem.pushMatrix();
//
//        if (stack.hasTag() && stack.getTag().contains("patrolPos", Tag.TAG_LIST)) {
//            ListNBT list = stack.getTag().getList("patrolPos", Tag.TAG_COMPOUND);
//            List<BlockPos> poses = new ArrayList<>(list.size());
//            for (int i = 0; i < list.size(); i++) {
//                poses.add(NBTUtil.getBlockPos(list.getCompound(i)));
//            }
//
//            for (BlockPos pos : poses) {
//                this.drawSelectionBox(event.getMatrixStack(), player, event.getPartialTicks(), new AxisAlignedBB(pos));
//            }
//        }
//
//
//        RenderSystem.popMatrix();
//    }

    public void drawSelectionBox(PoseStack matrixStackIn, Player player, float particleTicks, AABB boundingBox) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        // RenderSystem.disableAlphaTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 0.7F);
        //TODO Used when drawing outline of bounding box
        RenderSystem.lineWidth(2.0F);


        //RenderSystem.disableTexture();
        Vec3 vec3d = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        double d0 = vec3d.x();
        double d1 = vec3d.y();
        double d2 = vec3d.z();

        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        LevelRenderer.renderLineBox(matrixStackIn, bufferbuilder, boundingBox.move(-d0, -d1, -d2), 1F, 1F, 0F, 0.8F);
        Tesselator.getInstance().end();
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 0.3F);
        RenderSystem.depthMask(true);
        //RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        //RenderSystem.enableAlphaTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
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

//    @SubscribeEvent
//    public void onPreRenderGameOverlay(final RenderGameOverlayEvent.Post event) {
//        // TODO
//        label: if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT) {
//            Minecraft mc = Minecraft.getInstance();
//
//            if (mc.player == null || !(mc.player.getVehicle() instanceof DogEntity)) {
//                break label;
//            }
//
//            PoseStack stack = event.getMatrixStack();
//
//            DogEntity dog = (DogEntity) mc.player.getVehicle();
//            int width = mc.getWindow().getGuiScaledWidth();
//            int height = mc.getWindow().getGuiScaledHeight();
//            RenderSystem.pushMatrix();
//            RenderSystem.setShader(GameRenderer::getPositionTexShader);
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//            RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_LOCATION;
//            RenderSystem.enableBlend();
//            int left = width / 2 + 91;
//            int top = height - ((ForgeIngameGui) mc.gui).right_height;
//            ((ForgeIngameGui) mc.gui).right_height += 10;
//            int level = Mth.ceil((dog.getDogHunger() / dog.getMaxHunger()) * 20.0D);
//
//            for (int i = 0; i < 10; ++i) {
//                int idx = i * 2 + 1;
//                int x = left - i * 8 - 9;
//                int y = top;
//                int icon = 16;
//                byte backgound = 12;
//
//                mc.gui.blit(stack, x, y, 16 + backgound * 9, 27, 9, 9);
//
//
//                if (idx < level) {
//                    mc.gui.blit(stack, x, y, icon + 36, 27, 9, 9);
//                } else if (idx == level) {
//                    mc.gui.blit(stack, x, y, icon + 45, 27, 9, 9);
//                }
//            }
//            RenderSystem.disableBlend();
//
//            RenderSystem.enableBlend();
//            left = width / 2 + 91;
//            top = height - ForgeIngameGui.right_height;
//            RenderSystem.color4f(1.0F, 1.0F, 0.0F, 1.0F);
//            int l6 = dog.getAirSupply();
//            int j7 = dog.getMaxAirSupply();
//
//            if (dog.isEyeInFluid(FluidTags.WATER) || l6 < j7) {
//                int air = dog.getAirSupply();
//                int full = Mth.ceil((air - 2) * 10.0D / 300.0D);
//                int partial = Mth.ceil(air * 10.0D / 300.0D) - full;
//
//                for (int i = 0; i < full + partial; ++i) {
//                    mc.gui.blit(stack, left - i * 8 - 9, top, (i < full ? 16 : 25), 18, 9, 9);
//                }
//                ForgeIngameGui.right_height += 10;
//            }
//            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//            RenderSystem.disableBlend();
//
//            RenderSystem.popMatrix();
////        }
//    }
}
