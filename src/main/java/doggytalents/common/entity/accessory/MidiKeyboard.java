package doggytalents.common.entity.accessory;

import java.util.Properties;
import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import doggytalents.common.item.DyeableAccessoryItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class MidiKeyboard extends DyeableAccessory implements IAccessoryHasModel{
    public MidiKeyboard(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.UPPER_BODY, itemIn);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.MIDI_KEYBOARD;
    }
    
    public static class Item extends DyeableAccessoryItem {

        public Item(Supplier<? extends DyeableAccessory> accessoryIn, Properties properties) {
            super(accessoryIn, properties);
        }

        @Override
        public int getDefaultColor(ItemStack stack) {
            //return 0xffffffff;
            return 0xff262625;
        }
    }
}
