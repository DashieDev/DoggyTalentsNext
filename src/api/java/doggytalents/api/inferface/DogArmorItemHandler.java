package doggytalents.api.inferface;

import doggytalents.api.forge_imitate.inventory.ItemStackHandler;

public abstract class DogArmorItemHandler extends ItemStackHandler {

    protected final AbstractDog dog;
    
    public DogArmorItemHandler(AbstractDog dog) {
        super(4);
        this.dog = dog;
    }

}
