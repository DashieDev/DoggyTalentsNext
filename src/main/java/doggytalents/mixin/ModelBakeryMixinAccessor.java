package doggytalents.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;

@Mixin(ModelBakery.class)
public interface ModelBakeryMixinAccessor {
    
    @Accessor("topLevelModels")
    Map<ModelResourceLocation, UnbakedModel> dtn__getTopLevelModels();

}
