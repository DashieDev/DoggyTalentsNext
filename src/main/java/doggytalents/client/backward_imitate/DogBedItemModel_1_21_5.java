package doggytalents.client.backward_imitate;

import javax.annotation.Nullable;

import org.joml.Vector3f;

import doggytalents.client.block.model.DogBedItemOverride;
import doggytalents.client.block.model.DogBedModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.renderer.item.SpecialModelWrapper;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DogBedItemModel_1_21_5 extends SpecialModelWrapper<ItemStack> {

    public DogBedItemModel_1_21_5(DogBedModel dogBedModel, ModelRenderProperties props) {
        super(new DogBedItemModelRendererr_1_21_5(dogBedModel), props);
    }
    
}
