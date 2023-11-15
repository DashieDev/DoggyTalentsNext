package doggytalents.common.network.packet;

import doggytalents.common.Screens;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.packet.data.OpenDogScreenData;
import doggytalents.common.talent.PackPuppyTalent;
import doggytalents.common.util.EntityUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;


import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class OpenDogScreenPacket implements IPacket<OpenDogScreenData>  {

    @Override
    public OpenDogScreenData decode(FriendlyByteBuf buf) {
        return new OpenDogScreenData();
    }


    @Override
    public void encode(OpenDogScreenData data, FriendlyByteBuf buf) {

    }

    @Override
    public void handle(OpenDogScreenData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection().getReceptionSide() == LogicalSide.SERVER) {
                ServerPlayer player = ctx.get().getSender();
                List<Dog> dogs = player.level.getEntitiesOfClass(Dog.class, player.getBoundingBox().inflate(12D, 12D, 12D),
                    (dog) -> dog.canInteract(player) && PackPuppyTalent.hasInventory(dog)
                );
                Collections.sort(dogs, new EntityUtil.Sorter(player.position()));
                if (dogs.size() > PackPuppyTalent.MAX_DOG_INV_VIEW) {
                    dogs = dogs.subList(0, PackPuppyTalent.MAX_DOG_INV_VIEW);
                }
				if (!dogs.isEmpty()) {
				    Screens.openDogInventoriesScreen(player, dogs);
				}
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
