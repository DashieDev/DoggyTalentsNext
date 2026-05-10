package doggytalents.client.entity.model.animation;

import java.util.Optional;
import java.util.function.Function;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Unit;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

public abstract class SimpleAnimatedModel extends Model<net.minecraft.util.Unit> {

    public SimpleAnimatedModel(ModelPart root, Function<Identifier, RenderType> p_103110_) {
        super(root, p_103110_);
    }

    @Override
    public void setupAnim(net.minecraft.util.Unit state) {
        // no-op: animation is driven by custom calls
    }

    public abstract Optional<ModelPart> getPartFromName(String name);

    public abstract void resetPart(ModelPart part);

    public abstract void resetAllPose();
    
}
