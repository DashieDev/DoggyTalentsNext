package doggytalents.common.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.common.advancements.triggers.DogDrunkTrigger;
import doggytalents.common.advancements.triggers.OokamikazeTrigger;
import doggytalents.common.util.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemInteractWithBlockTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerInteractTrigger;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public class DTAdvancementProvider extends AdvancementProvider {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;

    public DTAdvancementProvider(DataGenerator generatorIn, net.minecraftforge.common.data.ExistingFileHelper fileHelper) {
        super(generatorIn, fileHelper);
        this.generator = generatorIn;
    }

    @Override
    public String getName() {
        return "DoggyTalents Advancements";
    }

    @Override
    public void registerAdvancements(Consumer<Advancement> consumer, net.minecraftforge.common.data.ExistingFileHelper fileHelper) {
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
                    ItemInteractWithBlockTrigger.TriggerInstance
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
                            EntityPredicate.Composite.wrap(
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
        // Old Advancement.

        // Advancement advancement = Advancement.Builder.advancement()
        //     .display(DisplayInfoBuilder.create().icon(DoggyItems.TRAINING_TREAT).frame(FrameType.TASK).translate("dog.root").background("stone.png").noToast().noAnnouncement().build())
        //     .addCriterion("tame_dog", TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(DoggyEntityTypes.DOG.get()).build()))
        //     //.withCriterion("get_dog", ItemUseTrigger.TameAnimalTrigger.Instance.create(EntityPredicate.Builder.create().type(DoggyEntityTypes.DOG.get()).build()))
        //     .requirements(RequirementsStrategy.OR)
        //     .save(consumer, Util.getResourcePath("default/tame_dog"));

        

        // Advancement advancement1 = Advancement.Builder.advancement()
        //         .parent(advancement)
        //         .display(DisplayInfoBuilder.create().icon(Items.WOODEN_PICKAXE).frame(FrameType.TASK).translate("dog.level_talent").build())
        //         .addCriterion("level_talent", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.COBBLESTONE))
        //         .save(consumer, Util.getResourcePath("default/level_talent"));
        // Advancement advancement2 = Advancement.Builder.advancement()
        //         .parent(advancement1)
        //         .display(DisplayInfoBuilder.create().icon(DoggyItems.TANTAN_CAPE).frame(FrameType.TASK).translate("dog.accessorise").build())
        //         .addCriterion("accessorise", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STONE_PICKAXE))
        //         .save(consumer, Util.getResourcePath("default/accessorise"));

        // Advancement advancement3 = Advancement.Builder.advancement()
        //         .parent(advancement2)
        //         .display(DisplayInfoBuilder.create().icon(DoggyItems.RADIO_COLLAR).frame(FrameType.TASK).translate("dog.radio_collar").build())
        //         .addCriterion("iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
        //         .save(consumer, Util.getResourcePath("default/radio_collar"));
    }
}
