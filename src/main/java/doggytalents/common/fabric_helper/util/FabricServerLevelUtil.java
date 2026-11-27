package doggytalents.common.fabric_helper.util;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class FabricServerLevelUtil {
    
    public static record FabricSavedData_Factory<T extends  SavedData>(
        Supplier<T> newCreator,
        BiFunction<CompoundTag, HolderLookup.Provider, T> fromSaved
    ) {
        public SavedData.Factory<T> unwrapFabric() {
            return new SavedData.Factory<T>(this.newCreator(), this.fromSaved(), null);
        }
    }

}
