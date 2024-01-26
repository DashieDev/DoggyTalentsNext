package doggytalents.common.network.packet.data;

import java.util.ArrayList;
import java.util.Optional;

import javax.annotation.Nullable;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.api.registry.TalentInstance;

public class DogSyncData {
    
    public final int dogId;
    private ArrayList<TalentInstance> talents = null;
    private ArrayList<AccessoryInstance> accessories = null;

    public DogSyncData(int dogId, @Nullable ArrayList<TalentInstance> talents,
        @Nullable ArrayList<AccessoryInstance> accessories) {
        this.dogId = dogId;
        this.talents = talents;
        this.accessories = accessories;
    }

    public Optional<ArrayList<AccessoryInstance>> accessories() {
        return Optional.ofNullable(accessories);
    }

    public Optional<ArrayList<TalentInstance>> talents() {
        return Optional.ofNullable(talents);
    }



}
