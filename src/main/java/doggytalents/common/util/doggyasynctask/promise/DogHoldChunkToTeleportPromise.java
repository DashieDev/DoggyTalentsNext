package doggytalents.common.util.doggyasynctask.promise;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import doggytalents.ChopinLogger;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.CachedSearchUtil.CachedSearchUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.common.world.ForgeChunkManager;

public class DogHoldChunkToTeleportPromise extends AbstractPromise {

    private final List<Dog> dogs;
    private final ServerLevel level;

    private int timeOut;

    private final ArrayList<ChunkPos> forcedDogChunk = new ArrayList<>();

    public DogHoldChunkToTeleportPromise(List<Dog> dogs, ServerLevel level) {
        this.dogs = dogs;
        this.level = level;
    } 

    @Override
    public void start() {
        forceDogChunk();
        this.timeOut = 40;
    }

    @Override
    public void tick() {
        if (--this.timeOut <= 0) {
            this.setState(State.FULFILLED);
            return;
        }
    }

    @Override
    public void onFulfilled() {
        ChopinLogger.l("teleported dogs distant!");
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
                this.level, Constants.MOD_ID, 
                this.getOwner().getUUID(),
                chunkpos.x, chunkpos.z, 
                true, true);
        }
    }

    private void cleanDogChunk() {
        for (var chunkpos : this.forcedDogChunk) {
            ForgeChunkManager.forceChunk(
                this.level, Constants.MOD_ID, 
                this.getOwner().getUUID(),
                chunkpos.x, chunkpos.z, 
                false, true);
        }
        this.forcedDogChunk.clear();
    }
    
}
