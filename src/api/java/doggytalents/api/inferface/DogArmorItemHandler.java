package doggytalents.api.inferface;

import doggytalents.api.backward_imitate.ItemStackHandler_1_21_7;
import net.neoforged.neoforge.items.ItemStackHandler;

public abstract class DogArmorItemHandler extends ItemStackHandler_1_21_7 {

    protected final AbstractDog dog;
    
    public DogArmorItemHandler(AbstractDog dog) {
        super(4);
        this.dog = dog;
    }

}
