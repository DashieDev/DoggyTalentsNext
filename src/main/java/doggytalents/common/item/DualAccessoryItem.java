package doggytalents.common.item;

import java.util.List;
import java.util.function.Supplier;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class DualAccessoryItem extends AccessoryItem {

    public Supplier<? extends Accessory> secondary;

    public DualAccessoryItem(Supplier<? extends Accessory> primary,
        Supplier<? extends Accessory> secondary, Properties properties) {
        super(primary, properties);
        this.secondary = secondary;
    }

    @Override
    public AccessoryInstance createInstance(AbstractDog dogIn, ItemStack stack, Player playerIn) {
        if (playerIn.isShiftKeyDown()) {
            return this.secondary.get().getDefault();
        }
        return super.createInstance(dogIn, stack, playerIn);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, TooltipContext p_339594_, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<net.minecraft.network.chat.Component> list,
            TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_339594_, tooltipDisplay, list, p_41424_);
        list.accept(Component.translatable("general.doggytalents.dual_accessories_item_help"));
    }
}
