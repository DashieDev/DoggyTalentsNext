package doggytalents.common.item.itemgroup;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import doggytalents.DoggyItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.ComposterBlock;

public class DTNCompostables {
    
    private static Map<Item, Float> compostables = null;

    public static Map<Item, Float> getCompostables() {
        if (compostables == null) {
            compostables = ImmutableMap.<Item, Float>builder()
                .put(DoggyItems.RICE_GRAINS.get(), 0.3f)
                .put(DoggyItems.UNCOOKED_RICE.get(), 0.3f)
                .put(DoggyItems.SOY_BEANS.get(), 0.3f)
                .put(DoggyItems.SOY_BEANS_DRIED.get(), 0.3f)
                .put(DoggyItems.RICE_WHEAT.get(), 0.65f)
                .put(DoggyItems.SOY_PODS.get(), 0.65f)
                .put(DoggyItems.SOY_PODS_DRIED.get(), 0.65f)
                .put(DoggyItems.MISO_PASTE.get(), 0.65f)
                .put(DoggyItems.KOJI.get(), 0.65f)
                .build();
        }
        return compostables;
    }

    public static void init() {
        var compostables = getCompostables();
        //ComposterBlock.COMPOSTABLES.putAll(compostables); // Neoforge already uses data gen for thisn
    }

}
