package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.accessories.TenguMaskModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.DogRenderState;
import doggytalents.common.entity.accessory.TenguMask;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class TenguMaskRenderEmtry extends Entry {

    public static final ModelLayerLocation DOG_TENGU_MASK_MODEL = new ModelLayerLocation(Util.getResource("dog_tengu_mask"), "main");

    public TenguMaskModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new TenguMaskModel(ctx.bakeLayer(DOG_TENGU_MASK_MODEL));
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_TENGU_MASK_MODEL, TenguMaskModel::createBodyLayer);
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public Identifier getResources(AccessoryInstance inst) {
        return Resources.TENGU_MASK;
    }

    @Override
    public void renderAccessory(RenderLayer<DogRenderState, DogModel> layer, PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector, int packedLight, DogRenderState renderState, AccessoryInstance inst) {
        this.model.wear();
        if (inst instanceof TenguMask.Inst tenguInst && tenguInst.unwear) {
            this.model.unWear();
        }
        super.renderAccessory(layer, poseStack, submitNodeCollector, packedLight, renderState, inst);
    }
}
