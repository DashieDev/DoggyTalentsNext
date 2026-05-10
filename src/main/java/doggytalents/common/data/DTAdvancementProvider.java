package doggytalents.common.data;

import doggytalents.DoggyItems;
import doggytalents.common.advancements.triggers.DogBandaidApplyTrigger;
import doggytalents.common.advancements.triggers.DogDrunkTrigger;
import doggytalents.common.advancements.triggers.DogRecoveredTrigger;
import doggytalents.common.advancements.triggers.OokamikazeTrigger;
import doggytalents.common.util.DogBedUtil;
import doggytalents.common.util.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.ItemUsedOnLocationTrigger;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.PlayerInteractTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class DTAdvancementProvider extends AdvancementProvider {

    public DTAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new DoggyAdvancementsSubProvider()));
    }

    public static class DoggyAdvancementsSubProvider implements AdvancementSubProvider {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer) {
            var charm_advancement =
                Advancement.Builder.advancement()
                    .display(
                        DisplayInfoBuilder.create()
                            .icon(DoggyItems.DOGGY_CHARM)
                            .frame(AdvancementType.TASK)
                            .translate("doggy_charm_summon")
                            .background("adventure.png")
                            .build()
                    )
                    .addCriterion(
                        "summon_dog",
                        ItemUsedOnLocationTrigger.TriggerInstance
                            .itemUsedOnBlock(
                                LocationPredicate.Builder.location(),
                                ItemPredicate.Builder.item()
                                    .of(registries.lookupOrThrow(net.minecraft.core.registries.Registries.ITEM), DoggyItems.DOGGY_CHARM.get())
                            )
                    )
                    .save(consumer, Util.getResourcePath("dtn_core/summon_dog"));

            var train_dog_advancement =
                Advancement.Builder.advancement()
                    .parent(charm_advancement)
                    .display(
                        DisplayInfoBuilder.create()
                            .icon(DoggyItems.TRAINING_TREAT)
                            .frame(AdvancementType.TASK)
                            .translate("train_dog_hajimemashite")
                            .build()
                    )
                    .addCriterion(
                        "train_dog",
                        PlayerInteractTrigger.TriggerInstance
                            .itemUsedOnEntity(
                                ItemPredicate.Builder.item()
                                    .of(registries.lookupOrThrow(net.minecraft.core.registries.Registries.ITEM), DoggyItems.TRAINING_TREAT.get()),
                                Optional.of(EntityPredicate.wrap(
                                    EntityPredicate.Builder.entity()
                                        .of(registries.lookupOrThrow(net.minecraft.core.registries.Registries.ENTITY_TYPE), EntityType.WOLF)
                                        .build()))
                            )
                    )
                    .save(consumer, Util.getResourcePath("dtn_core/train_dog"));

            var sake_advancement =
                Advancement.Builder.advancement()
                .parent(train_dog_advancement)
                .display(
                    DisplayInfoBuilder.create()
                        .icon(DoggyItems.SAKE)
                        .frame(AdvancementType.TASK)
                        .translate("get_dog_drunk")
                        .build()
                )
                .addCriterion(
                    "get_dog_drunk",
                    DogDrunkTrigger.getCriterion()
                )
                .save(consumer, Util.getResourcePath("default/get_dog_drunk"));

            var ookamikaze_advancement =
                Advancement.Builder.advancement()
                .parent(train_dog_advancement)
                .display(
                    DisplayInfoBuilder.create()
                        .icon(() -> Items.GUNPOWDER)
                        .frame(AdvancementType.TASK)
                        .translate("ookamikaze_trigger")
                        .build()
                )
                .addCriterion(
                    "ookamikaze_trigger",
                    OokamikazeTrigger.getCriterion()
                )
                .save(consumer, Util.getResourcePath("default/ookamikaze_trigger"));

            var bandaid_advancement =
                Advancement.Builder.advancement()
                .parent(train_dog_advancement)
                .display(
                    DisplayInfoBuilder.create()
                        .icon(() -> DoggyItems.BANDAID.get())
                        .frame(AdvancementType.TASK)
                        .translate("sterile")
                        .build()
                )
                .addCriterion(
                    "give_dog_bandaid",
                    DogBandaidApplyTrigger.getCriterion()
                )
                .save(consumer, Util.getResourcePath("default/sterile"));

            var recovered_advancement =
                Advancement.Builder.advancement()
                .parent(bandaid_advancement)
                .display(
                    DisplayInfoBuilder.create()
                        .icon(createFullRecoveryBed(false))
                        .frame(AdvancementType.TASK)
                        .translate("a_full_recovery")
                        .build()
                )
                .addCriterion(
                    "dog_recovered",
                    DogRecoveredTrigger.getCriterion(false)
                )
                .save(consumer, Util.getResourcePath("default/dog_recovered"));

            var best_dogtor_advancement =
                Advancement.Builder.advancement()
                .parent(recovered_advancement)
                .display(
                    DisplayInfoBuilder.create()
                        .icon(createFullRecoveryBed(true))
                        .frame(AdvancementType.TASK)
                        .translate("the_best_dogtor")
                        .build()
                )
                .addCriterion(
                    "dog_recovered_special",
                    DogRecoveredTrigger.getCriterion(true)
                )
                .save(consumer, Util.getResourcePath("default/dog_recovered_special"));
        }
    }

    private static ItemStack createFullRecoveryBed(boolean special) {
        var casing = special ? Blocks.STRIPPED_CHERRY_LOG : Blocks.STRIPPED_OAK_LOG;
        var bedding = special ? Blocks.RED_WOOL : Blocks.WHITE_WOOL;
        return DogBedUtil.createItemStackForced(casing, bedding);
    }
}
