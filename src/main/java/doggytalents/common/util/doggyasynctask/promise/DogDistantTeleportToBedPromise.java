package doggytalents.common.util.doggyasynctask.promise;

import doggytalents.DoggyBlocks;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.DogUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import doggytalents.common.forward_imitate.ComponentUtil;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraftforge.common.world.ForgeChunkManager;

/**
 * @author DashieDev
 */
public class DogDistantTeleportToBedPromise extends AbstractPromise {

    private Dog dog; 
    private ServerLevel level;
    private BlockPos bedPos;

    private boolean bedChunkForced;

    private boolean dogTeleported = false;
    private int tickPersist = 5;

    public DogDistantTeleportToBedPromise(Dog dog) {
        this.dog = dog;
        if (this.dog.level instanceof ServerLevel sLevel) {
            this.level = sLevel;
        } else {
            this.level = null;
        }
    }

    @Override
    public void start() {
        if (this.level == null) {
            this.rejectedMsg = "CLIENTLEVEL";
            this.setState(State.REJECTED);
            return;
        }
        var bedPosOptional = dog.getBedPos();
        if (!bedPosOptional.isPresent()) {
            this.rejectedMsg = "NOBEDPOSATDIM";
            this.setState(State.REJECTED);
            return;
        }
        var bedPos0 = bedPosOptional.get();
        this.bedPos = bedPos0;
        var chunkpos = new ChunkPos(bedPos);
        if (dog.level.hasChunk(chunkpos.x, chunkpos.z)) {
            this.rejectedMsg = "ALREADYREQUESTORLOADED";
            this.setState(State.REJECTED);
            return;
        }

        this.setBedChunk(true);

    }

    @Override
    public void tick() {
        if (this.dogTeleported) {
            if (--this.tickPersist <= 0) {
                this.setState(State.FULFILLED);
            }
            return;
        }
        var blockState = this.level.getBlockState(bedPos);
        if (blockState.getBlock() != DoggyBlocks.DOG_BED.get()) {
            this.rejectedMsg = "BEDDESTROYED";
            this.setState(State.REJECTED);
            return;
        }
        var b1 = bedPos.above();
        if (!DogUtil.isTeleportSafeBlockMidAir(dog, b1)) {
            this.rejectedMsg = "BEDOBSTRUCTED";
            this.setState(State.REJECTED);
            return;
        }
        this.dog.fallDistance = 0;
        dog.moveTo(b1.getX() + 0.5F, b1.getY(), b1.getZ() + 0.5F, dog.getYRot(), dog.getXRot());
        dog.getNavigation().stop();
        dog.setOrderedToSit(true);
        this.dogTeleported = true;
        this.tickPersist = 5;
    }

    @Override
    public void onFulfilled() {
        var owner = this.dog.getOwner();
        if (owner != null) {
            owner.sendMessage(
                ComponentUtil.translatable(
                    "item.doggytalents.conducting_bone.fulfilled.tp_bed", 
                    this.dog.getName().getString(), this.dog.getGenderPossessiveAdj()
                ),
                Util.NIL_UUID
            );
        }
    }

    @Override
    public void onRejected() {
        var owner = this.dog.getOwner();
        if (owner != null) {
            owner.sendMessage(
                ComponentUtil.translatable(
                    "item.doggytalents.conducting_bone.rejected",
                    ComponentUtil.literal(this.rejectedMsg).withStyle(
                        Style.EMPTY.withBold(true)
                        .withColor(ChatFormatting.RED)
                    )
                ), Util.NIL_UUID
            );
        }
    }

    @Override
    public void cleanUp() {
        if (this.bedChunkForced) this.setBedChunk(false);
    }

    private void setBedChunk(boolean loaded) {
        if (this.bedChunkForced == loaded) return;
        ChunkPos chunkpos = new ChunkPos(bedPos);
        //if (this.level.hasChunk(chunkpos.x, chunkpos.z)) return;
        ForgeChunkManager.forceChunk(
            this.level, Constants.MOD_ID, 
            this.dog.getUUID(),
            chunkpos.x, chunkpos.z, 
            loaded, true);
        this.bedChunkForced = loaded;
    }
    
}
