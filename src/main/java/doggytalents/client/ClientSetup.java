package doggytalents.client;

import java.util.ArrayList;
import java.util.List;

import doggytalents.DoggyContainerTypes;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyTileEntityTypes;
import doggytalents.api.events.RegisterDogSkinJsonPathEvent;
import doggytalents.client.block.model.RiceMillModel;
import doggytalents.client.block.render.RiceMillRenderer;
import doggytalents.client.entity.model.DogArmorModel;
import doggytalents.client.entity.model.DogBackpackModel;
import doggytalents.client.entity.model.DogFrontLegsSeperate;
import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.model.DogRescueModel;
import doggytalents.client.entity.model.FisherDogModel;
import doggytalents.client.entity.model.SyncedRenderFunctionWithHeadModel;
import doggytalents.client.entity.model.TorchDogModel;
import doggytalents.client.entity.model.animation.DTNAnimationLoader;
import doggytalents.client.entity.model.animation.DTNModelLoader;
import doggytalents.client.entity.model.animation.DogAnimationRegistry;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.dog.NullDogModel;
import doggytalents.client.entity.model.dog.VariantDogModel;
import doggytalents.client.entity.model.misc.DogPlushieModel;
import doggytalents.client.entity.model.misc.GrandPianoModel;
import doggytalents.client.entity.model.misc.SamoyedPlushieModel;
import doggytalents.client.entity.model.misc.UprightPianoModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.client.entity.render.CollarRenderManager;
import doggytalents.client.entity.render.DogFoodProjectileRenderer;
import doggytalents.client.entity.render.DogGunpowderProjectileRenderer;
import doggytalents.client.entity.render.DogScreenOverlays;
import doggytalents.client.entity.render.DogRenderer;
import doggytalents.client.entity.render.DoggyBeamRenderer;
import doggytalents.client.entity.render.layer.DogCustomGlowingOverlayRenderer;
import doggytalents.client.entity.render.layer.DogMouthItemRenderer;
import doggytalents.client.entity.render.layer.DogVariantRenderer;
import doggytalents.client.entity.render.layer.DogWolfArmorRenderer;
import doggytalents.client.entity.render.layer.FisherDogRenderer;
import doggytalents.client.entity.render.layer.PackPuppyRenderer;
import doggytalents.client.entity.render.layer.RescueDogRenderer;
import doggytalents.client.entity.render.layer.TorchDogRenderer;
import doggytalents.client.entity.render.layer.accessory.AccessoryModelRenderer;
import doggytalents.client.entity.render.layer.accessory.DoggyArmorRenderer;
import doggytalents.client.entity.render.layer.accessory.DefaultAccessoryRenderer;
import doggytalents.client.entity.render.layer.accessory.IncapacitatedRenderer;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.misc.DogArrowRenderer;
import doggytalents.client.entity.render.misc.DogPlushieRenderer;
import doggytalents.client.entity.render.misc.DogThrownTridentRenderer;
import doggytalents.client.entity.render.misc.PianoRenderer;
import doggytalents.client.entity.render.misc.SamoyedPlushieRenderer;
import doggytalents.client.entity.versionfix.FixClientTeleportDesync_1_21;
import doggytalents.client.screen.DogArmorScreen;
import doggytalents.client.screen.DogInventoriesScreen;
import doggytalents.client.screen.DoggyToolsScreen;
import doggytalents.client.screen.FoodBowlScreen;
import doggytalents.client.screen.PackPuppyScreen;
import doggytalents.client.screen.RiceMillScreen;
import doggytalents.client.screen.TreatBagScreen;
import doggytalents.client.screen.widget.DoggySpin.DoggySpinModel;
import doggytalents.client.tileentity.renderer.DogBedRenderer;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;
import doggytalents.common.effects.NattoBiteEffect;
import net.neoforged.fml.ModLoader;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

public class ClientSetup {

    public static final ModelLayerLocation DOG = new ModelLayerLocation(Util.getResource("dog"), "main");
    public static final ModelLayerLocation DOG_LEGACY = new ModelLayerLocation(Util.getResource("dog_legacy"), "main");
    
    public static final ModelLayerLocation DOG_ARMOR = new ModelLayerLocation(Util.getResource("dog"), "armor");
    public static final ModelLayerLocation DOG_ARMOR_LEGACY = new ModelLayerLocation(Util.getResource("dog"), "armor_legacy");
    public static final ModelLayerLocation DOG_FRONT_LEGS_SEPERATE = new ModelLayerLocation(Util.getResource("dog_hind_leg_diff_tex"), "main");
    public static final ModelLayerLocation DOG_BACKPACK = new ModelLayerLocation(Util.getResource("dog_backpack"), "main");
    public static final ModelLayerLocation DOG_RESCUE_BOX = new ModelLayerLocation(Util.getResource("dog_rescue_box"), "main");
    public static final ModelLayerLocation DOG_SYNCED_FUNCTION_WITH_HEAD = new ModelLayerLocation(Util.getResource("dog_mouth_item"), "main");
    public static final ModelLayerLocation DOG_TORCHIE = new ModelLayerLocation(Util.getResource("dog_torchie"), "main");
    public static final ModelLayerLocation DOG_FISHER_HAT = new ModelLayerLocation(Util.getResource("dog_fisher_hat"), "main");

    public static final ModelLayerLocation DOG_NULL = new ModelLayerLocation(Util.getResource("dog_null"), "main");

    public static final ModelLayerLocation PIANO = new ModelLayerLocation(Util.getResource("piano"), "main");
    public static final ModelLayerLocation PIANO_UPRIGHT = new ModelLayerLocation(Util.getResource("piano_upright"), "main");
    public static final ModelLayerLocation DOG_PLUSHIE = new ModelLayerLocation(Util.getResource("dog_plushie_toy"), "main");
    public static final ModelLayerLocation RICE_MILL = new ModelLayerLocation(Util.getResource("rice_mill"), "main");
    public static final ModelLayerLocation SAMOYED_PLUSHIE = new ModelLayerLocation(Util.getResource("samoyed_plushie_toy"), "main");

    public static final List<Identifier> OTHER_MOD_SKIN_JSONS = new ArrayList<Identifier>();

    public static void setupScreenManagers(final RegisterMenuScreensEvent event) {
        event.register(DoggyContainerTypes.FOOD_BOWL.get(), FoodBowlScreen::new);
        event.register(DoggyContainerTypes.PACK_PUPPY.get(), PackPuppyScreen::new);
        event.register(DoggyContainerTypes.TREAT_BAG.get(), TreatBagScreen::new);
        event.register(DoggyContainerTypes.DOG_INVENTORIES.get(), DogInventoriesScreen::new);
        event.register(DoggyContainerTypes.DOG_ARMOR.get(), DogArmorScreen::new);
        event.register(DoggyContainerTypes.DOG_TOOLS.get(), DoggyToolsScreen::new);
        event.register(DoggyContainerTypes.RICE_MILL.get(), RiceMillScreen::new);
    }

    public static void setupEntityRenderers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG, DogModel::createBodyLayer);
        event.registerLayerDefinition(DOG_LEGACY, VariantDogModel::createBodyLayer);

        event.registerLayerDefinition(DOG_ARMOR, DogArmorModel::createBodyLayer);
        event.registerLayerDefinition(DOG_ARMOR_LEGACY, DogArmorModel::createLegacyLayer);
        event.registerLayerDefinition(DOG_FRONT_LEGS_SEPERATE, DogFrontLegsSeperate::createBodyLayer);
        event.registerLayerDefinition(DOG_BACKPACK, DogBackpackModel::createChestLayer);
        event.registerLayerDefinition(DOG_RESCUE_BOX, DogRescueModel::createRescueBoxLayer);
        event.registerLayerDefinition(DOG_SYNCED_FUNCTION_WITH_HEAD, SyncedRenderFunctionWithHeadModel::createLayer);
        event.registerLayerDefinition(DOG_TORCHIE, TorchDogModel::createLayer);
        event.registerLayerDefinition(DOG_FISHER_HAT, FisherDogModel::createLayer);

        event.registerLayerDefinition(DOG_NULL, NullDogModel::createBodyLayer);

        event.registerLayerDefinition(PIANO, GrandPianoModel::creatPianoLayer);
        event.registerLayerDefinition(PIANO_UPRIGHT, UprightPianoModel::createPianoLayer);
        event.registerLayerDefinition(DOG_PLUSHIE, DogPlushieModel::createBodyLayer);
        event.registerLayerDefinition(RICE_MILL, RiceMillModel::createLayer);
        event.registerLayerDefinition(SAMOYED_PLUSHIE, SamoyedPlushieModel::createBodyLayer);

        AccessoryModelRenderEntries.registerEntries();
        AccessoryModelManager.registerLayerDef(event);

        DogAnimationRegistry.init();
        DogModelRegistry.init();
        gatherSkinJsonFromOtherMods();
    }

    private static void gatherSkinJsonFromOtherMods() {
        OTHER_MOD_SKIN_JSONS.clear();
        var paths = new ArrayList<Identifier>();
        ModLoader.postEvent(new RegisterDogSkinJsonPathEvent(paths));
        if (paths.isEmpty())
            return;
        OTHER_MOD_SKIN_JSONS.addAll(paths);
    }

    public static void setupTileEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DoggyEntityTypes.DOG.get(), DogRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.DOG_FOOD_PROJ.get(), DogFoodProjectileRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.DOG_GUNPOWDER_PROJ.get(), DogGunpowderProjectileRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.DOG_BEAM.get(), DoggyBeamRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.DOG_TRIDENT_PROJ.get(), DogThrownTridentRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.DOG_ARROW_PROJ.get(), DogArrowRenderer::new);
        event.registerBlockEntityRenderer(DoggyTileEntityTypes.DOG_BED.get(), DogBedRenderer::new);
        event.registerBlockEntityRenderer(DoggyTileEntityTypes.RICE_MILL.get(), RiceMillRenderer::new);

        event.registerEntityRenderer(DoggyEntityTypes.GRAND_PIANO_BLACK.get(), PianoRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.GRAND_PIANO_WHITE.get(), PianoRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.UPRIGHT_PIANO_BLACK.get(), PianoRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.UPRIGHT_PIANO_BROWN.get(), PianoRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.UPRIGHT_PIANO_WHITE.get(), PianoRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.DOG_PLUSHIE_TOY.get(), DogPlushieRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.SAMOYED_PLUSHIE_TOY.get(), SamoyedPlushieRenderer::new);
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        setupCollarRenderers();
        DoggySpinModel.init();
        FixClientTeleportDesync_1_21.init();
    }

    public static void setupCollarRenderers() {
        
        CollarRenderManager.registerLayer(DogVariantRenderer::new);
        CollarRenderManager.registerLayer(DogCustomGlowingOverlayRenderer::new);
        CollarRenderManager.registerLayer(DefaultAccessoryRenderer::new);
        CollarRenderManager.registerLayer(DogWolfArmorRenderer::new);
        CollarRenderManager.registerLayer(IncapacitatedRenderer::new);
        CollarRenderManager.registerLayer(DoggyArmorRenderer::new);
        CollarRenderManager.registerLayer(PackPuppyRenderer::new);
        CollarRenderManager.registerLayer(RescueDogRenderer::new);
        CollarRenderManager.registerLayer(AccessoryModelRenderer::new);
        CollarRenderManager.registerLayer(DogMouthItemRenderer::new);
        CollarRenderManager.registerLayer(TorchDogRenderer::new);
        CollarRenderManager.registerLayer(FisherDogRenderer::new);
    }

    public static void registerOverlay(RegisterGuiLayersEvent e) {
    }

    public static void addClientReloadListeners(final AddClientReloadListenersEvent event) {
        event.addListener(doggytalents.common.util.Util.getResource("dtn_model_loader"), DTNModelLoader.INSTANCE);
        event.addListener(doggytalents.common.util.Util.getResource("dog_texture_manager"), DogTextureManager.INSTANCE);
        event.addListener(doggytalents.common.util.Util.getResource("dog_random_name_registry"), DogRandomNameRegistry.getInstance());
        event.addListener(doggytalents.common.util.Util.getResource("dtn_animation_loader"), DTNAnimationLoader.INSTANCE);
    }

    public static void registerClientExtensions(final RegisterClientExtensionsEvent event) {
        event.registerMobEffect(new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInGui(net.minecraft.world.effect.MobEffectInstance instance) {
                return false;
            }
            @Override
            public boolean isVisibleInInventory(net.minecraft.world.effect.MobEffectInstance instance) {
                return false;
            }
        }, doggytalents.DoggyEffects.NATTO_BITE.get());
    }
}
