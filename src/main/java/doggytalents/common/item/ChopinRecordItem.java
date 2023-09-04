package doggytalents.common.item;

import java.util.function.Supplier;

import doggytalents.api.feature.EnumMode;
import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.phys.AABB;

public class ChopinRecordItem extends RecordItem {

    public final int EFFECT_RADIUS = 12;

    public ChopinRecordItem(int comparatorValue, Supplier<SoundEvent> soundSupplier, Properties builder,
            int lengthInTicks) {
        super(comparatorValue, soundSupplier, builder, lengthInTicks);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var blockpos = context.getClickedPos();
        var blockstate = level.getBlockState(blockpos);
        if (!level.isClientSide)
        if (blockstate.is(Blocks.JUKEBOX) && !blockstate.getValue(JukeboxBlock.HAS_RECORD)) {
            triggerChopinTailNearbyDogs(level, blockpos);
        }
        
        return super.useOn(context);
    }

    private void triggerChopinTailNearbyDogs(Level level, BlockPos pos) {
        var dogs = level.getEntitiesOfClass(Dog.class, 
            new AABB(pos.offset(-EFFECT_RADIUS, -5, -EFFECT_RADIUS), 
                pos.offset(EFFECT_RADIUS, 5, EFFECT_RADIUS)),
                d -> canDoChopinTail(d));
        for (var dog : dogs) {
            dog.setChopinTailFor(this.getLengthInTicks());
        }
    }

    private boolean canDoChopinTail(Dog dog) {
        if (!dog.isDoingFine())
            return false;
        if (dog.getMode() != EnumMode.DOCILE)
            return false;
        return true;
    }
    
}
