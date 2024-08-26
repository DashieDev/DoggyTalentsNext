package doggytalents.common.item;

import java.util.function.Supplier;

import doggytalents.common.entity.accessory.DoubleDyableAccessory;

public class StripedScarfItem extends DoubleDyableAccessoryItem {
    
    public StripedScarfItem(Supplier<? extends DoubleDyableAccessory> type, Properties properties) {
        super(type, properties);
    }
    
}