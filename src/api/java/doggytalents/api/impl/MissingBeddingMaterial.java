package doggytalents.api.impl;

import doggytalents.api.registry.IBeddingMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import doggytalents.common.forward_imitate.ComponentUtil;

public class MissingBeddingMaterial extends IBeddingMaterial {

    public static final IBeddingMaterial NULL = new MissingBeddingMaterial();
    private static final ResourceLocation MISSING_TEXTURE = new ResourceLocation("missingno");

    @Override
    public ResourceLocation getTexture() {
        return MissingBeddingMaterial.MISSING_TEXTURE;
    }

    @Override
    public Component getTooltip() {
        return ComponentUtil.translatable("dogbed.casing.missing");
    }

    @Override
    public Ingredient getIngredient() {
        return Ingredient.EMPTY;
    }
}
