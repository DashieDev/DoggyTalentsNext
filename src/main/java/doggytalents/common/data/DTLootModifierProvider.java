package doggytalents.common.data;

import java.util.List;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.DoggyTags;
import doggytalents.common.lib.Constants;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DTLootModifierProvider extends GlobalLootModifierProvider {

    public static final DeferredRegister<GlobalLootModifierSerializer<?>> CODEC = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, Constants.MOD_ID);
    public static final RegistryObject<GlobalLootModifierSerializer<RiceFromGrass>> RICE_FROM_GRASS_CODEC = CODEC.register("rice_from_grass", RiceFromGrass::getCodec);
    public static final RegistryObject<GlobalLootModifierSerializer<SoyFromZombies>> SOY_FROM_ZOMBIE_CODEC = CODEC.register("soy_from_zombie", SoyFromZombies::getCodec);

    public DTLootModifierProvider(DataGenerator output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void start() {
        this.add("rice_from_grass_modifier", RICE_FROM_GRASS_CODEC.get(), createGrassRiceModifer());
        this.add("soy_from_zombie_modifier", SOY_FROM_ZOMBIE_CODEC.get(), createSoyFromZombiesModifier());
    }

    private RiceFromGrass createGrassRiceModifer() {
        var correct_id_codition = 
            LootTableIdCondition.builder(Blocks.GRASS.getLootTable())
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
                    EntityTarget.KILLER, 
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
            LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.125F, 0.05F)
            .build();
        var conditions = new LootItemCondition[] {
            killed_by_dog_condition,
            drop_soy_condition,
            random_condition
        };
        return new SoyFromZombies(conditions);
    }

    public static class RiceFromGrass extends LootModifier {

        private static GlobalLootModifierSerializer<RiceFromGrass> CODEC = 
            new GlobalLootModifierSerializer<DTLootModifierProvider.RiceFromGrass>() {

                @Override
                public RiceFromGrass read(ResourceLocation location, JsonObject object,
                        LootItemCondition[] ailootcondition) {
                    return new RiceFromGrass(ailootcondition);
                }

                @Override
                public JsonObject write(RiceFromGrass instance) {
                    return makeConditions(instance.conditions);
                }
                
            };

        public static GlobalLootModifierSerializer<RiceFromGrass> getCodec() { return CODEC; }

        protected RiceFromGrass(LootItemCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Override
        @Nonnull
        protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            generatedLoot.add(new ItemStack(DoggyItems.RICE_GRAINS.get()));
            return generatedLoot;
        }

    }
    
    public static class SoyFromZombies extends LootModifier {

        private static GlobalLootModifierSerializer<SoyFromZombies> CODEC = 
            new GlobalLootModifierSerializer<DTLootModifierProvider.SoyFromZombies>() {

                @Override
                public SoyFromZombies read(ResourceLocation location, JsonObject object,
                        LootItemCondition[] ailootcondition) {
                    return new SoyFromZombies(ailootcondition);
                }

                @Override
                public JsonObject write(SoyFromZombies instance) {
                    return makeConditions(instance.conditions);
                }
                
            };

        public static GlobalLootModifierSerializer<SoyFromZombies> getCodec() { return CODEC; }

        protected SoyFromZombies(LootItemCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Override
        @Nonnull
        protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            int r = 1 + context.getRandom().nextInt(3);
            generatedLoot.add(new ItemStack(DoggyItems.SOY_BEANS.get(), r));
            return generatedLoot;
        }

    }

}
