package doggytalents.common.item;

import java.util.function.Function;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.backward_imitate.DogFoodProperties_21_3;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public abstract class DogEddibleBowlFoodItem extends DogEddibleItem {

    public DogEddibleBowlFoodItem(Properties itemProps, DogFoodProperties_21_3 foodProps) {
        super(
            itemProps
                .craftRemainder(Items.BOWL),
            foodProps
        );
    }

    public DogEddibleBowlFoodItem(DogFoodProperties_21_3 foodProperties) {
        this(new Properties(), foodProperties);
    }

    public DogEddibleBowlFoodItem(Function<DogFoodProperties_21_3, DogFoodProperties_21_3> propsCreator) {
        this(
            new Properties(), 
            propsCreator.apply(new DogFoodProperties_21_3())
                //.build()
        );
    }

    public DogEddibleBowlFoodItem(Function<Item.Properties, Item.Properties> itemPropsCreator,
        Function<DogFoodProperties_21_3, DogFoodProperties_21_3> propsCreator) {
    
        this(itemPropsCreator.apply(new Properties()),
            propsCreator.apply(new DogFoodProperties_21_3()));
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

        if (player.level().isClientSide)
            return returnStack;

        var bonusReturnStack = new ItemStack(Items.BOWL);
        var inv = player.getInventory();
        int freeSlot = inv.getFreeSlot();
        if (freeSlot >= 0)
            inv.add(bonusReturnStack);
        else
            player.spawnAtLocation((ServerLevel) player.level(), bonusReturnStack);
        
        return returnStack;
    }

    @Override
    public ItemStack getReturnStackAfterDogConsume(ItemStack useStack, AbstractDog dog) {
        return new ItemStack(Items.BOWL);
    }
    
}
