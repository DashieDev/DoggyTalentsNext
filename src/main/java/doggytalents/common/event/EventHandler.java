package doggytalents.common.event;

import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;

import doggytalents.DoggyAccessories;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.api.anim.DogAnimation;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.block.DogBedMaterialManager;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
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
import doggytalents.common.util.Util;
import doggytalents.common.util.dogpromise.DogPromiseManager;
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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.entity.animal.WolfVariants;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

public class EventHandler {

    @SubscribeEvent
    public void onServerTickEnd(final ServerTickEvent event) {

        if (event.phase != Phase.END) return;

        DogPromiseManager.tick();
        //DogLocationStorage.get(event.getServer()).getOnlineDogsManager().tick();
    }

    @SubscribeEvent
    public void onServerStop(final ServerStoppingEvent event) {
        DogPromiseManager.forceStop();
        DogLocationStorage.get(event.getServer()).onServerStop(event);
    }

    @SubscribeEvent
    public void onWolfRightClickWithTreat(final PlayerInteractEvent.EntityInteract event) {
        var level = event.getWorld();
        var stack = event.getItemStack();
        var target = event.getTarget();
        var entity = event.getEntity();

        if (!(entity instanceof Player owner)) return;
        if (stack.getItem() != DoggyItems.TRAINING_TREAT.get()) 
            return;
        if (!(target instanceof Wolf wolf)) return;
        event.setCanceled(true);
        
        if (!checkValidWolf(wolf, owner)) {
            event.setCancellationResult(InteractionResult.FAIL);
            return;
        }

        if (level.isClientSide) {
            PacketHandler.send(PacketDistributor.SERVER.noArg(), 
                new TrainWolfToDogData(wolf.getId(), wolf.yBodyRot, wolf.yHeadRot)
            );
        }

        event.setCancellationResult(InteractionResult.SUCCESS);
    }

    public static void checkAndTrainWolf(Player trainer, Wolf wolf, float yBodyRot, float yHeadRot) {
        var level = trainer.level;
        if (level.isClientSide)
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
        
        var server = owner.level.getServer();
        var locStore = DogLocationStorage.get(server);
        var respawnStore = DogRespawnStorage.get(server);
        
        int locCnt = locStore.getAll().size();
        int respawnCnt = respawnStore.getAll().size();
        
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
        Dog dog = DoggyEntityTypes.DOG.get().create(level);
        if (dog == null) {
            throw new IllegalStateException("Creator function for the dog returned \"null\"");
        }
        dog.setTame(true);
        dog.setOwnerUUID(owner.getUUID());
        dog.maxHealth();
        dog.setOrderedToSit(false);
        dog.setAge(wolf.getAge());
        dog.moveTo(wolf.getX(), wolf.getY(), wolf.getZ(), wolf.getYRot(), wolf.getXRot());
        dog.setYHeadRot(wolf.yBodyRot);
        dog.setYBodyRot(wolf.yBodyRot);
        dog.setYRot(wolf.yBodyRot);

        var wolf_collar_color = wolf.getCollarColor();
        var color = Util.srgbArrayToInt(wolf_collar_color.getTextureDiffuseColors());
        var dog_collar = DoggyAccessories.DYEABLE_COLLAR.get()
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
        dog.getJumpControl().jump();
    }

    private static void migrateUUID(UUID uuid, Dog dog, ServerLevel level) {
        if (ConfigHandler.SERVER.DISABLE_PRESERVE_UUID.get())
            return;
        if (level.getEntity(uuid) != null)
            return;
        dog.setUUID(uuid);
    }

    private static void migrateWolfVariant(Wolf wolf, Dog dog) {
        var dog_variant = DogVariantUtil.fromVanila(wolf.getVariant().unwrapKey().orElse(WolfVariants.PALE));
        dog.setDogVariant(dog_variant);
    }

    private static void migrateWolfArmor(Wolf wolf, Dog dog) {
        // if (!wolf.hasArmor())
        //     return;
        // var armor_stack = wolf.getBodyArmorItem().copyWithCount(1);
        // dog.setWolfArmor(armor_stack);
        return;
    }

    @SubscribeEvent
    public void onEntitySpawn(final EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        var level = entity.level;
        if (level.isClientSide)
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
        if (event.getEntity().level.isClientSide)
            return;

        if (isEnableStarterBundle()) {

            Player player = event.getPlayer();

            CompoundTag tag = player.getPersistentData();

            if (!tag.contains(Player.PERSISTED_NBT_TAG)) {
                tag.put(Player.PERSISTED_NBT_TAG, new CompoundTag());
            }

            CompoundTag persistTag = tag.getCompound(Player.PERSISTED_NBT_TAG);

            if (!persistTag.getBoolean("gotDTStartingItems")) {
                persistTag.putBoolean("gotDTStartingItems", true);

                player.getInventory().add(new ItemStack(DoggyItems.STARTER_BUNDLE.get()));
            }
        }
    }

    private boolean isEnableStarterBundle() {
        final var retMut = new MutableBoolean(false);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            if (ConfigHandler.ClientConfig
                .getConfig(ConfigHandler.CLIENT.ENABLE_STARTER_BUNDLE_BY_DEFAULT))
                retMut.setTrue();
        });
        if (retMut.getValue())
            return true;
        return ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.STARTING_ITEMS);
    }

    @SubscribeEvent
    public void onLootDrop(final LootingLevelEvent event) {
        HunterDogTalent.onLootDrop(event);
    }

    @SubscribeEvent
    public void onProjectileHit(final ProjectileImpactEvent event) {
        var levelChecker = event.getProjectile();
        if (levelChecker == null)
            return;
        var level = levelChecker.level;
        if (level.isClientSide)
            return;

        var hitResult = event.getRayTraceResult();
        if (hitResult instanceof EntityHitResult hitEntity)
            proccessEntityProjectileHitEvent(event, hitEntity);
        else if (hitResult instanceof BlockHitResult hitBlock)
            proccessBlockProjectileHitEvent(event, hitBlock);
    }    

    private void proccessEntityProjectileHitEvent(final ProjectileImpactEvent event, EntityHitResult hit) {
        if (detectAndCancelIfProjectileFromDogHitAllies(event, hit))
            return;
        var entity = hit.getEntity();
        if (entity instanceof Dog dog) {
            proccessDogProjectileHitEvent(event, hit, dog);
        }
    }

    private boolean detectAndCancelIfProjectileFromDogHitAllies(final ProjectileImpactEvent event, EntityHitResult hit) {
        var projectile = event.getProjectile();
        var projectileOnwer = projectile.getOwner();
        if (!(projectileOnwer instanceof Dog dog))
            return false;
        var hitEntity = hit.getEntity();
        if (hitEntity == null || hitEntity == dog)
            return false;
        if (!(hitEntity instanceof LivingEntity living))
            return false;
        var owner = dog.getOwner();
        if (owner == null)
            return false;
        if (!isAlliedToDog(living, owner))
            return false;
        projectile.discard();
        event.setCanceled(true);
        return true;
    }

    public static boolean isAlliedToDog(LivingEntity entity, LivingEntity owner) {
        if (owner == null || entity == null)
            return false;
        if (entity instanceof TamableAnimal otherDog) {
            entity = otherDog.getOwner();
        }
        if (entity == null)
            return false;
        if (owner == entity)
            return true;
        if (owner.isAlliedTo(entity))
            return true;
        if (entity instanceof Player) {
            if (ConfigHandler.SERVER.ALL_PLAYER_CANNOT_ATTACK_DOG.get())
                return true;
        }
        return false;
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

        boolean flag = 
            checkIfArrowShouldNotHurtDog(dog, projectileOnwer, dogOwner);
        if (!flag) return;

        //Workaround to avoid infinite loop
        //net.minecraft.world.entity.projectile.AbstractArrow:193
        //This should not happen by design.
        if (projectile instanceof AbstractArrow arrow) {
            if (arrow.getPierceLevel() > 0) {
                arrow.setPierceLevel((byte) 0);
            }
        }

        event.setCanceled(true);
    }

    private void proccessBlockProjectileHitEvent(final ProjectileImpactEvent event, BlockHitResult hit) {
        var projectile = event.getProjectile();
        if (!(projectile instanceof ThrownEgg))
            return;

        var level = projectile.level;
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
            level.random.nextFloat() * 0.2F);
    }

    private static boolean checkIfArrowShouldNotHurtDog(Dog dog, Entity projectileOnwer, LivingEntity dogOwner) {
        boolean allPlayerCannotAttackDog = 
            ConfigHandler.ClientConfig.getConfig(ConfigHandler.SERVER.ALL_PLAYER_CANNOT_ATTACK_DOG);

        if (allPlayerCannotAttackDog && projectileOnwer instanceof Player) {
            return true;
        } 
        
        if (!dog.canOwnerAttack() && dog.checkIfAttackedFromOwnerOrTeam(dogOwner, projectileOnwer)) {
            return true;
        }

        return false;
    }

    public final int COLLECT_RADIUS = 26;
    @SubscribeEvent
    public void onEntityChangeDimension(EntityTravelToDimensionEvent event) {
        var entity = event.getEntity();
        if (entity.level.isClientSide) return;
        if ((entity instanceof ServerPlayer owner)) {
            onOwnerChangeDimension(event, owner);
        }
    }

    private void onOwnerChangeDimension(EntityTravelToDimensionEvent event, ServerPlayer owner) {
        if (!(owner.level instanceof ServerLevel sLevel)) return;
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
        if (!(owner.level instanceof ServerLevel sLevel)) return;
        
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
    public void onLevelLoad(WorldEvent.Load event) {
        var level = event.getWorld();
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
    public void onLivingHurt(LivingHurtEvent event) {
        onDogPassenegerHurtInWall(event);
        if (event.isCanceled())
            return;
        
    }

    //Prevent passenger suffocate when riding dog.
    public void onDogPassenegerHurtInWall(LivingHurtEvent event) {
        var entity = event.getEntity();
        if (entity == null)
            return;
        if (!entity.isPassenger())
            return;
        
        var source = event.getSource();
        if (source != DamageSource.IN_WALL)
            return;
        
        var vehicle = entity.getVehicle();
        if (!(vehicle instanceof Dog dog))
            return;
        
        event.setAmount(0);
        event.setCanceled(true);
    }
}
