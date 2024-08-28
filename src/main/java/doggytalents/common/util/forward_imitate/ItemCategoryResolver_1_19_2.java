package doggytalents.common.util.forward_imitate;

import doggytalents.DoggyItemGroups;
import doggytalents.DoggyItems;
import doggytalents.common.item.itemgroup.DTNItemCategory;
import net.minecraft.world.item.Item;

public class ItemCategoryResolver_1_19_2 {
    
    public static void resolve() {
        var all_dtn_items = DoggyItems.ITEMS.getEntries();
        for (var item_sup : all_dtn_items) {
            resolveCategoryFor(item_sup.get());
        }
    }

    public static void resolveCategoryFor(Item item) {
        if (DTNItemCategory.isMain(item)) {
            item.category = DoggyItemGroups.MAIN;
        } else if (DTNItemCategory.isAgri(item)) {
            item.category = DoggyItemGroups.AGRI;
        } else if (DTNItemCategory.isStyle(item)) {
            item.category = DoggyItemGroups.STYLE;
        } else if (DTNItemCategory.isDogBed(item)) {
            item.category = DoggyItemGroups.DOG_BED;
        } else {
            item.category = DoggyItemGroups.MISC;
        }
    }

}
