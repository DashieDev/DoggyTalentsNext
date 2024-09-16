package doggytalents.common.entity.datasync;

import java.util.ArrayList;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogSyncData;
import net.minecraft.server.level.ServerPlayer;
import doggytalents.forge_imitate.network.PacketDistributor;

public class DogDataSyncManager {

    private final Dog dog;
    private final ArrayList<TalentInstance> talents =
            new ArrayList<>();
    private final ArrayList<AccessoryInstance> accessories = 
        new ArrayList<>();
    private final ArrayList<TalentInstance> talentsNeedsRefresh =
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
        if (this.dog.level().isClientSide)
            return;
        this.talentsDirty = true;
        this.dog.onDogSyncedDataUpdated(true, false);
    }

    public void setAccessoriesDirty() {
        if (this.dog.level().isClientSide)
            return;
        this.accessoriesDirty = true;
        this.dog.onDogSyncedDataUpdated(false, true);
    }

    public void markTalentNeedRefresh(TalentInstance inst) {
        this.talentsNeedsRefresh.add(inst);
    }

    public void tick() {
        if (this.accessoriesDirty || this.talentsDirty || !this.talentsNeedsRefresh.isEmpty()) 
            broadcastChangesToClients();
    }

    public void onStartBeingSeenBy(ServerPlayer seenBy) {
        sendAllDataTo(seenBy);
    }

    private void sendAllDataTo(ServerPlayer seenBy) {
        PacketHandler.send(PacketDistributor.PLAYER.with(() -> seenBy), 
            new DogSyncData(this.dog.getId(), talents(), accessories(), null)
        );
    }

    private void broadcastChangesToClients() {
        ArrayList<AccessoryInstance> accessories = null;
        ArrayList<TalentInstance> talents = null;
        ArrayList<TalentInstance> needRefresh = null;
        if (accessoriesDirty) {
            accessoriesDirty = false;
            accessories = new ArrayList<>(this.accessories());
        }
        if (talentsDirty) {
            talentsDirty = false;
            talents = new ArrayList<>(this.talents());
            this.talentsNeedsRefresh.clear();
        }
        if (!this.talentsNeedsRefresh.isEmpty()) {
            needRefresh = new ArrayList<>(this.talentsNeedsRefresh);
            this.talentsNeedsRefresh.clear();
        }
        PacketHandler.send(PacketDistributor.TRACKING_ENTITY.with(() -> this.dog), 
            new DogSyncData(this.dog.getId(), talents, accessories, needRefresh)
        );
    }

    public void updateFromDataPacketFromServer(DogSyncData data) {
        var accessories = data.accessories();
        if (accessories.isPresent()) {
            this.accessories.clear();
            this.accessories.addAll(accessories.get());
        }
        var talents = data.talents();
        if (talents.isPresent()) {
            this.talents.clear();
            this.talents.addAll(talents.get());
        }
        var refreshOptions = data.refreshOptions();
        if (refreshOptions.isPresent()) {
            for (var inst : refreshOptions.get()) {
                var talent = inst.getTalent();
                var dogInst = dog.getTalent(talent);
                if (!dogInst.isPresent())
                    continue;
                dogInst.get().copyTalentOptionFrom(inst);
            }
        }
        dog.onDogSyncedDataUpdated(talents.isPresent(), accessories.isPresent());
    }

}
