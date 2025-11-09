package doggytalents.client.backward_imitate;

import java.util.Arrays;
import java.util.Set;

import javax.annotation.Nullable;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.block.model.DogBedItemOverride;
import doggytalents.client.block.model.DogBedModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.SingleVariant;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DogBedItemModelRendererr_1_21_5 implements SpecialModelRenderer<ItemStack> {

    private final DogBedModel dogBedModel;
    private final DogBedItemOverride dogBedResolver = new DogBedItemOverride();
    
    public DogBedItemModelRendererr_1_21_5(DogBedModel dogBedModel) {
        this.dogBedModel = dogBedModel;
    }

    @Override
    public void submit(ItemStack itemStack, ItemDisplayContext context, PoseStack stack,
            SubmitNodeCollector collector_1_21_9, int light, int overlay, boolean p_387642_, int outlineColor_1_21_9) {
        if (itemStack == null)  
            return;
        var model_part = dogBedResolver.resolve(dogBedModel, itemStack, null, null, light);
        var model = new SingleVariant(model_part);
        collector_1_21_9.submitBlockModel(stack, ItemBlockRenderTypes.getRenderType(itemStack), model, 0, 0, 0, light, overlay, outlineColor_1_21_9);
    }

    @Override
    @Nullable
    public ItemStack extractArgument(ItemStack itemStack) {
        return itemStack;
    }


    //1.21.7+
    @Override
    public void getExtents(Set<Vector3f> extents) {
        extents.addAll(Arrays.asList(DefaultExtent_1_21_7.EXTENTS));
    }
    
}
