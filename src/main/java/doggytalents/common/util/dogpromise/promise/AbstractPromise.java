package doggytalents.common.util.dogpromise.promise;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import doggytalents.common.lib.Constants;
import doggytalents.common.util.dogpromise.chunk.DTNForcedChunkManager;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

/**
 * @author DashieDev
 */
public abstract class AbstractPromise {

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID + "/chunkTask");

    private @Nullable ServerPlayer owner;

    private State state;

    protected String rejectedMsg = "";
    private final Map<ResourceKey<Level>, AccquiredChunks> accquiredChunks = new HashMap<>();

    public AbstractPromise() {
        this.state = State.PENDING;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public ServerPlayer getOwner() {
        return this.owner;
    }

    public void setOwner(ServerPlayer owner) {
        this.owner = owner;
    }

    public abstract void start();
    
    public abstract void tick();

    public abstract void onFulfilled();

    public abstract void onRejected();

    public void cleanUp() {};

    public final void doCleanUp() {
        dropAllAccquiredChunk();
        cleanUp();
    }

    protected final void accquireChunk(ServerLevel level, ChunkPos pos) {
        var accquireds = this.accquiredChunks
            .computeIfAbsent(level.dimension(), $ -> new AccquiredChunks(level))
            .chunks();
        boolean added = accquireds.add(pos.toLong());
        if (!added) {
            //LOGGER.error("chunk already accquired. " + pos );
            return;
        }
        DTNForcedChunkManager.accquireChunk(level, this, pos);
        //LOGGER.info("Accquired chunk : " + pos);
    }

    protected final void dropAllAccquiredChunk() {
        int drop_count = 0;
        for (var entry : this.accquiredChunks.entrySet()) {
            var source = entry.getValue().source();
            var chunks = entry.getValue().chunks();
            if (chunks.isEmpty())
                continue;
            for (var chunk : chunks) {
                DTNForcedChunkManager.dropChunk(source, this, new ChunkPos(chunk));
                ++drop_count;
            }
        }
        this.accquiredChunks.clear();
        //LOGGER.info("Dropped " + drop_count + " accquired chunks");
    }

    public void forceReject() {
        this.setState(State.REJECTED);
        this.rejectedMsg = "FORCED";
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    public static enum State {
        PENDING,
        RUNNING, 
        FULFILLED,
        REJECTED
    }

    private static record AccquiredChunks(
        ServerLevel source,
        LongSet chunks
    ) {

        public AccquiredChunks(ServerLevel source) {
            this(source, new LongOpenHashSet());
        }
    }

}
