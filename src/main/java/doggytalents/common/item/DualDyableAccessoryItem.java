package doggytalents.common.item;

import java.util.List;
import java.util.function.Supplier;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.entity.accessory.DyeableAccessory;
import doggytalents.common.util.ItemUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class DualDyableAccessoryItem extends DyeableAccessoryItem {

    public Supplier<? extends DyeableAccessory> secondary;

    public DualDyableAccessoryItem(Supplier<? extends DyeableAccessory> primary,
        Supplier<? extends DyeableAccessory> secondary, Properties properties) {
        super(primary, properties);
        this.secondary = secondary;
    }

    @Override
    public AccessoryInstance createInstance(AbstractDog dogIn, ItemStack stack, Player playerIn) {
        if (playerIn.isShiftKeyDown()) {
            return this.secondary.get().create(ItemUtil.getDyeColorForStack(stack));
        }
        return super.createInstance(dogIn, stack, playerIn);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, TooltipContext p_339594_, List<Component> list,
            TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_339594_, list, p_41424_);
        list.add(Component.translatable("general.doggytalents.dual_accessories_item_help"));
    }
}
