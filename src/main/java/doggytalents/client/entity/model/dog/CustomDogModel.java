package doggytalents.client.entity.model.dog;

import javax.annotation.Nullable;

import org.joml.Vector3f;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;

public class CustomDogModel extends DogModel {

    private boolean accessory, incap;
    private Vector3f customRootPivot = null;

    public CustomDogModel(ModelPart box, boolean accessory, boolean incap, Vector3f pivot) {
        super(box);
        this.accessory = accessory;
        this.incap = incap;
        this.customRootPivot = pivot;
    }

    @Override
    public boolean acessoryShouldRender(Dog dog, AccessoryInstance inst) {
        return accessory;
    }

    @Override
    public boolean incapShouldRender(Dog dog) {
        return incap;
    }

    @Override
    public boolean scaleBabyDog() {
        return false;
    }

    @Override
    public @Nullable Vector3f getCustomRootPivotPoint() {
        return this.customRootPivot;
    }
    
}
