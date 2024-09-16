package doggytalents.common.talent;

import doggytalents.TalentsOptions;
import doggytalents.DoggyTalents;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.TalentOption;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.inventory.PackPuppyItemHandler;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collection;
import java.util.List;

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

            if (dogIn.level().getMaxLocalRawBrightness(dogIn.blockPosition()) < 8 && dogIn.level().isEmptyBlock(pos) && torchState.canSurvive(dogIn.level(), pos)) {
                PackPuppyItemHandler inventory = dogIn.getTalent(DoggyTalents.PACK_PUPPY)
                    .map((inst) -> inst.cast(PackPuppyTalent.class).inventory()).orElse(null);

                // If null might be because no pack puppy
                if (this.level() >= 5) {
                    dogIn.level().setBlockAndUpdate(pos, torchState);
                }
                else if (inventory != null) { // If null might be because no pack puppy
                    Pair<ItemStack, Integer> foundDetails = InventoryUtil.findStack(inventory, (stack) -> stack.getItem() == Items.TORCH);
                    if (foundDetails != null && !foundDetails.getLeft().isEmpty()) {
                        ItemStack torchStack = foundDetails.getLeft();
                        dogIn.consumeItemFromStack(dogIn, torchStack);
                        inventory.setStackInSlot(foundDetails.getRight(), torchStack);
                        dogIn.level().setBlockAndUpdate(pos, torchState);
                    }
                }
            }
        }
    }

    public boolean canRenderTorch() {
        return this.level() >= 5;
    }

    @Override
    public void readFromNBT(AbstractDog dogIn, CompoundTag compound) {
        super.readFromNBT(dogIn, compound);

        this.placingTorch = compound.getBoolean("placingTorch");
        this.renderTorch = compound.getBoolean("renderTorch");
    }

    @Override
    public void writeToNBT(AbstractDog dogIn, CompoundTag compound) {
        super.writeToNBT(dogIn, compound);
        compound.putBoolean("placingTorch", placingTorch);
        compound.putBoolean("renderTorch", renderTorch);
    }

    @Override
    public Object getTalentOption(TalentOption<?> entry) {
        if (entry == TalentsOptions.DOGGY_TORCH_ENABLE.get()) {
            return this.placingTorch;
        }
        if (entry == TalentsOptions.DOGGY_TORCH_RENDER.get()) {
            return this.renderTorch;
        }
        return null;
    }

    @Override
    public void setTalentOption(TalentOption<?> entry, Object data) {
        if (entry == TalentsOptions.DOGGY_TORCH_ENABLE.get()) {
            this.placingTorch = (Boolean) data;
        }
        if (entry == TalentsOptions.DOGGY_TORCH_RENDER.get()) {
            this.renderTorch = (Boolean) data;
        }
    }

    @Override
    public Collection<TalentOption<?>> getAllTalentOptions() {
        return List.of(TalentsOptions.DOGGY_TORCH_ENABLE.get(), TalentsOptions.DOGGY_TORCH_RENDER.get());
    }

    public boolean placingTorch() { return this.placingTorch; }
    public void setPlacingTorch(boolean torch) { this.placingTorch = torch; }

    public boolean renderTorch() { return this.renderTorch; }
    public void setRenderTorch(boolean torch) { this.renderTorch = torch; }    
    
}
