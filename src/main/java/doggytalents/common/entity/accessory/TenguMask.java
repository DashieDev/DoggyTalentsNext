package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class TenguMask extends Glasses implements IAccessoryHasModel {

    public TenguMask(Supplier<? extends ItemLike> itemIn) {
        super(itemIn);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.TENGU_MASK;
    }

    @Override
    public AccessoryInstance getDefault() {
        return new Inst(this);
    }

    public static class Inst extends AccessoryInstance implements IDogAlteration {

        public boolean unwear = false;

        public Inst(Accessory typeIn) {
            super(typeIn);
        }

        @Override
        public InteractionResult processInteract(AbstractDog dogIn, Level worldIn, Player playerIn,
                InteractionHand handIn) {

            if (dogIn.level().isClientSide)
            if (playerIn.getItemInHand(handIn).getItem() == Items.BLAZE_ROD) {
                unwear = !unwear;
            }

            return InteractionResult.PASS;
        }

    }
    
}
