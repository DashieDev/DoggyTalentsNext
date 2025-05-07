package doggytalents.client.backward_imitate.fabric_util;

import com.mojang.serialization.MapCodec;

import doggytalents.forge_imitate.event.Event;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.resources.ResourceLocation;

public class RegisterColorHandlersEvent_1_21_5 {
    
    public static class ItemTintSources extends Event {
        
        public ItemTintSources() {
        }

        public void register(ResourceLocation location, MapCodec<? extends ItemTintSource> source) {
            net.minecraft.client.color.item.ItemTintSources.ID_MAPPER.put(location, source);
        }
    }

}
