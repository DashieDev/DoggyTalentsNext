package doggytalents.common.data;

import java.util.Optional;

import net.minecraft.DetectedVersion;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DTNPackMetadataProvider {

    public static void start(GatherDataEvent event) {
        var gen = event.getGenerator();
        var output = gen.getPackOutput();
        
        var provider = new PackMetadataGenerator(output)
            .add(PackMetadataSection.SERVER_TYPE, new PackMetadataSection(
                    Component.literal("Doggy Talents Next Resources."),
                    new InclusiveRange<>(DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA))));
        gen.addProvider(true, provider);
    }

}
