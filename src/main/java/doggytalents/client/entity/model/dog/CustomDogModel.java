package doggytalents.client.entity.model.dog;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;

public class CustomDogModel extends DogModel {

    private boolean accessory, incap;

    public CustomDogModel(ModelPart box, boolean accessory, boolean incap) {
        super(box);
        this.accessory = accessory;
        this.incap = incap;
    }

    @Override
    public boolean acessoryShouldRender(Dog dog, AccessoryInstance inst) {
        return accessory;
    }

    @Override
    public boolean incapShouldRender(Dog dog) {
        return incap;
    }
    
}
