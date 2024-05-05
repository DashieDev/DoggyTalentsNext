package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.common.entity.Dog;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.packet.data.DogExplosionData;
import doggytalents.common.talent.OokamiKazeTalent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class DogExplosionPacket implements IPacket<DogExplosionData> {

    @Override
    public void encode(DogExplosionData data, FriendlyByteBuf buf) {
        buf.writeInt(data.dogId);
        boolean has_knockback = data.knockback().isPresent();
        buf.writeBoolean(has_knockback);
        if (has_knockback) {
            var knock = data.knockback().get();
            buf.writeDouble(knock.x());
            buf.writeDouble(knock.y());
            buf.writeDouble(knock.z());
        }
    }

    @Override
    public DogExplosionData decode(FriendlyByteBuf buf) {
        var id = buf.readInt();
        boolean has_knockback = buf.readBoolean();
        Vec3 knock = null;
        if (has_knockback) {
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            knock = new Vec3(x, y, z);
        }
        return new DogExplosionData(id, knock);
    }

    @Override
    public void handle(DogExplosionData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {

            if (ctx.get().isClientRecipent()) { 
                var mc = Minecraft.getInstance();
                var e = mc.level.getEntity(data.dogId);
                if (e instanceof Dog dog) {
                    OokamiKazeTalent.explodeClient(dog);
                    data.knockback().ifPresent(x -> {
                        var player = mc.player;
                        if (player == null)
                            return;
                        var knock_movement = player.getDeltaMovement().add(x);
                        player.setDeltaMovement(knock_movement);
                    });
                }
            }

        });

        ctx.get().setPacketHandled(true);
    }
    
}
