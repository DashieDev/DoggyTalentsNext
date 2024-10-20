package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.dog.kusa.LocatorOrbModel;
import doggytalents.common.lib.Resources;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class DyableLocatorOrbEntry extends DoubleDyableRenderEntry {
    
    public LocatorOrbModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new LocatorOrbModel(ctx.bakeLayer(LocatorOrbRenderEntry.LOCATOR_ORB));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
    }

    @Override
    protected ResourceLocation getFgResource(AccessoryInstance inst) {
        return Resources.DYABLE_ORB_FG;
    }

    @Override
    protected ResourceLocation getBgResource(AccessoryInstance inst) {
        return Resources.DYABLE_ORB_BG;
    }

}
