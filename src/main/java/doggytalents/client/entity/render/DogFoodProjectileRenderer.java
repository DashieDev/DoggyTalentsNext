package doggytalents.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.client.backward_imitate.EntityRenderer_21_3;
import doggytalents.common.entity.misc.DogFoodProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DogFoodProjectileRenderer extends EntityRenderer_21_3<DogFoodProjectile> {

    private ItemRenderer itemRenderer;
    private ItemStack placeholder;

    public DogFoodProjectileRenderer(Context ctx) {
        super(ctx);
        itemRenderer = Minecraft.getInstance().getItemRenderer();
        placeholder = new ItemStack(Items.SNOWBALL);
    }

    @Override
    public void render(DogFoodProjectile dogFood, float yRot, float pTick, PoseStack stack,
            MultiBufferSource buffer, int light) {
        stack.pushPose();
        stack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        stack.mulPose(Axis.YP.rotationDegrees(180.0F));
        var foodStack = dogFood.getDogFoodStack();
        if (foodStack.isEmpty()) {
            foodStack = placeholder;
        }
        this.itemRenderer.renderStatic(foodStack, 
            ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, 
            stack, buffer, null, dogFood.getId());
        stack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(DogFoodProjectile proj) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
    
}
