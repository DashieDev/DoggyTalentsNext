package doggytalents.common.entity.misc;

import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.ParticlePackets;
import doggytalents.common.talent.OokamiKazeTalent;
import doggytalents.common.util.DogUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class DogGunpowderProjectile extends ThrowableProjectile {
    
    public DogGunpowderProjectile(EntityType<? extends ThrowableProjectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public DogGunpowderProjectile(Level worldIn, LivingEntity livingEntityIn) {
        super(DoggyEntityTypes.DOG_GUNPOWDER_PROJ.get(), livingEntityIn, worldIn);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void onHit(HitResult hitResult) {
        if (this.level().isClientSide)
            return;
        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return;
        } 
        if (!this.level().isClientSide) {
            this.spawnAtLocation(new ItemStack(Items.GUNPOWDER));
        }
        if (!this.level().isClientSide)
            this.discard();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide)
            scanDogAroundAndTrigger();
    }

    private int tickTillScan = 0;
    private void scanDogAroundAndTrigger() {
        if (tickTillScan > 0) {
            --tickTillScan;
            return;
        }
        if (this.getOwner() == null)
            return;
        var pos = this.position();
        var aabb = new AABB(pos.add(-5, -5, -5), pos.add(5, 0, 5));
        var moveVec = this.getDeltaMovement();
        var moveVecXZ = new Vec3(moveVec.x, 0, moveVec.z).normalize();
        if (moveVecXZ.length() < 1) {
            return;
        }
        var dogs = this.level().getEntitiesOfClass(Dog.class, aabb, filter_dog -> 
            this.isValidDog(filter_dog, moveVecXZ));
        if (dogs.isEmpty())
            return;
        tickTillScan = 3;
        var nearestDog = dogs.get(0);
        double minDist = nearestDog.distanceToSqr(this);
        for (var dog : dogs) {
            double dist = dog.distanceToSqr(this);
            if (dist < minDist) {
                nearestDog = dog;
                minDist = dist;
            }
        }
        var talentInst = nearestDog.getTalent(DoggyTalents.OOKAMIKAZE)
            .map(inst -> inst.cast(OokamiKazeTalent.class))
            .orElse(null);
        if (talentInst == null)
            return;
        if (!talentInst.canExplode())
            return;
        var dy = this.position().y - nearestDog.position().y;
        var minDistXZ = new Vec3(
            this.position().x - nearestDog.position().x,
            0,
            this.position().z - nearestDog.position().z   
        ).lengthSqr();
        if (minDist < 4) {
            nearestDog.triggerAction(talentInst.actionCreator(nearestDog, null));
            this.feedDog(nearestDog);
        } else if (dy >= 1.5 && minDistXZ >= 10)
            nearestDog.triggerAction(talentInst.actionCreator(nearestDog, this));
    }

    private boolean isValidDog(Dog dog, Vec3 lookVecXZ) {
        if (!dog.isDoingFine())
            return false;
        if (dog.isOrderedToSit())
            return false;
        if (dog.getOwner() != this.getOwner())
            return false;
        if (!checkIfDogCanCatch(dog, lookVecXZ) && dog.distanceToSqr(this) >= 4)
            return false;
        if (!dog.readyForNonTrivialAction())
            return false;
        
        return dog.getDogLevel(DoggyTalents.OOKAMIKAZE.get()) > 0;
    }

    private boolean checkIfDogCanCatch(Dog dog, Vec3 lookVecXZ) {
        var posXZ = new Vec3(
            this.position().x,
            0,
            this.position().z
        );
        var dogPosXZ = new Vec3(
            dog.position().x,
            0,
            dog.position().z
        );
        var dist = DogUtil.distanceFromPointToLineOfUnitVector2DSqr(dogPosXZ, posXZ, lookVecXZ);
        if (dist < 0)
            return false;
        return dist < 1.5*1.5;
    }

    public void feedDog(Dog dog) {
        if (dog.level() instanceof ServerLevel) {
            ParticlePackets.DogEatingParticlePacket.sendDogEatingParticlePacketToNearby(
                dog, new ItemStack(Items.GUNPOWDER));
        }
        dog.playSound(
            SoundEvents.GENERIC_EAT, 
            dog.getSoundVolume(), 
            (dog.getRandom().nextFloat() - dog.getRandom().nextFloat()) * 0.2F + 1.0F
        );

        this.discard();
    }

}
