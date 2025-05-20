package doggytalents.api.registry;

import java.util.Optional;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public interface IDogBedMaterial {
    
    /**
     * Texture location that for material, eg 'minecraft:block/white_wool'
     */
    ResourceLocation getTexture();

    /**
     * The translation key using for the tooltip
     */
    Component getTooltip();

    /**
     * The ingredient used in the crafting recipe of the bed
     */
    Optional<Ingredient> getIngredient();

    ResourceLocation getSaveKey();

    boolean isNani();

}
