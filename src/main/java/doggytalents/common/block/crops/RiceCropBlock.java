package doggytalents.common.block.crops;

import doggytalents.DoggyItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class RiceCropBlock extends DogCropBlock {

    public RiceCropBlock() {
        super(Block.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .randomTicks()
            .instabreak()
            .sound(SoundType.CROP)
            .pushReaction(PushReaction.DESTROY));
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return DoggyItems.RICE_GRAINS.get();
    }

}
