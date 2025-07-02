package doggytalents.api.backward_imitate;

import doggytalents.DoggyTalentsNext;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.TagValueInput;
import net.neoforged.neoforge.items.ItemStackHandler;

//Override to get the serializeNBT and deserializeNBT back for now
//Use forge_imitate ItemStackHandler on Fabric side instead since it already contains thsoe methods
public class ItemStackHandler_1_21_7 extends ItemStackHandler {

    public ItemStackHandler_1_21_7(int slots) {
        super(slots);
    }
    
    public CompoundTag serializeNBT(HolderLookup.Provider prov) {
        var compound = new CompoundTag();
        ValueOutputUtil_1_21_7.outputValueOutputTo(compound, prov, 
            value_output -> this.serialize(value_output));
        return compound;
    }
        
    public void deserializeNBT(HolderLookup.Provider prov, CompoundTag compound) {
        var reporter = new ProblemReporter.Collector();
        var value_input = TagValueInput.create(reporter, prov, compound);
        this.deserialize(value_input);
        if (!reporter.isEmpty()) {
            DoggyTalentsNext.LOGGER.error("Failed to populate Value Output: " + reporter.getReport());
        }
    }

}
