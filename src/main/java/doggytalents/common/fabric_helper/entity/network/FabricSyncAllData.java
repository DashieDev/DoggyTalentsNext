package doggytalents.common.fabric_helper.entity.network;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

import doggytalents.common.entity.Dog;
import doggytalents.common.fabric_helper.entity.DogFabricHelper;
import doggytalents.common.fabric_helper.entity.network.SyncTypes.SyncType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class FabricSyncAllData {
    
    public final int dogId;
    private Map<SyncType<?>, Object> dataMap;

    public FabricSyncAllData(int dogId) {
        this.dogId = dogId;
        this.dataMap = Maps.newHashMap();
    }

    private FabricSyncAllData(int dogId, Map<SyncType<?>, Object> datMap) {
        this.dogId = dogId;
        this.dataMap = datMap;
    }

    public <T> T getVal(SyncType<T> type) {
        return (T) this.dataMap.get(type);
    }

    public <T> void putVal(SyncType<T> type, DogFabricHelper helper) {
        this.dataMap.put(type, type.getGetter().apply(helper));
    }

    public void updateDog(Dog dog) {
        var fabric_helper = dog.getDogFabricHelper();
        for (var entry : dataMap.entrySet()) {
            var type = entry.getKey();
            getValAndSetToDog(type, fabric_helper);
        }
    }

    public <T> void getValAndSetToDog(SyncType<T> type, DogFabricHelper helper) {
        T val = getVal(type);
        var setter = type.getClientSetter();
        setter.accept(helper, val);
    }

    public <T> void getValAndWrite(SyncType<T> type, FriendlyByteBuf buf) {
        T val = getVal(type);
        var serializer = type.getSerializer();
        serializer.codec().encode((RegistryFriendlyByteBuf) buf, val);
    }

    public void writeToPacket(FriendlyByteBuf buf) {
        buf.writeInt(dogId);
        buf.writeInt(this.dataMap.size());
        for (var entry : this.dataMap.entrySet()) {
            var type = entry.getKey();
            buf.writeInt(type.getId());
            getValAndWrite(type, buf);
        }
    }

    public static FabricSyncAllData readFromPacket(FriendlyByteBuf buf) {
        int dogId = buf.readInt();
        int mapSize = buf.readInt();
        Map<SyncType<?>, Object> dataMap = new HashMap<>(mapSize);
        for (int i = 0; i < mapSize; ++i) {
            var typeId = buf.readInt();
            var type = SyncTypes.fromId(typeId);
            var val = readValFromPacket(type, buf);
            dataMap.put(type, val);
        }
        return new FabricSyncAllData(dogId, dataMap);
    }

    private static <T> T readValFromPacket(SyncType<T> syncType, FriendlyByteBuf buf) {
        var val = syncType.getSerializer().codec().decode((RegistryFriendlyByteBuf)buf);
        return val;
    }

}
