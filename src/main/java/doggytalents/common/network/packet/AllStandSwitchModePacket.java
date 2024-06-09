package doggytalents.common.network.packet;

import java.util.Collections;
import java.util.function.Predicate;
import java.util.function.Supplier;

import doggytalents.DoggyItems;
import doggytalents.DoggySounds;
import doggytalents.api.feature.EnumMode;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.packet.data.AllStandSwitchModeData;
import doggytalents.common.util.EntityUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent.Context;

public class AllStandSwitchModePacket implements IPacket<AllStandSwitchModeData> {

    @Override
    public void encode(AllStandSwitchModeData data, FriendlyByteBuf buf) {
        buf.writeInt(data.mode.getIndex());
    }

    @Override
    public AllStandSwitchModeData decode(FriendlyByteBuf buf) {
        var mode_id = buf.readInt();
        var mode = EnumMode.byIndex(mode_id);
        return new AllStandSwitchModeData(mode);
    }

    @Override
    public void handle(AllStandSwitchModeData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var side = ctx.get().getDirection().getReceptionSide();
            if (!side.isServer()) return;
            var sender = ctx.get().getSender();
            if (sender.getCooldowns().isOnCooldown(DoggyItems.WHISTLE.get())) return;

            var target_mode = data.mode;
            if (target_mode.canWander())
                return;

            Predicate<Dog> switch_mode_target = filter_dog -> 
                filter_dog.isDoingFine()
                && !filter_dog.isOrderedToSit()
                && filter_dog.isOwnedBy(sender)
                && filter_dog.getMode() != target_mode
                && !filter_dog.getMode().canWander();
            var dogs = sender.level.getEntitiesOfClass(
                Dog.class, 
                sender.getBoundingBox().inflate(100D, 50D, 100D), 
                switch_mode_target
            );

            for (var dog : dogs) {
                dog.setMode(target_mode);
            }

            if (ConfigHandler.WHISTLE_SOUNDS)
            sender.level.playSound(null, sender.blockPosition(), DoggySounds.WHISTLE_LONG.get(), SoundSource.PLAYERS, 0.6F + sender.level.random.nextFloat() * 0.1F, 0.4F + sender.level.random.nextFloat() * 0.2F);
            sender.sendSystemMessage(Component.translatable("dogcommand.all_stand_switch_mode", 
                Component.translatable(data.mode.getUnlocalisedName())
                .withStyle(
                    Style.EMPTY
                    .withBold(true)
                )
            ));
            sender.getCooldowns().addCooldown(DoggyItems.WHISTLE.get(), 40);    
        });

        ctx.get().setPacketHandled(true);
    }
    
}
