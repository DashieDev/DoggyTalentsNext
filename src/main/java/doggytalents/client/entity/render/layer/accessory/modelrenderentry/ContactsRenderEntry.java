package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.BowTieModel;
import doggytalents.client.entity.model.ContactsModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class ContactsRenderEntry extends DoubleDyableRenderEntry {
    public static final ModelLayerLocation DOG_CONTACTS = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_contacts"), "main");

    private ContactsModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new ContactsModel(ctx.bakeLayer(DOG_CONTACTS));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_CONTACTS, ContactsModel::createBodyLayer);
    }

    @Override
    protected ResourceLocation getFgResource(AccessoryInstance inst) {
        return Resources.DOGGY_CONTACTS_FG;
    }

    @Override
    protected ResourceLocation getBgResource(AccessoryInstance inst) {
        return Resources.DOGGY_CONTACTS_BG;
    }
}
