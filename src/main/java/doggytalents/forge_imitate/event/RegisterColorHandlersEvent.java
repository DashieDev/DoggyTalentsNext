package doggytalents.forge_imitate.event;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;

public class RegisterColorHandlersEvent {
    
    public static class Block extends Event {

        private final RegisterCallback<BlockColor, net.minecraft.world.level.block.Block> callback = (x, y) -> {
            ColorProviderRegistry.BLOCK.register(x, y);  
        };

        public RegisterCallback<BlockColor, net.minecraft.world.level.block.Block> getBlockColors() {
            return this.callback;
        }

    }

    public static class Item extends Event {

        private final RegisterCallback<ItemColor, net.minecraft.world.item.Item> callback = (x, y) -> {
            ColorProviderRegistry.ITEM.register(x, y);  
        };

        public RegisterCallback<ItemColor, net.minecraft.world.item.Item> getItemColors() {
            return this.callback;
        }

        public void register(ItemColor color, net.minecraft.world.item.Item item) {
            this.callback.register(color, item);
        }

    }

    public static interface RegisterCallback<X, Y> {

        void register(X x, Y y);

    }

}
