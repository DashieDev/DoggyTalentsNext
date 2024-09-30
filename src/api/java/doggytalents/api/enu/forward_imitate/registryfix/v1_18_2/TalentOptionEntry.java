package doggytalents.api.enu.forward_imitate.registryfix.v1_18_2;

import doggytalents.api.registry.TalentOption;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class TalentOptionEntry extends ForgeRegistryEntry<TalentOptionEntry> {
    
    private TalentOption<?> val;

    private TalentOptionEntry(TalentOption<?> val) {
        this.val = val;
    }

    public TalentOption<?> getVal() {
        return this.val;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TalentOptionEntry other_entry))
            return false;
        return this.val == other_entry.val;
    }

    @Override
    public int hashCode() {
        return this.val.hashCode();
    }

    public static TalentOptionEntry of(TalentOption<?> option) {
        return new TalentOptionEntry(option);
    }

}
