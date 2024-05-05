package doggytalents.common.network;

import doggytalents.common.network.DTNNetworkHandler.DTNNetworkPayload;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class DTNNetworkHandlerClient {
    
    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(DTNNetworkHandler.CHANNEL_ID,
            DTNNetworkHandlerClient::handlePayloadClient);
    }

    private static void handlePayloadClient(DTNNetworkPayload<?> payload, ClientPlayNetworking.Context context) {
        DTNNetworkHandler.handlePayloadClient(payload);
    }

}
