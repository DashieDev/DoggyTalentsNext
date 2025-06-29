package doggytalents.api.fabric_helper.entry;

import net.fabricmc.loader.api.FabricLoader;

public interface PostInitEntry {
        
    public static String ENTRY_ID = "doggytalents.post_init";
    
    public void afterDTNInit();

    public static void firePosInitEntry() {
        var containers = FabricLoader.getInstance()
            .getEntrypointContainers(ENTRY_ID, PostInitEntry.class);
        if (containers.isEmpty())
            return;
        for (var container : containers) {
            container.getEntrypoint().afterDTNInit();
        }
    }

}
