package doggytalents.common.util.dogpromise.promise;

import doggytalents.DoggyBlocks;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.DogUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

/**
 * @author DashieDev
 */
public class DogDistantTeleportToBedPromise extends AbstractPromise {

    private Dog dog; 
    private ServerLevel level;
    private BlockPos bedPos;

    private boolean dogTeleported = false;
    private int tickPersist = 5;

    public DogDistantTeleportToBedPromise(Dog dog) {
        this.dog = dog;
        if (this.dog.level() instanceof ServerLevel sLevel) {
            this.level = sLevel;
        } else {
            this.level = null;
        }
    }

    @Override
    public void start() {
        if (!dog.isAlive()) {
            this.rejectedMsg = "DOGOFFLINE";
            this.setState(State.REJECTED);
            return;
        }
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
        if (dog.level().hasChunk(chunkpos.x, chunkpos.z)) {
            this.rejectedMsg = "ALREADYREQUESTORLOADED";
            this.setState(State.REJECTED);
            return;
        }

        this.accquireChunk(this.level, chunkpos);

    }

    @Override
    public void tick() {
        if (this.dogTeleported) {
            if (--this.tickPersist <= 0) {
                this.setState(State.FULFILLED);
            }
            return;
        }
        if (!dog.isAlive()) {
            this.rejectedMsg = "DOGOFFLINE";
            this.setState(State.REJECTED);
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
            owner.sendSystemMessage(
                Component.translatable(
                    "item.doggytalents.conducting_bone.fulfilled.tp_bed", 
                    this.dog.getName().getString(), this.dog.getGenderPossessiveAdj()
                )
            );
        }
    }

    @Override
    public void onRejected() {
        var owner = this.dog.getOwner();
        if (owner != null) {
            owner.sendSystemMessage(
                Component.translatable(
                    "item.doggytalents.conducting_bone.rejected",
                    Component.literal(this.rejectedMsg).withStyle(
                        Style.EMPTY.withBold(true)
                        .withColor(ChatFormatting.RED)
                    )
                )
            );
        }
    }
}
