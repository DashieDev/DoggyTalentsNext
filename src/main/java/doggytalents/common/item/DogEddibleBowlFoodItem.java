package doggytalents.common.item;

import java.util.function.Function;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public abstract class DogEddibleBowlFoodItem extends DogEddibleItem {

    public DogEddibleBowlFoodItem(Properties itemProps, FoodProperties foodProps) {
        super(
            itemProps
                .stacksTo(1)
                .craftRemainder(Items.BOWL),
            foodProps
        );
    }

    public DogEddibleBowlFoodItem(FoodProperties foodProperties) {
        this(new Properties(), foodProperties);
    }

    public DogEddibleBowlFoodItem(Function<FoodProperties.Builder, FoodProperties.Builder> propsCreator) {
        this(
            new Properties(), 
            propsCreator.apply(new FoodProperties.Builder())
                .build()
        );
    }

    public DogEddibleBowlFoodItem(Function<Item.Properties, Item.Properties> itemPropsCreator,
        Function<FoodProperties.Builder, FoodProperties.Builder> propsCreator) {
    
        this(itemPropsCreator.apply(new Properties()),
            propsCreator.apply(new FoodProperties.Builder()).build());
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
