package doggytalents.common.entity.datasync;

import java.util.ArrayList;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogSyncData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public class DogDataSyncManager {

    private final Dog dog;
    private final ArrayList<TalentInstance> talents =
            new ArrayList<>();
    private final ArrayList<AccessoryInstance> accessories = 
        new ArrayList<>();

    public DogDataSyncManager(Dog dog) {
        this.dog = dog;
    }
    
    private boolean talentsDirty = false;
    private boolean accessoriesDirty = false;

    public ArrayList<TalentInstance> talents() {
        return this.talents;
    }

    public ArrayList<AccessoryInstance> accessories() {
        return this.accessories;
    }

    public void setTalentsDirty() {
        this.talentsDirty = true;
        this.dog.onTalentsUpdated();
    }

    public void setAccessoriesDirty() {
        this.accessoriesDirty = true;
        this.dog.onAccessoriesUpdated();
    }

    public void tick() {
        if (this.accessoriesDirty || this.talentsDirty) 
            broadcastChangesToClients();
    }

    public void onStartBeingSeenBy(ServerPlayer seenBy) {
        sendAllDataTo(seenBy);
    }

    private void sendAllDataTo(ServerPlayer seenBy) {
        PacketHandler.send(PacketDistributor.PLAYER.with(() -> seenBy), 
            new DogSyncData(this.dog.getId(), talents(), accessories())
        );
    }

    private void broadcastChangesToClients() {
        ArrayList<AccessoryInstance> accessories = null;
        ArrayList<TalentInstance> talents = null;
        if (accessoriesDirty) {
            accessoriesDirty = false;
            accessories = new ArrayList<>(this.accessories());
        }
        if (talentsDirty) {
            talentsDirty = false;
            talents = new ArrayList<>(this.talents());
        }
        PacketHandler.send(PacketDistributor.TRACKING_ENTITY.with(() -> this.dog), 
            new DogSyncData(this.dog.getId(), talents, accessories)
        );
    }

    public void updateFromDataPacketFromServer(DogSyncData data) {
        var accessories = data.accessories();
        if (accessories.isPresent()) {
            this.accessories.clear();
            this.accessories.addAll(accessories.get());
            dog.onAccessoriesUpdated();
        }
        var talents = data.talents();
        if (talents.isPresent()) {
            this.talents.clear();
            this.talents.addAll(talents.get());
            dog.onTalentsUpdated();
        }
    }

}
