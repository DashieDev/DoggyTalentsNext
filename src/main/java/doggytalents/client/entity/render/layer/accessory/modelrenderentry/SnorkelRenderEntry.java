package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.accessories.SmartyGlassesModel;
import doggytalents.client.entity.model.accessories.SnorkelModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class SnorkelRenderEntry extends AccessoryModelManager.Entry {
    public static final ModelLayerLocation DOG_SNORKEL = new ModelLayerLocation(Util.getResource("snorkel"), "main");
    
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
    public Identifier getResources(AccessoryInstance inst) {
        return Resources.SNORKEL;
    }
    @Override
    public boolean isTranslucent() {
        return true;
    }    
}
