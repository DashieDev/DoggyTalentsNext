package doggytalents.api.backward_imitate;

import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.TagValueOutput;
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

    public static void outputValueOutputTo(CompoundTag tag, HolderLookup.Provider prov, 
        Consumer<ValueOutput> valueOutputPopulator) {
        
        var reporter = new ProblemReporter.Collector();
        var tag_value_output = TagValueOutput.createWithContext(reporter, prov);
        valueOutputPopulator.accept(tag_value_output);
        if (!reporter.isEmpty())
            throw new IllegalArgumentException("Failed to populate Value Output: " + reporter.getReport());
        
        var tag_output = tag_value_output.buildResult();
        for (var entry : tag_output.entrySet()) {
            tag.put(entry.getKey(), entry.getValue());
        }
    }

}
