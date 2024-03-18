package doggytalents.client.data.fabric_data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;

public class DTModelProvider extends FabricModelProvider {

    public DTModelProvider(FabricDataOutput output) {
        super(output);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateItemModels'");
    }
    
}
