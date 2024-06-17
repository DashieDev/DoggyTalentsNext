package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.ElytraCapeModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class AngelWingsRenderEntry extends Entry{
    public static final ModelLayerLocation ANGEL_WINGS = new ModelLayerLocation(Util.getResource("angel_wings"), "main");

    private ElytraCapeModel model;

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.ANGEL_WINGS;
    }

    @Override
    public void initModel(Context ctx) {
        this.model = new ElytraCapeModel(ctx.bakeLayer(ANGEL_WINGS));
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ANGEL_WINGS, ElytraCapeModel::angel);
    }
    @Override
    public boolean isDyable() {
        return true;
    }
    
}
