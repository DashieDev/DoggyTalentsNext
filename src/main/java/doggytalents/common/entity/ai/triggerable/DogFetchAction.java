package doggytalents.common.entity.ai.triggerable;

import javax.annotation.Nonnull;

import doggytalents.api.feature.EnumMode;
import doggytalents.api.inferface.IThrowableItem;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.EntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class DogFetchAction extends TriggerableAction {

    private final @Nonnull ItemEntity fetchTarget;
    private final @Nonnull LivingEntity owner;

    private final int MIN_DIST = 2;
    private final int MAX_DIST = 32;

    private boolean isBringingBack = false;
    private int tickTillPathRecalc = 1;

    private float oldWaterCost;

    private double oldRangeSense;

    public DogFetchAction(Dog dog, @Nonnull LivingEntity owner, @Nonnull ItemEntity fetchTarget) {
        super(dog, true, true);
        this.fetchTarget = fetchTarget;
        this.owner = owner;
    }

    @Override
    public void onStart() {
        if (dog.hasBone() || dog.getMode() != EnumMode.DOCILE) {
            this.setState(ActionState.FINISHED); return;
        }

        this.tickTillPathRecalc = 1;
        this.isBringingBack = false;

        initFetch();
    }

    @Override
    public void tick() {
        if (!this.isBringingBack) {
            if (!this.canFetchStack(fetchTarget)) {
                this.setState(ActionState.FINISHED); return;
            }
            if (this.dog.hasBone()) {
                this.setState(ActionState.FINISHED); return;
            }
        } else {
            if (!this.dog.hasBone()) {
                this.setState(ActionState.FINISHED); return;
            }
        }

        if (--tickTillPathRecalc < 0) this.tickTillPathRecalc = 20;

        if (!isBringingBack) goGetFetchItem();
        else if (bringBackFetchItem()) {
            this.setState(ActionState.FINISHED); return;
        };
    }

    @Override
    public void onStop() {
        dropFetchItem();
        finishFetch();
    }

    private void initFetch() {
        this.tickTillPathRecalc = 1;
        this.oldWaterCost = this.dog.getPathfindingMalus(BlockPathTypes.WATER);
        this.dog.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        var attrib = this.dog.getAttribute(Attributes.FOLLOW_RANGE);
        if (attrib == null) return;
        this.oldRangeSense = attrib.getValue();
        attrib.setBaseValue(this.MAX_DIST);
    }

    private void finishFetch() {
        this.dog.getNavigation().stop();
        this.dog.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
        var attrib = this.dog.getAttribute(Attributes.FOLLOW_RANGE);
        if (attrib == null) return;
        attrib.setBaseValue(this.oldRangeSense);
    }

    private void goGetFetchItem() {
        this.dog.getLookControl().setLookAt(this.fetchTarget, 10.0F, this.dog.getMaxHeadXRot());
        if (this.tickTillPathRecalc <= 0) {
            this.dog.getNavigation().moveTo(this.fetchTarget, 1);
        }
        checkAndGetFetchItem();
    }

    private boolean bringBackFetchItem() {
        this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
        if (this.dog.distanceToSqr(owner) <= MIN_DIST*MIN_DIST) {
            this.setState(ActionState.FINISHED); return true;
        }
        if (this.tickTillPathRecalc > 0) return false;
        this.dog.getNavigation().moveTo(this.owner, 1);
        return false;
    }

    private boolean checkAndGetFetchItem() {
        if (!this.dog.hasBone() && this.dog.distanceTo(this.fetchTarget) < this.MIN_DIST * this.MIN_DIST) {
            if (this.fetchTarget.isAlive() && !this.fetchTarget.hasPickUpDelay()) {

                this.dog.setBoneVariant(this.fetchTarget.getItem());

                this.fetchTarget.discard();
                this.isBringingBack = true;
                this.tickTillPathRecalc = 1;
                this.dog.getNavigation().stop();
                return true;
            }
        }
        return false;
    }

    private void dropFetchItem() {
        if (this.dog.hasBone()) {
            var throwableItem = this.dog.getThrowableItem();
            var fetchItem = throwableItem != null ? throwableItem.getReturnStack(this.dog.getBoneVariant()) : this.dog.getBoneVariant();

            this.dog.spawnAtLocation(fetchItem, 0.0F);
            this.dog.setBoneVariant(ItemStack.EMPTY);
        }
    }

    private boolean canFetchStack(ItemEntity e) {
        if (!e.isAlive() || e.isInvisible()) return false;
        if (e.distanceTo(this.dog) > EntityUtil.getFollowRange(this.dog)) return false;
        return e.getItem().getItem() instanceof IThrowableItem;
    }
    
}
