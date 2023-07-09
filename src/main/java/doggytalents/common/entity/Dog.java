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
import doggytalents.client.screen.DogInfoScreen;
import doggytalents.client.screen.DogNewInfoScreen.DogNewInfoScreen;
import doggytalents.client.screen.DogNewInfoScreen.screen.DogCannotInteractWithScreen;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.config.ConfigHandler.ClientConfig;
import doggytalents.common.entity.ai.nav.DogMoveControl;
import doggytalents.common.entity.ai.nav.DogPathNavigation;
import doggytalents.common.entity.ai.triggerable.DogPlayTagAction;
import doggytalents.common.entity.ai.triggerable.DogTriggerableGoal;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.entity.ai.triggerable.TriggerableAction.ActionState;
import doggytalents.common.entity.DogIncapacitatedMananger.DefeatedType;
import doggytalents.common.entity.DogIncapacitatedMananger.IncapacitatedSyncState;
import doggytalents.common.entity.ai.*;
import doggytalents.common.entity.serializers.DimensionDependantArg;
import doggytalents.common.entity.stats.StatsTracker;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.ParticlePackets;
import doggytalents.common.network.packet.ParticlePackets.CritEmitterPacket;
import doggytalents.common.network.packet.data.DogDismountData;
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
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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
     */
    private static final EntityDataAccessor<Byte> DOG_FLAGS = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.BYTE);

    private static final EntityDataAccessor<Float> HUNGER_INT = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<String> CUSTOM_SKIN = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Byte> SIZE = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<ItemStack> BONE_VARIANT = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.ITEM_STACK);

    // Use Cache.make to ensure static fields are not initialised too early (before Serializers have been registered)
    private static final Cache<EntityDataAccessor<List<AccessoryInstance>>> ACCESSORIES =  Cache.make(() -> (EntityDataAccessor<List<AccessoryInstance>>) SynchedEntityData.defineId(Dog.class, DoggySerializers.ACCESSORY_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<List<TalentInstance>>> TALENTS = Cache.make(() -> (EntityDataAccessor<List<TalentInstance>>) SynchedEntityData.defineId(Dog.class, DoggySerializers.TALENT_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<DogLevel>> DOG_LEVEL = Cache.make(() -> (EntityDataAccessor<DogLevel>) SynchedEntityData.defineId(Dog.class, DoggySerializers.DOG_LEVEL_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<EnumGender>> GENDER = Cache.make(() -> (EntityDataAccessor<EnumGender>) SynchedEntityData.defineId(Dog.class,  DoggySerializers.GENDER_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<EnumMode>> MODE = Cache.make(() -> (EntityDataAccessor<EnumMode>) SynchedEntityData.defineId(Dog.class, DoggySerializers.MODE_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>> DOG_BED_LOCATION = Cache.make(() -> (EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>) SynchedEntityData.defineId(Dog.class, DoggySerializers.BED_LOC_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>> DOG_BOWL_LOCATION = Cache.make(() -> (EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>) SynchedEntityData.defineId(Dog.class, DoggySerializers.BED_LOC_SERIALIZER.get()));
    private static final Cache<EntityDataAccessor<IncapacitatedSyncState>> DOG_INCAP_SYNC_STATE = Cache.make(() -> (EntityDataAccessor<IncapacitatedSyncState>) SynchedEntityData.defineId(Dog.class, DoggySerializers.INCAP_SYNC_SERIALIZER.get()));

    public static final void initDataParameters() {
        ACCESSORIES.get();
        TALENTS.get();
        DOG_LEVEL.get();
        GENDER.get();
        MODE.get();
        DOG_BED_LOCATION.get();
        DOG_BOWL_LOCATION.get();
        DOG_INCAP_SYNC_STATE.get();
    }

    // Cached values
    private final Cache<Integer> spendablePoints = Cache.make(this::getSpendablePointsInternal);
    private final List<IDogAlteration> alterations = new ArrayList<>(4);
    private final List<IDogFoodHandler> foodHandlers = new ArrayList<>(4);
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

    private static final UUID HUNGER_MOVEMENT = UUID.fromString("50671f49-1dfd-4397-242b-78bb6b178115");

    public Dog(EntityType<? extends Dog> type, Level worldIn) {
        super(type, worldIn);
        this.setTame(false);
        this.setGender(EnumGender.random(this.getRandom()));
        this.setLowHealthStrategy(LowHealthStrategy.STICK_TO_OWNER);

        this.navigation = new DogPathNavigation(this, worldIn);
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
        this.entityData.define(DOG_FLAGS, (byte) 0);
        this.entityData.define(GENDER.get(), EnumGender.UNISEX);
        this.entityData.define(MODE.get(), EnumMode.DOCILE);
        this.entityData.define(HUNGER_INT, 60F);
        this.entityData.define(CUSTOM_SKIN, "");
        this.entityData.define(DOG_LEVEL.get(), new DogLevel(0, 0));
        this.entityData.define(DOG_INCAP_SYNC_STATE.get(), IncapacitatedSyncState.NONE);
        this.entityData.define(SIZE, (byte) 3);
        this.entityData.define(BONE_VARIANT, ItemStack.EMPTY);
        this.entityData.define(DOG_BED_LOCATION.get(), new DimensionDependantArg<>(() -> EntityDataSerializers.OPTIONAL_BLOCK_POS));
        this.entityData.define(DOG_BOWL_LOCATION.get(), new DimensionDependantArg<>(() -> EntityDataSerializers.OPTIONAL_BLOCK_POS));
    }

    @Override
    protected void registerGoals() {
        int p = 1;
        this.goalSelector.addGoal(p, new DogFloatGoal(this));
        this.goalSelector.addGoal(p, new DogFindWaterGoal(this));
        this.goalSelector.addGoal(p, new DogAvoidPushWhenIdleGoal(this));
        //this.goalSelector.addGoal(1, new PatrolAreaGoal(this));
        ++p;
        this.goalSelector.addGoal(p, new DogGoAwayFromFireGoal(this));
        ++p;
        this.goalSelector.addGoal(p, new DogSitWhenOrderedGoal(this));
        ++p;
        this.goalSelector.addGoal(p, new DogHungryGoal(this, 1.0f, 2.0f));
        ++p;
        this.goalSelector.addGoal(p, new DogLowHealthGoal.StickToOwner(this));
        this.goalSelector.addGoal(p, new DogLowHealthGoal.RunAway(this));
        //this.goalSelector.addGoal(4, new DogLeapAtTargetGoal(this, 0.4F));
        ++p;
        this.goalSelector.addGoal(p, new DogEatFromChestDogGoal(this, 1.0));
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
        //Dog greet owner goal here
        this.goalSelector.addGoal(p, new DogTriggerableGoal(this, true));
        //this.goalSelector.addGoal(p, new FetchGoal(this, 1.0D, 32.0F));
        this.goalSelector.addGoal(p, new DogFollowOwnerGoalDefeated(this));
        this.goalSelector.addGoal(p, new DogFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        ++p;
        this.goalSelector.addGoal(p, new DogBreedGoal(this, 1.0D));
        ++p;
        this.goalSelector.addGoal(p, new DogRandomStrollGoal(this, 1.0D));
        ++p;
        this.goalSelector.addGoal(p, new DogBegGoal(this, 8.0F));
        ++p;
        this.goalSelector.addGoal(p, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(p, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new DogOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new DogOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(5, new DogNearestToOwnerAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
        this.targetSelector.addGoal(6, new BerserkerModeGoal(this));
        this.targetSelector.addGoal(6, new GuardModeGoal(this));
        //this.goalSelector.addGoal(1, new Wolf.WolfPanicGoal(1.5D)); //Stooopid...
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
        return this.getVehicle() instanceof Player ? 0.5D : 0.0D;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isAlive()) {
            this.headRotationCourseOld = this.headRotationCourse;
            if (this.isBegging()) {
                this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
            } else {
                this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
            }

            if (this.getMaxHealth() != this.maxHealth0) {
                this.maxHealth0 = this.getMaxHealth();
                this.radPerHealthDecrease = Mth.HALF_PI / this.maxHealth0;
            }

            boolean inWater = this.isInWater();
            // If inWater is false then isInRain is true in the or statement
            boolean inRain = inWater ? false : this.isInWaterOrRain();
            boolean inBubbleColumn = this.isInWaterOrBubble(); //!!!!!

            if (inWater || inRain || inBubbleColumn) {
                if (this.wetSource == null) {
                    this.wetSource = WetSource.of(inWater, inBubbleColumn, inRain);
                }
                if (this.isShaking && !this.level.isClientSide) {
                    this.finishShaking();
                    this.level.broadcastEntityEvent(this, doggytalents.common.lib.Constants.EntityState.WOLF_INTERUPT_SHAKING);
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
                                this.level.addParticle(ParticleTypes.LAVA, this.getX() + f1, f + 0.8F, this.getZ() + f2, vec3d.x, vec3d.y, vec3d.z);
                            else if (r==1)
                                this.level.addParticle(ParticleTypes.FLAME, this.getX() + f1, f + 0.8F, this.getZ() + f2, vec3d.x, vec3d.y, vec3d.z);
                            else if (r==2)
                                this.level.addParticle(ParticleTypes.SMOKE, this.getX() + f1, f + 0.8F, this.getZ() + f2, vec3d.x, vec3d.y, vec3d.z);
                        } else
                        this.level.addParticle(ParticleTypes.SPLASH, this.getX() + f1, f + 0.8F, this.getZ() + f2, vec3d.x, vec3d.y, vec3d.z);
                    }
                }

                if (this.timeWolfIsShaking > 0.8) {
                    if (this.shakeFire && random.nextInt(6) == 0) this.playSound(SoundEvents.FIRE_EXTINGUISH, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                }
            }

            // On server side
            if (!this.level.isClientSide) {

                // Every 2 seconds
                if (this.tickCount % 40 == 0) {
                    DogLocationStorage.get(this.level).getOrCreateData(this).update(this);


                    var owner = this.getOwner();
                    if (owner != null) {
                        this.setOwnersName(owner.getName());
                    }
                }
            }
        }

        this.setMaxUpStep(this.isVehicle() ? 1f : 0.6f);

        this.alterations.forEach((alter) -> alter.tick(this));

        //Client
        if (this.level.isClientSide) {
            proccessCustomModelSkin();
            
        }
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

        if (!this.level.isClientSide && this.delayedActionStart > 0)
            --this.delayedActionStart; 

        if (!this.level.isClientSide && this.wetSource != null && !this.isShaking && !this.isPathFinding() && this.isOnGround()) {
            this.startShakingAndBroadcast(false);
        }

        if (!this.level.isClientSide && this.fireImmune()) {
            if (this.isInLava()) {
                this.wasInLava = true;
            }
            if (this.wasInLava == true && !this.isInLava() && !this.isShaking && !this.isPathFinding() && this.isOnGround()) {
                this.startShakingAndBroadcast(true);
                this.wasInLava = false;
            }
        }
        
        //Hunger And Healing tick.
        if (!this.level.isClientSide && !this.isDefeated()) {
            
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

                if (this.hungerTick > 400) {
                    if (this.hungerSaturation > 0) {
                        --this.hungerSaturation;
                    } else {
                        this.setDogHunger(this.getDogHunger() - 1);
                    }
                    
                    this.hungerTick -= 400;
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

        if (this.level.isClientSide && this.getDogLevel().isDireDog() && ConfigHandler.ClientConfig.getConfig(ConfigHandler.CLIENT.DIRE_PARTICLES)) {
            for (int i = 0; i < 2; i++) {
                this.level.addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2D);
            }
        }

        // Check if dog bowl still exists every 50t/2.5s, if not remove
        if (this.tickCount % 50 == 0) {
            ResourceKey<Level> dimKey = this.level.dimension();
            Optional<BlockPos> bowlPos = this.getBowlPos(dimKey);

            // If the dog has a food bowl in this dimension then check if it is still there
            // Only check if the chunk it is in is loaded
            if (bowlPos.isPresent() && this.level.hasChunkAt(bowlPos.get()) && !this.level.getBlockState(bowlPos.get()).is(DoggyBlocks.FOOD_BOWL.get())) {
                this.setBowlPos(dimKey, Optional.empty());
            }
        }

        this.alterations.forEach((alter) -> alter.livingTick(this));

        this.incapacitatedMananger.tick();
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
            } else {
                this.activeAction.onStop();
            }
        }
        //Set.
        this.activeAction = action;
        return true;
    }

    public boolean isBusy() {
        if (!this.isDoingFine()) return false;
        if (this.isInSittingPose() && this.forceSit()) return true;
        return this.activeAction != null;
    }

    public boolean readyForNonTrivialAction() {
        if (!this.isDoingFine()) return false;
        if (this.isInSittingPose() && this.forceSit()) return false;
        if (this.activeAction == null) return true;
        return this.activeAction.isTrivial();
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

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        if (this.isDefeated()) 
            return this.incapacitatedMananger
                .interact(stack, player, hand);

        if (this.isTame()) {
            if (stack.getItem() == Items.STICK) {

                if (this.level.isClientSide) {
                    boolean useLegacyDogGui = 
                        ConfigHandler.ClientConfig.getConfig(ConfigHandler.CLIENT.USE_LEGACY_DOGGUI); 
                    if (this.canInteract(player)) {
                        if (!useLegacyDogGui) {
                            DogNewInfoScreen.open(this);
                        } else {
                            DogInfoScreen.open(this);
                        }
                    } else {
                        if (!useLegacyDogGui) {
                            DogCannotInteractWithScreen.open(this);
                        }
                    }
                }

                return InteractionResult.SUCCESS;
            }
        } else { // Not tamed
            if (stack.getItem() == Items.BONE || stack.getItem() == DoggyItems.TRAINING_TREAT.get()) {

                if (!this.level.isClientSide) {
                    this.usePlayerItem(player, hand, stack);

                    if (stack.getItem() == DoggyItems.TRAINING_TREAT.get() || this.random.nextInt(3) == 0) {
                        this.tame(player);
                        this.navigation.stop();
                        this.setTarget((LivingEntity) null);
                        this.setOrderedToSit(true);
                        this.setHealth(20.0F);
                        this.level.broadcastEntityEvent(this, doggytalents.common.lib.Constants.EntityState.WOLF_HEARTS);
                    } else {
                        this.level.broadcastEntityEvent(this, doggytalents.common.lib.Constants.EntityState.WOLF_SMOKE);
                    }
                }

                return InteractionResult.SUCCESS;
            } 
        }

        Optional<IDogFoodHandler> foodHandler = FoodHandler.getMatch(this, stack, player);

        if (foodHandler.isPresent()) {
            return foodHandler.get().consume(this, stack, player);
        }

        InteractionResult interactResult = InteractHandler.getMatch(this, stack, player, hand);

        if (interactResult != InteractionResult.PASS) {
            return interactResult;
        }

        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.processInteract(this, this.level, player, hand);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }

        InteractionResult actionresulttype = super.mobInteract(player, hand);
        if ((!actionresulttype.consumesAction() || this.isBaby()) && this.canInteract(player)) {
            this.setOrderedToSit(!this.isOrderedToSit());
            this.jumping = false;
            this.navigation.stop();
            this.setTarget(null);
            return InteractionResult.SUCCESS;
        } else if (this.level.isClientSide) {
            this.displayToastIfNoPermission(player);
        }

        return actionresulttype;
    }

    

    private void displayToastIfNoPermission(Player player) {
        if (this.canInteract(player)) return;
        player.displayClientMessage(
            Component.translatable("doggui.invalid_dog.no_permission.title")
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
        if (!this.level.isClientSide) { 
            var e0 = this.getVehicle();
            super.stopRiding();
            var e1 = this.getVehicle();
            if (e0 != e1 && e0 instanceof Player) {
                PacketHandler.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), 
                    new DogDismountData(this.getId())
                );
            }
        } else {
            super.stopRiding();
        }
        
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
            } else if (result == InteractionResult.FAIL) {
                return false;
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

        this.setOrderedToSit(false);

        if (attacker != null && !(attacker instanceof Player) && !(attacker instanceof AbstractArrow)) {
            amount = (amount + 1.0F) / 2.0F;
        }

        return super.hurt(source, amount);
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
        return this.canInteract(player) && super.canBeLeashed(player);
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

        if (this.level != null && !this.level.isClientSide) {
            DogLocationStorage.get(this.level).remove(oldUniqueId);
            DogLocationStorage.get(this.level).getOrCreateData(this).update(this);
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
           this.setHealth(20.0F);
        } else {
           this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
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
        return stack.is(DoggyTags.BREEDING_ITEMS);
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
            return this.getDogSize() * 0.3F + 0.1F;
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
            DogLocationStorage.get(this.level).getOrCreateData(this).update((Dog) transportedEntity);
        }
        return transportedEntity;
    }

    @Override
    public void onRemovedFromWorld() {
        if (this.level instanceof ServerLevel serverLevel && this.isAlive()) {
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
        if (this.level instanceof ServerLevel serverLevel && this.isAlive()) {
            var data = DogLocationStorage.get(serverLevel).getOrCreateData(this);
            
            if (data != null) data.update(this);
        }
        super.onAddedToWorld();
    }

    @Override
    public void remove(Entity.RemovalReason removalReason) {
        super.remove(removalReason);

        if (removalReason == RemovalReason.DISCARDED || removalReason == RemovalReason.KILLED) {
            if (this.level != null && !this.level.isClientSide) {                
                DogLocationStorage.get(this.level).remove(this);
                if (this.getOwnerUUID() != null)
                    DogRespawnStorage.get(this.level).putData(this);
            }
        }
    }

    @Override
    protected void tickDeath() {
        if (this.deathTime == 19) { // 1 second after death
            if (this.level != null && !this.level.isClientSide) {
//                DogRespawnStorage.get(this.world).putData(this);
//                DoggyTalents.LOGGER.debug("Saved dog as they died {}", this);
//
//                DogLocationStorage.get(this.world).remove(this);
//                DoggyTalents.LOGGER.debug("Removed dog location as they were removed from the world {}", this);
            }
        }

        super.tickDeath();
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
        if (this.level.isClientSide) return;
        if (shakeFire) {
            this.startShakingLava();
            ParticlePackets.DogStartShakingLavaPacket.sendDogStartShakingLavaPacketToNearByClients(this);
            return;
        }
        this.startShaking();
        this.level.broadcastEntityEvent(this, doggytalents.common.lib.Constants.EntityState.WOLF_START_SHAKING);
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
        if (this.level.isClientSide)
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
        this.incapacitatedMananger.onBeingDefeated();
        this.unRide();
        createIncapSyncState(source);

        var owner = this.getOwner();
        if (owner != null) sendIncapacitatedMsg(owner, source);
    
        //TODO Maybe need to find somewhere more appropriate to do this.
        this.removeAttributeModifier(Attributes.MOVEMENT_SPEED, HUNGER_MOVEMENT);
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
        if (source.is(DamageTypeTags.IS_FIRE)) {
            type = DefeatedType.BURN;
        } else if (source.is(DamageTypes.MAGIC)) {
            type = DefeatedType.POISON;
        } else {
            type = DefeatedType.BLOOD;
        }
        
        this.setIncapSyncState(new IncapacitatedSyncState(type));
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

        compound.putString("mode", this.getMode().getSaveName());
        compound.putString("dogGender", this.getGender().getSaveName());
        compound.putFloat("dogHunger", this.getDogHunger());
        this.getOwnersName().ifPresent((comp) -> {
            NBTUtil.putTextComponent(compound, "lastKnownOwnerName", comp);
        });

        compound.putString("customSkinHash", this.getSkinHash());
        compound.putBoolean("willObey", this.willObeyOthers());
        compound.putBoolean("friendlyFire", this.canOwnerAttack());
        compound.putBoolean("regardTeamPlayers", this.regardTeamPlayers());
        compound.putBoolean("forceSit", this.forceSit());
        compound.putByte("lowHealthStrategy", this.getLowHealthStrategy().getId());
        compound.putBoolean("crossOriginTp", this.crossOriginTp());
        compound.putInt("dogSize", this.getDogSize());
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

            if (compound.contains("customSkinHash", Tag.TAG_STRING)) {
                this.setSkinHash(compound.getString("customSkinHash"));
            } else {
                BackwardsComp.readDogTexture(compound, this::setSkinHash);
            }

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
                this.setDogSize(compound.getInt("dogSize"));
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
        if (TALENTS.get().equals(key) || ACCESSORIES.get().equals(key)) {
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
            if (this.level.isClientSide) {
                // Does not recall this notifyDataManagerChange as list object is
                // still the same, maybe in future MC versions this will change so need to watch out
                this.clientAccessories = new ArrayList<>(this.getAccessories());
                this.clientAccessories.sort(AccessoryInstance.RENDER_SORTER);
                //this.getAccessories().sort(AccessoryInstance.RENDER_SORTER);
            }
        }

        if (SIZE.equals(key)) {
            this.refreshDimensions();
        }

        if (this.level.isClientSide && CUSTOM_SKIN.equals(key)) {
            this.setClientSkin(
                DogTextureManager.INSTANCE
                    .getLocFromHashOrGet(
                        this.entityData.get(CUSTOM_SKIN), 
                        DogTextureManager.INSTANCE::getCached));
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

        accessories.add(accessoryInst);

        this.markDataParameterDirty(ACCESSORIES.get());

        return true;
    }

    @Override
    public List<AccessoryInstance> removeAccessories() {
        List<AccessoryInstance> removed = new ArrayList<>(this.getAccessories());

        for (AccessoryInstance inst : removed) {
            if (inst instanceof IDogAlteration) {
                ((IDogAlteration) inst).remove(this);
            }
        }

        this.getAccessories().clear();
        this.markDataParameterDirty(ACCESSORIES.get());
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
        return this.getBedPos(this.level.dimension());
    }

    public Optional<BlockPos> getBedPos(ResourceKey<Level> registryKey) {
        return this.entityData.get(DOG_BED_LOCATION.get()).getOrDefault(registryKey, Optional.empty());
    }

    public void setBedPos(@Nullable BlockPos pos) {
        this.setBedPos(this.level.dimension(), pos);
    }

    public void setBedPos(ResourceKey<Level> registryKey, @Nullable BlockPos pos) {
        this.setBedPos(registryKey, WorldUtil.toImmutable(pos));
    }

    public void setBedPos(ResourceKey<Level> registryKey, Optional<BlockPos> pos) {
        this.entityData.set(DOG_BED_LOCATION.get(), this.entityData.get(DOG_BED_LOCATION.get()).copy().set(registryKey, pos));
    }

    public Optional<BlockPos> getBowlPos() {
        return this.getBowlPos(this.level.dimension());
    }

    public Optional<BlockPos> getBowlPos(ResourceKey<Level> registryKey) {
        return this.entityData.get(DOG_BOWL_LOCATION.get()).getOrDefault(registryKey, Optional.empty());
    }

    public void setBowlPos(@Nullable BlockPos pos) {
        this.setBowlPos(this.level.dimension(), pos);
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
        return !Strings.isNullOrEmpty(this.getSkinHash());
    }

    public String getSkinHash() {
        return this.entityData.get(CUSTOM_SKIN);
    }

    public void setSkinHash(String hash) {
        if (hash == null) {
            hash = "";
        }
        this.entityData.set(CUSTOM_SKIN, hash);
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
        this.getDogLevel().incrementLevel(typeIn);
        this.markDataParameterDirty(DOG_LEVEL.get());
    }

    @Override
    public void setDogSize(int value) {
        this.entityData.set(SIZE, (byte)Math.min(5, Math.max(1, value)));
    }

    @Override
    public int getDogSize() {
        return this.entityData.get(SIZE);
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
        byte c = this.entityData.get(DOG_FLAGS);
        this.entityData.set(DOG_FLAGS, (byte)(flag ? c | bits : c & ~bits));
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

        List<TalentInstance> activeTalents = this.getTalentMap();

        TalentInstance inst = null;
        for (TalentInstance activeInst : activeTalents) {
            if (activeInst.of(talent)) {
                inst = activeInst;
                break;
            }
        }

        if (inst == null) {
            if (level == 0) {
                return InteractionResult.PASS;
            }

            inst = talent.getDefault(level);
            activeTalents.add(inst);
            inst.init(this);
        } else {
            int previousLevel = inst.level();
            if (previousLevel == level) {
                return InteractionResult.PASS;
            }

            inst.setLevel(level);
            inst.set(this, previousLevel);

            if (level <= 0) {
                //Safely remove the talents.
                inst.remove(this);
                activeTalents.remove(inst);
            }
        }

        this.markDataParameterDirty(TALENTS.get());
        return InteractionResult.SUCCESS;
    }


    public <T> void markDataParameterDirty(EntityDataAccessor<T> key) {
        this.markDataParameterDirty(key, true);
    }

    public <T> void markDataParameterDirty(EntityDataAccessor<T> key, boolean notify) {
        if (notify) {
            this.onSyncedDataUpdated(key);
        }

        // Force the entry to update
        DataItem<T> dataentry = this.entityData.getItem(key);
        dataentry.setDirty(true);
        this.entityData.isDirty = true;
    }

    @Override
    public void markAccessoriesDirty() {
        this.markDataParameterDirty(ACCESSORIES.get());
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

        this.getTalentMap().clear();
        this.markDataParameterDirty(TALENTS.get());

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
        // Gets the first passenger which is the controlling passenger
        return this.getPassengers().isEmpty() ? null : (LivingEntity) this.getPassengers().get(0);
    }

    // @Override
    // public boolean canBeControlledByRidxer() {
    //     this.isControlledByLocalInstance()
    //     return this.getControllingPassenger() instanceof LivingEntity;
    // }

    //TODO
    @Override
    public boolean isPickable() {
        return super.isPickable();
    }

    @Override
    public boolean isPushable() {
        return !this.isVehicle() && super.isPushable();
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

    //             if (this.jumpPower > 0.0F && !this.isDogJumping() && this.isOnGround()) {

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
    //             if (this.isOnGround()) {
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
    protected void tickRidden(LivingEntity rider, Vec3 rideVec) {

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

    private void checkAndJumpWhenBeingRidden(LivingEntity rider) {
        float forward = rider.zza;

        if (this.jumpPower > 0) {
            if (this.isInWater() && this.canSwimUnderwater())
                this.doDogRideFloat();
            else if (!this.isDogJumping() && this.isOnGround())
                this.doDogRideJump(forward);
        }

        // Once the entity reaches the ground again allow it to jump again
        if (this.isOnGround()) {
            this.jumpPower = 0.0F;
            this.setDogJumping(false);
        } else {
            this.jumpPower = 0.0f;
        }
    }

    @Override
    protected Vec3 getRiddenInput(LivingEntity rider, Vec3 rideVec) {
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
    protected float getRiddenSpeed(LivingEntity rider) {
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
            } else if (this.isOnGround()) {
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
        // BlockState blockBelow = this.level.getBlockState(this.blockPosition().below());
        // boolean onBed = blockBelow.is(DoggyBlocks.DOG_BED.get()) || blockBelow.is(BlockTags.BEDS);
        // if (onBed) {
        //     return true;
        // }

        if (this.isDefeated()) {
            BlockState blockBelow = this.level.getBlockState(this.blockPosition());
            boolean onBed = blockBelow.is(DoggyBlocks.DOG_BED.get()) || blockBelow.is(BlockTags.BEDS);
            if (onBed) {
                return true;
            }
        }

        return false;
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
        switch (this.level.getDifficulty()) {
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
        boolean notControlledByPlayer = !(this.getControllingPassenger() instanceof ServerPlayer);
        boolean notRidingBoat = !(this.getVehicle() instanceof Boat);
        this.goalSelector.setControlFlag(Goal.Flag.MOVE, notControlledByPlayer && !incapBlockedMove);
        this.goalSelector.setControlFlag(Goal.Flag.JUMP, notControlledByPlayer && notRidingBoat);
        this.goalSelector.setControlFlag(Goal.Flag.LOOK, notControlledByPlayer);
    }

    @Override
    protected void doPush(Entity pushTarget) {
        boolean pushEachOther = 
            ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.PREVENT_DOGS_PUSHING_EACH_OTHER);
        if (
            pushEachOther
            && pushTarget instanceof Dog dog
            && !dog.getNavigation().isDone()
            && !dog.isOnGround()
        )
            return;
        if (
            pushEachOther
            && pushTarget instanceof Player player
            && !player.isShiftKeyDown()
            && this.isDoingFine()
        )
            return;
        super.doPush(pushTarget);
    }

    @Override
    public boolean canCollideWith(Entity otherEntity) {
        //TODO should this be dog of the same team ?
        boolean pushEachOther = ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.PREVENT_DOGS_PUSHING_EACH_OTHER);
        if (
            pushEachOther
            && otherEntity instanceof Dog dog
            && !dog.getNavigation().isDone()
            && !dog.isOnGround()
        ) {
            ChopinLogger.l("Colliding with dog!");
            return false;
        }
            
        return super.canCollideWith(otherEntity);
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
