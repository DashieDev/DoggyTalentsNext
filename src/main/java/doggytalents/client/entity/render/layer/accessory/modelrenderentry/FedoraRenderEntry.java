package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.FedoraModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class FedoraRenderEntry extends Entry {
    
    public static final ModelLayerLocation DOG_FEDORA = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_fedora"), "main");
    
    public FedoraModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new FedoraModel(ctx.bakeLayer(DOG_FEDORA));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_FEDORA, FedoraModel::createLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.DOG_FEDORA;
    }

}
