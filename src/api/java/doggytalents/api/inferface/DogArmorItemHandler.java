package doggytalents.api.inferface;

import net.neoforged.neoforge.items.ItemStackHandler;

public abstract class DogArmorItemHandler extends ItemStackHandler {

    protected final AbstractDog dog;
    
    public DogArmorItemHandler(AbstractDog dog) {
        super(4);
        this.dog = dog;
    }

}
