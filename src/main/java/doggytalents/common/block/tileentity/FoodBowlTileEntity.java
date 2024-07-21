package doggytalents.common.block.tileentity;

import doggytalents.DoggyTileEntityTypes;
import doggytalents.api.feature.FoodHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.inventory.container.FoodBowlContainer;
import doggytalents.common.util.DogFoodUtil;
import doggytalents.common.util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class FoodBowlTileEntity extends PlacedTileEntity implements MenuProvider {

    private final ItemStackHandler inventory = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            // When contents change mark needs save to disc
            FoodBowlTileEntity.this.setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return FoodHandler.isFood(stack).isPresent();
        }
    };


    public int timeoutCounter;

    public FoodBowlTileEntity(BlockPos pos, BlockState blockState) {
        super(DoggyTileEntityTypes.FOOD_BOWL.get(), pos, blockState);
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider prov) {
        super.loadAdditional(compound, prov);
        this.inventory.deserializeNBT(prov, compound);
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider prov) {
        super.saveAdditional(compound, prov);
        compound.merge(this.inventory.serializeNBT(prov));
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, BlockEntity blockEntity) {
        if (level == null)
            return;
        if (level.isClientSide)
            return; 
        if (!(blockEntity instanceof FoodBowlTileEntity bowl)) {
            return;
        }

        if (++bowl.timeoutCounter < 20) { return; }

        var dogList = level.getEntitiesOfClass(Dog.class, new AABB(pos).inflate(5, 5, 5));

        for (Dog dog : dogList) {
            if (!dog.isDoingFine()) continue;

            UUID placerId = bowl.getPlacerId();
            if (placerId != null && !dog.getBowlPos().isPresent() 
                && placerId.equals(dog.getOwnerUUID())) {
                dog.setBowlPos(bowl.getBlockPos());
            }

            if (bowl.shouldFeed(dog)) {
               dog.triggerAction(new DogEatFromFoodBowl(dog, bowl));
            }
        }

        bowl.timeoutCounter = 0;
    }

    private boolean shouldFeed(Dog target) {
        if (target.isBusy())
            return false;
        if (target.isInSittingPose())
            return false;
        if (target.isOrderedToSit())
            return false;
        if (!this.hasFood(target))
            return false;
        return isHungryDog(target);
    }

    private boolean isHungryDog(Dog dog) {
        return dog.isDoingFine() && dog.getDogHunger() < 25;
    }

    public boolean hasFood(Dog dog) {
        return DogFoodUtil.dogFindFoodInInv(dog, false, this.getInventory()) >= 0;
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.doggytalents.food_bowl");
    }

    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerIn) {
        return new FoodBowlContainer(windowId, this.level, this.worldPosition, playerInventory, playerIn);
    }

    public static class DogEatFromFoodBowl extends TriggerableAction {

        private final FoodBowlTileEntity bowl;
        private int tickTillPathRecalc;
        private boolean enoughHealingFood = false;
        private int goToBowlTimeout = 0;
        private int feedCooldown = 0;
        private boolean failedEating = false;

        public DogEatFromFoodBowl(Dog dog, FoodBowlTileEntity bowl) {
            super(dog, false, false);
            this.bowl = bowl;
        }

        @Override
        public void onStart() {
            this.goToBowlTimeout = 10 * 20;
        }

        @Override
        public void tick() {
            if (!this.stillValidTarget()) {
                setState(ActionState.FINISHED);
                return;
            }
            if (enoughEating()) {
                setState(ActionState.FINISHED);
                return;
            }

            if (failedEating) {
                setState(ActionState.FINISHED);
                return;
            }
            
            boolean is_close_to_bowl = getBowlDistanceSqr() <= 1.5 * 1.5;
            if (!is_close_to_bowl) {
                --this.goToBowlTimeout;
            }
            if (this.goToBowlTimeout <= 0 && !is_close_to_bowl) {
                setState(ActionState.FINISHED);
                return;
            }

            if (feedCooldown > 0)
                --feedCooldown;
            
            if (!is_close_to_bowl) {

                var bowlPos = getBowPos();
                this.dog.getLookControl().setLookAt(bowlPos.x, bowlPos.y, bowlPos.z, 
                    10.0F, this.dog.getMaxHeadXRot());
                if (--this.tickTillPathRecalc <= 0) {
                    this.tickTillPathRecalc = 10;
                    if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
                        //A Valid target is not that far away and is checked above.
                        //if (dog.distanceToSqr(target) > 400) return;
                        this.dog.getNavigation().moveTo(bowlPos.x, bowlPos.y, bowlPos.z, 
                            this.dog.getUrgentSpeedModifier());
                    }
                }
            } else {
                this.dog.getNavigation().stop();
                checkAndEat();
            }

        }

        @Override
        public void onStop() {
            
        }

        private boolean enoughEating() {
            var hunger = dog.getDogHunger();
            if (hunger < 80)
                return false;
            if (dog.isDogLowHealth() 
                && hunger < dog.getMaxHunger())
                return false;
            return true;
        }

        private void checkAndEat() {
            if (feedCooldown > 0)
                return;
            boolean dogNeedsHealing = 
                this.dog.isDogLowHealth() && !dog.hasEffect(MobEffects.REGENERATION);
            if (!enoughHealingFood && dogNeedsHealing) {
                enoughHealingFood = true;
                failedEating = !DogFoodUtil.tryFeed(dog, true, this.bowl.getInventory());
            } else
                failedEating = !DogFoodUtil.tryFeed(dog, false, this.bowl.getInventory());
            feedCooldown = dog.getRandom().nextInt(11);
        }

        private boolean stillValidTarget() {  
            if (this.bowl.isRemoved())
                return false;
            if (getBowlDistanceSqr() > 16*16) 
                return false;
            if (!this.bowl.hasFood(dog))
                return false;
            return true;
        }

        private double getBowlDistanceSqr() {
            var bowlPos = getBowPos();
            return dog.distanceToSqr(bowlPos);
        }

        private Vec3 getBowPos() {
            return Vec3.atBottomCenterOf(this.bowl.getBlockPos());
        }

    }
}
