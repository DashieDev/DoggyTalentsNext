package doggytalents;

import doggytalents.common.backward_imitate.fabric_util.FabricDTNRecipeProvider_1_21_3;
import doggytalents.common.data.fabric_data.FabricDTRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DoggyTalentsNextEntryData implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();
        
        pack.addProvider(FabricDTNRecipeProvider_1_21_3::new);
    }
    
}
