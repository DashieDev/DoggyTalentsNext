package doggytalents.api.registry;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.Identifier;

import java.util.Optional;

import net.minecraft.network.chat.Component;

public abstract class IBeddingMaterial implements IDogBedMaterial {
    
    @Override
    public boolean isNani() {
        return false;
    }
    
}
