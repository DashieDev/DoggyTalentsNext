package doggytalents;

import doggytalents.api.feature.FoodHandler;
import doggytalents.api.feature.InteractHandler;
import doggytalents.client.ClientSetup;
import doggytalents.client.DoggyKeybinds;
import doggytalents.client.PettingArmPose;
import doggytalents.client.DTNClientPettingManager;
import doggytalents.client.data.DTBlockstateProvider;
import doggytalents.client.data.DTItemModelProvider;
import doggytalents.client.entity.render.DoggyArmorMapping;
import doggytalents.client.entity.render.world.BedFinderRenderer;
import doggytalents.client.entity.render.world.CanineTrackerLocateRenderer;
import doggytalents.client.event.ClientEventHandler;
import doggytalents.common.block.tileentity.RiceMillBlockEntity;
import doggytalents.common.chunk.GarbageChunkCollector;
//import doggytalents.common.addon.AddonManager;
import doggytalents.common.command.DoggyCommands;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.data.*;
import doggytalents.common.entity.BoostingFoodHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.MeatFoodHandler;
import doggytalents.common.entity.WhitelistFoodHandler;
import doggytalents.common.entity.DogDrinkMilkHandler;
import doggytalents.common.event.EventHandler;
import doggytalents.common.item.ChopinRecordItem;
import doggytalents.common.item.itemgroup.DTNItemCategory;
import doggytalents.common.lib.Constants;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.talent.HappyEaterTalent;
import doggytalents.common.variants_legacy.DTNWolfVariantsProvider;
import doggytalents.common.variants_legacy.DTNWolfVariantsSpawnOverride;
import doggytalents.common.variants_legacy.DTNWolfVariantsSpawnPlacements;
import doggytalents.common.variants_legacy.VSCodeWolfSpawnHandler;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.data.event.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Constants.MOD_ID)
public class DoggyTalentsNext {

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID);

    public static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder.named(Constants.CHANNEL_NAME)
            .clientAcceptedVersions(Constants.PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(Constants.PROTOCOL_VERSION::equals)
            .networkProtocolVersion(Constants.PROTOCOL_VERSION::toString)
            .simpleChannel();
            
    
    //TODO AUTOMATION CURSEFORGE !!!
    public DoggyTalentsNext() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Mod lifecycle
        modEventBus.addListener(this::gatherData);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::interModProcess);

        // Registries
        DogVariants.DOG_VARIANT.register(modEventBus);
        DogVariants.DOG_VARIANT_VANILLA.register(modEventBus);
        DoggyBlocks.BLOCKS.register(modEventBus);
        DoggyTileEntityTypes.TILE_ENTITIES.register(modEventBus);
        DoggyItems.ITEMS.register(modEventBus);
        DoggyEntityTypes.ENTITIES.register(modEventBus);
        DoggyContainerTypes.CONTAINERS.register(modEventBus);
        DoggySerializers.SERIALIZERS.register(modEventBus);
        DoggySounds.SOUNDS.register(modEventBus);
        DoggyRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        DoggyTalents.TALENTS.register(modEventBus);
        DoggyAccessories.ACCESSORIES.register(modEventBus);
        DoggyAccessoryTypes.ACCESSORY_TYPES.register(modEventBus);
        DoggyAttributes.ATTRIBUTES.register(modEventBus);
        //DoggyItemGroups.ITEM_GROUP.register(modEventBus);
        DoggyEffects.EFFECTS.register(modEventBus);
        //DoggyEntitySubPredicates.ENTITY_SUB_PREDICATES.register(modEventBus);

        DTLootModifierProvider.CODEC.register(modEventBus);

        modEventBus.addListener(DoggyRegistries::newRegistry);
        modEventBus.addListener(DoggyEntityTypes::addEntityAttributes);
        // modEventBus.addListener(DTNWolfVariantsSpawnPlacements::onRegisterSpawnPlacements);

        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        forgeEventBus.addListener(this::serverStarting);
        forgeEventBus.addListener(this::registerCommands);
        forgeEventBus.register(new EventHandler());

        // Client Events
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            modEventBus.addListener(DoggyKeybinds::registerDTKeyMapping);
            modEventBus.addListener(this::clientSetup);
            modEventBus.addListener(DoggyBlocks::registerBlockColours);
            modEventBus.addListener(DoggyItems::registerItemColours);
            modEventBus.addListener(ClientEventHandler::onModelBakeEvent);
            modEventBus.addListener(ClientSetup::setupTileEntityRenderers);
            modEventBus.addListener(ClientSetup::setupEntityRenderers);
            modEventBus.addListener(ClientSetup::addClientReloadListeners);
            modEventBus.addListener(ClientSetup::registerOverlay);
            forgeEventBus.register(new ClientEventHandler());
            forgeEventBus.addListener(BedFinderRenderer::onWorldRenderLast);
            forgeEventBus.addListener(CanineTrackerLocateRenderer::onWorldRenderLast);
            forgeEventBus.addListener(CanineTrackerLocateRenderer::tickUpdate);
            forgeEventBus.register(DTNClientPettingManager.get());
        });

        ConfigHandler.init(modEventBus);

        //AddonManager.init();
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
        //TODO CriteriaTriggers.register(criterion)
        FoodHandler.registerHandler(new MeatFoodHandler());
        FoodHandler.registerHandler(new BoostingFoodHandler());
        FoodHandler.registerHandler(new DogDrinkMilkHandler());
        FoodHandler.registerHandler(new WhitelistFoodHandler());

        //InteractHandler.registerHandler(new HelmetInteractHandler());
        event.enqueueWork(() -> {
            Dog.initDataParameters();
            DoggyAdvancementTriggers.registerAll();
            DoggyBrewingRecipes.registerAll();
            ConfigHandler.initTalentConfig();
            GarbageChunkCollector.init();
            RiceMillBlockEntity.initGrindMap();
            DTNItemCategory.init();
        });
    }

    public void serverStarting(final ServerStartingEvent event) {

    }

    public void registerCommands(final RegisterCommandsEvent event) {
        DoggyCommands.register(event.getDispatcher());
    }

    @OnlyIn(Dist.CLIENT)
    public void clientSetup(final FMLClientSetupEvent event) {
        ClientSetup.setupScreenManagers(event);

        ClientSetup.setupCollarRenderers(event);

        PettingArmPose.init();
    }

    protected void interModProcess(final InterModProcessEvent event) {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //AddonManager.init();
    }

    private void gatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        if (event.includeClient()) {
            DTBlockstateProvider blockstates = new DTBlockstateProvider(gen, event.getExistingFileHelper());
            gen.addProvider(true, blockstates);
            gen.addProvider(true, new DTItemModelProvider(gen, blockstates.getExistingHelper()));
        }

        if (event.includeServer()) {
            // gen.addProvider(new DTBlockTagsProvider(gen));
            gen.addProvider(true, new DTAdvancementProvider(gen, event.getExistingFileHelper()));
            
            DTBlockTagsProvider blockTagProvider = new DTBlockTagsProvider(gen, event.getExistingFileHelper());
            gen.addProvider(true, blockTagProvider);
            gen.addProvider(true, new DTItemTagsProvider(gen ,blockTagProvider, event.getExistingFileHelper()));
            gen.addProvider(true, new DTRecipeProvider(gen));
            gen.addProvider(true, new DTLootTableProvider(gen));
            gen.addProvider(true, new DTLootModifierProvider(gen));
            gen.addProvider(true, new DTEntityTagsProvider(gen, event.getExistingFileHelper()));
        }

        //DTNWolfVariantsProvider.start(event);
    }
}
