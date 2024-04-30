package doggytalents.common.util;

import doggytalents.common.lib.Constants;
import doggytalents.common.storage.DogLocationStorage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class DogLocationStorageMigration {

    public static void checkAndMigrate(ServerLevel level) {
        var savedData = level.getDataStorage();

        DogLocationStorage newStorage = null;
        try {
            newStorage = savedData.get(DogLocationStorage.storageFactory(), Constants.STORAGE_DOG_LOCATION);
        } catch (Exception e) {}
        if (newStorage != null)
            return;

        DogLocationStorage oldStorage = null;
        try {
            oldStorage = savedData.get(DogLocationStorage.storageFactory(), Constants.STORAGE_DOG_LOCATION_OLD);
        } catch (Exception e) {}
        if (oldStorage == null)
            return;
        if (oldStorage.getAll().isEmpty())
            return;
            
        savedData.set(Constants.STORAGE_DOG_LOCATION, oldStorage);
        oldStorage.setDirty();

        var dummy = new DogLocationStorage();
        savedData.set(Constants.STORAGE_DOG_LOCATION_OLD, dummy);
        dummy.setDirty();
    }

}
