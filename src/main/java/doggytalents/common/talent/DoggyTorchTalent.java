package doggytalents.common.talent;

import doggytalents.DoggyTalents;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.inventory.PackPuppyItemHandler;
import doggytalents.common.network.packet.data.DoggyTorchData;
import doggytalents.common.util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.Pair;

public class DoggyTorchTalent extends TalentInstance {

    private boolean placingTorch = true;
    private boolean renderTorch = true;

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

    public boolean canRenderTorch() {
        return this.level() >= 5;
    }

    @Override
    public void onRead(AbstractDog dogIn, CompoundTag compound) {
        this.placingTorch = compound.getBoolean("DoggyTorchTalent_placingTorch");
        this.renderTorch = compound.getBoolean("DoggyTorchTalent_renderTorch");
    }

    @Override
    public void onWrite(AbstractDog dogIn, CompoundTag compound) {
        compound.putBoolean("DoggyTorchTalent_placingTorch", placingTorch);
        compound.putBoolean("DoggyTorchTalent_renderTorch", renderTorch);
    }

    @Override
    public void writeToBuf(FriendlyByteBuf buf) {
        super.writeToBuf(buf);
        buf.writeBoolean(placingTorch);
        buf.writeBoolean(renderTorch);
    }

    @Override
    public void readFromBuf(FriendlyByteBuf buf) {
        super.readFromBuf(buf);
        placingTorch = buf.readBoolean();
        renderTorch = buf.readBoolean();
    }

    @Override
    public void updateOptionsFromServer(TalentInstance fromServer) {
        if (!(fromServer instanceof DoggyTorchTalent torch))
            return;
        this.setPlacingTorch(torch.placingTorch);
        this.setRenderTorch(torch.renderTorch);
    }

    public void updateFromPacket(DoggyTorchData data) {
        switch (data.type) {
        case ALLOW_PLACING:
            this.placingTorch = data.val;
            break;
        case RENDER_TORCH:
            this.renderTorch = data.val;
            break;
        default:
            break;   
        }
    }

    @Override
    public TalentInstance copy() {
        var ret = super.copy();
        if (!(ret instanceof DoggyTorchTalent torch))
            return ret;
        torch.setPlacingTorch(this.placingTorch);
        torch.setRenderTorch(this.renderTorch);
        return torch;
    }

    public boolean placingTorch() { return this.placingTorch; }
    public void setPlacingTorch(boolean torch) { this.placingTorch = torch; }

    public boolean renderTorch() { return this.renderTorch; }
    public void setRenderTorch(boolean torch) { this.renderTorch = torch; }    
    
}
