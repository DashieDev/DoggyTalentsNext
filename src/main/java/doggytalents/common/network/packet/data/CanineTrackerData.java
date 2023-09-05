package doggytalents.common.network.packet.data;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import doggytalents.common.storage.DogLocationData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ClipContext.Block;

public class CanineTrackerData {
    public static class RequestDogsData {
    }

    public static class ResponseDogsData {
        public List<Triple<UUID, String, BlockPos>> entries;
        public ResponseDogsData(List<Triple<UUID, String, BlockPos>> entries) {
            this.entries = entries;
        }
    }

    public static class StartLocatingData {
        public UUID uuid;
        public StartLocatingData(UUID uuid) {
            this.uuid = uuid;
        }
    }

    public static class RequestPosUpdateData {
        public UUID uuid;
        public BlockPos pos;
        public RequestPosUpdateData(UUID id, BlockPos pos) {
            this.uuid = id;
            this.pos = pos;
        }
    }

    public static class ResponsePosUpdateData {
        public UUID uuid;
        public BlockPos correctPos;
        public ResponsePosUpdateData(UUID id, BlockPos pos) {
            this.uuid = id;
            this.correctPos = pos;
        }
    }
}
