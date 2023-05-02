package doggytalents.common.event;

import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.DogPlayTagAction;
import doggytalents.common.talent.HunterDogTalent;
import doggytalents.common.util.doggyasynctask.DogAsyncTaskManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
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
    public void rightClickEntity(final PlayerInteractEvent.EntityInteract event) {

        Level world = event.getLevel();

        ItemStack stack = event.getItemStack();
        Entity target = event.getTarget();

        if (target.getType() == EntityType.WOLF && target instanceof TamableAnimal && stack.getItem() == DoggyItems.TRAINING_TREAT.get()) {
            event.setCanceled(true);

            TamableAnimal wolf = (TamableAnimal) target;

            Player player = event.getEntity();

            if (wolf.isAlive() && wolf.isTame() && wolf.isOwnedBy(player)) {

                if (!world.isClientSide) {
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    Dog dog = DoggyEntityTypes.DOG.get().create(world);
                    dog.tame(player);
                    dog.setHealth(dog.getMaxHealth());
                    dog.setOrderedToSit(false);
                    dog.setAge(wolf.getAge());
                    dog.absMoveTo(wolf.getX(), wolf.getY(), wolf.getZ(), wolf.getYRot(), wolf.getXRot());

                    world.addFreshEntity(dog);

                    wolf.discard();
                }

                event.setCancellationResult(InteractionResult.SUCCESS);
            } else {
                event.setCancellationResult(InteractionResult.FAIL);
            }
        }
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
}
