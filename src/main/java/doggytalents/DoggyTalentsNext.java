package doggytalents;

import doggytalents.api.feature.FoodHandler;
import doggytalents.client.ClientSetup;
import doggytalents.client.DTNClientDogSleepOnManager;
import doggytalents.client.DoggyKeybinds;
import doggytalents.client.DTNClientPettingManager;
import doggytalents.client.data.DTBlockstateProvider;
import doggytalents.client.data.DTItemModelProvider;
import doggytalents.client.entity.render.DoggyArmorMapping;
import doggytalents.client.entity.render.world.BedFinderRenderer;
import doggytalents.client.entity.render.world.CanineTrackerLocateRenderer;
import doggytalents.client.event.ClientEventHandler;
import doggytalents.client.screen.widget.DoggySpin.DoggySpinModel;
import doggytalents.common.block.tileentity.RiceMillBlockEntity;
//import doggytalents.common.addon.AddonManager;
import doggytalents.common.command.DoggyCommands;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.data.*;
import doggytalents.common.data.neoforge_data.DTNNeoForgeDataEntry;
import doggytalents.common.entity.BoostingFoodHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.MeatFoodHandler;
import doggytalents.common.entity.WhitelistFoodHandler;
import doggytalents.common.entity.texture.DogAllowedSkinManager;
import doggytalents.common.entity.DogDrinkMilkHandler;
import doggytalents.common.event.EventHandler;
import doggytalents.common.event.PackHandler;
import doggytalents.common.item.ChopinRecordItem;
import doggytalents.common.item.itemgroup.DTNCompostables;
import doggytalents.common.item.itemgroup.DTNItemCategory;
import doggytalents.common.lib.Constants;
import doggytalents.common.network.DTNNetworkHandler;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.talent.HappyEaterTalent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Constants.MOD_ID)
public class DoggyTalentsNext {

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID);

    // CurseForge publishing automation pending
    public DoggyTalentsNext() {
        var modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();

        // Mod lifecycle
        modEventBus.addListener(GatherDataEvent.Client.class, this::gatherDataClient);
        modEventBus.addListener(GatherDataEvent.Server.class, this::gatherDataServer);
        modEventBus.addListener(this::commonSetup);

        // Registries
        DogVariants.DOG_VARIANT.register(modEventBus);
        DogVariants.DOG_VARIANT_VANILLA.register(modEventBus);
        DoggyBlocks.BLOCKS.register(modEventBus);
        DoggyTileEntityTypes.TILE_ENTITIES.register(modEventBus);
        DoggyItems.ITEMS.register(modEventBus);
        DoggyEntityTypes.ENTITIES.register(modEventBus);
        DoggyContainerTypes.CONTAINERS.register(modEventBus);
        DoggySerializers.SERIALIZERS.register(modEventBus);
        TalentsOptions.TALENT_OPTIONS.register(modEventBus);
        DoggySounds.SOUNDS.register(modEventBus);
        DoggyRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        DoggyTalents.TALENTS.register(modEventBus);
        DoggyAccessories.ACCESSORIES.register(modEventBus);
        DoggyAccessoryTypes.ACCESSORY_TYPES.register(modEventBus);
        DoggyAttributes.ATTRIBUTES.register(modEventBus);
        DoggyItemGroups.ITEM_GROUP.register(modEventBus);
        DoggyEffects.EFFECTS.register(modEventBus);
        DoggyAdvancementTriggers.TRIGGERS.register(modEventBus);
        DoggyEntitySubPredicates.ENTITY_SUB_PREDICATES.register(modEventBus);

        DTLootModifierProvider.CODEC.register(modEventBus);

        modEventBus.addListener(DoggyRegistries::newRegistry);
        modEventBus.addListener(DoggyEntityTypes::addEntityAttributes);
        modEventBus.addListener(DTNNetworkHandler::onRegisterPayloadEvent);
        modEventBus.addListener(ClientSetup::setupScreenManagers);
        modEventBus.addListener(PackHandler::onAddPackFinder);

        var forgeEventBus = NeoForge.EVENT_BUS;
        forgeEventBus.addListener(this::serverStarting);
        forgeEventBus.addListener(this::registerCommands);
        forgeEventBus.addListener(DoggyBrewingRecipes::onRegisterEvent);
        forgeEventBus.addListener(ChopinRecordItem::onRightClickBlock);
        forgeEventBus.addListener(DogAllowedSkinManager::onRegisterReloadListener);
        forgeEventBus.addListener(DogAllowedSkinManager::onDataPackSyncServer);

        forgeEventBus.register(new EventHandler());

        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            modEventBus.addListener(DoggyKeybinds::registerDTKeyMapping);
            modEventBus.addListener(this::clientSetup);
            // modEventBus.addListener(DoggyBlocks::registerBlockColours); // needs migration to ItemTintSource
            // modEventBus.addListener(DoggyItems::registerItemColours); // needs migration to ItemTintSource
            modEventBus.addListener(ClientEventHandler::registerModelForBaking);
            modEventBus.addListener(ClientEventHandler::modifyBakedModels);
            modEventBus.addListener(ClientSetup::setupTileEntityRenderers);
            modEventBus.addListener(ClientSetup::setupEntityRenderers);
            modEventBus.addListener(ClientSetup::addClientReloadListeners);
            modEventBus.addListener(ClientSetup::registerOverlay);
            modEventBus.addListener(ClientSetup::registerClientExtensions);
            forgeEventBus.register(new ClientEventHandler());
            forgeEventBus.addListener(BedFinderRenderer::onWorldRenderLast);
            forgeEventBus.addListener(CanineTrackerLocateRenderer::onWorldRenderLast);
            forgeEventBus.addListener(CanineTrackerLocateRenderer::tickUpdate);
            forgeEventBus.register(DTNClientPettingManager.get());
            forgeEventBus.register(DTNClientDogSleepOnManager.get());
        }

        ConfigHandler.init(modEventBus);

        //AddonManager.init();
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        DTNNetworkHandler.init();
        PacketHandler.init();
        FoodHandler.registerHandler(new MeatFoodHandler());
        FoodHandler.registerHandler(new BoostingFoodHandler());
        FoodHandler.registerHandler(new DogDrinkMilkHandler());
        FoodHandler.registerHandler(new WhitelistFoodHandler());

        event.enqueueWork(() -> {
            ConfigHandler.initTalentConfig();
            RiceMillBlockEntity.initGrindMap();
            DTNItemCategory.init();
            DTNCompostables.init();
        });
    }

    public void serverStarting(final ServerStartingEvent event) {
    }

    public void registerCommands(final RegisterCommandsEvent event) {
        DoggyCommands.register(event.getDispatcher());
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        ClientSetup.onClientSetup(event);
    }

    private void gatherDataClient(final GatherDataEvent.Client event) {
        var packOutput = event.getGenerator().getPackOutput();

        DTNPackMetadataProvider.start(event);
        event.addProvider(new DTBlockstateProvider(packOutput));
        event.addProvider(new DTItemModelProvider(packOutput));

        DTNDatapackProvider.start(event);
        DTNDataRegistryProvider.start(event);
        DTNNeoForgeDataEntry.onGatherData(event);
    }

    private void gatherDataServer(final GatherDataEvent.Server event) {
        var packOutput = event.getGenerator().getPackOutput();
        var lookup = event.getLookupProvider();

        DTNPackMetadataProvider.start(event);
        event.addProvider(new DTAdvancementProvider(packOutput, lookup));

        DTBlockTagsProvider blockTagProvider = new DTBlockTagsProvider(packOutput, lookup);
        event.addProvider(blockTagProvider);
        event.addProvider(new DTItemTagsProvider(packOutput, lookup));
        event.addProvider(new DTRecipeProvider.Runner(packOutput, lookup));
        event.addProvider(new DTLootTableProvider(packOutput, lookup));
        event.addProvider(new DTLootModifierProvider(packOutput, lookup));
        event.addProvider(new DTEntityTagsProvider(packOutput, lookup));

        DTNDatapackProvider.start(event);
        DTNDataRegistryProvider.start(event);
        DTNNeoForgeDataEntry.onGatherData(event);
    }
}
