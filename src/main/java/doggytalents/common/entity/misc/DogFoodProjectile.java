package doggytalents.common.entity.misc;

import doggytalents.DoggyEntityTypes;
import doggytalents.api.anim.DogAnimation;
import doggytalents.api.feature.FoodHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.item.IDogEddible;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.NetworkUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;

public class DogFoodProjectile extends ThrowableProjectile implements IEntityAdditionalSpawnData {

    private ItemStack foodStack = ItemStack.EMPTY;

    public DogFoodProjectile(EntityType<? extends ThrowableProjectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public DogFoodProjectile(Level worldIn, LivingEntity livingEntityIn) {
        super(DoggyEntityTypes.DOG_FOOD_PROJ.get(), livingEntityIn, worldIn);
    }

    // public DogFoodProjectile(PlayMessages.SpawnEntity packet, Level worldIn) {
    //     super(DoggyEntityTypes.DOG_FOOD_PROJ.get(), worldIn);
    // }

    @Override
    protected void onHit(HitResult hitResult) {
        if (!this.level().isClientSide && !this.foodStack.isEmpty()) {
            this.spawnAtLocation(foodStack);
        }
        if (!this.level().isClientSide)
            this.discard();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide)
            scanDogAroundAndTrigger();
    }

    private int tickTillScan = 0;
    private void scanDogAroundAndTrigger() {
        if (tickTillScan > 0) {
            --tickTillScan;
            return;
        }
        if (this.getOwner() == null)
            return;
        var pos = this.position();
        var aabb = new AABB(pos.add(-5, -5, -5), pos.add(5, 0, 5));
        var moveVec = this.getDeltaMovement();
        var moveVecXZ = new Vec3(moveVec.x, 0, moveVec.z).normalize();
        if (moveVecXZ.length() < 1) {
            return;
        }
        var dogs = this.level().getEntitiesOfClass(Dog.class, aabb, filter_dog -> 
            this.isValidDog(filter_dog, moveVecXZ));
        if (dogs.isEmpty())
            return;
        tickTillScan = 3;
        var nearestDog = dogs.get(0);
        double minDist = nearestDog.distanceToSqr(this);
        for (var dog : dogs) {
            double dist = dog.distanceToSqr(this);
            if (dist < minDist) {
                nearestDog = dog;
                minDist = dist;
            }
        }
        var dy = this.position().y - nearestDog.position().y;
        var minDistXZ = new Vec3(
            this.position().x - nearestDog.position().x,
            0,
            this.position().z - nearestDog.position().z   
        ).lengthSqr();
        if (minDist < 4) {
            this.feedDog(nearestDog);
        } else if (dy >= 1.5 && minDistXZ >= 10)
            nearestDog.triggerAction(new DogJumpAndEatAction(nearestDog, this));
    }

    private boolean isValidDog(Dog dog, Vec3 lookVecXZ) {
        if (!dog.isDoingFine())
            return false;
        if (dog.isOrderedToSit())
            return false;
        if (dog.getOwner() != this.getOwner())
            return false;
        if (!checkIfDogCanCatch(dog, lookVecXZ) && dog.distanceToSqr(this) >= 4)
            return false;
        if (!dog.readyForNonTrivialAction())
            return false;
        
        return dogCanStillEatItem(dog, foodStack);
    }

    private boolean checkIfDogCanCatch(Dog dog, Vec3 lookVecXZ) {
        var posXZ = new Vec3(
            this.position().x,
            0,
            this.position().z
        );
        var dogPosXZ = new Vec3(
            dog.position().x,
            0,
            dog.position().z
        );
        var dist = DogUtil.distanceFromPointToLineOfUnitVector2DSqr(dogPosXZ, posXZ, lookVecXZ);
        if (dist < 0)
            return false;
        return dist < 1.5*1.5;
    }

    private boolean dogCanStillEatItem(Dog dog, ItemStack stack) {
        var handlerOptional = FoodHandler.getMatch(dog, stack, null);
        if (!handlerOptional.isPresent())
            return false;
        var handler = handlerOptional.get();
        if ((handler instanceof IDogEddible eddible) && eddible.alwaysEatWhenDogConsume(dog))
            return true;
        return dog.getDogHunger() < dog.getMaxHunger();
    }   

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        boolean hasStack = !this.foodStack.isEmpty();
        buffer.writeBoolean(hasStack);
        if (hasStack) {
            NetworkUtil.writeItemToBuf(buffer, foodStack);
        }
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buffer) {
        boolean hasStack = buffer.readBoolean();
        if (hasStack) {
            this.foodStack = NetworkUtil.readItemFromBuf(buffer);
        }
    }

    public ItemStack getDogFoodStack() {
        return this.foodStack;
    }

    public void setDogFoodStack(ItemStack stack) {
        this.foodStack = stack;
    }

    public boolean feedDog(Dog dog) {
        if (!this.isAlive())
            return false;
        var stack = getDogFoodStack();
        if (stack.isEmpty())
            return false;
        var handlerOptional = FoodHandler.getMatch(dog, stack,null);
        if (!handlerOptional.isPresent())
            return false;
        var handler = handlerOptional.get();
        handler.consume(dog, stack, null);
        if (stack.isEmpty())
            this.discard();
        return true;
    }

    // @Override
    // public Packet<ClientGamePacketListener> getAddEntityPacket() {
    //     return (Packet<ClientGamePacketListener>) NetworkHooks.getEntitySpawningPacket(this);
    // }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder b) {
        
    }

    public static class DogJumpAndEatAction extends TriggerableAction {

        private DogFoodProjectile target;
        private int tickTillJump;
        private int tickTillConsumeItem;
        private int stopTick;
        private boolean consumed = false;

        public DogJumpAndEatAction(Dog dog, DogFoodProjectile target) {
            super(dog, false, false);
            this.target = target;
        }

        @Override
        public void onStart() {
            this.stopTick = dog.tickCount + DogAnimation.BACKFLIP.getLengthTicks();
            this.dog.setAnim(DogAnimation.BACKFLIP);
            this.tickTillJump = 3;
            this.tickTillConsumeItem = 5;
        }

        @Override
        public void tick() {
            if (dog.getAnim() != DogAnimation.BACKFLIP) {
                this.setState(ActionState.FINISHED);
                return;
            }
            if (dog.tickCount >= stopTick) {
                this.setState(ActionState.FINISHED);
                return;
            }

            if (--this.tickTillJump == 0) {
                this.dog.getJumpControl().jump();
            }

            // var targetPos = target.position();
            // var dogPos = dog.position();
            // if (targetPos.distanceToSqr(dogPos) > 2.5) {
            //     consumeItem();
            // }
            if (!consumed && --this.tickTillConsumeItem <= 0) {
                tryConsumeItem();
            }
        }

        public void tryConsumeItem() {
            if (!target.isAlive())
                return;
            if (dog.distanceToSqr(target) > 4)
                return;
            target.feedDog(dog);
            consumed = true;
        }

        @Override
        public void onStop() {
            this.dog.getNavigation().stop();
            if (dog.getAnim() == DogAnimation.BACKFLIP) {
                dog.setAnim(DogAnimation.NONE);
            }
        }

    }

}
