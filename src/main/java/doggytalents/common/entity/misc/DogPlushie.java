package doggytalents.common.entity.misc;

import doggytalents.DoggyItems;
import doggytalents.common.util.ItemUtil;
import doggytalents.common.util.NetworkUtil;
import doggytalents.common.variant.DogVariant;
import doggytalents.common.variant.util.DogVariantUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;

public class DogPlushie extends BaseDogPlushie implements IEntityWithComplexSpawn {

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
    protected void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput input) {
        if (input.getInt("PlushCollarColor").isPresent())
            this.setCollarColor(input.getIntOr("PlushCollarColor", 0));
        input.getString("classicalVariant").ifPresent(s ->
            this.setDogVariant(DogVariantUtil.fromSaveString(s))
        );
        this.setCollarThicc(input.getBooleanOr("collarThicc", false));
    }

    @Override
    protected void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput output) {
        output.putInt("PlushCollarColor", this.getCollarColor());
        output.putString("classicalVariant", DogVariantUtil.toSaveString(this.getDogVariant()));
        output.putBoolean("collarThicc", this.getCollarThicc());
    }

    @Override
    public ItemStack getDogPlusieItemDrop() {
        var item = DoggyItems.DOG_PLUSHIE_TOY.get();
        var stack = new ItemStack(item);
        ItemUtil.setDyeColorForStack(stack, this.getCollarColor());
        return stack;
    }

    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf buf) {
        buf.writeInt(getCollarColor());
        NetworkUtil.writeDogVariantToBuf(buf, variant);
        buf.writeBoolean(getCollarThicc());
    }

    @Override
    public void readSpawnData(RegistryFriendlyByteBuf buf) {
        int collar_color = buf.readInt();
        var variant = NetworkUtil.readDogVariantFromBuf(buf);
        boolean thicc = buf.readBoolean();
        this.setCollarColor(collar_color);
        this.setDogVariant(variant);
        this.setCollarThicc(thicc);
    }
}
