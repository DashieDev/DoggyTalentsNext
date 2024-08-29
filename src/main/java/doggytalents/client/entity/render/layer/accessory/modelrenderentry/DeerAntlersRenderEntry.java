package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.WigModel;
import doggytalents.client.entity.model.dog.kusa.DeerAntlersModel;
import doggytalents.client.entity.model.dog.kusa.LocatorOrbModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import doggytalents.forge_imitate.event.client.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class DeerAntlersRenderEntry extends Entry {

    public static final ModelLayerLocation DEER_ANTLERS = new ModelLayerLocation(Util.getResource("deer_antlers"), "main");

    public DeerAntlersModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new DeerAntlersModel(ctx.bakeLayer(DEER_ANTLERS));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DEER_ANTLERS, DeerAntlersModel::createLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.DEER_ANTLERS;
    }
    
}
