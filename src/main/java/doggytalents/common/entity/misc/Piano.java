package doggytalents.common.entity.misc;

import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.common.entity.Dog;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
        this.setYRot(0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(PIANO_FLAGS, 0);
    }

    @Override
    protected void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput input) {
        boolean bigLidClosed = input.getBooleanOr("pianoFallboardClosed", false);
        this.setFallboardClosed(bigLidClosed);
    }

    @Override
    protected void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput output) {
        output.putBoolean("pianoFallboardClosed", isFallboardClosed());
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

    private boolean isInvulnerableToInternal(DamageSource source) {
        if (this.isRemoved())
            return true;
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
            return false;
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
    public boolean canBeHitByProjectile() {
        return false;
    }

    @Override
    public boolean hurtServer(net.minecraft.server.level.ServerLevel level, DamageSource source, float damage) {
        if (this.isInvulnerableToInternal(source))
            return false;
        var killer = source.getDirectEntity();
        boolean killedByCreative =
            (killer instanceof Player player)
            && player.getAbilities().instabuild;
        var drop = this.getPianoDrop();
        if (!drop.isEmpty() && !killedByCreative) {
            this.spawnAtLocation(level, drop);
        }
        this.discard();
        return true;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand, net.minecraft.world.phys.Vec3 hitPos) {
        var stack = player.getItemInHand(hand);
        var item = stack.getItem();
        if (item == Items.TORCH) {
            if (!this.level().isClientSide() && player.isShiftKeyDown())
                this.setYRot(this.getYRot() + 45);
            return InteractionResult.SUCCESS;
        }
        this.setFallboardClosed(!this.isFallboardClosed());
        return InteractionResult.SUCCESS;
    }

    public ItemStack getPianoDrop() {
        var type = this.getType();
        Item item = null;
        if (type == DoggyEntityTypes.GRAND_PIANO_BLACK.get())
            item = DoggyItems.GRAND_PIANO_BLACK.get();
        else if (type == DoggyEntityTypes.GRAND_PIANO_WHITE.get())
            item = DoggyItems.GRAND_PIANO_WHITE.get();
        else if (type == DoggyEntityTypes.UPRIGHT_PIANO_BLACK.get())
            item = DoggyItems.UPRIGHT_PIANO_BLACK.get();
        else if (type == DoggyEntityTypes.UPRIGHT_PIANO_BROWN.get())
            item = DoggyItems.UPRIGHT_PIANO_BROWN.get();
        else if (type == DoggyEntityTypes.UPRIGHT_PIANO_WHITE.get())
            item = DoggyItems.UPRIGHT_PIANO_WHITE.get();
        if (item == null)
            return ItemStack.EMPTY;
        return new ItemStack(item);
    }

    public PianoType getPianoType() { return this.pianoType; }
    public PianoColor getPianoColor() { return this.pianoColor; }

    public static enum PianoType { GRAND, UPRIGHT }

    public static enum PianoColor { BLACK, WHITE, BROWN }

}
