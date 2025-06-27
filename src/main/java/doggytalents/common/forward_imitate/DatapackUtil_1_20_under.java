package doggytalents.common.forward_imitate;

import java.util.Optional;
import java.util.function.Function;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;

public class DatapackUtil_1_20_under {

    public static record KnownPack(String modId, String id, String version) {}

    public static record PackLocationInfo(String id, Component title, 
        PackSource source, Optional<KnownPack> knownPackInfo) {}

    public static record PackSelectionConfig(boolean required, 
        Pack.Position defaultPosition, boolean fixedPosition) {}

    public static Pack readMetaAndCreate(PackLocationInfo info, Pack.ResourcesSupplier resourceSupplier, 
        PackType type, PackSelectionConfig config) {

        return Pack.readMetaAndCreate(info.id, info.title, config.required, 
            resourceSupplier, type, config.defaultPosition, info.source);
    }

    public static class BuiltInPackSource {
        public static Pack.ResourcesSupplier fromName(final Function<String, PackResources> onName) {
            return new Pack.ResourcesSupplier() {

                @Override
                public PackResources open(String id) {
                    return onName.apply(id);
                }
                
            };
        }
    }
    
}
