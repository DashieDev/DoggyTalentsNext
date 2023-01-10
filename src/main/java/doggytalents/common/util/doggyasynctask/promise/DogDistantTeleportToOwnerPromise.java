package doggytalents.common.util.doggyasynctask.promise;

import java.util.UUID;

import javax.annotation.Nonnull;

import doggytalents.ChopinLogger;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.storage.DogLocationStorage;
import doggytalents.common.util.DogUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment.Server;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.world.ForgeChunkManager;

/**
 * @author DashieDev
 */
public class DogDistantTeleportToOwnerPromise extends AbstractPromise {

    private static final int TIMEOUT = 200;
    private static final int SEARCH_INTERVAL = 10;

    private final BlockPos targetPos;
    private final BlockPos dogPos;
    private final UUID dogUUID;
    private final ServerLevel level;

    private int timeOutTick;
    private int tickTillSearch;

    private Dog teleportedDog;
    private final LivingEntity owner;

    private boolean dogChunkForced;

    public DogDistantTeleportToOwnerPromise(@Nonnull UUID dogUUID, @Nonnull LivingEntity owner,
         @Nonnull BlockPos dogPos) {
        this.dogUUID = dogUUID;
        if (owner.level instanceof ServerLevel sL) {
            this.level = sL;
        } else {
            this.level = null;
        }
        this.targetPos = owner.blockPosition();
        this.dogPos = dogPos;
        this.owner = owner;
    }

    @Override
    public void tick() {
        Entity dog = null;
        if (--tickTillSearch <= 0) {
            tickTillSearch = SEARCH_INTERVAL;
            dog = this.level.getEntity(dogUUID);
        } 
        if (dog != null) {
            if (! (dog instanceof Dog)) {
                this.rejectedMsg = "WHAT?";
                this.setState(State.REJECTED);
                return;
            }
            boolean flag = 
                DogUtil.dynamicSearchAndTeleportToBlockPos((Dog)dog, targetPos, 4);
            if (!flag) {
                this.rejectedMsg = "NOSAFEPOS";
                this.setState(State.REJECTED);
                return;
            }
            this.teleportedDog = (Dog) dog;
            this.setState(State.FULFILLED);
        } else {
            if (--this.timeOutTick <= 0) {
                this.rejectedMsg = "TIMEOUT";
                this.setState(State.REJECTED);
            }
        }
    }

    @Override
    public void onFulfilled() {
        if (this.owner != null && this.teleportedDog != null)
            this.owner.sendSystemMessage(
                Component.translatable(
                    "item.doggytalents.conducting_bone.fulfilled.tp_self", 
                    this.teleportedDog.getName().getString()  
                )
            );
        if (this.teleportedDog != null)
        this.level.sendParticles(
            ParticleTypes.PORTAL, 
            this.teleportedDog.getX(), this.teleportedDog.getY(), this.teleportedDog.getZ(), 
            30, 
            this.teleportedDog.getBbWidth(), 0.8f, this.teleportedDog.getBbWidth(), 
            0.1
        );
    }

    //Ressurect
    @Override
    public void onRejected() {
        if (this.owner != null)
            this.owner.sendSystemMessage(
                Component.translatable(
                    "item.doggytalents.conducting_bone.rejected",
                    Component.literal(this.rejectedMsg).withStyle(
                        Style.EMPTY.withBold(true)
                        .withColor(ChatFormatting.RED)
                    )
                )
            );     
    }

    //this method also check if can start, if not, then set it to rejected.
    @Override
    public void start() {
        ChopinLogger.l("this level should have no forced chunk, and this is the result : " 
            +   ForgeChunkManager.hasForcedChunks(level)
        );
        
        if (this.level == null) {
            this.rejectedMsg = "CLIENTLEVEL";
            this.setState(State.REJECTED);
            return;
        }

        //This promise don't handle cross dimension tp.
        var storage = DogLocationStorage.get(level);
        var data = storage.getData(this.dogUUID);
        if (data == null) {
            this.rejectedMsg = "WHAT?";
            this.setState(State.REJECTED);
            return;
        }
        var dogDimKey = data.getDimension();
        var ownerDimKey = owner.level.dimension();
        if (ownerDimKey == null || dogDimKey == null) {
            this.rejectedMsg = "WHAT?";
            this.setState(State.REJECTED);
            return;
        }
        if (!dogDimKey.equals(ownerDimKey)) {
            this.rejectedMsg = "DIFFERENTDIM";
            this.setState(State.REJECTED);
            return;
        }

        this.timeOutTick = TIMEOUT;
        ChunkPos chunkpos = new ChunkPos(dogPos);
        if (this.level.hasChunk(chunkpos.x, chunkpos.z)) {
            this.rejectedMsg = "ALREADYREQUESTORLOADED";
            this.setState(State.REJECTED);
            return;
        }

        ChopinLogger.l("hasChunk before ? : " 
            + this.level.hasChunk(chunkpos.x, chunkpos.z)
        );

        this.setDogChunk(true);

        ChopinLogger.l("Does hasChunk return true immediately after forced? : " 
            + this.level.hasChunk(chunkpos.x, chunkpos.z)
        );
    }

    //No Ressurect
    @Override
    public void cleanUp() {
        if (this.dogChunkForced) this.setDogChunk(false);
    }

    private void setDogChunk(boolean loaded) {
        if (this.dogChunkForced == loaded) return;
        ChunkPos chunkpos = new ChunkPos(dogPos);
        //if (this.level.hasChunk(chunkpos.x, chunkpos.z)) return;
        ForgeChunkManager.forceChunk(
            this.level, Constants.MOD_ID, 
            dogUUID,
            chunkpos.x, chunkpos.z, 
            loaded, true);
        this.dogChunkForced = loaded;
    }

}
