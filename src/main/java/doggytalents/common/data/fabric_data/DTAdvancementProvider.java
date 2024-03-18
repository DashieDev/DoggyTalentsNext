package doggytalents.common.data.fabric_data;

import java.util.function.Consumer;

import doggytalents.DoggyItems;
import doggytalents.common.advancements.triggers.DogDrunkTrigger;
import doggytalents.common.advancements.triggers.OokamikazeTrigger;
import doggytalents.common.data.DisplayInfoBuilder;
import doggytalents.common.util.Util;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerInteractTrigger;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;

public class DTAdvancementProvider extends FabricAdvancementProvider {

    public DTAdvancementProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        var charm_advancement =
            Advancement.Builder.advancement()
                .display(
                    DisplayInfoBuilder.create()
                        .icon(DoggyItems.DOGGY_CHARM)
                        .frame(FrameType.TASK)
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
                                .of(DoggyItems.DOGGY_CHARM.get())
                        )
                )
                .save(consumer, Util.getResourcePath("dtn_core/summon_dog"));
        
        var train_dog_advancement = 
            Advancement.Builder.advancement()
                .parent(charm_advancement)
                .display(
                    DisplayInfoBuilder.create()
                        .icon(DoggyItems.TRAINING_TREAT)
                        .frame(FrameType.TASK)
                        .translate("train_dog_hajimemashite")
                        .build()
                )
                .addCriterion(
                    "train_dog", 
                    PlayerInteractTrigger.TriggerInstance
                        .itemUsedOnEntity(
                            ItemPredicate.Builder.item()
                                .of(DoggyItems.TRAINING_TREAT.get()),
                            EntityPredicate.wrap(
                                EntityPredicate.Builder.entity()
                                    .of(EntityType.WOLF)
                                    .build()
                            )                              
                        )
                )
                .save(consumer, Util.getResourcePath("dtn_core/train_dog"));
        
        var sake_advancement = 
            Advancement.Builder.advancement()
            .display(
                DisplayInfoBuilder.create()
                    .icon(DoggyItems.SAKE)
                    .frame(FrameType.TASK)
                    .translate("get_dog_drunk")
                    .build()
            )
            .addCriterion(
                "get_dog_drunk", 
                DogDrunkTrigger.getInstance()
            )
            .save(consumer, Util.getResourcePath("default/get_dog_drunk"));

        var ookamikaze_advancement = 
            Advancement.Builder.advancement()
            .display(
                DisplayInfoBuilder.create()
                    .icon(() -> Items.GUNPOWDER)
                    .frame(FrameType.TASK)
                    .translate("ookamikaze_trigger")
                    .build()
            )
            .addCriterion(
                "ookamikaze_trigger", 
                OokamikazeTrigger.getInstance()
            )
            .save(consumer, Util.getResourcePath("default/ookamikaze_trigger"));
    }
    
}
