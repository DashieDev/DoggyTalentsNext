package doggytalents.common.network.packet.data;

import java.util.UUID;

import net.minecraft.util.Util;

public class DogMigrateOwnerData extends DogData {

    public boolean confirmed;

    public DogMigrateOwnerData(int entityId, boolean confirmed) {
        super(entityId);
        this.confirmed = confirmed;
    }

    

}
