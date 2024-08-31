package doggytalents.common.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.DoggyEntitySubPredicates.RawWolfVariantIdSubPredicate;
import doggytalents.common.advancements.triggers.DogBandaidApplyTrigger;
import doggytalents.common.advancements.triggers.DogDrunkTrigger;
import doggytalents.common.advancements.triggers.DogRecoveredTrigger;
import doggytalents.common.advancements.triggers.OokamikazeTrigger;
import doggytalents.common.item.DoggyCharmItem;
import doggytalents.common.util.DogBedUtil;
import doggytalents.common.util.Util;
import doggytalents.common.variants_legacy.DTNWolfVariants;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerInteractTrigger;
import net.minecraft.advancements.critereon.TameAnimalTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.WolfVariants;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.AdvancementProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class DTAdvancementProvider extends AdvancementProvider {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    public DTAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper,
            List.of(new DoggyAdvancementsSubProvider())
        );
    }   


    // private static Path getPath(Path pathIn, Advancement advancementIn) {
    //     return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    // }

    public static class DoggyAdvancementsSubProvider implements AdvancementProvider.AdvancementGenerator {

        @Override
        public void generate(Provider registries, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
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
                            .frame(AdvancementType.TASK)
                            .translate("train_dog_hajimemashite")
                            .build()
                    )
                    .addCriterion(
                        "train_dog", 
                        PlayerInteractTrigger.TriggerInstance
                            .itemUsedOnEntity(
                                ItemPredicate.Builder.item()
                                    .of(DoggyItems.TRAINING_TREAT.get()),
                                Optional.of(EntityPredicate.wrap(
                                    EntityPredicate.Builder.entity()
                                        .of(EntityType.WOLF)
                                        .build())
                                )                              
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

    private static ItemStack createFullRecoveryBed(boolean special) {
        var casing = special ? 
            Blocks.STRIPPED_CHERRY_LOG
            : Blocks.STRIPPED_OAK_LOG;
        var bedding = special ?
            Blocks.RED_WOOL
            : Blocks.WHITE_WOOL;
        return DogBedUtil.createItemStackForced(casing, bedding);
    }

}
