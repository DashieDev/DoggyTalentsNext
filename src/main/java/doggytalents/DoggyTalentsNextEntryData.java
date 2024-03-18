package doggytalents;

import doggytalents.common.data.fabric_data.DTAdvancementProvider;
import doggytalents.common.data.fabric_data.DTBlockTagsProvider;
import doggytalents.common.data.fabric_data.DTEntityTagsProvider;
import doggytalents.common.data.fabric_data.DTItemTagsProvider;
import doggytalents.common.data.fabric_data.DTLootTableProvider;
import doggytalents.common.data.fabric_data.DTRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DoggyTalentsNextEntryData implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();
        
        pack.addProvider(DTAdvancementProvider::new);
        //pack.addProvider(DTBlockTagsProvider::new);
        pack.addProvider(DTEntityTagsProvider::new);
        pack.addProvider(DTItemTagsProvider::new);
        pack.addProvider(DTLootTableProvider::new);
        pack.addProvider(DTRecipeProvider::new);
    }
    
}
