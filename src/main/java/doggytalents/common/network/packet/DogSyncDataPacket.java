package doggytalents.common.network.packet;

import java.util.ArrayList;
import java.util.function.Supplier;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.api.registry.TalentInstance;
import doggytalents.client.event.ClientEventHandler;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.packet.data.DogSyncData;
import doggytalents.common.util.NetworkUtil;
import net.minecraft.network.FriendlyByteBuf;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class DogSyncDataPacket implements IPacket<DogSyncData> {

    @Override
    public void encode(DogSyncData data, FriendlyByteBuf buf) {
        buf.writeInt(data.dogId);
        var talentsOptional = data.talents();
        if (talentsOptional.isPresent()) {
            buf.writeInt(ReadState.TALENTS.getId());
            writeTalents(talentsOptional.get(), buf);
        }
        var accessoriesOptional = data.accessories();
        if (accessoriesOptional.isPresent()) {
            buf.writeInt(ReadState.ACCESSORIES.getId());
            writeAccessories(accessoriesOptional.get(), buf);
        }
        var refreshOptionOptional = data.refreshOptions();
        if (refreshOptionOptional.isPresent()) {
            buf.writeInt(ReadState.TALENTS_REFRESH_OPTIONS.getId());
            writeTalents(refreshOptionOptional.get(), buf);
        }
        buf.writeInt(ReadState.FINISH.getId());
    }

    private void writeTalents(ArrayList<TalentInstance> talents, FriendlyByteBuf buf) {
        buf.writeInt(talents.size());

        for (TalentInstance inst : talents) {
            NetworkUtil.writeTalentToBuf(buf, inst.getTalent());
            inst.writeToBuf(buf);
        }
    }

    private void writeAccessories(ArrayList<AccessoryInstance> value, FriendlyByteBuf buf) {
        buf.writeInt(value.size());

        for (AccessoryInstance inst : value) {
            NetworkUtil.writeAccessoryToBuf(buf, inst.getAccessory());
            inst.getAccessory().write(inst, buf);
        }
    }

    @Override
    public DogSyncData decode(FriendlyByteBuf buf) {
        int dogId = buf.readInt();
        var readState = ReadState.fromId(buf.readInt());
        ArrayList<TalentInstance> talents = null;
        ArrayList<AccessoryInstance> accessories = null;
        ArrayList<TalentInstance> refreshOptions = null;
        while (readState != ReadState.FINISH) {
            if (readState == ReadState.TALENTS) {
                talents = readTalents(buf);
            } else if (readState == ReadState.ACCESSORIES) {
                accessories = readAccessories(buf);
            } else if (readState == ReadState.TALENTS_REFRESH_OPTIONS) {
                refreshOptions = readTalents(buf);
            }
            readState = ReadState.fromId(buf.readInt());
        }

        return new DogSyncData(dogId, talents, accessories, refreshOptions);
    }

    private ArrayList<TalentInstance> readTalents(FriendlyByteBuf buf) {
        int size = buf.readInt();
        var newInst = new ArrayList<TalentInstance>(size);
        for (int i = 0; i < size; i++) {
            var inst = NetworkUtil.readTalentFromBuf(buf).getDefault();
            inst.readFromBuf(buf);
            newInst.add(inst);
        }
        return newInst;
    }

    private ArrayList<AccessoryInstance> readAccessories(FriendlyByteBuf buf) {
        int size = buf.readInt();
        var newInst = new ArrayList<AccessoryInstance>(size);

        for (int i = 0; i < size; i++) {
            var type = NetworkUtil.readAccessoryFromBuf(buf);
            newInst.add(type.createInstance(buf));
        }

        return newInst;
    }

    @Override
    public void handle(DogSyncData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (!ctx.get().isClientRecipent())
                return;
            ClientEventHandler.onDogSyncedDataUpdated(data);
        });
        ctx.get().setPacketHandled(true);
    }
    
    public static enum ReadState {
        FINISH(0),
        TALENTS(1),
        ACCESSORIES(2),
        TALENTS_REFRESH_OPTIONS(3);

        private int id;

        private ReadState(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static ReadState fromId(int id) {
            var values = ReadState.values();
            return values[id];
        }
    }

}
