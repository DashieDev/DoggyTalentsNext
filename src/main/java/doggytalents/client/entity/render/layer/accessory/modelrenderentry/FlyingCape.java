package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.ElytraCapeModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class FlyingCape extends Entry {

    public static final ModelLayerLocation FLYING_CAPE = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "elytra_and_cape"), "main");

    private ElytraCapeModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new ElytraCapeModel(ctx.bakeLayer(FLYING_CAPE));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }
    
    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FLYING_CAPE, ElytraCapeModel::createLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.SUPERDOG_MODEL;
    }
    
}
