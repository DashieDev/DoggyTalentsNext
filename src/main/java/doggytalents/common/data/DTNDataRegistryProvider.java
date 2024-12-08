package doggytalents.common.data;

import java.util.Set;

import doggytalents.common.lib.Constants;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

public class DTNDataRegistryProvider {
    public static void start(GatherDataEvent event) {
        var gen = event.getGenerator();
        var packOutput = gen.getPackOutput();
        var lookup = event.getLookupProvider();

        var data_set = new RegistrySetBuilder()
            .add(Registries.JUKEBOX_SONG, DTMusicProvider::bootstrap);
        gen.addProvider(true, new DatapackBuiltinEntriesProvider(packOutput, 
            lookup, data_set, Set.of(Constants.MOD_ID)));
    }
}
