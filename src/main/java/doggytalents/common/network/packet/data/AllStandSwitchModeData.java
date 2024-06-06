package doggytalents.common.network.packet.data;

import doggytalents.api.feature.EnumMode;

public class AllStandSwitchModeData {
    
    public final EnumMode mode;

    public AllStandSwitchModeData(EnumMode mode) {
        this.mode = mode == null ? EnumMode.DOCILE : mode;
    }

}
