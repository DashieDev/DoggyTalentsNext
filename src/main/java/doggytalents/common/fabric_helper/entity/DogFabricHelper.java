package doggytalents.common.fabric_helper.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import doggytalents.api.feature.DogLevel;
import doggytalents.api.feature.DogSize;
import doggytalents.api.feature.DogGender;
import doggytalents.api.feature.DogMode;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogIncapacitatedMananger.IncapacitatedSyncState;
import doggytalents.common.entity.DogPettingManager.DogPettingState;
import doggytalents.common.entity.DogSleepOnManager.DogSleepOnState;
import doggytalents.common.entity.anim.DogAnimationManager.DogAnimDebugState;
import doggytalents.common.entity.serializers.Dimension2BlockPosMap;
import doggytalents.common.entity.texture.DogSkinData;
import doggytalents.common.fabric_helper.entity.network.FabricSyncAllData;
import doggytalents.common.fabric_helper.entity.network.SyncTypes;
import doggytalents.common.fabric_helper.entity.network.SyncTypes.SyncType;
import doggytalents.common.item.DoggyArtifactItem;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.variant.DogVariant;
import doggytalents.common.variant.util.DogVariantUtil;
import doggytalents.forge_imitate.network.PacketDistributor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.server.level.ServerPlayer;

public class DogFabricHelper {
    
    private final Dog dog;
    private DogLevel dogLevel = new DogLevel(0, 0);
    private DogVariant dogVariant = DogVariantUtil.getDefault();
    private DogGender dogGender = DogGender.MALE;
    private DogMode dogMode = DogMode.DOCILE;
    private Dimension2BlockPosMap bowlPos = new Dimension2BlockPosMap();    
    private Dimension2BlockPosMap bedPos = new Dimension2BlockPosMap();
    private IncapacitatedSyncState incapSyncState = IncapacitatedSyncState.NONE;
    private List<DoggyArtifactItem> artifacts = new ArrayList<>();
    private DogSize dogSize = DogSize.MODERATO;
    private DogSkinData dogSkin = DogSkinData.NULL;
    private DogPettingState dogPettingState = DogPettingState.NULL;
    private DogAnimDebugState dogAnimDebugState = DogAnimDebugState.NONE;
    private DogSleepOnState dogSleepOnState = DogSleepOnState.NULL;

    private final ArrayList<SyncType<?>> dirtyEntries = new ArrayList<>();

    public DogFabricHelper(Dog dog) {
        this.dog = dog;
    }

    private void setDirty(SyncType<?> type) {
        if (dirtyEntries.contains(type))
            return;
        dirtyEntries.add(type);
    }
    
    public void tick() {
        if (dog.level().isClientSide)
            return;
        if (dirtyEntries.isEmpty())
            return;
        broadcastChangesToClients(dirtyEntries);
        dirtyEntries.clear();
    }

    private void broadcastChangesToClients(List<SyncType<?>> dirty) {
        var data = new FabricSyncAllData(this.dog.getId());
        for (var type : dirty) {
            data.putVal(type, this);
        }
        PacketHandler.send(PacketDistributor.TRACKING_ENTITY.with(() -> this.dog), data);
    }

    public DogLevel getDogLevel() {
        return dogLevel;
    }

    public void setDogLevel(DogLevel dogLevel) {
        if (dogLevel == this.dogLevel)
            return;
        if (!dog.level().isClientSide) {
            this.setDirty(SyncTypes.DOG_LEVEL);
        }
        this.dogLevel = dogLevel;
        this.dog.onFabricDataUpdated(SyncTypes.DOG_LEVEL);
    }

    public DogVariant getDogVariant() {
        return dogVariant;
    }

    public void setDogVariant(DogVariant val) {
        if (val == this.dogVariant)
            return;
        if (!dog.level().isClientSide) {
            this.setDirty(SyncTypes.DOG_VARIANT);
        }
        this.dogVariant = val;
        this.dog.onFabricDataUpdated(SyncTypes.DOG_VARIANT);
    }

    public DogGender getDogGender() {
        return dogGender;
    }

    public void setDogGender(DogGender dogGender) {
        if (dogGender == this.dogGender)
            return;
        if (!dog.level().isClientSide) {
            this.setDirty(SyncTypes.DOG_GENDER);
        }
        this.dogGender = dogGender;
    }

    public DogMode getDogMode() {
        return dogMode;
    }

    public void setDogMode(DogMode dogMode) {
        if (dogMode == this.dogMode)
            return;
        if (!dog.level().isClientSide) {
            this.setDirty(SyncTypes.DOG_MODE);
        }
        this.dogMode = dogMode;
        this.dog.onFabricDataUpdated(SyncTypes.DOG_MODE);
    }

    public Dimension2BlockPosMap getBowlPos() {
        return bowlPos;
    }

    public void setBowlPos(Dimension2BlockPosMap bowlPos) {
        if (this.bowlPos.equals(bowlPos))
            return;
        if (!dog.level().isClientSide) 
            this.setDirty(SyncTypes.BOWL_POS);
        this.bowlPos = bowlPos;
        this.dog.onFabricDataUpdated(SyncTypes.BOWL_POS);
    }

    public Dimension2BlockPosMap getBedPos() {
        return bedPos;
    }

    public void setBedPos(Dimension2BlockPosMap bedPos) {
        this.bedPos = bedPos;
    }

    public IncapacitatedSyncState getIncapSyncState() {
        return incapSyncState;
    }

    public void setIncapSyncState(IncapacitatedSyncState incapSyncState) {
        if (this.incapSyncState.equals(incapSyncState))
            return;
        if (!dog.level().isClientSide) {
            this.setDirty(SyncTypes.INCAP_SYNC_STATE);
        }
        this.incapSyncState = incapSyncState;
    }

    public List<DoggyArtifactItem> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<DoggyArtifactItem> artifacts) {
        if (this.artifacts == artifacts)
            return;
        if (!dog.level().isClientSide) {
            this.setDirty(SyncTypes.ARTIFACTS);
        }
        this.artifacts = artifacts;
        dog.onFabricDataUpdated(SyncTypes.ARTIFACTS);
    }

    public DogSize getDogSize() {
        return dogSize;
    }

    public void setDogSize(DogSize dogSize) {
        if (this.dogSize == dogSize)
            return;
        if (!dog.level().isClientSide) {
            this.setDirty(SyncTypes.DOG_SIZE);
        }
        this.dogSize = dogSize;
        dog.onFabricDataUpdated(SyncTypes.DOG_SIZE);
    }

    public DogSkinData getDogSkin() {
        return this.dogSkin;
    }

    public void setDogSkin(DogSkinData data) {
        if (this.dogSkin == data)
            return;
        if (!dog.level().isClientSide) {
            this.setDirty(SyncTypes.DOG_SKIN);
        }
        this.dogSkin = data;
        dog.onFabricDataUpdated(SyncTypes.DOG_SKIN);
    }

    public DogPettingState getDogPettingState() {
        return this.dogPettingState;
    }

    public void setDogPettingState(DogPettingState data) {
        if (this.dogPettingState.equals(data))
            return;
        if (!dog.level().isClientSide) {
            this.setDirty(SyncTypes.DOG_PETTING_STATE);
        }
        this.dogPettingState = data;
        dog.onFabricDataUpdated(SyncTypes.DOG_PETTING_STATE);
    }

    public DogAnimDebugState getDogAnimDebugState() {
        return this.dogAnimDebugState;
    }

    public void setDogAnimDebugState(DogAnimDebugState data) {
        if (this.dogAnimDebugState.equals(data))
            return;
        if (!dog.level().isClientSide) {
            this.setDirty(SyncTypes.DOG_ANIM_DEBUG_STATE);
        }
        this.dogAnimDebugState = data;
        dog.onFabricDataUpdated(SyncTypes.DOG_ANIM_DEBUG_STATE);
    }

    public DogSleepOnState getDogSleepOnState() {
        return this.dogSleepOnState;
    }

    public void setDogSleepOnState(DogSleepOnState data) {
        if (this.dogSleepOnState.equals(data))
            return;
        if (!dog.level().isClientSide) {
            this.setDirty(SyncTypes.DOG_SLEEP_ON_STATE);
        }
        this.dogSleepOnState = data;
        dog.onFabricDataUpdated(SyncTypes.DOG_SLEEP_ON_STATE);
    }

    public void onStartBeingSeenBy(ServerPlayer player) {
        sendAllDataTo(player);
    }

    private void sendAllDataTo(ServerPlayer player) {
        var data = new FabricSyncAllData(this.dog.getId());
        for (var type : SyncTypes.getAll()) {
            data.putVal(type, this);
        }
        PacketHandler.send(PacketDistributor.PLAYER.with(() -> player), data);
    }
}
