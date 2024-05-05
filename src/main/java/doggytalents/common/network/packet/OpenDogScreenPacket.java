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
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;


import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class OpenDogScreenPacket implements IPacket<OpenDogScreenData>  {

    @Override
    public OpenDogScreenData decode(FriendlyByteBuf buf) {
        var type = OpenDogScreenData.ScreenType
            .byId(buf.readInt());
        var dogId = buf.readInt();
        return new OpenDogScreenData(type, dogId);
    }


    @Override
    public void encode(OpenDogScreenData data, FriendlyByteBuf buf) {
        buf.writeInt(data.type.getId());
        buf.writeInt(data.dogId);
    }

    @Override
    public void handle(OpenDogScreenData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (!ctx.get().isClientRecipent()) {
                ServerPlayer player = ctx.get().getSender();
                selectAndOpenDogScreen(data, player);
            }
        });

        ctx.get().setPacketHandled(true);
    }

    public void selectAndOpenDogScreen(OpenDogScreenData data, ServerPlayer player) {
        switch (data.type) {
        case TOOL:
        {
            var e = player.level().getEntity(data.dogId);
            if (e instanceof Dog dog)
                Screens.openDoggyToolsScreen(player, dog);
            break;
        }
        case ARMOR:
        {
            var e = player.level().getEntity(data.dogId);
            if (e instanceof Dog dog)
                Screens.openArmorScreen(player, dog);
            break;
        }
        case INVENTORY_SINGLE:
        {
            var e = player.level().getEntity(data.dogId);
            if (e instanceof Dog dog)
                Screens.openPackPuppyScreen(player, dog);
            break;
        }
        default:
        {
            List<Dog> dogs = player.level().getEntitiesOfClass(Dog.class, player.getBoundingBox().inflate(12D, 12D, 12D),
                (dog) -> dog.canInteract(player) && PackPuppyTalent.hasInventory(dog)
            );
            Collections.sort(dogs, new EntityUtil.Sorter(player.position()));
            if (dogs.size() > PackPuppyTalent.MAX_DOG_INV_VIEW) {
                dogs = dogs.subList(0, PackPuppyTalent.MAX_DOG_INV_VIEW);
            }
            if (!dogs.isEmpty()) {
                Screens.openDogInventoriesScreen(player, dogs);
            }
            break;
        }
        }
    } 
}
