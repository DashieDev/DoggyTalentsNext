package doggytalents.common.util.dogpromise.promise;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

public class DogHoldChunkToTeleportPromise extends AbstractPromise {

    private final List<Dog> dogs;
    private final ServerLevel level;

    private int timeOut;

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
    }

    @Override
    public void onRejected() {
    }

    private void forceDogChunk() {
        for (var dog : dogs) {
            if (!dog.isDoingFine())
                continue;
            var chunkpos = ChunkPos.containing(dog.blockPosition());
            this.accquireChunk(this.level, chunkpos);
        }
    }
    
}
