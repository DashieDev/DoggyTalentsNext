package doggytalents.forge_imitate.event;

import doggytalents.forge_imitate.event.util.EventBus;

public class FabricTempEventFinishRegisterFix {
    
    private static int finishedLeft = 2;

    public static void onFinish() {
        --finishedLeft;
        if (finishedLeft <= 0)
            EventBus.COMMON_BUS.finishRegister();
    }

}
