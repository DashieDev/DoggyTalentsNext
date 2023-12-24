package doggytalents.common.talent;

import doggytalents.DoggyTalents;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.inventory.PackPuppyItemHandler;
import doggytalents.common.network.packet.data.DoggyTorchPlacingTorchData;
import doggytalents.common.util.InventoryUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.block.BlockState;
import org.apache.commons.lang3.tuple.Pair;

public class DoggyTorchTalent extends TalentInstance {

    private boolean placingTorch = true;

    public DoggyTorchTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void tick(AbstractDog dogIn) {
        if (placingTorch && dogIn.tickCount % 10 == 0 && dogIn.isTame()) {

            BlockPos pos = dogIn.blockPosition();
            BlockState torchState = Blocks.TORCH.defaultBlockState();

            if (dogIn.level.getMaxLocalRawBrightness(dogIn.blockPosition()) < 8 && dogIn.level.isEmptyBlock(pos) && torchState.canSurvive(dogIn.level, pos)) {
                PackPuppyItemHandler inventory = dogIn.getTalent(DoggyTalents.PACK_PUPPY)
                    .map((inst) -> inst.cast(PackPuppyTalent.class).inventory()).orElse(null);

                // If null might be because no pack puppy
                if (this.level() >= 5) {
                    dogIn.level.setBlockAndUpdate(pos, torchState);
                }
                else if (inventory != null) { // If null might be because no pack puppy
                    Pair<ItemStack, Integer> foundDetails = InventoryUtil.findStack(inventory, (stack) -> stack.getItem() == Items.TORCH);
                    if (foundDetails != null && !foundDetails.getLeft().isEmpty()) {
                        ItemStack torchStack = foundDetails.getLeft();
                        dogIn.consumeItemFromStack(dogIn, torchStack);
                        inventory.setStackInSlot(foundDetails.getRight(), torchStack);
                        dogIn.level.setBlockAndUpdate(pos, torchState);
                    }
                }
            }
        }
    }

    @Override
    public void onRead(AbstractDog dogIn, CompoundTag compound) {
        this.placingTorch = compound.getBoolean("DoggyTorchTalent_placingTorch");
    }

    @Override
    public void onWrite(AbstractDog dogIn, CompoundTag compound) {
        compound.putBoolean("DoggyTorchTalent_placingTorch", placingTorch);
    }

    @Override
    public void writeToBuf(PacketBuffer buf) {
        super.writeToBuf(buf);
        buf.writeBoolean(placingTorch);
    }

    @Override
    public void readFromBuf(PacketBuffer buf) {
        super.readFromBuf(buf);
        placingTorch = buf.readBoolean();
    }

    public void updateFromPacket(DoggyTorchPlacingTorchData data) {
        placingTorch = data.val;
    }

    @Override
    public TalentInstance copy() {
        var ret = super.copy();
        if (!(ret instanceof DoggyTorchTalent torch))
            return ret;
        torch.setPlacingTorch(this.placingTorch);
        return torch;
    }

    public boolean placingTorch() { return this.placingTorch; }
    public void setPlacingTorch(boolean torch) { this.placingTorch = torch; }
    
}
