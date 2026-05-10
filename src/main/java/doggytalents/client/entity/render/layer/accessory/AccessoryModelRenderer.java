package doggytalents.client.entity.render.layer.accessory;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.client.entity.render.DogRenderState;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class AccessoryModelRenderer extends RenderLayer<DogRenderState, DogModel>  {

    public AccessoryModelRenderer(RenderLayerParent<DogRenderState, DogModel> parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        AccessoryModelManager.resolve(ctx);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, DogRenderState renderState, float yRot, float xRot) {
        var dog = renderState.dog;
        if (dog == null || renderState.isInvisible) {
            return;
        }

        var activeSkin = renderState.activeSkin;
        for (var accessoryInst : dog.getAccessories()) {
            var skin = activeSkin;
            if (skin.useCustomModel()) {
                var model = skin.getCustomModel().getValue();
                if (!model.acessoryShouldRender(dog, accessoryInst)) {
                    continue;
                }
            }
            var accessory = accessoryInst.getAccessory();
            if (!(accessory instanceof IAccessoryHasModel)) continue;
            var hasModelAccessory = (IAccessoryHasModel) accessory;
            hasModelAccessory.getRenderEntry().renderAccessory(
                this, poseStack, submitNodeCollector, packedLight, renderState, accessoryInst);
        }

    }

}
