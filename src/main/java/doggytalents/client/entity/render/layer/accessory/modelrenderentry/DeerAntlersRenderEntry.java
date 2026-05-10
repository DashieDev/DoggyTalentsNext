package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.accessories.WigModel;
import doggytalents.client.entity.model.dog.dogs.kusa.DeerAntlersModel;
import doggytalents.client.entity.model.dog.dogs.kusa.LocatorOrbModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

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
    public Identifier getResources(AccessoryInstance inst) {
        return Resources.DEER_ANTLERS;
    }
    
}
