package doggytalents.common.entity.misc;

import doggytalents.DoggyItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;

public class DogPlushie extends Entity {

    private static final EntityDataAccessor<Integer> COLLAR_COLOR = SynchedEntityData.defineId(DogPlushie.class, EntityDataSerializers.INT);

    public DogPlushie(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(COLLAR_COLOR, 11546150);
    }

    public void setCollarColor(int val) {
        this.entityData.set(COLLAR_COLOR, val);
    }

    public int getCollarColor() {
        return this.entityData.get(COLLAR_COLOR);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("PlushCollarColor", Tag.TAG_INT))
            this.setCollarColor(compound.getInt("PlushCollarColor"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("PlushCollarColor", this.getCollarColor());
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        
        this.setDeltaMovement(this.getDeltaMovement().scale(0.7D));

        this.pushOtherPlush();
    }

    @Override
    public boolean isPushable() {
        return true;
    }
    
    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (this.isRemoved())
            return true;
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
            return false;
        if (source.is(DamageTypeTags.IS_FIRE))
            return false;
        return !(source.getDirectEntity() instanceof Player);
    }

    @Override
    public boolean fireImmune() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        if (this.isInvulnerableTo(source))
            return false;
        mayDropSelf(source);
        this.discard();
        return true;
    }

    private void mayDropSelf(DamageSource source) {
        var entity = source.getEntity();
        if (!(entity instanceof Player player))
            return;
        if (player.getAbilities().instabuild)
            return;
        
        var drop = this.getDogPlusieItemDrop();
        if (!drop.isEmpty()) {
            this.spawnAtLocation(drop);
        }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        var item = stack.getItem();
        if (item == Items.TORCH) {
            if (!this.level().isClientSide && player.isShiftKeyDown())
                this.setYRot(this.getYRot() + 45);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    public ItemStack getDogPlusieItemDrop() {
        var item = DoggyItems.DOG_PLUSHIE_TOY.get();
        var stack = new ItemStack(item);
        item.setColor(stack, this.getCollarColor());
        return stack;
    }
    
    private void pushOtherPlush() {
        if (this.level().isClientSide)
            return;
        var list = this.level().getEntities(EntityTypeTest.forClass(DogPlushie.class), this.getBoundingBox(), e -> true);
        for (var e : list)
            e.push(this);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    public Level level() {
        return this.level;
    }

}
