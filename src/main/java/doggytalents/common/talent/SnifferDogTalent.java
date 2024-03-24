package doggytalents.common.talent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import doggytalents.DoggyItems;
import doggytalents.api.anim.DogAnimation;
import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.item.ScentTreatItem;
import doggytalents.common.util.EntityUtil;
import doggytalents.common.util.NBTUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class SnifferDogTalent extends TalentInstance {

    public SnifferDogTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }
    
    private Block detectingBlock = Blocks.AIR;
    private Optional<BlockPos> suspiciousPos = Optional.empty();
    private int dontNotifyOwnerAgainTick = 0;

    @Override
    public void init(AbstractDog dogIn) {
    }
    
    @Override
    public void tick(AbstractDog dogIn) {
        if (dogIn.level().isClientSide)
            return;
        if (!dogIn.isDoingFine())
            return;
        if (!(dogIn instanceof Dog dog))
            return;

        if (this.dontNotifyOwnerAgainTick > 0)
            --this.dontNotifyOwnerAgainTick;

        this.validateSuspiciousPos(dog);

        if (!this.suspiciousPos.isPresent() && !dog.isInSittingPose()) {
            if (this.detectingBlock != Blocks.AIR) {
                detectBlocksAround(dog);
            }
        }
        
        if (dog.isBusy())
            return;
            
        if (suspiciousPos.isPresent() && this.dontNotifyOwnerAgainTick <= 0) {
            notifyOwner(dog);
        }

    }

    private int tickTillDetect = 5;
    private void detectBlocksAround(Dog dog) {
        if (--tickTillDetect > 0)
            return;
        tickTillDetect = 10;
        long startTime = System.nanoTime();
        doDetectBlockAround(dog);
        long stopTime = System.nanoTime();
        //ChopinLogger.lwn(dog, "Detected ore in " + (stopTime - startTime) + "ns");
    }

    private void doDetectBlockAround(Dog dog) {
        var dog_b0 = dog.blockPosition();
        int radius = getDetectRadius(dog);
        int start = 0;
        while (start + 2 <= radius) {
            for (int i = 0; i < 10; ++i) {
                var r = dog.getRandom();
                int offSmall = 2;
                int offLarge = start + 2;
                int r1 = EntityUtil.getRandomNumber(dog, -offLarge, offLarge);
                int r2 = EntityUtil.getRandomNumber(dog, -offSmall, offSmall);
                r2 += Mth.sign(r2)*start;
                int randXOff = r1;
                int randZOff = r2;
                if (r.nextBoolean()) {
                    randXOff = r2;
                    randZOff = r1;
                }
                int randYOff = EntityUtil.getRandomNumber(dog, -3, 3);
                var rand_b0 = dog_b0.offset(new Vec3i(randXOff, randYOff, randZOff));
                var state = dog.level().getBlockState(rand_b0);
                if (state.getBlock() == this.detectingBlock) {
                    suspiciousPos = Optional.of(rand_b0);
                    //this.tickTillDetect = 100;
                    return;
                }
            }
            start += 2;
        }
    }

    private int getDetectRadius(Dog dog) {
        if (this.level() >= 5)
            return 28;
        if (this.level() < 0)
            return 0;
        switch (this.level()) {
        case 1:
            return 6;
        case 2:
            return 8;
        case 3:
            return 16;
        case 4:
            return 22;
        default:
            return 8;
        }
    }

    // private int getDetectInterval(Dog dog) {
    //     return Math.max(20, 100 - (this.level() - 1) * 20);
    // }

    public void setDetectBlock(Block block) {
        this.detectingBlock = block;
        if (block == null || block instanceof AirBlock)
            this.detectingBlock = Blocks.AIR;
    }

    public void clearDetectBlock() {
        this.detectingBlock = Blocks.AIR;
    }

    @Override
    public InteractionResult processInteract(AbstractDog dog, Level worldIn, Player playerIn,
            InteractionHand handIn) {
        var stack = playerIn.getItemInHand(handIn);
        if (!stack.is(DoggyItems.SCENT_TREAT.get()))
            return InteractionResult.PASS;

        if (dog.level().isClientSide)
            return InteractionResult.SUCCESS;

        if (playerIn.isShiftKeyDown()) {
            if (this.detectingBlock == null)
                this.detectingBlock = Blocks.AIR;
            var c1 = this.detectingBlock == Blocks.AIR ? 
            ComponentUtil.translatable("talent.doggytalents.sniffer_dog.detecting_block_status.none")
            : ComponentUtil.translatable("talent.doggytalents.sniffer_dog.detecting_block_status",
                dog.getName().getString(),
                ComponentUtil.translatable(this.detectingBlock.asItem().getDescriptionId()).withStyle(
                    Style.EMPTY.withItalic(true)
                )
            );
            playerIn.sendMessage(c1, Util.NIL_UUID);
            return InteractionResult.SUCCESS;
        }

        var tag = stack.getOrCreateTag();
        if (!tag.contains(ScentTreatItem.SCENT_BLOCK_ID)) {
            this.clearDetectBlock();
        } else {
            var block = NBTUtil.getRegistryValue(tag, ScentTreatItem.SCENT_BLOCK_ID, ForgeRegistries.BLOCKS);
            if (block == null)
                return InteractionResult.SUCCESS;
            
            this.setDetectBlock(block);
        }
        
        dog.playSound(
            SoundEvents.INK_SAC_USE, 1f, 1f
        );

        var retItem = new ItemStack(DoggyItems.DROOL_SCENT_TREAT.get());
        if (stack.hasTag()) {
            retItem.setTag(stack.getTag().copy());
        }
        dog.spawnAtLocation(retItem);

        stack.shrink(1);

        return InteractionResult.SUCCESS;
    }

    public void validateSuspiciousPos(Dog dog) {
        if (!this.suspiciousPos.isPresent())
            return;
        var pos = this.suspiciousPos.get();
        var state = dog.level().getBlockState(pos);
        if (!state.is(detectingBlock))
            this.suspiciousPos = Optional.empty(); 
    }

    public void notifyOwner(Dog dog) {
        var owner = dog.getOwner();
        if (owner == null)
            return;
        if (owner.distanceToSqr(dog) > 64)
            return;
        if (!dog.canDoIdileAnim())
            return;
        dog.triggerAction(new DogGetOwnerAttentionAndInformAction(dog, owner, this));
    }

    @Override
    public void writeToNBT(AbstractDog dogIn, CompoundTag compound) {
        super.writeToNBT(dogIn, compound);
        if (this.detectingBlock == null)
            this.detectingBlock = Blocks.AIR;
        var id = ForgeRegistries.BLOCKS.getKey(this.detectingBlock);
        NBTUtil.putResourceLocation(compound, "snifferDog_detectingBlock", id);
    }

    @Override
    public void readFromNBT(AbstractDog dogIn, CompoundTag compound) {
        super.readFromNBT(dogIn, compound);
        var block = NBTUtil.getRegistryValue(compound, "snifferDog_detectingBlock", ForgeRegistries.BLOCKS);
        if (block == null)
            block = Blocks.AIR;
        this.detectingBlock = block;
    }

    public static class DogGetOwnerAttentionAndInformAction extends TriggerableAction {

        private SnifferDogTalent inst;
        private LivingEntity owner;
        private boolean isPointingToPos = false;
        private int timeOutTick = 0;
        private boolean notifiedOwner = false;
        private BlockPos targetPos;
        private int tickLookLeft = 0;
        private int tickAnim = 0;
        private DogAnimation currentAnimation = DogAnimation.SNIFFER_DOG_POINT_STRAIGHT;

        public DogGetOwnerAttentionAndInformAction(Dog dog, LivingEntity owner, SnifferDogTalent inst) {
            super(dog, false, false);
            this.inst = inst;
            this.owner = owner;
        }

        @Override
        public void onStart() {
            isPointingToPos = false;
            notifiedOwner = false;
            timeOutTick = 20 * 20 + dog.getRandom().nextInt(6) * 20;
            this.targetPos = inst.suspiciousPos.orElse(null);
            tickLookLeft = 10;
            tickAnim = 0;
        }

        @Override
        public void tick() {
            if (!this.owner.isAlive() || this.owner.isSpectator()) {
                this.setState(ActionState.FINISHED);
                return;
            }

            if (owner.distanceToSqr(dog) > 144) {
                this.setState(ActionState.FINISHED);
                return;
            }

            invalidateTargetPos();

            if (this.targetPos == null) {
                this.setState(ActionState.FINISHED);
                return;
            }

            if (!this.isPointingToPos) {
                --this.timeOutTick;
                if (timeOutTick <= 0) {
                    this.setState(ActionState.FINISHED);
                    return;
                }
            } else {
                boolean anim_end_or_interupted = 
                    this.tickAnim >= currentAnimation.getLengthTicks()
                    || this.dog.getAnim() != this.currentAnimation
                    || !this.dog.onGround();

                if (this.tickLookLeft <= 0 && anim_end_or_interupted) {
                    this.setState(ActionState.FINISHED);
                    return;
                }
            }

            if (this.isPointingToPos)
                pointsTowardThePos();
            else
                getOwnerAttention();
        }

        private void invalidateTargetPos() {
            if (this.targetPos == null)
                return;
            if (!this.inst.suspiciousPos.isPresent()) {
                this.targetPos = null;
                return;
            } 
            if (!this.inst.suspiciousPos.get().equals(targetPos)) {
                this.targetPos = null;
                return;
            }
        }

        private void getOwnerAttention() {
            if (this.targetPos == null)
                return;
            this.dog.getLookControl().setLookAt(owner);
            if (!notifiedOwner) {
                notifiedOwner = true;
                notifyOwner(dog, owner, dog.distanceToSqr(Vec3.atCenterOf(targetPos)));
                this.moveBackIfNeeded(dog, targetPos);
            }
            if (dog.distanceToSqr(owner) > 16)
                return;
            if (!checkIfCanSeeOwner(dog, owner)) {
                return;
            }
            if (checkIfOwnerIsLooking(dog, owner)) {
                this.isPointingToPos = true;
            }
        }

        private void pointsTowardThePos() {
            if (this.targetPos == null)
                return;
            this.dog.getLookControl().setLookAt(Vec3.atCenterOf(targetPos));
            if (this.tickLookLeft > 0) {
                --this.tickLookLeft;
                if (this.tickLookLeft <= 0) {
                    this.currentAnimation = pickAnim(dog, targetPos);
                    this.dog.setAnim(currentAnimation);
                }
                return;
            }
            ++tickAnim;
            
        }

        private void moveBackIfNeeded(Dog dog, BlockPos pos) {
            var v_dog_pos = Vec3.atBottomCenterOf(pos)
                .subtract(dog.position());
            var v_dog_pos_xz = new Vec3(v_dog_pos.x, 0, v_dog_pos.z)
                .normalize();
            var wantedStandPos = Vec3.atBottomCenterOf(dog.blockPosition())
                .subtract(v_dog_pos_xz.scale(0.5));
            dog.getMoveControl().setWantedPosition(wantedStandPos.x, wantedStandPos.y, wantedStandPos.z, 0.5); 
        }

        private DogAnimation pickAnim(Dog dog, BlockPos pos) {
            var v_dog_pos = Vec3.atCenterOf(pos)
                .subtract(dog.getEyePosition());
            // double l_xz = Mth.sqrt((float)(v_dog_pos.x*v_dog_pos.x + v_dog_pos.z*v_dog_pos.z));
            // double dy_abs = Mth.abs((float)v_dog_pos.y);
            // double angle = Mth.atan2(dy_abs, l_xz) * Mth.RAD_TO_DEG;
            // if (angle < 45)
            //     return DogAnimation.SNIFFER_DOG_POINT_STRAIGHT;
            // return v_dog_pos.y < 0 ? 
            //     DogAnimation.SNIFFER_DOG_POINT_DOWNARD
            //     : DogAnimation.SNIFFER_DOG_POINT_UPWARD;
            if (v_dog_pos.y < -1)
                return DogAnimation.SNIFFER_DOG_POINT_DOWNARD;
            if (v_dog_pos.y > 1)
                return DogAnimation.SNIFFER_DOG_POINT_UPWARD;
            return DogAnimation.SNIFFER_DOG_POINT_STRAIGHT;
        }

        private void notifyOwner(Dog dog, LivingEntity owner, double distanceAwaySqr) {
            var c1 = ComponentUtil.translatable(getStringStatus(dog, distanceAwaySqr),
                dog.getName().getString());
            owner.sendMessage(c1, Util.NIL_UUID);
            dog.playSound(SoundEvents.WOLF_AMBIENT, 1f, 1.5f);
        }

        private String getStringStatus(Dog dog, double distanceAwaySqr) {
            int r = dog.getRandom().nextInt(3);
            var posfix = ".mid";
            if (distanceAwaySqr > 16 * 16) 
                posfix = ".far";
            else if (distanceAwaySqr <= 8 * 8)
                posfix = ".near";
            return "talent.doggytalents.sniffer_dog.notify_owner." + r + posfix;
        }

        private boolean checkIfCanSeeOwner(Dog dog, LivingEntity owner) {
            if (dog.tickCount % 5 != 0)
                return false;
            return dog.hasLineOfSight(owner);
        }

        private boolean checkIfOwnerIsLooking(Dog dog, LivingEntity owner) {
            var v_look_owner = owner.getViewVector(1);
            var v_look_wanted = dog.getEyePosition()
                .subtract(owner.getEyePosition())
                .normalize();
            var dot = v_look_wanted.dot(v_look_owner);
            return dot > 0.7;
        }

        @Override
        public void onStop() {
            this.inst.suspiciousPos = Optional.empty();
            this.inst.dontNotifyOwnerAgainTick = (5 + dog.getRandom().nextInt(10)) * 20;
        }
        
    }

}
