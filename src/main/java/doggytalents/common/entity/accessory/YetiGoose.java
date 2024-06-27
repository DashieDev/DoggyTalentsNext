package doggytalents.common.entity.accessory;

import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.api.registry.AccessoryType;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import doggytalents.common.item.AccessoryItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class YetiGoose extends Accessory implements IAccessoryHasModel{


    public YetiGoose(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.BODY, itemIn);
    }

    @Override
    public byte getRenderLayer() {
        return AccessoryInstance.RENDER_TOP;
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.YETI_GOOSE;
    }

    public static class YetiGooseItem extends AccessoryItem {

        public YetiGooseItem(Supplier<? extends Accessory> type, Properties properties) {
            super(type, properties);
        }
        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> components,
                TooltipFlag flags) {
            var desc_id = this.getDescriptionId(stack) + ".description";
            components.add(Component.translatable(desc_id).withStyle(
                Style.EMPTY.withItalic(true)
            ));
        }
    }

}
