package doggytalents.common.entity.misc;

import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.tags.DamageTypeTags;
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

public abstract class BaseDogPlushie extends Entity {

    public BaseDogPlushie(EntityType<?> type, Level level) {
        super(type, level);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        
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

    private void pushOtherPlush() {
        if (this.level().isClientSide)
            return;
        var list = this.level().getEntities(EntityTypeTest.forClass(BaseDogPlushie.class), this.getBoundingBox(), e -> true);
        for (var e : list)
            e.push(this);
    }

    public abstract ItemStack getDogPlusieItemDrop();


    
}
