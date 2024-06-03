package doggytalents.common.talent.doggy_tools;

import java.util.Optional;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.misc.DogThrownTrident;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.EntityUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.items.ItemStackHandler;

public interface ShootHandler {

    boolean isToolReadyToShoot(DoggyToolsRangedAttack ranged_manager, AbstractDog dog, int tick_using);

    void shoot(DoggyToolsRangedAttack ranged_manager, AbstractDog dog, LivingEntity target, int tick_using);

    public static ShootHandler NONE = new ShootHandler() {

        @Override
        public boolean isToolReadyToShoot(DoggyToolsRangedAttack ranged_manager, AbstractDog dog, int tick_using) {
            return false;
        }

        @Override
        public void shoot(DoggyToolsRangedAttack ranged_manager, AbstractDog dog, LivingEntity target, int tick_using) {
        }
        
    };

    public static ShootHandler BOW = new ShootHandler() {

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

            bowStack.hurtAndBreak(1, dog, EquipmentSlot.MAINHAND);

            return projArrowOptional;
        }

        private Optional<AbstractArrow> getArrowFromBow(AbstractDog dog, ItemStack bow_stack, ItemStack arrowStack, float power) {
            if (!(bow_stack.getItem() instanceof BowItem bow))
                return Optional.empty();
            boolean is_infinity_bow = DoggyToolsRangedAttack.isInfinityBow(bow_stack);
    
            ArrowItem arrow = null;
    
            if (is_infinity_bow)
                arrow = (ArrowItem) Items.ARROW;
    
            if (arrowStack.getItem() instanceof ArrowItem arrowItem)
                arrow = arrowItem;
            
            if (arrow == null)  
                return Optional.empty();
    
            var arrow_proj = arrow.createArrow(dog.level(), arrowStack, dog);
            if (arrow_proj == null)
                return Optional.empty();
    
            arrow_proj = bow.customArrow(arrow_proj, arrowStack);
            if (power >= 1.0F) {
                arrow_proj.setCritArrow(true);
            }
    
            int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER, bow_stack);
            if (j > 0) {
                arrow_proj.setBaseDamage(arrow_proj.getBaseDamage() + (double)j * 0.5D + 0.5D);
            }
    
            int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH, bow_stack);
            if (k > 0) {
                arrow_proj.setKnockback(k);
            }
    
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAME, bow_stack) > 0) {
                EntityUtil.setSecondsOnFire(arrow_proj, 100);
            }
    
            if (is_infinity_bow) {
                arrow_proj.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            } else {
                arrow_proj.pickup = AbstractArrow.Pickup.ALLOWED;
            }
    
            return Optional.of(arrow_proj);
        }
    
        private void consumeArrow(AbstractDog dog, ItemStack bow_stack, ItemStack arrowStack) {
            boolean is_infinity_bow = DoggyToolsRangedAttack.isInfinityBow(bow_stack);
            
            if (!is_infinity_bow)
                arrowStack.shrink(1);
    
        }

    };

    public static ShootHandler TRIDENT = new ShootHandler() {

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
            ranged_manager.shootProjectile(dog, trident, target, SoundEvents.TRIDENT_THROW);
            ranged_manager.setAwaitingTrident(trident);
        }

        private Optional<DogThrownTrident> getAndConsumeDogTrident(Dog dog) {
            var trident_stack = dog.getMainHandItem();
            if (!DogUtil.isTrident(trident_stack))
                return Optional.empty();
            
            var proj = new DogThrownTrident(dog, trident_stack.copy());
            proj.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            trident_stack.hurtAndBreak(1, dog, EquipmentSlot.MAINHAND);
            
            return Optional.of(proj);
        }
        
    };

}