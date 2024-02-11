package doggytalents.common.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import doggytalents.DoggyBlocks;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdventureAdvancements;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DTLootTableProvider extends LootTableProvider {

    public DTLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    public String getName() {
        return "DoggyTalents LootTables";
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(
                Pair.of(Blocks::new, LootContextParamSets.BLOCK),
                Pair.of(Entities::new, LootContextParamSets.ENTITY)
               );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker) {}

    private static class Blocks extends BlockLoot {

        private void dropDogBed(Supplier<? extends Block> block) {
            LootTable.Builder lootTableBuilder = LootTable.lootTable().withPool(applyExplosionCondition(block.get(),
                       LootPool.lootPool()
                         .setRolls(ConstantValue.exactly(1)))
                         .add(LootItem.lootTableItem(block.get())
                                 .apply(
                                         CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
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

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return DoggyBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        }

        @Override
        protected void addTables() {
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
            final var KOJI_LOOT_CONDITION_ADDITIONAL = 
                LootItemRandomChanceCondition.randomChance(0.05f);
                    //.and(RICE_LOOT_CONDITION);

            final var KOJI_LOOT_POOL = 
                LootPool.lootPool().when(RICE_LOOT_CONDITION).when(KOJI_LOOT_CONDITION_ADDITIONAL)
                    .add(LootItem.lootTableItem(DoggyItems.KOJI.get()))
                    .apply(
                        ApplyBonusCount
                            .addBonusBinomialDistributionCount(
                                Enchantments.BLOCK_FORTUNE, 0.5714286F, 3));

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
    }

    private static class Entities extends EntityLoot {

        @Override
        protected void addTables() {
            this.registerNoLoot(DoggyEntityTypes.DOG);
        }

        protected void registerNoLoot(Supplier<? extends EntityType<?>> type) {
           this.add(type.get(), LootTable.lootTable());
        }

        @Override
        protected Iterable<EntityType<?>> getKnownEntities() {
            return DoggyEntityTypes.ENTITIES.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        }
    }
}
