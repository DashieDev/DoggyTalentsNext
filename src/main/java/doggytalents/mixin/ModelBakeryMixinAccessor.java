package doggytalents.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.resources.Identifier;

@Mixin(ModelBakery.class)
public interface ModelBakeryMixinAccessor {

    @Accessor("resolvedModels")
    Map<Identifier, ResolvedModel> dtn__getResolvedModels();

}
