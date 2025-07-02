package doggytalents.api.inferface;

import doggytalents.api.forge_imitate.inventory.ItemStackHandler;
import doggytalents.api.backward_imitate.ItemStackHandler_1_21_7;

public abstract class DogArmorItemHandler extends ItemStackHandler_1_21_7 {

    protected final AbstractDog dog;
    
    public DogArmorItemHandler(AbstractDog dog) {
        super(4);
        this.dog = dog;
    }

}
