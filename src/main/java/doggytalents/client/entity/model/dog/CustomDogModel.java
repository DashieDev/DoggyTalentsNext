package doggytalents.client.entity.model.dog;

import javax.annotation.Nullable;

import org.joml.Vector3f;

import doggytalents.api.events.RegisterCustomDogModelsEvent.DogModelProps;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;

public class CustomDogModel extends DogModel {

    private final DogModelProps props;

    public CustomDogModel(ModelPart box, DogModelProps props) {
        super(box);
        this.props = props;
    }

    @Override
    public boolean acessoryShouldRender(Dog dog, AccessoryInstance inst) {
        return props.shouldRenderAccessories;
    }

    @Override
    public boolean incapShouldRender(Dog dog) {
        return props.shouldRenderIncapacitated;
    }

    @Override
    public boolean scaleBabyDog() {
        return false;
    }

    @Override
    public @Nullable Vector3f getCustomRootPivotPoint() {
        return this.props.customRootPivot;
    }

    @Override
    public boolean hasDefaultScale() {
        return this.props.hasDefaultScale;
    }

    @Override
    public float getDefaultScale() {
        return this.props.defaultScale;
    }
    
}
