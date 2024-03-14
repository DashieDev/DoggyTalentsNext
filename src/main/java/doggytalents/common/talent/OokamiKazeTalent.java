package doggytalents.common.talent;

import javax.annotation.Nullable;

import doggytalents.api.anim.DogAnimation;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogGunpowderProjectile;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogExplosionData;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

public class OokamiKazeTalent extends TalentInstance {

    public OokamiKazeTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    public DogCatchGunpowderAndExplodeAction actionCreator(Dog dog, @Nullable DogGunpowderProjectile proj) {
        return new DogCatchGunpowderAndExplodeAction(dog, proj, getRadius());
    }

    public int getRadius() {
        var level = this.level();
        if (level <= 5)
            return 10;
        if (level <= 1)
            return 3;
        if (level <= 2)
            return 4;
        if (level <= 3)
            return 6;
        if (level <= 4)
            return 8;
        return 0;
    }

    public static class DogCatchGunpowderAndExplodeAction extends TriggerableAction {

        private ActionPhase phase;
        private int stopTick;
        private final int radius;

        private final DogGunpowderProjectile toCatch;
        private int tickTillJump;
        private int tickTillConsumeItem;

        private int tickTillHowl = 50;
        private int tickTillBoom = 55;

        public DogCatchGunpowderAndExplodeAction(Dog dog, @Nullable DogGunpowderProjectile toCatch, int radius) {
            super(dog, false, false);
            this.phase = toCatch != null ? ActionPhase.CATCH : ActionPhase.EXPLODE;
            this.toCatch = toCatch;
            this.radius = radius;
        }

        @Override
        public void onStart() {
            if (phase == ActionPhase.CATCH) {
                beginCatchAnim();
            } else if (phase == ActionPhase.EXPLODE) {
                beginHowlAnim();
            } else {
                this.setState(ActionState.FINISHED);
                return;
            }
        }

        @Override
        public void tick() {
            if (phase == ActionPhase.CATCH) {
                updateToCatch();
            } else if (phase == ActionPhase.WAIT_TILL_LAND) {
                updateWaitTillLand();
            } else {
                updateHowlAndExplode();
            }
        }

        private void updateToCatch() {
            if (dog.getAnim() != DogAnimation.BACKFLIP) {
                this.setState(ActionState.FINISHED);
                return;
            }
            if (toCatch == null) {
                this.setState(ActionState.FINISHED);
                return;
            }
            if (dog.tickCount >= stopTick) {
                this.setState(ActionState.FINISHED);
                return;
            }

            if (--this.tickTillJump == 0) {
                this.dog.getJumpControl().jump();
            }

            if (--this.tickTillConsumeItem <= 0) {
                if (tryConsumeItem()) {
                    phase = ActionPhase.WAIT_TILL_LAND;
                    return;
                }
            }
        }

        public boolean tryConsumeItem() {
            if (toCatch == null)
                return false;
            if (!toCatch.isAlive())
                return false;
            if (dog.distanceToSqr(toCatch) > 4)
                return false;
            toCatch.feedDog(dog);
            
            return true;
        }

        public void updateWaitTillLand() {
            if (dog.getAnim() == DogAnimation.NONE) {
                this.phase = ActionPhase.EXPLODE;
                beginHowlAnim();
                return;
            }
            if (dog.getAnim() != DogAnimation.BACKFLIP) {
                this.setState(ActionState.FINISHED);
                return;
            }
        }

        private void updateHowlAndExplode() {
            if (dog.getAnim() != DogAnimation.HOWL) {
                this.setState(ActionState.FINISHED);
                return;
            }
            if (dog.tickCount >= stopTick) {
                this.setState(ActionState.FINISHED);
                return;
            }
            --tickTillHowl;
            if (tickTillHowl == 0) {
                dog.howl();
            } else if (tickTillHowl == 30) {
                this.dog.playSound(SoundEvents.WOLF_GROWL, 0.3F, dog.getVoicePitch());
            }

            --tickTillBoom;
            if (tickTillBoom == 0) {
                var explode = new DogExplosion(dog, this.radius);
                explode.explode();
            }
        }

        @Override
        public void onStop() {
            if (phase == ActionPhase.EXPLODE) {
                if (dog.getAnim() == DogAnimation.HOWL) {
                    dog.setAnim(DogAnimation.NONE);
                }
                return;
            }

            if (dog.getAnim() == DogAnimation.BACKFLIP) {
                dog.setAnim(DogAnimation.NONE);
            }
            return;
            
        }

        private void beginCatchAnim() {
            this.stopTick = dog.tickCount + DogAnimation.BACKFLIP.getLengthTicks();
            this.dog.setAnim(DogAnimation.BACKFLIP);
            this.tickTillJump = 3;
            this.tickTillConsumeItem = 5;
        }

        private void beginHowlAnim() {
            this.stopTick = dog.tickCount + DogAnimation.HOWL.getLengthTicks();
            this.dog.setAnim(DogAnimation.HOWL);
            tickTillBoom = 63;
        }

        private enum ActionPhase {
            CATCH, WAIT_TILL_LAND, EXPLODE
        }

    }


    public static class DogExplosion {

        private final Dog dog;
        private final int radius;

        public DogExplosion(Dog dog, int radius) {
            this.dog = dog;
            this.radius = radius;
        }

        public void explode() {
            if (dog.level().isClientSide)
                return;
            dog.level().gameEvent(dog, GameEvent.EXPLODE, dog.position());
            broadcastExploisionToClient();
            hurtEntities();
        }

        private void broadcastExploisionToClient() {
            PacketHandler.send(PacketDistributor.TRACKING_ENTITY.with(() -> dog), 
                new DogExplosionData(dog.getId())
            );
        }

        private void hurtEntities() {
            var owner = dog.getOwner();
            if (owner == null)
                return;
            var dog_pos = dog.position();
            float ext_radius = 2 * this.radius;
            var impact_bb = new AABB(
                dog_pos.subtract(
                    ext_radius + 1, 
                    ext_radius + 1, 
                    ext_radius + 1),
                dog_pos.add(
                    ext_radius + 1, 
                    ext_radius + 1, 
                    ext_radius + 1)
                );
            var entities = dog.level().getEntities(dog, impact_bb);
            if (entities.isEmpty())
                return;
            for (var e : entities) {
                if (e == dog)
                    continue;
                if (e.ignoreExplosion())
                    continue;
                if (e instanceof LivingEntity living && isAlliedToDog(living, owner))
                    continue;
                hurtAndKnockback(owner, e, ext_radius);
            }
        }

        private double calculateImpactValue(Vec3 dog_pos, Entity e, int hurt_radius) {
            var far_percent = Math.sqrt(e.distanceToSqr(dog_pos)) / hurt_radius;
            if (far_percent > 1)
                return -1;
            var close_percent = 1 - far_percent;
            var seen_percent = Explosion.getSeenPercent(dog_pos, e);
            var impact_value = seen_percent * close_percent;
            return impact_value;
        }

        private void hurtAndKnockback(LivingEntity owner, Entity e, float hurt_radius) {
            var dog_pos = dog.position();            
            var impact_value = calculateImpactValue(dog_pos, e, radius);
            if (impact_value <= 0)
                return;

            var t = impact_value * impact_value + impact_value;
            final int base_damage = 7;
            var hurt_amount = 1 + t * base_damage * this.radius;
            e.hurt(e.damageSources().explosion(dog, owner), (float) hurt_amount);

            double knockback = impact_value;
            if (e instanceof LivingEntity living) {
                knockback = ProtectionEnchantment.getExplosionKnockbackAfterDampener(living, knockback);
            }
            Vec3 e_pos;
            if (e instanceof PrimedTnt)
                e_pos = e.position();
            else
                e_pos = e.getEyePosition();
            var knock_vec = e_pos
                .subtract(dog_pos)
                .normalize()
                .scale(knockback);
            var knock_movement = e.getDeltaMovement().add(knock_vec);
            e.setDeltaMovement(knock_movement);
        }

        private boolean isAlliedToDog(LivingEntity entity, LivingEntity owner) {
            if (owner == null || entity == null)
                return false;
            if (entity instanceof TamableAnimal otherDog) {
                entity = otherDog.getOwner();
            }
            if (owner == entity)
                return true;
            if (owner.isAlliedTo(entity))
                return true;
            if (entity instanceof Player player) {
                if (ConfigHandler.SERVER.ALL_PLAYER_CANNOT_ATTACK_DOG.get())
                    return true;
            }
            return false;
        }

    }



    public static void explodeClient(Dog dog) {
        var level = dog.level();
        var dog_pos = dog.position();
        if (level.isClientSide) {
            level.playLocalSound(dog_pos.x, dog_pos.y, dog_pos.z, 
                SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, 
                (1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.2F) * 0.7F, false);
        }
        level.addParticle(ParticleTypes.EXPLOSION, dog_pos.x, dog_pos.y, dog_pos.z, 1.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.EXPLOSION_EMITTER, dog_pos.x, dog_pos.y, dog_pos.z, 1.0D, 0.0D, 0.0D);
    }
    
}
