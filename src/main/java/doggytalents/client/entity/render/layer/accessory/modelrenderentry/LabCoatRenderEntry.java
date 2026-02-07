package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.accessories.ChefHatModel;
import doggytalents.client.entity.model.accessories.LabCoatModel;
import doggytalents.client.entity.model.accessories.WigModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
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
    public Identifier getResources(AccessoryInstance inst) {
        return Resources.LAB_COAT;
    }

    @Override
    public boolean isDyable() {
        return true;
    }

    
}
