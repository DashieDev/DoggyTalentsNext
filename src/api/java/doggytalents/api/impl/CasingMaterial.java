package doggytalents.api.impl;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.ICasingMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class CasingMaterial extends ICasingMaterial {

    private final Supplier<Block> block;
    protected ResourceLocation texture;

    @Nullable
    private String translationKey;

    public CasingMaterial(Supplier<Block> blockIn) {
        this.block = blockIn;
    }

    public CasingMaterial(Supplier<Block> blockIn, ResourceLocation texture) {
        this.block = blockIn;
        this.texture = texture;
    }

    /**
     * Texture location that for material, eg 'minecraft:block/oak_planks'
     */
    @Override
    public ResourceLocation getTexture() {
        if (this.texture == null) {
            ResourceLocation loc = ForgeRegistries.BLOCKS.getKey(this.block.get());
            this.texture = new ResourceLocation(loc.getNamespace(), "block/" + loc.getPath());
        }

        return this.texture;
    }

    /**
     * The translation key using for the tooltip
     */
    @Override
    public Component getTooltip() {
        var block = this.block.get();
        if (block == null) {
            return Component.empty();
        }
        return Component.translatable(block.asItem().getDescriptionId()).withStyle(
            Style.EMPTY.withItalic(true)
                .withColor(block.defaultMapColor().col)
        );
    }

    /**
     * The ingredient used in the crafting recipe of the bed
     */
    @Override
    public Ingredient getIngredient() {
        return Ingredient.of(this.block.get());
    }
}
