package doggytalents.common.entity.misc;

import doggytalents.ChopinLogger;
import doggytalents.common.entity.Dog;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Piano extends Entity {

    private static final EntityDataAccessor<Integer> PIANO_FLAGS = SynchedEntityData.defineId(Piano.class, EntityDataSerializers.INT);
    private final PianoType pianoType;
    private final PianoColor pianoColor;

    public Piano(EntityType<Piano> type, Level level) {
        super(type, level);
        this.pianoType = PianoType.GRAND;
        this.pianoColor = PianoColor.BLACK;
    }

    public Piano(EntityType<Piano> type, Level level, PianoType pianoType, PianoColor pianoColor) {
        super(type, level);
        this.pianoType = pianoType;
        this.pianoColor = pianoColor;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(PIANO_FLAGS, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        boolean bigLidClosed = compound.getBoolean("pianoFallboardClosed");
        this.setFallboardClosed(bigLidClosed);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putBoolean("pianoFallboardClosed", isFallboardClosed());
    }

    private boolean getPianoFlag(int bit) {
        return (this.entityData.get(PIANO_FLAGS) & bit) != 0;
    }

    private void setPianoFlag(int bits, boolean flag) {
        int c = this.entityData.get(PIANO_FLAGS);
        this.entityData.set(PIANO_FLAGS, (flag ? c | bits : c & ~bits));
    }

    public boolean isFallboardClosed() {
        return this.getPianoFlag(1);
    }

    public void setFallboardClosed(boolean val) {
        this.setPianoFlag(1, val);
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

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        this.setFallboardClosed(!this.isFallboardClosed());
        return InteractionResult.SUCCESS;
    }

    public PianoType getPianoType() { return this.pianoType; }
    public PianoColor getPianoColor() { return this.pianoColor; }

    public static enum PianoType { GRAND, UPRIGHT }

    public static enum PianoColor { BLACK, WHITE, BROWN }

}
