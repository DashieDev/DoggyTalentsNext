package doggytalents.common.data;

import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DTNPackMetadataProvider {

    public static void start(GatherDataEvent event) {
        var gen = event.getGenerator();
        var output = gen.getPackOutput();

        gen.addProvider(true, PackMetadataGenerator.forFeaturePack(output,
            Component.literal("Doggy Talents Next Resources.")));
    }

}
