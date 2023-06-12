package doggytalents.common.util.doggyasynctask.promise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.CachedSearchUtil.CachedSearchUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.world.ForgeChunkManager;

public class DogBatchTeleportToDimensionPromise extends AbstractPromise {

    private final List<Dog> dogs;
    private final UUID playerUUID;
    private final ResourceKey<Level> dimeansion;
    private final ServerLevel origin;

    private int tickTillCheck;
    private int timeOut;

    private final ArrayList<ChunkPos> forcedDogChunk = new ArrayList<>();

    public DogBatchTeleportToDimensionPromise(List<Dog> dogs, ServerLevel origin, UUID playerUUID, ResourceKey<Level> dimeansion) {
        this.dogs = dogs;
        this.playerUUID = playerUUID;
        this.dimeansion = dimeansion;
        this.origin = origin;
    } 

    @Override
    public void start() {
        forceDogChunk();
        this.tickTillCheck = 7;
        this.timeOut = 50;
    }

    @Override
    public void tick() {
        if (--this.timeOut <= 0) {
            this.setState(State.REJECTED);
            return;
        }
        if (--this.tickTillCheck > 0) return;
        this.tickTillCheck = 20;
        var mcServer = origin.getServer();
        var targetLevel = mcServer.getLevel(dimeansion);
        if (targetLevel == null) return;
        if (targetLevel == origin) {
            this.setState(State.REJECTED);
            return;
        }
        
        var owner = targetLevel.getPlayerByUUID(playerUUID);
        if (owner == null) return;

        var owner_b0 = owner.blockPosition();

        if (!allChunkInvoledAtTargetIsLoaded(targetLevel, owner_b0))
            return;

        var tp_dogs = this.dogs.stream()
            .filter(d -> d.isDoingFine())
            .collect(Collectors.toList());
        if (tp_dogs.isEmpty()) {
            this.setState(State.REJECTED);
            return;
        };

        var safePosList =
            CachedSearchUtil.getAllSafePosUsingPool(targetLevel, tp_dogs, owner_b0, 4, 1);
        if (safePosList.isEmpty()) return;

        for (var dog : tp_dogs) {
            int index = dog.getRandom().nextInt(safePosList.size());
            teleportDog(dog, targetLevel, safePosList.get(index));
        }

        this.setState(State.FULFILLED);
    }

    private boolean allChunkInvoledAtTargetIsLoaded(ServerLevel target, BlockPos ownerPos) {
        var center = new ChunkPos(ownerPos);
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (!target.getChunkSource().hasChunk(center.x + i, center.z + j)) 
                    return false;
            }
        }
        return true;
    }

    private void teleportDog(Dog dog0, ServerLevel targetLevel, BlockPos pos) {
        if (!dog0.isDoingFine()) return;

        //TODO Maybe broadcast Forge Event here.

        var e = dog0.getType().create(targetLevel);
        if (!(e instanceof Dog dog)) return;
        dog.restoreFrom(dog0);

        dog.fallDistance = 0;
        dog.moveTo(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, dog.getYRot(), dog.getXRot());
        dog.getNavigation().stop();
        dog.setPortalCooldown();
        targetLevel.addDuringTeleport(dog);
        
        dog0.unRide();
        dog0.remove(RemovalReason.CHANGED_DIMENSION);
        targetLevel.resetEmptyTime();
        if (dog0.level() instanceof ServerLevel sLevel) 
            sLevel.resetEmptyTime();
    }

    @Override
    public void onFulfilled() {
    }

    @Override
    public void onRejected() {
    }

    @Override
    public void cleanUp() {
        cleanDogChunk();
    }

    private void forceDogChunk() {
        for (var dog : dogs) {
            if (!dog.isDoingFine())
                continue;
            var chunkpos = new ChunkPos(dog.blockPosition());
            if (this.forcedDogChunk.contains(chunkpos))
                continue;
            this.forcedDogChunk.add(chunkpos);
            ForgeChunkManager.forceChunk(
                this.origin, Constants.MOD_ID, 
                this.getOwner().getUUID(),
                chunkpos.x, chunkpos.z, 
                true, true);
        }
    }

    private void cleanDogChunk() {
        for (var chunkpos : this.forcedDogChunk) {
            ForgeChunkManager.forceChunk(
                this.origin, Constants.MOD_ID, 
                this.getOwner().getUUID(),
                chunkpos.x, chunkpos.z, 
                false, true);
        }
    }
    
}
