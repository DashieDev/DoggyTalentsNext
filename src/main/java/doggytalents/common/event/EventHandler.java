package doggytalents.common.event;

import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;

import doggytalents.ChopinLogger;
import doggytalents.DoggyAccessories;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.block.DogBedMaterialManager;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.WolfBegAtTreatGoal;
import doggytalents.common.entity.ai.triggerable.DogBackFlipAction;
import doggytalents.common.entity.ai.triggerable.DogPlayTagAction;
import doggytalents.common.entity.anim.DogAnimation;
import doggytalents.common.storage.DogLocationStorage;
import doggytalents.common.storage.OnlineDogLocationManager;
import doggytalents.common.talent.HunterDogTalent;
import doggytalents.common.talent.PackPuppyTalent;
import doggytalents.common.util.DogLocationStorageMigration;
import doggytalents.common.util.Util;
import doggytalents.common.util.doggyasynctask.DogAsyncTaskManager;
import doggytalents.common.util.doggyasynctask.promise.DogHoldChunkToTeleportPromise;
import doggytalents.common.util.doggyasynctask.promise.DogBatchTeleportToDimensionPromise;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEgg;
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
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistries;

public class EventHandler {

    @SubscribeEvent
    public void onServerTickEnd(final ServerTickEvent event) {

        if (event.phase != Phase.END) return;

        DogAsyncTaskManager.tick();
        OnlineDogLocationManager.get().tick(event.getServer());
    }

    @SubscribeEvent
    public void onServerStop(final ServerStoppingEvent event) {
        DogAsyncTaskManager.forceStop();
        OnlineDogLocationManager.get().unrideAllDogOnPlayer();
    }

    @SubscribeEvent
    public void onWolfRightClickWithTreat(final PlayerInteractEvent.EntityInteract event) {
        var level = event.getLevel();
        var stack = event.getItemStack();
        var target = event.getTarget();
        var owner = event.getEntity();

        if (stack.getItem() != DoggyItems.TRAINING_TREAT.get()) 
            return;
        if (target.getType() != EntityType.WOLF) return;
        if (!(target instanceof Wolf wolf)) return;
        event.setCanceled(true);
        
        if (!checkValidWolf(wolf, owner)) {
            event.setCancellationResult(InteractionResult.FAIL);
            return;
        }

        if (!level.isClientSide) {
            if (!owner.getAbilities().instabuild) {
                stack.shrink(1);
            }

            trainWolf(wolf, owner, level);
        }

        event.setCancellationResult(InteractionResult.SUCCESS);
    }

    private boolean checkValidWolf(Wolf wolf, Player owner) {
        if (!wolf.isAlive()) return false;
        boolean trainUntamed = !ConfigHandler.SERVER.DISABLE_TRAIN_UNTAMED_WOLF.get();
        boolean condition1 = trainUntamed && !wolf.isTame();
        boolean condition2 = wolf.isTame() && wolf.isOwnedBy(owner);
        
        return condition1 || condition2;
    }

    private void trainWolf(Wolf wolf, Player owner, Level level) {
        Dog dog = DoggyEntityTypes.DOG.get().create(level);
        if (dog == null) {
            throw new IllegalStateException("Creator function for the dog returned \"null\"");
        }
        dog.tame(owner);
        dog.setHealth(dog.getMaxHealth());
        dog.setOrderedToSit(false);
        dog.setAge(wolf.getAge());
        dog.absMoveTo(wolf.getX(), wolf.getY(), wolf.getZ(), wolf.getYRot(), wolf.getXRot());
        dog.setYHeadRot(wolf.getYHeadRot());
        dog.setYBodyRot(wolf.getVisualRotationYInDegrees());

        var wolf_collar_color = wolf.getCollarColor();
        var color = Util.srgbArrayToInt(wolf_collar_color.getTextureDiffuseColors());
        var dog_collar = DoggyAccessories.DYEABLE_COLLAR.get()
            .create(color);
        if (dog_collar != null)
            dog.addAccessory(dog_collar);
            
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

    private void migrateUUID(UUID uuid, Dog dog, ServerLevel level) {
        if (ConfigHandler.SERVER.DISABLE_PRESERVE_UUID.get())
            return;
        if (level.getEntity(uuid) != null)
            return;
        dog.setUUID(uuid);
    }

    @SubscribeEvent
    public void onEntitySpawn(final EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        var level = entity.level();
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
        if (event.getEntity().level().isClientSide)
            return;

        if (isEnableStarterBundle()) {

            Player player = event.getEntity();

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
        var level = levelChecker.level();
        if (level.isClientSide)
            return;

        var hitResult = event.getRayTraceResult();
        if (hitResult instanceof EntityHitResult hitEntity)
            proccessEntityProjectileHitEvent(event, hitEntity);
        else if (hitResult instanceof BlockHitResult hitBlock)
            proccessBlockProjectileHitEvent(event, hitBlock);
            
    }

    private void proccessEntityProjectileHitEvent(final ProjectileImpactEvent event, EntityHitResult hit) {
        var entity = hit.getEntity();
        if (!(entity instanceof Dog)) return;
        var dog = (Dog) entity;

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
        if (entity.level().isClientSide) return;
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

        DogAsyncTaskManager.addPromiseWithOwnerAndStartImmediately(
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

        DogAsyncTaskManager.addPromiseWithOwnerAndStartImmediately(
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

    //Prevent passenger suffocate when riding dog.
    @SubscribeEvent
    public void onDogPassenegerHurtInWall(LivingHurtEvent event) {
        var entity = event.getEntity();
        if (entity == null)
            return;
        if (!entity.isPassenger())
            return;
        
        var source = event.getSource();
        if (!source.is(DamageTypes.IN_WALL))
            return;
        
        var vehicle = entity.getVehicle();
        if (!(vehicle instanceof Dog dog))
            return;
        
        event.setAmount(0);
        event.setCanceled(true);
    }
}
