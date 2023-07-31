package doggytalents.common.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.common.util.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.TameAnimalTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class DTAdvancementProvider extends ForgeAdvancementProvider {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    public DTAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper,
            List.of(new DoggyAdvancementsSubProvider())
        );
    }   


    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    public static class DoggyAdvancementsSubProvider implements ForgeAdvancementProvider.AdvancementGenerator {

        @Override
        public void generate(Provider registries, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
            // TODO Advancement.

            Advancement advancement = Advancement.Builder.advancement()
                .display(DisplayInfoBuilder.create().icon(DoggyItems.TRAINING_TREAT).frame(FrameType.TASK).translate("dog.root").background("stone.png").noToast().noAnnouncement().build())
                .addCriterion("tame_dog", TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(DoggyEntityTypes.DOG.get()).build()))
                //.withCriterion("get_dog", ItemUseTrigger.TameAnimalTrigger.Instance.create(EntityPredicate.Builder.create().type(DoggyEntityTypes.DOG.get()).build()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, Util.getResourcePath("default/tame_dog"));

            Advancement advancement1 = Advancement.Builder.advancement()
                    .parent(advancement)
                    .display(DisplayInfoBuilder.create().icon(Items.WOODEN_PICKAXE).frame(FrameType.TASK).translate("dog.level_talent").build())
                    .addCriterion("level_talent", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.COBBLESTONE))
                    .save(consumer, Util.getResourcePath("default/level_talent"));
            Advancement advancement2 = Advancement.Builder.advancement()
                    .parent(advancement1)
                    .display(DisplayInfoBuilder.create().icon(DoggyItems.TANTAN_CAPE).frame(FrameType.TASK).translate("dog.accessorise").build())
                    .addCriterion("accessorise", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STONE_PICKAXE))
                    .save(consumer, Util.getResourcePath("default/accessorise"));

            Advancement advancement3 = Advancement.Builder.advancement()
                    .parent(advancement2)
                    .display(DisplayInfoBuilder.create().icon(DoggyItems.RADIO_COLLAR).frame(FrameType.TASK).translate("dog.radio_collar").build())
                    .addCriterion("iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                    .save(consumer, Util.getResourcePath("default/radio_collar"));
            
        }
        
    }
}
