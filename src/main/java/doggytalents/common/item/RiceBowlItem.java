package doggytalents.common.item;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class RiceBowlItem extends Item implements IDogEddible {

    public static final FoodProperties FOOD_PROPS =
        (new FoodProperties.Builder())
            .nutrition(3)
            .saturationMod(0.5F)
            .build();
    
    public RiceBowlItem() {
        super(
            (new Properties()).food(
                FOOD_PROPS
            ).stacksTo(1).craftRemainder(Items.BOWL)
        ); 
    }

    @Override
    public float getAddedHunger(ItemStack useStack, AbstractDog dog) {
        return FOOD_PROPS.getNutrition() * 5;
    }

    @Override
    public ItemStack getReturnStackAfterDogConsume(ItemStack useStack, AbstractDog dog) {
        return new ItemStack(Items.BOWL);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
        ItemStack itemstack = super.finishUsingItem(stack, level, living);
        return living instanceof Player && ((Player)living).getAbilities().instabuild ? itemstack : new ItemStack(Items.BOWL);
    }

}
