package doggytalents.common.talent;

import doggytalents.api.feature.EnumMode;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
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

            if (dog.getTarget() instanceof Creeper creeper) {
                creeper.setSwellDir(-1);
            }

            if (
                this.level() >= this.talent.getMaxLevel()
                && ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.MAX_CREEPER_SWEEPER_DONT_GROWL)
            ) return;

            int timeLeft = this.cooldown - dog.tickCount;

            if (timeLeft <= 0 && (
                !dog.isInSittingPose() 
                || dog.isPassenger()
            )) {
                List<Creeper> list = dog.level.getEntitiesOfClass(Creeper.class, dog.getBoundingBox().inflate(this.getSearchRange(dog), this.level() * 2, this.getSearchRange(dog)));

                if (!list.isEmpty()) {
                    dog.playSound(SoundEvents.WOLF_GROWL, dog.getSoundVolume(), (dog.getRandom().nextFloat() - dog.getRandom().nextFloat()) * 0.2F + 1.0F);
                    this.cooldown = dog.tickCount + 60 + dog.getRandom().nextInt(40);
                }
            }

        }
    }

    private int getSearchRange(AbstractDog dog) {
        if ((dog instanceof Dog d)) {
            if (d.isMode(EnumMode.GUARD, EnumMode.GUARD_FLAT, EnumMode.GUARD_MINOR)) {
                return Math.min(this.level()*5, 8);
            }
        }
        return this.level()*5;
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
