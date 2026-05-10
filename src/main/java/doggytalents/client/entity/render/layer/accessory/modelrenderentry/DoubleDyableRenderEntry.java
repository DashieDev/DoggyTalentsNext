package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.DogRenderState;
import doggytalents.client.entity.render.layer.accessory.DefaultAccessoryRenderer;
import doggytalents.common.entity.accessory.DoubleDyableAccessory.DoubleDyableAccessoryInstance;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;

public abstract class DoubleDyableRenderEntry extends Entry {

    @Override
    public void renderAccessory(RenderLayer<DogRenderState, DogModel> layer,
        PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight,
        DogRenderState renderState, AccessoryInstance inst) {

        var model = this.getModel();
        var dogModel = layer.getParentModel();
        dogModel.copyPropertiesTo(model);
        model.setupAnim(renderState);
        model.sync(dogModel);

        if (!(inst instanceof DoubleDyableAccessoryInstance dInst))
            return;

        float[] bgColor = dInst.getBgColor();
        float[] fgColor = dInst.getFgColor();

        doRenderLayer(isTranslucent(), model, getBgResource(dInst),
            poseStack, submitNodeCollector, packedLight, renderState, bgColor);
        doRenderLayer(isTranslucent(), model, getFgResource(dInst),
            poseStack, submitNodeCollector, packedLight, renderState, fgColor);
    }

    private static void doRenderLayer(boolean isTranslucent, SyncedAccessoryModel model,
        Identifier resource, PoseStack stack, SubmitNodeCollector collector, int light,
        DogRenderState renderState, float[] color) {
        if (isTranslucent) {
            DefaultAccessoryRenderer.renderTranslucentModel(model, resource,
                stack, collector, light, renderState, color[0], color[1], color[2], 1f);
        } else {
            AccessoryModelManager.renderColoredCutoutModel(model, resource,
                stack, collector, light, renderState, color[0], color[1], color[2]);
        }
    }

    @Override
    public boolean isDyable() {
        return true;
    }

    protected abstract Identifier getFgResource(AccessoryInstance inst);
    protected abstract Identifier getBgResource(AccessoryInstance inst);

    @Override
    public Identifier getResources(AccessoryInstance inst) {
        return getBgResource(inst);
    }

    @Override
    public boolean isTranslucent() {
        return true;
    }

}
