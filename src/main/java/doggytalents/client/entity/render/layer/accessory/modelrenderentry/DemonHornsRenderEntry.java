package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.DemonHornsModel;
import doggytalents.client.entity.model.HotDogModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class DemonHornsRenderEntry extends AccessoryModelManager.Entry{
    public static final ModelLayerLocation DEMON_HORNS = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "demon_horns"), "main");

    private DemonHornsModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new DemonHornsModel(ctx.bakeLayer(DEMON_HORNS));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DEMON_HORNS, DemonHornsModel::createDemonHornsLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.DEMON_HORNS;    
    }
    
}
