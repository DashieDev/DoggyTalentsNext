package doggytalents.common.fabric_helper.pack_handler;

import doggytalents.common.event.PackHandler;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class FabricPackHandler {

    public static void init() {
        var mod_container = FabricLoader.getInstance()
            .getModContainer(Constants.MOD_ID).orElseThrow();
        
        ResourceManagerHelper.registerBuiltinResourcePack(
            getPackLocation(PackHandler.ALT_RECIPE_1),
            mod_container, 
            Component.literal("DTN Recipe Pack I."),
            ResourcePackActivationType.NORMAL
        );
    }

    private static ResourceLocation getPackLocation(String id) {
        return Util.getResource(PackHandler.getBuiltinPackLocationNamespace(id));
    }

    public static boolean isBuiltinPack(String name) {
        return name.startsWith(Util.getResource("data/").toString());
    }

}
