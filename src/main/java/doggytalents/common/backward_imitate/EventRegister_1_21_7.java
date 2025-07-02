package doggytalents.common.backward_imitate;

import doggytalents.client.backward_imitate.GuiDoggySpinRenderer_1_21_7;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;

public class EventRegister_1_21_7 {
    
    public static void registerEvent(IEventBus mod_event_bus, IEventBus forge_event_bus) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            mod_event_bus.addListener(GuiDoggySpinRenderer_1_21_7::onRegisterPIPRenderers);
        }
    }

}
