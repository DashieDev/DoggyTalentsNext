package doggytalents.common.block.crops;

import doggytalents.DoggyItems;
import doggytalents.common.backward_imitate.ResourceKeyHelper_21_3;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class SoyCropBlock extends DogCropBlock {

    public SoyCropBlock() {
        super(Block.Properties.of().setId(ResourceKeyHelper_21_3.blockKey("soy_crop"))
            .mapColor(MapColor.PLANT)
            .noCollission()
            .randomTicks()
            .instabreak()
            .sound(SoundType.CROP)
            .pushReaction(PushReaction.DESTROY));
    }
    
    @Override
    protected ItemLike getBaseSeedId() {
        return DoggyItems.SOY_BEANS.get();
    }

}
