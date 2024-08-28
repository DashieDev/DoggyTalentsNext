package doggytalents.common.item;

import java.util.function.Function;

import doggytalents.DoggyItemGroups;
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
        ItemStack returnStack = super.finishUsingItem(stack, level, user);
        boolean creativeUse = user instanceof Player && ((Player)user).getAbilities().instabuild;
        if (creativeUse)
            return returnStack;

        if (returnStack.isEmpty())
            return new ItemStack(Items.BOWL);
        
        if (!(user instanceof Player player))
            return returnStack;

        if (player.level.isClientSide)
            return returnStack;

        var bonusReturnStack = new ItemStack(Items.BOWL);
        var inv = player.getInventory();
        int freeSlot = inv.getFreeSlot();
        if (freeSlot >= 0)
            inv.add(bonusReturnStack);
        else
            player.spawnAtLocation(bonusReturnStack);
        
        return returnStack;
    }

    @Override
    public ItemStack getReturnStackAfterDogConsume(ItemStack useStack, AbstractDog dog) {
        return new ItemStack(Items.BOWL);
    }
    
}
