package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.accessories.KitsuneMaskModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.DogRenderState;
import doggytalents.common.entity.accessory.KitsuneMask;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class KitsuneMaskRenderEntry extends Entry {

    public static final ModelLayerLocation DOG_KITSUNE_MASK = new ModelLayerLocation(Util.getResource("dog_kitsune_mask"), "main");

    public KitsuneMaskModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new KitsuneMaskModel(ctx.bakeLayer(DOG_KITSUNE_MASK));
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_KITSUNE_MASK, KitsuneMaskModel::createBodyLayer);
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public Identifier getResources(AccessoryInstance inst) {
        return doggytalents.common.lib.Resources.KITSUNE_MASK;
    }

    @Override
    public void renderAccessory(RenderLayer<DogRenderState, DogModel> layer, PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector, int packedLight, DogRenderState renderState, AccessoryInstance inst) {
        this.model.wear();
        if (inst instanceof KitsuneMask.Inst kitsuneInst && kitsuneInst.unwear) {
            this.model.unWear();
        }
        super.renderAccessory(layer, poseStack, submitNodeCollector, packedLight, renderState, inst);
    }

}
