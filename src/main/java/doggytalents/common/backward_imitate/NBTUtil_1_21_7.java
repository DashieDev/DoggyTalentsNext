package doggytalents.common.backward_imitate;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import doggytalents.api.backward_imitate.CompoundTag_1_21_7;
import doggytalents.common.util.NBTUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public class NBTUtil_1_21_7 {
    
    public static void putUniqueId(CompoundTag compound, String key, @Nullable UUID uuid) {
        NBTUtil.putUniqueId(CompoundTag_1_21_7.wrap(compound), key, uuid);
    }

    public static void putIdentifier(CompoundTag compound, String key, @Nullable Identifier rl) {
        NBTUtil.putIdentifier(CompoundTag_1_21_7.wrap(compound), key, rl);
    }

    public static void putVector3d(CompoundTag compound, @Nullable Vec3 vec3d) {
        NBTUtil.putVector3d(CompoundTag_1_21_7.wrap(compound), vec3d);
    }

    public static void putTextComponent(CompoundTag compound, String key, @Nullable Component component) {
        NBTUtil.putTextComponent(CompoundTag_1_21_7.wrap(compound), key, component);
    }

    public static void putRegistryValue(CompoundTag compound, String key, Identifier value) {
        NBTUtil.putRegistryValue(CompoundTag_1_21_7.wrap(compound), key, value);
    }

    public static void putBlockPos(CompoundTag compound, String key, Optional<BlockPos> vec3d) {
        NBTUtil.putBlockPos(CompoundTag_1_21_7.wrap(compound), key, vec3d);
    }

    public static void putBlockPos(CompoundTag compound, String key, @Nullable BlockPos vec3d) {
        NBTUtil.putBlockPos(CompoundTag_1_21_7.wrap(compound), key, vec3d);
    }


    public static void putUniqueId(ValueOutput compound, String key, @Nullable UUID uuid) {
        NBTUtil.putUniqueId(CompoundTag_1_21_7.wrap(compound), key, uuid);
    }

    public static void putIdentifier(ValueOutput compound, String key, @Nullable Identifier rl) {
        NBTUtil.putIdentifier(CompoundTag_1_21_7.wrap(compound), key, rl);
    }

    public static void putVector3d(ValueOutput compound, @Nullable Vec3 vec3d) {
        NBTUtil.putVector3d(CompoundTag_1_21_7.wrap(compound), vec3d);
    }

    public static void putTextComponent(ValueOutput compound, String key, @Nullable Component component) {
        NBTUtil.putTextComponent(CompoundTag_1_21_7.wrap(compound), key, component);
    }

    public static void putRegistryValue(ValueOutput compound, String key, Identifier value) {
        NBTUtil.putRegistryValue(CompoundTag_1_21_7.wrap(compound), key, value);
    }

    public static void putBlockPos(ValueOutput compound, String key, Optional<BlockPos> vec3d) {
        NBTUtil.putBlockPos(CompoundTag_1_21_7.wrap(compound), key, vec3d);
    }

    public static void putBlockPos(ValueOutput compound, String key, @Nullable BlockPos vec3d) {
        NBTUtil.putBlockPos(CompoundTag_1_21_7.wrap(compound), key, vec3d);
    }

}
