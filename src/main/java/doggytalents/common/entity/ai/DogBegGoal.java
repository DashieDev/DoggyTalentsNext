package doggytalents.common.entity.ai;

import doggytalents.DoggyTags;
import doggytalents.api.anim.DogAnimation;
import doggytalents.api.feature.FoodHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.Dog.RestingState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.Optional;

public class DogBegGoal extends Goal {

    private final Dog dog;
    private final float minPlayerDistance;
    private LivingEntity owner;
    private int timeoutCounter;

    public DogBegGoal(Dog wolf, float minDistance) {
        this.dog = wolf;
        this.minPlayerDistance = minDistance;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (dog.isDogResting())
            return false;
        if (!this.dog.getAnim().freeHead() && this.dog.getAnim() != DogAnimation.NONE)
            return false;
        var owner = dog.getOwner();
        if (owner == null)
            return false;
        if (!owner.isAlive() || owner.isSpectator())
            return false;
        if (owner.distanceToSqr(dog) > minPlayerDistance * minPlayerDistance)
            return false;
        if (!this.hasTemptationItemInHand(owner))
            return false;
        this.owner = owner;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.owner.isAlive() || this.owner.isSpectator()) {
            return false;
        } else if (this.dog.distanceToSqr(this.owner) > this.minPlayerDistance * this.minPlayerDistance) {
            return false;
        } else {
            return this.timeoutCounter > 0 && this.hasTemptationItemInHand(this.owner);
        }
    }

    @Override
    public void start() {
        this.dog.setBegging(true);
        this.timeoutCounter = 40 + this.dog.getRandom().nextInt(40);
    }

    @Override
    public void stop() {
        this.dog.setBegging(false);
        this.owner = null;
    }

    @Override
    public void tick() {
        this.dog.getLookControl().setLookAt(this.owner.getX(), this.owner.getEyeY(), this.owner.getZ(), 10.0F, this.dog.getMaxHeadXRot());
        --this.timeoutCounter;
    }

    private boolean hasTemptationItemInHand(LivingEntity player) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack itemstack = player.getItemInHand(hand);
            if (itemstack.is(this.dog.isTame() ? DoggyTags.BEG_ITEMS_TAMED : DoggyTags.BEG_ITEMS_UNTAMED)) {
                return true;
            }

            if (itemstack.is(DoggyTags.TREATS)) {
                return true;
            }

            if (FoodHandler.isFood(itemstack, this.dog).isPresent()) {
                return true;
            }
        }

        return false;
    }
}
