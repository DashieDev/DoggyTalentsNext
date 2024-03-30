package doggytalents.common.fabric_helper.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import doggytalents.api.feature.DogLevel;
import doggytalents.api.feature.DogSize;
import doggytalents.api.feature.EnumGender;
import doggytalents.api.feature.EnumMode;
import doggytalents.common.entity.ClassicalVar;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogIncapacitatedMananger.IncapacitatedSyncState;
import doggytalents.common.entity.serializers.DimensionDependantArg;
import doggytalents.common.entity.texture.DogSkinData;
import doggytalents.common.fabric_helper.entity.network.FabricSyncAllData;
import doggytalents.common.fabric_helper.entity.network.SyncTypes;
import doggytalents.common.fabric_helper.entity.network.SyncTypes.SyncType;
import doggytalents.common.item.DoggyArtifactItem;
import doggytalents.common.network.PacketHandler;
import doggytalents.forge_imitate.network.PacketDistributor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.server.level.ServerPlayer;

public class DogFabricHelper {
    
    private final Dog dog;
    private DogLevel dogLevel = new DogLevel(0, 0);
    private ClassicalVar classicalVar = ClassicalVar.PALE;
    private EnumGender dogGender = EnumGender.MALE;
    private EnumMode dogMode = EnumMode.DOCILE;
    private DimensionDependantArg<Optional<BlockPos>> bowlPos = new DimensionDependantArg<>(() -> EntityDataSerializers.OPTIONAL_BLOCK_POS);    
    private DimensionDependantArg<Optional<BlockPos>> bedPos = new DimensionDependantArg<>(() -> EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private IncapacitatedSyncState incapSyncState = IncapacitatedSyncState.NONE;
    private List<DoggyArtifactItem> artifacts = new ArrayList<>();
    private DogSize dogSize = DogSize.MODERATO;
    private DogSkinData dogSkin = DogSkinData.NULL;

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

    public ClassicalVar getClassicalVar() {
        return classicalVar;
    }

    public void setClassicalVar(ClassicalVar val) {
        if (val == this.classicalVar)
            return;
        if (!dog.level().isClientSide) {
            this.setDirty(SyncTypes.CLASSICAL_VAR);
        }
        this.classicalVar = val;
    }

    public EnumGender getDogGender() {
        return dogGender;
    }

    public void setDogGender(EnumGender dogGender) {
        if (dogGender == this.dogGender)
            return;
        if (!dog.level().isClientSide) {
            this.setDirty(SyncTypes.DOG_GENDER);
        }
        this.dogGender = dogGender;
    }

    public EnumMode getDogMode() {
        return dogMode;
    }

    public void setDogMode(EnumMode dogMode) {
        if (dogMode == this.dogMode)
            return;
        if (!dog.level().isClientSide) {
            this.setDirty(SyncTypes.DOG_MODE);
        }
        this.dogMode = dogMode;
        this.dog.onFabricDataUpdated(SyncTypes.DOG_MODE);
    }

    public DimensionDependantArg<Optional<BlockPos>> getBowlPos() {
        return bowlPos;
    }

    public void setBowlPos(DimensionDependantArg<Optional<BlockPos>> bowlPos) {
        this.bowlPos = bowlPos;
    }

    public DimensionDependantArg<Optional<BlockPos>> getBedPos() {
        return bedPos;
    }

    public void setBedPos(DimensionDependantArg<Optional<BlockPos>> bedPos) {
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
