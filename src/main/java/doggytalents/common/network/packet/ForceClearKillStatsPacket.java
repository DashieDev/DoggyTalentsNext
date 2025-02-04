package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyItems;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;
import doggytalents.common.network.packet.data.ForceClearKillStatsData;
import doggytalents.common.util.PlayerUtil;
import net.minecraft.network.FriendlyByteBuf;

public class ForceClearKillStatsPacket extends DogPacket<ForceClearKillStatsData> {

    @Override
    public void encode(ForceClearKillStatsData data, FriendlyByteBuf buf) {
        buf.writeInt(data.entityId);
    }

    @Override
    public ForceClearKillStatsData decode(FriendlyByteBuf buf) {
        var id = buf.readInt();
        return new ForceClearKillStatsData(id);
    }


    @Override
    public void handleDog(Dog dogIn, ForceClearKillStatsData data, Supplier<Context> ctx) {
        //Same logic as force claim Owner.
        var sender = ctx.get().getSender();
        if (!sender.hasPermissions(Constants.OPERATOR_PERMISSION))
            return;
        if (!sender.getAbilities().instabuild)
            return;

        var stack = sender.getMainHandItem();
        if (!stack.is(DoggyItems.AMNESIA_BONE.get()))
            return;
        if (PlayerUtil.isOnCooldown(sender, DoggyItems.AMNESIA_BONE.get()))
            return;
        
        dogIn.getStatTracker().clearAllStatsKill();

        PlayerUtil.addCooldown(sender, DoggyItems.AMNESIA_BONE.get(), 20);
    }
    
}
