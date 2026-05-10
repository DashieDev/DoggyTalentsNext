package doggytalents.common.data;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.DoggyTags;
import doggytalents.common.lib.Constants;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class DTLootModifierProvider extends GlobalLootModifierProvider {

    public static final float RICE_FROM_GRASS_DROP_CHANCE = 0.125F;
    public static final float SOY_FROM_ZOMBIE_DROP_CHANCE = 0.01F;

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> CODEC = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MOD_ID);
    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> RICE_FROM_GRASS_CODEC = CODEC.register("rice_from_grass", RiceFromGrass::getCodec);
    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> SOY_FROM_ZOMBIE_CODEC = CODEC.register("soy_from_zombie", SoyFromZombies::getCodec);

    public DTLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> prov) {
        super(output, prov, Constants.MOD_ID);
    }

    @Override
    protected void start() {
        this.add("rice_from_grass_modifier", createGrassRiceModifer());
        this.add("soy_from_zombie_modifier", createSoyFromZombiesModifier());
    }

    private RiceFromGrass createGrassRiceModifer() {
        var correct_id_codition = 
            LootTableIdCondition.builder(Blocks.SHORT_GRASS.getLootTable().orElseThrow().identifier())
            .build();
        var not_shear_condtion = 
            MatchTool.toolMatches(ItemPredicate.Builder.item().of(this.registries.lookupOrThrow(net.minecraft.core.registries.Registries.ITEM), Items.SHEARS))
            .invert()
            .build();
        var random_condition =
            LootItemRandomChanceCondition.randomChance(RICE_FROM_GRASS_DROP_CHANCE)
            .build();
        var conditions = new LootItemCondition[] {
            correct_id_codition,
            not_shear_condtion,
            random_condition
        };
        return new RiceFromGrass(conditions, 0);
    }

    private SoyFromZombies createSoyFromZombiesModifier() {
        var killed_by_dog_condition =
            LootItemEntityPropertyCondition
                .hasProperties(
                    EntityTarget.ATTACKER, 
                    EntityPredicate.Builder.entity().of(
                        this.registries.lookupOrThrow(net.minecraft.core.registries.Registries.ENTITY_TYPE), DoggyEntityTypes.DOG.get())
                )
                .build();
        // Tag-based conditions fail at parse time in 26.1.2 (tags not yet loaded).
        // Use AnyOfCondition with the individual entity types from the tag instead.
        var lookup = this.registries.lookupOrThrow(net.minecraft.core.registries.Registries.ENTITY_TYPE);
        var drop_soy_condition = net.minecraft.world.level.storage.loot.predicates.AnyOfCondition.anyOf(
            LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(lookup, net.minecraft.world.entity.EntityType.ZOMBIE)),
            LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(lookup, net.minecraft.world.entity.EntityType.CREEPER)),
            LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(lookup, net.minecraft.world.entity.EntityType.SKELETON)),
            LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(lookup, net.minecraft.world.entity.EntityType.SPIDER))
        ).build();
        var random_condition = 
            LootItemRandomChanceCondition.randomChance(SOY_FROM_ZOMBIE_DROP_CHANCE)
            .build();
        var conditions = new LootItemCondition[] {
            killed_by_dog_condition,
            drop_soy_condition,
            random_condition
        };
        return new SoyFromZombies(conditions, 0);
    }

    public static class RiceFromGrass extends LootModifier {

        private static MapCodec<LootModifier> CODEC = 
            RecordCodecBuilder.mapCodec(x -> codecStart(x).apply(x, RiceFromGrass::new));

        public static MapCodec<LootModifier> getCodec() { return CODEC; }

        protected RiceFromGrass(LootItemCondition[] conditionsIn, int priority) {
            super(conditionsIn, priority);
        }

        @Override
        public MapCodec<? extends IGlobalLootModifier> codec() {
            return RICE_FROM_GRASS_CODEC.get();
        }

        @Override
        protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot,
                LootContext context) {
            generatedLoot.add(new ItemStack(DoggyItems.RICE_GRAINS.get()));
            return generatedLoot;
        }

    }
    
    public static class SoyFromZombies extends LootModifier {

        private static MapCodec<LootModifier> CODEC = 
            RecordCodecBuilder.mapCodec(x -> codecStart(x).apply(x, SoyFromZombies::new));

        public static MapCodec<LootModifier> getCodec() { return CODEC; }

        protected SoyFromZombies(LootItemCondition[] conditionsIn, int priority) {
            super(conditionsIn, priority);
        }

        @Override
        public MapCodec<? extends IGlobalLootModifier> codec() {
            return SOY_FROM_ZOMBIE_CODEC.get();
        }

        @Override
        protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot,
                LootContext context) {
            int r = 1 + context.getRandom().nextInt(3);
            generatedLoot.add(new ItemStack(DoggyItems.SOY_BEANS.get(), r));
            return generatedLoot;
        }

    }

}
