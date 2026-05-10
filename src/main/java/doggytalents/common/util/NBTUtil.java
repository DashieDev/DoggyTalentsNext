package doggytalents.common.util;

import doggytalents.DoggyTalentsNext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;

import java.util.Optional;
import java.util.UUID;

public class NBTUtil {

    /**
     * Writes the UUID to the CompoundNBT under the given key if it is not null
     */
    public static void putUniqueId(CompoundTag compound, String key, @Nullable UUID uuid) {
        if (uuid != null) {
            compound.putIntArray(key, UUIDUtil.uuidToIntArray(uuid));
        }
    }

    public static boolean hasUniqueId(CompoundTag compound, String key) {
        return compound.getIntArray(key).map(arr -> arr.length == 4).orElse(false);
    }

    /**
     * Reads the UUID from the CompoundNBT if it exists returns null otherwise
     */
    @Nullable
    public static UUID getUniqueId(CompoundTag compound, String key) {
        var arr = compound.getIntArray(key).orElse(null);
        if (arr != null && arr.length == 4) {
            return UUIDUtil.uuidFromIntArray(arr);
        } else if (NBTUtil.hasOldUniqueId(compound, key)) {
            return NBTUtil.getOldUniqueId(compound, key);
        }

        return null;
    }

    public static UUID getOldUniqueId(CompoundTag compound, String key) {
        return new UUID(compound.getLongOr(key + "Most", 0L), compound.getLongOr(key + "Least", 0L));
    }

    public static boolean hasOldUniqueId(CompoundTag compound, String key) {
        return compound.contains(key + "Most") && compound.contains(key + "Least");
    }

    public static void removeOldUniqueId(CompoundTag compound, String key) {
        compound.remove(key + "Most");
        compound.remove(key + "Least");
    }

    public static void putResourceLocation(CompoundTag compound, String key, @Nullable Identifier rl) {
        if (rl != null) {
            compound.putString(key, rl.toString());
        }
    }

    @Nullable
    public static Identifier getResourceLocation(CompoundTag compound, String key) {
        if (compound.contains(key)) {
            return Identifier.tryParse(compound.getStringOr(key, ""));
        }

        return null;
    }

    public static void putVector3d(CompoundTag compound, @Nullable Vec3 vec3d) {
        if (vec3d != null) {
            compound.putDouble("x", vec3d.x());
            compound.putDouble("y", vec3d.y());
            compound.putDouble("z", vec3d.z());
        }
    }

    @Nullable
    public static Vec3 getVector3d(CompoundTag compound) {
        if (compound.contains("x") && compound.contains("y") && compound.contains("z")) {
            return new Vec3(compound.getDoubleOr("x", 0.0), compound.getDoubleOr("y", 0.0), compound.getDoubleOr("z", 0.0));
        }

        return null;
    }


    public static void putTextComponent(CompoundTag compound, String key, @Nullable Component component) {
        if (component != null) {
            compound.putString(key, serializeComponentToJsonStr(component));
        }
    }

    @Nullable
    public static Component getTextComponent(CompoundTag compound, String key) {

        if (compound.contains(key)) {
            return parseComponentJsonStr(compound.getStringOr(key, ""));
        }

        return null;
    }

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    private static Component parseComponentJsonStr(String str) {
        var c1_json = JsonParser.parseString(str);
        if (c1_json == null)
            return Component.empty();
        Component ret = null;
        try {
            ret = ComponentSerialization.CODEC.parse(JsonOps.INSTANCE, c1_json)
                .getOrThrow();
        } catch (Exception e) {
            ret = Component.empty();
        }
        if (ret == null)
            ret = Component.empty();
        return ret;
    }

    private static String serializeComponentToJsonStr(Component c1) {
        if (c1 == null)
            return "";
        JsonElement c1_json = null;
        try {
            c1_json = ComponentSerialization.CODEC.encodeStart(JsonOps.INSTANCE, c1)
                .getOrThrow();
        } catch (Exception e) {
            c1_json = null;
        }
        return c1_json != null ? GSON.toJson(c1_json) : "";
    }

    @Nullable
    public static <T> T getRegistryValue(CompoundTag compound, String key, Registry<T> registry) {
        Identifier rl = NBTUtil.getResourceLocation(compound, key);
        if (rl != null) {
            if (registry.containsKey(rl)) {
                return registry.get(rl).map(net.minecraft.core.Holder.Reference::value).orElse(null);
            } else {
                DoggyTalentsNext.LOGGER.warn("Unable to load registry value in registry {} with resource location {}", registry.key(), rl);
            }
        } else {
            DoggyTalentsNext.LOGGER.warn("Unable to load resource location in NBT:{}, for {} registry", key, registry.key());
        }

        return null;
    }

    public static void putRegistryValue(CompoundTag compound, String key, Identifier value) {
        if (value != null) {
            NBTUtil.putResourceLocation(compound, key, value);
        }
    }

    public static void putBlockPos(CompoundTag compound, @Nullable BlockPos vec3d) {
        if (vec3d != null) {
            compound.putInt("x", vec3d.getX());
            compound.putInt("y", vec3d.getY());
            compound.putInt("z", vec3d.getZ());
        }
    }

    @Nullable
    public static BlockPos getBlockPos(CompoundTag compound) {
        if (compound.contains("x") && compound.contains("y") && compound.contains("z")) {
            return new BlockPos(compound.getIntOr("x", 0), compound.getIntOr("y", 0), compound.getIntOr("z", 0));
        }

        return null;
    }


    public static void putBlockPos(CompoundTag compound, String key, Optional<BlockPos> vec3d) {
        if (vec3d.isPresent()) {
            CompoundTag posNBT = new CompoundTag();
            putBlockPos(posNBT, vec3d.get());
            compound.put(key, posNBT);
        }
    }

    public static Optional<BlockPos> getBlockPos(CompoundTag compound, String key) {
        if (compound.contains(key)) {
            return Optional.of(getBlockPos(compound.getCompoundOrEmpty(key)));
        }

        return Optional.empty();
    }

    public static void putBlockPos(CompoundTag compound, String key, @Nullable BlockPos vec3d) {
        if (vec3d != null) {
            CompoundTag posNBT = new CompoundTag();
            putBlockPos(posNBT, vec3d);
            compound.put(key, posNBT);
        }
    }

//    @Nullable
//    public static BlockPos getBlockPos(CompoundNBT compound, String key) {
//        if (compound.contains(key)) {
//            return getBlockPos(compound.getCompound(key));
//        }
//
//        return null;
//    }

    public static void writeItemStack(HolderLookup.Provider prov, CompoundTag compound, String key, ItemStack stackIn) {
        if (!stackIn.isEmpty()) {
            ItemStack.CODEC.encodeStart(prov.createSerializationContext(NbtOps.INSTANCE), stackIn)
                .ifSuccess(tag -> compound.put(key, tag));
        }
    }

    @Nonnull
    public static ItemStack readItemStack(HolderLookup.Provider prov, CompoundTag compound, String key) {
        if (compound.contains(key)) {
            var tag = compound.get(key);
            if (tag != null) {
                return ItemStack.CODEC.parse(prov.createSerializationContext(NbtOps.INSTANCE), tag)
                    .getOrThrow(msg -> new RuntimeException(msg));
            }
        }
        return ItemStack.EMPTY;
    }
}
