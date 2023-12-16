package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.CeramonyGarbModel;
import doggytalents.client.entity.model.LabCoatModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class CeramonyGarbRenderEntry extends Entry{
    public static final ModelLayerLocation CERA_GARB = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "ceramonial_garb"), "main");
    public CeramonyGarbModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new CeramonyGarbModel(ctx.bakeLayer(CERA_GARB));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CERA_GARB, CeramonyGarbModel::createBodyLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.CERA_GARB;
    }
    @Override
    public boolean isDyable() {
        return true;
    }

    
}
