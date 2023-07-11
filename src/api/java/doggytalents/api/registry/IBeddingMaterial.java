package doggytalents.api.registry;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraft.resources.ResourceLocation;
import doggytalents.api.enu.forward_imitate.ComponentUtil;
import net.minecraft.network.chat.Component;

public abstract class IBeddingMaterial extends ForgeRegistryEntry<IBeddingMaterial> {

    /**
     * Texture location that for material, eg 'minecraft:block/white_wool'
     */
    public abstract ResourceLocation getTexture();

    /**
     * The translation key using for the tooltip
     */
    public abstract Component getTooltip();

    /**
     * The ingredient used in the crafting recipe of the bed
     */
    public abstract Ingredient getIngredient();
}
