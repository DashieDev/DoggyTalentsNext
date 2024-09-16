package doggytalents.common.network.packet.data;

import doggytalents.api.registry.TalentOption;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.NetworkUtil;
import net.minecraft.network.FriendlyByteBuf;

public class DogTalentOptionSetData extends DogData {

    public final Talent talent;
    public final TalentOption<?> entry;
    public final Object val;
    
    private DogTalentOptionSetData(int entityId, Talent talent, TalentOption<?> entry, Object val) {
        super(entityId);
        this.talent = talent;
        this.entry = entry;
        this.val = val;
    }

    public static <T> DogTalentOptionSetData of(Dog dog, Talent talent, TalentOption<T> entry, T val) {
        return new DogTalentOptionSetData(dog.getId(), talent, entry, val);
    }

    public void writeToBuf(FriendlyByteBuf buf) {
        NetworkUtil.writeTalentToBuf(buf, talent);
        NetworkUtil.writeTalentOptionToBuf(buf, entry);
        writeEntryDataToBuf(entry, buf);
    }

    private <T> void writeEntryDataToBuf(TalentOption<T> entry, FriendlyByteBuf buf) {
        entry.encode(buf, (T) this.val);
    }

    public static DogTalentOptionSetData readFromBuf(FriendlyByteBuf buf) {
        int dog_id = buf.readInt();
        var talent = NetworkUtil.readTalentFromBuf(buf);
        var entry = NetworkUtil.readTalentOptionFromBuf(buf);
        var val = entry.decode(buf);
        return new DogTalentOptionSetData(dog_id, talent, entry, val);
    }

    public void setToTalent(TalentInstance inst) {
        doSetToTalent(inst, this.entry);
    }

    private <T> void doSetToTalent(TalentInstance inst, TalentOption<T> entry) {
        inst.setTalentOption(entry, (T) val);
    }

}
