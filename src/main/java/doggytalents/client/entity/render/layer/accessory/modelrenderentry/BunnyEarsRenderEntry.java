package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.BunnyEarsModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.dog.kusa.DeerAntlersModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class BunnyEarsRenderEntry extends Entry {
    
    public static final ModelLayerLocation BUNNY_EARS = new ModelLayerLocation(Util.getResource("bunny_ears"), "main");

    public BunnyEarsModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new BunnyEarsModel(ctx.bakeLayer(BUNNY_EARS));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BUNNY_EARS, BunnyEarsModel::createBodyLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.BUNNY_EARS;
    }
    
}

