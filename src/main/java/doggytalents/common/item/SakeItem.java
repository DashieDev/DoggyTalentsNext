package doggytalents.common.item;

import java.util.List;

import javax.annotation.Nullable;

import doggytalents.DoggyAdvancementTriggers;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.entity.Dog;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class SakeItem extends DogEddibleItem {
    
    public SakeItem() {
        super(b -> b
            .stacksTo(8).craftRemainder(Items.GLASS_BOTTLE),
        b -> b
            .alwaysEdible());
    }

    @Override
    public boolean canConsume(AbstractDog dog, ItemStack stackIn, @Nullable Entity entityIn) {
        if (entityIn == null)
            return false;
        if (!(entityIn instanceof Player player))
            return false;
        if (player.getCooldowns().isOnCooldown(this))
            return false;
        if (dog.getOwner() != player)
            return false;
        return !dog.isBaby() && super.canConsume(dog, stackIn, entityIn);
    }

    @Override
    public InteractionResult consume(AbstractDog dog, ItemStack stack, @Nullable Entity entityIn) {
        var ret = super.consume(dog, stack, entityIn);
        mayBoostOrDrunkEntity(dog, entityIn);
        if (entityIn instanceof Player player) {
            player.getCooldowns().addCooldown(this, 40);
        }
        return ret;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        var ret = stack;
        if (!(entity instanceof Player player))
            return ret;

        if (!player.level().isClientSide) {
            mayBoostOrDrunkEntity(player, null);
            player.getCooldowns().addCooldown(this, 40);
        }

        if (!player.getAbilities().instabuild)
            ret.shrink(1);

        if (ret.isEmpty())
            return new ItemStack(Items.GLASS_BOTTLE);

        var bonusReturnStack = new ItemStack(Items.GLASS_BOTTLE);
        var inv = player.getInventory();
        int freeSlot = inv.getFreeSlot();
        if (freeSlot >= 0)
            inv.add(bonusReturnStack);
        else
            player.spawnAtLocation(bonusReturnStack);
        return ret;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_42993_, Player p_42994_, InteractionHand p_42995_) {
        return ItemUtils.startUsingInstantly(p_42993_, p_42994_, p_42995_);
    }

    // @Override
    // public boolean isEdible() {
    //     return false;
    // }

    @Override
    public SoundEvent getDogEatingSound(AbstractDog dog) {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 32;
    }

    @Override
    public boolean alwaysEatWhenDogConsume(AbstractDog dog) {
        return true;
    }

    @Override
    public ItemStack getReturnStackAfterDogConsume(ItemStack useStack, AbstractDog dog) {
        return new ItemStack(Items.GLASS_BOTTLE);
    }

    private void mayBoostOrDrunkEntity(LivingEntity entity, @Nullable Entity feeder) {
        float r = entity.getRandom().nextFloat();
        boolean drunk = r <= 0.4f;
        if (!drunk) {
            if (!(entity instanceof Dog || entity instanceof Player))
                return;
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60 * 20, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60 * 20, 2));
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60 * 20, 0));
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 60 * 20, 0));
            return;
        }

        int r_drunkTicks = 20 * (30 + entity.getRandom().nextInt(15));
        if (entity instanceof Dog dog) {
            if (feeder instanceof ServerPlayer sP)
                DoggyAdvancementTriggers.DOG_DRUNK_TRIGGER.trigger(dog, sP);
            dog.setDrunkTicks(r_drunkTicks);
        } else if (entity instanceof Player player) {
            r_drunkTicks = 60 * 20;
            if (player.getRandom().nextBoolean())
                r_drunkTicks *=2;
            player.removeEffect(MobEffects.MOVEMENT_SPEED);
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, r_drunkTicks, 2));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, r_drunkTicks, 3));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, r_drunkTicks, 1));
            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, r_drunkTicks, 3));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId(stack) + ".description";
        components.add(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }
    
}
