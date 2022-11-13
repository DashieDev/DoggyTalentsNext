package doggytalents.common.talent;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;

import java.util.List;

public class CreeperSweeperTalent extends TalentInstance {

    private int cooldown;

    public CreeperSweeperTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dogIn) {
        this.cooldown = dogIn.tickCount;
    }

    @Override
    public void tick(AbstractDog dog) {
        if (this.level() > 0) {
            int timeLeft = this.cooldown - dog.tickCount;

            if (timeLeft <= 0 && (
                !dog.isInSittingPose() 
                
            )) {
                List<Creeper> list = dog.level.getEntitiesOfClass(Creeper.class, dog.getBoundingBox().inflate(this.level() * 5,this.level() * 2, this.level() * 5));

                if (!list.isEmpty()) {
                    dog.playSound(SoundEvents.WOLF_GROWL, dog.getSoundVolume(), (dog.getRandom().nextFloat() - dog.getRandom().nextFloat()) * 0.2F + 1.0F);
                    this.cooldown = dog.tickCount + 60 + dog.getRandom().nextInt(40);
                }
            }

            if (dog.getTarget() instanceof Creeper) {
                Creeper creeper = (Creeper) dog.getTarget();
                creeper.setSwellDir(-1);
            }
        }
    }

    @Override
    public InteractionResult canAttack(AbstractDog dog, EntityType<?> entityType) {
        return entityType == EntityType.CREEPER && this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResult canAttack(AbstractDog dog, LivingEntity entity) {
        return entity instanceof Creeper && this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResult shouldAttackEntity(AbstractDog dog, LivingEntity target, LivingEntity owner) {
        return target instanceof Creeper && this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
     }
}
