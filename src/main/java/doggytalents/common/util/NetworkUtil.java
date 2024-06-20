package doggytalents.common.util;

import doggytalents.DoggyRegistries;
import doggytalents.DoggyTalents;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.Talent;
import doggytalents.common.variant.DogVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;

public class NetworkUtil {
    
    public static StreamCodec
        <RegistryFriendlyByteBuf, Talent> 
        TALENT_STREAM_CODEC = ByteBufCodecs.registry(DoggyRegistries.Keys.TALENTS_REGISTRY);

    public static StreamCodec
        <RegistryFriendlyByteBuf, Accessory> 
        ACCESSORY_CODEC = ByteBufCodecs.registry(DoggyRegistries.Keys.ACCESSORIES_REGISTRY);

    public static StreamCodec
        <RegistryFriendlyByteBuf, DogVariant> 
        DOG_VARIANT_CODEC = ByteBufCodecs.registry(DoggyRegistries.Keys.DOG_VARIANT);
    

    public static void writeTalentToBuf(FriendlyByteBuf buf, Talent val) {
        var reg_buf = (RegistryFriendlyByteBuf) buf;
        TALENT_STREAM_CODEC.encode(reg_buf, val);
    }

    public static Talent readTalentFromBuf(FriendlyByteBuf buf) {
        var reg_buf = (RegistryFriendlyByteBuf) buf;
        return TALENT_STREAM_CODEC.decode(reg_buf);
    }

    public static void writeAccessoryToBuf(FriendlyByteBuf buf, Accessory val) {
        var reg_buf = (RegistryFriendlyByteBuf) buf;
        ACCESSORY_CODEC.encode(reg_buf, val);
    }

    public static Accessory readAccessoryFromBuf(FriendlyByteBuf buf) {
        var reg_buf = (RegistryFriendlyByteBuf) buf;
        return ACCESSORY_CODEC.decode(reg_buf);
    }

    public static void writeItemToBuf(FriendlyByteBuf buf, ItemStack stack) {
        var reg_buf = (RegistryFriendlyByteBuf) buf;
        ItemStack.OPTIONAL_STREAM_CODEC.encode(reg_buf, stack);
    }

    public static ItemStack readItemFromBuf(FriendlyByteBuf buf) {  
        var reg_buf = (RegistryFriendlyByteBuf) buf;
        return ItemStack.OPTIONAL_STREAM_CODEC.decode(reg_buf);
    }

    public static <T> void writeRegistryId(FriendlyByteBuf buf, ResourceKey<Registry<T>> regKey, T val) {
        var regBuf = (RegistryFriendlyByteBuf) buf;
        var reg = regBuf.registryAccess().registryOrThrow(regKey);
        int id = reg.getIdOrThrow(val);
        regBuf.writeInt(id);
    }

    public static <T> T readRegistryId(FriendlyByteBuf buf, ResourceKey<Registry<T>> regKey) {
        var regBuf = (RegistryFriendlyByteBuf) buf;
        var reg = regBuf.registryAccess().registryOrThrow(regKey);
        int id = regBuf.readInt();
        return reg.byIdOrThrow(id);
    }

    public static void writeDogVariantToBuf(FriendlyByteBuf buf, DogVariant val) {
        var reg_buf = (RegistryFriendlyByteBuf) buf;
        DOG_VARIANT_CODEC.encode(reg_buf, val);
    }

    public static DogVariant readDogVariantFromBuf(FriendlyByteBuf buf) {
        var reg_buf = (RegistryFriendlyByteBuf) buf;
        return DOG_VARIANT_CODEC.decode(reg_buf);
    }

}
