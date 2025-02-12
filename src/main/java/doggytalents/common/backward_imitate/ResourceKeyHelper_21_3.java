package doggytalents.common.backward_imitate;

import doggytalents.common.util.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ResourceKeyHelper_21_3 {
    
    public static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, Util.getResource(name));
    }

    public static ResourceKey<Block> blockKey(String name) {
        return ResourceKey.create(Registries.BLOCK, Util.getResource(name));
    }

}
