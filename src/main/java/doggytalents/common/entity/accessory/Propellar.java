package doggytalents.common.entity.accessory;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import doggytalents.common.item.AccessoryItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class Propellar extends Accessory implements IAccessoryHasModel {

    public Propellar(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.HEAD, itemIn);
    }

    @Override
    public byte getRenderLayer() {
        return AccessoryInstance.RENDER_TOP;
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.PROPELLAR;
    }

    public static class PropellerHatItem extends AccessoryItem {

        public PropellerHatItem(Supplier<? extends Accessory> type, Properties properties) {
            super(type, properties);
        }
        
        @Override
        public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components,
                TooltipFlag flags) {
            var desc_id = this.getDescriptionId(stack) + ".description";
            components.add(Component.translatable(desc_id).withStyle(
                Style.EMPTY.withItalic(true)
            ));
        }

    }

}
