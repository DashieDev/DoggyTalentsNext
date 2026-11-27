package doggytalents.common.util;

import java.util.Optional;
import java.util.function.Supplier;

import doggytalents.common.fabric_helper.util.FabricServerLevelUtil;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class ServerWorldUtil {
    
    public static <T> T getOrCreateTransientLevelOwnedObject(
        ServerLevel level, 
        Supplier<T> creator,
        String id
    ) {
    
        final var factory = new FabricServerLevelUtil.FabricSavedData_Factory<TransientObjectWrapper<T>>(
            () -> new TransientObjectWrapper(creator.get()), 
            (tag, prov) -> new TransientObjectWrapper(creator.get())
        ).unwrapFabric();
        return level.getDataStorage().computeIfAbsent(factory, id).unwrap();
    }

    public static <T> Optional<T> getTransientLevelOwnedObject(
        ServerLevel level, 
        Supplier<T> creator,
        String id
    ) {
        final var factory = new FabricServerLevelUtil.FabricSavedData_Factory<TransientObjectWrapper<T>>(
            () -> new TransientObjectWrapper(creator.get()), 
            (tag, prov) -> new TransientObjectWrapper(creator.get())
        ).unwrapFabric();
        return Optional.ofNullable(level.getDataStorage().get(factory, id))
            .map(TransientObjectWrapper::unwrap);
    }

    private static class TransientObjectWrapper<T> extends SavedData {

        private T obj;
        public TransientObjectWrapper(T obj) {
            this.obj = obj;
        }
        public T unwrap() {
            return this.obj;
        }

        @Override
        public CompoundTag save(CompoundTag tag, Provider prov) {
            return tag;
        }
    }
}
