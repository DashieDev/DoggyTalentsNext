package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.KitsuneFrontModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.WigModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class KitsuneFrontRenderEntry extends AccessoryModelManager.Entry {
    public static final ModelLayerLocation DOG_KITSUNE_FRONT = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "kitsune_front"), "main");
    
    public KitsuneFrontModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new KitsuneFrontModel(ctx.bakeLayer(DOG_KITSUNE_FRONT));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }
    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_KITSUNE_FRONT, KitsuneFrontModel::createKitsuneFrontLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.KITSUNE_FRONT;
    }
    
}
