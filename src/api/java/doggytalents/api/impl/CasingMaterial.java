package doggytalents.api.impl;

import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.ICasingMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class CasingMaterial extends ICasingMaterial {

    private final Supplier<Block> block;
    protected ResourceLocation texture;
    protected final ResourceLocation id;

    @Nullable
    private String translationKey;

    public CasingMaterial(ResourceLocation id, Supplier<Block> blockIn) {
        this.block = blockIn;
        this.id = id;
    }

    public CasingMaterial(ResourceLocation id, Supplier<Block> blockIn, ResourceLocation texture) {
        this.block = blockIn;
        this.texture = texture;
        this.id = id;
    }

    /**
     * Texture location that for material, eg 'minecraft:block/oak_planks'
     */
    @Override
    public ResourceLocation getTexture() {
        if (this.texture == null) {
            ResourceLocation loc = BuiltInRegistries.BLOCK.getKey(this.block.get());
            this.texture = ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), "block/" + loc.getPath());
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
    public Optional<Ingredient> getIngredient() {
        var block = this.block.get();
        if (block == null || block == Blocks.AIR) {
            return Optional.empty();
        }
        return Optional.of(Ingredient.of(block));
    }

    @Override
    public ResourceLocation getSaveKey() {
        return this.id;
    }
}
