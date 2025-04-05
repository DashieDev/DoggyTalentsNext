package doggytalents.client.backward_imitate;

import doggytalents.DoggyBlocks;
import doggytalents.client.block.model.DogBedModel;
import doggytalents.common.backward_imitate.DogBedItemProps_1_21_5;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.util.Util;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.event.ModelEvent;

public class DogBedModifyingBakingResult_1_21_5 {
    
    public static void modifyBakedModels(final ModelEvent.ModifyBakingResult event) {
        var baking_result = event.getBakingResult();
        var default_bed_state = DoggyBlocks.DOG_BED.get().defaultBlockState();
        var default_baked_model = baking_result.blockStateModels().get(default_bed_state);
        
        var unbaked_model_loc = getBlockModelLocation(DoggyBlocks.DOG_BED.get());
        var unbaked_model = (BlockModel) event.getModelBakery().resolvedModels.get(unbaked_model_loc).wrapped();
        
        var dog_bed_model = new DogBedModel(event.getModelBakery(), unbaked_model, default_baked_model, ConfigHandler.CLIENT.MAX_DOG_BED_MODEL_CACHE.get());
    
        DoggyBlocks.DOG_BED.get().getStateDefinition().getPossibleStates().forEach(state -> {
            baking_result.blockStateModels().put(state, dog_bed_model);
        });

        //ItemStackModel
        baking_result.itemStackModels().put(DogBedItemProps_1_21_5.dogBedModelLocation(), new DogBedItemModel_1_21_5(dog_bed_model));
    }

    private static ResourceLocation getBlockModelLocation(Block block) {
        var block_id = BuiltInRegistries.BLOCK.getKey(block);
        return Util.modifyPath(block_id, x -> "block/" + x);
    }

}
