package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyItems;
import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.Talent;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.DogDeTrainData;
import doggytalents.common.network.packet.data.DogTalentData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class DogDeTrainPacket extends DogPacket<DogDeTrainData> {

    @Override
    public void encode(DogDeTrainData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeRegistryIdUnsafe(DoggyTalentsAPI.TALENTS.get(), data.talent);
    }

    @Override
    public DogDeTrainData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        Talent talent = buf.readRegistryIdUnsafe(DoggyTalentsAPI.TALENTS.get());
        return new DogDeTrainData(entityId, talent);
    }

    @Override
    public void handleDog(Dog dog, DogDeTrainData data, Supplier<Context> ctx) {
        var sender = ctx.get().getSender();
        var stack = sender.getMainHandItem();
        if (stack.getItem() != DoggyItems.AMNESIA_BONE.get()) return;
        var ownerUUID = dog.getOwnerUUID();
        if (ownerUUID == null) return;
        if (!ownerUUID.equals(sender.getUUID())) return;
        var talent = data.talent;
        var dog_level = dog.getDogLevel(talent);
        if (dog_level <= 0) return;
        var xp_cost = talent.getDeTrainXPCost(dog_level);
        if (sender.experienceLevel < xp_cost) return;
        dog.setTalentLevel(talent, 0);
        dog.clearTriggerableAction();
        sender.getCooldowns().addCooldown(DoggyItems.AMNESIA_BONE.get(), 20);
        sender.giveExperienceLevels(-xp_cost);
    }
    
}
