package doggytalents.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import doggytalents.common.entity.misc.DogFoodProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DogFoodProjectileRenderer extends EntityRenderer<DogFoodProjectile> {

    private ItemRenderer itemRenderer;
    private ItemStack placeholder;

    public DogFoodProjectileRenderer(Context ctx) {
        super(ctx);
        itemRenderer = ctx.getItemRenderer();
        placeholder = new ItemStack(Items.SNOWBALL);
    }

    @Override
    public void render(DogFoodProjectile dogFood, float yRot, float pTick, PoseStack stack,
            MultiBufferSource buffer, int light) {
        stack.pushPose();
        stack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        stack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        var foodStack = dogFood.getDogFoodStack();
        if (foodStack.isEmpty()) {
            foodStack = placeholder;
        }
        this.itemRenderer.renderStatic(foodStack, 
            ItemTransforms.TransformType.GROUND, light, OverlayTexture.NO_OVERLAY, 
            stack, buffer, dogFood.getId());
        stack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(DogFoodProjectile proj) {
        return InventoryMenu.BLOCK_ATLAS;
    }
    
}
