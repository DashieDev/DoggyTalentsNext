package doggytalents.api.registry;

import java.util.Objects;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.api.DoggyTalentsAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.ItemLike;
import net.minecraft.Util;

public class Accessory {

    @Nullable
    private String translationKey;
    @Deprecated // Do not call directly use Accessory#getReturnItem
    private final Supplier<ItemStack> stack;

    @Deprecated // Do not call directly use Accessory#getType
    private final Supplier<? extends AccessoryType> type;

    public Accessory(Supplier<? extends AccessoryType> typeIn, Supplier<ItemStack> stackIn, int x) {
        this.type = typeIn;
        this.stack = stackIn;
    }

    public Accessory(Supplier<? extends AccessoryType> typeIn, Supplier<? extends ItemLike> itemIn) {
        this(typeIn, () -> new ItemStack(itemIn.get()), 0);
    }

    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("accessory", DoggyTalentsAPI.ACCESSORIES.get().getKey(this));
        }
        return this.translationKey;
    }

    public byte getRenderLayer() {
        return AccessoryInstance.RENDER_DEFAULT;
    }

    public AccessoryInstance getDefault() {
        return new AccessoryInstance(this);
    }

    public AccessoryInstance createInstance(FriendlyByteBuf buf) {
        return this.getDefault();
    }

    public AccessoryInstance read(CompoundTag compound) {
        return this.getDefault();
    }

    public void write(AccessoryInstance instance, FriendlyByteBuf buf) {

    }

    public void write(AccessoryInstance instance, CompoundTag compound) {

    }

    public AccessoryInstance createFromStack(ItemStack stackIn) {
        return this.getDefault();
    }

    public ItemStack getReturnItem(AccessoryInstance instance) {
        return this.stack.get();
    }

    public final AccessoryType getType() {
        return this.type.get();
    }
    

    public <T extends Accessory> boolean of(Supplier<T> accessoryIn) {
        return this.of(accessoryIn.get());
    }

    public <T extends Accessory> boolean of(T accessoryIn) {
        //return this.of( () -> accessoryIn);
        return accessoryIn == this;
    }
    //TODO !!!!!!!!!!!!!!!

    // public <T extends Accessory> boolean of(T accessoryDelegateIn) {
    //     return accessoryDelegateIn.equals(this.delegate);
    // }

    private ResourceLocation modelTexture;

    public <T extends Accessory> T setModelTexture(ResourceLocation modelTextureIn) {
        this.modelTexture = modelTextureIn;
        return (T) this;
    }

    @Nullable
    public ResourceLocation getModelTexture() {
        return this.modelTexture;
    }

    private boolean renderTranslucent = false;

    public boolean renderTranslucent() {
        return renderTranslucent;
    }

    public <T extends Accessory> T setRenderTranslucent(boolean translucent) {
        this.renderTranslucent = translucent;
        return (T) this;
    }

    private boolean hasHindLegDiffTex = false;

    public boolean hasHindLegDiffTex() {
        return hasHindLegDiffTex;
    }

    public <T extends Accessory> T setHasHindLegDiffTex(boolean val) {
        this.hasHindLegDiffTex = val;
        return (T) this;
    }

    public static enum AccessoryRenderType { OVERLAY, MODEL, OVERLAY_AND_MODEL, INCAPACITATED }
    private AccessoryRenderType accessoryRenderType = AccessoryRenderType.OVERLAY;
    
    public <T extends Accessory> T setAccessoryRenderType(AccessoryRenderType type) {
        this.accessoryRenderType = type;
        return (T) this;
    }

    public AccessoryRenderType getAccessoryRenderType() {
        return this.accessoryRenderType;
    }

}
