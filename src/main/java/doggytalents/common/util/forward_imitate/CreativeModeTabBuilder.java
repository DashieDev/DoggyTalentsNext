package doggytalents.common.util.forward_imitate;

import java.util.function.Supplier;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeModeTabBuilder {
    
    public static CreativeModeTabBuilder builder() {
        return new CreativeModeTabBuilder();
    }

    
    private Component title = Component.empty();
    private Supplier<ItemStack> icon = () -> ItemStack.EMPTY;

    private CreativeModeTabBuilder() {}

    public CreativeModeTabBuilder title(Component val) {
        this.title = val;
        return this;
    }

    public CreativeModeTabBuilder icon(Supplier<ItemStack> val) {
        this.icon = val;
        return this;
    }

    public CreativeModeTabBuilder withTabsBefore(CreativeModeTab tab) {
        return this;
    }

    public CreativeModeTab build() {
        final var captured_icon = this.icon;
        final var captured_title = this.title;
        return new CreativeModeTab("") {
            @Override
            public ItemStack makeIcon() {
                return captured_icon.get();
            }
            @Override
            public Component getDisplayName() {
                return captured_title;
            }
        };
    }
}
