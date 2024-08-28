package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.StripedScarfModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class StripedScarfRenderEntry extends DoubleDyableRenderEntry {
    public static final ModelLayerLocation DOG_STRIPED_SCARF = new ModelLayerLocation(Util.getResource("dog_striped_scarf"), "main");
    
    public StripedScarfModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new StripedScarfModel(ctx.bakeLayer(DOG_STRIPED_SCARF));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_STRIPED_SCARF, StripedScarfModel::createBodyLayer);
    }

    @Override
    protected ResourceLocation getFgResource(AccessoryInstance inst) {
        return Resources.STRIPED_SCARF_FG;
    }

    @Override
    protected ResourceLocation getBgResource(AccessoryInstance inst) {
        return Resources.STRIPED_SCARF_BG;
    }
    
}
