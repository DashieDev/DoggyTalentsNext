package doggytalents;

import doggytalents.common.data.fabric_data.FabricDTRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DoggyTalentsNextEntryData implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();
        
        pack.addProvider(FabricDTRecipeProvider::new);
    }
    
}
