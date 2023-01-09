package doggytalents.common.network.packet.data;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;

import doggytalents.common.storage.DogLocationData;

public class ConductingBoneData {
    public static class RequestDogsData {
    }

    public static class ResponseDogsData {
        public List<Pair<UUID, String>> entries;
        public ResponseDogsData(List<Pair<UUID, String>> entries) {
            this.entries = entries;
        }
    }

    public static class RequestDistantTeleportDogData {
        public UUID dogUUID;
        public boolean toBed;
        public RequestDistantTeleportDogData(UUID dogUUID, boolean toBed) {
            this.dogUUID = dogUUID;
            this.toBed = toBed;
        }
    }

}
