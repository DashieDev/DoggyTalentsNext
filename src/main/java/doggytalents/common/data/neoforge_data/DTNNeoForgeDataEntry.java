package doggytalents.common.data.neoforge_data;

import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DTNNeoForgeDataEntry {
    
    public static void onGatherData(final GatherDataEvent event) {
        var gen = event.getGenerator();
        var packOutput = gen.getPackOutput();
        var lookup = event.getLookupProvider();
        if (event.includeServer()) {
            gen.addProvider(true, new DTNNeoForgeComposterProvider(packOutput, lookup));
        }
    }
    
}
