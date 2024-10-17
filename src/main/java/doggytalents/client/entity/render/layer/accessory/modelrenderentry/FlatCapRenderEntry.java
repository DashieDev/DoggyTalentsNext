package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.FlatCapModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class FlatCapRenderEntry extends Entry {
    
    public static final ModelLayerLocation DOG_FLATCAP = new ModelLayerLocation(Util.getResource("dog_flatcap"), "main");
    
    public FlatCapModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new FlatCapModel(ctx.bakeLayer(DOG_FLATCAP));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_FLATCAP, FlatCapModel::createLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.DOG_FLATCAP;
    }

    @Override
    public boolean isDyeable() {
        return true;
    }

}
