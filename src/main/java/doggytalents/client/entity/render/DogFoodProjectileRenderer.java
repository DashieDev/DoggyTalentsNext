package doggytalents.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.client.backward_imitate.EntityRenderer_1_21_9;
import doggytalents.client.backward_imitate.EntityRenderer_21_3;
import doggytalents.common.entity.misc.DogFoodProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DogFoodProjectileRenderer extends EntityRenderer_1_21_9<DogFoodProjectile> {

    private ItemRenderer itemRenderer;
    private ItemStack placeholder;

    public DogFoodProjectileRenderer(Context ctx) {
        super(ctx);
        itemRenderer = Minecraft.getInstance().getItemRenderer();
        placeholder = new ItemStack(Items.SNOWBALL);
    }

    @Override
    public void submit(DogFoodProjectile dogFood, float yRot, float pTick, PoseStack stack,
            RenderContext_1_21_9<DogFoodProjectile> context_1_21_9, int light) {
        stack.pushPose();
        stack.mulPose(context_1_21_9.cameraState().orientation);
        stack.mulPose(Axis.YP.rotationDegrees(180.0F));
        var foodStack = dogFood.getDogFoodStack();
        if (foodStack.isEmpty()) {
            foodStack = placeholder;
        }
        this.submitItemStack(foodStack, 
            ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, 
            stack, context_1_21_9);
        stack.popPose();
    }

    @Override
    public Identifier getTextureLocation(DogFoodProjectile proj) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
    
}
