package doggytalents.api.registry;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Collection;
import java.util.List;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogAlteration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class TalentInstance implements IDogAlteration {

    protected final Talent talent;

    protected int level;

    // public TalentInstance(Talent talentIn, int levelIn) {
    //     this(talentIn, levelIn);
    // }

    public TalentInstance(Talent talentIn) {
        this(talentIn, 1);
    }

    public TalentInstance(Talent talentIn, int levelIn) {
        this.talent = talentIn;
        this.level = levelIn;
    }

    public Talent getTalent() {
        return this.talent;
    }

    public final int level() {
        return this.level;
    }

    public final void setLevel(int levelIn) {
        this.level = levelIn;
    }

    public boolean of(Supplier<Talent> talentIn) {
        return this.of(talentIn.get());
    }

    public boolean of(Talent talentIn) {
        return talentIn == this.talent;
    }

    // public boolean of(Talent talentIn) {
    //     return this.of(talentIn);
    // }

    // public boolean of(IRegistryDelegate<Talent> talentDelegateIn) {
    //     return talentDelegateIn.equals(this.talentDelegate);
    // }

    public TalentInstance copy() {
        var ret = this.talent.getDefault(this.level);
        ret.copyTalentOptionFrom(this);
        return ret;
    }

    public void writeToNBT(AbstractDog dogIn, CompoundTag compound) {
        
    }

    public void readFromNBT(AbstractDog dogIn, CompoundTag compound) {
        
    }

    public final void doReadFromNBT(AbstractDog dogIn, CompoundTag compound) {
        this.setLevel(compound.getInt("level"));
        readFromNBT(dogIn, compound);
    }

    public final void doWriteToNBT(AbstractDog dogIn, CompoundTag compound) {
        compound.putInt("level", this.level());
        writeToNBT(dogIn, compound);
    }

    public final void writeToBuf(FriendlyByteBuf buf, 
        BiConsumer<FriendlyByteBuf, TalentOption<?>> talent_option_writer) {
        
        buf.writeInt(this.level());
        var talent_options = this.getAllTalentOptions();
        if (talent_options.isEmpty()) {
            buf.writeInt(0);
            return;
        }
        buf.writeInt(talent_options.size());
        for (var entry : talent_options) {
            talent_option_writer.accept(buf, entry);
            this.writeTalentOptionToBuf(buf, entry);
        }
    }

    private <T> void writeTalentOptionToBuf(FriendlyByteBuf buf, TalentOption<T> entry) {
        T value = this.getTalentOptionOrDefault(entry);
        entry.encode(buf, value);
    }

    public final void readFromBuf(FriendlyByteBuf buf, 
        Function<FriendlyByteBuf, TalentOption<?>> talent_option_reader) {
        
        this.setLevel(buf.readInt());
        var talent_options_size = buf.readInt();
        if (talent_options_size <= 0)
            return;
        for (int i = 0; i < talent_options_size; ++i) {
            var entry = talent_option_reader.apply(buf);
            readTalentOptionFromBuf(buf, entry);
        }
    }

    private <T> void readTalentOptionFromBuf(FriendlyByteBuf buf, TalentOption<T> entry) {
        T value = entry.decode(buf);
        if (value == null)
            value = entry.getDefault();
        this.setTalentOption(entry, value);
    }

    public final void writeInstance(AbstractDog dogIn, CompoundTag compound) {
        ResourceLocation rl = DoggyTalentsAPI.TALENTS.get().getKey(this.talent);
        if (rl != null) {
            compound.putString("type", rl.toString());
        }

        this.doWriteToNBT(dogIn, compound);
    }

    public static Optional<TalentInstance> readInstance(AbstractDog dogIn, CompoundTag compound) {
        ResourceLocation rl = ResourceLocation.tryParse(compound.getString("type"));
        if (DoggyTalentsAPI.TALENTS.get().containsKey(rl)) {
            TalentInstance inst = DoggyTalentsAPI.TALENTS.get().get(rl).getDefault();
            inst.doReadFromNBT(dogIn, compound);
            return Optional.of(inst);
        } else {
            DoggyTalentsAPI.LOGGER.warn("Failed to load talent {}", rl);
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends TalentInstance> T cast(Class<T> type) {
        if (this.getClass().isAssignableFrom(type)) {
            return (T) this;
        } else {
            throw new RuntimeException("Could not cast " + this.getClass().getName() + " to " + type.getName());
        }
    }

    @Override
    public String toString() {
        return String.format("%s [talent: %s, level: %d]", this.getClass().getSimpleName(), DoggyTalentsAPI.TALENTS.get().getKey(this.talent), this.level);
    }

    /**
     * Called when ever this instance is first added to a dog, this is called when
     * the level is first set on the dog or when it is loaded from NBT and when the
     * talents are synced to the client
     *
     * @param dogIn The dog
     */
    public void init(AbstractDog dogIn) {

    }

    /**
     * Called when the level of the dog changes
     * Is not called when the dog is loaded from NBT
     *
     * @param dogIn The dog
     */
    public void set(AbstractDog dog, int levelBefore) {

    }

    public final <T> T getTalentOptionOrDefault(TalentOption<T> entry) {
        var val = getTalentOption(entry);
        if (val == null) val = entry.getDefault();
        return (T) val;
    }

    public Object getTalentOption(TalentOption<?> entry) {
        return null;
    }

    public void setTalentOption(TalentOption<?> entry, Object data) {
    }

    public Collection<TalentOption<?>> getAllTalentOptions() {
        return List.of();
    }

    public void copyTalentOptionFrom(TalentInstance other) {
        var entries = this.getAllTalentOptions();
        if (entries.isEmpty())
            return;
        for (var entry : entries) {
            doCopyTalentOption(other, entry);
        }
    }

    private <T> void doCopyTalentOption(TalentInstance other, TalentOption<T> entry) {
        T val = other.getTalentOptionOrDefault(entry);
        this.setTalentOption(entry, val);
    } 

    public boolean hasRenderer() {
        return this.getTalent().hasRenderer();
    }
}
