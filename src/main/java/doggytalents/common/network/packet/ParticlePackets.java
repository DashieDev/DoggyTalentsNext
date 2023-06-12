package doggytalents.common.network.packet;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogData;
import doggytalents.common.network.packet.data.DogEatingParticleData;
import doggytalents.common.network.packet.data.DogStartShakingLavaData;
import doggytalents.common.network.packet.data.ParticleData.CritEmitterData;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;

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

                if (ctx.get().getDirection().getReceptionSide().isClient()) { 
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
            final int RADIUS = 64;
            PacketDistributor.TargetPoint tarp = new PacketDistributor.TargetPoint(e.getX(), e.getY(), e.getZ(), RADIUS, e.level().dimension());
            PacketHandler.send(PacketDistributor.NEAR.with(() -> tarp), new CritEmitterData(e.getId()));
        }

    }

    public static class DogEatingParticlePacket implements IPacket<DogEatingParticleData> {

        @Override
        public void encode(DogEatingParticleData data, FriendlyByteBuf buf) {
            buf.writeInt(data.dogId);
            buf.writeItem(data.food);            
        }

        @Override
        public DogEatingParticleData decode(FriendlyByteBuf buf) {
            int dogId = buf.readInt();
            var food = buf.readItem();       
            return new DogEatingParticleData(dogId, food);
        }

        @Override
        public void handle(DogEatingParticleData data, Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {

                if (ctx.get().getDirection().getReceptionSide().isClient()) { 
                    Minecraft mc = Minecraft.getInstance();
                    Entity e = mc.level.getEntity(data.dogId);
                    if (e != null && data.food != null) {
                        var dog = e;
                        var a1 = dog.getYRot();
                        var dx1 = -Mth.sin(a1*Mth.DEG_TO_RAD);
                        var dz1 = Mth.cos(a1*Mth.DEG_TO_RAD);

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
            final int RADIUS = 64;
            PacketDistributor.TargetPoint tarp = new PacketDistributor.TargetPoint(dog.getX(), dog.getY(), dog.getZ(), RADIUS, dog.level().dimension());
            PacketHandler.send(PacketDistributor.NEAR.with(() -> tarp), new DogEatingParticleData(dog.getId(), food));
        }
        
    }

    public static class DogStartShakingLavaPacket implements IPacket<DogStartShakingLavaData>  {
        @Override
        public void encode(DogStartShakingLavaData data, FriendlyByteBuf buf) {
            buf.writeInt(data.dogId);          
        }

        @Override
        public DogStartShakingLavaData decode(FriendlyByteBuf buf) {
            int dogId = buf.readInt();    
            return new DogStartShakingLavaData(dogId);
        }

        @Override
        public void handle(DogStartShakingLavaData data, Supplier<Context> ctx) {
            
            ctx.get().enqueueWork(() -> {

                if (ctx.get().getDirection().getReceptionSide().isClient()) { 
                    Minecraft mc = Minecraft.getInstance();
                    Entity e = mc.level.getEntity(data.dogId);
                    if (e instanceof Dog) {
                        Dog d = (Dog) e;
                        d.startShakingLava();
                    }
                }

            });

            ctx.get().setPacketHandled(true);
        }

        public static void sendDogStartShakingLavaPacketToNearByClients(AbstractDog dog) {
            final int RADIUS = 64;
            PacketDistributor.TargetPoint tarp = new PacketDistributor.TargetPoint(dog.getX(), dog.getY(), dog.getZ(), RADIUS, dog.level().dimension());
            PacketHandler.send(PacketDistributor.NEAR.with(() -> tarp), new DogStartShakingLavaData(dog.getId()));
        }

    }
}
