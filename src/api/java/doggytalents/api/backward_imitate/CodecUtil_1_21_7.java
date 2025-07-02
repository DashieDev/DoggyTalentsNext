package doggytalents.api.backward_imitate;

import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.world.item.ItemStack;

public class CodecUtil_1_21_7 {
    
    public static Tag saveItemStack(ItemStack stack, HolderLookup.Provider prov, CompoundTag prefix) {
        var encode_ctx = prov.createSerializationContext(NbtOps.INSTANCE);
        return 
            ItemStack.CODEC.encode(stack, encode_ctx, prefix)
                .result().orElse(new CompoundTag());
    }

    public static Optional<ItemStack> parseItemStack(HolderLookup.Provider prov, CompoundTag compound) {
        var decode_ctx = prov.createSerializationContext(NbtOps.INSTANCE);
        return ItemStack.CODEC.parse(decode_ctx, compound).result();
    }

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    public static String encodeComponentToStr(Component component, HolderLookup.Provider prov) {
        var encode_ctx = prov.createSerializationContext(JsonOps.INSTANCE);
        return ComponentSerialization.CODEC.encodeStart(encode_ctx, component).result()
            .map(x -> GSON.toJson(x)).orElse("");
    }

    public static Component parseComponentFromStr(String str, HolderLookup.Provider prov) {
        var json = JsonParser.parseString(str);
        if (json == null)
            return Component.empty();
        var decode_ctx = prov.createSerializationContext(JsonOps.INSTANCE);
        return ComponentSerialization.CODEC.parse(decode_ctx, json)
            .result().orElse(Component.empty());
    }

}
