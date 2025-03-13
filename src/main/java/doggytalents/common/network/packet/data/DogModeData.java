package doggytalents.common.network.packet.data;

import doggytalents.api.feature.DogMode;

public class DogModeData extends DogData {

    public DogMode mode;

    public DogModeData(int entityId, DogMode modeIn) {
        super(entityId);
        this.mode = modeIn == null ? DogMode.DOCILE : modeIn;
    }
}
