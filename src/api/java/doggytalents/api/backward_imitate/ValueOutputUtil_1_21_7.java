package doggytalents.api.backward_imitate;

import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.storage.ValueOutput;

public class ValueOutputUtil_1_21_7 {
    
    public static void outputTagTo(ValueOutput output, CompoundTag tag) {
        for (var entry : tag.entrySet()) {
            output.store(entry.getKey(), ExtraCodecs.NBT, entry.getValue());
        }
    }

    public static void outputTagTo(ValueOutput output, Consumer<CompoundTag> tagPopulator) {
        var tag = new CompoundTag();
        tagPopulator.accept(tag);
        outputTagTo(output, tag);
    }

}
