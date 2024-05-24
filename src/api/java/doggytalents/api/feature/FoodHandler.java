package doggytalents.api.feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import doggytalents.api.inferface.IDogFoodPredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class FoodHandler {

    private static final List<IDogFoodHandler> commonHandlers = new ArrayList<>(4);
    
    public static synchronized void registerHandler(IDogFoodHandler handler) {
        commonHandlers.add(handler);
    }

    public static Optional<IDogFoodPredicate> isFood(ItemStack stackIn) {
        return isFood(stackIn, null);
    }

    public static Optional<IDogFoodPredicate> isFood(ItemStack stackIn, @Nullable AbstractDog dog) {
        if (dog != null) {
            for (var handler : dog.getFoodHandlers()) {
                if (!handler.isFood(stackIn))
                    continue;
                return Optional.of(handler);
            }
        }

        if (stackIn.getItem() instanceof IDogFoodHandler) {
            if (((IDogFoodHandler) stackIn.getItem()).isFood(stackIn)) {
                return Optional.of((IDogFoodHandler) stackIn.getItem());
            }
        }

        for (IDogFoodHandler handler : commonHandlers) {
            if (handler.isFood(stackIn)) {
                return Optional.of(handler);
            }
        }

        return Optional.empty();
    }

    public static Optional<IDogFoodHandler> getMatch(@Nullable AbstractDog dogIn, ItemStack stackIn, @Nullable Entity entityIn) {
        
        //TODO temporary place here
        //This function and interfaces here still need some explicit constraints
        if (dogIn == null) return Optional.empty();
        
        for (IDogFoodHandler handler : dogIn.getFoodHandlers()) {
            if (handler.canConsume(dogIn, stackIn, entityIn)) {
                return Optional.of(handler);
            }
        }

        if (stackIn.getItem() instanceof IDogFoodHandler) {
            if (((IDogFoodHandler) stackIn.getItem()).canConsume(dogIn, stackIn, entityIn)) {
                return Optional.of((IDogFoodHandler) stackIn.getItem());
            }
        }

        for (IDogFoodHandler handler : commonHandlers) {
            if (handler.canConsume(dogIn, stackIn, entityIn)) {
                return Optional.of(handler);
            }
        }

        return Optional.empty();
    }
}
