package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SmartyGlassesModel;
import doggytalents.client.entity.model.SnorkelModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class SnorkelRenderEntry extends AccessoryModelManager.Entry {
    public static final ModelLayerLocation DOG_SNORKEL = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "snorkel"), "main");
    
    public SnorkelModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new SnorkelModel(ctx.bakeLayer(DOG_SNORKEL));
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_SNORKEL, SnorkelModel::createGlassesLayer);
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.SNORKEL;
    }
    @Override
    public boolean isTranslucent() {
        return true;
    }    
}
