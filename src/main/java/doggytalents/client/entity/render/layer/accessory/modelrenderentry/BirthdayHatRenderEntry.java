package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.BirthdayHatModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.WigModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.client.entity.render.layer.accessory.DefaultAccessoryRenderer;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.accessory.BirthdayHatAccessory;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class BirthdayHatRenderEntry extends DoubleDyeableRenderEntry {
    public static final ModelLayerLocation DOG_BIRTHDAY_HAT = new ModelLayerLocation(Util.getResource("dog_birthday_hat"), "main");
    
    public BirthdayHatModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new BirthdayHatModel(ctx.bakeLayer(DOG_BIRTHDAY_HAT));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_BIRTHDAY_HAT, BirthdayHatModel::createBodyLayer);
    }

    @Override
    protected ResourceLocation getFgResource(AccessoryInstance inst) {
        return Resources.BIRTHDAY_HAT_FG;
    }

    @Override
    protected ResourceLocation getBgResource(AccessoryInstance inst) {
        return Resources.BIRTHDAY_HAT_BG;
    }
    
}
