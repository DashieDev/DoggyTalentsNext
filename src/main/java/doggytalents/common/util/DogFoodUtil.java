package doggytalents.common.util;

import java.util.Optional;

import javax.annotation.Nullable;

import doggytalents.api.feature.FoodHandler;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.MeatFoodHandler;
import doggytalents.common.item.DogEddibleItem;
import doggytalents.common.item.IDogEddible;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemStackHandler;

public class DogFoodUtil {
    
    private static final MeatFoodHandler meat_food_handler_limited = new MeatFoodHandler() {

        @Override
        public boolean isFood(ItemStack stack) {
            var props = stack.getFoodProperties(null);

            if (props == null) return false;
            return stack.is(ItemTags.MEAT) && stack.getItem() != Items.ROTTEN_FLESH
                && props.nutrition() >= 6;
        }
        
    };

    public static MeatFoodHandler limitedMeatFoodHandler() {
        return meat_food_handler_limited;
    }

    public static int dogFindFoodInInv(Dog dog, boolean findHealingFood, ItemStackHandler inv) {
        return dogFindFoodInInv(dog, dog, findHealingFood, inv);
    }

    public static int dogFindFoodInInv(Dog finder, 
        Dog target, boolean findHealingFood, ItemStackHandler inv) {
        int eddibleFoodId = dogFindBestDogEddibleFood(finder, target, findHealingFood, inv);
        if (eddibleFoodId >= 0)
            return eddibleFoodId;
        int meatFoodId = dogFindMeatFood(finder, target, inv);
        if (meatFoodId >= 0)
            return meatFoodId;

        return -1;
    }

    public static int dogFindBestDogEddibleFood(Dog finder, Dog target, boolean findHealingFood, ItemStackHandler inv) {
        var inventory = inv;
        if (inventory == null)
            return -1;

        float minNutrition = -1; 
        int selectedStack = -1;
        
        for (int i = 0; i < inventory.getSlots(); i++) {
            var stack = inventory.getStackInSlot(i);
            var item = stack.getItem();
            if (!(item instanceof DogEddibleItem eddible))
                continue;
            if (!eddible.canConsume(target, stack, finder))
                continue;
            if (findHealingFood && checkRegenEffects(target, stack, eddible)) {
                return i;
            }
            float addedNutrition = eddible.getAddedHungerWhenDogConsume(stack, target);
            if (minNutrition < 0) {
                minNutrition = addedNutrition;
                selectedStack = i;
                continue;
            }
            if (minNutrition > addedNutrition) {
                minNutrition = addedNutrition;
                selectedStack = i;
            }
        }
        return selectedStack;
    }

    private static boolean checkRegenEffects(AbstractDog target, ItemStack stack, DogEddibleItem item) {
        var effects = item.getAdditionalEffectsWhenDogConsume(stack, target);
        for (var pair : effects) {
            var effect = pair.effect();
            if (effect.getEffect() == MobEffects.REGENERATION)
                return true;
        }
        return false;
    }

    public static int dogFindMeatFood(Dog finder, Dog target, ItemStackHandler inv) {
        var inventory = inv;
        if (inventory == null)
            return -1;

        for (int i = 0; i < inventory.getSlots(); i++) {
            var stack = inventory.getStackInSlot(i);
            if (meat_food_handler_limited.canConsume(target, stack, finder)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean tryFeed(Dog dog, boolean findHealingFood,
        ItemStackHandler inv) {
        return tryFeed(dog, dog, findHealingFood, inv);
    }

    public static boolean tryFeed(Dog dog, Dog feeder, 
        boolean findHealingFood, ItemStackHandler inv) {
        int foodSlot = DogFoodUtil.dogFindFoodInInv(feeder, dog, 
            findHealingFood, inv);
        if (foodSlot < 0)
            return false;
        var feedStack = inv.getStackInSlot(foodSlot)
            .copy();
        var feedItem = feedStack.getItem();
        if (feedItem instanceof IDogEddible eddible) {
            eddible.consume(dog, feedStack, feeder);
        } else {
            limitedMeatFoodHandler().consume(dog, feedStack, feeder);
        }
        inv.setStackInSlot(foodSlot, feedStack);
        return true;
    }

    public static InteractionResult tryFeedAny(
        AbstractDog dog, @Nullable Entity feeder, IItemHandlerModifiable inv) {

        int found_food_id = -1;
        IDogFoodHandler found_food = null;
    
        for (int i = 0; i < inv.getSlots(); ++i) {
            var stack = inv.getStackInSlot(i);
            var food = FoodHandler.getMatch(dog, stack, feeder);
            if (!food.isPresent())
                continue;
            found_food_id = i;
            found_food = food.get(); 
            break;
        }

        if (found_food_id < 0 || found_food == null)
            return InteractionResult.PASS;

        var feed_stack = inv.getStackInSlot(found_food_id).copy();
        var response = found_food.consume(dog, feed_stack, feeder);
        inv.setStackInSlot(found_food_id, feed_stack);
        return response;
    }

}
