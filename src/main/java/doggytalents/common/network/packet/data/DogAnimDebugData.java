package doggytalents.common.network.packet.data;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.item.DogAnimDebugItem;
import doggytalents.common.item.DogAnimDebugItem.ItemMode;

public class DogAnimDebugData {

    public static class UpdateItemSettingsData {

        public final DogAnimation selected;
        public final ItemMode mode;

        public UpdateItemSettingsData(DogAnimation selected, ItemMode mode) {
            this.selected = selected == null ? DogAnimation.NONE: selected;
            this.mode = mode == null ? ItemMode.ANIM: mode;
        }

    }

}
