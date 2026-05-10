package doggytalents.common.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;

import doggytalents.DogVariants;
import doggytalents.DoggyAccessories;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.api.anim.DogAnimation;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.block.DogBedMaterialManager;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogAllyCheck;
import doggytalents.common.entity.DogDuplicationDetection;
import doggytalents.common.entity.DogSleepOnManager;
import doggytalents.common.entity.ai.WolfBegAtTreatGoal;
import doggytalents.common.entity.ai.triggerable.DogBackFlipAction;
import doggytalents.common.entity.ai.triggerable.DogPlayTagAction;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.TrainWolfToDogData;
import doggytalents.common.storage.DogLocationStorage;
import doggytalents.common.storage.DogRespawnStorage;
import doggytalents.common.storage.OnlineDogLocationManager;
import doggytalents.common.talent.HunterDogTalent;
import doggytalents.common.talent.PackPuppyTalent;
import doggytalents.common.util.DogLocationStorageMigration;
import doggytalents.common.util.LangUtil;
import doggytalents.common.util.Util;
import doggytalents.common.util.dogpromise.DogPromiseManager;
import doggytalents.common.util.dogpromise.chunk.DTNForcedChunkManager;
import doggytalents.common.util.dogpromise.promise.DogBatchTeleportToDimensionPromise;
import doggytalents.common.util.dogpromise.promise.DogHoldChunkToTeleportPromise;
import doggytalents.common.variant.util.DogVariantUtil;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.animal.wolf.WolfVariant;
import net.minecraft.world.entity.animal.wolf.WolfVariants;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEgg;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.entity.player.CanContinueSleepingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import doggytalents.common.network.PacketDistributor;

public class EventHandler {

    @SubscribeEvent
    public void onServerTickEnd(final ServerTickEvent.Post event) {

        //if (event.phase != Phase.END) return;

        DogPromiseManager.tick();
        DogLocationStorage.get(event.getServer()).getOnlineDogsManager().tick();
        DogSleepOnManager.tickServer(event.getServer());
    }

    @SubscribeEvent
    public void onServerStop(final ServerStoppingEvent event) {
        DogPromiseManager.forceStop();
        DTNForcedChunkManager.onServerStop();
        var overworld = event.getServer().getLevel(Level.OVERWORLD);
        if (overworld != null)
            DogLocationStorage.get(overworld).onServerStop(event);
        DogSleepOnManager.onServerStop(event.getServer());
    }

    @SubscribeEvent
    public void onServerStopped(final ServerStoppedEvent event) {
        var overworld = event.getServer().getLevel(Level.OVERWORLD);
        if (overworld != null)
            DogLocationStorage.get(overworld).onServerStopped(event);
    }

    @SubscribeEvent
    public void onWolfRightClickWithTreat(final PlayerInteractEvent.EntityInteract event) {
        var level = event.getLevel();
        var stack = event.getItemStack();
        var target = event.getTarget();
        var owner = event.getEntity();

        if (stack.getItem() != DoggyItems.TRAINING_TREAT.get()) 
            return;
        if (!(target instanceof Wolf wolf)) return;
        event.setCanceled(true);
        
        if (!checkValidWolf(wolf, owner)) {
            event.setCancellationResult(InteractionResult.FAIL);
            return;
        }

        if (level.isClientSide()) {
            PacketHandler.send(PacketDistributor.SERVER.noArg(), 
                new TrainWolfToDogData(wolf.getId(), wolf.yBodyRot, wolf.yHeadRot)
            );
        }

        event.setCancellationResult(InteractionResult.SUCCESS);
    }

    public static void checkAndTrainWolf(Player trainer, Wolf wolf, float yBodyRot, float yHeadRot) {
        var level = trainer.level();
        if (level.isClientSide())
            return;
        
        var stack = trainer.getMainHandItem();
        if (stack.getItem() != DoggyItems.TRAINING_TREAT.get()) 
            return;

        if (!checkValidWolf(wolf, trainer))
            return;

        if (!isWithinTrainWolfLimit(trainer)) {
            level.broadcastEntityEvent(wolf, doggytalents.common.lib.Constants.EntityState.WOLF_SMOKE);
            return;
        }

        if (!trainer.getAbilities().instabuild) {
            stack.shrink(1);
        }

        tameWolfIfNeccessary(wolf, trainer);
        rotateWolfIfNecceassary(wolf, yHeadRot, yBodyRot);
        trainWolf(wolf, trainer, level);
    }

    private static void rotateWolfIfNecceassary(Wolf wolf, float yHeadRot, float yBodyRot) {
        float wanted_yBodyRot = yHeadRot;
        float wanted_dYBodyRot =  Mth.degreesDifference(yBodyRot, wanted_yBodyRot);
        float approaching_dYBodyRot = Mth.clamp(wanted_dYBodyRot, -25, 25);
        float approaching_yBodyRot = yBodyRot + approaching_dYBodyRot;
        approaching_yBodyRot = Mth.wrapDegrees(approaching_yBodyRot);
        wolf.yBodyRot = approaching_yBodyRot;
    }

    private static boolean checkValidWolf(Wolf wolf, Player owner) {
        if (!wolf.isAlive()) return false;
        boolean trainUntamed = !ConfigHandler.SERVER.DISABLE_TRAIN_UNTAMED_WOLF.get();
        boolean condition1 = trainUntamed && !wolf.isTame();
        boolean condition2 = wolf.isTame() && wolf.isOwnedBy(owner);
        
        return condition1 || condition2;
    }

    public static boolean isWithinTrainWolfLimit(Player owner) {
        int limit = ConfigHandler.SERVER.TRAIN_WOLF_LIMIT.get();
        if (limit <= 0)
            return true;
        
        var server = owner.level().getServer();
        var locStore = DogLocationStorage.get(server);
        var respawnStore = DogRespawnStorage.get(server);
        
        int locCnt = locStore.getDogs(owner)
            .collect(Collectors.toList()).size();
        int respawnCnt = respawnStore.getDogs(owner.getUUID())
            .collect(Collectors.toList()).size();
        
        int totalTrained = locCnt + respawnCnt;
        if (totalTrained >= limit)
            return false;
        
        return true;
    }

    public static void tameWolfIfNeccessary(Wolf wolf, Player owner) {
        //Using training treat to convert a vanilla wolf to DTN wolf
        //also counts as taming that wolf.
        if (wolf.isTame())
            return;
        wolf.tame(owner);
    }

    public static void trainWolf(Wolf wolf, Player owner, Level level) {
        if (!(level instanceof ServerLevel sLevelForCreate)) {
            throw new IllegalStateException("trainWolf called on client side");
        }
        Dog dog = DoggyEntityTypes.DOG.get().create(sLevelForCreate, net.minecraft.world.entity.EntitySpawnReason.BREEDING);
        if (dog == null) {
            throw new IllegalStateException("Creator function for the dog returned \"null\"");
        }
        dog.setTame(true, true);
        dog.setOwnerUUID(owner.getUUID());
        dog.maxHealth();
        dog.setOrderedToSit(false);
        dog.setAge(wolf.getAge());
        dog.setPos(wolf.getX(), wolf.getY(), wolf.getZ());
        dog.setYHeadRot(wolf.yBodyRot);
        dog.setYBodyRot(wolf.yBodyRot);
        dog.setYRot(wolf.yBodyRot);

        var wolf_collar_color = wolf.getCollarColor();
        var color = wolf_collar_color.getTextureDiffuseColor();
        var dog_collar = DoggyAccessories.DYEABLE_COLLAR_THICC.get()
            .create(color);
        if (dog_collar != null)
            dog.addAccessory(dog_collar);
        migrateWolfVariant(wolf, dog);
        migrateWolfArmor(wolf, dog);
            
        if (wolf.hasCustomName()) {
            dog.setDogCustomName(wolf.getCustomName());
        }
        
        var wolf_uuid = wolf.getUUID();
        wolf.discard();

        if (level instanceof ServerLevel sL)
            migrateUUID(wolf_uuid, dog, sL);

        level.addFreshEntity(dog);

        dog.triggerAnimationAction(new DogBackFlipAction(dog));
    }

    private static void migrateUUID(UUID uuid, Dog dog, ServerLevel level) {
        if (ConfigHandler.SERVER.DISABLE_PRESERVE_UUID.get())
            return;
        if (level.getEntity(uuid) != null)
            return;
        dog.setUUID(uuid);
    }

    private static void migrateWolfVariant(Wolf wolf, Dog dog) {
        var wolfVariantHolder = wolf.get(net.minecraft.core.component.DataComponents.WOLF_VARIANT);
        var wolfVarKey = wolfVariantHolder != null ? wolfVariantHolder.unwrapKey().orElse(WolfVariants.PALE) : WolfVariants.PALE;
        var dog_variant = DogVariantUtil.fromVanila(wolfVarKey);
        boolean random_var_on_pale = 
            dog_variant == DogVariantUtil.getDefault()
            && ConfigHandler.SERVER.RANDOM_VAR_ON_PALE.get();
        if (random_var_on_pale) {
            var vanilla_variants = List.of(
                DogVariants.PALE.get(), DogVariants.RUSTY.get(), DogVariants.WOOD.get(), 
                DogVariants.CHESTNUT.get(), DogVariants.STRIPED.get(), DogVariants.ASHEN.get(), 
                DogVariants.SNOWY.get(), DogVariants.SPOTTED.get(), DogVariants.BLACK.get());
            dog_variant = LangUtil.getRandomItem(
                wolf.getRandom(), vanilla_variants).get();
        }
        dog.setDogVariant(dog_variant);
    }

    private static void migrateWolfArmor(Wolf wolf, Dog dog) {
        if (!wolf.isWearingBodyArmor())
            return;
        var armor_stack = wolf.getBodyArmorItem().copyWithCount(1);
        dog.setWolfArmor(armor_stack);
    }

    @SubscribeEvent
    public void onEntitySpawn(final EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        var level = entity.level();
        if (level.isClientSide())
            return;

        if (entity instanceof AbstractSkeleton) {
            AbstractSkeleton skeleton = (AbstractSkeleton) entity;
            skeleton.goalSelector.addGoal(3, new AvoidEntityGoal<>(skeleton, Dog.class, 6.0F, 1.0D, 1.2D)); // Same goal as in AbstractSkeletonEntity
        } else if (entity instanceof Wolf 大神) {
            大神.goalSelector.addGoal(9, new WolfBegAtTreatGoal(大神, 8));
        }
    }

    @SubscribeEvent
    public void playerLoggedIn(final PlayerLoggedInEvent event) {
        if (event.getEntity().level().isClientSide())
            return;

        if (isEnableStarterBundle()) {

            Player player = event.getEntity();

            CompoundTag tag = player.getPersistentData();

            if (!tag.contains(Player.PERSISTED_NBT_TAG)) {
                tag.put(Player.PERSISTED_NBT_TAG, new CompoundTag());
            }

            CompoundTag persistTag = tag.getCompoundOrEmpty(Player.PERSISTED_NBT_TAG);

            if (!persistTag.getBooleanOr("gotDTStartingItems", false)) {
                persistTag.putBoolean("gotDTStartingItems", true);

                player.getInventory().add(new ItemStack(DoggyItems.STARTER_BUNDLE.get()));
            }
        }
    }

    private boolean isEnableStarterBundle() {
        final var retMut = new MutableBoolean(false);
        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            if (ConfigHandler.ClientConfig
                .getConfig(ConfigHandler.CLIENT.ENABLE_STARTER_BUNDLE_BY_DEFAULT))
                retMut.setTrue();
        }
        if (retMut.getValue())
            return true;
        return ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.STARTING_ITEMS);
    }

    // @SubscribeEvent
    // public void onLootDrop(final LootingLevelEvent event) {
    //     HunterDogTalent.onLootDrop(event);
    // }

    @SubscribeEvent
    public void onProjectileHit(final ProjectileImpactEvent event) {
        var levelChecker = event.getProjectile();
        if (levelChecker == null)
            return;
        var level = levelChecker.level();
        if (level.isClientSide())
            return;

        var hitResult = event.getRayTraceResult();
        if (hitResult instanceof EntityHitResult hitEntity)
            proccessEntityProjectileHitEvent(event, hitEntity);
        else if (hitResult instanceof BlockHitResult hitBlock)
            proccessBlockProjectileHitEvent(event, hitBlock);
    }    

    private void proccessEntityProjectileHitEvent(final ProjectileImpactEvent event, EntityHitResult hit) {
        var entity = hit.getEntity();
        if (entity instanceof Dog dog) {
            proccessDogProjectileHitEvent(event, hit, dog);
        }
    }

    private void proccessDogProjectileHitEvent(final ProjectileImpactEvent event, EntityHitResult hit, Dog dog) {
        var projectile = event.getProjectile();
        var projectileOnwer = projectile.getOwner();
        if (projectileOnwer == null) return;
        var dogOwner = dog.getOwner();
        
        if (projectile instanceof Snowball) {
            if (
                projectileOnwer == dogOwner
                && ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.PLAY_TAG_WITH_DOG)
                && !dog.isBusy()
                && !dog.isOrderedToSit()
            ) 
                dog.triggerAction(new DogPlayTagAction(dog, dogOwner));
            return;
        }
    }

    private void proccessBlockProjectileHitEvent(final ProjectileImpactEvent event, BlockHitResult hit) {
        var projectile = event.getProjectile();
        if (!(projectile instanceof ThrownEgg))
            return;

        var level = projectile.level();
        var pos = hit.getBlockPos();
        var dir = hit.getDirection();
        if (dir != Direction.UP)
            return;

        var state = level.getBlockState(pos);
        if (!state.is(Blocks.WATER_CAULDRON))
            return;
        
        var state_under = level.getBlockState(pos.below());
        if (!WalkNodeEvaluator.isBurningBlock(state_under))
            return;
        
        var resultStack = new ItemStack(DoggyItems.ONSEN_TAMAGO.get());
        var resultEntity = new ItemEntity(
            level, 
            pos.getX() + 0.5, 
            pos.getY() + 1.0, 
            pos.getZ() + 0.5, resultStack);
        level.addFreshEntity(resultEntity);

        projectile.playSound(SoundEvents.TURTLE_EGG_CRACK, 0.5F, 0.9F + 
            level.getRandom().nextFloat() * 0.2F);
    }

    public final int COLLECT_RADIUS = 26;
    @SubscribeEvent
    public void onEntityChangeDimension(EntityTravelToDimensionEvent event) {
        var entity = event.getEntity();
        if (entity.level().isClientSide()) return;
        if ((entity instanceof ServerPlayer owner)) {
            onOwnerChangeDimension(event, owner);
        }
    }

    private void onOwnerChangeDimension(EntityTravelToDimensionEvent event, ServerPlayer owner) {
        if (!(owner.level() instanceof ServerLevel sLevel)) return;
        var mcServer = sLevel.getServer();
        var fromLevel = sLevel;
        var toLevel = mcServer.getLevel(event.getDimension());
        if (fromLevel == toLevel) return;
        
        var crossOriginTpList = fromLevel
            .getEntitiesOfClass(
                Dog.class, 
                owner.getBoundingBox().inflate(COLLECT_RADIUS, 4, COLLECT_RADIUS),
                d -> isDogReadyToTeleport(d, owner)
            );
        if (crossOriginTpList.isEmpty()) return;

        DogPromiseManager.addPromiseWithOwnerAndStartImmediately(
            new DogBatchTeleportToDimensionPromise(
                crossOriginTpList, 
                fromLevel, owner.getUUID(), event.getDimension(), d -> isDogReadyToTeleport(d, owner))
            , owner);
    }

    public final int MIN_DISTANCE_TO_TRIGGER_TELEPORT_SQR = 400;
    @SubscribeEvent
    public void onOwnerTeleport(EntityTeleportEvent event) {
        var entity = event.getEntity();
        if (!(entity instanceof ServerPlayer owner)) return;
        if (!(owner.level() instanceof ServerLevel sLevel)) return;
        
        if (this.distanceTooShortToTeleport(event.getPrev(), event.getTarget()))
            return;

        var crossOriginTpList = sLevel
            .getEntitiesOfClass(
                Dog.class, 
                owner.getBoundingBox().inflate(COLLECT_RADIUS, 4, COLLECT_RADIUS),
                d -> isDogReadyToTeleport(d, owner)
            );
        if (crossOriginTpList.isEmpty()) return;

        DogPromiseManager.addPromiseWithOwnerAndStartImmediately(
            new DogHoldChunkToTeleportPromise(
                crossOriginTpList, sLevel
            )
            , owner);
    }

    private boolean isDogReadyToTeleport(Dog dog, LivingEntity owner) {
        if (!dog.isDoingFine()) 
        return false;
        if (owner == null || dog.getOwnerUUID() == null)
            return false;
        if (ObjectUtils.notEqual(dog.getOwnerUUID(), owner.getUUID()))
            return false;
        if (dog.isOrderedToSit())
            return false;
        if (!dog.getMode().shouldFollowOwner())
            return false;
        return dog.crossOriginTp();
    }

    private boolean distanceTooShortToTeleport(Vec3 from, Vec3 to) {
        return from.distanceToSqr(to) < MIN_DISTANCE_TO_TRIGGER_TELEPORT_SQR;
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDropsEvent event) {
        PackPuppyTalent.mayNotifyNearbyPackPuppy(event);
    }

    @SubscribeEvent
    public void onTagsUpdated(TagsUpdatedEvent event) {
        DogBedMaterialManager.onTagsUpdated(event);
    }

    @SubscribeEvent
    public void onLevelLoad(LevelEvent.Load event) {
        var level = event.getLevel();
        if (level == null)
            return;
        var server = level.getServer();
        if (server == null)
            return;
        var level_overworld = server.getLevel(Level.OVERWORLD);
        if (level != level_overworld)
            return;
        DogLocationStorageMigration.checkAndMigrate(level_overworld);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingDamageEvent.Pre event) {
        onDogPassenegerHurtInWall(event);
        // if (event.isCanceled())
        //     return;
        
    }

    //Prevent passenger suffocate when riding dog.
    public void onDogPassenegerHurtInWall(LivingDamageEvent.Pre event) {
        var entity = event.getEntity();
        if (entity == null)
            return;
        if (!entity.isPassenger())
            return;
        
        var source = event.getContainer().getSource();
        if (!source.is(DamageTypes.IN_WALL))
            return;
        
        var vehicle = entity.getVehicle();
        if (!(vehicle instanceof Dog dog))
            return;
        
        event.getContainer().setNewDamage(0);
        //event.setCanceled(true);
    }

    @SubscribeEvent
    public void onWolfSetTarget(LivingChangeTargetEvent event) {
        var entity = event.getEntity();
        if (!ConfigHandler.SERVER.PREVENT_WILD_WOLVES_ANGRY.get())
            return;
        if (!(entity instanceof Wolf 大神))
            return;
        if (大神.isTame())
            return;
        var new_target = event.getNewAboutToBeSetTarget();
        if (!(new_target instanceof Player))
            return;
        
        event.setCanceled(true);
    }

    public static void onWolfTame(Wolf wolf) {
        if (!ConfigHandler.SERVER.TAMED_WOLF_NON_MOB_CAP.get())
            return;
        wolf.setPersistenceRequired();
    }

    @SubscribeEvent
    public void canPlayerContinueSleeping(CanContinueSleepingEvent event) {
        DogSleepOnManager.canPlayerContinueSleeping(event);
    }

    @SubscribeEvent
    public void beforeAllPlayerWakeUp(SleepFinishedTimeEvent event) {
        DogSleepOnManager.beforeSleepFinishedForAllPlayer(event);
    }

    @SubscribeEvent
    public void playerWakeUpEvent(PlayerWakeUpEvent event) {
        if (!event.getEntity().level().isClientSide())
            DogSleepOnManager.onPlayerWakeUp(event.getEntity());
    }

    @SubscribeEvent
    public void playerLoggedOut(PlayerLoggedOutEvent event) {
        var player = event.getEntity();
        if (player.level().isClientSide())
            return;
        if (!player.isVehicle())
            return;
        var dog_list = new ArrayList<Dog>();
        for (var passenger : player.getPassengers()) {
            if (passenger.getType() != DoggyEntityTypes.DOG.get())
                continue;
            if (passenger instanceof Dog dog)
                dog_list.add(dog);
        }

        for (var dog : dog_list) {
            dog.stopRiding();
        }
    }

    @SubscribeEvent
    public void onEntityJoinLevel(EntityJoinLevelEvent event) {
        DogDuplicationDetection.beforeEntityJoinLevel(event);
    }
}
