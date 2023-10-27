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
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class BirthdayHatRenderEntry extends AccessoryModelManager.Entry{
    public static final ModelLayerLocation DOG_BIRTHDAY_HAT = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_birthday_hat"), "main");
    
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
    public void renderAccessory(RenderLayer<Dog, DogModel> layer, PoseStack poseStack, MultiBufferSource buffer,
            int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
            float netHeadYaw, float headPitch, AccessoryInstance inst) {
        super.renderAccessory(layer, poseStack, buffer, packedLight, dog, limbSwing, limbSwingAmount, partialTicks, ageInTicks,
                netHeadYaw, headPitch, inst);
        if (!(inst instanceof BirthdayHatAccessory.Inst birthday))
            return;
        var fg_color = birthday.getFgColor();
        DefaultAccessoryRenderer.renderTranslucentModel(model, Resources.BIRTHDAY_HAT_FG, 
            poseStack, buffer, packedLight, dog, fg_color[0], fg_color[1], fg_color[2], 1f);
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_BIRTHDAY_HAT, BirthdayHatModel::createBodyLayer);
    }

    @Override
    public boolean isDyable() {
        return true;
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return inst.getAccessory().getModelTexture();
    }
    
}
