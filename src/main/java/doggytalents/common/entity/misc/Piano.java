package doggytalents.common.entity.misc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Piano extends Entity {
    
    public Piano(EntityType<Piano> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {
        
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {
        
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return !(source.getDirectEntity() instanceof Player);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isEffectiveAi() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource p_19946_, float p_19947_) {
        if (this.isInvulnerableTo(p_19946_))
            return false;
        this.discard();
        return true;
    }

}
