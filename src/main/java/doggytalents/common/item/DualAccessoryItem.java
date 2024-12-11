package doggytalents.common.item;

import java.util.List;
import java.util.function.Supplier;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

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
    public void appendHoverText(ItemStack p_41421_, Level p_339594_, List<Component> list,
            TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_339594_, list, p_41424_);
        list.add(ComponentUtil.translatable("general.doggytalents.dual_accessories_item_help"));
    }
}
