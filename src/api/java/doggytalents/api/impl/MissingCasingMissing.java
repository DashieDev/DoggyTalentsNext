package doggytalents.api.impl;

import cpw.mods.modlauncher.api.ITransformationService.Resource;
import doggytalents.api.registry.ICasingMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
public class MissingCasingMissing extends ICasingMaterial {

    public static final ICasingMaterial NULL = new MissingCasingMissing();
    private static final ResourceLocation MISSING_TEXTURE = new ResourceLocation("missingno");

    private ResourceLocation registryName;

    @Override
    public ResourceLocation getTexture() {
        return MissingCasingMissing.MISSING_TEXTURE;
    }

    @Override
    public Component getTooltip() {
        return Component.translatable("dogbed.casing.missing", this.registryName.getPath());
    }

    public MissingCasingMissing setRegistryName(ResourceLocation r) {
        this.registryName = r;
        return this;
    }

    @Override
    public Ingredient getIngredient() {
        return Ingredient.EMPTY;
    }
}
