package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.DemonHornsModel;
import doggytalents.client.entity.model.SnorkelModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.entity.accessory.DemonHornsAccessory;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class DemonHornsRenderEntry extends AccessoryModelManager.Entry{
    public static final ModelLayerLocation DOG_DEMON_HORNS = new ModelLayerLocation(Util.getResource("dog_demon_horns"), "main");
    
    public DemonHornsModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new DemonHornsModel(ctx.bakeLayer(DOG_DEMON_HORNS));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_DEMON_HORNS, DemonHornsModel::createBodyLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.DEMON_HORNS;
    }
    
}
