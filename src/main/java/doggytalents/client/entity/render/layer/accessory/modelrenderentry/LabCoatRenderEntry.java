package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.ChefHatModel;
import doggytalents.client.entity.model.LabCoatModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.WigModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class LabCoatRenderEntry extends Entry{
    public static final ModelLayerLocation LAB_COAT = new ModelLayerLocation(Util.getResource("lab_coat"), "main");
    
    public LabCoatModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new LabCoatModel(ctx.bakeLayer(LAB_COAT));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LAB_COAT, LabCoatModel::createBodyLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.LAB_COAT;
    }

    @Override
    public boolean isDyeable() {
        return true;
    }

    
}
