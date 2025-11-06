package doggytalents.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;

@Mixin(NameTagFeatureRenderer.Storage.class)
public interface NameTagFeatureRendererStorageAccessorMixin_1_21_9 {
 
    @Accessor("nameTagSubmitsSeethrough")
    public List<SubmitNodeStorage.NameTagSubmit> dtn__getNameTagSubmitsSeethrough();

    @Accessor("nameTagSubmitsNormal")
    public List<SubmitNodeStorage.NameTagSubmit> dtn__getNameTagSubmitsNormal();

}
