package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggySounds;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;

import doggytalents.common.network.IPacket;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogData;
import doggytalents.common.network.packet.data.HeelByNameData;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.EntityUtil;
import net.minecraft.client.Minecraft;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent.Context;


 public class HeelByNamePacket extends DogPacket<HeelByNameData> {

    @Override
    public void encode(HeelByNameData data, FriendlyByteBuf buf) {
        buf.writeInt(data.entityId);
    }

    @Override
    public HeelByNameData decode(FriendlyByteBuf buf) {
        return new HeelByNameData(buf.readInt());
    }


    @Override
    public void handleDog(Dog dog, HeelByNameData data, Supplier<Context> ctx) {
        var owner = ctx.get().getSender();
        dog.setOrderedToSit(false);
        DogUtil.searchAndTeleportToOwner(dog, 2);
        if (ConfigHandler.WHISTLE_SOUNDS)
        owner.level.playSound(null, owner.blockPosition(), DoggySounds.WHISTLE_LONG.get(), SoundSource.PLAYERS, 0.6F + owner.level.random.nextFloat() * 0.1F, 0.4F + owner.level.random.nextFloat() * 0.2F);
;       owner.sendSystemMessage(Component.translatable("dogcommand.heel_by_name", dog.getName().getString()));
    }
    

}
