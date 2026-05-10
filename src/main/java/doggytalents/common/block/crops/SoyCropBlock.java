package doggytalents.common.block.crops;

import doggytalents.DoggyItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class SoyCropBlock extends DogCropBlock {

    public SoyCropBlock(net.minecraft.world.level.block.state.BlockBehaviour.Properties props) {
        super(props.mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY));
    }
    
    @Override
    protected ItemLike getBaseSeedId() {
        return DoggyItems.SOY_BEANS.get();
    }

}
