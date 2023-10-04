package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.HeadBandModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.WigModel;
import doggytalents.client.entity.model.dog.kusa.LocatorOrbModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.lib.Constants;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class HeadBandRenderEntry extends AccessoryModelManager.Entry {
    public static final ModelLayerLocation HEAD_BAND = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "head_band"), "main");

    public HeadBandModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new HeadBandModel(ctx.bakeLayer(HEAD_BAND));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HEAD_BAND, HeadBandModel::createHeadBandLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return inst.getAccessory().getModelTexture();
    }
}
