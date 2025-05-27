package doggytalents.common.fabric_helper.config;

import doggytalents.common.lib.Constants;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
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
        ConfigRegistry.INSTANCE.register(
            Constants.MOD_ID, ModConfig.Type.SERVER, CONFIG_SERVER_SPEC, FABRIC_CONFIG_FILENAME);
    }

    public static class ServerConfig {

        public ForgeConfigSpec.BooleanValue DISABLE_RICE_GRAIN_LOOT;
        public ForgeConfigSpec.BooleanValue DISABLE_SOY_LOOT;
        
        public ServerConfig(ForgeConfigSpec.Builder builder) {
            builder.push("Fabric Dedicated Config");
            
            DISABLE_RICE_GRAIN_LOOT = builder
                    .comment("Disable DTN Rice Grains randomly drop from Grass.")
                    .define("disable_rice_loot", false);

            DISABLE_SOY_LOOT = builder
                    .comment("Disable DTN Soy Beans randomly drop when certain entities is being")
                    .comment("killed by Dogs.")
                    .define("disable_soy_loot", false);

            builder.pop();
        }

    }

}
