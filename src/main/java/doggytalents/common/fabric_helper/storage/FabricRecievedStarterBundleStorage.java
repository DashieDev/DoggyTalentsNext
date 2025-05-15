package doggytalents.common.fabric_helper.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import doggytalents.api.backward_imitate.CompoundTag_1_21_5;
import doggytalents.api.backward_imitate.LegacyNbtCodec_1_21_5;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class FabricRecievedStarterBundleStorage extends SavedData {

    private static String SAVE_LOCATION = "doggytalents_recievedStarterBundle";

    private ArrayList<UUID> recievedBundle = new ArrayList<>();

    private FabricRecievedStarterBundleStorage() {}

    public static FabricRecievedStarterBundleStorage get(MinecraftServer server) {
        var overworld = server.getLevel(Level.OVERWORLD);

        var storage = overworld.getDataStorage();
        return storage.computeIfAbsent(savedDataType_1_21_5(SAVE_LOCATION));
    }

    private static FabricRecievedStarterBundleStorage load(CompoundTag_1_21_5 compound/*, HolderLookup.Provider prov*/) {
        var list = readRecievedBundleList(compound);
        var ret = new FabricRecievedStarterBundleStorage();
        ret.recievedBundle = list;
        return ret;
    }

    private static ArrayList<UUID> readRecievedBundleList(CompoundTag_1_21_5 compound) {
        if (!compound.contains("recievedBundle", Tag.TAG_LIST))
            return new ArrayList<>();
        var ret = new ArrayList<UUID>();
        var list = compound.getList("recievedBundle", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); ++i) {
            var c1 = list.getCompound(i);
            if (!c1.hasUUID("player_uuid"))
                continue;
            var uuid = c1.getUUID("player_uuid");
            ret.add(uuid);
        }
        return ret;
    }

    //@Override
    public CompoundTag_1_21_5 save(CompoundTag_1_21_5 compound/*, HolderLookup.Provider prov*/) {
        var listTag = new ListTag();
        for (var uuid : this.recievedBundle) {
            var c1 = CompoundTag_1_21_5.createEmpty();
            c1.putUUID("player_uuid", uuid);
            listTag.add(c1.wrapped());
        }
        compound.put("recievedBundle", listTag);
        return compound;
    }

    public boolean hasBundleAlready(ServerPlayer player) {
        var uuid = player.getUUID();
        return this.recievedBundle.contains(uuid);
    }

    public void onRecievedBundle(ServerPlayer player) {
        var uuid = player.getUUID();
        this.recievedBundle.add(uuid);
        this.setDirty();
    }

    // private static SavedData.Factory<FabricRecievedStarterBundleStorage> FACTORY
    //     = new SavedData.Factory<>(FabricRecievedStarterBundleStorage::new, FabricRecievedStarterBundleStorage::load, null);
    // public static SavedData.Factory<FabricRecievedStarterBundleStorage> storageFactory() {
    //     return FACTORY;
    // }


    //1.21.5+
    public static SavedDataType<FabricRecievedStarterBundleStorage> savedDataType_1_21_5(String name) {
        return LegacyNbtCodec_1_21_5.createSavedDataType(name, 
            FabricRecievedStarterBundleStorage::new, FabricRecievedStarterBundleStorage::save, FabricRecievedStarterBundleStorage::load);
    }
    
}
