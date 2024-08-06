package doggytalents.common.item.itemgroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import doggytalents.DoggyBlocks;
import doggytalents.DoggyItems;
import doggytalents.common.block.DogBedBlock;
import doggytalents.common.block.DogBedMaterialManager;
import doggytalents.common.item.AccessoryItem;
import doggytalents.common.item.EmptyLocatorOrbItem;
import doggytalents.common.item.IDogEddible;
import doggytalents.common.util.DogBedUtil;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DTNItemCategory {
    
    private static Set<Item> main;
    private static Set<Item> agri_base;

    public static void init() {
        main = new HashSet<Item>(List.of(
            DoggyItems.DOGGY_CHARM.get(),
            DoggyItems.TRAINING_TREAT.get(),
            DoggyItems.SUPER_TREAT.get(),
            DoggyItems.MASTER_TREAT.get(),
            DoggyItems.KAMI_TREAT.get(),
            DoggyItems.CONDUCTING_BONE.get(),
            DoggyItems.CANINE_TRACKER.get(),
            DoggyItems.CREATIVE_CANINE_TRACKER.get(),
            DoggyItems.WHISTLE.get(),
            DoggyItems.SCENT_TREAT.get(),
            DoggyItems.AMNESIA_BONE.get(),
            DoggyItems.THROW_BONE.get(),
            DoggyItems.THROW_STICK.get(),
            DoggyItems.BREEDING_BONE.get(),
            DoggyItems.TREAT_BAG.get(),
            DoggyItems.GENDER_BONE.get(),
            DoggyItems.STARTER_BUNDLE.get(),
            DoggyItems.MAGNIFYING_BONE.get(),
            DoggyItems.SHRINKING_MALLET.get(),
            DoggyItems.BANDAID.get(),
            DoggyItems.FRISBEE.get(),
            DoggyBlocks.FOOD_BOWL.get().asItem(),
            DoggyBlocks.DOG_BATH.get().asItem()
        ));
        agri_base = new HashSet<Item>(List.of(
            DoggyItems.RICE_GRAINS.get(),
            DoggyItems.RICE_WHEAT.get(),
            DoggyBlocks.RICE_MILL.get().asItem(),
            DoggyItems.UNCOOKED_RICE_BOWL.get(),
            DoggyItems.UNCOOKED_RICE.get(),
            DoggyItems.KOJI.get(),
            DoggyItems.UNCOOKED_RICE.get(),
            DoggyItems.SOY_BEANS_DRIED.get(),
            DoggyItems.SOY_PODS_DRIED.get(),
            DoggyItems.SOY_PODS.get(),
            DoggyItems.SOY_BEANS.get(),
            DoggyItems.MISO_PASTE.get(),
            DoggyItems.EDAMAME.get()
        ));
    }

    public static boolean isMain(Item item) {
        if (main == null || item == null)
            return false;
        return main.contains(item);
    }

    public static boolean isStyle(Item item) {
        if (item == null)
            return false;
        if (item instanceof AccessoryItem)
            return true;
        if (item instanceof EmptyLocatorOrbItem)
            return true;
        return false;
    }

    public static boolean isAgri(Item item) {
        if (item == null)
            return false;
        if (item instanceof IDogEddible)
            return true;
        return agri_base != null && agri_base.contains(item);
    }

    public static boolean isDogBed(Item item) {
        if (!(item instanceof BlockItem blockItem))
            return false;
        return (blockItem.getBlock() instanceof DogBedBlock);
    }

    public static boolean isMisc(Item item) {
        if (isMain(item))
            return false;
        if (isAgri(item))
            return false;
        if (isStyle(item))
            return false;
        if (isDogBed(item))
            return false;
        return true;
    }

    public static Stream<Item> getAllItemOfCategory(Predicate<Item> pred) {
        var all_items = DoggyItems.ITEMS.getEntries();
        var item_list = new ArrayList<Item>();
        for (var item_holder : all_items) {
            var item = item_holder.get();
            if (item == null)
                continue;
            if (item_list.contains(item))
                continue; 
            if (pred.test(item))
            item_list.add(item);
        }
        return item_list.stream();
    }

    public static Stream<ItemStack> getRandomBedsForTab() {
        final int maxBeddingEntries = 13;
        final int maxCasingEntries = 13;
        var beddingList = DogBedMaterialManager.getBeddings().entrySet().stream()
            .map(x -> x.getValue())
            .filter(x -> !(x instanceof DogBedMaterialManager.NaniBedding))
            .collect(Collectors.toList());
        var casingList = DogBedMaterialManager.getCasings().entrySet().stream()
            .map(x -> x.getValue())
            .filter(x -> !(x instanceof DogBedMaterialManager.NaniCasing))
            .collect(Collectors.toList());
        
        Collections.shuffle(beddingList);
        Collections.shuffle(casingList);
        var bed_list = new ArrayList<ItemStack>();
        for (int i = 0; i < Math.min(maxCasingEntries, casingList.size()); ++i) {
            for (int j = 0; j < Math.min(maxBeddingEntries, beddingList.size()); ++j) {
                var beddingId = beddingList.get(j);
                var casingId = casingList.get(i);
                var created = DogBedUtil.createItemStack(casingId, beddingId);
                bed_list.add(created);
            }
        }
        return bed_list.stream();
    }

}
