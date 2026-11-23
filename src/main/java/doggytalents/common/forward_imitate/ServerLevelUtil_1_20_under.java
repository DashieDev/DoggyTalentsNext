package doggytalents.common.forward_imitate;

import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class ServerLevelUtil_1_20_under {

    public static record SavedData_Factory<T>(
        Supplier<T> newCreator,
        Function<CompoundTag, T> fromSaved
    ) {}

    public static record DimensionDataStorageWrapper_1_20_under(DimensionDataStorage storage) {
        

        public <T extends SavedData> T computeIfAbsent(SavedData_Factory<T> factory, String id) {
            return this.storage().computeIfAbsent(factory.fromSaved(), factory.newCreator(), id);
        }

        public <T extends SavedData> T get(SavedData_Factory<T> factory, String id) {
            return this.storage().get(factory.fromSaved(), id);
        }
    }

    public static DimensionDataStorageWrapper_1_20_under wrapStorage(DimensionDataStorage storage) {
        return new DimensionDataStorageWrapper_1_20_under(storage);
    }

}
