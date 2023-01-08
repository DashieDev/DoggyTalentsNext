package doggytalents.common.talent;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

import doggytalents.api.feature.DataKey;
import doggytalents.api.feature.EnumMode;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

/**
 * @author DashieDev
 */
public class WaterHolderTalent extends TalentInstance {

    private static DataKey<MoveToExtinguishGoal> WATER_HOLDER_DOG_AI = DataKey.make();

    private static int EFFECT_RANGE = 5;
    private static int SEARCH_RADIUS = 12;

    private int waterUnitLeft;

    private int ticktillSearch;
    private int tickTillRegenWater;

    private int tickScheduledForExtinguish = 0;
    private boolean scheduledExtinguish = false;

    private final ArrayList<LivingEntity> targets = new ArrayList<LivingEntity>();

    public WaterHolderTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dog) {
        if (!(dog instanceof Dog)) return;
        if (!dog.hasData(WATER_HOLDER_DOG_AI)) {
            var extinguishGoal = new MoveToExtinguishGoal((Dog)dog, this);
            dog.goalSelector.addGoal(((Dog) dog).TALENT_GOAL_PRIORITY, extinguishGoal);
            dog.setData(WATER_HOLDER_DOG_AI, extinguishGoal);
        }
    }

    private int getMaxWaterHold() {
        return 5 + this.level()*3;
    }

    private float getOnFireTickFactor() {
        return 0.2f*this.level();
    }

    private int decreaseTickOnFire(int tickOnFire) {
        if (tickOnFire <= 2) return 0;
        return Mth.floor(tickOnFire - this.getOnFireTickFactor()*tickOnFire);
    }

    private int getWaterUnitleft() {
        return this.waterUnitLeft;
    }

    private void setWaterUnitLeft(int unit) {
        this.waterUnitLeft = Mth.clamp(unit, 0, this.getMaxWaterHold());
    }

    private void incWaterUnitLeft() {
        this.setWaterUnitLeft(this.getWaterUnitleft() + 1);
    }

    private void decWaterUnitLeft() {
        this.setWaterUnitLeft(this.getWaterUnitleft() - 1);
    }

    @Override
    public void livingTick(AbstractDog dog) {
        if (dog.level.isClientSide) {
            return;
        }

        if (!(dog instanceof Dog)) return;

        var d = (Dog) dog;

        if (!dog.isInSittingPose() && (d.isMode(EnumMode.DOCILE, EnumMode.GUARD_MINOR)) && --this.ticktillSearch <= 0) {
            this.ticktillSearch = 10;
            this.refreshOnFireTargets(dog);
        }
        if (scheduledExtinguish && dog.tickCount >= this.tickScheduledForExtinguish) {
            this.extinguishNearby(dog);
            this.scheduledExtinguish = false;
        }
        if (dog.isInWaterOrRain() && --this.tickTillRegenWater <= 0) {
            this.tickTillRegenWater = 10;
            this.incWaterUnitLeft();
        }
    }

    @Override
    public InteractionResult processInteract(AbstractDog dog, Level level, Player player, InteractionHand hand) {
        
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof BucketItem) {
            if (!dog.level.isClientSide) {
                if (player.isShiftKeyDown()) {
                    var c1 = Component.translatable("talent.doggytalents.water_holder.amount");
                    c1.append(Component.literal(": "));
                    if (this.level < this.talent.getMaxLevel()) {
                        c1.append(Component.literal("" +this.getWaterUnitleft())
                        .withStyle(
                            Style.EMPTY.withBold(true)
                            .withColor(0x03a5fc)
                        ));
                        c1.append(Component.literal("/" + this.getMaxWaterHold()));
                    } else {
                        c1.append(Component.translatable("talent.doggytalents.water_holder.amount.unlim")
                        .withStyle(
                            Style.EMPTY.withBold(true)
                            .withColor(0x03a5fc)
                        ));
                    }
                    
                    player.sendSystemMessage(c1);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        if (stack.getItem() == Items.WATER_BUCKET) {

            if (!dog.level.isClientSide) {
                
                if (this.getWaterUnitleft() >= this.getMaxWaterHold()) return InteractionResult.PASS;

                this.setWaterUnitLeft(this.getMaxWaterHold());

                if (!player.getAbilities().instabuild) player.setItemInHand(hand, new ItemStack(Items.BUCKET) );
                dog.playSound(SoundEvents.BUCKET_FILL, dog.getSoundVolume(), 1.0f);
                if (dog.level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(
                        ParticleTypes.SPLASH, 
                        dog.getX(), dog.getY(), dog.getZ(), 
                        this.level*8, 
                        dog.getBbWidth(), 0.8f, dog.getBbWidth(), 
                        0.5 //TODO Tune
                    );
                }
            }
            //Already max'ed
            
            return InteractionResult.SUCCESS;
            
        } 
        
        return InteractionResult.PASS;
    }

    @Override
    public void writeToNBT(AbstractDog dog, CompoundTag compound) {
        super.writeToNBT(dog, compound);
        compound.putInt("DTwaterUnitLeft", this.getWaterUnitleft());
    }

    @Override
    public void readFromNBT(AbstractDog dog, CompoundTag compound) {
        super.readFromNBT(dog, compound);
        this.setWaterUnitLeft(compound.getInt("DTwaterUnitLeft")); 
    }

    private void extinguishNearby(AbstractDog dog) {
        var targets = this.getNearbyOnFire(dog);
        if (targets.isEmpty()) return;
        this.decWaterUnitLeft();

        for (var e : targets) {
            this.extinguishEntity(dog, e);
        }
    }

    private void extinguishEntity(AbstractDog dog, LivingEntity e) {
        int a0 = e.getRemainingFireTicks();
        e.setRemainingFireTicks(this.decreaseTickOnFire(a0));
        e.playSound( 
            SoundEvents.FIRE_EXTINGUISH,  
            0.5F, 2.6F + e.getRandom().nextFloat() - e.getRandom().nextFloat() * 0.8F);
        if (dog.level instanceof ServerLevel serverLevel)
        serverLevel.sendParticles(
            ParticleTypes.SMOKE, 
            e.getX(), e.getY(), e.getZ(), 
            15, 
            e.getBbWidth(), 0.8f, e.getBbWidth(), 
            0.1 //TODO Tune
        );
        var bp = e.blockPosition().mutable();

        for (int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                bp.setX(bp.getX() + i); bp.setZ(bp.getZ()+j);
                if (e.level.getBlockState(bp).getBlock() == Blocks.FIRE ) {
                    e.level.setBlockAndUpdate(bp, Blocks.AIR.defaultBlockState());
                }
            }
        }
    }

    public void scheduleDelayedExtinguish(AbstractDog dog) {
        if (this.scheduledExtinguish) return; // Already scheduled
        this.scheduledExtinguish = true;
        this.tickScheduledForExtinguish = dog.tickCount + this.getDelay();
    }

    private List<LivingEntity> getNearbyOnFire(AbstractDog dog) {
        return dog.level.getEntitiesOfClass(LivingEntity.class, dog.getBoundingBox().inflate(EFFECT_RANGE, 4, EFFECT_RANGE), 
            e -> 
            
                //Low health entities
                (e.isOnFire()) && (

                    // is Dog's Owner
                    (dog.getOwner() == e) 
                    
                    //is dog of the same owner
                    || ( 
                        e instanceof AbstractDog
                        && ((AbstractDog)e).getOwner() == dog.getOwner()
                    )

                    //is wolf of the same owner
                    || ( 
                        e instanceof Wolf
                        && ((Wolf)e).getOwner() == dog.getOwner()
                    )

                //can see target
                ) && (dog.hasLineOfSight(e))

        );
    } 

    private void refreshOnFireTargets(AbstractDog dog) {
        this.targets.clear();
        Predicate<LivingEntity> onFireAndWitness =
            e -> e.isOnFire()
                && (dog.hasLineOfSight(e));
        
        //Get owner 
        var owner = dog.getOwner();
        if (owner != null && onFireAndWitness.test(owner)) {
            this.targets.add(owner);
        }
        
        //Get Dogs of the same owner
        var dogs = dog.level.getEntitiesOfClass(
            AbstractDog.class,    
            dog.getBoundingBox().inflate(SEARCH_RADIUS, 4, SEARCH_RADIUS),
            d -> (
                    d.getOwner() == dog.getOwner()
                    && onFireAndWitness.test(d)
                )
            );
        if (!dogs.isEmpty()) {
            this.targets.addAll(dogs);
        }

        //Get Wolves of the same owner
        var wolves = dog.level.getEntitiesOfClass(
            Wolf.class,    
            dog.getBoundingBox().inflate(SEARCH_RADIUS, 4, SEARCH_RADIUS),
            w -> (
                    w.getOwner() == dog.getOwner()
                    && onFireAndWitness.test(w)
                )
            );
        if (!wolves.isEmpty()) {
            this.targets.addAll(wolves);
        }

    }

    private LivingEntity selectOnFireTarget(AbstractDog dog) {
        if (this.targets.isEmpty()) return null;

        var target = this.targets.get(0); 
        double mindistanceSqr = target.distanceToSqr(dog);
        
        for (var i : this.targets) {
            if (dog.getOwner() == i) return i;
            else {
                var d = i.distanceToSqr(dog);
                if (d < mindistanceSqr) {
                    target = i;
                    mindistanceSqr = d;
                }
            }
        }

        //ChopinLogger.l("target : " +target);

        return target;

    }

    public int getDelay() {
        return 10;
    }

    private boolean canAffordToExtinguish(AbstractDog dog) {
        return this.getWaterUnitleft() > 0 || this.level() >= this.talent.getMaxLevel();
    }

    //TODO There is seems like a pattern emerging from this
    //And the RescueDogTalent, maybe consider coupling it
    //together... Or composition is enough already.
    public static class MoveToExtinguishGoal extends Goal {

        private Dog dog;
        private WaterHolderTalent talentInst;
        private LivingEntity target;

        private int ticksUntilPathRecalc = 0;
        

        private final int stopDist = 2;    

        public MoveToExtinguishGoal(Dog dog, WaterHolderTalent talentInst) {
            this.dog = dog;
            this.talentInst = talentInst;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.dog.isInSittingPose()) return false;
            if (!this.dog.isMode(EnumMode.DOCILE, EnumMode.GUARD_MINOR)) return false;
            this.target = this.talentInst.selectOnFireTarget(this.dog);
            if (target == null) return false;
            if (!this.stillValidTarget()) return false;       
            return true;
        }

        @Override
        public boolean canContinueToUse() {
            if (this.dog.isInSittingPose()) return false;
            if (!this.dog.isMode(EnumMode.DOCILE, EnumMode.GUARD_MINOR)) return false;
            if (target == null) return false;
            if (!this.stillValidTarget()) return false;

            return true;
        }

        @Override
        public void start() {
            this.dog.getLookControl().setLookAt(target, 10.0F, this.dog.getMaxHeadXRot());
        }

        @Override
        public void tick() {
            
            if (this.dog.distanceToSqr(this.target) > stopDist*stopDist) {

                this.dog.getLookControl().setLookAt(target, 10.0F, this.dog.getMaxHeadXRot());
                if (--this.ticksUntilPathRecalc <= 0) {
                    this.ticksUntilPathRecalc = 10;
                    if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
                        //TODO ?
                        // if (this.dog.distanceToSqr(this.target) >= 144.0D) {
                        //     DogUtil.guessAndTryToTeleportToOwner(dog, 4);
                        // } else {
                            this.dog.getNavigation().moveTo(this.target, this.dog.getUrgentSpeedModifier());
                        //}
                    }
                }
            } else {
                this.dog.getNavigation().stop();
                this.dog.startShakingAndBroadcast(false);
                this.talentInst.scheduleDelayedExtinguish(dog);
            }
        }

        private boolean stillValidTarget() {
            if (!this.talentInst.canAffordToExtinguish(dog)) return false;
            if (this.target.isInLava()) return false;

            return (
                this.target.isAlive()
                && target.getRemainingFireTicks() >= 30
            );
        }


    }


    
}
