package doggytalents.client.entity.render.layer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

@FunctionalInterface
public interface LayerFactory<S extends EntityRenderState, M extends EntityModel<? super S>> {

    RenderLayer<S, M> createLayer(RenderLayerParent<S, M> rendererIn, EntityRendererProvider.Context ctx);
}
