package doggytalents.common.item;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public abstract class DogEddibleBowlFoodItem extends DogEddibleItem {

    public DogEddibleBowlFoodItem(Properties itemProps) {
        super(
            itemProps
                .stacksTo(1)
                .craftRemainder(Items.BOWL)
        );
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        ItemStack itemstack = super.finishUsingItem(stack, level, user);
        return user instanceof Player && ((Player)user).getAbilities().instabuild ? itemstack : new ItemStack(Items.BOWL);
    }

    @Override
    public ItemStack getReturnStackAfterDogConsume(ItemStack useStack, AbstractDog dog) {
        return new ItemStack(Items.BOWL);
    }
    
}
