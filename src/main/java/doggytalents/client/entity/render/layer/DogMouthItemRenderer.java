package doggytalents.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import doggytalents.DoggyTalents;
import doggytalents.api.inferface.IThrowableItem;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.SyncedItemModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DogRenderer;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;

public class DogMouthItemRenderer extends RenderLayer<Dog, DogModel> {
    
    private ItemInHandRenderer itemInHandRenderer;
    private SyncedItemModel itemSyncer;

    public DogMouthItemRenderer(RenderLayerParent dogRendererIn, EntityRendererProvider.Context ctx) {
        super(dogRendererIn);
        this.itemInHandRenderer = Minecraft.getInstance().getItemInHandRenderer();
        itemSyncer = new SyncedItemModel(ctx.bakeLayer(ClientSetup.DOG_MOUTH_ITEM), itemInHandRenderer);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource bufferSource, int packedLight, Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!ConfigHandler.CLIENT.MOUTH_ITEM_FORCE_RENDER.get()) {
            var skin = dog.getClientSkin();
            if (skin.useCustomModel()) {
                var model = skin.getCustomModel().getValue();
                if (!model.armorShouldRender(dog))
                    return;
            }
        }
        
        var stackOptional = dog.getMouthItemForRender();
        if (!stackOptional.isPresent())
            return;
        var stack = stackOptional.get();

        var model = this.getParentModel();

        model.copyPropertiesTo(itemSyncer);
        itemSyncer.sync(model);
        itemSyncer.startRenderFromRoot(matrixStack, bufferSource, packedLight, dog, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
    }
}
