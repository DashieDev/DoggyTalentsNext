package doggytalents.common.talent.doggy_tools;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import doggytalents.DoggyTalents;
import doggytalents.api.impl.IDogRangedAttackManager.UsingWeaponContext;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.misc.DogArrow;
import doggytalents.common.entity.misc.DogThrownTrident;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.EntityUtil;
import doggytalents.common.util.ItemUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.items.ItemStackHandler;

public interface ShootHandler {

    boolean updateUsingWeapon(DoggyToolsRangedAttack ranged_manager, UsingWeaponContext ctx);

    public static abstract class BowlikeShootHandler implements ShootHandler {
        @Override
        public boolean updateUsingWeapon(DoggyToolsRangedAttack ranged_manager, UsingWeaponContext ctx) {
            var dog = ctx.dog;
            if (!dog.isUsingItem()) {
                mayStartUsingWeapon(ctx);
                return false;
            }

            boolean should_stop_using = 
                !ctx.canSeeTarget && ctx.seeTime < -60;

            if (should_stop_using) {
                dog.stopUsingItem();
                return false;
            }

            if (ctx.canSeeTarget) {
                int using_tick = dog.getTicksUsingItem();
                if (this.isToolReadyToShoot(ranged_manager, dog, using_tick)) {
                    dog.stopUsingItem();
                    this.shoot(ranged_manager, dog, ctx.target, using_tick);
                    return true;
                }
            }
            return false;
        }

        private void mayStartUsingWeapon(UsingWeaponContext ctx) {
            if (ctx.cooldown <= 0 && ctx.seeTime >= -60) {
                ctx.dog.startUsingItem(InteractionHand.MAIN_HAND);
            }
        }

        public abstract boolean isToolReadyToShoot(DoggyToolsRangedAttack ranged_manager, AbstractDog dog, int tick_using);

        public abstract void shoot(DoggyToolsRangedAttack ranged_manager, AbstractDog dog, LivingEntity target, int tick_using);
    }

    public static ShootHandler NONE = new ShootHandler() {

        @Override
        public boolean updateUsingWeapon(DoggyToolsRangedAttack ranged_manager, UsingWeaponContext ctx) {
            return false;
        }
        
    };

    public static ShootHandler BOW = new BowlikeShootHandler() {

        @Override
        public boolean isToolReadyToShoot(DoggyToolsRangedAttack ranged_manager, AbstractDog dog, int tick_using) {
            return tick_using >= 20;
        }

        @Override
        public void shoot(DoggyToolsRangedAttack ranged_manager, AbstractDog dog, LivingEntity target, int tick_using) {
            shootFromBow(ranged_manager, dog, target, BowItem.getPowerForTime(tick_using));
        }

        private void shootFromBow(DoggyToolsRangedAttack ranged_manager, AbstractDog dog, LivingEntity target, float damage) {
            var arrowOptional = getAndConsumeDogArrow(dog, damage);
            if (!arrowOptional.isPresent())
                return;

            var arrow = arrowOptional.get();
            ranged_manager.shootProjectile(dog, arrow, target, SoundEvents.SKELETON_SHOOT);
        }
        
        public Optional<AbstractArrow> getAndConsumeDogArrow(AbstractDog dog, float damage) {
            var bowStack = dog.getMainHandItem();
            if (!(bowStack.getItem() instanceof BowItem)) {
                return Optional.empty();
            }
            
            var arrowInvOptional = DoggyToolsRangedAttack.findArrowsInInventory(dog);
            ItemStackHandler inv = null;
            int id = -1;
            if (arrowInvOptional.isPresent()) {
                inv = arrowInvOptional.get().getLeft();
                id = arrowInvOptional.get().getRight();
            }
            
            var arrow_stack = ItemStack.EMPTY;

            if (inv != null && id >= 0) {
                arrow_stack = inv.getStackInSlot(id).copy();
            }
            var projArrowOptional = getArrowFromBow(dog, bowStack, arrow_stack, damage);
            if (!projArrowOptional.isPresent())
                return Optional.empty();
            
            consumeArrow(dog, bowStack, arrow_stack);
            if (inv != null && id >= 0)
                inv.setStackInSlot(id, arrow_stack);

            bowStack.hurtAndBreak(1, dog, (p_150845_) -> {p_150845_.broadcastBreakEvent(InteractionHand.MAIN_HAND);});

            return projArrowOptional;
        }

        private Optional<AbstractArrow> getArrowFromBow(AbstractDog dog, ItemStack bow_stack, ItemStack arrowStack, float power) {
            if (!(bow_stack.getItem() instanceof BowItem bow))
                return Optional.empty();
            boolean is_infinity_bow = DoggyToolsRangedAttack.isInfinityBow(bow_stack);
    
            ItemStack select_arrowStack = null;
            Item select_arrow = null;
    
            if (is_infinity_bow) {
                select_arrow = Items.ARROW;
                select_arrowStack = new ItemStack(select_arrow);
            }
    
            if (arrowStack.getItem() instanceof ArrowItem arrowItem) {
                select_arrow = arrowItem;
                select_arrowStack = arrowStack;
            }
            
            if (select_arrowStack == null || select_arrowStack.isEmpty())
                return Optional.empty();
            if (!(select_arrow instanceof ArrowItem arrow))
                return Optional.empty();
                
            arrowStack = select_arrowStack;

            var arrow_proj = createDogArrow(dog, arrow, arrowStack);
            if (arrow_proj == null)
                return Optional.empty();
    
            arrow_proj = bow.customArrow(arrow_proj);
            if (power >= 1.0F) {
                arrow_proj.setCritArrow(true);
            }
    
            int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, bow_stack);
            if (j > 0) {
                arrow_proj.setBaseDamage(arrow_proj.getBaseDamage() + (double)j * 0.5D + 0.5D);
            }
    
            int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, bow_stack);
            if (k > 0) {
                arrow_proj.setKnockback(k);
            }
    
            boolean flaming_arrow = 
                EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, bow_stack) > 0
                || dog.getDogLevel(DoggyTalents.HELL_HOUND) >= 5;
            if (flaming_arrow) {
                arrow_proj.setSecondsOnFire(100);
            }
    
            if (is_infinity_bow) {
                arrow_proj.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            } else {
                arrow_proj.pickup = AbstractArrow.Pickup.ALLOWED;
            }
    
            return Optional.of(arrow_proj);
        }

        private AbstractArrow createDogArrow(AbstractDog dog, ArrowItem arrow_item, ItemStack arrow_stack) {
            if (!ConfigHandler.SERVER.DOGGY_TOOLS_PROJECTILE_PASS_ALLIES.get()) {
                return arrow_item.createArrow(dog.level(), arrow_stack, dog);
            }
            return new DogArrow(dog.level(), dog, arrow_stack.copyWithCount(1));
        }
    
        private void consumeArrow(AbstractDog dog, ItemStack bow_stack, ItemStack arrowStack) {
            boolean is_infinity_bow = DoggyToolsRangedAttack.isInfinityBow(bow_stack);
            
            if (!is_infinity_bow)
                arrowStack.shrink(1);
    
        }

    };

    public static ShootHandler TRIDENT = new BowlikeShootHandler() {

        @Override
        public boolean isToolReadyToShoot(DoggyToolsRangedAttack ranged_manager, AbstractDog dog, int tick_using) {
            return tick_using >= 20 && !ranged_manager.hasAwaitingTrident();
        }

        @Override
        public void shoot(DoggyToolsRangedAttack ranged_manager, AbstractDog dog, LivingEntity target, int tick_using) {
            shootFromTrident(ranged_manager, dog, target);
        }

        private void shootFromTrident(DoggyToolsRangedAttack ranged_manager, AbstractDog ddog, LivingEntity target) {
            if (!(ddog instanceof Dog dog))
                return;
            var tridentOptional = getAndConsumeDogTrident(dog);
            if (!tridentOptional.isPresent())
                return;

            var trident = tridentOptional.get();
            boolean flaming_trident = dog.getDogLevel(DoggyTalents.HELL_HOUND) >= 5;
            if (flaming_trident) {
                trident.setSecondsOnFire(100);
            }
            ranged_manager.shootProjectile(dog, trident, target, SoundEvents.TRIDENT_THROW);
            ranged_manager.setAwaitingTrident(trident);
        }

        private Optional<DogThrownTrident> getAndConsumeDogTrident(Dog dog) {
            var trident_stack = dog.getMainHandItem();
            if (!DogUtil.isTrident(trident_stack))
                return Optional.empty();
            
            var proj = new DogThrownTrident(dog, trident_stack.copy());
            proj.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            trident_stack.hurtAndBreak(1, dog, (p_150845_) -> {p_150845_.broadcastBreakEvent(InteractionHand.MAIN_HAND);});
            
            return Optional.of(proj);
        }
        
    };

    public static ShootHandler CROSSBOW = new ShootHandler() {

        @Override
        public boolean updateUsingWeapon(DoggyToolsRangedAttack ranged_manager, UsingWeaponContext ctx) {
            var dog = ctx.dog;
            var crossbow_stack = dog.getMainHandItem();
            if (!(crossbow_stack.getItem() instanceof CrossbowItem)) {
                if (dog.isUsingItem())
                    dog.stopUsingItem();
                return false;
            }
            var arrow_optional = DoggyToolsRangedAttack.findArrowsInInventory(dog);
            if (!arrow_optional.isPresent()) {
                if (dog.isUsingItem())
                    dog.stopUsingItem();
                return false;
            }
            
            var arrow_stack = arrow_optional.get();

            boolean attack_result = false;

            if (isCrossbowCharged(crossbow_stack)) {
                attack_result = updateCrossbowAttack(ranged_manager, dog, ctx.target, crossbow_stack);
            } else {
                updateChargeCrossbow(ranged_manager, dog, crossbow_stack, arrow_stack, ctx);
            }

            return attack_result;
        }

        private boolean isCrossbowCharged(ItemStack crossbow_stack) {
            return CrossbowItem.isCharged(crossbow_stack);
        }

        private void updateChargeCrossbow(DoggyToolsRangedAttack ranged_manager, AbstractDog dog,
            ItemStack crossbow_stack, Pair<ItemStackHandler, Integer> arrow_getter, UsingWeaponContext ctx) {
            ranged_manager.setDelayedCrossbowAttack(10);
            
            if (!dog.isUsingItem()) {
                mayStartUsingWeapon(ctx);
                return;
            }

            boolean should_stop_using = 
                !ctx.canSeeTarget && ctx.seeTime < -60;

            if (should_stop_using) {
                dog.stopUsingItem();
                return;
            }

            int using_tick = dog.getTicksUsingItem();
            if (using_tick >= CrossbowItem.getChargeDuration(crossbow_stack)) {
                dog.stopUsingItem();
                chargeCrossbowAndConsumeArrow(dog, crossbow_stack, arrow_getter);
            } 

        }

        private void chargeCrossbowAndConsumeArrow(AbstractDog dog, ItemStack crossbow_stack, Pair<ItemStackHandler, Integer> arrow_getter) {
            ItemStackHandler inv = null;
            int id = -1;
            inv = arrow_getter.getLeft();
            id = arrow_getter.getRight();

            ItemStack arrow_stack = ItemStack.EMPTY;
            if (inv != null && id >= 0) {
                arrow_stack = inv.getStackInSlot(id).copy();
            }

            if (arrow_stack.isEmpty())
                return;
            
            boolean is_multishot = EnchantmentHelper
                .getItemEnchantmentLevel(Enchantments.MULTISHOT, crossbow_stack) > 0;
            int shoot_amount = is_multishot ? 3 : 1;
            var item_list = new ArrayList<ItemStack>(shoot_amount);
            for (int i = 0; i < shoot_amount; ++i) {
                item_list.add(arrow_stack.copyWithCount(1));   
            }
            if (item_list.isEmpty()) 
                return;
            ItemUtil.addCrossbowProj(crossbow_stack, item_list);
            
            if (inv != null) {
                arrow_stack = arrow_stack.copy();
                arrow_stack.shrink(1);
                inv.setStackInSlot(id, arrow_stack);
            }


            dog.playSound(SoundEvents.CROSSBOW_LOADING_END, 1.0F,
                1.0F / (dog.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }

        private boolean updateCrossbowAttack(DoggyToolsRangedAttack ranged_manager, AbstractDog dog,
            LivingEntity target, ItemStack crossbow_stack) {
            ranged_manager.decDelayedCrossbowAttack();

            if (ranged_manager.getDelayedCrossbowAttack() <= 0) {
                ranged_manager.setDelayedCrossbowAttack(20);
                shootViaCrossbow(dog, target, crossbow_stack);
                return true;
            }
            return false;
        }

        private void shootViaCrossbow(AbstractDog dog, LivingEntity target, ItemStack crossbow_stack) {
            if (!(crossbow_stack.getItem() instanceof CrossbowItem crossbowitem))
                return;
            CrossbowItem.performShooting(
                dog.level(), dog, InteractionHand.MAIN_HAND, crossbow_stack, 1.6f, 2
            );
            CrossbowItem.setCharged(crossbow_stack, false);
            hellHoundSetFireToOwnedArrows(dog);
        }

        private boolean hellHoundSetFireToOwnedArrows(AbstractDog dog) {
            if (!dog.fireImmune())
                return false;
            
            Predicate<AbstractArrow> is_target_arrow = e -> {
                if (!e.isAlive())
                    return false;
                if (e.getOwner() != dog)
                    return false;
                return true;
            };

            var bb = dog.getBoundingBox().inflate(1);

            var targets = dog.level()
                .getEntitiesOfClass(AbstractArrow.class, bb, is_target_arrow);
            
            if (targets.isEmpty())
                return false;
            
            for (var target : targets)
                target.setSecondsOnFire(20);
            return true;
        }

        private void mayStartUsingWeapon(UsingWeaponContext ctx) {
            if (ctx.cooldown <= 0 && ctx.seeTime >= -60) {
                ctx.dog.startUsingItem(InteractionHand.MAIN_HAND);
            }
        }
        
    };

}