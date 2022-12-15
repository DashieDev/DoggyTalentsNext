package doggytalents.common.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import doggytalents.common.lib.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DTBlockTagsProvider extends BlockTagsProvider {

    public DTBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "DoggyTalents Block Tags";
    }

    @Override
    protected void addTags(Provider p_256380_) {
        // TODO Auto-generated method stub
        
    }
}
