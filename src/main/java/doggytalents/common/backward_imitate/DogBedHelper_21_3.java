package doggytalents.common.backward_imitate;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

public class DogBedHelper_21_3 {
    
    public static List<Block> excludeWoodBlocksFromList(List<Block> blocks) {
        Predicate<Block> block_filter = block -> {
            var id = BuiltInRegistries.BLOCK.getKey(block);
            if (id == null)
                return false;
            if (id.getPath().contains("wood") || id.getPath().contains("hyphae"))
                return false;
            return true;
        };
        return blocks.stream().filter(block_filter).collect(Collectors.toList());
    }

}
