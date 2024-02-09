package doggytalents.client.entity.model.animation;

import java.util.Optional;
import java.util.function.Function;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public abstract class SimpleAnimatedModel extends Model  {

    public SimpleAnimatedModel(Function<ResourceLocation, RenderType> p_103110_) {
        super(p_103110_);
        //TODO Auto-generated constructor stub
    }

    public abstract Optional<ModelPart> getPartFromName(String name);

    public abstract void resetPart(ModelPart part);

    public abstract void resetAllPose();
    
}
