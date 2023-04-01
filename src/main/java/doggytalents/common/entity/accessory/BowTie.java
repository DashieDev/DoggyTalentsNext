package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.DoggyTalentsNext;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.api.registry.AccessoryType;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import doggytalents.common.util.ColourCache;
import doggytalents.common.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class BowTie extends DyeableAccessory implements IAccessoryHasModel {

    public BowTie(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.BOWTIE, itemIn);
    }
    
    @Override
    public byte getRenderLayer() {
        return AccessoryInstance.RENDER_TOP;
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.BOWTIE;
    }

}
