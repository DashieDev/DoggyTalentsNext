package doggytalents.common.entity.ai.triggerable;

import javax.annotation.Nonnull;

import doggytalents.common.block.tileentity.DogBedTileEntity;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.WorldUtil;
import net.minecraft.core.BlockPos;

public class DogMoveToBedAction extends TriggerableAction {

    private final @Nonnull BlockPos targetBedPos;
    private final boolean claimBed;

    private boolean bedReached = false;
    private int timeOut;

    public DogMoveToBedAction(Dog dog, @Nonnull BlockPos targetBedPos, boolean claimBed) {
        super(dog, false, false);
        this.targetBedPos = targetBedPos;
        this.claimBed = claimBed;
    }

    @Override
    public void onStart() {
        this.timeOut = 400;
        this.dog.getNavigation().moveTo(
            (targetBedPos.getX()) + 0.5D, 
            targetBedPos.getY() + 1, 
            (targetBedPos.getZ()) + 0.5D, 1.0D);
    }

    @Override
    public void tick() {

        --timeOut;

        if (timeOut <= 0) {
            this.setState(ActionState.FINISHED);
            return;
        }

        this.bedReached = dog.blockPosition().equals(this.targetBedPos);

        if (bedReached) {
            if (claimBed) claimBed();
            this.dog.setOrderedToSit(true);
            this.setState(ActionState.FINISHED);
            return;
        } else if (dog.getNavigation().isDone()) {
            this.setState(ActionState.FINISHED);
            return;
        }
    }

    private void claimBed() {
        var target = this.targetBedPos;
        var dogBedTileEntity = WorldUtil.getTileEntity(dog.level, target, DogBedTileEntity.class);
        if (dogBedTileEntity == null) return;
        if (dogBedTileEntity.getOwnerUUID() != null) return;

        dogBedTileEntity.setOwner(this.dog);
        this.dog.setBedPos(this.dog.level.dimension(), target);
        this.dog.level.broadcastEntityEvent(this.dog, Constants.EntityState.WOLF_HEARTS);
    }

    @Override
    public void onStop() {
        
    }
    
    @Override
    public boolean canOverrideSit() {
        return true;
    }

}
