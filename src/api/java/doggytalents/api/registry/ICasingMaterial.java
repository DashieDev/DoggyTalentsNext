package doggytalents.api.registry;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

import net.minecraft.network.chat.Component;

public abstract class ICasingMaterial implements IDogBedMaterial {

    @Override
    public boolean isNani() {
        return false;
    }
    
}
