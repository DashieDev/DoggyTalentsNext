package doggytalents.common.talent;

import doggytalents.DoggyItems;
import doggytalents.TalentsOptions;
import doggytalents.DoggyTags;
import doggytalents.DoggyTalents;
import doggytalents.api.feature.EnumMode;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import doggytalents.api.registry.TalentOption;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.MeatFoodHandler;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.inventory.PackPuppyItemHandler;
import doggytalents.common.item.DogEddibleItem;
import doggytalents.common.item.IDogEddible;
import doggytalents.common.lib.Constants;
import doggytalents.common.network.packet.ParticlePackets;
import doggytalents.common.util.DogFoodUtil;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.InventoryUtil;
import doggytalents.common.util.ItemUtil;
import doggytalents.forge_imitate.event.LivingDropsEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;

public class PackPuppyTalent extends TalentInstance {

    public static final int MAX_DOG_INV_VIEW = 8;

    private boolean renderChest = true;
    private boolean pickupItems = true;
    private boolean offerFood = true;
    private boolean collectKillLoot = true;

    private PackPuppyItemHandler packPuppyHandler;
    private MeatFoodHandler meatFoodHandler = new MeatFoodHandler() {

        @Override
        public boolean isFood(ItemStack stack) {
            var props = ItemUtil.food(stack);

            if (props == null) return false;
            return stack.is(ItemTags.MEAT) && stack.getItem() != Items.ROTTEN_FLESH
                && props.nutrition() >= 6;
        }
        
    };

    public PackPuppyTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
        PackPuppyItemHandler handler = new PackPuppyItemHandler();
        this.packPuppyHandler = handler;
    }

    public PackPuppyItemHandler inventory() {
        return this.packPuppyHandler;
    }

    @Override
    public void tick(AbstractDog dogIn) {
        if (!dogIn.isDoingFine())
            return;
        if (dogIn.level().isClientSide)
            return;
        
        tickDogCollectItems(dogIn);
        tickOfferFoodToTeammate(dogIn);
    }

    public boolean canCollectItems() {
        return this.level() >= 3;
    }

    public boolean canOfferFood() {
        return this.level() >= 4;
    }

    private final double COLLECT_RADIUS = 2;
    private int tickTillUpdateCollect = 10;
    private void tickDogCollectItems(AbstractDog dog) {
        if (dog.level().isClientSide)
            return;
        
        if (!canCollectItems() || !this.pickupItems)
            return;

        if (--this.tickTillUpdateCollect > 0)
            return;
        this.tickTillUpdateCollect = 10;

        var itemList = dog.level().getEntitiesOfClass(
            ItemEntity.class, 
            dog.getBoundingBox().inflate(COLLECT_RADIUS, 1D, COLLECT_RADIUS), 
            PackPuppyTalent::eligibleItemToPickUp);
        if (itemList.isEmpty())
            return;
        for (var entity : itemList) {
            var remaining = InventoryUtil.addItem(this.packPuppyHandler, entity.getItem());

            if (!remaining.isEmpty()) {
                entity.setItem(remaining);
            } else {
                entity.discard();
                dog.playSound(
                    SoundEvents.ITEM_PICKUP, 0.25F, 
                    ((dog.getRandom().nextFloat() - dog.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            }
        }
    }

    public static boolean eligibleItemToPickUp(ItemEntity entity) {
        if (!entity.isAlive())
            return false;
        if (entity.hasPickUpDelay())
            return false;
        var stack = entity.getItem();
        return !stack.is(DoggyTags.PACK_PUPPY_BLACKLIST);
    }

    private final int TRIGGER_RADIUS = 12;
    private int tickTillUpdateFood = 30;
    private void tickOfferFoodToTeammate(AbstractDog dogIn) {
        if (dogIn.level().isClientSide)
            return;

        if (!canOfferFood() || !this.offerFood)
            return;

        if (--this.tickTillUpdateFood > 0)
            return;
        this.tickTillUpdateFood = 60;
        
        if (!(dogIn instanceof Dog dog))
            return;
        
        if (!hasFood(dog, dog)) {
            return;
        }
            
        var hungry_dogs = getNearbyHungryDogs(dog);
        if (hungry_dogs.isEmpty()) return;
        for (var hungry_dog : hungry_dogs) {
            checkAndFeedDog(dog, hungry_dog);
        }

    }

    private void checkAndFeedDog(Dog dog, Dog target) {
        if (target == dog) {
            tryFeed(dog, dog, false);
            return;
        }
        if (target.isBusy())
            return;
        if (target.isInSittingPose())
            return;
        if (target.isOrderedToSit())
            return;
        if (!this.hasFood(dog, target))
            return;
        target.triggerAction(
            new DogEatFromChestDogAction(target, dog)
        );
    }

    private boolean tryFeed(Dog dog, Dog feeder, boolean findHealingFood) {
        return DogFoodUtil.tryFeed(dog, feeder, findHealingFood, this.inventory());
    }

    private List<Dog> getNearbyHungryDogs(Dog dog) {
        var dogs = dog.level().getEntitiesOfClass(
            Dog.class, 
            dog.getBoundingBox().inflate(TRIGGER_RADIUS, 4, TRIGGER_RADIUS), 
            filter_dog -> isEligibleDog(dog, filter_dog));
        return dogs;
    }

    private boolean isEligibleDog(Dog offerer, Dog target) {
        if (!isHungryDog(target))
            return false;
        var ownerUUID = offerer.getOwnerUUID();
        if (ownerUUID == null)
            return false;
        if (!offerer.willObeyOthers() && ObjectUtils.notEqual(ownerUUID, target.getOwnerUUID()))
            return false;
        return true;
    }

    private boolean isHungryDog(Dog dog) {
        return dog.isDoingFine() && dog.getDogHunger() < 25;
    }

    @Override
    public void set(AbstractDog dog, int preLevel) {
        // No need to drop anything if dog didn't have pack puppy
        if (preLevel > 0 && this.level == 0) {
            this.dropInventory(dog);
        }
    }

    @Override
    public void dropInventory(AbstractDog dogIn) {
        if (dogIn.level().isClientSide)
            return;
        if (dogIn.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY))
            return;

        //TODO either drop inventory or save to respawn data, currently does both
        // No need to drop anything if dog didn't have pack puppy
        for (int i = 0; i < this.packPuppyHandler.getSlots(); ++i) {
            Containers.dropItemStack(dogIn.level(), dogIn.getX(), dogIn.getY(), dogIn.getZ(), this.packPuppyHandler.getStackInSlot(i));
            this.packPuppyHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public void writeToNBT(AbstractDog dogIn, CompoundTag compound) {
        super.writeToNBT(dogIn, compound);
        compound.merge(this.packPuppyHandler.serializeNBT(dogIn.registryAccess()));
        compound.putBoolean("renderChest", this.renderChest);
        compound.putBoolean("pickupNearby", this.pickupItems);
        compound.putBoolean("offerFood", this.offerFood);
        compound.putBoolean("collectKillLoot", this.collectKillLoot);
    }

    @Override
    public void readFromNBT(AbstractDog dogIn, CompoundTag compound) {
        super.readFromNBT(dogIn, compound);
        this.packPuppyHandler.deserializeNBT(dogIn.registryAccess(), compound);
        this.renderChest = compound.getBoolean("renderChest");
        this.pickupItems = compound.getBoolean("pickupNearby");
        this.offerFood = compound.getBoolean("offerFood");
        this.collectKillLoot = compound.getBoolean("collectKillLoot");
    }

    // Left in for backwards compatibility for versions <= 2.0.0.5
    @Override
    public void onRead(AbstractDog dogIn, CompoundTag compound) {
        this.packPuppyHandler.deserializeNBT(dogIn.registryAccess(), compound);
    }

    @Override
    public boolean hasRenderer() {
        return ConfigHandler.ClientConfig.getConfig(ConfigHandler.CLIENT.RENDER_CHEST);
    }

    public static boolean hasInventory(AbstractDog dogIn) {
        return dogIn.isDoingFine() && dogIn.getTalent(DoggyTalents.PACK_PUPPY).isPresent();
    }

    public boolean hasFood(Dog finder, Dog forWho) {
        return DogFoodUtil.dogFindFoodInInv(finder, forWho, false, inventory()) >= 0;
    }

    public static PackPuppyTalent getInstanceFromDog(AbstractDog dog) {
        return
            dog.getTalent(DoggyTalents.PACK_PUPPY)
            .map(
                (inst) -> inst.cast(PackPuppyTalent.class)
            ).orElse(null);
    }

    public static class DogEatFromChestDogAction extends TriggerableAction {

        private Dog target;
        private int tickTillPathRecalc;
        private final int stopDist = 2;
        private boolean enoughHealingFood = false;
        private int goToTimeout = 0;
        private int feedCooldown = 0;
        private boolean failedEating = false;

        public DogEatFromChestDogAction(Dog dog, Dog target) {
            super(dog, false, false);
            this.target = target;
        }

        @Override
        public void onStart() {
            this.goToTimeout = 10 * 20;
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

            boolean is_close_to_target = this.dog.distanceToSqr(this.target) <= stopDist*stopDist;
            if (!is_close_to_target) {
                --this.goToTimeout;
            }
            if (this.goToTimeout <= 0 && !is_close_to_target) {
                setState(ActionState.FINISHED);
                return;
            }

            if (feedCooldown > 0)
                --feedCooldown;
            
            if (!is_close_to_target) {

                this.dog.getLookControl().setLookAt(target, 10.0F, this.dog.getMaxHeadXRot());
                if (--this.tickTillPathRecalc <= 0) {
                    this.tickTillPathRecalc = 10;
                    if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
                        //A Valid target is not that far away and is checked above.
                        //if (dog.distanceToSqr(target) > 400) return;
                        this.dog.getNavigation().moveTo(this.target, this.dog.getUrgentSpeedModifier());
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
            var inst = getInstanceFromDog(target);
            if (inst == null)
                return;
            boolean dogNeedsHealing = 
                this.dog.isDogLowHealth() && !dog.hasEffect(MobEffects.REGENERATION);
            if (!enoughHealingFood && dogNeedsHealing) {
                enoughHealingFood = true;
                failedEating = !inst.tryFeed(dog, target, true);
            } else
                failedEating = !inst.tryFeed(dog, target, false);
            feedCooldown = dog.getRandom().nextInt(11);
        }

        private boolean stillValidTarget() {  
            if (!this.target.isAlive())
                return false;
            if (dog.distanceToSqr(target) > 16*16) 
                return false;
            var inst = getInstanceFromDog(target);
            if (inst == null)
                return false;
            if (!inst.hasFood(target, dog))
                return false;
            return true;
        }

    }

    @Override
    public Object getTalentOption(TalentOption<?> entry) {
        if (entry == TalentsOptions.PACK_PUPPY_RENDER.get()) {
            return this.renderChest;
        }
        if (entry == TalentsOptions.PACK_PUPPY_PICKUP.get()) {
            return this.pickupItems;
        }
        if (entry == TalentsOptions.PACK_PUPPY_FOOD.get()) {
            return this.offerFood;
        }
        if (entry == TalentsOptions.PACK_PUPPY_LOOT.get()) {
            return this.collectKillLoot;
        }
        return null;
    }

    @Override
    public void setTalentOption(TalentOption<?> entry, Object data) {
        if (entry == TalentsOptions.PACK_PUPPY_RENDER.get()) {
            this.renderChest = (Boolean) data;
        }
        if (entry == TalentsOptions.PACK_PUPPY_PICKUP.get()) {
            this.pickupItems = (Boolean) data;
        }
        if (entry == TalentsOptions.PACK_PUPPY_FOOD.get()) {
            this.offerFood = (Boolean) data;
        }
        if (entry == TalentsOptions.PACK_PUPPY_LOOT.get()) {
            this.collectKillLoot = (Boolean) data;
        }
    }

    @Override
    public Collection<TalentOption<?>> getAllTalentOptions() {
        return List.of(
            TalentsOptions.PACK_PUPPY_RENDER.get(),
            TalentsOptions.PACK_PUPPY_LOOT.get(),
            TalentsOptions.PACK_PUPPY_PICKUP.get(),
            TalentsOptions.PACK_PUPPY_FOOD.get()    
        );
    }

    public boolean renderChest() { return this.renderChest; }
    public void setRenderChest(boolean render) { this.renderChest = render; }
    public boolean pickupItems() { return this.pickupItems; }
    public void setPickupItems(boolean val) { this.pickupItems = val; }
    public boolean offerFood() { return this.offerFood; }
    public void setOfferFood(boolean val) { this.offerFood = val; }
    public boolean collectKillLoot() { return this.collectKillLoot; }
    public void setCollectKillLoot(boolean val) { this.collectKillLoot = val; }

    private static int NOTIFY_RADIUS = 20;
    public static void mayNotifyNearbyPackPuppy(LivingDropsEvent event) {
        // var source = event.getSource();
        // var killed = event.getEntity();
        // var killer = source.getEntity();
        // if (killer == null)
        //     return;
        // if (killer.level().isClientSide)
        //     return;

        // var drops = event.getDrops();
        // if (drops.isEmpty())
        //     return;

        // if (!(killer instanceof LivingEntity killerLiving))
        //     return;
        
        // boolean eligibleKiller = 
        //     killerLiving instanceof Player
        //     || killerLiving instanceof Dog;
        // if (!eligibleKiller)
        //     return;
        
        // var dogOptional = findNearestChestDogToNotify(killerLiving);
        // if (!dogOptional.isPresent())
        //     return;
        // var dog = dogOptional.get();
        // dog.triggerAction(new DogCollectLootAction(dog, killed.blockPosition()));
    }

    private static Optional<Dog> findNearestChestDogToNotify(LivingEntity killer) {
        var dogs = killer.level().getEntitiesOfClass(
            Dog.class, 
            killer.getBoundingBox().inflate(NOTIFY_RADIUS, 3, NOTIFY_RADIUS),
            filter_dog -> isValidItemCollector(filter_dog, killer)
        );
        if (dogs.isEmpty())
            return Optional.empty();
        
        Dog selected_dog = dogs.get(0);
        double min_dist = selected_dog.distanceToSqr(killer);
        for (var dog : dogs) {
            double dist = dog.distanceToSqr(killer);
            if (dist < min_dist) {
                min_dist = dist;
                selected_dog = dog;
            }
        }
        return Optional.ofNullable(selected_dog);
    }

    private static boolean isValidItemCollector(Dog dog, LivingEntity killer) {
        if (!dog.isDoingFine())
            return false;
        if (!dog.readyForNonTrivialAction())
            return false;

        if (killer == dog)
            return false;
            
        var ownerUUID = dog.getOwnerUUID();
        if (ownerUUID == null)
            return false;
        UUID killerOwnerUUID = null; 
        if (killer instanceof Player player) {
            killerOwnerUUID = player.getUUID();
        } else if (killer instanceof Dog killerDog) {
            killerOwnerUUID = dog.getOwnerUUID();
        }
        if (killerOwnerUUID == null)
            return false;
        if (ObjectUtils.notEqual(ownerUUID, killerOwnerUUID))
            return false;
        
        var instOptional = dog.getTalent(DoggyTalents.PACK_PUPPY);
        if (!instOptional.isPresent())
            return false;
        var inst = instOptional.get();
        if (!(inst instanceof PackPuppyTalent packPup))
            return false;
        if (!packPup.canCollectItems())
            return false;
        if (!packPup.pickupItems())
            return false;
        if (!packPup.collectKillLoot())
            return false;
        
        var inv = packPup.inventory();
        if (inv == null)
            return false;
        boolean hasFreeSlot = false;
        for (int i = 0; i < inv.getSlots(); ++i) {
            var stack = inv.getStackInSlot(i);
            if (stack.isEmpty()) {
                hasFreeSlot = true;
                break;
            }
        }
        if (!hasFreeSlot)
            return false;
    
        return true;
    }

    public static class DogCollectLootAction extends TriggerableAction {

        private BlockPos target;

        public DogCollectLootAction(Dog dog, @Nonnull BlockPos target) {
            super(dog, false, false);
            this.target = target;
        }

        @Override
        public void onStart() {
            if (this.dog.distanceToSqr(Vec3.atBottomCenterOf(target)) > 16 * 16) {
                this.setState(ActionState.FINISHED);
                return;
            }
            this.dog.getNavigation().stop();
            DogUtil.moveToIfReachOrElse(dog, target, 
                dog.getUrgentSpeedModifier(), 1, 1, d -> { this.target = null; });
        }

        @Override
        public void tick() {
            if (this.target == null) {
                this.setState(ActionState.FINISHED);
                return;
            }
            if (this.dog.getNavigation().isDone()) {
                this.setState(ActionState.FINISHED);
                return;
            }
        }

        @Override
        public void onStop() {
        }

    }

}
