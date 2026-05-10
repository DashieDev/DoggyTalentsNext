package doggytalents.common.data.neoforge_data;

import java.util.concurrent.CompletableFuture;

import doggytalents.common.item.itemgroup.DTNCompostables;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

public class DTNNeoForgeComposterProvider extends DataMapProvider {

    protected DTNNeoForgeComposterProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(net.minecraft.core.HolderLookup.Provider provider) {
        var compostables = DTNCompostables.getCompostables();
        var builder = this.builder(NeoForgeDataMaps.COMPOSTABLES);
        compostables.forEach((item, chance) -> {
            var id = BuiltInRegistries.ITEM.getResourceKey(item);
            if (!id.isPresent())
                return;
            builder.add(id.get(), new Compostable(chance), false);
        });
    }
    
    

}
