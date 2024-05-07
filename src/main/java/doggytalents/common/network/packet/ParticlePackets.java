package doggytalents.common.network.packet;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogData;
import doggytalents.common.network.packet.data.DogEatingParticleData;
import doggytalents.common.network.packet.data.DogShakingData;
import doggytalents.common.network.packet.data.DogShakingData.State;
import doggytalents.common.network.packet.data.ParticleData.CritEmitterData;
import doggytalents.common.util.NetworkUtil;
import doggytalents.forge_imitate.network.PacketDistributor;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ParticlePackets {
    public static class CritEmitterPacket implements IPacket<CritEmitterData> {

        @Override
        public void encode(CritEmitterData data, FriendlyByteBuf buf) {
            buf.writeInt(data.targetId);
        }

        @Override
        public CritEmitterData decode(FriendlyByteBuf buf) {
            return new CritEmitterData(buf.readInt());
        }

        @Override
        public void handle(CritEmitterData data, Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {

                if (ctx.get().isClientRecipent()) { 
                    Minecraft mc = Minecraft.getInstance();
                    Entity e = mc.level.getEntity(data.targetId);
                    if (e != null) {
                        Minecraft.getInstance().particleEngine.createTrackingEmitter(e, ParticleTypes.CRIT);
                    }
                }

            });

            ctx.get().setPacketHandled(true);
        }

        public static void sendCritEmitterPacketToNearClients(Entity e) {
            PacketHandler.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> e), new CritEmitterData(e.getId()));
        }

    }

    public static class DogEatingParticlePacket implements IPacket<DogEatingParticleData> {

        @Override
        public void encode(DogEatingParticleData data, FriendlyByteBuf buf) {
            buf.writeInt(data.dogId);
            NetworkUtil.writeItemToBuf(buf, data.food);            
        }

        @Override
        public DogEatingParticleData decode(FriendlyByteBuf buf) {
            int dogId = buf.readInt();
            var food = NetworkUtil.readItemFromBuf(buf);    
            return new DogEatingParticleData(dogId, food);
        }

        @Override
        public void handle(DogEatingParticleData data, Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {

                if (ctx.get().isClientRecipent()) { 
                    Minecraft mc = Minecraft.getInstance();
                    Entity e = mc.level.getEntity(data.dogId);
                    if (e instanceof Dog dog && data.food != null) {
                        var a1 = dog.getClientAnimatedYBodyRotInRadians();
                        var dx1 = -Mth.sin(a1) * (dog.getDogVisualBbWidth() * 1.5);
                        var dz1 = Mth.cos(a1) * (dog.getDogVisualBbWidth() * 1.5);

                        for(int i = 0; i < 15; ++i) {
                            double d1 = mc.level.getRandom().nextGaussian() * (double)0.5;
                            double d3 = mc.level.getRandom().nextGaussian() * (double)0.8 ;
                            double d5 = mc.level.getRandom().nextGaussian() * (double)0.5;
                            double d6 = mc.level.getRandom().nextGaussian() * (double)0.1;
                            double d7 = mc.level.getRandom().nextGaussian() * (double)0.1;
                            double d8 = mc.level.getRandom().nextGaussian() * (double)0.1;
                
                            mc.level.addParticle(
                                new ItemParticleOption(ParticleTypes.ITEM, data.food), 
                                dog.getX() + dx1 + d1, 
                                dog.getY() + dog.getEyeHeight() + d3, 
                                dog.getZ() + dz1 + d5,
                                d6, d7, d8
                            );
                         }
                        // dog.level().sendParticles(
                        //     new ItemParticleOption(ParticleTypes.ITEM, stack), 
                        //     dog.getX() + dx1, dog.getY() + dog.getEyeHeight(), dog.getZ() + dz1, 
                        //     15, 
                        //     0.5, 0.8f, 0.5, 
                        //     0.1
                        // );
                    }
                }

            });

            ctx.get().setPacketHandled(true);
        }

        public static void sendDogEatingParticlePacketToNearby(AbstractDog dog, ItemStack food) {
            PacketHandler.send(PacketDistributor.TRACKING_ENTITY.with(() -> dog), new DogEatingParticleData(dog.getId(), food));
        }
        
    }

    public static class DogShakingPacket implements IPacket<DogShakingData>  {
        @Override
        public void encode(DogShakingData data, FriendlyByteBuf buf) {
            buf.writeInt(data.dogId);
            buf.writeByte((byte) data.state.getId());
        }

        @Override
        public DogShakingData decode(FriendlyByteBuf buf) {
            int dogId = buf.readInt();
            State state = State.fromId((int) buf.readByte());
            return new DogShakingData(dogId, state);
        }

        @Override
        public void handle(DogShakingData data, Supplier<Context> ctx) {
            
            ctx.get().enqueueWork(() -> {

                if (ctx.get().isClientRecipent()) { 
                    Minecraft mc = Minecraft.getInstance();
                    Entity e = mc.level.getEntity(data.dogId);
                    if (e instanceof Dog) {
                        Dog d = (Dog) e;
                        d.handleDogShakingUpdate(data.state);
                    }
                }

            });

            ctx.get().setPacketHandled(true);
        }

        public static void sendDogShakingPacket(AbstractDog dog, State state) {
            PacketHandler.send(PacketDistributor.TRACKING_ENTITY.with(() -> dog), new DogShakingData(dog.getId(), state));
        }

    }
}
