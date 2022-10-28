package doggytalents.api.registry;

import javax.annotation.Nullable;

import doggytalents.api.DoggyTalentsAPI;
import net.minecraft.Util;

public class AccessoryType {

    @Nullable
    private String translationKey;

    public int numberToPutOn() {
        return 1;
    }

    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("accessory_type", DoggyTalentsAPI.ACCESSORY_TYPE.get().getKey(this));
        }
        return this.translationKey;
    }
}
