package doggytalents.client.backward_imitate;

import org.joml.Vector3fc;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelBaker.PartCache;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class DogBedUtil_1_21_11 {
    
    private static final PartCache PART_CACHE_IMPL = new PartCache() {
        private final Interner<Vector3fc> vectors = Interners.newStrongInterner();
        private final Interner<net.neoforged.neoforge.client.model.quad.BakedNormals> normals = Interners.newStrongInterner();
        private final Interner<net.neoforged.neoforge.client.model.quad.BakedColors> colors = Interners.newStrongInterner();

        @Override
        public Vector3fc vector(Vector3fc p_470814_) {
            return this.vectors.intern(p_470814_);
        }

        @Override
        public net.neoforged.neoforge.client.model.quad.BakedNormals normals(net.neoforged.neoforge.client.model.quad.BakedNormals normals) {
            return this.normals.intern(normals);
        }

        @Override
        public net.neoforged.neoforge.client.model.quad.BakedColors colors(net.neoforged.neoforge.client.model.quad.BakedColors colors) {
            return this.colors.intern(colors);
        }
    };  

    public static PartCache partCacheImpl() {
        return PART_CACHE_IMPL;
    }

    public static RenderType getItemBlockRenderType(ItemStack stack) {
        var item = stack.getItem();
        if (!(item instanceof BlockItem block_item))
            throw new IllegalArgumentException("Not a block item");
        var block = block_item.getBlock();
        return ItemBlockRenderTypes.getRenderType(block.defaultBlockState());
    }

}
