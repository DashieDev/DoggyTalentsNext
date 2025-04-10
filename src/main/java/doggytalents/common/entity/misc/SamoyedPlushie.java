package doggytalents.common.entity.misc;

import doggytalents.DoggyItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;

public class SamoyedPlushie extends Entity {

    public SamoyedPlushie(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
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
    public boolean hurt(DamageSource source, float damage) {
        if (this.isInvulnerableTo(source))
            return false;
        var killer = source.getDirectEntity();
        boolean killedByCreative = 
            (killer instanceof Player player)
            && player.getAbilities().instabuild;
        var drop = this.getDogPlusieItemDrop();
        if (!drop.isEmpty() && !killedByCreative) {
            this.spawnAtLocation(drop);
        }
        this.discard();
        return true;
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
        var item = DoggyItems.SAMOYED_PLUSHIE.get();
        var stack = new ItemStack(item);
        return stack;
    }
    
    private void pushOtherPlush() {
        if (this.level().isClientSide)
            return;
        var list = this.level().getEntities(EntityTypeTest.forClass(SamoyedPlushie.class), this.getBoundingBox(), e -> true);
        for (var e : list)
            e.push(this);
    }

    @Override
    protected void defineSynchedData(Builder p_326003_) {
    }

}
