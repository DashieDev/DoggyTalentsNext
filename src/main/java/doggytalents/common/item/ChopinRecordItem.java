package doggytalents.common.item;

import java.util.function.Supplier;

import doggytalents.DoggyItems;
import doggytalents.api.feature.EnumMode;
import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;

public class ChopinRecordItem extends Item {

    public final int EFFECT_RADIUS = 20;
    private final int length_ticks;

    public ChopinRecordItem(Properties builder, int length_ticks) {
        super(builder);
        this.length_ticks = length_ticks;
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
            AABB.encapsulatingFullBlocks(pos.offset(-EFFECT_RADIUS, -5, -EFFECT_RADIUS), 
                pos.offset(EFFECT_RADIUS, 5, EFFECT_RADIUS)),
                d -> canDoChopinTail(d));
        for (var dog : dogs) {
            dog.setChopinTailFor(length_ticks);
        }
    }

    private boolean canDoChopinTail(Dog dog) {
        if (!dog.isDoingFine())
            return false;
        if (!dog.isMode(EnumMode.DOCILE, EnumMode.WANDERING))
            return false;
        return true;
    }

    public static void onRightClickBlock(RightClickBlock event) {
        var stack = event.getItemStack();
        if (!stack.is(DoggyItems.MUSIC_DISC_CHOPIN_OP64_NO1.get()))
            return;
        
        stack.getItem().useOn(new UseOnContext(event.getEntity(), event.getHand(), event.getHitVec()));
    }
    
}
