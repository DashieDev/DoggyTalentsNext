package doggytalents.common.talent;

import doggytalents.api.feature.EnumMode;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.CreeperSweeperData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;

import java.util.List;

public class CreeperSweeperTalent extends TalentInstance {

    private int cooldown;
    private boolean onlyAttackCreeper = false;

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

            // if (dog.getTarget() instanceof Creeper creeper) {
            //     creeper.setSwellDir(-1);
            // }

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
    public InteractionResult canAttack(AbstractDog dog, LivingEntity entity) {
        if (this.level() >= 5 && onlyAttackCreeper) {
            return entity instanceof Creeper ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        }
        return entity instanceof Creeper && this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResult shouldAttackEntity(AbstractDog dog, LivingEntity target, LivingEntity owner) {
        if (this.level() >= 5 && onlyAttackCreeper) {
            return target instanceof Creeper ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        }
        return target instanceof Creeper && this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
     }

    @Override
    public void doAdditionalAttackEffects(AbstractDog dogIn, Entity target) {
        if (this.level() < 5)
            return;
        if (!(target instanceof Creeper creeper))
            return;
        creeper.setHealth(0);
        creeper.die(DamageSource.mobAttack(dogIn));
    }

    @Override
    public void readFromNBT(AbstractDog dogIn, CompoundTag compound) {
        super.readFromNBT(dogIn, compound);
        this.onlyAttackCreeper = compound.getBoolean("targetOnlyCreeper");
    }

    @Override
    public void writeToNBT(AbstractDog dogIn, CompoundTag compound) {
        super.writeToNBT(dogIn, compound);
        compound.putBoolean("targetOnlyCreeper", this.onlyAttackCreeper);
    }

    @Override
    public void writeToBuf(FriendlyByteBuf buf) {
        super.writeToBuf(buf);
        buf.writeBoolean(this.onlyAttackCreeper);
    }

    @Override
    public void readFromBuf(FriendlyByteBuf buf) {
        super.readFromBuf(buf);
        this.onlyAttackCreeper = buf.readBoolean();
    }

    @Override
    public void updateOptionsFromServer(TalentInstance fromServer) {
        if (!(fromServer instanceof CreeperSweeperTalent sweep))
            return;
        this.onlyAttackCreeper = sweep.onlyAttackCreeper;
    }

    public void updateFromPacket(CreeperSweeperData data) {
        onlyAttackCreeper = data.val;
    }

    @Override
    public TalentInstance copy() {
        var ret = super.copy();
        if (!(ret instanceof CreeperSweeperTalent sweep))
            return ret;
        sweep.setOnlyAttackCreeper(this.onlyAttackCreeper);
        return sweep;
    }

    public boolean onlyAttackCreeper() { return this.onlyAttackCreeper; }
    public void setOnlyAttackCreeper(boolean val) { this.onlyAttackCreeper = val; }
}
