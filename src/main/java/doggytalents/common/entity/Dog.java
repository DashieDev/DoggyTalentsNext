package doggytalents.common.entity;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import doggytalents.*;
import doggytalents.api.enu.WetSource;
import doggytalents.api.feature.*;
import doggytalents.api.feature.DogLevel.Type;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.api.inferface.IDogFoodHandler;
import doggytalents.api.inferface.IThrowableItem;
import doggytalents.api.registry.*;
import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.skin.DogSkin;
import doggytalents.client.event.ClientEventHandler;
import doggytalents.client.screen.DogNewInfoScreen.DogNewInfoScreen;
import doggytalents.client.screen.DogNewInfoScreen.screen.DogCannotInteractWithScreen;
import doggytalents.common.artifacts.DoggyArtifact;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.config.ConfigHandler.ClientConfig;
import doggytalents.common.entity.ai.nav.DogBodyRotationControl;
import doggytalents.common.entity.ai.nav.DogMoveControl;
import doggytalents.common.entity.ai.nav.DogPathNavigation;
import doggytalents.common.entity.ai.triggerable.AnimationAction;
import doggytalents.common.entity.ai.triggerable.DogBackFlipAction;
import doggytalents.common.entity.ai.triggerable.DogDrownAction;
import doggytalents.common.entity.ai.triggerable.DogPlayTagAction;
import doggytalents.common.entity.ai.triggerable.DogTriggerableGoal;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.entity.ai.triggerable.TriggerableAction.ActionState;
import doggytalents.common.entity.anim.DogAnimation;
import doggytalents.common.entity.anim.DogAnimationManager;
import doggytalents.common.entity.anim.DogPose;
import doggytalents.common.entity.DogIncapacitatedMananger.BandaidState;
import doggytalents.common.entity.DogIncapacitatedMananger.DefeatedType;
import doggytalents.common.entity.DogIncapacitatedMananger.IncapacitatedSyncState;
import doggytalents.common.entity.ai.*;
import doggytalents.common.entity.serializers.DimensionDependantArg;
import doggytalents.common.entity.stats.StatsTracker;
import doggytalents.common.entity.texture.DogSkinData;
import doggytalents.common.item.DoggyArtifactItem;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.ParticlePackets;
import doggytalents.common.network.packet.ParticlePackets.CritEmitterPacket;
import doggytalents.common.network.packet.data.DogMountData;
import doggytalents.common.storage.DogLocationStorage;
import doggytalents.common.storage.DogRespawnStorage;
import doggytalents.common.util.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.DataItem;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Dog extends AbstractDog {

    private static final EntityDataAccessor<Optional<Component>> LAST_KNOWN_NAME = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.OPTIONAL_COMPONENT);
    
    /**
     *     Bit number      Decimal Val         Flag
     *     0               1                   BEGGING
     *     1               2                   OBEY_OTHER
     *     2               4                   FRIENDLY_FIRE
     *     3               8                   FORCE_SIT
     *     4               16                  LOW_HEALTH_STRATEGY_LSB           
     *     5               32                  LOW_HEALTH_STRATEGY_MSB
     *     6               64                  CROSS_ORIGIN_TP
     *     7               128                 REGARD_TEAM_PLAYERS
     *     8               256                 RESTING
     *     9               512                 <Reserved>
     *     .
     *     .
     *     31              2^31                <Reserved>
     */
    private static final EntityDataAccessor<Integer> DOG_FLAGS = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> HUNGER_INT = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.FLOAT);
   
    private static final EntityDataAccessor<ItemStack> BONE_VARIANT = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.ITEM_STACK);

    private static final EntityDataAccessor<Integer> ANIMATION = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FREEZE_ANIM = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Long> FREEZE_ANIM_TIME = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.LONG);
    private static final EntityDataAccessor<Float> FREEZE_YROT = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.FLOAT);

    // Use Cache.make to ensure static fields are not initialised too early (before Serializers have been registered)
    private static final Cache<EntityDataAccessor<List<AccessoryInstance>>> ACCESSORIES =  Cache.make(() -> (EntityDataAccessor<List<AccessoryInstance>>) SynchedEntityData.defineId(Dog.class, DoggySerializers.ACCESSORY_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<List<TalentInstance>>> TALENTS = Cache.make(() -> (EntityDataAccessor<List<TalentInstance>>) SynchedEntityData.defineId(Dog.class, DoggySerializers.TALENT_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<DogLevel>> DOG_LEVEL = Cache.make(() -> (EntityDataAccessor<DogLevel>) SynchedEntityData.defineId(Dog.class, DoggySerializers.DOG_LEVEL_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<EnumGender>> GENDER = Cache.make(() -> (EntityDataAccessor<EnumGender>) SynchedEntityData.defineId(Dog.class,  DoggySerializers.GENDER_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<EnumMode>> MODE = Cache.make(() -> (EntityDataAccessor<EnumMode>) SynchedEntityData.defineId(Dog.class, DoggySerializers.MODE_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>> DOG_BED_LOCATION = Cache.make(() -> (EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>) SynchedEntityData.defineId(Dog.class, DoggySerializers.BED_LOC_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>> DOG_BOWL_LOCATION = Cache.make(() -> (EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>) SynchedEntityData.defineId(Dog.class, DoggySerializers.BED_LOC_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<IncapacitatedSyncState>> DOG_INCAP_SYNC_STATE = Cache.make(() -> (EntityDataAccessor<IncapacitatedSyncState>) SynchedEntityData.defineId(Dog.class, DoggySerializers.INCAP_SYNC_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<List<DoggyArtifactItem>>> ARTIFACTS = Cache.make(() -> (EntityDataAccessor<List<DoggyArtifactItem>>) SynchedEntityData.defineId(Dog.class, DoggySerializers.ARTIFACTS_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<DogSize>> DOG_SIZE = Cache.make(() -> (EntityDataAccessor<DogSize>) SynchedEntityData.defineId(Dog.class,  DoggySerializers.DOG_SIZE_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<DogSkinData>> CUSTOM_SKIN = Cache.make(() -> (EntityDataAccessor<DogSkinData>) SynchedEntityData.defineId(Dog.class,  DoggySerializers.DOG_SKIN_DATA_SERIALIZER.get()));

    public static final void initDataParameters() { 
        ACCESSORIES.get();
        TALENTS.get();
        DOG_LEVEL.get();
        GENDER.get();
        MODE.get();
        DOG_BED_LOCATION.get();
        DOG_BOWL_LOCATION.get();
        DOG_INCAP_SYNC_STATE.get();
        ARTIFACTS.get();
        DOG_SIZE.get();
    }

    // Cached values
    private final Cache<Integer> spendablePoints = Cache.make(this::getSpendablePointsInternal);
    private final List<IDogAlteration> alterations = new ArrayList<>(4);
    private final List<IDogFoodHandler> foodHandlers = new ArrayList<>(4);
    public final DogAnimationManager animationManager = new DogAnimationManager(this);
    public final Map<Integer, Object> objects = new HashMap<>();

    private DogSkin clientSkin = DogSkin.CLASSICAL;
    private ArrayList<AccessoryInstance> clientAccessories
        = new ArrayList<AccessoryInstance>();

    
        
    public final StatsTracker statsTracker = new StatsTracker();
    public final DogOwnerDistanceManager dogOwnerDistanceManager 
        = new DogOwnerDistanceManager(this);
    public final DogMiningCautiousManager dogMiningCautiousManager
        = new DogMiningCautiousManager(this);
    public final DogGroupsManager dogGroupsManager
        = new DogGroupsManager();
    public final DogIncapacitatedMananger incapacitatedMananger
        = new DogIncapacitatedMananger(this);

    protected final PathNavigation defaultNavigation;
    protected final MoveControl defaultMoveControl;
    
    protected TriggerableAction stashedAction;
    protected TriggerableAction activeAction;
    protected int delayedActionStart = 0;
    protected int actionPendingTime = 0;
    protected List<WrappedGoal> trivialBlocking;
    protected List<WrappedGoal> nonTrivialBlocking;
    

    private int hungerTick;
    private int prevHungerTick;  
    private int hungerSaturation; // Extra Hunger to prevent real hunger from decreasing when saturated
    private int hungerSaturationHealingTick;   
    private int healingTick;  
    private int prevHealingTick;

    private float headRotationCourse;
    private float headRotationCourseOld;
    private WetSource wetSource;
    private boolean isShaking;
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;
    private int tickUntilRest;

    private boolean wasInLava = false;
    private boolean shakeFire = false;
    
    private float radPerHealthDecrease;
    private float maxHealth0;

    protected boolean dogJumping;
    protected float jumpPower;

    protected boolean isDogSwimming;
    protected boolean isLowHunger;
    protected boolean isZeroHunger;
    protected int hungerDamageTick;

    public int lastOrderedToSitTick;
    private int tickChopinTail;

    private static final UUID HUNGER_MOVEMENT = UUID.fromString("50671f49-1dfd-4397-242b-78bb6b178115");

    public Dog(EntityType<? extends Dog> type, Level worldIn) {
        super(type, worldIn);
        this.setTame(false);
        this.setGender(EnumGender.random(this.getRandom()));
        this.setLowHealthStrategy(LowHealthStrategy.STICK_TO_OWNER);
        this.resetTickTillRest();

        this.moveControl = new DogMoveControl(this);

        this.defaultNavigation = this.navigation;
        this.defaultMoveControl = this.moveControl;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ACCESSORIES.get(), new ArrayList<>());
        this.entityData.define(TALENTS.get(), new ArrayList<>());
        this.entityData.define(LAST_KNOWN_NAME, Optional.empty());
        this.entityData.define(DOG_FLAGS, 0);
        this.entityData.define(GENDER.get(), EnumGender.UNISEX);
        this.entityData.define(MODE.get(), EnumMode.DOCILE);
        this.entityData.define(HUNGER_INT, 60F);
        this.entityData.define(CUSTOM_SKIN.get(), DogSkinData.NULL);
        this.entityData.define(DOG_LEVEL.get(), new DogLevel(0, 0));
        this.entityData.define(DOG_INCAP_SYNC_STATE.get(), IncapacitatedSyncState.NONE);
        this.entityData.define(DOG_SIZE.get(), DogSize.MODERATO);
        this.entityData.define(BONE_VARIANT, ItemStack.EMPTY);
        this.entityData.define(ARTIFACTS.get(), new ArrayList<DoggyArtifactItem>(3));
        this.entityData.define(DOG_BED_LOCATION.get(), new DimensionDependantArg<>(() -> EntityDataSerializers.OPTIONAL_BLOCK_POS));
        this.entityData.define(DOG_BOWL_LOCATION.get(), new DimensionDependantArg<>(() -> EntityDataSerializers.OPTIONAL_BLOCK_POS));
        this.entityData.define(ANIMATION, 0);
        this.entityData.define(FREEZE_ANIM_TIME, 0L);
        this.entityData.define(FREEZE_ANIM, 0);
        this.entityData.define(FREEZE_YROT, 0f);
    }

    @Override
    protected void registerGoals() {
        int trivial_p = 0, non_trivial_p = 0;
        DogSitWhenOrderedGoal sitGoal = null;
        
        int p = 1;
        this.goalSelector.addGoal(p, new DogFloatGoal(this));
        this.goalSelector.addGoal(p, new DogFindWaterGoal(this));
        this.goalSelector.addGoal(p, new DogAvoidPushWhenIdleGoal(this));
        //this.goalSelector.addGoal(1, new PatrolAreaGoal(this));
        ++p;
        this.goalSelector.addGoal(p, new DogGoAwayFromFireGoal(this));
        ++p;
        sitGoal = new DogSitWhenOrderedGoal(this);
        this.goalSelector.addGoal(p, sitGoal);
        this.goalSelector.addGoal(p, new DogProtestSitOrderGoal(this));
        ++p;
        this.goalSelector.addGoal(p, new DogHungryGoal(this, 1.0f, 2.0f));
        ++p;
        this.goalSelector.addGoal(p, new DogLowHealthGoal.StickToOwner(this));
        this.goalSelector.addGoal(p, new DogLowHealthGoal.RunAway(this));
        //this.goalSelector.addGoal(4, new DogLeapAtTargetGoal(this, 0.4F));
        ++p;
        non_trivial_p = p;
        this.goalSelector.addGoal(p, new DogTriggerableGoal(this, false));
        ++p; //Prioritize Talent Action
        //All mutex by nature
        this.goalSelector.addGoal(p, new GuardModeGoal.Minor(this));
        this.goalSelector.addGoal(p, new GuardModeGoal.Major(this));
        ++p;
        this.goalSelector.addGoal(p, new DogMeleeAttackGoal(this, 1.0D, true, 20, 40));
        this.goalSelector.addGoal(p, new DogWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(p, new DogGoRestOnBedGoalDefeated(this));
        ++p;
        trivial_p = p;
        //Dog greet owner goal here
        this.goalSelector.addGoal(p, new DogTriggerableGoal(this, true));
        //this.goalSelector.addGoal(p, new FetchGoal(this, 1.0D, 32.0F));
        this.goalSelector.addGoal(p, new DogFollowOwnerGoalDefeated(this));
        this.goalSelector.addGoal(p, new DogFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        ++p;
        this.goalSelector.addGoal(p, new DogBreedGoal(this, 1.0D));
        ++p;
        this.goalSelector.addGoal(p, new DogRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(p, new DogRandomStandIdleGoal(this));
        ++p;
        this.goalSelector.addGoal(p, new DogBegGoal(this, 8.0F));
        ++p;
        this.goalSelector.addGoal(p, new DogLookAtPlayerGoal(this));
        this.goalSelector.addGoal(p, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(p, new DogRandomSitIdleGoal(this));
        this.goalSelector.addGoal(p, new DogRestWhenSitGoal(this));
        
        this.targetSelector.addGoal(1, new DogOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new DogOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(5, new DogNearestToOwnerAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
        this.targetSelector.addGoal(6, new BerserkerModeGoal(this));
        this.targetSelector.addGoal(6, new GuardModeGoal(this));
        //this.goalSelector.addGoal(1, new Wolf.WolfPanicGoal(1.5D)); //Stooopid...

        populateActionBlockingGoals(trivial_p, non_trivial_p, sitGoal);
    }

    private void populateActionBlockingGoals(int trivialP, int nonTrivialP, DogSitWhenOrderedGoal sitGoal) {
        nonTrivialBlocking = new ArrayList<WrappedGoal>();
        trivialBlocking = new ArrayList<WrappedGoal>();

        var trivial = this.goalSelector.getAvailableGoals()
            .stream().filter(x -> (
                x.getGoal() != sitGoal 
                && x.getPriority() <= trivialP
                && x.getFlags().contains(Goal.Flag.MOVE)    
            ))
            .collect(Collectors.toList());
        var nonTrivial = this.goalSelector.getAvailableGoals()
            .stream().filter(x -> (
                x.getGoal() != sitGoal
                && x.getPriority() <= nonTrivialP
                && x.getFlags().contains(Goal.Flag.MOVE)    
            ))
            .collect(Collectors.toList());
        nonTrivialBlocking.addAll(nonTrivial);
        trivialBlocking.addAll(trivial);
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isDefeated()) {
            // if (this.getDogHunger() > 32) {
            //     return this.random.nextInt(2) == 0 ? SoundEvents.WOLF_WHINE : SoundEvents.WOLF_PANT;
            // }
            return this.random.nextInt(2) == 0 ? SoundEvents.WOLF_WHINE : SoundEvents.WOLF_PANT;
        }
        if (this.random.nextInt(3) == 0) {
            return this.isTame() && this.getHealth() < 10.0F ? SoundEvents.WOLF_WHINE : SoundEvents.WOLF_PANT;
        } else {
            return SoundEvents.WOLF_AMBIENT;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.WOLF_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    protected SoundEvent getHowlSound() {
        return SoundEvents.WOLF_HOWL;
    }

    public void howl() {
        this.playSound(this.getHowlSound(), 1, this.getVoicePitch());
    }

    @Override
    public float getSoundVolume() {
        return 0.4F;
    }

    public boolean isDogWet() {
        return this.wetSource != null;
    }

    @OnlyIn(Dist.CLIENT)
    public float getShadingWhileWet(float partialTicks) {
        return Math.min(0.5F + Mth.lerp(partialTicks, this.prevTimeWolfIsShaking, this.timeWolfIsShaking) / 2.0F * 0.5F, 1.0F);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getShakeAngle(float partialTicks, float offset) {
        float f = (Mth.lerp(partialTicks, this.prevTimeWolfIsShaking, this.timeWolfIsShaking) + offset) / 1.8F;
        if (f < 0.0F) {
            f = 0.0F;
        } else if (f > 1.0F) {
            f = 1.0F;
        }

        return Mth.sin(f * (float)Math.PI) * Mth.sin(f * (float)Math.PI * 11.0F) * 0.15F * (float)Math.PI;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getInterestedAngle(float partialTicks) {
        return Mth.lerp(partialTicks, this.headRotationCourseOld, this.headRotationCourse) * 0.15F * (float)Math.PI;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == doggytalents.common.lib.Constants.EntityState.WOLF_START_SHAKING) {
            this.startShaking();
        } else if (id == doggytalents.common.lib.Constants.EntityState.WOLF_INTERUPT_SHAKING) {
            this.finishShaking();
        } else {
            super.handleEntityEvent(id);
        }
    }

    public float getTailRotation() {
        return 
            this.isTame() ? 
            (1.73f) - this.radPerHealthDecrease*(this.getMaxHealth() - this.getHealth()) 
            : ((float)Math.PI / 5F);
    }

    @Override
    public float getWagAngle(float limbSwing, float limbSwingAmount, float partialTickTime) {
        return Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return sizeIn.height * 0.8F;
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    @Override
    public int getMaxHeadXRot() {
        return this.isInSittingPose() ? 20 : super.getMaxHeadXRot();
    }

    @Override
    public double getMyRidingOffset() {
        return this.getVehicle() instanceof Player ? 0.5D : 0.2D;
    }

    @Override
    protected void addPassenger(Entity passanger) {
        super.addPassenger(passanger);
        this.refreshDimensions();
    }

    @Override
    protected void removePassenger(Entity passanger) {
        super.removePassenger(passanger);
        this.refreshDimensions();
    }

    @Override
    public EntityDimensions getDimensions(Pose p_19975_) {
        var self_dim = super.getDimensions(p_19975_);
        if (this.isVehicle() && !this.getPassengers().isEmpty())
            self_dim = computeRidingDimension(self_dim);
        return self_dim;
    }

    @Override
    public double getPassengersRidingOffset() {
        return (double)this.getRealDimensions().height * 0.75D;
    }

    public EntityDimensions getRealDimensions() {
        return super.getDimensions(getPose());
    }

    private EntityDimensions computeRidingDimension(EntityDimensions self_dim) {
        float total_width = self_dim.width;
        float total_height = (float) this.getPassengersRidingOffset();
        
        var passenger = this.getPassengers().get(0);
        total_width = Math.max(total_width, passenger.getBbWidth());
        total_height += passenger.getBbHeight() + passenger.getMyRidingOffset();
        
        return new EntityDimensions(total_width, total_height, self_dim.fixed);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isAlive() && this.canUpdateClassicalAnim()) {
            var pose = this.getDogPose();
            this.headRotationCourseOld = this.headRotationCourse;
            if (this.isBegging() && pose.canBeg) {
                this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
            } else {
                this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
            }

            if (pose.canShake) {
                boolean inWater = this.isInWater();
                // If inWater is false then isInRain is true in the or statement
                boolean inRain = inWater ? false : this.isInWaterOrRain();
                boolean inBubbleColumn = this.isInWaterOrBubble(); //!!!!!

                if (inWater || inRain || inBubbleColumn) {
                    if (this.wetSource == null) {
                        this.wetSource = WetSource.of(inWater, inBubbleColumn, inRain);
                    }
                    if (this.isShaking && !this.level().isClientSide) {
                        this.finishShaking();
                        this.level().broadcastEntityEvent(this, doggytalents.common.lib.Constants.EntityState.WOLF_INTERUPT_SHAKING);
                    }
                } else if ((this.wetSource != null || this.isShaking) && this.isShaking) {
                    if (this.timeWolfIsShaking == 0.0F) {
                        if (!this.shakeFire) this.playSound(SoundEvents.WOLF_SHAKE, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    }

                    this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
                    this.timeWolfIsShaking += 0.05F;
                    if (this.prevTimeWolfIsShaking >= 2.0F) {

                        //TODO check if only called server side
                        if (this.wetSource != null) {
                            for (IDogAlteration alter : this.alterations) {
                                alter.onShakingDry(this, this.wetSource);
                            }
                        }

                        this.wetSource = null;
                        this.finishShaking();
                    }

                    if (this.timeWolfIsShaking > 0.4F) {
                        float f = (float)this.getY();
                        int i = (int)(Mth.sin((this.timeWolfIsShaking - 0.4F) * (float)Math.PI) * 7.0F);
                        Vec3 vec3d = this.getDeltaMovement();

                        for (int j = 0; j < i; ++j) {
                            float f1 = (this.random.nextFloat() * 2.0F - 1.0F) * this.getBbWidth() * 0.5F;
                            float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.getBbWidth() * 0.5F;
                            if (this.shakeFire) {
                                byte r = (byte) this.getRandom().nextInt(3);
                                if (r==0)
                                    this.level().addParticle(ParticleTypes.LAVA, this.getX() + f1, f + 0.8F, this.getZ() + f2, vec3d.x, vec3d.y, vec3d.z);
                                else if (r==1)
                                    this.level().addParticle(ParticleTypes.FLAME, this.getX() + f1, f + 0.8F, this.getZ() + f2, vec3d.x, vec3d.y, vec3d.z);
                                else if (r==2)
                                    this.level().addParticle(ParticleTypes.SMOKE, this.getX() + f1, f + 0.8F, this.getZ() + f2, vec3d.x, vec3d.y, vec3d.z);
                            } else
                            this.level().addParticle(ParticleTypes.SPLASH, this.getX() + f1, f + 0.8F, this.getZ() + f2, vec3d.x, vec3d.y, vec3d.z);
                        }
                    }

                    if (this.timeWolfIsShaking > 0.8) {
                        if (this.shakeFire && random.nextInt(6) == 0) this.playSound(SoundEvents.FIRE_EXTINGUISH, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    }
                }
            } else {
                if (this.isShaking) finishShaking();
            }
        }
        if (this.isAlive() && !this.canUpdateClassicalAnim() && this.isShaking) {
            this.finishShaking();
            this.headRotationCourseOld = this.headRotationCourse;
            this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
        }

        this.setMaxUpStep(this.isVehicle() ? 1f : 0.6f);
        if (this.isAlive()) {
            this.updateDogPose();
        }

        this.alterations.forEach((alter) -> alter.tick(this));

        if (this.isAlive() && this.getMaxHealth() != this.maxHealth0) {
            this.maxHealth0 = this.getMaxHealth();
            this.radPerHealthDecrease = Mth.HALF_PI / this.maxHealth0;
        }

        // On server side
        if (this.isAlive() && !this.level().isClientSide) {

            // Every 2 seconds
            if (this.tickCount % 40 == 0) {
                DogLocationStorage.get(this.level()).getOrCreateData(this).update(this);


                var owner = this.getOwner();
                if (owner != null) {
                    this.setOwnersName(owner.getName());
                }
            }
        }
        
        if (this.isAlive()) {
            this.animationManager.tick();
            if (!this.level().isClientSide)
                this.tickAnimAction();
        }

        //Client
        if (this.level().isClientSide) {
            proccessCustomModelSkin();
        }

        if (this.level().isClientSide && this.isOnFire() 
            && ConfigHandler.CLIENT.DISPLAY_SMOKE_WHEN_ON_FIRE.get()) {
            if (this.getRandom().nextInt(3) == 0) {
                float f1 = (this.getRandom().nextFloat() * 2.0F - 1.0F) * this.getBbWidth() * 0.5F;
                float f2 = (this.getRandom().nextFloat() * 2.0F - 1.0F) * this.getBbWidth() * 0.5F;
                this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                this.getX() + f1,
                this.getY() + this.getEyeHeight(),
                this.getZ() + f2,
                0, 0.05 , 0 );
            }
        }

        if (this.freezeAnim != DogAnimation.NONE) {
            this.yBodyRot = this.getFreezeYRot();
            this.yHeadRot = this.yBodyRot;
            this.setYRot(this.yBodyRot);
            this.yBodyRotO = this.yBodyRot;
            this.yHeadRotO = this.yHeadRot;
            this.yRotO = this.getYRot();
        }
    }

    public boolean canDoIdileAnim() {
        if (!this.isAlive())
            return false;
        if (this.isOnFire())
            return false;
        if (this.isPassenger() || this.isVehicle())
            return false;
        return !this.isShaking && !this.animationManager.started();
    }

    public boolean canContinueDoIdileAnim() {
        if (this.isPassenger() || this.isVehicle())
            return false;
        return this.isAlive() && !this.isShaking && !this.isOnFire();
    }

    public boolean canUpdateClassicalAnim() {
        if (this.animationManager.started())
            return false;
        return true;
    }

    private void proccessCustomModelSkin() {
        var skin = this.getClientSkin();
        if (!skin.useCustomModel()) return;
        var model = skin.getCustomModel().getValue();
        if (model.tickClient()) model.doTickClient(this);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide && this.delayedActionStart > 0)
            --this.delayedActionStart;

        if (!this.level().isClientSide) {
            updateAndInvalidatePendingAction();
        }   

        if (!this.level().isClientSide && this.wetSource != null && !this.isShaking && !this.isPathFinding() && this.onGround() && this.canUpdateClassicalAnim()) {
            this.startShakingAndBroadcast(false);
        }

        if (!this.level().isClientSide && this.fireImmune()) {
            if (this.isInLava()) {
                this.wasInLava = true;
            }
            if (this.wasInLava == true && !this.isInLava() && !this.isShaking && !this.isPathFinding() && this.onGround()) {
                this.startShakingAndBroadcast(true);
                this.wasInLava = false;
            }
        }
        
        //Hunger And Healing tick.
        if (!this.level().isClientSide && !this.isDefeated()) {
            
            if (! ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.DISABLE_HUNGER)) {
                this.prevHungerTick = this.hungerTick;

                if (!this.isVehicle() && !this.isInSittingPose()) {
                    this.hungerTick += 1;
                }

                for (IDogAlteration alter : this.alterations) {
                    InteractionResultHolder<Integer> result = alter.hungerTick(this, this.hungerTick - this.prevHungerTick);

                    if (result.getResult().shouldSwing()) {
                        this.hungerTick = result.getObject() + this.prevHungerTick;
                    }
                }

                int tickPerDec = 
                    ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.TICK_PER_HUNGER_DEC);

                if (this.hungerTick > tickPerDec) {
                    if (this.hungerSaturation > 0) {
                        --this.hungerSaturation;
                    } else {
                        this.setDogHunger(this.getDogHunger() - 1);
                    }
                    
                    this.hungerTick -= tickPerDec;
                }
                if (this.isZeroHunger)
                    this.handleZeroHunger();
            }

            if (hungerSaturation > 0) {
                if (this.getHealth() < this.getMaxHealth()) {
                    if (--this.hungerSaturationHealingTick <= 0) {
                        this.hungerSaturationHealingTick = 10;
                        this.heal(1.0f);
                        hungerSaturation -= 3; // -3 saturation per health healed
                    }
                }
            }
            
            this.prevHealingTick = this.healingTick;
            this.healingTick += 8;

            if (this.isInSittingPose()) {
                this.healingTick += 4;
            }

            for (IDogAlteration alter : this.alterations) {
                InteractionResultHolder<Integer> result = alter.healingTick(this, this.healingTick - this.prevHealingTick);

                if (result.getResult().shouldSwing()) {
                    this.healingTick = result.getObject() + this.prevHealingTick;
                }
            }

            if (this.healingTick >= 6000) {
                if (this.getHealth() < this.getMaxHealth()) {
                    this.heal(1);
                }

                this.healingTick = 0;
            }

            this.dogOwnerDistanceManager.tick();
            this.dogMiningCautiousManager.tick();
        }

        if (this.level().isClientSide && this.getDogLevel().isDireDog() && ConfigHandler.ClientConfig.getConfig(ConfigHandler.CLIENT.DIRE_PARTICLES)) {
            for (int i = 0; i < 2; i++) {
                this.level().addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2D);
            }
        }

        // Check if dog bowl still exists every 50t/2.5s, if not remove
        if (this.tickCount % 50 == 0) {
            ResourceKey<Level> dimKey = this.level().dimension();
            Optional<BlockPos> bowlPos = this.getBowlPos(dimKey);

            // If the dog has a food bowl in this dimension then check if it is still there
            // Only check if the chunk it is in is loaded
            if (bowlPos.isPresent() && this.level().hasChunkAt(bowlPos.get()) && !this.level().getBlockState(bowlPos.get()).is(DoggyBlocks.FOOD_BOWL.get())) {
                this.setBowlPos(dimKey, Optional.empty());
            }
        }

        this.alterations.forEach((alter) -> alter.livingTick(this));

        if (this.isDefeated())
        this.incapacitatedMananger.tick();

        if (this.tickChopinTail > 0) {
            --this.tickChopinTail;
        }

        

        if (!this.level().isClientSide && this.isInSittingPose() && !this.resting() && this.tickUntilRest > 0 ) {
            --this.tickUntilRest;
        }
    }

    public TriggerableAction getTriggerableAction() {
        return this.activeAction;
    }

    public boolean triggerAction(TriggerableAction action) {
        if (this.activeAction == action) {
            return false;
        }
        //Trigger Action cancel.
        //If dog have stashed action then will push the action back instead.
        if (action == null) {
            if (activeAction != null) this.activeAction.onStop();
            this.activeAction = null;
            if (this.stashedAction != null) {
                this.activeAction = this.stashedAction;
                this.stashedAction = null;
                ChopinLogger.lwn(this, "retrieved stashed action : " + this.activeAction);
            }
            return false;
        }
        //Replacement only happens if 
        //old action is Trivial and new action is not.
        if (this.activeAction != null) {
            if (!this.activeAction.isTrivial()) return false;
            else if (action.isTrivial()) return false;
        }
        //Only set action dog is not sitting or action can override sit.
        if (this.isOrderedToSit()) {
            if (this.forceSit()) return false;
            if (!action.canOverrideSit()) return false;
        }
        this.setOrderedToSit(false);
        //Check And Stash existing action.
        if (activeAction != null) {
            if (this.activeAction.canPause()) {
                if (this.stashedAction != null) {
                    this.stashedAction.onStop();
                }
                this.activeAction.setState(ActionState.PAUSED);
                this.stashedAction = this.activeAction;
                ChopinLogger.lwn(this,"Stashed action : " + this.stashedAction);
            } else {
                this.activeAction.onStop();
            }
        }
        //Set.
        this.activeAction = action;
        ChopinLogger.lwn(this, "triggered action : " + action);
        return true;
    }

    public boolean isBusy() {
        if (!this.isDoingFine()) return true;
        if (this.isInSittingPose() && this.forceSit()) return true;
        if (this.activeAction != null) return true;
        for (var x : this.trivialBlocking) {
            if (x.isRunning())
                return true;
        }
        if (this.hasControllingPassenger())
            return true;
        return false;
    }

    public boolean readyForNonTrivialAction() {
        if (!this.isDoingFine()) return false;
        if (this.isInSittingPose() && this.forceSit()) return false;
        if (this.activeAction != null && !this.activeAction.isTrivial())
            return false;
        for (var x : this.nonTrivialBlocking) {
            if (x.isRunning())
                return false;
        }
        if (this.hasControllingPassenger())
            return false;
        return true;
    }

    public TriggerableAction getStashedTriggerableAction() {
        return this.stashedAction;
    }

    public void clearTriggerableAction() {
        if (this.stashedAction != null) {
            this.stashedAction.onStop();
            this.stashedAction = null;
        }
        this.triggerAction(null);
    }

    public void setStashedTriggerableAction(TriggerableAction action) {
        if (action == this.stashedAction) return;
        if (this.stashedAction != null) this.stashedAction.onStop();
        this.stashedAction = action;
    }

    public boolean hasDelayedActionStart() {
        return this.delayedActionStart > 0;
    }

    public boolean triggerActionDelayed(int delay, TriggerableAction action) {
        boolean triggered = this.triggerAction(action);
        if (triggered)
            this.delayedActionStart = delay;
        return triggered;
    }

    protected void updateAndInvalidatePendingAction() {
        if (this.activeAction == null) {
            this.actionPendingTime = 0;
            return;
        }
        if (this.activeAction.getState() != ActionState.PENDING) {
            this.actionPendingTime = 0;
            return;
        }
        if (++this.actionPendingTime >= 20) {
            ChopinLogger.sendToOwner(this, "action waited too long!");
            this.actionPendingTime = 0;
            this.clearTriggerableAction();
        }
    }

    private DogAnimation freezeAnim = DogAnimation.NONE;
    public void setFreezePose() {
        this.entityData.set(FREEZE_ANIM, this.getAnim().getId());
        if (!this.level().isClientSide)
        this.animationManager.animationState.updateTime(this.tickCount, this.getAnim().getSpeedModifier());
        this.entityData.set(FREEZE_ANIM_TIME, this.animationManager.animationState.getAccumulatedTime());
    }

    public long freezeTime() {
        return this.entityData.get(FREEZE_ANIM_TIME);
    }

    public DogAnimation getFreezeAnim() {
        return freezeAnim;
    }

    public void setFreezeYRot(float yRot) {
        this.entityData.set(FREEZE_YROT, yRot);
    }

    public float getFreezeYRot() {
        return this.entityData.get(FREEZE_YROT);
    }

    //End Client
    
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        // if (!this.level().isClientSide && stack.getItem() == Items.STONE_AXE) {
        //     long startTime = System.nanoTime();
        //     CachedSearchUtil.getRandomSafePosUsingPool(this, this.blockPosition(), 4, 2);
        //     long stopTime = System.nanoTime();
        //     ChopinLogger.l("get random pos " + (stopTime-startTime) + " nanoseconds." );
        // })
    
        if (stack.getItem() == Items.STONE_PICKAXE) {
            if (this.freezeAnim != DogAnimation.NONE) {
                this.entityData.set(FREEZE_ANIM, 0);
            } else
            this.setFreezePose();
            return InteractionResult.SUCCESS;
        } else if (stack.getItem() == Items.PINK_DYE) {
            this.setAnim(DogAnimation.BACKFLIP);
            return InteractionResult.SUCCESS;
        } else if (stack.getItem() == Items.BLUE_DYE) {
            this.setAnim(DogAnimation.STRETCH);
            return InteractionResult.SUCCESS;
        }else if (stack.getItem() == Items.GREEN_DYE) {
            this.setAnim(DogAnimation.SIT_IDLE_2);
            return InteractionResult.SUCCESS;
        }else if (stack.getItem() == Items.PURPLE_DYE) {
            this.setAnim(DogAnimation.SCRATCHIE);
            return InteractionResult.SUCCESS;
        }else if (stack.getItem() == Items.YELLOW_DYE) {
            this.setAnim(DogAnimation.LYING_DOWN);
            return InteractionResult.SUCCESS;
        }else if (stack.getItem() == Items.RED_DYE) {
            this.setAnim(DogAnimation.DIG);
            return InteractionResult.SUCCESS;
        }else if (stack.getItem() == Items.ORANGE_DYE) {
            this.setAnim(DogAnimation.CHOPIN_TAIL);
            return InteractionResult.SUCCESS;
        }else if (stack.getItem() == Items.BROWN_DYE) {
            this.setAnim(DogAnimation.BELLY_RUB);
            return InteractionResult.SUCCESS;
        }else if (stack.getItem() == Items.BLACK_DYE) {
            this.setAnim(DogAnimation.BACKFLIP);
            return InteractionResult.SUCCESS;
        } else if (stack.getItem() == Items.RED_WOOL) {
            this.setAnim(DogAnimation.HOWL);
            return InteractionResult.SUCCESS;
        } else if (stack.getItem() == Items.TORCH) {
            this.setFreezeYRot(player.yBodyRot);
            return InteractionResult.SUCCESS;
        }

        if (this.isDefeated()) 
            return this.incapacitatedMananger
                .interact(stack, player, hand);

        if (this.isTame()) {
            if (stack.getItem() == Items.STICK) {

                if (this.level().isClientSide) {
                    if (this.canInteract(player)) {
                        DogNewInfoScreen.open(this);
                    } else {
                        DogCannotInteractWithScreen.open(this);
                    }
                }

                return InteractionResult.SUCCESS;
            }
        } else { // Not tamed
            if (stack.getItem() == Items.BONE || stack.getItem() == DoggyItems.TRAINING_TREAT.get()) {

                if (!this.level().isClientSide) {
                    this.usePlayerItem(player, hand, stack);

                    if (stack.getItem() == DoggyItems.TRAINING_TREAT.get() || this.random.nextInt(3) == 0) {
                        this.tame(player);
                        this.navigation.stop();
                        this.setTarget((LivingEntity) null);
                        this.setOrderedToSit(true);
                        this.setHealth(20.0F);
                        this.level().broadcastEntityEvent(this, doggytalents.common.lib.Constants.EntityState.WOLF_HEARTS);
                    } else {
                        this.level().broadcastEntityEvent(this, doggytalents.common.lib.Constants.EntityState.WOLF_SMOKE);
                    }
                }

                return InteractionResult.SUCCESS;
            } 
        }

        if (dogCheckAndRidePlayer(player, stack).shouldSwing())
            return InteractionResult.SUCCESS;

        Optional<IDogFoodHandler> foodHandler = FoodHandler.getMatch(this, stack, player);

        if (foodHandler.isPresent()) {
            return foodHandler.get().consume(this, stack, player);
        }

        InteractionResult interactResult = InteractHandler.getMatch(this, stack, player, hand);

        if (interactResult != InteractionResult.PASS) {
            return interactResult;
        }

        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.processInteract(this, this.level(), player, hand);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }

        if (handleBreeding(player, hand, stack).shouldSwing()) {
            return InteractionResult.SUCCESS;
        }

        InteractionResult actionresulttype = super.mobInteract(player, hand);
        int sit_interval = this.tickCount - this.lastOrderedToSitTick;
        float r = this.getRandom().nextFloat();
        if ((!actionresulttype.consumesAction() || this.isBaby()) && this.canInteract(player) && !this.isProtesting()) {
            if (!this.level().isClientSide && this.isOrderedToSit() 
                && checkRandomBackflip(r, sit_interval)
                && this.level().getBlockState(this.blockPosition().above()).isAir()) {
                this.setStandAnim(DogAnimation.NONE);
                this.triggerAnimationAction(new DogBackFlipAction(this));
            }
            if (!this.level().isClientSide && !this.isOrderedToSit()) {
                this.lastOrderedToSitTick = this.tickCount;
            }
            this.setOrderedToSit(!this.isOrderedToSit());
            this.jumping = false;
            this.navigation.stop();
            this.setTarget(null);
            return InteractionResult.SUCCESS;
        } else if (this.level().isClientSide) {
            this.displayToastIfNoPermission(player);
        }

        return actionresulttype;
    }

    public InteractionResult dogCheckAndRidePlayer(Player player, ItemStack stack) {
        if (player.hasPassenger(this)) {
            if (!this.level().isClientSide)
                this.unRide();
            return InteractionResult.SUCCESS;
        }
        if (stack.getItem() != Items.BONE)
            return InteractionResult.PASS;
        if (!player.isShiftKeyDown())
            return InteractionResult.PASS;
        if (this.isVehicle())
            return InteractionResult.PASS;
        if (!this.canInteract(player))
            return InteractionResult.PASS;
        if (!this.level().isClientSide) {
            if (this.startRiding(player))
            player.displayClientMessage(
                Component.translatable(
                    "talent.doggytalents.bed_finder.dog_mount", 
                    this.getGenderPronoun()), true);
        }
        return InteractionResult.SUCCESS;
    }

    private InteractionResult handleBreeding(Player player, InteractionHand hand, ItemStack stack) {
        if (!stack.is(DoggyTags.BREEDING_ITEMS))
            return InteractionResult.PASS;
        if (!canInteract(player))
            return InteractionResult.PASS;
        

        if (this.level().isClientSide)
            return InteractionResult.SUCCESS;

        int age = this.getAge();
        if (age == 0 && this.canFallInLove()) {
            this.usePlayerItem(player, hand, stack);
            this.setInLove(player);
        } else if (this.isBaby()) {
            this.usePlayerItem(player, hand, stack);
            this.ageUp(getSpeedUpSecondsWhenFeeding(-age), true);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean canStillEat() {
        if (this.level().isClientSide)
            return false;
        if (ConfigHandler.SERVER.DISABLE_HUNGER.get()) {
            if(this.getHealth() < this.getMaxHealth()
                && this.hungerSaturation <= 0)
                return true;
        }
        
        return this.getDogHunger() < this.getMaxHunger();
    }

    private boolean checkRandomBackflip(float r, int sit_interval) {
        if (sit_interval <= 30) 
            return false;
        if (sit_interval >= 1200)
            return r <= 0.7f;
        return r <= 0.3f;
    }

    private boolean isProtesting = false;

    public boolean isProtesting() { return isProtesting; }
    public void setProtesting(boolean p) { this.isProtesting = p;  }

    private void displayToastIfNoPermission(Player player) {
        if (this.canInteract(player)) return;
        player.displayClientMessage(
            Component.translatable("doggui.invalid_dog.no_permission.title", this.getName().getString())
            .withStyle(ChatFormatting.RED) 
        , true);
    }

    @Override
    public boolean dismountsUnderwater() {
        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.canBeRiddenInWater(this);

            if (result.shouldSwing()) {
                return false;
            } else if (result == InteractionResult.FAIL) {
                return true;
            }
        }

        return super.dismountsUnderwater();
    }

    @Override
    public void stopRiding() {
        if (!this.level().isClientSide) { 
            var e0 = this.getVehicle();
            super.stopRiding();
            var e1 = this.getVehicle();
            if (e0 != e1 && e0 instanceof ServerPlayer player) {
                PacketHandler.send(PacketDistributor.PLAYER.with(() -> player), 
                    new DogMountData(this.getId(), false)
                );
            }
        } else {
            super.stopRiding();
        }
        
    }
    private boolean ridingAuthorized = false;

    @Override
    public boolean startRiding(Entity entity) {
        var result = false;
        boolean not_authorized = 
            requireRidingAuthorization(entity)
            && !isRidingAuthorized();
        if (!not_authorized) {   
            result = super.startRiding(entity);
        }
        ridingAuthorized = false;

        if (!this.level().isClientSide && result) {
            if (entity instanceof ServerPlayer player) {
                PacketHandler.send(PacketDistributor.PLAYER.with(() -> player), 
                    new DogMountData(this.getId(), true)
                );
            }
        }
        return result;
    }

    public boolean isRidingAuthorized() {
        return this.ridingAuthorized;
    }

    public void authorizeRiding() {
        this.ridingAuthorized = true;
    }

    public boolean requireRidingAuthorization(Entity entity) {
        return entity instanceof AbstractMinecart
            || entity instanceof Boat;
    }

    @Override
    public boolean canTrample(BlockState state, BlockPos pos, float fallDistance) {
        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.canTrample(this, state, pos, fallDistance);

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }

        return super.canTrample(state, pos, fallDistance);
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.onLivingFall(this, distance, damageMultiplier); // TODO pass source

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }

        // Start: Logic copied from the super call and altered to apply the reduced fall damage to passengers too. #358
        float[] ret = net.minecraftforge.common.ForgeHooks.onLivingFall(this, distance, damageMultiplier);
        if (ret == null) return false;
        distance = ret[0];
        damageMultiplier = ret[1];

        int i = this.calculateFallDamage(distance, damageMultiplier);

        if (i > 0) {
            if (this.isVehicle()) {
                for(Entity e : this.getPassengers()) {
                   e.hurt(this.damageSources().fall(), i);
                }
            }

            // Sound selection is copied from Entity#getFallDamageSound()
           this.playSound(i > 4 ? this.getFallSounds().big() : this.getFallSounds().small(), 1.0F, 1.0F);
           this.playBlockFallSound();
           this.hurt(this.damageSources().fall(), (float)i);
           return true;
        } else {
           return false;
        }
        // End: Logic copied from the super call and altered to apply the reduced fall damage to passengers too. #358
    }

    // TODO
    @Override
    public int getMaxFallDistance() {
        // for (var a : this.alterations) {
        // var i = a.safeFallDistance(this);
        // if (i.getResult().shouldSwing()) {
        // return i.getObject().intValue();
        // }
        // }
        return 3; 
        // For now, set it to 3 to prevent the dog from jumping down a dangerous height to chase a target
        // Which is kinda sucks because as i experienced, one of my dog chase a skeleton and drop down from a height
        // distance which kills him, which is the same with vanilla wolves/dogs :)))
    }

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        MobEffectInstance effectInst = this.getEffect(MobEffects.JUMP);
        float f = effectInst == null ? 0.0F : effectInst.getAmplifier() + 1;
        distance -= f;

        for (IDogAlteration alter : this.alterations) {
            InteractionResultHolder<Float> result = alter.calculateFallDistance(this, distance);

            if (result.getResult().shouldSwing()) {
                distance = result.getObject();
                break;
            }
        }

        return Mth.ceil((distance - 3.0F - f) * damageMultiplier);
    }

    @Override
    public boolean canBreatheUnderwater() {
        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.canBreatheUnderwater(this);

            if (result.shouldSwing()) {
                return true;
            }
        }

        return super.canBreatheUnderwater();
    }

    @Override
    protected int decreaseAirSupply(int air) {
        for (IDogAlteration alter : this.alterations) {
            InteractionResultHolder<Integer> result = alter.decreaseAirSupply(this, air);

            if (result.getResult().shouldSwing()) {
                return result.getObject();
            }
        }

        return super.decreaseAirSupply(air);
    }

    @Override
    public boolean canStandOnFluid(FluidState state) {
        for (var alter : this.alterations) {
            var result = alter.canStandOnFluid(this, state);

            if (result.shouldSwing()) {
                return true;
            }
        }

        return super.canStandOnFluid(state);
    }

    @Override
    public boolean ignoreExplosion() {
        for (var alter : this.alterations) {
            var result = alter.negateExplosion(this);
            if (result.shouldSwing()) {
                return true;
            }
        }
        return super.ignoreExplosion();
    }


    @Override
    protected int increaseAirSupply(int currentAir) {
        currentAir += 4;
        for (IDogAlteration alter : this.alterations) {
            InteractionResultHolder<Integer> result = alter.determineNextAir(this, currentAir);

            if (result.getResult().shouldSwing()) {
                currentAir = result.getObject();
                break;
            }
        }

        return Math.min(currentAir, this.getMaxAirSupply());
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if (this.isMode(EnumMode.DOCILE)) {
            return false;
        }

        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.canAttack(this, target);

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }

        // Stop dogs being able to attack creepers. If the dog has lvl 5 creeper
        // sweeper then we will return true in the for loop above.
        if (target instanceof Creeper) {
            return false;
        }

        return super.canAttack(target);
    }

    @Override
    public boolean canAttackType(EntityType<?> entityType) {
        if (this.isMode(EnumMode.DOCILE)) {
            return false;
        }

        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.canAttack(this, entityType);

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }

        // Stop dogs being able to attack creepers. If the dog has lvl 5 creeper
        // sweeper then we will return true in the for loop above.
        if (entityType == EntityType.CREEPER) {
            return false;
        }

        return super.canAttackType(entityType);
    }

    @Override
    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (this.isMode(EnumMode.DOCILE)) {
            return false;
        }

        //TODO make wolves not able to attack dogs
        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.shouldAttackEntity(this, target, owner);

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }

        // Stop dogs being able to attack creepers. If the dog has lvl 5 creeper
        // sweeper then we will return true in the for loop above.
        if (target instanceof Creeper || target instanceof Ghast) {
            return false;
        }

        if (target instanceof Wolf) {
            Wolf wolfentity = (Wolf)target;
            return !wolfentity.isTame() || wolfentity.getOwner() != owner;
        } else if (target instanceof Dog) {
            Dog dogEntity = (Dog)target;
            return !dogEntity.isTame() || dogEntity.getOwner() != owner;
         } else if (target instanceof Player && owner instanceof Player && !((Player)owner).canHarmPlayer((Player)target)) {
             return false;
        } else if (target instanceof AbstractHorse && ((AbstractHorse)target).isTamed()) {
            return false;
        } else {
            return !(target instanceof TamableAnimal) || !((TamableAnimal)target).isTame();
        }
    }

    // TODO
    //@Override
//    public boolean canAttack(LivingEntity livingentityIn, EntityPredicate predicateIn) {
//        return predicateIn.canTarget(this, livingentityIn);
//     }

    @Override
    public boolean hurt(DamageSource source, float amount) {

        var attacker = source.getEntity();

        if (this.isDefeated() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            //Reset the dog incapacitated healing time
            //The dog is already weak, hurting the dog makes,
            //the dog being weak for longer...
            this.setDogHunger(0);
            this.incapacitatedMananger.onHurt();

            //Invalidate dog as a target for whatever killed him.
            if (attacker instanceof Mob mob) {
                var target = mob.getTarget();
                if (target == this) {
                    mob.setTarget(null);
                }
            }

            return false;
        }

        for (IDogAlteration alter : this.alterations) {
            InteractionResultHolder<Float> result = alter.gettingAttackedFrom(this, source, amount);

            // TODO
            if (result.getResult() == InteractionResult.FAIL) {
                return false;
            } else {
                amount = result.getObject();
            }
        }

        if (this.isInvulnerableTo(source)) {
            return false;
        }

        boolean allPlayerCannotAttackDog = 
            ConfigHandler.ClientConfig.getConfig(ConfigHandler.SERVER.ALL_PLAYER_CANNOT_ATTACK_DOG);

        if (allPlayerCannotAttackDog && attacker instanceof Player) {
            return false;
        } 

        // Must be checked here too as hitByEntity only applies to when the dog is
        // directly hit not indirect damage like sweeping effect etc
        if (!this.canOwnerAttack()) {
            var owner = this.getOwner();
            boolean flag = 
                this.checkIfAttackedFromOwnerOrTeam(owner, attacker);
            if (flag) return false;
        }
        
        if (!this.level().isClientSide)
        if (this.isInSittingPose() || amount > 6.0f) {
            this.setAnim(DogAnimation.HURT_1);
        } else if (source.getEntity() != null) {
            this.setAnim(DogAnimation.HURT_2);
        }
        this.setStandAnim(DogAnimation.NONE);
        this.setOrderedToSit(false);
        

        if (attacker != null && !(attacker instanceof Player) && !(attacker instanceof AbstractArrow)) {
            amount = (amount + 1.0F) / 2.0F;
        }

        boolean ret = super.hurt(source, amount);
        if (this.level().isClientSide
            && ConfigHandler.CLIENT.BLOCK_RED_OVERLAY_WHEN_HURT.get()) {
            this.hurtTime = 0;
            this.hurtDuration = 0;
        }
        return ret;
    }

    public boolean checkIfAttackedFromOwnerOrTeam(LivingEntity owner, Entity attacker) {
        if (owner == null || attacker == null) 
            return false;
        if (owner == attacker) 
            return true;
        if (attacker.isAlliedTo(owner))
            return true;
        return false;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        

        var attackDamageInst = this.getAttribute(Attributes.ATTACK_DAMAGE);
        var critDamageInst = this.getAttribute(DoggyAttributes.CRIT_CHANCE.get());

        Set<AttributeModifier> critModifiers = null;

        if (critDamageInst != null && critDamageInst.getValue() > this.getRandom().nextDouble()) {
            var critBonusInst = this.getAttribute(DoggyAttributes.CRIT_BONUS.get());
            critModifiers = 
                critBonusInst == null ? null 
                    : critBonusInst.getModifiers();
            if (critModifiers != null && attackDamageInst != null)
            critModifiers.forEach(attackDamageInst::addTransientModifier);
        }

        int damage = (int)(attackDamageInst == null ? 0
            : attackDamageInst.getValue());

        //Vanilla hardcoded enchantment effect bonus
        var stack = this.getMainHandItem();
        if (target instanceof LivingEntity && stack != null) {
            damage += EnchantmentHelper
                .getDamageBonus(this.getMainHandItem(), ((LivingEntity)target).getMobType());
        }


        if (critModifiers != null && attackDamageInst != null) {
            critModifiers.forEach(attackDamageInst::removeModifier);
        }

        boolean flag = target.hurt(this.damageSources().mobAttack(this), damage);
        if (!flag) return false;

        this.doEnchantDamageEffects(this, target);
        this.statsTracker.increaseDamageDealt(damage);

        if (critModifiers != null) {
            CritEmitterPacket.sendCritEmitterPacketToNearClients(target);
            //Borrow the sound from the player.
            this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 0.5f, 1);
        }

        for (IDogAlteration alter : this.alterations) {
            alter.doAdditionalAttackEffects(this, target);
        }

        return true;
    }

    @Override
    public void doEnchantDamageEffects(LivingEntity dog, Entity target) {
        
        //instead of doing this before the damage is done, this can be done after
        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            target.setSecondsOnFire(i * 4);
        }

        //Also this
        float knockback = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        knockback += (float)EnchantmentHelper.getKnockbackBonus(this);
        if (knockback > 0 && target instanceof LivingEntity living) {
            living.knockback(
                (double)(knockback * 0.5F), 
                (double)Mth.sin(this.getYRot() * Mth.DEG_TO_RAD), 
                (double)(-Mth.cos(this.getYRot() * Mth.DEG_TO_RAD))
            );
            this.setDeltaMovement(
                this.getDeltaMovement()
                    .multiply(0.6D, 1.0D, 0.6D)
            );
        }

        super.doEnchantDamageEffects(dog, target);
    }

    @Override
    public void awardKillScore(Entity killed, int scoreValue, DamageSource damageSource) {
        super.awardKillScore(killed, scoreValue, damageSource);
        this.statsTracker.incrementKillCount(killed);
    }

    @Override
    public boolean isDamageSourceBlocked(DamageSource source) {
        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.canBlockDamageSource(this, source);

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }

        return super.isDamageSourceBlocked(source);
    }

    @Override
    public boolean canBeSeenAsEnemy() {
        return !this.isDefeated() && super.canBeSeenAsEnemy();
    }

    @Override
    public void setSecondsOnFire(int second) {
        for (IDogAlteration alter : this.alterations) {
            InteractionResultHolder<Integer> result = alter.setFire(this, second);

            if (result.getResult().shouldSwing()) {
                second = result.getObject();
            }
        }

        super.setSecondsOnFire(second);
    }

    @Override
    public boolean fireImmune() {
        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.isImmuneToFire(this);

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }

        return super.fireImmune();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.isInvulnerableTo(this, source);

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }

        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean isInvulnerable() {
        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.isInvulnerable(this);

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }

        return super.isInvulnerable();
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectIn) {
        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.isPotionApplicable(this, effectIn);

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }

        return super.canBeAffected(effectIn);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public void setUUID(UUID uniqueIdIn) {

        // If the UUID is changed remove old one and add new one
        UUID oldUniqueId = this.getUUID();

        if (uniqueIdIn.equals(oldUniqueId)) {
            return; // No change do nothing
        }

        super.setUUID(uniqueIdIn);

        //It doesn't make sense updating a dangling dog.
        //The location data only persist if the dog is saved.
        //And when the dog joined the world, the object always being created
        //new and restore from nbt, so there won't be any case
        //where a dog object is removed from the world, change the UUID and then re-added back in.
        if (!this.isAddedToWorld()) return;

        if (this.level() != null && !this.level().isClientSide) {
            DogLocationStorage.get(this.level()).remove(oldUniqueId);
            DogLocationStorage.get(this.level()).getOrCreateData(this).update(this);
        }
    }

    @Override
    public void tame(Player player) {
        super.tame(player);
        // When tamed by player cache their display name
        this.setOwnersName(player.getName());
    }

    /**
     * This function actually contains a so called "bug" but i think it is more of 
     * a hidden feature in vanilla wolves/dogs. This function would always be called
     * when the dog is loaded from disk to memory to populate the java object. As you can
     * see setHealth(20.0f) is always called if the dog is tamed, so the result is that 
     * the dog health will always reset to 20.0f whether the owner :
     * <p>+ Quit the game and come back in singleplayer</p> 
     * <p>+ Unload the chunk and then come back</p> 
     * <p>+ Anything that involves reloading a dog in memory</p> 
     * <p>This is default behaviour and it is actually a hidden feature to help you
     * heals a large amount of dogs easier, as you can just leave that chunk and come back later
     * or in singleplayer... the classic restart the program :)) Which allows you to save your 
     * dog even if he is in LAVA :)</p>
     */
    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if (tamed) {
           this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
           this.maxHealth();
        } else {
           this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
     }

    public void maxHealth() {
        this.setHealth(this.getMaxHealth());
    }

    @Override
    public void setOwnerUUID(@Nullable UUID uuid) {
        super.setOwnerUUID(uuid);

        if (uuid == null) {
            this.setOwnersName((Component) null);
        }
    }

    @Override // blockAttackFromPlayer
    public boolean skipAttackInteraction(Entity entityIn) {

        boolean allPlayerCannotAttackDog = 
            ConfigHandler.ClientConfig.getConfig(ConfigHandler.SERVER.ALL_PLAYER_CANNOT_ATTACK_DOG);

        if (allPlayerCannotAttackDog && entityIn instanceof Player) {
            return true;
        } 

        if (!this.canOwnerAttack() 
            && this.checkIfAttackedFromOwnerOrTeam(this.getOwner(), entityIn)) {
            return true;
        }

        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.shouldSkipAttackFrom(this, entityIn);

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }

        return false;
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(DoggyItems.DOGGY_CHARM.get());
    }

    @Override
    public boolean isFood(ItemStack stack) {
        //Only authorized breeding!
        return false;
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        if (this.isDefeated()) return false;

        if (otherAnimal == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(otherAnimal instanceof Dog)) {
            return false;
        } else {
            Dog entitydog = (Dog) otherAnimal;
            if (!entitydog.isTame()) {
                return false;
            } else if (entitydog.isInSittingPose()) {
                return false;
            } else if (ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.DOG_GENDER) && !this.getGender().canMateWith(entitydog.getGender())) {
                return false;
            } else {
                return !entitydog.isDefeated() && this.isInLove() && entitydog.isInLove();
            }
        }
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel worldIn, AgeableMob partner) {
        Dog child = DoggyEntityTypes.DOG.get().create(worldIn);
        UUID uuid = this.getOwnerUUID();

        if (uuid != null) {
            child.setOwnerUUID(uuid);
            child.setTame(true);
        }

        if (partner instanceof Dog && ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.PUPS_GET_PARENT_LEVELS)) {
            child.setLevel(this.getDogLevel().combine(((Dog) partner).getDogLevel()));
        }

        return child;
    }

    @Override
    public boolean shouldShowName() {
        return (ConfigHandler.ALWAYS_SHOW_DOG_NAME && this.hasCustomName()) || super.shouldShowName();
    }

    @Override
    public float getScale() {
        if (this.isBaby()) {
            return 0.5F;
        } else {
            return this.getDogSize().getScale();
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        // Any mod that tries to access capabilities from entity size/entity
        // creation event will crash here because of the order java inits the
        // classes fields and so will not have been initialised and are
        // accessed during the classes super() call.
        // Since this.alterations will be empty anyway as we have not read
        // NBT data at this point just avoid silent error
        // DoggyTalents#295, DoggyTalents#296
        if (this.alterations == null) {
            return super.getCapability(cap, side);
        }

        for (IDogAlteration alter : this.alterations) {
            LazyOptional<T> result = alter.getCapability(this, cap, side);

            if (result != null) {
                return result;
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public Entity changeDimension(ServerLevel worldIn, ITeleporter teleporter) {
        boolean flag =
            ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.ALL_DOG_BLOCK_PORTAL);
        if (flag) return null;
        Entity transportedEntity = super.changeDimension(worldIn, teleporter);
        if (transportedEntity instanceof Dog) {
            DogLocationStorage.get(this.level()).getOrCreateData(this).update((Dog) transportedEntity);
        }
        return transportedEntity;
    }

    @Override
    public void onRemovedFromWorld() {
        if (this.level() instanceof ServerLevel serverLevel && this.isAlive()) {
            //Force location update when the dog is about to get untracked from world.
            //To be sure, only update existing data and when the dog is still living.
            var data = DogLocationStorage.get(serverLevel).getData(this);
            
            if (data != null) data.update(this);
        }
        super.onRemovedFromWorld();
        for (var x : this.alterations) {
            x.remove(this);
        }
    }

    @Override
    public void onAddedToWorld() {
        if (this.level() instanceof ServerLevel serverLevel && this.isAlive()) {
            var data = DogLocationStorage.get(serverLevel).getOrCreateData(this);
            
            if (data != null) data.update(this);
        }
        super.onAddedToWorld();
    }

    @Override
    public void remove(Entity.RemovalReason removalReason) {
        if (removalReason == RemovalReason.DISCARDED || removalReason == RemovalReason.KILLED) {
            if (this.level() != null && !this.level().isClientSide) {                
                DogLocationStorage.get(this.level()).remove(this);
                if (this.getOwnerUUID() != null)
                    DogRespawnStorage.get(this.level()).putData(this);
            }
        }
        
        super.remove(removalReason);
    }
    
    private void startShaking() {
        if (this.isShaking) return; // don't shake if already shaking
        this.isShaking = true;
        this.shakeFire = false;
        this.timeWolfIsShaking = 0.0F;
        this.prevTimeWolfIsShaking = 0.0F;
    }

    /**
     * Force the dog to shake, only if the dog is not already shaking
     * and broadcast to clients
     * 
     * @param shakeFire
     */
    public void startShakingAndBroadcast(boolean shakeFire) {
        if (this.isShaking) return; //Already shaking
        if (this.level().isClientSide) return;
        if (shakeFire) {
            this.startShakingLava();
            ParticlePackets.DogStartShakingLavaPacket.sendDogStartShakingLavaPacketToNearByClients(this);
            return;
        }
        this.startShaking();
        this.level().broadcastEntityEvent(this, doggytalents.common.lib.Constants.EntityState.WOLF_START_SHAKING);
    }

    private void finishShaking() {
        this.isShaking = false;
        this.shakeFire = false;
        this.timeWolfIsShaking = 0.0F;
        this.prevTimeWolfIsShaking = 0.0F;
    }

    public void startShakingLava() {
        if (this.isShaking) return;
        this.isShaking = true;
        this.shakeFire = true;
        this.timeWolfIsShaking = 0.0F;
        this.prevTimeWolfIsShaking = 0.0F;
    }

    public boolean isShakingLava() {
        return this.isShaking && this.shakeFire;
    }

    public void resetBeggingRotation() {
        this.headRotationCourse = 0;
        this.headRotationCourseOld = 0;
    }

    @Override
    public void die(DamageSource cause) {
        if (checkAndHandleIncapacitated(cause))
            return;
        
        this.wetSource = null;
        this.finishShaking();

        this.alterations.forEach((alter) -> alter.onDeath(this, cause));
        super.die(cause);
    }

    private boolean checkAndHandleIncapacitated(DamageSource cause) {
        if (this.level().isClientSide)
            return false;
        if (!ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.IMMORTAL_DOGS)) 
            return false;
        if (cause.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
            return false;
        if (this.getOwnerUUID() == null) 
            return false;

        handleIncapacitated(cause);
        return true;
    }

    /**
     * in Incapacitated Mode, the dog hunger is rendered by the negative of 
        the maxIncapaciatedHunger's complement of the actuall hunger value.
        At first the hunger will be zero, when the dog enters Incapacitated.
        To exit incapacitated, the dog hunger have to reach maxIncapacitatedHunger.
     */
    private void handleIncapacitated(DamageSource source) {
        this.setHealth(1);
        this.setMode(EnumMode.INCAPACITATED);
        this.setDogHunger(0);
        this.unRide();
        createIncapSyncState(source);
        if (this.isInWater() || this.isInLava()) {
            this.triggerAnimationAction(new DogDrownAction(this));
        } else
        this.setAnim(this.incapacitatedMananger.getAnim());

        var owner = this.getOwner();
        if (owner != null) sendIncapacitatedMsg(owner, source);
        this.incapacitatedMananger.setIncapMsg(
            source.getLocalizedDeathMessage(this).getString()
        );
    }

    private void sendIncapacitatedMsg(LivingEntity owner, DamageSource source) {
        var msg = source.getLocalizedDeathMessage(this).copy();
        var genderStr = Component.translatable(this.getGender()
            .getUnlocalisedSubject()).getString();
        var msg005 = ". "
            + genderStr.substring(0, 1).toUpperCase()
            + genderStr.substring(1)
            + " ";
        var msg01 = Component.translatable(
            "dog.mode.incapacitated.msg.partition1",
            Component.literal(msg005),
            Component.translatable(EnumMode.INCAPACITATED.getUnlocalisedName())
            .withStyle(
                Style.EMPTY
                .withBold(true)
                .withColor(0xd60404)
            )
        );
    
        msg.append(msg01);
        owner.sendSystemMessage(msg);
    }

    private void createIncapSyncState(DamageSource source) {
        DefeatedType type;
        if (source.is(DamageTypeTags.IS_FIRE) || (this.isOnFire() && !this.fireImmune())) {
            type = DefeatedType.BURN;
        } else if (source.is(DamageTypes.MAGIC)) {
            type = DefeatedType.POISON;
        } else if (source.is(DamageTypes.DROWN))  {
            type = DefeatedType.DROWN;
        } else if (source.is(DamageTypes.STARVE))  {
            type = DefeatedType.STARVE;
        }  else {
            type = DefeatedType.BLOOD;
        }
        
        int poseId = this.getRandom().nextInt(2);
        
        this.setIncapSyncState(new IncapacitatedSyncState(type, BandaidState.NONE, poseId));
    }

    @Override
    public double getFluidJumpThreshold() {
        float defeated_threshold = this.isInLava() ? 0.9f : 1;
        return this.isDefeated() ? defeated_threshold : super.getFluidJumpThreshold();
    }

    @Override
    public void dropEquipment() {
        super.dropEquipment();

        this.alterations.forEach((alter) -> alter.dropInventory(this));
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.alterations.forEach((alter) -> alter.invalidateCapabilities(this));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        try {
            compound.putInt("freeze_anim", this.entityData.get(FREEZE_ANIM));
            compound.putLong("freeze_anim_time", this.entityData.get(FREEZE_ANIM_TIME));
            compound.putFloat("freeze_anim_yrot", this.getFreezeYRot());
        } catch (Exception e) {

        }

        ListTag talentList = new ListTag();
        List<TalentInstance> talents = this.getTalentMap();

        for (int i = 0; i < talents.size(); i++) {
            CompoundTag talentTag = new CompoundTag();
            talents.get(i).writeInstance(this, talentTag);
            talentList.add(talentTag);
        }

        compound.put("talents", talentList);

        ListTag accessoryList = new ListTag();
        List<AccessoryInstance> accessories = this.getAccessories();

        for (int i = 0; i < accessories.size(); i++) {
            CompoundTag accessoryTag = new CompoundTag();
            accessories.get(i).writeInstance(accessoryTag);
            accessoryList.add(accessoryTag);
        }

        compound.put("accessories", accessoryList);

        var artifactsListTag = new ListTag();
        var artifacts = this.getArtifactsList();
        for (var x : artifacts) {
            var artifactTag = DoggyArtifactItem.writeCompound(x);
            if (artifactTag == null) continue;
            artifactsListTag.add(artifactTag);
        }
        compound.put("doggy_artifacts", artifactsListTag);

        compound.putString("mode", this.getMode().getSaveName());
        compound.putString("dogGender", this.getGender().getSaveName());
        compound.putFloat("dogHunger", this.getDogHunger());
        this.getOwnersName().ifPresent((comp) -> {
            NBTUtil.putTextComponent(compound, "lastKnownOwnerName", comp);
        });

        this.getSkinData().save(compound);
        compound.putBoolean("willObey", this.willObeyOthers());
        compound.putBoolean("friendlyFire", this.canOwnerAttack());
        compound.putBoolean("regardTeamPlayers", this.regardTeamPlayers());
        compound.putBoolean("forceSit", this.forceSit());
        compound.putByte("lowHealthStrategy", this.getLowHealthStrategy().getId());
        compound.putBoolean("crossOriginTp", this.crossOriginTp());
        compound.putInt("dogSize", this.getDogSize().getId());
        compound.putInt("level_normal", this.getDogLevel().getLevel(Type.NORMAL));
        compound.putInt("level_dire", this.getDogLevel().getLevel(Type.DIRE));
        NBTUtil.writeItemStack(compound, "fetchItem", this.getBoneVariant());

        DimensionDependantArg<Optional<BlockPos>> bedsData = this.entityData.get(DOG_BED_LOCATION.get());

        if (!bedsData.isEmpty()) {
            ListTag bedsList = new ListTag();

            for (Entry<ResourceKey<Level>, Optional<BlockPos>> entry : bedsData.entrySet()) {
                CompoundTag bedNBT = new CompoundTag();
                NBTUtil.putResourceLocation(bedNBT, "dim", entry.getKey().location());
                NBTUtil.putBlockPos(bedNBT, "pos", entry.getValue());
                bedsList.add(bedNBT);
            }

            compound.put("beds", bedsList);
        }

        DimensionDependantArg<Optional<BlockPos>> bowlsData = this.entityData.get(DOG_BOWL_LOCATION.get());

        if (!bowlsData.isEmpty()) {
            ListTag bowlsList = new ListTag();

            for (Entry<ResourceKey<Level>, Optional<BlockPos>> entry : bowlsData.entrySet()) {
                CompoundTag bowlsNBT = new CompoundTag();
                NBTUtil.putResourceLocation(bowlsNBT, "dim", entry.getKey().location());
                NBTUtil.putBlockPos(bowlsNBT, "pos", entry.getValue());
                bowlsList.add(bowlsNBT);
            }

            compound.put("bowls", bowlsList);
        }

        this.statsTracker.writeAdditional(compound);
        this.dogOwnerDistanceManager.save(compound);

        this.alterations.forEach((alter) -> alter.onWrite(this, compound));

        this.dogGroupsManager.save(compound);
        if (this.isDefeated()) 
            this.incapacitatedMananger.save(compound);

        //Never save these entry, these will be loaded by the talents itself.
        compound.remove("HandItems");
        compound.remove("ArmorItems");
    }

    @Override
    public void load(CompoundTag compound) {

        // DataFix uuid entries and attribute ids
        try {
            if (NBTUtil.hasOldUniqueId(compound, "UUID")) {
                UUID entityUUID = NBTUtil.getOldUniqueId(compound, "UUID");

                compound.putUUID("UUID", entityUUID);
                NBTUtil.removeOldUniqueId(compound, "UUID");
            }

            if (compound.contains("OwnerUUID", Tag.TAG_STRING)) {
                UUID ownerUUID = UUID.fromString(compound.getString("OwnerUUID"));

                compound.putUUID("Owner", ownerUUID);
                compound.remove("OwnerUUID");
            } else if (compound.contains("Owner", Tag.TAG_STRING)) {
                UUID ownerUUID = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), compound.getString("Owner"));

                compound.putUUID("Owner", ownerUUID);
            }

            if (NBTUtil.hasOldUniqueId(compound, "LoveCause")) {
                UUID entityUUID = NBTUtil.getOldUniqueId(compound, "LoveCause");

                compound.putUUID("LoveCause", entityUUID);
                NBTUtil.removeOldUniqueId(compound, "LoveCause");
            }
        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.error("Failed to data fix UUIDs: " + e.getMessage());
        }

        try {
            if (compound.contains("Attributes", Tag.TAG_LIST)) {
                ListTag attributeList = compound.getList("Attributes", Tag.TAG_COMPOUND);
                for (int i = 0; i < attributeList.size(); i++) {
                    CompoundTag attributeData = attributeList.getCompound(i);
                    String namePrev = attributeData.getString("Name");
                    Object name = namePrev;

                    switch (namePrev) {
                    case "forge.swimSpeed": name = ForgeMod.SWIM_SPEED; break;
                    case "forge.nameTagDistance": name = ForgeMod.NAMETAG_DISTANCE; break;
                    case "forge.entity_gravity": name = ForgeMod.ENTITY_GRAVITY; break;
                    case "forge.reachDistance": name = ForgeMod.BLOCK_REACH; break;
                    case "generic.maxHealth": name = Attributes.MAX_HEALTH; break;
                    case "generic.knockbackResistance": name = Attributes.KNOCKBACK_RESISTANCE; break;
                    case "generic.movementSpeed": name = Attributes.MOVEMENT_SPEED; break;
                    case "generic.armor": name = Attributes.ARMOR; break;
                    case "generic.armorToughness": name = Attributes.ARMOR_TOUGHNESS; break;
                    case "generic.followRange": name = Attributes.FOLLOW_RANGE; break;
                    case "generic.attackKnockback": name = Attributes.ATTACK_KNOCKBACK; break;
                    case "generic.attackDamage": name = Attributes.ATTACK_DAMAGE; break;
                    case "generic.jumpStrength": name = DoggyAttributes.JUMP_POWER; break;
                    case "generic.critChance": name = DoggyAttributes.CRIT_CHANCE; break;
                    case "generic.critBonus": name = DoggyAttributes.CRIT_BONUS; break;
                    }

                    ResourceLocation attributeRL = Util.getRegistryId(name);

                    if (attributeRL != null && ForgeRegistries.ATTRIBUTES.containsKey(attributeRL)) {
                        attributeData.putString("Name", attributeRL.toString());
                        ListTag modifierList = attributeData.getList("Modifiers", Tag.TAG_COMPOUND);
                        for (int j = 0; j < modifierList.size(); j++) {
                            CompoundTag modifierData = modifierList.getCompound(j);
                            if (NBTUtil.hasOldUniqueId(modifierData, "UUID")) {
                                UUID entityUUID = NBTUtil.getOldUniqueId(modifierData, "UUID");

                                modifierData.putUUID("UUID", entityUUID);
                                NBTUtil.removeOldUniqueId(modifierData, "UUID");
                            }
                        }
                    } else {
                        DoggyTalentsNext.LOGGER.warn("Failed to data fix '" + namePrev + "'");
                    }
                }
            }
        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.error("Failed to data fix attribute IDs: " + e.getMessage());
        }

        super.load(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        try {
            this.entityData.set(FREEZE_ANIM, compound.getInt("freeze_anim"));
            this.entityData.set(FREEZE_ANIM_TIME, compound.getLong("freeze_anim_time"));
            this.setFreezeYRot(compound.getFloat("freeze_anim_yrot"));
        } catch (Exception e) {
            
        }

        var newTlInstLs = new ArrayList<TalentInstance>();

        if (compound.contains("talents", Tag.TAG_LIST)) {
            ListTag talentList = compound.getList("talents", Tag.TAG_COMPOUND);

            for (int i = 0; i < talentList.size(); i++) {
                // Add directly so that nothing is lost, if number allowed on changes
                TalentInstance.readInstance(this, talentList.getCompound(i)).ifPresent(newTlInstLs::add);
            }
        } else {
            // Try to read old talent format if new one doesn't exist
            BackwardsComp.readTalentMapping(compound, newTlInstLs);
        }

        //this.markDataParameterDirty(TALENTS.get(), false); // Mark dirty so data is synced to client
        this.entityData.set(TALENTS.get(), newTlInstLs);

        var newAccInstLs = new ArrayList<AccessoryInstance>();

        if (compound.contains("accessories", Tag.TAG_LIST)) {
            ListTag accessoryList = compound.getList("accessories", Tag.TAG_COMPOUND);

            for (int i = 0; i < accessoryList.size(); i++) {
                // Add directly so that nothing is lost, if number allowed on changes
                AccessoryInstance.readInstance(accessoryList.getCompound(i)).ifPresent(newAccInstLs::add);
            }
        } else {
            // Try to read old accessories from their individual format
            BackwardsComp.readAccessories(compound, newAccInstLs);
        }

        //this.markDataParameterDirty(ACCESSORIES.get(), false); // Mark dirty so data is synced to client
        this.entityData.set(ACCESSORIES.get(), newAccInstLs);

        var artifactsList = new ArrayList<DoggyArtifactItem>(3);
        if (compound.contains("doggy_artifacts", Tag.TAG_LIST)) {
            var artifactsListTag = compound.getList("doggy_artifacts", Tag.TAG_COMPOUND);
            for (int i = 0; i < artifactsListTag.size(); ++i) {
                var artifactItem = DoggyArtifactItem.readCompound(
                    artifactsListTag.getCompound(i));
                if (artifactItem != null) {
                    artifactsList.add(artifactItem);
                }
            }
        }
        this.entityData.set(ARTIFACTS.get(), artifactsList);

        try {
            // Does what notifyDataManagerChange would have done but this way only does it once
            this.recalculateAlterations();
            this.spendablePoints.markForRefresh();
        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.error("Failed to init alteration: " + e.getMessage());
            e.printStackTrace();
		}

        try {
            this.setGender(EnumGender.bySaveName(compound.getString("dogGender")));

            if (compound.contains("mode", Tag.TAG_STRING)) {
            this.setMode(EnumMode.bySaveName(compound.getString("mode")));
            } else {
                // Read old mode id
                BackwardsComp.readMode(compound, this::setMode);
            }

            var dogSkinData = DogSkinData.readFromTag(compound);
            this.setDogSkinData(dogSkinData);

            if (compound.contains("fetchItem", Tag.TAG_COMPOUND)) {
                this.setBoneVariant(NBTUtil.readItemStack(compound, "fetchItem"));
            } else {
                BackwardsComp.readHasBone(compound, this::setBoneVariant);
            }

            this.setHungerDirectly(compound.getFloat("dogHunger"));
            this.setOwnersName(NBTUtil.getTextComponent(compound, "lastKnownOwnerName"));
            this.setWillObeyOthers(compound.getBoolean("willObey"));
            this.setCanPlayersAttack(compound.getBoolean("friendlyFire"));
            this.setRegardTeamPlayers(compound.getBoolean("regardTeamPlayers"));
            this.setForceSit(compound.getBoolean("forceSit"));
            this.setCrossOriginTp(compound.getBoolean("crossOriginTp")); 
            var low_health_strategy_id = compound.getByte("lowHealthStrategy");
            this.setLowHealthStrategy(LowHealthStrategy.fromId(low_health_strategy_id));
            if (compound.contains("dogSize", Tag.TAG_ANY_NUMERIC)) {
                this.setDogSize(DogSize.fromId(compound.getInt("dogSize")));
            }
        } catch (Exception e) {
            //TODO What?
            DoggyTalentsNext.LOGGER.error("Failed to load levels: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            int level_normal = 0;
            int level_dire = 0;
            if (compound.contains("level_normal", Tag.TAG_ANY_NUMERIC)) {
                level_normal = compound.getInt("level_normal");
            }
            if (compound.contains("level_dire", Tag.TAG_ANY_NUMERIC)) {
                level_dire = compound.getInt("level_dire");          
            }
            this.entityData.set(DOG_LEVEL.get(), new DogLevel(level_normal, level_dire));
            float h = this.getDogLevel().getMaxHealth();
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(h);
            this.maxHealth();
        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.error("Failed to load levels: " + e.getMessage());
            e.printStackTrace();
        }

        DimensionDependantArg<Optional<BlockPos>> bedsData = this.entityData.get(DOG_BED_LOCATION.get()).copyEmpty();

        try {
            if (compound.contains("beds", Tag.TAG_LIST)) {
                ListTag bedsList = compound.getList("beds", Tag.TAG_COMPOUND);

                for (int i = 0; i < bedsList.size(); i++) {
                    CompoundTag bedNBT = bedsList.getCompound(i);
                    ResourceLocation loc = NBTUtil.getResourceLocation(bedNBT, "dim");
                    ResourceKey<Level> type = ResourceKey.create(Registries.DIMENSION, loc);
                    Optional<BlockPos> pos = NBTUtil.getBlockPos(bedNBT, "pos");
                    bedsData.put(type, pos);
                }
            } else {
                BackwardsComp.readBedLocations(compound, bedsData);
            }
        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.error("Failed to load beds: " + e.getMessage());
            e.printStackTrace();
        }

        this.entityData.set(DOG_BED_LOCATION.get(), bedsData);

        DimensionDependantArg<Optional<BlockPos>> bowlsData = this.entityData.get(DOG_BOWL_LOCATION.get()).copyEmpty();

        try {
            if (compound.contains("bowls", Tag.TAG_LIST)) {
                ListTag bowlsList = compound.getList("bowls", Tag.TAG_COMPOUND);

                for (int i = 0; i < bowlsList.size(); i++) {
                    CompoundTag bowlsNBT = bowlsList.getCompound(i);
                    ResourceLocation loc = NBTUtil.getResourceLocation(bowlsNBT, "dim");
                    ResourceKey<Level> type = ResourceKey.create(Registries.DIMENSION, loc);
                    Optional<BlockPos> pos = NBTUtil.getBlockPos(bowlsNBT, "pos");
                    bowlsData.put(type, pos);
                }
            } else {
                BackwardsComp.readBowlLocations(compound, bowlsData);
            }
        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.error("Failed to load bowls: " + e.getMessage());
            e.printStackTrace();
        }

        this.entityData.set(DOG_BOWL_LOCATION.get(), bowlsData);

        try {
            this.statsTracker.readAdditional(compound);
        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.error("Failed to load stats tracker: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            this.dogOwnerDistanceManager.load(compound);
        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.error("Failed to load owner distance manager: " + e.getMessage());
            e.printStackTrace();
        }
        this.alterations.forEach((alter) -> {
            try {
                alter.onRead(this, compound);
            } catch (Exception e) {
                DoggyTalentsNext.LOGGER.error("Failed to load alteration: " + e.getMessage());
                e.printStackTrace();
            }
        });

        try {
            this.dogGroupsManager.load(compound);
            if (this.isDefeated())
            this.incapacitatedMananger.load(compound);
        } catch (Exception e) {

        }

    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (TALENTS.get().equals(key) || ACCESSORIES.get().equals(key) 
            || ARTIFACTS.get().equals(key)) {
            this.recalculateAlterations();
        }

        if (TALENTS.get().equals(key)) {
            this.spendablePoints.markForRefresh();
        }

        if (DOG_LEVEL.get().equals(key)) {
            this.spendablePoints.markForRefresh();
        }

        if (ACCESSORIES.get().equals(key)) {
            // If client sort accessories
            if (this.level().isClientSide) {
                // Does not recall this notifyDataManagerChange as list object is
                // still the same, maybe in future MC versions this will change so need to watch out
                this.clientAccessories = new ArrayList<>(this.getAccessories());
                this.clientAccessories.sort(AccessoryInstance.RENDER_SORTER);
                //this.getAccessories().sort(AccessoryInstance.RENDER_SORTER);
            }
        }

        if (DOG_SIZE.get().equals(key)) {
            this.refreshDimensions();
        }

        if (this.level().isClientSide && CUSTOM_SKIN.get().equals(key)) {
            this.setClientSkin(
                DogTextureManager.INSTANCE
                    .getDogSkin(
                        this.getSkinData().getHash()));
        }

        if (ANIMATION.equals(key)) {
            this.animationManager.onAnimationChange(getAnim());
        }

        if (!this.level().isClientSide && MODE.get().equals(key)) {
            var mode = getMode();
            this.incapacitatedMananger.onModeUpdate(mode);
            if (mode == EnumMode.INCAPACITATED) {
                this.removeAttributeModifier(Attributes.MOVEMENT_SPEED, HUNGER_MOVEMENT);
            }
        }

        if (FREEZE_ANIM.equals(key)) {
            this.freezeAnim = DogAnimation.byId(this.entityData.get(FREEZE_ANIM));
        }
    }

    public void recalculateAlterations() {
        //safely remove all alterations
        for (var inst : this.alterations) {
            inst.remove(this);
        }
        this.alterations.clear();
        this.foodHandlers.clear();

        for (AccessoryInstance inst : this.getAccessories()) {
            if (inst instanceof IDogAlteration) {
                this.alterations.add((IDogAlteration) inst);
            }

            if (inst instanceof IDogFoodHandler) {
                this.foodHandlers.add((IDogFoodHandler) inst);
            }
        };

        List<TalentInstance> talents = this.getTalentMap();
        this.alterations.addAll(talents);
        for (TalentInstance inst : talents) {
            if (inst instanceof IDogFoodHandler) {
                this.foodHandlers.add((IDogFoodHandler) inst);
            }
        }

        var artifacts = this.getArtifactsList();
        for (var artifactItem : artifacts) {
            var artifact = artifactItem.createArtifact();
            this.alterations.add(artifact);
        }

        for (var inst : this.alterations) {
            inst.init(this);
        }
    }

    /**
     * If the entity can make changes to the dog
     * @param livingEntity The entity
     */
    @Override
    public boolean canInteract(LivingEntity livingEntity) {
        if (!this.isDoingFine()) return false;
        return this.willObeyOthers() || this.isOwnedBy(livingEntity);
    }

    @Override
    public List<AccessoryInstance> getAccessories() {
        return this.entityData.get(ACCESSORIES.get());
    }

    @Override
    public boolean addAccessory(@Nonnull AccessoryInstance accessoryInst) {
        List<AccessoryInstance> accessories = this.getAccessories();
        AccessoryType type = accessoryInst.getAccessory().getType();

        // Gets accessories of the same type
        List<AccessoryInstance> filtered = accessories.stream().filter((inst) -> {
            return type == inst.getAccessory().getType();
        }).collect(Collectors.toList());

//        while (!filtered.isEmpty() && filtered.size() >= type.numberToPutOn()) {
//            accessories.remove(filtered.get(0));
//            filtered.remove(0);
//        }

        if (filtered.size() >= type.numberToPutOn()) {
            return false;
        }

        this.modifyAccessory(x -> {
            x.add(accessoryInst);
        });

        return true;
    }

    @Override
    public List<AccessoryInstance> removeAccessories() {
        List<AccessoryInstance> removed = new ArrayList<>(this.getAccessories());

        this.modifyAccessory(x -> {
            x.clear();
        });
        return removed;
    }

    public Optional<AccessoryInstance> getAccessory(AccessoryType typeIn) {
        List<AccessoryInstance> accessories = this.getAccessories();

        for (AccessoryInstance inst : accessories) {
            if (inst.getAccessory().getType() == typeIn) {
                return Optional.of(inst);
            }
        }

        return Optional.empty();
    }

    public Optional<AccessoryInstance> getAccessory(Accessory typeIn) {
        List<AccessoryInstance> accessories = this.getAccessories();

        for (AccessoryInstance inst : accessories) {
            if (inst.getAccessory() == typeIn) {
                return Optional.of(inst);
            }
        }

        return Optional.empty();
    }

    public Optional<Component> getOwnersName() {
        return this.entityData.get(LAST_KNOWN_NAME);
    }

    public void setOwnersName(@Nullable Component comp) {
        this.setOwnersName(Optional.ofNullable(comp));
    }

    public void setOwnersName(Optional<Component> collar) {
        this.entityData.set(LAST_KNOWN_NAME, collar);
    }

    public EnumGender getGender() {
        return this.entityData.get(GENDER.get());
    }

    public void setGender(EnumGender collar) {
        this.entityData.set(GENDER.get(), collar);
    }

    @Override
    public EnumMode getMode() {
        return this.entityData.get(MODE.get());
    }

    public boolean isMode(EnumMode... modes) {
        EnumMode mode = this.getMode();
        for (EnumMode test : modes) {
            if (mode == test) {
                return true;
            }
        }

        return false;
    }

    public void setMode(EnumMode collar) {
        this.entityData.set(MODE.get(), collar);
    }

    public Optional<BlockPos> getBedPos() {
        return this.getBedPos(this.level().dimension());
    }

    public Optional<BlockPos> getBedPos(ResourceKey<Level> registryKey) {
        return this.entityData.get(DOG_BED_LOCATION.get()).getOrDefault(registryKey, Optional.empty());
    }

    public void setBedPos(@Nullable BlockPos pos) {
        this.setBedPos(this.level().dimension(), pos);
    }

    public void setBedPos(ResourceKey<Level> registryKey, @Nullable BlockPos pos) {
        this.setBedPos(registryKey, WorldUtil.toImmutable(pos));
    }

    public void setBedPos(ResourceKey<Level> registryKey, Optional<BlockPos> pos) {
        this.entityData.set(DOG_BED_LOCATION.get(), this.entityData.get(DOG_BED_LOCATION.get()).copy().set(registryKey, pos));
    }

    public Optional<BlockPos> getBowlPos() {
        return this.getBowlPos(this.level().dimension());
    }

    public Optional<BlockPos> getBowlPos(ResourceKey<Level> registryKey) {
        return this.entityData.get(DOG_BOWL_LOCATION.get()).getOrDefault(registryKey, Optional.empty());
    }

    public void setBowlPos(@Nullable BlockPos pos) {
        this.setBowlPos(this.level().dimension(), pos);
    }

    public void setBowlPos(ResourceKey<Level> registryKey, @Nullable BlockPos pos) {
        this.setBowlPos(registryKey, WorldUtil.toImmutable(pos));
    }

    public void setBowlPos(ResourceKey<Level> registryKey, Optional<BlockPos> pos) {
        this.entityData.set(DOG_BOWL_LOCATION.get(), this.entityData.get(DOG_BOWL_LOCATION.get()).copy().set(registryKey, pos));
    }

    @Override
    public float getMaxHunger() {
        float maxHunger = ConfigHandler.DEFAULT_MAX_HUNGER;

        for (IDogAlteration alter : this.alterations) {
            InteractionResultHolder<Float> result = alter.getMaxHunger(this, maxHunger);

            if (result.getResult().shouldSwing()) {
                maxHunger = result.getObject();
            }
        }

        return maxHunger;
    }

    @Override
    public float getDogHunger() {
        return this.entityData.get(HUNGER_INT);
    }

    @Override
    public void addHunger(float add) {
        var h0 = this.getDogHunger();
        var h1 = h0 + add;
        var h2 = (int) (h1 - this.getMaxHunger());
        if (h2 > 0) {
            this.hungerSaturation = h2;
        }
        this.setDogHunger(h0 + add);
    }

    @Override
    public void setDogHunger(float hunger) {
        float diff = hunger - this.getDogHunger();

        for (IDogAlteration alter : this.alterations) {
            InteractionResultHolder<Float> result = alter.setDogHunger(this, hunger, diff);

            if (result.getResult().shouldSwing()) {
                hunger = result.getObject();
                diff = hunger - this.getDogHunger();
            }
        }

        this.setHungerDirectly(Mth.clamp(hunger, 0, this.getMaxHunger()));
    }

    private void setHungerDirectly(float hunger) {
        this.entityData.set(HUNGER_INT, hunger);
        this.updateLowHunger();
    }

    public boolean hasCustomSkin() {
        return !Strings.isNullOrEmpty(this.getSkinData().getHash());
    }

    public DogSkinData getSkinData () {
        return this.entityData.get(CUSTOM_SKIN.get());
    }

    public void setDogSkinData(DogSkinData data) {
        if (data == null) {
            data = DogSkinData.NULL;
        }
        this.entityData.set(CUSTOM_SKIN.get(), data);
    }

    @Override
    public DogLevel getDogLevel() {
        return this.entityData.get(DOG_LEVEL.get());
    }

    public void setLevel(DogLevel level) {
        this.entityData.set(DOG_LEVEL.get(), level);
    }

    public IncapacitatedSyncState getIncapSyncState() {
        return this.entityData.get(DOG_INCAP_SYNC_STATE.get());
    }

    public void setIncapSyncState(IncapacitatedSyncState state) {
        this.entityData.set(DOG_INCAP_SYNC_STATE.get(), state);
    }

    @Override
    public void increaseLevel(DogLevel.Type typeIn) {
        var copy = this.getDogLevel().copy();
        copy.incrementLevel(typeIn);
        this.setLevel(copy);
    }

    @Override
    public void setDogSize(DogSize size) {
        this.entityData.set(DOG_SIZE.get(), size);
    }

    @Override
    public DogSize getDogSize() {
        return this.entityData.get(DOG_SIZE.get());
    }

    @Override
    public boolean isBaby() {
        if (this.getDogSize() == DogSize.PPP)
            return true;
        return super.isBaby();
    }

    public void setBoneVariant(ItemStack stack) {
        this.entityData.set(BONE_VARIANT, stack);
    }

    public ItemStack getBoneVariant() {
        return this.entityData.get(BONE_VARIANT);
    }

    @Nullable
    public IThrowableItem getThrowableItem() {
        Item item = this.entityData.get(BONE_VARIANT).getItem();
        return item instanceof IThrowableItem ? (IThrowableItem) item : null;
    }

    public boolean hasBone() {
        return !this.getBoneVariant().isEmpty();
    }

    private boolean getDogFlag(int bit) {
        return (this.entityData.get(DOG_FLAGS) & bit) != 0;
    }

    private void setDogFlag(int bits, boolean flag) {
        int c = this.entityData.get(DOG_FLAGS);
        this.entityData.set(DOG_FLAGS, (flag ? c | bits : c & ~bits));
    }

    public void setBegging(boolean begging) {
        this.setDogFlag(1, begging);
    }

    public boolean isBegging() {
        return this.getDogFlag(1);
    }

    public void setWillObeyOthers(boolean obeyOthers) {
        this.setDogFlag(2, obeyOthers);
    }

    public boolean willObeyOthers() {
        return this.getDogFlag(2);
    }

    public void setCanPlayersAttack(boolean flag) {
        this.setDogFlag(4, flag);
    }

    public boolean canOwnerAttack() {
        return this.getDogFlag(4);
    }

    public void setForceSit(boolean val) {
        this.setDogFlag(8, val);
    }

    public boolean forceSit() {
        return this.getDogFlag(8);
    }
    
    public LowHealthStrategy getLowHealthStrategy() {
        int msb = this.getDogFlag(32) ? 1 : 0;
        int lsb = this.getDogFlag(16) ? 1 : 0;
        return LowHealthStrategy.fromId(msb * 2 + lsb);
    }

    public void setLowHealthStrategy(LowHealthStrategy strategy) {
        int id = strategy.getId();
        boolean lsb = (id & 1) == 1;
        boolean msb = ((id >> 1) & 1) == 1;
        this.setDogFlag(32, msb);
        this.setDogFlag(16, lsb);
    }

    public void setRegardTeamPlayers(boolean val) {
        this.setDogFlag(128, val);
    }

    public boolean regardTeamPlayers() {
        return this.getDogFlag(128);
    }

    public boolean crossOriginTp() {
        return this.getDogFlag(64);
    }

    public void setCrossOriginTp(boolean val) {
        this.setDogFlag(64, val);
    }

    public boolean resting() {
        return this.getDogFlag(256);
    }

    public void setResting(boolean val) {
        this.setDogFlag(256, val);
    }

    public boolean wantsToRest() {
        return this.tickUntilRest <= 0 && this.getRandom().nextFloat() < 0.02f;
    }

    public void resetTickTillRest() {
        this.tickUntilRest = 30 * 20 + this.getRandom().nextInt(271) * 20; 
    }

    public List<TalentInstance> getTalentMap() {
        return this.entityData.get(TALENTS.get());
    }

    public void setTalentMap(List<TalentInstance> map) {
        this.entityData.set(TALENTS.get(), map);
    }

    public InteractionResult setTalentLevel(Talent talent, int level) {
        if (0 > level || level > talent.getMaxLevel()) {
            return InteractionResult.FAIL;
        }

        var activeTalents = this.getTalentMap();

        TalentInstance inst = null;
        int selected_id = -1;
        for (int i = 0; i < activeTalents.size(); ++i) {
            var activeInst = activeTalents.get(i);
            if (activeInst.of(talent)) {
                inst = activeInst;
                selected_id = i;
                break;
            }
        }

        if (inst == null) {
            if (level == 0) {
                return InteractionResult.PASS;
            }

            this.modifyTalent(x -> {
                var newTalent = talent.getDefault(level);
                x.add(newTalent);
                newTalent.init(this);
            });            
        } else {
            int previousLevel = inst.level();
            if (previousLevel == level) {
                return InteractionResult.PASS;
            }

            inst.setLevel(level);
            inst.set(this, previousLevel);

            if (level <= 0) {
                inst.remove(this);
                final int remove_id = selected_id;
                modifyTalent(x -> {
                    if (remove_id >= 0) x.remove(remove_id);
                });
            } else {
                forceSyncTalents();
            }
        }
        return InteractionResult.SUCCESS;
    }

    public List<DoggyArtifactItem> getArtifactsList() {
        var array = this.entityData.get(ARTIFACTS.get());
        return array;
    }

    public boolean addArtifact(DoggyArtifactItem artifact) {
        if (artifact == null)
            return false;
        var array = this.getArtifactsList();
        if (array.size() >= DoggyArtifact.ARTIFACTS_LIMIT) {
            return false;
        }
        if (array.contains(artifact)) 
            return false;
        this.modifyArtifact(artifacts -> {
            artifacts.add(artifact);
        });
        return true;
    }

    public ItemStack removeArtifact(int indx) {
        var array = this.getArtifactsList();
        if (indx < 0 || indx >= array.size())
            return null;
        var removedArtifact = array.get(indx);
        this.modifyArtifact(artifacts -> {
            artifacts.remove(indx);
        });
        return new ItemStack(removedArtifact);
    }

    public void forceSyncTalents() {
        var copy_list = new ArrayList<>(this.getTalentMap());
        this.getTalentMap().clear();
        this.modifyTalent(x -> x.addAll(copy_list));
    }

    public void modifyTalent(Consumer<List<TalentInstance>> modify) {
        this.modifyListSyncedData(TALENTS.get(), modify);
    }

    public void modifyAccessory(Consumer<List<AccessoryInstance>> modify) {
        this.modifyListSyncedData(ACCESSORIES.get(), modify);
    }

    public void modifyArtifact(Consumer<List<DoggyArtifactItem>> modify) {
        this.modifyListSyncedData(ARTIFACTS.get(), modify);
    }

    public <T> void modifyListSyncedData(EntityDataAccessor<List<T>> key, Consumer<List<T>> modify) {
        modifySyncedData(key, modify, x -> new ArrayList<>(x));
    }

    public <T> void modifySyncedData(EntityDataAccessor<T> key, Consumer<T> modify, Function<T, T> copyFunc) {
        var result = copyFunc.apply(this.entityData.get(key));
        modify.accept(result);
        this.entityData.set(key, result);
    }

    @Override
    public Optional<TalentInstance> getTalent(Talent talentIn) {
        List<TalentInstance> activeTalents = this.getTalentMap();

        for (TalentInstance activeInst : activeTalents) {
            if (activeInst.of(talentIn)) {
                return Optional.of(activeInst);
            }
        }

        return Optional.empty();
    }

    @Override
    public int getDogLevel(Talent talentIn) {
        return this.getTalent(talentIn).map(TalentInstance::level).orElse(0);
    }

    @Override
    public <T> void setData(DataKey<T> key, T value) {
        if (key.isFinal() && this.hasData(key)) {
            throw new RuntimeException("Key is final but was tried to be set again.");
        }
        this.objects.put(key.getIndex(), value);
    }

    /**
     * Tries to put the object in the map, does nothing if the key already exists
     */
    @Override
    public <T> void setDataIfEmpty(DataKey<T> key, T value) {
        if (!this.hasData(key)) {
            this.objects.put(key.getIndex(), value);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getData(DataKey<T> key) {
        return (T) this.objects.get(key.getIndex());
    }

    @Override
    public <T> T getDataOrGet(DataKey<T> key, Supplier<T> other) {
        if (this.hasData(key)) {
            return this.getData(key);
        }
        return other.get();
    }

    @Override
    public <T> T getDataOrDefault(DataKey<T> key, T other) {
        if (this.hasData(key)) {
            return this.getData(key);
        }
        return other;
    }

    @Override
    public <T> boolean hasData(DataKey<T> key) {
        return this.objects.containsKey(key.getIndex());
    }

    @Override
    public void untame() {
        this.navigation.stop();
        this.clearTriggerableAction();
        this.goalSelector.getRunningGoals()
            .map(goal -> { goal.stop(); return goal; } );
        this.setOrderedToSit(false);
        this.setHealth(8);
        this.setCustomName(null);

        this.modifyTalent(x -> x.clear());

        this.setTame(false);
        this.setOwnerUUID(null);
        this.setWillObeyOthers(false);
        this.setCanPlayersAttack(true);
        this.setMode(EnumMode.DOCILE);
    }

    public void migrateOwner(UUID newOwnerUUID) {
        this.navigation.stop();
        this.clearTriggerableAction();
        this.goalSelector.getRunningGoals()
            .forEach(goal -> { goal.stop(); } );
        
        this.setMode(EnumMode.DOCILE);
        this.setOwnerUUID(newOwnerUUID);
    }

    public boolean canSpendPoints(int amount) {
        return this.getSpendablePoints() >= amount || this.getAccessory(DoggyAccessories.GOLDEN_COLLAR.get()).isPresent();
    }

    // When this method is changed the cache may need to be updated at certain points
    private final int getSpendablePointsInternal() {
        int totalPoints = 15 + this.getDogLevel().getLevel(Type.NORMAL) + this.getDogLevel().getLevel(Type.DIRE);
        for (TalentInstance entry : this.getTalentMap()) {
            totalPoints -= entry.getTalent().getCummulativeCost(entry.level());
        }
        return totalPoints;
    }

    public int getSpendablePoints() {
        return this.spendablePoints.get();
    }

    @Override
    public boolean canRiderInteract() {
        return true;
    }

    @Override
    public LivingEntity getControllingPassenger() {
        var passengers = this.getPassengers();
        if (passengers.isEmpty())
            return null;
        var first_passenger = passengers.get(0);
        if (!(first_passenger instanceof Player player))
            return null;
        return this.canInteract(player) ? player : null;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        var a1 = this.getYRot();
        var dx1 = -Mth.sin(a1*Mth.DEG_TO_RAD);
        var dz1 = Mth.cos(a1*Mth.DEG_TO_RAD);
        
        var newX = this.getX() + dx1;
        var newZ = this.getZ() + dz1;
        var newPos = new Vec3(newX, this.getY() + 0.5, newZ);
        var b0 = BlockPos.containing(newPos);
        var type = WalkNodeEvaluator.getBlockPathTypeStatic(this.level(), b0.mutable());
        if (type == BlockPathTypes.WALKABLE) {
            return newPos;
        }
        return super.getDismountLocationForPassenger(passenger);
    }

    // @Override
    // public boolean canBeControlledByRidxer() {
    //     this.isControlledByLocalInstance()
    //     return this.getControllingPassenger() instanceof LivingEntity;
    // }

    //TODO
    @Override
    public boolean isPickable() {
        if (this.level().isClientSide) {
            if (ClientEventHandler.shouldClientBlockPick(this))
                return false;
        }
        return super.isPickable();
    }

    @Override
    public boolean isPushable() {
        return !(this.isVehicle() && this.hasControllingPassenger()) && super.isPushable();
    }

    // @Override
    // public boolean isControlledByLocalInstance() {
    //     // Super calls canBeSteered so controlling passenger can be guaranteed to be LivingEntity
    //     return super.isControlledByLocalInstance() && this.canInteract((LivingEntity) this.getControllingPassenger());
    // }

    public boolean isDogJumping() {
        return this.dogJumping;
    }

    public void setDogJumping(boolean jumping) {
        this.dogJumping = jumping;
    }

//    public double getDogJumpStrength() {
//        float verticalVelocity = 0.42F + 0.06F * this.TALENTS.getLevel(ModTalents.WOLF_MOUNT);
//        if (this.TALENTS.getLevel(ModTalents.WOLF_MOUNT) == 5) verticalVelocity += 0.04F;
//        return verticalVelocity;
//    }

    // 0 - 100 input
    public void setJumpPower(int jumpPowerIn) {
       // if (this.TALENTS.getLevel(ModTalents.WOLF_MOUNT) > 0) {
            this.jumpPower = 1.0F;
       // }
    }

    public boolean canJump() {
        return true;
        //TODO return this.TALENTS.getLevel(ModTalents.WOLF_MOUNT) > 0;
    }

    @Override
    public void travel(Vec3 positionIn) {
        super.travel(positionIn);
        this.addMovementStat(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
    }

    // @Override
    // public void travel(Vec3 positionIn) {
    //     if (this.isAlive()) {
    //         if (this.isVehicle()) {
    //             LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();

    //             // Face the dog in the direction of the controlling passenger
    //             this.setYRot(livingentity.getYRot());
    //             this.yRotO = this.getYRot();
    //             this.setXRot(livingentity.getXRot() * 0.5F);
    //             this.setRot(this.getYRot(), this.getXRot());
    //             this.yBodyRot = this.getYRot();
    //             this.yHeadRot = this.yBodyRot;

    //             this.maxUpStep = 1.0F;

    //             float straf = livingentity.xxa * 0.7F;
    //             float forward = livingentity.zza;
    //             double downward = positionIn.y; 
                
    //             if (this.isInWater() && forward > 0 && this.canSwimUnderwater() ) {
    //                 float l = forward;
    //                 downward = -l*Mth.sin(this.getXRot() * ((float)Math.PI / 180F));
    //                 forward = l*Mth.cos(this.getXRot() * ((float)Math.PI / 180F));
    //             } 
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
    //             // If moving backwards half  the speed
    //             if (forward <= 0.0F) {
    //                forward *= 0.5F;
    //             } 

    //             if (this.jumpPower > 0.0F && !this.isDogJumping() && this.onGround()) {

    //                 // Calculate jump value based of jump strength, power this jump and jump boosts
    //                 double jumpValue = this.getAttribute(DoggyAttributes.JUMP_POWER.get()).getValue() * this.getBlockJumpFactor() * this.jumpPower; //TODO do we want getJumpFactor?
    //                 if (this.hasEffect(MobEffects.JUMP)) {
    //                     jumpValue += (this.getEffect(MobEffects.JUMP).getAmplifier() + 1) * 0.1F;
    //                 }

    //                 // Apply jump
    //                 Vec3 vec3d = this.getDeltaMovement();
    //                 this.setDeltaMovement(vec3d.x, jumpValue, vec3d.z);
    //                 this.setDogJumping(true);
    //                 this.hasImpulse = true;

    //                 // If moving forward, propel further in the direction
    //                 if (forward > 0.0F) {
    //                     final float amount = 0.4F; // TODO Allow people to change this value
    //                     float compX = Mth.sin(this.getYRot() * ((float)Math.PI / 180F));
    //                     float compZ = Mth.cos(this.getYRot() * ((float)Math.PI / 180F));
    //                     this.setDeltaMovement(this.getDeltaMovement().add(-amount * compX * this.jumpPower, 0.0D, amount * compZ * this.jumpPower));
    //                     //this.playJumpSound();
    //                 }

    //                 // Mark as unable jump until reset
    //                 this.jumpPower = 0.0F;
    //             }

    //             // Flying Speed is now being dynamically gotten.
    //             // with the exact same logic
    //             //this.flyingSpeed = this.getSpeed() * 0.1F;
    //             if (this.isControlledByLocalInstance()) {
    //                 // Set the move speed and move the dog in the direction of the controlling entity
    //                 this.setSpeed((float)this.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * 0.5F);
    //                 super.travel(new Vec3(straf, downward, forward));
    //                 this.lerpSteps = 0;
    //             } else if (livingentity instanceof Player) {
    //                 // A player is riding and can not control then
    //                 this.setDeltaMovement(Vec3.ZERO);
    //             }

    //             // Once the entity reaches the ground again allow it to jump again
    //             if (this.onGround()) {
    //                 this.jumpPower = 0.0F;
    //                 this.setDogJumping(false);
    //             }

    //             double changeX = this.getX() - this.xo;
    //             double changeY = this.getZ() - this.zo;
    //             float f4 = Mth.sqrt((float) (changeX * changeX + changeY * changeY)) * 4.0F;

    //             if (f4 > 1.0F) {
    //                f4 = 1.0F;
    //             }

    //             // // Same logic
    //             // this.animationSpeedOld = this.animationSpeed;
    //             // this.animationSpeed += (f4 - this.animationSpeed) * 0.4F;
    //             // this.animationPosition += this.animationSpeed;
    //             this.walkAnimation.update(f4, 0.4f);

    //             if (this.onClimbable()) {
    //                 this.fallDistance = 0.0F;
    //             }
    //          } else {
    //              this.maxUpStep = 0.5F; // Default
    //              // Flying Speed is now being dynamically gotten.
    //             // with the exact same logic
    //              //this.flyingSpeed = 0.02F; // Default
    //              super.travel(positionIn);
    //          }

            
    //     }
    // }

    @Override
    protected void tickRidden(Player rider, Vec3 rideVec) {

        if (this.isDefeated()) return;

        // Face the dog in the direction of the controlling passenger
        this.setYRot(rider.getYRot());
        this.yRotO = this.getYRot();
        this.setXRot(rider.getXRot() * 0.5F);
        this.setRot(this.getYRot(), this.getXRot());
        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.yBodyRot;
        
        checkAndJumpWhenBeingRidden(rider);

        if (this.onClimbable()) {
            this.fallDistance = 0.0F;
        }

        // TODO 1.19.4 ???? 
        // double changeX = this.getX() - this.xo;
        // double changeY = this.getZ() - this.zo;
        // float f4 = Mth.sqrt((float) (changeX * changeX + changeY * changeY)) * 4.0F;

        // if (f4 > 1.0F) {
        //     f4 = 1.0F;
        // }

        // // // Same logic
        // // this.animationSpeedOld = this.animationSpeed;
        // // this.animationSpeed += (f4 - this.animationSpeed) * 0.4F;
        // // this.animationPosition += this.animationSpeed;
        // this.walkAnimation.update(f4, 0.4f);

        this.addMovementStat(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new DogBodyRotationControl(this);
    }

    private void checkAndJumpWhenBeingRidden(LivingEntity rider) {
        float forward = rider.zza;

        if (this.jumpPower > 0) {
            if (this.isInWater() && this.canSwimUnderwater())
                this.doDogRideFloat();
            else if (!this.isDogJumping() && this.onGround())
                this.doDogRideJump(forward);
        }

        // Once the entity reaches the ground again allow it to jump again
        if (this.onGround()) {
            this.jumpPower = 0.0F;
            this.setDogJumping(false);
        } else {
            this.jumpPower = 0.0f;
        }
    }

    @Override
    protected Vec3 getRiddenInput(Player rider, Vec3 rideVec) {
        float straf = rider.xxa * 0.7F;
        float forward = rider.zza;
        double downward = rideVec.y; 
        
        if (this.isInWater() && forward > 0 && this.canSwimUnderwater() ) {
            float l = forward;
            downward = -l*Mth.sin(this.getXRot() * ((float)Math.PI / 180F));
            forward = l*Mth.cos(this.getXRot() * ((float)Math.PI / 180F));
        } 
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
        // If moving backwards half  the speed
        if (forward <= 0.0F) {
            forward *= 0.5F;
        }
        
        return new Vec3(straf, downward, forward);
    }

    @Override
    protected float getRiddenSpeed(Player rider) {
        return (float) this.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * 0.5f;
    }

    private void doDogRideFloat() {
        Vec3 vec3d = this.getDeltaMovement();
        this.setDeltaMovement(vec3d.x, 0.1, vec3d.z);
        //Consume
        this.jumpPower = 0.0F;
    }

    private void doDogRideJump(double forward) {
        // Calculate jump value based of jump strength, power this jump and jump boosts
        double jumpValue = this.getAttribute(DoggyAttributes.JUMP_POWER.get()).getValue() * this.getBlockJumpFactor() * this.jumpPower; //TODO do we want getJumpFactor?
        if (this.hasEffect(MobEffects.JUMP)) {
            jumpValue += (this.getEffect(MobEffects.JUMP).getAmplifier() + 1) * 0.1F;
        }

        // Apply jump
        Vec3 vec3d = this.getDeltaMovement();
        this.setDeltaMovement(vec3d.x, jumpValue, vec3d.z);
        this.setDogJumping(true);
        this.hasImpulse = true;

        // If moving forward, propel further in the direction
        if (forward > 0.0F) {
            final float amount = 0.4F; // TODO Allow people to change this value
            float compX = Mth.sin(this.getYRot() * ((float)Math.PI / 180F));
            float compZ = Mth.cos(this.getYRot() * ((float)Math.PI / 180F));
            this.setDeltaMovement(this.getDeltaMovement().add(-amount * compX * this.jumpPower, 0.0D, amount * compZ * this.jumpPower));
            //this.playJumpSound();
        }

        // Mark as unable jump until reset
        this.jumpPower = 0.0F;
    }

    public void addMovementStat(double xD, double yD, double zD) {
        if (this.isVehicle()) {
            int j = Math.round(Mth.sqrt((float) (xD * xD + zD * zD)) * 100.0F);
            this.statsTracker.increaseDistanceRidden(j);
        }
        if (!this.isPassenger()) {
            if (this.isEyeInFluid(FluidTags.WATER)) {
                int j = Math.round(Mth.sqrt((float) (xD * xD + yD * yD + zD * zD)) * 100.0F);
                if (j > 0) {
                    this.statsTracker.increaseDistanceOnWater(j);
                }
            } else if (this.isInWater()) {
                int k = Math.round(Mth.sqrt((float) (xD * xD + zD * zD)) * 100.0F);
                if (k > 0) {
                    this.statsTracker.increaseDistanceInWater(k);
                }
            } else if (this.onGround()) {
                int l = Math.round(Mth.sqrt((float) (xD * xD + zD * zD)) * 100.0F);
                if (l > 0) {
                    if (this.isSprinting()) {
                        this.statsTracker.increaseDistanceSprint(l);
                    } else if (this.isCrouching()) {
                        this.statsTracker.increaseDistanceSneaking(l);
                    } else {
                        this.statsTracker.increaseDistanceWalk(l);
                    }
                }
            } else { // Time in air
                int j1 = Math.round(Mth.sqrt((float) (xD * xD + zD * zD)) * 100.0F);
                //this.STATS.increaseDistanceInWater(k);
            }
        }
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        for (var alter : this.alterations) {
            InteractionResult result = alter.canResistPushFromFluidType(type);

            if (result.shouldSwing()) {
                return false;
            }
        }
        return super.isPushedByFluid(type);
    }

    @Override
    public MutableComponent getTranslationKey(Function<EnumGender, String> function) {
        return Component.translatable(function.apply(ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.DOG_GENDER) ? this.getGender() : EnumGender.UNISEX));
    }

    @Override
    public boolean isLying() {
        LivingEntity owner = this.getOwner();
        boolean ownerSleeping = owner != null && owner.isSleeping();
        if (ownerSleeping) {
            return true;
        }

        // The dog blockPos is still the same as the bed pos when the dog is sitting on it :v
        // Disable it for now.
        // BlockState blockBelow = this.level().getBlockState(this.blockPosition().below());
        // boolean onBed = blockBelow.is(DoggyBlocks.DOG_BED.get()) || blockBelow.is(BlockTags.BEDS);
        // if (onBed) {
        //     return true;
        // }

        if (this.isDefeated()) {
            BlockState blockBelow = this.level().getBlockState(this.blockPosition());
            boolean onBed = blockBelow.is(DoggyBlocks.DOG_BED.get()) || blockBelow.is(BlockTags.BEDS);
            if (onBed) {
                return true;
            }
        }

        return false;
    }


    private DogAnimation sitAnim = DogAnimation.SIT_DOWN;
    private DogAnimation standAnim = DogAnimation.STAND_QUICK;

    @Override
    public void setInSittingPose(boolean sit) {
        if (!this.level().isClientSide
            && !(this.animAction != null && this.animAction.blockSitStandAnim())) {
            boolean sit0 = this.isInSittingPose();
            if (sit0 != sit) {
                var anim = sit ? this.getSitAnim() : this.getStandAnim();
                if (!sit0 && this.isLying())
                    anim = DogAnimation.NONE;
                if (anim != DogAnimation.NONE)
                    this.setAnim(anim);
            }
            this.sitAnim = DogAnimation.SIT_DOWN;
            this.standAnim = DogAnimation.STAND_QUICK;
        }
        super.setInSittingPose(sit);
    }
    
    public void setSitAnim(DogAnimation anim) {
        if (anim == null) this.sitAnim = DogAnimation.SIT_DOWN;
        this.sitAnim = anim;
    }

    public DogAnimation getStandAnim() {
        return this.standAnim;
    }

    public void setStandAnim(DogAnimation anim) {
        if (anim == null) this.standAnim = DogAnimation.STAND_QUICK;
        this.standAnim = anim;
    }

    public DogAnimation getSitAnim() {
        return this.sitAnim;
    }

    public void setChopinTailFor(int ticks) {
        this.tickChopinTail = ticks;
    }

    public boolean isChopinTail() {
        return this.tickChopinTail > 0;
    }

    @Override
    public List<IDogFoodHandler> getFoodHandlers() {
        return this.foodHandlers;
    }

    @Override
    public void resetNavigation() {
        this.setNavigation(this.defaultNavigation);
    }

    @Override
    public void resetMoveControl() {
        this.setMoveControl(this.defaultMoveControl);
        
    }

    @Override
    protected PathNavigation createNavigation(Level p_21480_) {
        return new DogPathNavigation(this, p_21480_);
    }

    public List<IDogAlteration> getAlterations() {
        return this.alterations;
    }

    @Override
    public boolean canSwimUnderwater() {
        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.canSwimUnderwater(this);

            if (result.shouldSwing()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onEquipItem(EquipmentSlot p_238393_, ItemStack p_238394_, ItemStack p_238395_) {
        //Don't play additional sound
        //Cause that been done by the talents.
    }

    public boolean isLowAirSupply() {
        return this.getAirSupply() < this.getMaxAirSupply() * 0.3;
    }

    public boolean isDogLowHealth() {
        return this.getHealth() < 6;
    }

    public void setDogSwimming(boolean s) {
        this.isDogSwimming = s;
    }

    public boolean isDogSwimming() {
        return this.isDogSwimming;
    }

    private void hungerHighToLow() {
        if (!this.isDefeated())
        this.setAttributeModifier(Attributes.MOVEMENT_SPEED, HUNGER_MOVEMENT,
                (d, u) -> new AttributeModifier(u, "Hunger Slowness", -0.35f, Operation.MULTIPLY_TOTAL) // this
                                                                                                        // operation is
                                                                                                        // mutiply by 1
                                                                                                        // + x
        );

    }

    private void hungerLowToHigh() {
        this.removeAttributeModifier(Attributes.MOVEMENT_SPEED, HUNGER_MOVEMENT);
    }

    public boolean isLowHunger() {
        return this.isLowHunger;

    }

    public void updateLowHunger () {
        if (this.isLowHunger) {
            if (this.getDogHunger() > 10) {
                this.isLowHunger = false;
                this.hungerLowToHigh();
            }
        } else {
            if (this.getDogHunger() <= 10) {
                this.isLowHunger = true;
                this.hungerHighToLow();
            }
        }
        this.isZeroHunger = this.getDogHunger() == 0;
    }

    protected void handleZeroHunger() {
        ++this.hungerDamageTick;
        int hurt_interval = -1;
        boolean hurt_last_health = false;
        switch (this.level().getDifficulty()) {
            case EASY: {
                hurt_interval = 125;
                break;
            }
            case NORMAL: {
                hurt_interval = 100;
                break;
            }
            case HARD: {
                hurt_interval = 75;
                hurt_last_health = true;
                break;
            }
            default: {
                hurt_interval = -1;
                break;
            }
        }

        // Sorry, i think i need some food...
        // var food beg
        if (hurt_interval >= 0 && ++this.hungerDamageTick >= hurt_interval
                && (hurt_last_health || this.getHealth() > 1f)) {
            this.hurt(this.damageSources().starve(), 0.5f);
            
            this.hungerDamageTick = 0;
        }
    }

    @Override
    protected void updateControlFlags() {
        boolean incapBlockedMove = this.isDefeated() && !this.incapacitatedMananger.canMove();
        boolean animBlockedMove = this.animAction != null && this.animAction.blockMove();
        boolean animBlockedLook = this.animAction != null && this.animAction.blockLook();
        boolean animFreeze = this.getFreezeAnim() != DogAnimation.NONE;
        boolean notControlledByPlayer = !(this.getControllingPassenger() instanceof ServerPlayer);
        boolean notRidingBoat = !(this.getVehicle() instanceof Boat);
        this.goalSelector.setControlFlag(Goal.Flag.MOVE, 
            notControlledByPlayer && !incapBlockedMove && !animBlockedMove && !animFreeze);
        this.goalSelector.setControlFlag(Goal.Flag.JUMP, notControlledByPlayer && notRidingBoat);
        this.goalSelector.setControlFlag(Goal.Flag.LOOK, notControlledByPlayer && !animBlockedLook && !animFreeze);
    }

    @Override
    protected void doPush(Entity pushTarget) {
        if (shouldBlockPush(pushTarget))
            return;
        if (pushTarget.getVehicle() == this
            || this.getVehicle() == pushTarget) {
            return;        
        }
        if (this.isVehicle() && !this.hasControllingPassenger())
            Entity_push(pushTarget);
        else
            super.doPush(pushTarget);
    }

    protected boolean shouldBlockPush(Entity target) {
        boolean avoidPush = 
            ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.PREVENT_DOGS_PUSHING_EACH_OTHER);
        if (!avoidPush)
            return false;
        if (!(target instanceof Dog otherDog)) {
            return false;
        }
        boolean oneDogStillNotOnGround =
            !this.onGround()
            || !otherDog.onGround();
        return oneDogStillNotOnGround;
    }

    @Override
    public void push(Entity source) {
        if (source.getVehicle() == this
            || this.getVehicle() == source)
            return;
        if (this.isVehicle() && !this.hasControllingPassenger())
            Entity_push(source);
        else
            super.push(source);
    }

    private void Entity_push(Entity source) {
        if (this.isPassengerOfSameVehicle(source)) 
            return;
        if (source.noPhysics || this.noPhysics)
            return;
        double dx_vec = source.getX() - this.getX();
        double dz_vec = source.getZ() - this.getZ();
        double max_magnitude = Mth.absMax(dx_vec, dz_vec);
        if (max_magnitude < 0.01)
            return;

        max_magnitude = Math.sqrt(max_magnitude);
        dx_vec /= max_magnitude;
        dz_vec /= max_magnitude;
        double max_magnitude_inv = 1.0D / max_magnitude;
        if (max_magnitude_inv > 1.0D) {
            max_magnitude_inv = 1.0D;
        }

        dx_vec *= max_magnitude_inv;
        dz_vec *= max_magnitude_inv;
        dx_vec *= 0.05;
        dz_vec *= 0.05;
        if (this.isPushable()) {
            this.push(-dx_vec, 0.0D, -dz_vec);
        }

        if (source.isPushable()) {
            source.push(dx_vec, 0.0D, dz_vec);
        }
    }

    @Override
    public boolean canCollideWith(Entity otherEntity) {
        if (shouldBlockPush(otherEntity)) {
            return false;
        }

        if (otherEntity.getVehicle() == this
            || this.getVehicle() == otherEntity)
            return false;
        return super.canCollideWith(otherEntity);
    }

    public BlockPathTypes getBlockPathTypeViaAlterations(BlockPos pos) {
        var blockType = WalkNodeEvaluator.getBlockPathTypeStatic(
            this.level(), 
            pos.mutable()
        );

        for (var alt : this.alterations) {
            var result = alt.inferType(this, blockType);
            if (result.getResult().shouldSwing()) {
                blockType = result.getObject();
                break;
            }
        }
        return blockType;
    }

    public float getTimeDogIsShaking() {
        return this.timeWolfIsShaking;
    }

    public float getUrgentSpeedModifier() {
        if (this.getAttributeValue(Attributes.MOVEMENT_SPEED) <= 0.3) {
            return 1.5f;
        } else {
            return 1.0f;
        }
    }

    public int getMaxIncapacitatedHunger() {
        return 64;
    }

    public StatsTracker getStatTracker() {
        return this.statsTracker;
    }

    public DogGroupsManager getGroups() {
        return this.dogGroupsManager;
    }

    public boolean isMiningCautious() {
        return this.dogMiningCautiousManager.isMiningCautious();
    }

    public void setAnim(DogAnimation animation) {
        this.entityData.set(ANIMATION, animation.getId());
    }

    public DogAnimation getAnim() {
        return DogAnimation.byId(this.entityData.get(ANIMATION));
    }

    private AnimationAction animAction;
    
    public void triggerAnimationAction(AnimationAction action) {
        if (animAction != null) 
            animAction.onStop();
        this.animAction = action;
        if (this.animAction != null) {
            this.animAction.onStart();
            if (this.animAction.blockMove()) {
                this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
                this.getNavigation().stop();
            }
        }
    }

    protected void tickAnimAction() {
        if (this.animAction == null)
            return;
        switch (animAction.getState()) {
            case FINISHED:
                this.triggerAnimationAction(null);
                break;
            default:
                this.animAction.tick();
                break;
        }
    }

    private DogPose activePose = DogPose.STAND;

    public DogPose getDogPose() {
        return activePose;
    }
    
    private void setDogPose(DogPose pose) {
        this.activePose = pose;
    }

    public void updateDogPose() {
        if (this.isDefeated() && !this.incapacitatedMananger.canMove()) {
            this.setDogPose(this.incapacitatedMananger.getPose());
            return;
        }
        if (this.isInSittingPose()) {
            if (this.resting()) {
                this.setDogPose(DogPose.REST);
                return;
            }
            this.setDogPose(this.isLying() ? DogPose.LYING_2 : DogPose.SIT);
            return;
        }
        this.setDogPose(DogPose.STAND);
    }

    //Client
    public DogSkin getClientSkin() {
        return this.clientSkin;
    }

    //Client
    public void setClientSkin(DogSkin skin) {
        if (skin == null) {
            this.clientSkin = DogSkin.CLASSICAL;
        } else {
            this.clientSkin = skin;
        }
    }

    //Client
    public List<AccessoryInstance> getClientSortedAccessories() {
        return this.clientAccessories;
    }

    /**
     * 2 bit for strategy
     */
    public static enum LowHealthStrategy {
        NONE(0),
        RUN_AWAY(1),
        STICK_TO_OWNER(2);

        public static final LowHealthStrategy[] VALUES = 
            Arrays.stream(LowHealthStrategy.values())
            .sorted(
                Comparator.comparingInt(LowHealthStrategy::getId)
            ).toArray(size -> {
                return new LowHealthStrategy[size];
            });

        private final byte id;

        private LowHealthStrategy(int id) {
            this.id = (byte) id;
        }

        public byte getId() {
            return this.id;
        }

        public String getUnlocalisedTitle() {
            return "dog.low_health_strategy." + this.getId();
        }

        public String getUnlocalisedInfo() {
            return "dog.low_health_strategy." + this.getId() + ".help";
        }

        public static LowHealthStrategy fromId(int id) {
            if (!(0 <= id && id <= 2)) return NONE;
            return VALUES[id];
        }

        public LowHealthStrategy prev() {
            int i = this.getId() - 1;
            if (i < 0) {
                i = VALUES.length - 1;
            }
            return VALUES[i];
        }
    
        public LowHealthStrategy next() {
            int i = this.getId() + 1;
            if (i >= VALUES.length) {
                i = 0;
            }
            return VALUES[i];
        }
    }

}
