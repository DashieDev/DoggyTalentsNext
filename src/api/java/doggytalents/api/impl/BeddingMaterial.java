package doggytalents.api.impl;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.api.registry.IBeddingMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;

public class BeddingMaterial extends IBeddingMaterial {

    private final Supplier<Block> block;
    protected ResourceLocation texture;

    @Nullable
    private String translationKey;

    public BeddingMaterial(Supplier<Block> blockIn) {
        this.block = blockIn;
    }

    public BeddingMaterial(Supplier<Block> blockIn, ResourceLocation texture) {
        this.block = blockIn;
        this.texture = texture;
    }

    /**
     * Texture location that for material, eg 'minecraft:block/white_wool'
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
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("dogbed.bedding", DoggyTalentsAPI.BEDDING_MATERIAL.get().getKey(this));
        }

        return ComponentUtil.translatable(this.translationKey);
    }

    /**
     * The ingredient used in the crafting recipe of the bed
     */
    @Override
    public Ingredient getIngredient() {
        return Ingredient.of(this.block.get());
    }
}
