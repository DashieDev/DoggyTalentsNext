package doggytalents.common.data.fabric_data;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import doggytalents.DoggyBlocks;
import doggytalents.DoggyItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.CopyCustomDataFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class DTLootTableProvider extends FabricBlockLootTableProvider {

    public DTLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropsSelf(DoggyBlocks.DOG_BATH);
        dropDogBed(DoggyBlocks.DOG_BED);
        dropsSelf(DoggyBlocks.FOOD_BOWL); // Drop with the name of the dog bowl
        dropsSelf(DoggyBlocks.RICE_MILL);

        final var RICE_LOOT_CONDITION = 
            LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(DoggyBlocks.RICE_CROP.get())
                .setProperties(
                    StatePropertiesPredicate
                    .Builder.properties()
                    .hasProperty(DoggyBlocks.RICE_CROP.get().getAgeProperty(), 7));
        final var KOJI_LOOT_CONDITION = 
            LootItemRandomChanceCondition.randomChance(0.05f)
                .and(RICE_LOOT_CONDITION);

        final var KOJI_LOOT_POOL = 
            LootPool.lootPool().when(KOJI_LOOT_CONDITION)
                .add(LootItem.lootTableItem(DoggyItems.KOJI.get()))
                .apply(
                    ApplyBonusCount
                        .addBonusBinomialDistributionCount(
                            this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 0.5714286F, 3));

        final var RICE_LOOTABLE = 
            LootTable.lootTable().withPool(
                LootPool.lootPool().add(
                    LootItem.lootTableItem(DoggyItems.RICE_WHEAT.get())
                        .apply(
                            SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                        .when(RICE_LOOT_CONDITION)
                        .otherwise(LootItem.lootTableItem(DoggyItems.RICE_GRAINS.get()))
                )
            )
            .withPool(KOJI_LOOT_POOL)
            .apply(ApplyExplosionDecay.explosionDecay());

        this.add(DoggyBlocks.RICE_CROP.get(), RICE_LOOTABLE);

        final var SOY_LOOT_CONDITION = 
            LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(DoggyBlocks.SOY_CROP.get())
                .setProperties(
                    StatePropertiesPredicate
                    .Builder.properties()
                    .hasProperty(DoggyBlocks.SOY_CROP.get().getAgeProperty(), 7));
        this.add(DoggyBlocks.SOY_CROP.get(), 
            this.createCropDrops(DoggyBlocks.SOY_CROP.get(), 
                DoggyItems.SOY_PODS.get(), 
                DoggyItems.SOY_BEANS.get(), 
                SOY_LOOT_CONDITION));
    }

    private void dropDogBed(Supplier<? extends Block> block) {
        LootTable.Builder lootTableBuilder = LootTable.lootTable().withPool(applyExplosionCondition(block.get(),
                   LootPool.lootPool()
                     .setRolls(ConstantValue.exactly(1)))
                     .add(LootItem.lootTableItem(block.get())
                             .apply(
                                     CopyCustomDataFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                     .copy("casingId", "doggytalents.casingId")
                                     .copy("beddingId", "doggytalents.beddingId")
                                     .copy("ownerId", "doggytalents.ownerId")
                                     .copy("name", "doggytalents.name")
                                     .copy("ownerName", "doggytalents.ownerName")
                             )));

        this.add(block.get(), lootTableBuilder);
    }

    private void dropsSelf(Supplier<? extends Block> block) {
        this.dropSelf(block.get());
    }
    
}
