package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.BakerHatModel;
import doggytalents.client.entity.model.ChefHatModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class ChefHatRenderEntry extends AccessoryModelManager.Entry {
    public static final ModelLayerLocation DOG_CHEF = new ModelLayerLocation(Util.getResource("dog_chef"), "main");
    
    public ChefHatModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new ChefHatModel(ctx.bakeLayer(DOG_CHEF));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_CHEF, ChefHatModel::createBodyLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.CHEF_HAT;
    }

    @Override
    public boolean isDyable() {
        return true;
    }
    
}
