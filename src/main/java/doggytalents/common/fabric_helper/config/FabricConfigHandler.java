package doggytalents.common.fabric_helper.config;

import doggytalents.common.lib.Constants;
import fuzs.forgeconfigapiport.fabric.api.forge.v4.ForgeConfigRegistry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.neoforged.fml.config.ModConfig;

public class FabricConfigHandler {
 
    public static final String FABRIC_CONFIG_FILENAME = "doggytalents-fabric_server.toml";
    public static ServerConfig SERVER;
    private static ForgeConfigSpec CONFIG_SERVER_SPEC;

    public static void init() {
        var commonPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        CONFIG_SERVER_SPEC = commonPair.getRight();
        SERVER = commonPair.getLeft();
        ForgeConfigRegistry.INSTANCE.register(
            Constants.MOD_ID, ModConfig.Type.SERVER, CONFIG_SERVER_SPEC, FABRIC_CONFIG_FILENAME);
    }

    public static class ServerConfig {
        
        public ServerConfig(ForgeConfigSpec.Builder builder) {
            builder.push("Fabric Dedicated Config");

            builder.pop();
        }

    }

}
