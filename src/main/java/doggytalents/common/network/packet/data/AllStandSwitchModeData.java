package doggytalents.common.network.packet.data;

import doggytalents.api.feature.DogMode;

public class AllStandSwitchModeData {
    
    public final DogMode mode;

    public AllStandSwitchModeData(DogMode mode) {
        this.mode = mode == null ? DogMode.DOCILE : mode;
    }

}
