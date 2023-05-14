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
        registerPacket(new DogStartShakingLavaPacket(), DogStartShakingLavaData.class);
        registerPacket(new ConductingBonePackets.RequestDogsPacket(), ConductingBoneData.RequestDogsData.class);
        registerPacket(new ConductingBonePackets.ResponseDogsPackets(), ConductingBoneData.ResponseDogsData.class);
        registerPacket(new ConductingBonePackets.RequestDistantTeleportDogPacket(), ConductingBoneData.RequestDistantTeleportDogData.class);
        registerPacket(new ChangeAccessoryPacket(), ChangeAccessoriesData.class);
        registerPacket(new StatsSyncPackets.Request(), StatsSyncData.Request.class);
        registerPacket(new StatsSyncPackets.Response(), StatsSyncData.Response.class);
        registerPacket(new DogDismountPacket(), DogDismountData.class);
        registerPacket(new DogRegardTeamPlayersPacket(), DogRegardTeamPlayersData.class);
        registerPacket(new DogForceSitPacket(), DogForceSitData.class);
        registerPacket(new RadarPackets.RequestDogsPacket(), RadarData.RequestDogsData.class);
        registerPacket(new RadarPackets.ResponseDogsPackets(), RadarData.ResponseDogsData.class);
        registerPacket(new RadarPackets.StartLocatingPacket(), RadarData.StartLocatingData.class);
        registerPacket(new RadarPackets.RequestPosUpdatePacket(), RadarData.RequestPosUpdateData.class);
        registerPacket(new RadarPackets.ResponsePosUpdatePacket(), RadarData.ResponsePosUpdateData.class);
        registerPacket(new DogDeTrainPacket(), DogDeTrainData.class);
        registerPacket(new DogUntamePacket(), DogUntameData.class);
        registerPacket(new DogMigrateOwnerPacket(), DogMigrateOwnerData.class);
        registerPacket(new WhistleEditHotKeyPacket(), WhisltleEditHotKeyData.class);
        registerPacket(new WhistleUsePacket(), WhistleUseData.class);
        registerPacket(new DogGroupPackets.EDIT(), DogGroupsData.EDIT.class);
        registerPacket(new DogGroupPackets.FETCH_REQUEST(), DogGroupsData.FETCH_REQUEST.class);
        registerPacket(new DogGroupPackets.UPDATE(), DogGroupsData.UPDATE.class);
    }

    public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message) {
        DoggyTalentsNext.HANDLER.send(target, message);
    }

    public static <D> void registerPacket(IPacket<D> packet, Class<D> dataClass) {
        DoggyTalentsNext.HANDLER.registerMessage(PacketHandler.disc++, dataClass, packet::encode, packet::decode, packet::handle);
    }
}
