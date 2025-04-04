package doggytalents.client.backward_imitate;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DogItemRender_1_21_5 {
    
    public static void render(ItemRenderer renderer, ItemStack item_stack, ItemDisplayContext context, boolean a, PoseStack stack, MultiBufferSource buffer, int light, int overlay) {
        renderer.renderStatic(item_stack, context, overlay, light, stack, buffer, null, overlay);
    }

}
