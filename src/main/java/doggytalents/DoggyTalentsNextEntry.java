package doggytalents;

import doggytalents.api.feature.FoodHandler;
import doggytalents.common.block.tileentity.RiceMillBlockEntity;
import doggytalents.common.command.DoggyCommands;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.BoostingFoodHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogDrinkMilkHandler;
import doggytalents.common.entity.MeatFoodHandler;
import doggytalents.common.entity.WhitelistFoodHandler;
import doggytalents.common.fabric_helper.FabricEventCallbackHandler;
import doggytalents.common.fabric_helper.entity.network.SyncTypes;
import doggytalents.common.fabric_helper.lootmodifer_imitate.DTLootModifiers;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.talent.HappyEaterTalent;
import doggytalents.forge_imitate.atrrib.ForgeMod;
import doggytalents.forge_imitate.event.EventHandlerRegisterer;
import doggytalents.forge_imitate.event.ForgeCommonSetup;
import doggytalents.common.network.DTNNetworkHandler
;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class DoggyTalentsNextEntry implements ModInitializer {

    @Override
    public void onInitialize() {
        DoggyTalentsNext.init();
        initAllModRegistries();
        FabricEventCallbackHandler.init();
        DTLootModifiers.init();
        registerCommands();
        ConfigHandler.init();
        SyncTypes.init();

        //Last
        ForgeCommonSetup.init();
        doModCommonSetup();
    }

    private void initAllModRegistries() {
        DoggyRegistries.newRegistry();
        
        DoggyBlocks.BLOCKS.initAll();
        DoggyTileEntityTypes.TILE_ENTITIES.initAll();
        DoggyItems.ITEMS.initAll();
        DoggyEntityTypes.ENTITIES.initAll();
        DoggyContainerTypes.CONTAINERS.initAll();
        //DoggySerializers.SERIALIZERS.initAll();
        DoggySounds.SOUNDS.initAll();
        DoggyRecipeSerializers.RECIPE_SERIALIZERS.initAll();
        DoggyTalents.TALENTS.initAll();
        DoggyAccessories.ACCESSORIES.initAll();
        DoggyAccessoryTypes.ACCESSORY_TYPES.initAll();
        DoggyAttributes.ATTRIBUTES.initAll();
        DoggyItemGroups.ITEM_GROUP.initAll();
        DoggyEffects.EFFECTS.initAll();
        DoggyAdvancementTriggers.TRIGGERS.initAll();
    }

    private void doModCommonSetup() {
        PacketHandler.init();
        //TODO CriteriaTriggers.register(criterion)
        FoodHandler.registerHandler(new MeatFoodHandler());
        FoodHandler.registerHandler(new BoostingFoodHandler());
        FoodHandler.registerHandler(new DogDrinkMilkHandler());
        FoodHandler.registerHandler(new WhitelistFoodHandler());

        FoodHandler.registerDynPredicate(HappyEaterTalent.INNER_DYN_PRED);
        //InteractHandler.registerHandler(new HelmetInteractHandler());
        //Dog.initDataParameters();
        //DoggyAdvancementTriggers.registerAll();
        DoggyBrewingRecipes.registerAll();
        ConfigHandler.initTalentConfig();
        //GarbageChunkCollector.init();
        RiceMillBlockEntity.initGrindMap();
    }

    private void registerCommands() {
        CommandRegistrationCallback.EVENT.register((d, r, c) -> {
            DoggyCommands.register(d);
        });
    }
    
}
