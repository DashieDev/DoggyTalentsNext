package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.PlagueDoctorMaskModel;
import doggytalents.client.entity.model.SnorkelModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class PlagueDoctorMaskRenderEntry extends AccessoryModelManager.Entry{
    public static final ModelLayerLocation DOG_PLAGUE_DOC = new ModelLayerLocation(Util.getResource("dog_plague_doc"), "main");
    
    public PlagueDoctorMaskModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new PlagueDoctorMaskModel(ctx.bakeLayer(DOG_PLAGUE_DOC));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_PLAGUE_DOC, PlagueDoctorMaskModel::createBodyLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.PLAGUE_DOC_MASK;
    }
    
}
