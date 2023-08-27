package doggytalents.common.event;

import doggytalents.DoggyAccessories;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.DogBackFlipAction;
import doggytalents.common.entity.ai.triggerable.DogPlayTagAction;
import doggytalents.common.entity.anim.DogAnimation;
import doggytalents.common.talent.HunterDogTalent;
import doggytalents.common.util.Util;
import doggytalents.common.util.doggyasynctask.DogAsyncTaskManager;
import doggytalents.common.util.doggyasynctask.promise.DogHoldChunkToTeleportPromise;
import doggytalents.common.util.doggyasynctask.promise.DogBatchTeleportToDimensionPromise;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler {

    @SubscribeEvent
    public void onServerTickEnd(final ServerTickEvent event) {

        if (event.phase != Phase.END || !event.haveTime()) return;

        DogAsyncTaskManager.tick();
    }

    @SubscribeEvent
    public void onServerStop(final ServerStoppingEvent event) {
        DogAsyncTaskManager.forceStop();
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
        if (!wolf.isTame()) return false;
        if (!wolf.isOwnedBy(owner)) return false;
        return true;
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
            dog.setCustomName(wolf.getCustomName());
        }

        level.addFreshEntity(dog);
        wolf.discard();

        dog.triggerAnimationAction(new DogBackFlipAction(dog));
        dog.getJumpControl().jump();
    }

    @SubscribeEvent
    public void onEntitySpawn(final EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof AbstractSkeleton) {
            AbstractSkeleton skeleton = (AbstractSkeleton) entity;
            skeleton.goalSelector.addGoal(3, new AvoidEntityGoal<>(skeleton, Dog.class, 6.0F, 1.0D, 1.2D)); // Same goal as in AbstractSkeletonEntity
        }
    }

    @SubscribeEvent
    public void playerLoggedIn(final PlayerLoggedInEvent event) {
        if (ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.STARTING_ITEMS)) {

            Player player = event.getEntity();

            CompoundTag tag = player.getPersistentData();

            if (!tag.contains(Player.PERSISTED_NBT_TAG)) {
                tag.put(Player.PERSISTED_NBT_TAG, new CompoundTag());
            }

            CompoundTag persistTag = tag.getCompound(Player.PERSISTED_NBT_TAG);

            if (!persistTag.getBoolean("gotDTStartingItems")) {
                persistTag.putBoolean("gotDTStartingItems", true);

                player.getInventory().add(new ItemStack(DoggyItems.DOGGY_CHARM.get()));
                player.getInventory().add(new ItemStack(DoggyItems.WHISTLE.get()));
            }
        }
    }

    @SubscribeEvent
    public void onLootDrop(final LootingLevelEvent event) {
        HunterDogTalent.onLootDrop(event);
    }

    @SubscribeEvent
    public void onProjectileHit(final ProjectileImpactEvent event) {
        var hitResult = event.getRayTraceResult();
        if (!(hitResult instanceof EntityHitResult)) return;

        var entityHitResult = (EntityHitResult) hitResult;
        var entity = entityHitResult.getEntity();
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
                fromLevel, owner.getUUID(), event.getDimension())
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
        if (dog.getOwner() != owner)
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
}
