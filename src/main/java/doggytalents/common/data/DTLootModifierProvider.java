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
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
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
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.NeoForgeRegistries;

public class DTLootModifierProvider extends GlobalLootModifierProvider {

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
            LootTableIdCondition.builder(Blocks.SHORT_GRASS.getLootTable().location())
            .build();
        var not_shear_condtion = 
            MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS))
            .invert()
            .build();
        var random_condition =
            LootItemRandomChanceCondition.randomChance(0.125F)
            .build();
        var conditions = new LootItemCondition[] {
            correct_id_codition,
            not_shear_condtion,
            random_condition
        };
        return new RiceFromGrass(conditions);
    }

    private SoyFromZombies createSoyFromZombiesModifier() {
        var killed_by_dog_condition =
            LootItemEntityPropertyCondition
                .hasProperties(
                    EntityTarget.ATTACKER, 
                    EntityPredicate.Builder.entity().of(
                        DoggyEntityTypes.DOG.get())
                )
                .build();
        var drop_soy_condition =
            LootItemEntityPropertyCondition
                .hasProperties(
                    EntityTarget.THIS, 
                    EntityPredicate.Builder.entity().of(DoggyTags.DROP_SOY_WHEN_DOG_KILL)
                )
                .build();
        var random_condition = 
            LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.01F, 0.05F)
            .build();
        var conditions = new LootItemCondition[] {
            killed_by_dog_condition,
            drop_soy_condition,
            random_condition
        };
        return new SoyFromZombies(conditions);
    }

    public static class RiceFromGrass extends LootModifier {

        private static MapCodec<LootModifier> CODEC = 
            RecordCodecBuilder.mapCodec(x -> codecStart(x).apply(x, RiceFromGrass::new));

        public static MapCodec<LootModifier> getCodec() { return CODEC; }

        protected RiceFromGrass(LootItemCondition[] conditionsIn) {
            super(conditionsIn);
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

        protected SoyFromZombies(LootItemCondition[] conditionsIn) {
            super(conditionsIn);
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
