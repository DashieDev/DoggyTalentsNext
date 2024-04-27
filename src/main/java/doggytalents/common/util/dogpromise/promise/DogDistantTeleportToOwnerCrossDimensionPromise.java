package doggytalents.common.util.dogpromise.promise;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import doggytalents.common.chunk.DoggyChunkController;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.CachedSearchUtil.CachedSearchUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.ITeleporter;

public class DogDistantTeleportToOwnerCrossDimensionPromise extends AbstractPromise {
    
    private static final int TIMEOUT = 200;
    private static final int SEARCH_INTERVAL = 10;

    private final BlockPos targetPos;
    private final BlockPos dogPos;
    private final UUID dogUUID;
    private final ServerLevel ownerLevel;
    private final ServerLevel dogLevel;

    private int timeOutTick;
    private int tickTillSearch;

    private Dog teleportedDog;
    private final LivingEntity owner;

    private boolean dogChunkForced;

    public DogDistantTeleportToOwnerCrossDimensionPromise(@Nonnull UUID dogUUID, @Nonnull LivingEntity owner,
         @Nonnull BlockPos dogPos, ServerLevel dogLevel, ServerLevel ownerLevel) {
        this.dogUUID = dogUUID;
        this.ownerLevel = ownerLevel;
        this.targetPos = owner.blockPosition();
        this.dogPos = dogPos;
        this.owner = owner;
        this.dogLevel = dogLevel;
    }

    @Override
    public void tick() {
        if (this.dogLevel == this.ownerLevel) {
            this.rejectedMsg = "WHAT?";
            this.setState(State.REJECTED);
            return;
        }
        Entity e = null;
        if (--tickTillSearch <= 0) {
            tickTillSearch = SEARCH_INTERVAL;
            e = this.dogLevel.getEntity(dogUUID);
        } 
        if (e == null) {
            if (--this.timeOutTick <= 0) {
                this.rejectedMsg = "TIMEOUT";
                this.setState(State.REJECTED);
            }
            return;
        }
        if (!(e instanceof Dog dog)) {
            this.rejectedMsg = "WHAT?";
            this.setState(State.REJECTED);
            return;
        }
        var safePosList = 
            CachedSearchUtil.getAllSafePosUsingPool(ownerLevel, List.of((Dog)e), targetPos, 4, 1);
        if (safePosList.isEmpty()) {
            this.rejectedMsg = "NOSAFEPOS";
            this.setState(State.REJECTED);
            return;
        }
        
        int r = dog.getRandom().nextInt(safePosList.size());
        var safePos = safePosList.get(r);
        dog.authorizeChangeDimension();
        var dogafterTp = dog.changeDimension(ownerLevel, new DogTeleporter(safePos));

        if (dogafterTp instanceof Dog) {
            this.teleportedDog = (Dog) dogafterTp;
        }
        
        this.setState(State.FULFILLED);
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
        this.ownerLevel.sendParticles(
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
        // ChopinLogger.l("this level should have no forced chunk, and this is the result : " 
        //     +   ForgeChunkManager.hasForcedChunks(dogLevel)
        // );
        
        if (this.ownerLevel == null || this.dogLevel == null) {
            this.rejectedMsg = "CLIENTLEVEL";
            this.setState(State.REJECTED);
            return;
        }

        //This promise don't handle same dimension tp.
        if (this.ownerLevel == this.dogLevel) {
            this.rejectedMsg = "SAMEDIM";
            this.setState(State.REJECTED);
            return;
        }

        this.timeOutTick = TIMEOUT;
        ChunkPos chunkpos = new ChunkPos(dogPos);
        if (this.dogLevel.hasChunk(chunkpos.x, chunkpos.z)) {
            return;
        }

        // ChopinLogger.l("hasChunk before ? : " 
        //     + this.dogLevel.hasChunk(chunkpos.x, chunkpos.z)
        // );

        this.setDogChunk(true);

        // ChopinLogger.l("Does hasChunk return true immediately after forced? : " 
        //     + this.dogLevel.hasChunk(chunkpos.x, chunkpos.z)
        // );
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
        DoggyChunkController.get().forceChunk(
            this.dogLevel,
            dogUUID,
            chunkpos.x, chunkpos.z, 
            loaded, true);
        this.dogChunkForced = loaded;
    }

    private static class DogTeleporter implements ITeleporter {

        private BlockPos safePos;

        public DogTeleporter(BlockPos safePos) {
            this.safePos = safePos;
        }

        @Override
        public @Nullable PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld,
                Function<ServerLevel, PortalInfo> defaultPortalInfo) {
            return new PortalInfo(
                Vec3.atBottomCenterOf(safePos), 
                Vec3.ZERO, 
                entity.getYRot(), entity.getXRot()
            );
        }

    }

}
