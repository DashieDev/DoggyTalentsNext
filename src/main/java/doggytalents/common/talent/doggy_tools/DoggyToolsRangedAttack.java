package doggytalents.common.talent.doggy_tools;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import doggytalents.DoggyTalents;
import doggytalents.api.forge_imitate.inventory.ItemStackHandler;
import doggytalents.api.impl.IDogRangedAttackManager;
import doggytalents.api.impl.IDogRangedAttackManager.UsingWeaponContext;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.misc.DogThrownTrident;
import doggytalents.common.talent.PackPuppyTalent;
import doggytalents.common.util.DogUtil;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class DoggyToolsRangedAttack implements IDogRangedAttackManager {

    @Override
    public boolean isApplicable(AbstractDog dog) {
        return getActiveShootHandler(dog) != ShootHandler.NONE;
    }

    @Override
    public boolean updateUsingWeapon(UsingWeaponContext ctx) {

        this.validateAwaitingTrident();

        var handler = getActiveShootHandler(ctx.dog);

        return handler.updateUsingWeapon(this, ctx);
    }

    public void shootProjectile(AbstractDog dog, Projectile proj, LivingEntity target,
        SoundEvent shoot_sound) {
        final double aim_y_offset_l_xz_influence = 0.2;
        final double aim_y_offset_l_xz_influence_down = 0.1;

        double dx = target.getX() - dog.getX();
        double dz = target.getZ() - dog.getZ();
        double l_xz = Math.sqrt(dx * dx + dz * dz);

        double aim_y = target.getY()
            + 0.5 * target.getBbHeight(); 
        double dy = aim_y - proj.getY();
        if (dy > 0) {
            dy += l_xz * aim_y_offset_l_xz_influence;
        } else {
            dy += l_xz * aim_y_offset_l_xz_influence_down;
        }
        
        double shoot_dir_x = dx;
        double shoot_dir_y = dy;
        double shoot_dir_z = dz;
        float power = 1.6f;
        float error_window = 2;
        proj.shoot(shoot_dir_x, shoot_dir_y, shoot_dir_z, 
            power, error_window);

        dog.playSound(shoot_sound, 1.0F, 1.0F / (dog.getRandom().nextFloat() * 0.4F + 0.8F));
        dog.level().addFreshEntity(proj);
    }

    @Override
    public void onStop(AbstractDog dog) {
        dog.stopUsingItem();
        this.awaitingTrident = Optional.empty();
    }

    private Optional<DogThrownTrident> awaitingTrident = Optional.empty();

    private void validateAwaitingTrident() {
        if (awaitingTrident == null)
            return;
        if (!awaitingTrident.isPresent())
            return;
        var trident = awaitingTrident.get();
        if (!trident.isAlive()) {
            this.awaitingTrident = Optional.empty();
            return;
        }
    }

    public boolean hasAwaitingTrident() {
        return this.awaitingTrident != null && this.awaitingTrident.isPresent();
    }

    public void setAwaitingTrident(DogThrownTrident trident) {
        this.awaitingTrident = Optional.ofNullable(trident);
    }

    private int delayedCrossbowAttack;

    public void decDelayedCrossbowAttack() {
        if (delayedCrossbowAttack > 0)
            --delayedCrossbowAttack;
    }

    public void setDelayedCrossbowAttack(int x) {
        this.delayedCrossbowAttack = x;
    }

    public int getDelayedCrossbowAttack() {
        return delayedCrossbowAttack;
    }

    public static boolean isTridentAndEligible(ItemStack stack) {
        if (!DogUtil.isTrident(stack))
            return false;
        if (!ConfigHandler.SERVER.DOGGY_TOOLS_USE_TRIDENT.get())
            return false;
        if (EnchantmentHelper.getLoyalty(stack) < 2)
            return false; 
        return true;
    }

    public static Optional<Pair<ItemStackHandler, Integer>> findArrowsInInventory(AbstractDog dog) {
        var talentInstOptional = dog.getTalent(DoggyTalents.DOGGY_TOOLS);
        if (!talentInstOptional.isPresent())
            return Optional.empty();
        if (!(talentInstOptional.get() instanceof DoggyToolsTalent tools))
            return Optional.empty();

        ItemStackHandler inv = tools.getTools();
        int id = findArrowStackInInventory(inv);
        if (id >= 0)
            return Optional.of(Pair.of(inv, id));

        talentInstOptional = dog.getTalent(DoggyTalents.PACK_PUPPY);
        if (!talentInstOptional.isPresent())
            return Optional.empty();
        if (!(talentInstOptional.get() instanceof PackPuppyTalent packPup))
            return Optional.empty();
        
        inv = packPup.inventory();
        id = findArrowStackInInventory(inv);
        if (id >= 0)
            return Optional.of(Pair.of(inv, id));
        return Optional.empty();
    }

    private static int findArrowStackInInventory(ItemStackHandler inv) {
        if (inv == null)
            return -1;
        int selected_id = -1;
        for (int i = 0; i < inv.getSlots(); ++i) {
            var stack = inv.getStackInSlot(i);
            if (!(stack.getItem() instanceof ArrowItem))
                continue;
            selected_id = i;
            break;
        }
        return selected_id;
    }

    public static boolean isInfinityBow(ItemStack bowStack) {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bowStack) > 0;
    }

    public static ShootHandler getActiveShootHandler(AbstractDog dog) {
        var mainhand_item = dog.getMainHandItem();
        return getShootHandler(mainhand_item, dog);
    }

    public static ShootHandler getShootHandler(ItemStack stack, AbstractDog dog) {
        if (stack.isEmpty())
            return ShootHandler.NONE;
        if (stack.getItem() instanceof BowItem) {
            boolean eligible = 
                isInfinityBow(stack)
                || findArrowsInInventory(dog).isPresent();
            if (eligible)
                return ShootHandler.BOW;
        }
        if (stack.getItem() instanceof CrossbowItem) {
            boolean eligible =  findArrowsInInventory(dog).isPresent();
            if (eligible)
                return ShootHandler.CROSSBOW;
        }
        if (isTridentAndEligible(stack))
            return ShootHandler.TRIDENT;
        return ShootHandler.NONE;
    }
}
