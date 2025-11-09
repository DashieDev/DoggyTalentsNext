package doggytalents.common.backward_imitate;

import java.util.function.Function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JavaOps;

import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.neoforged.neoforge.resource.JarContentsPackResources;
import net.neoforged.neoforgespi.language.IModInfo;

public class DataUtil_1_21_9 {

    public static ContextNbtProvider getBlockEntityNbtProvider() {
        var id = LootContext.BlockEntityTarget.BLOCK_ENTITY.getSerializedName();  
        var dyanmic_data = new Dynamic<>(JavaOps.INSTANCE, id);
        var codec = ContextNbtProvider.INLINE_CODEC;
        var result = codec.decode(dyanmic_data);
        return result.getOrThrow().getFirst();
    }

    public static Function<PackLocationInfo, PackResources> getPackResourceSupplier(IModInfo modInfo, String prefix) {
        return (pack_info) -> {
            var contents = modInfo.getOwningFile().getFile().getContents();
            return new JarContentsPackResources(pack_info, contents, prefix);
        };
    }

}
