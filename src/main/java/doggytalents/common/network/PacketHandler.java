package doggytalents.common.network;

import doggytalents.DoggyTalentsNext;
import doggytalents.common.network.packet.*;
import doggytalents.common.network.packet.data.*;
import net.minecraftforge.network.PacketDistributor;
import doggytalents.common.network.packet.data.ParticleData.*;
import doggytalents.common.network.packet.ParticlePackets.*;

public final class PacketHandler {

    private static int disc = 0;

    public static void init() {
        registerPacket(new DogModePacket(), DogModeData.class);
        registerPacket(new DogNamePacket(), DogNameData.class);
        registerPacket(new DogObeyPacket(), DogObeyData.class);
        registerPacket(new DogTalentPacket(), DogTalentData.class);
        //registerPacket(new DogTexturePacket(), DogTextureData.class);
        registerPacket(new FriendlyFirePacket(), FriendlyFireData.class);
        registerPacket(new SendSkinPacket(), SendSkinData.class);
        registerPacket(new RequestSkinPacket(), RequestSkinData.class);
        registerPacket(new OpenDogScreenPacket(), OpenDogScreenData.class);
        registerPacket(new DogInventoryPagePacket(), DogInventoryPageData.class);
        registerPacket(new DogTexturePacket(), DogTextureData.class);
        registerPacket(new HeelByNamePacket(), HeelByNameData.class);
        registerPacket(new WhistleRequestModePacket(), WhistleRequestModeData.class);
        registerPacket(new CritEmitterPacket(), CritEmitterData.class); 
        registerPacket(new DogEatingParticlePacket(), DogEatingParticleData.class);
    }

    public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message) {
        DoggyTalentsNext.HANDLER.send(target, message);
    }

    public static <D> void registerPacket(IPacket<D> packet, Class<D> dataClass) {
        DoggyTalentsNext.HANDLER.registerMessage(PacketHandler.disc++, dataClass, packet::encode, packet::decode, packet::handle);
    }
}
