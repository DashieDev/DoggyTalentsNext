package doggytalents.common.block.crops;

import doggytalents.DoggyItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;

public class RiceCropBlock extends DogCropBlock {

    public RiceCropBlock() {
        super(
            BlockBehaviour.Properties.of(Material.PLANT)
                .noCollission().randomTicks().instabreak().sound(SoundType.CROP));
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return DoggyItems.RICE_GRAINS.get();
    }

}
