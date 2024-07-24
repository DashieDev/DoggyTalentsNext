package doggytalents.common.entity;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import doggytalents.*;
import doggytalents.api.anim.DogAnimation;
import doggytalents.api.enu.WetSource;
import doggytalents.api.feature.*;
import doggytalents.api.feature.DogLevel.Type;
import doggytalents.api.impl.DogAlterationProps;
import doggytalents.api.impl.DogArmorItemHandler;
import doggytalents.api.impl.IDogRangedAttackManager;
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
import doggytalents.common.effects.NattoBiteEffect;
import doggytalents.common.entity.ai.nav.DogBodyRotationControl;
import doggytalents.common.entity.ai.nav.DogJumpControl;
import doggytalents.common.entity.ai.nav.DogMoveControl;
import doggytalents.common.entity.ai.nav.DogPathNavigation;
import doggytalents.common.entity.ai.nav.IDogNavLock;
import doggytalents.common.entity.ai.triggerable.AnimationAction;
import doggytalents.common.entity.ai.triggerable.DogBackFlipAction;
import doggytalents.common.entity.ai.triggerable.DogDrownAction;
import doggytalents.common.entity.ai.triggerable.DogPlayTagAction;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.entity.ai.triggerable.TriggerableAction.ActionState;
import doggytalents.common.entity.anim.DogAnimationManager;
import doggytalents.common.entity.anim.DogPose;
import doggytalents.common.entity.datasync.DogDataSyncManager;
import doggytalents.common.entity.DogIncapacitatedMananger.BandaidState;
import doggytalents.common.entity.DogIncapacitatedMananger.DefeatedType;
import doggytalents.common.entity.DogIncapacitatedMananger.IncapacitatedSyncState;
import doggytalents.common.entity.ai.*;
import doggytalents.common.entity.serializers.DimensionDependantArg;
import doggytalents.common.entity.stats.StatsTracker;
import doggytalents.common.entity.texture.DogSkinData;
import doggytalents.common.fabric_helper.entity.DogFabricHelper;
import doggytalents.common.fabric_helper.entity.network.SyncTypes;
import doggytalents.common.fabric_helper.entity.network.SyncTypes.SyncType;
import doggytalents.common.event.EventHandler;
import doggytalents.common.item.DoggyArtifactItem;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.ParticlePackets;
import doggytalents.common.network.packet.ParticlePackets.CritEmitterPacket;
import doggytalents.common.network.packet.data.DogMountData;
import doggytalents.common.network.packet.data.DogShakingData;
import doggytalents.common.storage.DogLocationStorage;
import doggytalents.common.storage.DogRespawnStorage;
import doggytalents.common.storage.OnlineDogLocationManager;
import doggytalents.common.util.*;
import doggytalents.forge_imitate.atrrib.ForgeMod;
import doggytalents.forge_imitate.network.PacketDistributor;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
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
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;

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
     *     8               256                 <Reserved>
     *     9               512                 PATROL_TARGET_LOCK
     *     10              1024                FLYING
     *     11              2048                SHOW_ARMOR
     *     12              4096                COMBAT_RETURN_STRATEGY_LSB
     *     13              8192                COMBAT_RETURN_STRATEGY_MSB
     *     14              16384               AUTO_MOUNT
     *     15              32768               RESTING
     *     16              65536               REST_BELLY
     *     17              131072              DRUNK_POSE
     *     18              262144              ACTION_FORCED_NONE_ANIM
     *     .
     *     31              2^31                <Reserved>
     */
    private static final EntityDataAccessor<Integer> DOG_FLAGS = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Float> HUNGER_INT = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.FLOAT);
   
    private static final EntityDataAccessor<ItemStack> BONE_VARIANT = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.ITEM_STACK);

    private static final EntityDataAccessor<Integer> INCAP_VAL = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIM_SYNC_TIME = SynchedEntityData.defineId(Dog.class, EntityDataSerializers.INT);

    // Use Cache.make to ensure static fields are not initialised too early (before Serializers have been registered)
    // private static final Cache<EntityDataAccessor<DogLevel>> DOG_LEVEL = Cache.make(() -> (EntityDataAccessor<DogLevel>) SynchedEntityData.defineId(Dog.class, DoggySerializers.DOG_LEVEL_SERIALIZER.get()));
    // private static final Cache<EntityDataAccessor<EnumGender>> GENDER = Cache.make(() -> (EntityDataAccessor<EnumGender>) SynchedEntityData.defineId(Dog.class,  DoggySerializers.GENDER_SERIALIZER.get()));
    // private static final Cache<EntityDataAccessor<EnumMode>> MODE = Cache.make(() -> (EntityDataAccessor<EnumMode>) SynchedEntityData.defineId(Dog.class, DoggySerializers.MODE_SERIALIZER.get()));
    // private static final Cache<EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>> DOG_BED_LOCATION = Cache.make(() -> (EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>) SynchedEntityData.defineId(Dog.class, DoggySerializers.BED_LOC_SERIALIZER.get()));
    // private static final Cache<EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>> DOG_BOWL_LOCATION = Cache.make(() -> (EntityDataAccessor<DimensionDependantArg<Optional<BlockPos>>>) SynchedEntityData.defineId(Dog.class, DoggySerializers.BED_LOC_SERIALIZER.get()));
    // private static final Cache<EntityDataAccessor<IncapacitatedSyncState>> DOG_INCAP_SYNC_STATE = Cache.make(() -> (EntityDataAccessor<IncapacitatedSyncState>) SynchedEntityData.defineId(Dog.class, DoggySerializers.INCAP_SYNC_SERIALIZER.get()));
    // private static final Cache<EntityDataAccessor<List<DoggyArtifactItem>>> ARTIFACTS = Cache.make(() -> (EntityDataAccessor<List<DoggyArtifactItem>>) SynchedEntityData.defineId(Dog.class, DoggySerializers.ARTIFACTS_SERIALIZER.get()));
    // private static final Cache<EntityDataAccessor<DogSize>> DOG_SIZE = Cache.make(() -> (EntityDataAccessor<DogSize>) SynchedEntityData.defineId(Dog.class,  DoggySerializers.DOG_SIZE_SERIALIZER.get()));
    // private static final Cache<EntityDataAccessor<DogSkinData>> CUSTOM_SKIN = Cache.make(() -> (EntityDataAccessor<DogSkinData>) SynchedEntityData.defineId(Dog.class,  DoggySerializers.DOG_SKIN_DATA_SERIALIZER.get()));
    
    public static final void initDataParameters() { 
        // DOG_LEVEL.get();
        // GENDER.get();
        // MODE.get();
        // DOG_BED_LOCATION.get();
        // DOG_BOWL_LOCATION.get();
        // DOG_INCAP_SYNC_STATE.get();
        // ARTIFACTS.get();
        // DOG_SIZE.get();
        // CUSTOM_SKIN.get();
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
    public final DogDataSyncManager dogSyncedDataManager
        = new DogDataSyncManager(this);
    public final DogOwnerDistanceManager dogOwnerDistanceManager 
        = new DogOwnerDistanceManager(this);
    public final DogMiningCautiousManager dogMiningCautiousManager
        = new DogMiningCautiousManager(this);
    public final DogGroupsManager dogGroupsManager
        = new DogGroupsManager();
    public final DogIncapacitatedMananger incapacitatedMananger
        = new DogIncapacitatedMananger(this);
    private final DogHungerManager hungerManager
        = new DogHungerManager(this);
    public final DogAiManager dogAi;
    private DogAlterationProps alterationProps
        = new DogAlterationProps();
    private IDogRangedAttackManager dogRangedAttackManager
        = IDogRangedAttackManager.NONE;

    private final DogArmorItemHandler dogArmors = new DogArmorItemHandler(this);
    private ItemStack mouthStack = ItemStack.EMPTY;
    private ItemStack wolfArmorStack = ItemStack.EMPTY;

    protected final PathNavigation defaultNavigation;
    protected final MoveControl defaultMoveControl;
    protected @Nullable IDogNavLock navigationLock;
    protected PathNavigation currentNavigation;
    
    protected int switchNavCooldown = 0;
    private int pushFromOtherDogResistTick = 0;

    private int healingTick;  
    private int prevHealingTick;
    //private int wanderRestTime = 0;
    private int wanderCooldown = 0;
    private int drunkTickLeft = 0;

    private float headRotationCourse;
    private float headRotationCourseOld;
    private @Nonnull WetSource wetSource = WetSource.NONE;
    private boolean isShaking;
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;
    private int tickUntilRest;
    private int onFireSmokeTick;

    private boolean shakeFire = false;
    
    private float percentDecreasePerHealthLost;
    private float maxHealth0;

    protected boolean dogJumping;
    protected float jumpPower;

    protected boolean isDogSwimming;

    public int lastOrderedToSitTick;
    private int tickChopinTail;
    private boolean dogAnimHurtImpules = false;
    private int idleAnimHurtCooldown = 0;

    public Dog(EntityType<? extends Dog> type, Level worldIn) {
        super(type, worldIn);
        this.setTame(false, true);
        this.setGender(EnumGender.random(this.getRandom()));
        this.setLowHealthStrategy(LowHealthStrategy.STICK_TO_OWNER);
        this.authorizedChangingOwner = true;
        this.setOwnerUUID(null); //Just to be sure
        this.authorizedChangingOwner = false;
        this.resetTickTillRest();

        this.moveControl = new DogMoveControl(this);
        this.jumpControl = new DogJumpControl(this);

        this.defaultNavigation = this.navigation;
        this.defaultMoveControl = this.moveControl;

        this.dogAi = new DogAiManager(this, this.level().getProfilerSupplier());
        this.dogAi.init();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        //this.entityData.define(CLASSICAL_VAR.get(), ClassicalVar.PALE);
        builder.define(LAST_KNOWN_NAME, Optional.empty());
        builder.define(DOG_FLAGS, 0);
        //builder.define(GENDER.get(), EnumGender.UNISEX);
        //builder.define(MODE.get(), EnumMode.DOCILE);
        builder.define(HUNGER_INT, 60F);
        //builder.define(CUSTOM_SKIN.get(), DogSkinData.NULL);
        //builder.define(DOG_LEVEL.get(), new DogLevel(0, 0));
        //builder.define(DOG_INCAP_SYNC_STATE.get(), IncapacitatedSyncState.NONE);
        //builder.define(DOG_SIZE.get(), DogSize.MODERATO);
        builder.define(BONE_VARIANT, ItemStack.EMPTY);
        //builder.define(ARTIFACTS.get(), new ArrayList<DoggyArtifactItem>(3));
        //builder.define(DOG_BED_LOCATION.get(), new DimensionDependantArg<>(() -> EntityDataSerializers.OPTIONAL_BLOCK_POS));
        //builder.define(DOG_BOWL_LOCATION.get(), new DimensionDependantArg<>(() -> EntityDataSerializers.OPTIONAL_BLOCK_POS));
        builder.define(INCAP_VAL, 0);
        builder.define(ANIMATION, 0);
        builder.define(ANIM_SYNC_TIME, 0);
    }

    @Override
    protected final void registerGoals() {
        
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isDefeated()) {
            if (this.getDogIncapValue() > 20) {
                int chance_window = 
                    this.incapacitatedMananger.canMove() ? 5 : 8; 
                return this.random.nextInt(chance_window) == 0 ? SoundEvents.WOLF_WHINE : null;
            } else {
                return SoundEvents.WOLF_PANT;
            }
        }
        if (this.random.nextInt(3) == 0) {
            return this.isTame() && this.getHealth() < 10.0F ? SoundEvents.WOLF_WHINE : SoundEvents.WOLF_PANT;
        } else {
            return SoundEvents.WOLF_AMBIENT;
        }
    }

    @Override
    public float getSoundVolume() {
        float default_val = 0.4f;
        if (this.isDefeated()) {
            if (this.getDogIncapValue() > 20) {
                default_val = this.incapacitatedMananger.canMove() ? 
                    0.2f : 0.05f;
            }
        }
        return default_val;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        if (canWolfArmorAbsorb(damageSourceIn)) {
            return SoundEvents.WOLF_ARMOR_DAMAGE;
        }
        
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

    public boolean isDogSoaked() {
        return this.wetSource.soaked();
    }

    @Environment(EnvType.CLIENT)
    public float getShadingWhileWet(float partialTicks) {
        return Math.min(0.5F + Mth.lerp(partialTicks, this.prevTimeWolfIsShaking, this.timeWolfIsShaking) / 2.0F * 0.5F, 1.0F);
    }

    @Override
    @Environment(EnvType.CLIENT)
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
    @Environment(EnvType.CLIENT)
    public float getInterestedAngle(float partialTicks) {
        return Mth.lerp(partialTicks, this.headRotationCourseOld, this.headRotationCourse) * 0.15F * (float)Math.PI;
    }

    @Override
    public void handleEntityEvent(byte id) {
        // if (id == doggytalents.common.lib.Constants.EntityState.WOLF_START_SHAKING) {
        //     this.startShaking();
        // } else if (id == doggytalents.common.lib.Constants.EntityState.WOLF_INTERUPT_SHAKING) {
        //     this.finishShaking();
        // } else {
        //     super.handleEntityEvent(id);
        // }
        super.handleEntityEvent(id);
    }

    public void handleDogShakingUpdate(DogShakingData.State state) {
        switch (state) {
        case SHAKE_WATER:
            this.startShaking();
            break;
        case SHAKE_LAVA:
            this.startShakingLava();
            break;
        case STOP:
            this.finishShaking();
            break;
        default:
            this.finishShaking();
            break;
        }
    }

    public float getTailRotation() {
        if (!this.isTame())
            return ((float) Math.PI / 5f);
        final float full_health_angle = 1.73f;
        float lost_health = this.getMaxHealth() - this.getHealth();
        float lost_health_percent = lost_health * this.percentDecreasePerHealthLost;
        float lost_rad_percent = lost_health_percent * lost_health_percent;
        float lost_rad = Mth.HALF_PI * lost_rad_percent;
        lost_rad = Mth.clamp(lost_rad, 0, Mth.HALF_PI);
        
        return full_health_angle - lost_rad;
    }

    @Override
    public float getWagAngle(float limbSwing, float limbSwingAmount, float partialTickTime) {
        return Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    // @Override
    // protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
    //     if (this.isVehicle()) {
    //         return this.getDogVisualBbHeight() * 0.8F;
    //     }
    //     return sizeIn.height * 0.8F;
    // }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    @Override
    public int getMaxHeadXRot() {
        return this.isInSittingPose() ? 20 : super.getMaxHeadXRot();
    }

    // @Override
    // public double getMyRidingOffset() {
    //     return this.getVehicle() instanceof Player ? 0.5D : 0.2D;
    // }



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

    private EntityDimensions visualDimension = null;

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        var self_dim = super.getDimensions(pose);
        visualDimension = null;
        if (self_dim.width() >= 1f) {
            self_dim = new EntityDimensions(1f, self_dim.height(), self_dim.eyeHeight(), self_dim.attachments(), self_dim.fixed());
        }
        
        boolean collide_passeneger = 
            ConfigHandler.SERVER.WOLF_MOUNT_PASSENGER_COLLISION.get();
        if (!collide_passeneger)
            return self_dim;
        if (this.isVehicle() && !this.getPassengers().isEmpty()) {
            visualDimension = self_dim;
            self_dim = computeRidingDimension(self_dim);
        }
        return self_dim;
    }

    //@Override
    public double getPassengersRidingOffset(EntityDimensions self_dim) {
        return self_dim.height() * 0.75D;
    }

    public EntityDimensions getRealDimensions() {
        return super.getDimensions(getPose());
    }

    public float getDogVisualBbHeight() {
        if (this.visualDimension != null)
            return this.visualDimension.height();
        return this.getBbHeight();
    }

    public float getDogVisualBbWidth() {
        if (this.visualDimension != null)
            return this.visualDimension.width();
        return this.getBbWidth();
    }

    private EntityDimensions computeRidingDimension(EntityDimensions self_dim) {
        float total_width = self_dim.width();
        
        var passenger = this.getPassengers().get(0);
        
        float total_height = (float) self_dim.height();
        total_width = Math.max(total_width, passenger.getBbWidth());
        total_height += passenger.getBbHeight() - passenger.getVehicleAttachmentPoint(this).y;
        
        if (total_width >= 1f)
            total_width = 1f;

        return new EntityDimensions(total_width, total_height, self_dim.eyeHeight(), self_dim.attachments(), self_dim.fixed());
    }

    @Override
    public void tick() {
        super.tick();

        updateClassicalAnim();

        //this.setMaxUpStep(this.isVehicle() ? 1f : 0.6f);
        if (this.isAlive()) {
            this.updateDogPose();
        }

        this.alterations.forEach((alter) -> alter.tick(this));

        if (this.isAlive() && this.getMaxHealth() != this.maxHealth0) {
            this.maxHealth0 = this.getMaxHealth();
            this.percentDecreasePerHealthLost = 1 / this.maxHealth0;
        }

        // On server side
        if (this.isAlive() && !this.level().isClientSide) {

            // Every 2 seconds
            if (this.tickCount % 40 == 0) {
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
            
        }

        if (this.level().isClientSide 
            && ConfigHandler.CLIENT.DISPLAY_SMOKE_WHEN_ON_FIRE.get()) {
            addAdditionalOnFireEffect();
        }

        if (!this.level().isClientSide) {
            this.dogSyncedDataManager.tick();
        }

        //Fabric
        if (!this.level().isClientSide) {
            this.getDogFabricHelper().tick();
        }
    }

    private void addAdditionalOnFireEffect() {
        if (this.isOnFire()) {
            if (this.getRandom().nextInt(3) == 0) {
                float f1 = (this.getRandom().nextFloat() * 2.0F - 1.0F) * this.getDogVisualBbWidth() * 0.5F;
                float f2 = (this.getRandom().nextFloat() * 2.0F - 1.0F) * this.getDogVisualBbWidth() * 0.5F;
                this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                this.getX() + f1,
                this.getY() + this.getEyeHeight(),
                this.getZ() + f2,
                0, 0.05 , 0 );
            }
            if (onFireSmokeTick <= 20 * 15) {
                ++onFireSmokeTick;
            } else if (onFireSmokeTick <= 20 * 30
                && this.tickCount % 4 == 0) {
                ++onFireSmokeTick;
            }
            onFireSmokeTick = Mth.clamp(onFireSmokeTick, 20, 20 * 15);
        } else if (onFireSmokeTick > 0) {
            --onFireSmokeTick;
            if (this.getRandom().nextInt(3) == 0) {
                float f1 = (this.getRandom().nextFloat() * 2.0F - 1.0F) * this.getDogVisualBbWidth() * 0.5F;
                float f2 = (this.getRandom().nextFloat() * 2.0F - 1.0F) * this.getDogVisualBbWidth() * 0.5F;
                this.level().addParticle(ParticleTypes.SMOKE,
                this.getX() + f1,
                this.getY() + this.getEyeHeight(),
                this.getZ() + f2,
                0, 0.05 , 0 );
            }
        }
    }

    private void updateClassicalAnim() {
        if (!this.isAlive())
            return;
            
        updateClassicalBegAnim();

        boolean val = checkDogInWetSourceAndWetTheDog();
        updateClassicalShakeAnim(val);
    }

    private void updateClassicalBegAnim() {
        if (!this.canDogDoBegAnim()) {
            this.headRotationCourse = 0;
            this.headRotationCourseOld = 0;
            return;
        }

        this.headRotationCourseOld = this.headRotationCourse;
        if (this.isBegging()) {
            this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
        } else {
            this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
        }
    }

    private boolean checkDogInWetSourceAndWetTheDog() {
        if (this.isInLava()) {
            this.wetSource = WetSource.LAVA;
            return true;
        }
        if (this.isInWater()) {
            this.wetSource = WetSource.WATER;
            return true;
        }
        if (this.isInWaterOrRain()) {
            this.wetSource = WetSource.RAIN;
            return true;
        }
        if (this.isInWaterOrBubble()) {
            this.wetSource = WetSource.BUBBLE_COLUMN;
            return true;
        }
        return false;
    }

    private void updateClassicalShakeAnim(boolean currentlyInWater) {
        if (!this.level().isClientSide) {
            if (this.isShaking)
            if (!this.canDogDoShakeAnim() || currentlyInWater) {
                this.finishShaking();
                ParticlePackets.DogShakingPacket.sendDogShakingPacket(this, DogShakingData.State.STOP);
                return;
            }
        }
        if (!this.isShaking)
            return;

        if (this.timeWolfIsShaking == 0.0F) {
            if (!this.shakeFire) this.playSound(SoundEvents.WOLF_SHAKE, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        }

        this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
        this.timeWolfIsShaking += 0.05F;
        if (this.prevTimeWolfIsShaking >= 2.0F) {

            //TODO check if only called server side
            for (IDogAlteration alter : this.alterations) {
                alter.onShakingDry(this, this.wetSource);
            }

            this.wetSource = WetSource.NONE;
            this.finishShaking();
        }

        if (this.timeWolfIsShaking > 0.4F) {
            float f = (float)this.getY();
            int i = (int)(Mth.sin((this.timeWolfIsShaking - 0.4F) * (float)Math.PI) * 7.0F);
            Vec3 vec3d = this.getDeltaMovement();

            for (int j = 0; j < i; ++j) {
                float f1 = (this.random.nextFloat() * 2.0F - 1.0F) * this.getDogVisualBbWidth() * 0.5F;
                float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.getDogVisualBbWidth() * 0.5F;
                if (this.shakeFire) {
                    float r = this.getRandom().nextFloat();
                    var type = ParticleTypes.SMOKE;
                    if (r <= 0.15f) {
                        type = ParticleTypes.LANDING_LAVA;
                    } else if (r <= 0.6f) {
                        type = ParticleTypes.LAVA;
                    }
                    this.level().addParticle(type, this.getX() + f1, f + 0.8F, this.getZ() + f2, vec3d.x, vec3d.y, vec3d.z);
                } else
                this.level().addParticle(ParticleTypes.SPLASH, this.getX() + f1, f + 0.8F, this.getZ() + f2, vec3d.x, vec3d.y, vec3d.z);
            }
        }

        if (this.timeWolfIsShaking > 0.5) {
            if (this.shakeFire && random.nextInt(6) == 0) this.playSound(SoundEvents.FIRE_EXTINGUISH, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        }
    }

    public boolean canDoIdileAnim() {
        if (!this.isAlive())
            return false;
        if (this.isPassenger() || this.isVehicle())
            return false;
        if (this.idleAnimHurtCooldown > 0)
            return false;
        return !this.isShaking && !this.animationManager.started();
    }

    public boolean canContinueDoIdileAnim() {
        if (this.dogAnimHurtImpules) {
            this.dogAnimHurtImpules = false;
            this.idleAnimHurtCooldown = 20;
            return false;
        }
        if (this.isPassenger() || this.isVehicle())
            return false;
        return this.isAlive() && !this.isShaking;
    }

    public void setAnimForIdle(DogAnimation anim) {
        this.setAnim(anim);
        this.dogAnimHurtImpules = false;
    }

    public boolean canDogDoBegAnim() {
        var pose = this.getDogPose();
        if (!pose.canBeg)
            return false;
        if (this.animationManager.started()) {
            if (!this.getAnim().freeHead())
                return false;
        } 
        return true;
    }

    public boolean canDogDoShakeAnim() {
        var pose = this.getDogPose();
        if (!pose.canShake)
            return false;
        if (this.animationManager.started())
            return false;
        return true;
    }

    @Override
    public void aiStep() {
        if (this.navigation != this.currentNavigation) {
            this.navigation = this.currentNavigation;
        }
        if (this.navigationLock != null)
            this.navigationLock.unlockDogNavigation();
        
        validateGoalsAndTickNonRunningIfNeeded();

        if (!this.level().isClientSide) {
            this.getSensing().tick();
            this.lerpSteps = 0;
            this.lerpHeadSteps = 0;
            this.dogAi.tickServer();
        }
            

        super.aiStep();

        updateDogBeginShake();
        
        if (!this.level().isClientSide && !this.isDefeated()) {
            this.hungerManager.tick();
            this.tickDogHealing();
            this.dogOwnerDistanceManager.tick();
            this.dogMiningCautiousManager.tick();
        }

        if (this.level().isClientSide && this.getDogLevel().isFullKami() && ConfigHandler.ClientConfig.getConfig(ConfigHandler.CLIENT.KAMI_PARTICLES)) {
            for (int i = 0; i < 2; i++) {
                this.level().addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2D);
            }
        }

        if (this.level().isClientSide && this.isInDrunkPose()) {
            if (this.tickCount % 16 == 0) {
                int color = 0x9a24e3;
                // double d0 = (double)(color >> 16 & 255) / 255.0D;
                // double d1 = (double)(color >> 8 & 255) / 255.0D;
                // double d2 = (double)(color >> 0 & 255) / 255.0D;
                this.level().addParticle(ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, color), this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 1, 1, 1);
            }
        }

        // Check if dog bowl still exists every 50t/2.5s, if not remove
        if (this.tickCount % 50 == 0) {
            ResourceKey<Level> dimKey = this.level().dimension();
            Optional<BlockPos> bowlPos = this.getBowlPos(dimKey);

            // If the dog has a food bowl in this dimension then check if it is still there
            // Only check if the chunk it is in is loaded
            if (bowlPos.isPresent() && this.distanceToSqr(Vec3.atBottomCenterOf(bowlPos.get())) >= 64) {
                this.setBowlPos(dimKey, Optional.empty());
            }
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

        if (this.switchNavCooldown > 0) {
            --this.switchNavCooldown;
        }

        if (this.idleAnimHurtCooldown > 0) {
            --this.idleAnimHurtCooldown;
        }

        if (this.drunkTickLeft > 0)
            --this.drunkTickLeft;

        // if (!this.level().isClientSide && this.getMode().canWander()) {
        //     if (!this.getMode().shouldAttack()) {
        //         updateWanderRestState();
        //         boolean invalidated = invalidateWanderCenter(25*25);
        //         if (invalidated) {
        //             this.restrictTo(this.blockPosition(), 12);
        //         }
        //     }
        // }

        if (!this.level().isClientSide && this.isInSittingPose() && !this.isDogResting() && this.tickUntilRest > 0 ) {
            --this.tickUntilRest;
        }

        if (this.getNavigation().isDone()) {
            if (this.pushFromOtherDogResistTick > 0)
                --this.pushFromOtherDogResistTick;
        } else {
            this.pushFromOtherDogResistTick = 20;
        }

        if (this.navigationLock != null)
            this.navigationLock.lockDogNavigation();
    }

    private void tickDogHealing() {
        this.prevHealingTick = this.healingTick;
        this.healingTick += 8;

        if (this.isInSittingPose()) {
            this.healingTick += 4;
        }

        for (var alter : this.alterations) {
            var result = alter.healingTick(this, this.healingTick - this.prevHealingTick);

            if (result.getResult().shouldSwing()) {
                this.healingTick = result.getObject() + this.prevHealingTick;
            }
        }

        if (this.healingTick >= 6000) {
            if (this.getHealth() < this.getMaxHealth()) {
                this.heal(2);
            }

            this.healingTick = 0;
        }
    }

    private void updateDogBeginShake() {
        if (this.level().isClientSide)
            return;
        if (this.isShaking)
            return;
        if (this.isPathFinding())
            return;
        if (!this.onGround())
            return;
        if (!this.canDogDoShakeAnim())
            return;
        if (this.wetSource.isNone())
            return;
        if (this.checkDogInWetSourceAndWetTheDog())
            return;
        if (this.wetSource.flame() && this.isOnFire())
            return;
        
        this.startShakingAndBroadcast(this.wetSource.flame());
    } 

    private void validateGoalsAndTickNonRunningIfNeeded() {
        //Valiate goals
        if (this.level().isClientSide)
            return;
        var availableGoals = this.goalSelector.getAvailableGoals();
        if (!availableGoals.isEmpty())
            availableGoals.clear();
    }

    public boolean triggerAction(TriggerableAction action) {
        return this.dogAi.triggerAction(action);
    }

    public boolean isBusy() {
        if (!this.isDoingFine()) return true;
        if (this.isInSittingPose() && this.forceSit()) return true;
        if (this.dogAi.isBusy())
            return true;
        if (this.hasControllingPassenger())
            return true;
        return false;
    }

    public boolean readyForNonTrivialAction() {
        if (!this.isDoingFine()) return false;
        if (this.isInSittingPose() && this.forceSit()) return false;
        if (!this.dogAi.readyForNonTrivivalAction())
            return false; 
        if (this.hasControllingPassenger())
            return false;
        return true;
    }

    public void clearTriggerableAction() {
        this.dogAi.clearTriggerableAction();
    }

    public boolean triggerActionDelayed(int delay, TriggerableAction action) {
        return this.dogAi.triggerActionDelayed(action, delay);
    }

    public boolean isOnSwitchNavCooldown() {
        return this.switchNavCooldown > 0;
    }

    public boolean canUpdateDogAi() {
        return !this.isImmobile() && this.isEffectiveAi();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        var stack = player.getItemInHand(hand);
        
        if (this.isDefeated()) 
            return this.incapacitatedMananger
                .interact(stack, player, hand);
        
        if (handleOpenDogScreenDedicated(player, stack).shouldSwing())
            return InteractionResult.SUCCESS;
        if (handleTameDogIfNotTamed(player, stack, hand).shouldSwing())
            return InteractionResult.SUCCESS;
        if (dogCheckAndRidePlayer(player, stack).shouldSwing())
            return InteractionResult.SUCCESS;

        if (handleSetWolfArmor(player, stack).shouldSwing())
            return InteractionResult.SUCCESS;
        if (handleRepairWolfArmor(player, stack).shouldSwing())
            return InteractionResult.SUCCESS;
        if (handleUnsetWolfArmor(player, stack, hand).shouldSwing())
            return InteractionResult.SUCCESS;

        var otherHandlerResult = 
            handleAlterationsAndOtherHandlers(player, stack, hand);
        if (otherHandlerResult.isPresent())
            return otherHandlerResult.get();

        if (handleBreeding(player, hand, stack).shouldSwing())
            return InteractionResult.SUCCESS;

        if (handleOpenDogScreen(player).shouldSwing()) {
            return InteractionResult.SUCCESS;
        }

        if (handleDogSitStand(player).shouldSwing())
            return InteractionResult.SUCCESS;

        if (this.level().isClientSide) {
            this.displayToastIfNoPermission(player);
        }

        return InteractionResult.PASS;
    }

    private InteractionResult handleDogSitStand(Player player) {
        if (!this.canInteract(player))
            return InteractionResult.FAIL;
        if (this.isProtesting())
            return InteractionResult.FAIL;
        

        int sit_interval = this.tickCount - this.lastOrderedToSitTick;
        float r = this.getRandom().nextFloat();

        if (!this.level().isClientSide && this.isOrderedToSit()
            && this.isInSittingPose()
            && checkRandomBackflip(r, sit_interval)
            && this.level().getBlockState(this.blockPosition().above()).isAir()) {
                
            this.setStandAnim(DogAnimation.NONE);
            this.setInSittingPose(false);
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
    }

    private InteractionResult handleOpenDogScreen(Player player) {
        if (!player.isShiftKeyDown())
            return InteractionResult.PASS;
        if (!this.canInteract(player))
            return InteractionResult.PASS;

        if (this.level().isClientSide)
            DogNewInfoScreen.open(this);
        return InteractionResult.SUCCESS;
    }

    private InteractionResult handleOpenDogScreenDedicated(Player player, ItemStack stack) {
        if (stack.getItem() != Items.STICK)
            return InteractionResult.FAIL;
        if (!this.isTame())
            return InteractionResult.FAIL;

        if (!this.level().isClientSide)
            return InteractionResult.SUCCESS;
        
        if (this.canInteract(player))
            DogNewInfoScreen.open(this);
        else 
            DogCannotInteractWithScreen.open(this);

        return InteractionResult.SUCCESS;
    }

    private InteractionResult dogCheckAndRidePlayer(Player player, ItemStack stack) {
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

    private InteractionResult handleSetWolfArmor(Player player, ItemStack stack) {
        if (!stack.is(Items.WOLF_ARMOR))
            return InteractionResult.PASS;
        if (this.hasWolfArmor())
            return InteractionResult.PASS;
        if (!this.canInteract(player))
            return InteractionResult.PASS;

        if (this.level().isClientSide)
            return InteractionResult.SUCCESS;
        this.setWolfArmor(stack.copyWithCount(1));
        stack.consume(1, player);
        return InteractionResult.SUCCESS;
    }

    private InteractionResult handleRepairWolfArmor(Player player, ItemStack stack) {
        if (!DogUtil.isScute(stack))
            return InteractionResult.PASS;
        if (!this.hasWolfArmor())
            return InteractionResult.PASS;
        if (!this.canInteract(player))
            return InteractionResult.PASS;
        
        var wolf_armor = wolfArmor();
        if (!wolf_armor.isDamaged())
            return InteractionResult.PASS;

        
        if (this.level().isClientSide)
            return InteractionResult.SUCCESS;
        stack.shrink(1);
        this.playSound(SoundEvents.WOLF_ARMOR_REPAIR);
        int repair_val = DogUtil.getWolfArmorRepairVal(wolf_armor);
        int new_damage_val = wolf_armor.getDamageValue() - repair_val;
        if (new_damage_val < 0) new_damage_val = 0;
        wolf_armor.setDamageValue(new_damage_val);
        return InteractionResult.SUCCESS;
    }

    private InteractionResult handleUnsetWolfArmor(Player player, ItemStack stack, InteractionHand hand) {
        if (!stack.is(Items.SHEARS))
            return InteractionResult.PASS;
        if (!this.hasWolfArmor())
            return InteractionResult.PASS;
        if (!this.canInteract(player))
            return InteractionResult.PASS;

        if (this.level().isClientSide)
            return InteractionResult.SUCCESS;
        stack.hurtAndBreak(1, player, getSlotForHand(hand));
        this.playSound(SoundEvents.ARMOR_UNEQUIP_WOLF);

        var wolf_armor0 = this.wolfArmor();
        this.setWolfArmor(ItemStack.EMPTY);
        this.spawnAtLocation(wolf_armor0);
        return InteractionResult.SUCCESS;
    }

    private Optional<InteractionResult> handleAlterationsAndOtherHandlers(
        Player player, ItemStack stack, InteractionHand hand) {
        
        Optional<IDogFoodHandler> foodHandler = FoodHandler.getMatch(this, stack, player);

        if (foodHandler.isPresent()) {
            return Optional.of(foodHandler.get().consume(this, stack, player));
        }

        InteractionResult interactResult = InteractHandler.getMatch(this, stack, player, hand);

        if (interactResult != InteractionResult.PASS) {
            return Optional.of(interactResult);
        }

        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.processInteract(this, this.level(), player, hand);
            if (result != InteractionResult.PASS) {
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }

    private InteractionResult handleBreeding(Player player, InteractionHand hand, ItemStack stack) {
        if (!stack.is(DoggyItems.BREEDING_BONE.get()))
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

    private InteractionResult handleTameDogIfNotTamed(Player player, ItemStack stack, InteractionHand hand) {
        if (this.isTame())
            return InteractionResult.FAIL;
        if (!isDogTameItem(stack))
            return InteractionResult.FAIL;
        if (this.level().isClientSide)
            return InteractionResult.SUCCESS;
        
        this.usePlayerItem(player, hand, stack);
        boolean alwaysTame = stack.getItem() == DoggyItems.TRAINING_TREAT.get();
        if (alwaysTame || this.random.nextInt(3) == 0) {
            this.tame(player);
            this.navigation.stop();
            this.setTarget((LivingEntity) null);
            this.setOrderedToSit(true);
            this.maxHealth();
            this.level().broadcastEntityEvent(this, doggytalents.common.lib.Constants.EntityState.WOLF_HEARTS);
        } else {
            this.level().broadcastEntityEvent(this, doggytalents.common.lib.Constants.EntityState.WOLF_SMOKE);
        }

        return InteractionResult.SUCCESS;
    }

    private boolean isDogTameItem(ItemStack stack) {
        return stack.is(Items.BONE) || stack.is(DoggyItems.TRAINING_TREAT.get());
    }

    @Override
    public boolean canStillEat() {
        if (this.level().isClientSide)
            return false;
        if (ConfigHandler.SERVER.DISABLE_HUNGER.get()) {
            if(this.getHealth() < this.getMaxHealth()
                && this.hungerManager.saturation() <= 0)
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
            Component.translatable("doggui.invalid_dog.no_permission.title", this.getGenderPronoun())
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
        if (this.dogAutoMount())
            return false;
        var ownerUUID = this.getOwnerUUID();
        if (ownerUUID == null)
            return false;
        if (!ObjectUtils.notEqual(ownerUUID, entity.getUUID()))
            return false;
        if (entity instanceof Dog otherDog
            && !ObjectUtils.notEqual(ownerUUID, otherDog.getOwnerUUID())) {
            return false;
        }
        return true;
    }

    //@Override
    public boolean canTrample(BlockState state, BlockPos pos, float fallDistance) {
        //Temporary to avoid wolf mount bug when trampling crops.
        return false;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_332775_, 
        DifficultyInstance p_332793_, MobSpawnType p_332761_, @Nullable SpawnGroupData p_332782_) {
        return null;
    }

    @Override
    public double getAttributeValue(Holder<Attribute> attrib) {
        if (attrib == Attributes.FOLLOW_RANGE) {
            var ranged_attack = this.getDogRangedAttack();
            if (ranged_attack != null && ranged_attack.isApplicable(this))
                return 20;
        }
        return super.getAttributeValue(attrib);
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        if (dogFallImmune()) {
            return false;
        }
        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.onLivingFall(this, distance, damageMultiplier); // TODO pass source

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }

        // Start: Logic copied from the super call and altered to apply the reduced fall damage to passengers too. #358
        float[] ret = /*net.minecraftforge.common.ForgeHooks.onLivingFall(this, distance, damageMultiplier);
        if (ret == null) return false;*/ (new float[] {distance, damageMultiplier});
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

    public boolean dogFallImmune() {
        return this.alterationProps.fallImmune();
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
        return alterationProps.canBreatheUnderwater();
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
        if (this.fireImmune() && state.is(FluidTags.LAVA))
            return true;

        return super.canStandOnFluid(state);
    }

    @Override
    public boolean ignoreExplosion(Explosion x) {
        for (var alter : this.alterations) {
            var result = alter.negateExplosion(this);
            if (result.shouldSwing()) {
                return true;
            }
        }
        return super.ignoreExplosion(x);
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
        if (!this.getMode().shouldAttack()) {
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
        if (!this.getMode().shouldAttack()) {
            return false;
        }
        if (entityType == EntityType.GHAST && !this.canDogFly())
            return false;

        return true;
    }

    @Override
    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (!this.getMode().shouldAttack()) {
            return false;
        }

        for (IDogAlteration alter : this.alterations) {
            InteractionResult result = alter.shouldAttackEntity(this, target, owner);

            if (result.shouldSwing()) {
                return true;
            } else if (result == InteractionResult.FAIL) {
                return false;
            }
        }

        if (target instanceof Creeper) {
            return false;
        }

        return !EventHandler.isAlliedToDog(target, owner);
    }

    // TODO
    //@Override
//    public boolean canAttack(LivingEntity livingentityIn, EntityPredicate predicateIn) {
//        return predicateIn.canTarget(this, livingentityIn);
//     }

    protected boolean stillIdleOrSitWhenHurt(DamageSource source, float amount) {
        if (this.isDogDrunk())
            return true;
        for (var alt : this.alterations) {
            if (alt.stillIdleOrSitWhenHurt(this, source, amount).shouldSwing())
                return true;
        }
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {

        var attacker = source.getEntity();

        if (this.isDefeated() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            if (ConfigHandler.SERVER.INCAP_VAL_RESET_WHEN_HURT.get()) {
                var currentIncapVal = this.getDogIncapValue();
                if (currentIncapVal < this.getDefaultInitIncapVal()) {
                    this.setDogIncapValue(this.getDefaultInitIncapVal());
                }
            }
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

        float health0 = this.getHealth();

        boolean ret = super.hurt(source, amount);

        float actual_hurt_amount = health0 - this.getHealth();

        if (!this.level().isClientSide) {
            mayStandUpAndPlayHurtAnim(source, actual_hurt_amount, health0);   
            
        }

        if (this.level().isClientSide
            && ConfigHandler.CLIENT.BLOCK_RED_OVERLAY_WHEN_HURT.get()) {
            this.hurtTime = 0;
            this.hurtDuration = 0;
        }
        return ret;
    }

    @Override
    protected void actuallyHurt(DamageSource source, float amount) {
        if (mayWolfArmorAbsorb(source, amount))
            return;
        super.actuallyHurt(source, amount);
    }

    private boolean mayWolfArmorAbsorb(DamageSource source, float amount) {
        if (!canWolfArmorAbsorb(source))
            return false;

        var wolf_armor_stack = this.wolfArmor();
        var damage_val0 = wolf_armor_stack.getDamageValue();
        var damage_max0 = wolf_armor_stack.getMaxDamage();
        wolf_armor_stack.hurtAndBreak(Mth.ceil(amount), this, EquipmentSlot.BODY);
        
        this.playWolfArmorCrackSound(useItem, damage_val0, damage_max0);
        return true;
    }

    private boolean canWolfArmorAbsorb(DamageSource source) {
        return this.hasWolfArmor() && !source.is(DamageTypeTags.BYPASSES_WOLF_ARMOR);
    }

    private void playWolfArmorCrackSound(ItemStack wolf_armor_stack, int damage_val0, int damage_max0) {
        var crackiness0 = Crackiness.WOLF_ARMOR.byDamage(damage_val0, damage_max0);
        var crackiness = Crackiness.WOLF_ARMOR.byDamage(wolf_armor_stack);
        
        if (crackiness == crackiness0)
            return;

        this.playSound(SoundEvents.WOLF_ARMOR_CRACK);
        if (this.level() instanceof ServerLevel sLevel) {
            sLevel.sendParticles(
                new ItemParticleOption(ParticleTypes.ITEM, Items.ARMADILLO_SCUTE.getDefaultInstance()),
                this.getX(), this.getY() + 1.0, this.getZ(),
                20,   0.2, 0.1, 0.2,   0.1
            );
        }
    }

    private void mayStandUpAndPlayHurtAnim(DamageSource source, float real_hurt_amount, float health0) {
        if (this.isDefeated())
            return;
        if (this.isDeadOrDying()) {
            this.setAnim(DogAnimation.HURT_1);
            return;
        }

        if (this.stillIdleOrSitWhenHurt(source, real_hurt_amount))
            return;

        this.dogAnimHurtImpules = true;

        this.setOrderedToSit(false);
        boolean wasSitting = this.isInSittingPose();

        if (wasSitting) {
            this.setStandAnim(DogAnimation.NONE);
            this.setInSittingPose(false);
        }

        if (wasSitting || real_hurt_amount >= 6) {
            this.setAnim(DogAnimation.HURT_1);
            return;
        }

        if (source.getEntity() != null && real_hurt_amount >= 1) {
            this.setAnim(DogAnimation.HURT_2);
            return;
        }
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
        
        for (IDogAlteration alter : this.alterations) {
            alter.doInitialAttackEffects(this, target);
        }

        var attackDamageInst = this.getAttribute(Attributes.ATTACK_DAMAGE);
        var critDamageInst = this.getAttribute(DoggyAttributes.CRIT_CHANCE.holder());

        Set<AttributeModifier> critModifiers = null;

        if (critDamageInst != null && critDamageInst.getValue() > this.getRandom().nextDouble()) {
            var critBonusInst = this.getAttribute(DoggyAttributes.CRIT_BONUS.holder());
            critModifiers = 
                critBonusInst == null ? null 
                    : critBonusInst.getModifiers();
            if (critModifiers != null && attackDamageInst != null)
            critModifiers.forEach(attackDamageInst::addTransientModifier);
        }

        float damage = (float)(attackDamageInst == null ? 0
            : attackDamageInst.getValue());

        //Vanilla hardcoded enchantment effect bonus
        var stack = this.getMainHandItem();
        if (this.level() instanceof ServerLevel serverlevel) {
            damage = EnchantmentHelper.modifyDamage(serverlevel, stack, target, this.damageSources().mobAttack(this), damage);
        }


        if (critModifiers != null && attackDamageInst != null) {
            critModifiers.forEach(attackDamageInst::removeModifier);
        }

        this.doInitialEnchantDamageEffects(this, target);

        boolean flag = target.hurt(this.damageSources().mobAttack(this), damage);
        if (!flag) return false;

        if (this.level() instanceof ServerLevel serverlevel1) {
            EnchantmentHelper.doPostAttackEffects(serverlevel1, target, this.damageSources().mobAttack(this));
        }
        this.statsTracker.increaseDamageDealt(damage);

        if (critModifiers != null) {
            CritEmitterPacket.sendCritEmitterPacketToNearClients(target);
            //Borrow the sound from the player.
            this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 0.5f, 1);
        }

        for (IDogAlteration alter : this.alterations) {
            alter.doAdditionalAttackEffects(this, target);
        }

        if (this.hasEffect(DoggyEffects.NATTO_BITE.holder())
            && target instanceof LivingEntity living) {
            ((NattoBiteEffect)DoggyEffects.NATTO_BITE.get()).doAdditionalAttackEffects(this, living);
        }

        this.setLastHurtMob(target);

        return true;
    }

    @Override
    public boolean killedEntity(ServerLevel level, LivingEntity entity) {
        this.statsTracker.incrementKillCount(entity);
        return true;
    }

    protected void doInitialEnchantDamageEffects(LivingEntity dog, Entity target) {
        // int i = EnchantmentHelper.getFireAspect(this);
        // if (i > 0) {
        //     EntityUtil.setSecondsOnFire(target, i * 4);
        // }

        float knockback = this.getKnockback(target, this.damageSources().mobAttack(this));
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
    public void setRemainingFireTicks(int ticks) {
        for (IDogAlteration alter : this.alterations) {
            InteractionResultHolder<Integer> result = alter.setFire(this, ticks);

            if (result.getResult().shouldSwing()) {
                ticks = result.getObject();
            }
        }

        super.setRemainingFireTicks(ticks);
    }

    @Override
    public boolean fireImmune() {
        return alterationProps.fireImmune();
    }

    @Override
    public boolean canFreeze() {
        return !alterationProps.fireImmune();
    }

    public boolean shouldDogNotAfraidOfFire() {
        for (var alter : this.alterations) {
            var result = alter.shouldNotAfraidOfFire(this);

            if (result.shouldSwing()) {
                return true;
            }
        }

        return false;
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
    public boolean addEffect(MobEffectInstance effectInst, @Nullable Entity adder) {
        if (this.isDefeated())
            return false;
        return super.addEffect(effectInst, adder);
    }

    @Override
    public void forceAddEffect(MobEffectInstance p_147216_, @Nullable Entity adder) {
        if (this.isDefeated())
            return;
        super.forceAddEffect(p_147216_, adder);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectIn) {
        if (this.isDefeated())
            return false;
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
    public boolean canHaveALeashAttachedToIt() {
        return false;
    }

    @Override
    public void setLeashedTo(Entity p_21464_, boolean p_21465_) {
        return;
    }

    @Override
    public boolean isLeashed() {
        return false;
    }

    @Override
    @Nullable
    public Entity getLeashHolder() {
        return null;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    public void checkDespawn() {
        this.noActionTime = 0;
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

    @Override
    public void setTame(boolean tame, boolean update_tame_props) {
        super.setTame(tame, update_tame_props);
        if (tame) {
            var maxHealth = this.getDogLevel().getMaxHealth();
           this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        } else {
           this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
     }

    public void maxHealth() {
        this.setHealth(this.getMaxHealth());
    }

    private boolean authorizedChangingOwner = false;

    @Override
    public void setOwnerUUID(@Nullable UUID uuid) {
        var currentUUID = this.getOwnerUUID();
        boolean isChangingOwner = 
            currentUUID != null
            && ObjectUtils.notEqual(currentUUID, uuid);
        if (isChangingOwner && !authorizedChangingOwner) {
            return;
        }

        super.setOwnerUUID(uuid);

        if (uuid == null) {
            this.setOwnersName((Component) null);
        }
    }

    private boolean authorizedChangingName = false;

    @Override
    public void setCustomName(@Nullable Component name) {
        if (!authorizedChangingName)
            return;

        name = checkDogNameLength(name);
        name = checkDogValidName(name);
        
        super.setCustomName(name);
    }

    public static int MAX_NAME_LEN = 32;
    private Component checkDogNameLength(Component name) {
        if (name == null)
            return null;
        var str = name.getString();
        if (str.length() <= MAX_NAME_LEN)
            return name;
        var new_str = str.substring(0, MAX_NAME_LEN);
        var new_name = Component.literal(new_str)
            .withStyle(name.getStyle());
        return new_name;
    }

    private Component checkDogValidName(Component name) {
        if (name == null)
            return null;
        var str = name.getString();
        var newStr = DogUtil.checkAndCorrectInvalidName(str);
        if (newStr == str) {
            return name;
        }
        return Component.literal(newStr).withStyle(name.getStyle());
    } 

    public void setDogCustomName(@Nullable Component name) {
        this.authorizedChangingName = true;
        this.setCustomName(name);
        this.authorizedChangingName = false;
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

    // @Override
    // public ItemStack getPickedResult(HitResult target) {
    //     return new ItemStack(DoggyItems.DOGGY_CHARM.get());
    // }

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
            } else if (!ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.DISABLE_GENDER) && !this.getGender().canMateWith(entitydog.getGender())) {
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
            child.setTame(true, true);
            child.maxHealth();
        }

        if (partner instanceof Dog && ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.PUPS_GET_PARENT_LEVELS)) {
            child.setLevel(this.getDogLevel().combine(((Dog) partner).getDogLevel()));
        }

        return child;
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel level, Animal otherDog) {
        if (!checkOwnerTrainLimitBeforeBreed()) {
            this.setAge(6000);
            otherDog.setAge(6000);
            this.resetLove();
            otherDog.resetLove();
            this.level().broadcastEntityEvent(this, doggytalents.common.lib.Constants.EntityState.WOLF_SMOKE);

            return;
        }
        super.spawnChildFromBreeding(level, otherDog);
    }

    private boolean checkOwnerTrainLimitBeforeBreed() {
        var owner = this.getOwner();
        if (owner == null)
            return false;
        if (!(owner instanceof ServerPlayer sP))
            return false;
        return EventHandler.isWithinTrainWolfLimit(sP);
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

    @Override
    public float getAgeScale() {
        return 1;
    }

    private boolean changeDimensionAuthorized = false;

    public void authorizeChangeDimension() {
        changeDimensionAuthorized = true;
    }

    @Override
    public Entity changeDimension(DimensionTransition tansition) {
        boolean flag = 
            !changeDimensionAuthorized
            && ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.ALL_DOG_BLOCK_PORTAL);
        if (flag) return null;
        changeDimensionAuthorized = false;
        this.DTN_dogChangingDim = true;
        Entity transportedEntity = super.changeDimension(tansition);
        this.DTN_dogChangingDim = false;
        if (transportedEntity instanceof Dog) {
            DogLocationStorage.get(this.level()).getOrCreateData(this).update((Dog) transportedEntity);
        }
        return transportedEntity;
    }

    //@Override
    public void onRemovedFromWorld() {
        if (this.level() instanceof ServerLevel serverLevel && this.isAlive()) {
            //Force location update when the dog is about to get untracked from world.
            //To be sure, only update existing data and when the dog is still living.
            var data = DogLocationStorage.get(serverLevel).getData(this);
            
            if (data != null) data.update(this);
        }
        //super.onRemovedFromWorld();
    }

    //@Override
    public void onAddedToWorld() {
        if (this.level() instanceof ServerLevel serverLevel && this.isAlive()) {
            var storage = DogLocationStorage.get(serverLevel);
            var data = storage.getOrCreateData(this);
            
            if (data != null) data.update(this);
            storage.getOnlineDogsManager().onDogGoOnline(this);
        }
        //super.onAddedToWorld();
    }

    @Override
    public void remove(Entity.RemovalReason removalReason) {
        if (removalReason.shouldDestroy()) {
            if (this.level() != null && !this.level().isClientSide) {       
                cacheSessionUUID();
                DogLocationStorage.get(this.level()).remove(this);
                if (this.getOwnerUUID() != null)
                    DogRespawnStorage.get(this.level()).putData(this);
            }
        }
        
        super.remove(removalReason);
        for (var x : this.alterations) {
            x.remove(this);
        }
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
            ParticlePackets.DogShakingPacket.sendDogShakingPacket(this, DogShakingData.State.SHAKE_LAVA);
            return;
        }
        this.startShaking();
        ParticlePackets.DogShakingPacket.sendDogShakingPacket(this, DogShakingData.State.SHAKE_WATER);
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

    private Optional<DamageSource> dogDeathCause = Optional.empty();
    @Override
    public void die(DamageSource cause) {
        if (checkAndHandleIncapacitated(cause))
            return;
        
        this.wetSource = WetSource.NONE;
        this.finishShaking();

        this.alterations.forEach((alter) -> alter.onDeath(this, cause));
        
        dogDeathCause = Optional.empty();
        if (ConfigHandler.SERVER.DOG_RESPAWN_INCAPACITATED_WHEN_KILLED.get()
            && !cause.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            dogDeathCause = Optional.ofNullable(cause);
            
            //This value will be saved to respawn storage and
            //restored when the dog respawned, the storage only
            //need to set
            this.setDogIncapValue(this.getInitalDogIncapVal(cause));
        }    
        dogProccessAndBroadcastDieVanilla(cause);
    }

    public Optional<DamageSource> getDogDeathCause() {
        return this.dogDeathCause;
    }

    private void dogProccessAndBroadcastDieVanilla(DamageSource cause) {
        if (this.isRemoved())
            return;
        if (this.dead)
            return;

        var deathMessage = this.getCombatTracker().getDeathMessage();

        this.dead = true;
        this.getCombatTracker().recheckStatus();
        var level = this.level();
        var entity = cause.getEntity();
        if (level instanceof ServerLevel) {
            ServerLevel serverlevel = (ServerLevel)level;
            if (entity == null || entity.killedEntity(serverlevel, this)) {
                this.gameEvent(GameEvent.ENTITY_DIE);
                this.dropAllDeathLoot(serverlevel, cause);
            }

            this.level().broadcastEntityEvent(this, (byte)3);
        }
        
        var owner = this.getOwner();
        if (!this.level().isClientSide && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && owner instanceof ServerPlayer) {
            owner.sendSystemMessage(deathMessage);
        }
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
        this.setMode(EnumMode.INJURED);
        this.setDogHunger(0);
        this.removeAllEffects();
        this.setDogIncapValue(this.getInitalDogIncapVal(source));
        this.updateControlFlags();
        this.dogAi.forceStopAllGoal();
        
        this.getNavigation().stop();
        this.unRide();
        createAndSetIncapSyncState(source);
        if (this.isInWater() || (!this.fireImmune() && this.isInLava())) {
            this.triggerAnimationAction(new DogDrownAction(this));
        } else
        this.setAnim(this.incapacitatedMananger.getAnim());

        var owner = this.getOwner();
        if (owner != null) sendIncapacitatedMsg(owner, source);
        this.incapacitatedMananger.setIncapMsg(
            source.getLocalizedDeathMessage(this).getString()
        );

        this.wetSource = WetSource.NONE;
        this.finishShaking();
    }

    public int getInitalDogIncapVal(DamageSource source) {
        var difficulty = this.level().getDifficulty();
        if (difficulty == Difficulty.PEACEFUL)
            return this.getDefaultInitIncapVal()/2;
        if (difficulty == Difficulty.EASY)
            return this.getDefaultInitIncapVal();
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
            return this.getDefaultInitIncapVal();
        var fatal_damage = this.lastHurt;
        if (fatal_damage <= 0)
            return this.getDefaultInitIncapVal();
        fatal_damage = getDamageAfterArmorAbsorb(source, fatal_damage);
        fatal_damage = getDamageAfterMagicAbsorb(source, fatal_damage);
        if (fatal_damage <= 0)
            return this.getDefaultInitIncapVal();
        int multipler = difficulty == Difficulty.HARD ? 2 : 1;
        int additional_incap = Mth.floor(multipler * fatal_damage);
        return this.getDefaultInitIncapVal() + additional_incap;
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
            Component.translatable(EnumMode.INJURED.getUnlocalisedName())
            .withStyle(
                Style.EMPTY
                .withBold(true)
                .withColor(0xd60404)
            )
        );
    
        msg.append(msg01);
        owner.sendSystemMessage(msg);
    }

    public IncapacitatedSyncState createIncapSyncState(DamageSource source) {
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
        
        return (new IncapacitatedSyncState(type, BandaidState.NONE, poseId));
    }

    private void createAndSetIncapSyncState(DamageSource source) {
        var state = createIncapSyncState(source);
        this.setIncapSyncState(state);
    }

    @Override
    public double getFluidJumpThreshold() {
        float defeated_threshold = this.isInLava() ? 0.9f : 1;
        return this.isDefeated() ? defeated_threshold : super.getFluidJumpThreshold();
    }

    @Override
    protected void jumpInLiquid(TagKey<Fluid> tagKey) {
        if (this.getNavigation().canFloat()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, (double)0.04f, 0.0));
        } else {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.3, 0.0));
        }
    }

    @Override
    public void dropEquipment() {
        this.alterations.forEach((alter) -> alter.dropInventory(this));
    }

    // @Override
    // public void invalidateCaps() {
    //     super.invalidateCaps();
    //     this.alterations.forEach((alter) -> alter.invalidateCapabilities(this));
    // }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        addDTNAdditionalSavedData(compound);
    }

    public void addDTNAdditionalSavedData(CompoundTag compound) {
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

        compound.putString("classicalVariant", this.getClassicalVar().getId().toString());
        compound.putString("mode", this.getMode().getSaveName());
        compound.putString("dogGender", this.getGender().getSaveName());
        compound.putFloat("dogHunger", this.getDogHunger());
        compound.putInt("dogIncapacitatedValue", this.getDogIncapValue());
        this.getOwnersName().ifPresent((comp) -> {
            NBTUtil.putTextComponent(compound, "lastKnownOwnerName", comp);
        });

        this.getSkinData().save(compound);
        compound.putBoolean("willObey", this.willObeyOthers());
        compound.putBoolean("friendlyFire", this.canOwnerAttack());
        compound.putBoolean("regardTeamPlayers", this.regardTeamPlayers());
        compound.putBoolean("forceSit", this.forceSit());
        compound.putByte("lowHealthStrategy", this.getLowHealthStrategy().getId());
        compound.putByte("combatReturnStrategy", this.getCombatReturnStrategy().getId());
        compound.putBoolean("crossOriginTp", this.crossOriginTp());
        compound.putBoolean("patrolTargetLock", this.patrolTargetLock());
        compound.putBoolean("hideDogArmor", this.hideArmor());
        compound.putInt("dogSize", this.getDogSize().getId());
        compound.putInt("level_normal", this.getDogLevel().getLevel(Type.NORMAL));
        compound.putInt("level_kami", this.getDogLevel().getLevel(Type.KAMI));
        NBTUtil.writeItemStack(this.registryAccess(), compound, "fetchItem", this.getBoneVariant());
        var wolf_armor = this.wolfArmor();
        if (wolf_armor != null && !wolf_armor.isEmpty())
            NBTUtil.writeItemStack(this.registryAccess(), compound, "wolfArmorItem", wolf_armor);

        DimensionDependantArg<Optional<BlockPos>> bedsData = this.dogFabricHelper.getBedPos();

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

        DimensionDependantArg<Optional<BlockPos>> bowlsData = this.dogFabricHelper.getBowlPos();

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

        if (this.getMode().canWander() && this.hasRestriction()) {
            var restrict = this.getRestrictCenter();
            int restrict_r = (int) this.getRestrictRadius();
            if (restrict != null) {
                var wanderTg = new CompoundTag();
                wanderTg.putInt("wanderX", restrict.getX());
                wanderTg.putInt("wanderY", restrict.getY());
                wanderTg.putInt("wanderZ", restrict.getZ());
                wanderTg.putInt("wanderR", restrict_r);
                compound.put("dogWanderCenter", wanderTg);
            }
        }

        //Duplication Detection
        if (!this.level().isClientSide && !DTN_dogChangingDim) {
            var uuid = this.getUUID();
            var ownerUUID = this.getOwnerUUID();
            if (uuid != null && ownerUUID != null) {
                var backupUUIDTag = new CompoundTag();
                backupUUIDTag.putUUID("dtn_uuid_owner", ownerUUID);
                backupUUIDTag.putUUID("dtn_uuid_self", uuid);
                writeSessionUUIDToCompound(uuid, backupUUIDTag);
                compound.put("DTN_DupeDetect_UUID", backupUUIDTag);
            }
        }
    }

    public void addNonDTNAdditionalData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void load(CompoundTag compound) {

        this.authorizedChangingOwner = true;
        this.authorizedChangingName = true;

        super.load(compound);

        this.authorizedChangingOwner = false;
        this.authorizedChangingName = false;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        var newTlInstLs = new ArrayList<TalentInstance>();

        tryReadAllTalents(compound, newTlInstLs);
        
        this.dogSyncedDataManager.talents().clear();
        this.dogSyncedDataManager.talents().addAll(newTlInstLs);
        this.dogSyncedDataManager.setTalentsDirty();

        var newAccInstLs = new ArrayList<AccessoryInstance>();
        tryReadAllAccessories(compound, newAccInstLs);

        this.dogSyncedDataManager.accessories().clear();
        this.dogSyncedDataManager.accessories().addAll(newAccInstLs);
        this.dogSyncedDataManager.setAccessoriesDirty();

        var artifactsList = new ArrayList<DoggyArtifactItem>(3);
        
        try {
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
        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.error("Failed to load artifacts : " + e);
        }
        this.dogFabricHelper.setArtifacts(artifactsList);

        try {
            this.spendablePoints.markForRefresh();
        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.error("Failed to init alteration: " + e.getMessage());
            e.printStackTrace();
		}

        try {
            this.setGender(EnumGender.bySaveName(compound.getString("dogGender")));

            if (compound.contains("mode", Tag.TAG_STRING)) {
            this.setMode(EnumMode.bySaveName(compound.getString("mode")));
            }

            var dogSkinData = DogSkinData.readFromTag(compound);
            this.setDogSkinData(dogSkinData);

            if (compound.contains("wolfArmorItem", Tag.TAG_COMPOUND)) {
                this.setWolfArmor(NBTUtil.readItemStack(this.registryAccess(), compound, "wolfArmorItem"));
            }
            if (compound.contains("fetchItem", Tag.TAG_COMPOUND)) {
                this.setBoneVariant(NBTUtil.readItemStack(this.registryAccess(), compound, "fetchItem"));
            }

            this.setClassicalVar(
                ClassicalVar.bySaveName(compound.getString("classicalVariant"))
            );
            this.setHungerDirectly(compound.getFloat("dogHunger"));
            this.setDogIncapValue(compound.getInt("dogIncapacitatedValue"));
            this.setOwnersName(NBTUtil.getTextComponent(compound, "lastKnownOwnerName"));
            this.setWillObeyOthers(compound.getBoolean("willObey"));
            this.setCanPlayersAttack(compound.getBoolean("friendlyFire"));
            this.setRegardTeamPlayers(compound.getBoolean("regardTeamPlayers"));
            this.setForceSit(compound.getBoolean("forceSit"));
            this.setCrossOriginTp(compound.getBoolean("crossOriginTp"));
            this.setPatrolTargetLock(compound.getBoolean("patrolTargetLock")); 
            this.setHideArmor(compound.getBoolean("hideDogArmor"));
            var low_health_strategy_id = compound.getByte("lowHealthStrategy");
            this.setLowHealthStrategy(LowHealthStrategy.fromId(low_health_strategy_id));
            var combat_return_strategy_id = compound.getByte("combatReturnStrategy");
            this.setCombatReturnStrategy(CombatReturnStrategy.fromId(combat_return_strategy_id));
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
            int level_kami = 0;
            if (compound.contains("level_normal", Tag.TAG_ANY_NUMERIC)) {
                level_normal = compound.getInt("level_normal");
            }
            if (compound.contains("level_kami", Tag.TAG_ANY_NUMERIC)) {
                level_kami = compound.getInt("level_kami");          
            } 
            //Old
            else if (compound.contains("level_dire", Tag.TAG_ANY_NUMERIC)) {
                level_kami = compound.getInt("level_dire");    
            }
            this.dogFabricHelper.setDogLevel(new DogLevel(level_normal, level_kami));
        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.error("Failed to load levels: " + e.getMessage());
            e.printStackTrace();
        }

        DimensionDependantArg<Optional<BlockPos>> bedsData = this.dogFabricHelper.getBedPos().copyEmpty();

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
            }
        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.error("Failed to load beds: " + e.getMessage());
            e.printStackTrace();
        }

        this.dogFabricHelper.setBedPos(bedsData);

        DimensionDependantArg<Optional<BlockPos>> bowlsData = this.dogFabricHelper.getBowlPos().copyEmpty();

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
            }
        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.error("Failed to load bowls: " + e.getMessage());
            e.printStackTrace();
        }

        this.dogFabricHelper.setBowlPos(bowlsData);

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

        try {
            if (this.getMode().canWander() 
                && compound.contains("dogWanderCenter", Tag.TAG_COMPOUND)) {
                var wanderTg = compound.getCompound("dogWanderCenter");
                var restrictPos = new BlockPos(
                    wanderTg.getInt("wanderX"),
                    wanderTg.getInt("wanderY"),
                    wanderTg.getInt("wanderZ")
                );
                int restrict_r = wanderTg.getInt("wanderR");
                restrict_r = Math.max(0, restrict_r);
                this.restrictTo(restrictPos, restrict_r);
            }
        } catch (Exception e) {
            
        }

        //Duplication Detection
        boolean duplicate_detected = false;
        if (!this.level().isClientSide) {
            try {
                duplicate_detected = detectDuplicate(compound);
            } catch (Exception e) {

            }
        }
        if (duplicate_detected) {
            int strategy = ConfigHandler.SERVER.DUPLICATION_RESOLVE_STRATEGY.get();
            if (strategy == 0 || strategy == 1) {
                this.untame();  

                if (!this.isAddedToWorld()) {
                    this.setRemoved(RemovalReason.DISCARDED);
                } else {
                    this.remove(RemovalReason.DISCARDED);
                }
            }
            compound.putBoolean("DTN_DupeDetect_marked", true);
            if (strategy == 0)
                throw new IllegalStateException(
                    "This dog has been restored from third-party storage which may leads to duplications."
                );
            return;
        } else {
            detectedDuplicateVertified = true;
        }

        if (!this.level().isClientSide) {
            try {
                checkAndRecorrectOwner(compound);
            } catch (Exception e) {
            }
        }

        if (!this.level().isClientSide) {
            try {
                this.setAnim(DogAnimation.NONE);
            } catch (Exception e) {
            }
        }
    }

    private void tryReadAllTalents(CompoundTag compound, ArrayList<TalentInstance> target) {
        try {
            if (compound.contains("talents", Tag.TAG_LIST)) {
                ListTag talentList = compound.getList("talents", Tag.TAG_COMPOUND);
    
                for (int i = 0; i < talentList.size(); ++i) {
                    try {
                        TalentInstance.readInstance(this, talentList.getCompound(i)).ifPresent(target::add);
                    } catch (Exception e)  {
                        DoggyTalentsNext.LOGGER.error(e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.error("Failed to load talents : " + e);
        }
    }

    private void tryReadAllAccessories(CompoundTag compound, ArrayList<AccessoryInstance> target) {
        try {
            if (compound.contains("accessories", Tag.TAG_LIST)) {
                ListTag accessoryList = compound.getList("accessories", Tag.TAG_COMPOUND);
    
                for (int i = 0; i < accessoryList.size(); ++i) {
                    try {
                        AccessoryInstance.readInstance(accessoryList.getCompound(i)).ifPresent(target::add);
                    } catch (Exception e)  {
                        DoggyTalentsNext.LOGGER.error(e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            DoggyTalentsNext.LOGGER.error("Failed to load accessories : " + e);
        }
    }

    private boolean detectedDuplicateVertified = false;
    private boolean DTN_dogChangingDim = false;
    private boolean detectDuplicate(CompoundTag tag) {
        if (detectedDuplicateVertified)
            return false; 
        if (ConfigHandler.SERVER.DISABLE_PRESERVE_UUID.get())
            return false;
        if (!tag.contains("DTN_DupeDetect_UUID", Tag.TAG_COMPOUND))
            return false;
        if (tag.contains("DTN_DupeDetect_marked"))
            return tag.getBoolean("DTN_DupeDetect_marked");
        var backupUUIDTag = tag.getCompound("DTN_DupeDetect_UUID");
        var uuid = backupUUIDTag.getUUID("dtn_uuid_self");
        var ownerUUID = backupUUIDTag.getUUID("dtn_uuid_owner");
        UUID sessionUUID = null;
        if (backupUUIDTag.hasUUID("session_uuid")) {
            sessionUUID = backupUUIDTag.getUUID("session_uuid");
        }
        if (uuid == null || ownerUUID == null)
            return false;

        // Only detect if dog is not added to world while having the same uuid
        // for now. This is a pretty unlikely case though.
        if (this.isAddedToWorld() && uuid.equals(this.getUUID())) {
            return false;
        }
        
        boolean isDuplicate = false;
        
        if (!isDuplicate && checkRespawnStorageForDuplicate(uuid, ownerUUID))
            isDuplicate = true;
        if (!isDuplicate && checkLocationStorageForDuplicate(uuid, ownerUUID, sessionUUID))
            isDuplicate = true;
        if (!isDuplicate)
            return false;
        
        DoggyTalentsNext.LOGGER.warn(
            "Duplicated Dog Detected! dog_uuid=[" 
            + uuid.toString()
            + "] owner_uuid=["
            + ownerUUID.toString()
            + "]"
        );
        return true;
    }

    private boolean checkRespawnStorageForDuplicate(UUID uuid, UUID ownerUUID) {
        var storage = DogRespawnStorage.get(this.level());
        if (storage == null)
            return false;
        var data = storage.getData(uuid);
        if (data == null)
            return false;
        var ownerUUID0 = data.getOwnerId();
        if (ownerUUID0 == null)
            return false;

        if (ObjectUtils.notEqual(ownerUUID0, ownerUUID))        
            return false;
        
        return true;
    }

    private boolean checkLocationStorageForDuplicate(UUID uuid, UUID ownerUUID, UUID sessionUUID) {
        var storage = DogLocationStorage.get(this.level());
        if (storage == null) 
            return false;
        var data = storage.getData(uuid);
        if (data == null)
            return false;
        var ownerUUID0 = data.getOwnerId();
        if (ownerUUID0 == null) 
            return false;
        
        if (ObjectUtils.notEqual(ownerUUID0, ownerUUID))        
            return false;
        
        var correctSessionUUID = data.getSessionUUID();
        if (correctSessionUUID == null)
            return false;
        return ObjectUtils.notEqual(correctSessionUUID, sessionUUID);
    }

    private UUID cachedSessionUUID = null;

    private void writeSessionUUIDToCompound(UUID uuid, CompoundTag tag) {
        if (cachedSessionUUID != null) {
            tag.putUUID("session_uuid", cachedSessionUUID);
            cachedSessionUUID = null;
            return;
        }
        var level = this.level();
        if (level == null)
            return;
        var storage = DogLocationStorage.get(level);
        if (storage == null) 
            return;
        var data = storage.getData(uuid);
        if (data == null)
            return;
        var sessionUUID = data.getSessionUUID();
        if (sessionUUID == null)
            return;
        tag.putUUID("session_uuid", sessionUUID);
    }

    private void cacheSessionUUID() {
        var uuid = this.getUUID();
        var level = this.level();
        if (level == null)
            return;
        var storage = DogLocationStorage.get(level);
        if (storage == null) 
            return;
        var data = storage.getData(uuid);
        if (data == null)
            return;
        var sessionUUID = data.getSessionUUID();
        if (sessionUUID == null)
            return;
        this.cachedSessionUUID = sessionUUID;
    }

    private void checkAndRecorrectOwner(CompoundTag tag) {
        if (!tag.contains("DTN_DupeDetect_UUID", Tag.TAG_COMPOUND))
            return;
        var backupUUIDTag = tag.getCompound("DTN_DupeDetect_UUID");
        var ownerUUID = backupUUIDTag.getUUID("dtn_uuid_owner");
        if (!ObjectUtils.notEqual(ownerUUID, this.getOwnerUUID()))
            return;
        boolean prevAuthorized = this.authorizedChangingOwner;
        this.authorizedChangingOwner = true;
        this.setOwnerUUID(ownerUUID);
        this.authorizedChangingOwner = prevAuthorized;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        // if (ARTIFACTS.get().equals(key)) {
        //     this.refreshAlterations();
        // }

        // if (DOG_LEVEL.get().equals(key)) {
        //     this.spendablePoints.markForRefresh();
        //     float h = this.getDogLevel().getMaxHealth();
        //     if (h != this.getMaxHealth())
        //         this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(h);
        //     this.maxHealth();
        // }

        // if (DOG_SIZE.get().equals(key)) {
        //     this.refreshDimensions();
        // }

        // if (this.level().isClientSide && CUSTOM_SKIN.get().equals(key)) {
        //     this.setClientSkin(
        //         DogTextureManager.INSTANCE
        //             .getDogSkin(
        //                 this.getSkinData().getHash()));
        // }

        if (ANIMATION.equals(key)) {
            this.animationManager.onAnimationChange(getAnim());
        }
        if (ANIM_SYNC_TIME.equals(key)) {
            this.animationManager.onSyncTimeUpdated();
        }

        if (HUNGER_INT.equals(key)) {
            this.hungerManager.onHungerUpdated(this.getDogHunger());
        }

        // if (!this.level().isClientSide && MODE.get().equals(key)) {
        //     var mode = getMode();
        //     this.incapacitatedMananger.onModeUpdate(mode);
        //     if (mode == EnumMode.INCAPACITATED) {
        //         this.removeAttributeModifier(Attributes.MOVEMENT_SPEED, HUNGER_MOVEMENT);
        //     }
        //     updateWanderState(mode);
        // }
    }

    public void onDogSyncedDataUpdated(boolean talents, boolean accessories) {
        if (talents || accessories) {
            this.refreshAlterations();
            this.spendablePoints.markForRefresh();
        }
        if (talents) {
            if (this.level().isClientSide)
                ClientEventHandler.onDogTalentUpdated(this);
        }
        if (accessories) {
            if (this.level().isClientSide) {
                this.clientAccessories = new ArrayList<>(this.getAccessories());
                this.clientAccessories.sort(AccessoryInstance.RENDER_SORTER);
            }
        }
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.dogSyncedDataManager.onStartBeingSeenBy(player);
        //Fabric
        getDogFabricHelper().onStartBeingSeenBy(player);
    }

    private void updateWanderState(EnumMode mode) {
        if (!mode.canWander()) {
            this.clearRestriction();
            return;
        }
        var restrictPos = this.blockPosition();
        int restrictRadius = 12;
        var bowlPosOptional = this.getBowlPos();
        if (bowlPosOptional.isPresent()) {
            var bowlPos = bowlPosOptional.get();
            if (bowlPos.distSqr(this.blockPosition()) < 64) {
                restrictRadius = 5;
                restrictPos = bowlPos;
            }
        }
        this.restrictTo(restrictPos, restrictRadius);
    }

    private boolean invalidateWanderCenter(int distanceSqr) {
        if (!this.hasRestriction())
            return false;
        var restrict_pos = this.getRestrictCenter();
        if (restrict_pos == null)
            return false;
        if (restrict_pos.distSqr(this.blockPosition()) >= distanceSqr) {
            this.clearRestriction();
            return true;
        }
        return false;
    }

    public void refreshAlterations() {
        //safely remove all alterations
        for (var inst : this.alterations) {
            inst.remove(this);
        }
        this.alterations.clear();
        this.foodHandlers.clear();
        this.alterationProps = new DogAlterationProps();
        this.dogRangedAttackManager = IDogRangedAttackManager.NONE;

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
            inst.props(this, alterationProps);
            inst.init(this);
            if (this.dogRangedAttackManager == IDogRangedAttackManager.NONE) {
                if (inst.getRangedAttack().isPresent())
                    this.dogRangedAttackManager = inst.getRangedAttack().get();
            }
        }

        onPropsUpdated();
    }

    private void onPropsUpdated() {
        if (this.level().isClientSide)
            return;
        this.dogArmors.onPropsUpdated(alterationProps);
        if (!alterationProps.canUseTools())
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }

    public IDogRangedAttackManager getDogRangedAttack() {
        if (this.dogRangedAttackManager == null)
            return IDogRangedAttackManager.NONE;
        return this.dogRangedAttackManager;
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
        return this.dogSyncedDataManager.accessories();
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

        this.dogSyncedDataManager.accessories().add(accessoryInst);
        this.dogSyncedDataManager.setAccessoriesDirty();

        return true;
    }

    @Override
    public List<AccessoryInstance> removeAccessories() {
        List<AccessoryInstance> removed = new ArrayList<>(this.getAccessories());

        this.dogSyncedDataManager.accessories().clear();
        this.dogSyncedDataManager.setAccessoriesDirty();
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

    public ClassicalVar getClassicalVar() {
        return this.getDogFabricHelper().getClassicalVar();
    }

    public void setClassicalVar(ClassicalVar val) {
        this.getDogFabricHelper().setClassicalVar(val);
    }

    public EnumGender getGender() {
        return this.dogFabricHelper.getDogGender();
    }

    public void setGender(EnumGender collar) {
        this.dogFabricHelper.setDogGender(collar);
    }

    @Override
    public EnumMode getMode() {
        return this.dogFabricHelper.getDogMode();
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
        this.dogFabricHelper.setDogMode(collar);
    }

    public Optional<BlockPos> getBedPos() {
        return this.getBedPos(this.level().dimension());
    }

    public Optional<BlockPos> getBedPos(ResourceKey<Level> registryKey) {
        return this.dogFabricHelper.getBedPos().getOrDefault(registryKey, Optional.empty());
    }

    public void setBedPos(@Nullable BlockPos pos) {
        this.setBedPos(this.level().dimension(), pos);
    }

    public void setBedPos(ResourceKey<Level> registryKey, @Nullable BlockPos pos) {
        this.setBedPos(registryKey, WorldUtil.toImmutable(pos));
    }

    public void setBedPos(ResourceKey<Level> registryKey, Optional<BlockPos> pos) {
        this.dogFabricHelper.setBedPos(this.dogFabricHelper.getBedPos().copy().set(registryKey, pos));
    }

    public Optional<BlockPos> getBowlPos() {
        return this.getBowlPos(this.level().dimension());
    }

    public Optional<BlockPos> getBowlPos(ResourceKey<Level> registryKey) {
        return this.dogFabricHelper.getBowlPos().getOrDefault(registryKey, Optional.empty());
    }

    public void setBowlPos(@Nullable BlockPos pos) {
        this.setBowlPos(this.level().dimension(), pos);
    }

    public void setBowlPos(ResourceKey<Level> registryKey, @Nullable BlockPos pos) {
        this.setBowlPos(registryKey, WorldUtil.toImmutable(pos));
    }

    public void setBowlPos(ResourceKey<Level> registryKey, Optional<BlockPos> pos) {
        this.dogFabricHelper.setBowlPos(this.dogFabricHelper.getBowlPos().copy().set(registryKey, pos));
    }

    @Override
    public int getDefaultInitIncapVal() {
        return 64;
    }

    @Override
    public int getMaxDogIncapVal() {
        return 256;
    }

    @Override
    public int getDogIncapValue() {
        return this.entityData.get(INCAP_VAL);
    }

    @Override
    public void setDogIncapValue(int val) {
        val = Mth.clamp(val, 0, getMaxDogIncapVal());
        this.entityData.set(INCAP_VAL, val);
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
        this.hungerManager.addHunger(add);
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
    }

    @Override
    public void heal(float add) {
        //🥴
        if (add <= 0)
            return;
        // var add1 = net.minecraftforge.event.ForgeEventFactory.onLivingHeal(this, add);
        // add = Math.max(add1, add);
        
        float h = this.getHealth();
        if (h > 0.0F) {
           this.setHealth(h + add);
        }
     }

    public void setTarget(@Nullable LivingEntity target) {
        var oldTarget = this.getTarget();
        super.setTarget(target);
        var newTarget = this.getTarget();
        if (oldTarget != newTarget) {
            for (var alt : this.alterations) {
                alt.onDogSetTarget(this, newTarget, oldTarget);
            }
        }
    }

    public boolean hasCustomSkin() {
        return !Strings.isNullOrEmpty(this.getSkinData().getHash());
    }

    public DogSkinData getSkinData () {
        return this.dogFabricHelper.getDogSkin();
    }

    public void setDogSkinData(DogSkinData data) {
        if (data == null) {
            data = DogSkinData.NULL;
        }
        this.dogFabricHelper.setDogSkin(data);
    }

    @Override
    public DogLevel getDogLevel() {
        return this.dogFabricHelper.getDogLevel();
    }

    public void setLevel(DogLevel level) {
        this.dogFabricHelper.setDogLevel(level);
    }

    public IncapacitatedSyncState getIncapSyncState() {
        return this.dogFabricHelper.getIncapSyncState();
    }

    public void setIncapSyncState(IncapacitatedSyncState state) {
        this.dogFabricHelper.setIncapSyncState(state);
    }

    @Override
    public void increaseLevel(DogLevel.Type typeIn) {
        var copy = this.getDogLevel().copy();
        copy.incrementLevel(typeIn);
        this.setLevel(copy);
    }

    @Override
    public void setDogSize(DogSize size) {
        this.dogFabricHelper.setDogSize(size);
    }

    @Override
    public DogSize getDogSize() {
        return this.dogFabricHelper.getDogSize();
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

    public CombatReturnStrategy getCombatReturnStrategy() {
        int msb = this.getDogFlag(8192) ? 1 : 0;
        int lsb = this.getDogFlag(4096) ? 1 : 0;
        return CombatReturnStrategy.fromId(msb * 2 + lsb);
    }

    public void setCombatReturnStrategy(CombatReturnStrategy strategy) {
        int id = strategy.getId();
        boolean lsb = (id & 1) == 1;
        boolean msb = ((id >> 1) & 1) == 1;
        this.setDogFlag(8192, msb);
        this.setDogFlag(4096, lsb);
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

    public static enum RestingState { NONE, LYING, BELLY }

    public RestingState getDogRestingState() {
        boolean isResting = this.getDogFlag(32768);
        if (!isResting)
            return RestingState.NONE;
        boolean bellyUp = this.getDogFlag(65536);
        return bellyUp ? RestingState.BELLY : RestingState.LYING;
    }

    public void setDogRestingState(RestingState val) {
        if (val == null) val = RestingState.NONE;
        switch (val) {
        default:
            this.setDogFlag(32768, false);
            this.setDogFlag(65536, false);
            break;
        case LYING:
            this.setDogFlag(32768, true);
            this.setDogFlag(65536, false);
            break;
        case BELLY:
            this.setDogFlag(32768, true);
            this.setDogFlag(65536, true);
            break;
        }
    }

    public boolean isDogResting() {
        return this.getDogRestingState() != RestingState.NONE;
    }

    public boolean patrolTargetLock() {
        return this.getDogFlag(512);
    }

    public boolean isInDrunkPose() {
        return this.getDogFlag(131072);
    }

    public void setInDrunkPose(boolean val) {
        this.setDogFlag(131072, val);
    }

    public void setForcedActionAnim(boolean val) {
        this.setDogFlag(262144, val);
    }

    public boolean forcedWhenNoneAnim() {
        return getDogFlag(262144);
    }

    public void setDrunkTicks(int ticks) {
        this.drunkTickLeft = ticks;
    }

    public boolean isDogDrunk() {
        return this.drunkTickLeft > 0;
    }

    public void setPatrolTargetLock(boolean val) {
        this.setDogFlag(512, val);
    }

    public boolean hideArmor() {
        return this.getDogFlag(2048);
    }

    public void setHideArmor(boolean val) {
        this.setDogFlag(2048, val);
    }

    public boolean dogAutoMount() {
        return this.getDogFlag(16384);
    }

    public void setDogAutoMount(boolean val) {
        this.setDogFlag(16384, val);
    }

    public boolean wantsToRest() {
        return this.tickUntilRest <= 0 && this.getRandom().nextFloat() < 0.02f;
    }

    public void resetTickTillRest() {
        this.tickUntilRest = 30 * 20 + this.getRandom().nextInt(271) * 20; 
    }

    public List<TalentInstance> getTalentMap() {
        return this.dogSyncedDataManager.talents();
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

            var newTalent = talent.getDefault(level);
            this.dogSyncedDataManager.talents().add(newTalent);
        } else {
            int previousLevel = inst.level();
            if (previousLevel == level) {
                return InteractionResult.PASS;
            }

            inst.setLevel(level);
            inst.set(this, previousLevel);

            if (level <= 0) {
                final int remove_id = selected_id;
                if (remove_id >= 0) 
                    this.dogSyncedDataManager.talents().remove(remove_id);
            }
        }
        this.dogSyncedDataManager.setTalentsDirty();
        return InteractionResult.SUCCESS;
    }

    public List<DoggyArtifactItem> getArtifactsList() {
        var array = this.dogFabricHelper.getArtifacts();
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


    public void modifyArtifact(Consumer<List<DoggyArtifactItem>> modify) {
        var list = new ArrayList<>(this.dogFabricHelper.getArtifacts()); modify.accept(list); this.dogFabricHelper.setArtifacts(list);
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

    public void untame() {
        this.navigation.stop();
        this.clearTriggerableAction();
        this.dogAi.forceStopAllGoal();
        this.setOrderedToSit(false);
        this.setHealth(8);
        this.setDogCustomName(null);

        this.dogSyncedDataManager.talents().clear();
        this.dogSyncedDataManager.setTalentsDirty();

        this.authorizedChangingOwner = true;
        this.setTame(false, true);
        this.setOwnerUUID(null);
        this.setWillObeyOthers(false);
        this.setCanPlayersAttack(true);
        this.setMode(EnumMode.DOCILE);
        this.authorizedChangingOwner = false;
    }

    public void migrateOwner(UUID newOwnerUUID) {
        this.navigation.stop();
        this.clearTriggerableAction();
        this.dogAi.forceStopAllGoal();
        
        this.setMode(EnumMode.DOCILE);
        this.authorizedChangingOwner = true;
        this.setOwnerUUID(newOwnerUUID);
        this.authorizedChangingOwner = false;
    }

    public boolean canSpendPoints(int amount) {
        return this.getSpendablePoints() >= amount || this.getAccessory(DoggyAccessories.GOLDEN_COLLAR.get()).isPresent();
    }

    // When this method is changed the cache may need to be updated at certain points
    private final int getSpendablePointsInternal() {
        int totalPoints = 15 + this.getDogLevel().getLevel(Type.NORMAL) + this.getDogLevel().getLevel(Type.KAMI);
        for (TalentInstance entry : this.getTalentMap()) {
            totalPoints -= entry.getTalent().getCummulativeCost(entry.level());
        }
        return totalPoints;
    }

    public int getSpendablePoints() {
        return this.spendablePoints.get();
    }

    // @Override
    // public boolean canRiderInteract() {
    //     return true;
    // }

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
        var type = WalkNodeEvaluator.getPathTypeStatic(this, b0.mutable());
        if (type == PathType.WALKABLE) {
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
        if (this.isDogResistingPush()) {
            mayDogResistPush();
        }
        super.travel(positionIn);
        if (this.isDogFlying()) {
            var moveVec = this.getDeltaMovement();
            double down = moveVec.y;
            
            double gravity = -0.112102;
            var attrib = this.getAttribute(Attributes.GRAVITY);
            if (attrib != null)
                gravity = -attrib.getValue();
            
            if (down < 0 ) {
                down = Math.min(down * 0.7, gravity);
            } else {
                down *= 0.7;
            }
            this.setDeltaMovement(moveVec.x*0.67, down, moveVec.z*0.67);
        }
        this.addMovementStat(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo)   ;
    }

    private void mayDogResistPush() {
        if (!this.onGround())
            return;
        if (!this.isDoingFine()) return;
        if (this.isVehicle()) return;
        if (this.isPathFinding()) return;

        final double max_XZ_push_len = 0.0005;
        var move = this.getDeltaMovement();
        if (this.tickCount % 2 == 0) {
            this.setDeltaMovement(0, move.y, 0);
            return;
        }
        double moveX = move.x();
        double moveZ = move.z();
        double moveXZ_lSqr = moveX*moveX + moveZ*moveZ;
        if (moveXZ_lSqr <= max_XZ_push_len * max_XZ_push_len)
            return;
        double moveXZ_l = Math.sqrt(moveXZ_lSqr);
        moveX = moveX / moveXZ_l * max_XZ_push_len;
        moveZ = moveZ / moveXZ_l * max_XZ_push_len;

        this.setDeltaMovement(new Vec3(moveX, move.y(), moveZ));
    }

    @Override
    protected float getFlyingSpeed() {
        return this.isDogFlying() ? 0.49f : super.getFlyingSpeed();
    }

    public boolean canDogFly() {
        return alterationProps.canFly();
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
        
        if (forward > 0 && !isDogRidingConstraintToGround()) {
            float l = forward;
            downward = -l*Mth.sin(rider.getXRot() * ((float)Math.PI / 180F));
            forward = l*Mth.cos(rider.getXRot() * ((float)Math.PI / 180F));
        } 
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
        // If moving backwards half  the speed
        if (forward <= 0.0F) {
            forward *= 0.5F;
        }
        
        return new Vec3(straf, downward, forward);
    }

    protected boolean isDogRidingConstraintToGround() {
        if (this.isInWater() && this.canSwimUnderwater())
            return false;
        if (this.canDogFly())
            return false;
        return true;
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
        double jumpValue = this.getAttribute(DoggyAttributes.JUMP_POWER.holder()).getValue() * this.getBlockJumpFactor() * this.jumpPower; //TODO do we want getJumpFactor?
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
    public boolean isPushedByFluid() {
        // if (this.fireImmune() && type == NeoForgeMod.LAVA_TYPE.value())
        //     return false;
        for (var alter : this.alterations) {
            InteractionResult result = alter.canResistPushFromFluidType();

            if (result.shouldSwing()) {
                return false;
            }
        }
        return super.isPushedByFluid();
    }

    @Override
    public MutableComponent getTranslationKey(Function<EnumGender, String> function) {
        return Component.translatable(function.apply(!ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.DISABLE_GENDER) ? this.getGender() : EnumGender.UNISEX));
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
        if (anim == null) {
            this.sitAnim = DogAnimation.SIT_DOWN;
            return;
        } 
        this.sitAnim = anim;
    }

    public DogAnimation getStandAnim() {
        return this.standAnim;
    }

    public void setStandAnim(DogAnimation anim) {
        if (anim == null) {
            this.standAnim = DogAnimation.STAND_QUICK;
            return;
        }
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
    public void setNavigation(PathNavigation p) {
        super.setNavigation(p);
        this.switchNavCooldown = 5;
        if (p instanceof IDogNavLock lock) {
            this.currentNavigation = p;
            this.navigationLock = lock;
        }
    }

    @Override
    public PathNavigation getDefaultNavigation() {
        return this.defaultNavigation;
    }

    @Override
    public MoveControl getDefaultMoveControl() {
        return this.defaultMoveControl;
    }

    public boolean isDefaultNavigation() {
        return this.defaultNavigation == this.getNavigation();
    }

    @Override
    protected PathNavigation createNavigation(Level p_21480_) {
        var dogPathNav = new DogPathNavigation(this, p_21480_);
        this.currentNavigation = dogPathNav;
        this.navigationLock = dogPathNav;
        this.navigationLock.lockDogNavigation();
        return dogPathNav;
    }

    public float getPathfindingMalus(PathType type) {
        switch (type) {
        default:
            break;
        case WATER:
        case WATER_BORDER:
            if (shouldDogOmitWaterPathWeight())
                return 0;
            break;
        case LAVA:
        case DAMAGE_FIRE:
        case DANGER_FIRE:
            if (fireImmune())
                return 0;
            break;
        case DOOR_WOOD_CLOSED:
            if (this.canDogPassGate())
                return 8;
            break;
        case DANGER_POWDER_SNOW:
        case POWDER_SNOW:
            return -1;
        }
        return super.getPathfindingMalus(type);
    }

    private boolean shouldDogOmitWaterPathWeight() {
        if (isDogFollowingSomeone())
            return true;
        if (!this.isInWater())
            return false;
        if (this.canBreatheUnderwater())
            return true;
        if (this.canSwimUnderwater() && !this.isLowAirSupply())
            return true;
        return false;
    }

    public boolean shouldDogBlockFloat() {
        if (fireImmune() && isInLava())
            return true;
        return false;
    }

    private boolean isDogFollowingSomeone;

    public boolean isDogFollowingSomeone() {
        return isDogFollowingSomeone;    
    }

    public void setDogFollowingSomeone(boolean val) {
        this.isDogFollowingSomeone = val;
    }

    private boolean isDogResistingPush;
    
    public void setDogResistingPush(boolean val) {
        this.isDogResistingPush = val;
    }

    public boolean isDogResistingPush() {
        return this.isDogResistingPush;
    }

    public List<IDogAlteration> getAlterations() {
        return this.alterations;
    }

    @Override
    public boolean canSwimUnderwater() {
        return alterationProps.canSwimUnderwater();
    }

    @Override
    public boolean canDogWearArmor() {
        return this.alterationProps.canWearArmor();
    }

    @Override
    public boolean canDogUseTools() {
        return this.alterationProps.canUseTools();
    }

    public DogArmorItemHandler dogArmors() {
        return this.dogArmors;
    }

    public ItemStack wolfArmor() {
        var stack = this.getItemBySlot(EquipmentSlot.BODY);
        if (stack == null || !stack.is(Items.WOLF_ARMOR))
            return ItemStack.EMPTY;
        return stack;
    }

    public void setWolfArmor(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            this.setItemSlot(EquipmentSlot.BODY, ItemStack.EMPTY);
        }
        if (!stack.is(Items.WOLF_ARMOR))
            return;
        this.setItemSlot(EquipmentSlot.BODY, stack);
    }

    public boolean hasWolfArmor() {
        return !this.wolfArmor().isEmpty();
    }

    public Iterable<ItemStack> getHandSlots() {
        if (!this.canDogUseTools() || this.mouthStack == null)
            return List.of(); 
        return List.of(this.mouthStack);
    }

    public Iterable<ItemStack> getArmorSlots() {
        if (!this.canDogWearArmor())
            return List.of();
        return this.dogArmors.armors();
    }

    public Iterable<ItemStack> getArmorAndBodyArmorSlots() {
        return getArmorSlots();
    }

    public ItemStack getItemBySlot(EquipmentSlot slot) {
        var type = slot.getType();
        boolean getArmor = 
            type == EquipmentSlot.Type.HUMANOID_ARMOR
            && (alterationProps.canWearArmor() || this.level().isClientSide);
        if (getArmor) {
            return this.dogArmors.getArmorFromSlot(slot);
        }
        boolean getMouth = 
            slot == EquipmentSlot.MAINHAND 
            && (alterationProps.canUseTools() || this.level().isClientSide)
            && mouthStack != null;
        if (getMouth) {
            return this.mouthStack;
        }
        if (slot == EquipmentSlot.BODY && this.wolfArmorStack != null &&
            this.wolfArmorStack.is(Items.WOLF_ARMOR)) {
            return this.wolfArmorStack;
        }
        return ItemStack.EMPTY;
    }

    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        this.verifyEquippedItem(stack);
        if (trySetDogArmorSlot(slot, stack))
            return;
        if (trySetDogToolSlot(slot, stack))
            return;
        if (trySetWolfArmor(slot, stack))
            return;
    }

    private boolean trySetDogArmorSlot(EquipmentSlot slot, ItemStack stack) {
        if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR)
            return false;
        if (!this.level().isClientSide && !this.canDogWearArmor())
            return false;
        var oldStack =  this.dogArmors.getArmorFromSlot(slot);
        this.dogArmors.setArmorInSlot(stack, slot);
        onEquipItem(slot, oldStack, stack);
        return true;
    }

    private boolean trySetDogToolSlot(EquipmentSlot slot, ItemStack stack) {
        if (slot != EquipmentSlot.MAINHAND)
            return false;
        if (!this.level().isClientSide && !this.canDogUseTools())
            return false;
        var oldStack =  this.mouthStack == null ? ItemStack.EMPTY : this.mouthStack;
        this.mouthStack = stack;
        onEquipItem(slot, oldStack, stack);
        return true;
    }

    private boolean trySetWolfArmor(EquipmentSlot slot, ItemStack stack) {
        if (slot != EquipmentSlot.BODY)
            return false;
        boolean is_not_empty_not_wolf_armor = 
            stack != null
            && !stack.isEmpty()
            && !stack.is(Items.WOLF_ARMOR);

        if (is_not_empty_not_wolf_armor)
            return false;
        if (stack == null) stack = ItemStack.EMPTY;
        
        var oldStack =  this.wolfArmorStack == null ? ItemStack.EMPTY : this.wolfArmorStack;
        this.wolfArmorStack = stack;
        onEquipItem(slot, oldStack, stack);
        return true;
    }

    @Override
    public void onEquipItem(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack) {
        var type = slot.getType();
        boolean is_slot_should_be_handled = 
            type == EquipmentSlot.Type.HUMANOID_ARMOR
            || type == EquipmentSlot.Type.ANIMAL_ARMOR;    

        if (!is_slot_should_be_handled)
            return;
        super.onEquipItem(slot, oldStack, newStack);
    }
    
    @Override
    public boolean canTakeItem(ItemStack stack) {
        if (checkEligibleArmorItemAndAvailableSlot(stack))
            return true;
        return false;
    }

    private boolean checkEligibleArmorItemAndAvailableSlot(ItemStack stack) {
        if (!this.canDogWearArmor())
            return false;
        if (!(stack.getItem() instanceof ArmorItem))
            return false;
        var slot = this.getEquipmentSlotForItem(stack);
        if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR)
            return false;
        var current = this.getItemBySlot(slot);
        if (current != null && !current.isEmpty())
            return false;
        return true;
    }

    //Dog dont drop from Lootables.
    @Override
    protected void dropAllDeathLoot(ServerLevel p_348524_, DamageSource source) {
        this.dropEquipment();
    }

    public boolean isLowAirSupply() {
        return this.getAirSupply() < this.getMaxAirSupply() * 0.3;
    }

    public boolean isDogLowHealth() {
        return this.getHealth() < 6;
    }

    @Override
    public void setDogSwimming(boolean s) {
        this.isDogSwimming = s;
    }

    public boolean isDogSwimming() {
        return this.isDogSwimming;
    }

    private boolean isDogCurious;
    public boolean isDogCurious() {
        return isDogCurious;
    }

    public void setDogCurious(boolean val) {
        this.isDogCurious = val;
    }

    @Override
    public void setDogFlying(boolean s) {
        this.setDogFlag(1024, s);
    }

    public boolean isDogFlying() {
        return this.getDogFlag(1024);
    }
    public boolean isLowHunger() {
        return this.hungerManager.isLowHunger();
    }

    @Override
    protected void updateControlFlags() {
        boolean incapBlockedMove = this.isDefeated() && !this.incapacitatedMananger.canMove();
        boolean animBlockedMove = this.animAction != null && this.animAction.blockMove();
        boolean animBlockedLook = this.animAction != null && this.animAction.blockLook();
        boolean notControlledByPlayer = !(this.getControllingPassenger() instanceof ServerPlayer);
        boolean notRidingBoat = !(this.getVehicle() instanceof Boat);
        this.dogAi.setLockedFlag(Goal.Flag.MOVE, 
            notControlledByPlayer && !incapBlockedMove && !animBlockedMove);
        this.dogAi.setLockedFlag(Goal.Flag.JUMP, notControlledByPlayer && notRidingBoat);
        this.dogAi.setLockedFlag(Goal.Flag.LOOK, notControlledByPlayer && !animBlockedLook);
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
        if (this.pushFromOtherDogResistTick <= 0)
            return false;
        if (this.isDefeated())
            return false;
        if (isDogInFluid())
            return false;
        if (!(target instanceof Dog otherDog)) {
            return false;
        }
        if (!isPushingTeammateDog(otherDog))
            return false;
        if (otherDog.isDogFlying() && this.isDogFlying())
            return false;
        boolean oneDogStillNotOnGround =
            !this.onGround()
            || !otherDog.onGround();
        return oneDogStillNotOnGround;
    }

    private boolean isPushingTeammateDog(Dog otherDog) {
        var owner0 = this.getOwner();
        var owner1 = otherDog.getOwner();
        if (owner0 == null || owner1 == null)
            return false;
        if (owner0 == owner1)
            return true;
        
        return owner0.isAlliedTo(owner1);
    }

    private boolean isDogInFluid() {
        return this.getMaxFluidHeight().isPresent();
    }

    @Override
    public void push(Entity source) {
        if (source.getVehicle() == this
            || this.getVehicle() == source)
            return;
        if (this.isVehicle() && !this.hasControllingPassenger())
            Entity_push(source);
        else {
            if (isDogCurious())
                setDogCurious(false);
            super.push(source);
        }
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

    public PathType getBlockPathTypeViaAlterations(BlockPos pos) {
        var blockType = WalkNodeEvaluator.getPathTypeStatic(
            this, 
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

    public boolean canDogPassGate() {
        for (var alt : this.alterations) {
            if (alt.canDogPassGate(this).shouldSwing())
                return true;
        }
        return false;
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

    public void setAnimSyncTime(int val) {
        this.entityData.set(ANIM_SYNC_TIME, val);
    }

    public int getAnimSyncTime() {
        return this.entityData.get(ANIM_SYNC_TIME);
    }

    private AnimationAction animAction;

    public void triggerAnimationAction(AnimationAction action) {
        if (animAction != null) 
            animAction.stop();
        this.animAction = action;
        if (this.animAction != null) {
            this.getNavigation().stop();
            updateControlFlags();
            if (this.animAction.blockMove()) {
                forceStopAllGoalWithFlag(Goal.Flag.MOVE);
            }
            if (this.animAction.blockLook()) {
                forceStopAllGoalWithFlag(Goal.Flag.LOOK);
            }
            this.animAction.start();
        }
    }

    private void forceStopAllGoalWithFlag(Goal.Flag flag) {
        this.dogAi.forceStopAllGoalWithFlag(flag);
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
        if (this.isInDrunkPose()) {
            this.setDogPose(DogPose.REST_BELLY);
            return;
        }
        if (this.isInSittingPose()) {
            var restState = this.getDogRestingState();
            if (restState == RestingState.LYING) {
                this.setDogPose(DogPose.REST);
                return;
            }
            if (restState == RestingState.BELLY) {
                this.setDogPose(DogPose.REST_BELLY);
                return;
            }
            this.setDogPose(this.isLying() ? DogPose.LYING_2 : DogPose.SIT);
            return;
        }
        if (this.isDogFlying() && !this.forcedWhenNoneAnim()) {
            this.setDogPose(DogPose.FLYING);
            return;
        }
        this.setDogPose(DogPose.STAND);
    }

    public float getClientAnimatedYBodyRotInRadians() {
        if (!this.level().isClientSide)
            return this.yBodyRot * Mth.DEG_TO_RAD;
        return this.yBodyRot * Mth.DEG_TO_RAD + ClientEventHandler.getAnimatedYRot(this);
    }

    //Client
    public DogSkin getClientSkin() {
        if (ConfigHandler.CLIENT.ALWAYS_RENDER_CLASSICAL.get())
            return DogSkin.CLASSICAL;
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

    //Client
    public Optional<ItemStack> getMouthItemForRender() {
        if (this.hasBone()) {
            var renderStack = this.getBoneVariant();
            var throwableItem = this.getThrowableItem();
            if (throwableItem != null) {
                var customStack = throwableItem.getCustomRenderStack(renderStack);
                if (customStack != null)
                    renderStack = customStack;
            }
            return Optional.of(renderStack);
        }

        var stack = this.getMainHandItem();
        if (stack != null && !stack.isEmpty()) {
            return Optional.of(stack);
        }

        return Optional.empty();
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

    /**
     * 2 bit for strategy
     */
    public static enum CombatReturnStrategy {
        STANDARD(0),
        FAR(1),
        NONE(2);

        public static final CombatReturnStrategy[] VALUES = 
            Arrays.stream(CombatReturnStrategy.values())
            .sorted(
                Comparator.comparingInt(CombatReturnStrategy::getId)
            ).toArray(size -> {
                return new CombatReturnStrategy[size];
            });

        private final byte id;

        private CombatReturnStrategy(int id) {
            this.id = (byte) id;
        }

        public byte getId() {
            return this.id;
        }

        public String getUnlocalisedTitle() {
            return "dog.combat_return_strategy." + this.getId();
        }

        public String getUnlocalisedInfo() {
            return "dog.combat_return_strategy." + this.getId() + ".help";
        }

        public static CombatReturnStrategy fromId(int id) {
            if (!(0 <= id && id <= 2)) return NONE;
            return VALUES[id];
        }

        public CombatReturnStrategy prev() {
            int i = this.getId() - 1;
            if (i < 0) {
                i = VALUES.length - 1;
            }
            return VALUES[i];
        }
    
        public CombatReturnStrategy next() {
            int i = this.getId() + 1;
            if (i >= VALUES.length) {
                i = 0;
            }
            return VALUES[i];
        }
    }

    //Fabric
    private final DogFabricHelper dogFabricHelper = new DogFabricHelper(this);
    public DogFabricHelper getDogFabricHelper() { return dogFabricHelper; }

    public void onFabricDataUpdated(SyncType<?> type) {
        if (type == SyncTypes.ARTIFACTS) {
            this.refreshAlterations();
        }

        if (type == SyncTypes.DOG_LEVEL) {
            this.spendablePoints.markForRefresh();
            float h = this.getDogLevel().getMaxHealth();
            if (h != this.getMaxHealth())
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(h);
        }

        if (type == SyncTypes.DOG_SIZE) {
            this.refreshDimensions();
        }

        if (this.level().isClientSide && type == SyncTypes.DOG_SKIN) {
            this.setClientSkin(
                DogTextureManager.INSTANCE
                    .getDogSkin(
                        this.getSkinData().getHash()));
        }

        if (!this.level().isClientSide && type == SyncTypes.DOG_MODE) {
            var mode = getMode();
            this.incapacitatedMananger.onModeUpdate(mode);
            if (mode == EnumMode.INJURED) {
                this.hungerManager.onBeingIncapacitated();
            }
            updateWanderState(mode);
        }
    }

    private boolean isAddedToWorld = false;
    public boolean isAddedToWorld() {
        return isAddedToWorld;
    }
    public void setAddedToWorld(boolean val) {
        this.isAddedToWorld = val;
    }

    public Optional<TagKey<Fluid>> getMaxFluidHeight() {
        return this.fluidHeight.object2DoubleEntrySet()
            .stream().max(java.util.Comparator.comparingDouble(Object2DoubleMap.Entry::getDoubleValue)).map(Object2DoubleMap.Entry::getKey);
    }

    // @Override
    // protected PortalInfo findDimensionEntryPoint(ServerLevel serverLevel) {
    //     var owner = this.getOwner();
    //     if (owner != null) {
    //         this.portalEntrancePos = owner.blockPosition();
    //     } else {
    //         this.portalEntrancePos = BlockPos.ZERO;
    //     }
    //     double tpScale = DimensionType.getTeleportationScale(this.level().dimensionType(), serverLevel.dimensionType());
    //     var destPos = serverLevel.getWorldBorder().clampToBounds(this.getX() * tpScale, this.getY(), this.getZ() * tpScale);
    //     return new PortalInfo(Vec3.atBottomCenterOf(destPos), this.getDeltaMovement(), this.getYRot(), this.getXRot());
    // }
}
