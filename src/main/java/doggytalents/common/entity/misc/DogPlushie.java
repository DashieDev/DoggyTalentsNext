package doggytalents.common.entity.misc;

import doggytalents.DoggyItems;
import doggytalents.common.util.ItemUtil;
import doggytalents.common.util.NetworkUtil;
import doggytalents.common.variant.DogVariant;
import doggytalents.common.variant.util.DogVariantUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class DogPlushie extends BaseDogPlushie implements IEntityAdditionalSpawnData {

    private int collarCollor = 11546150;
    private DogVariant variant = DogVariantUtil.getDefault();
    private boolean collarThicc = false;

    public DogPlushie(EntityType<?> type, Level level) {
        super(type, level);
    }
        
    public void setCollarColor(int val) {
        this.collarCollor = val;
    }

    public int getCollarColor() {
        return this.collarCollor;
    }

    public void setDogVariant(DogVariant variant) {
        this.variant = variant;
    }

    public DogVariant getDogVariant() {
        return this.variant;
    }

    public void setCollarThicc(boolean val) {
        this.collarThicc = val;
    }

    public boolean getCollarThicc() {
        return this.collarThicc;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("PlushCollarColor", Tag.TAG_INT))
            this.setCollarColor(compound.getInt("PlushCollarColor"));
        if (compound.contains("classicalVariant", Tag.TAG_STRING)) {
            this.setDogVariant(DogVariantUtil.fromSaveString(
                compound.getString("classicalVariant")
            ));
        }
        this.setCollarThicc(compound.getBoolean("collarThicc"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("PlushCollarColor", this.getCollarColor());
        compound.putString("classicalVariant", 
            DogVariantUtil.toSaveString(this.getDogVariant()));
        compound.putBoolean("collarThicc", this.getCollarThicc());
    }

    @Override
    public ItemStack getDogPlusieItemDrop() {
        var item = DoggyItems.DOG_PLUSHIE_TOY.get();
        var stack = new ItemStack(item);
        item.setColor(stack, this.getCollarColor());
        return stack;
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buf) {
        buf.writeInt(getCollarColor());
        NetworkUtil.writeDogVariantToBuf(buf, variant);
        buf.writeBoolean(getCollarThicc());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buf) {
        int collar_color = buf.readInt();
        var variant = NetworkUtil.readDogVariantFromBuf(buf);
        boolean thicc = buf.readBoolean();
        this.setCollarColor(collar_color);
        this.setDogVariant(variant);
        this.setCollarThicc(thicc);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
