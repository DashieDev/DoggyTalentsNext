package doggytalents.common.entity.misc;

import com.google.common.base.Predicates;
import doggytalents.DoggyEntityTypes;
import doggytalents.api.feature.EnumMode;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.EntityUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.UUID;

public class DoggyBeamEntity extends ThrowableProjectile {

    public DoggyBeamEntity(EntityType<? extends ThrowableProjectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public DoggyBeamEntity(Level worldIn, LivingEntity livingEntityIn) {
        super(DoggyEntityTypes.DOG_BEAM.get(), livingEntityIn, worldIn);
    }

    // public DoggyBeamEntity(PlayMessages.SpawnEntity packet, Level worldIn) {
    //     super(DoggyEntityTypes.DOG_BEAM.get(), worldIn);
    // }

    @Override
    protected void onHit(HitResult result) {
        if (result.getType() == HitResult.Type.ENTITY) {
            if (!this.level().isClientSide) {
                Entity entityHit = ((EntityHitResult) result).getEntity();

                Entity thrower = this.getOwner();

                if (thrower instanceof LivingEntity && entityHit instanceof LivingEntity) {
                    LivingEntity livingThrower = (LivingEntity) thrower;
                    LivingEntity livingEntity = (LivingEntity) entityHit;

                    this.level().getEntitiesOfClass(Dog.class, this.getBoundingBox().inflate(64D, 16D, 64D)).stream()
                        .filter(Predicates.not(Dog::isInSittingPose))
                        .filter(d -> d.isMode(EnumMode.AGGRESIVE, EnumMode.TACTICAL, EnumMode.BERSERKER))
                        .filter(d -> d.canInteract(livingThrower))
                        .filter(d -> d != livingEntity && d.wantsToAttack(livingEntity, d.getOwner()))
                        .filter(d -> d.distanceTo(entityHit) < EntityUtil.getFollowRange(d))
                        .forEach(d -> d.setTarget(livingEntity));
                }
            } else {
                for (int j = 0; j < 8; ++j) {
                    this.level().addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
                }
            }
        }

        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, Constants.EntityState.DEATH);
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData() {
        // TODO Auto-generated method stub

    }
}
