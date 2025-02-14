package doggytalents.common.backward_imitate;

import doggytalents.client.ClientSetup;
import doggytalents.client.backward_imitate.PlayerRenderPrep_21_3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;

public class EventRegister_21_3 {
    
    public static void registerEvent(IEventBus mod_event_bus, IEventBus forge_event_bus) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            mod_event_bus.addListener(ClientSetup::onRegisterClientExtension_21_3);
            forge_event_bus.addListener(PlayerRenderPrep_21_3::afterPlayerRender);
        }
    }

}
