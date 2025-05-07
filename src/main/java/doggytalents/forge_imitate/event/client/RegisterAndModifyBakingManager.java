package doggytalents.forge_imitate.event.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import doggytalents.DoggyBlocks;
import doggytalents.client.backward_imitate.DogBedItemModel_1_21_5;
import doggytalents.client.backward_imitate.DogBedModifyingBakingResult_1_21_5;
import doggytalents.client.backward_imitate.fabric_util.LateResolveItemModel_1_21_5;
import doggytalents.client.block.model.DogBedModel;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import doggytalents.forge_imitate.event.Event;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin.Context;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class RegisterAndModifyBakingManager {
    
    private static DTNModelLoadingHook HOOK_INST = new DTNModelLoadingHook();

    private static DogBedModelHook DOG_BED_HOOK = new DogBedModelHook();

    public static void init() {
        ModelLoadingPlugin.register(HOOK_INST);
    }

    public static class DTNModelLoadingHook implements ModelLoadingPlugin {

        @Override
        public void initialize(Context ctx) {
            doRegisterLoc(ctx);
            doRegisterModifyAfterBake(ctx);
            doRegisterItemModelModifyAfterBake_1_21_5(ctx);
        }

        private void doRegisterLoc(Context ctx) {
            //DOG_BED_HOOK.addModels(ctx);
        }

        private void doRegisterModifyAfterBake(Context ctx) {
            ctx.modifyBlockModelAfterBake().register((baked, baked_ctx) -> {
                return DOG_BED_HOOK.onModifyModel(baked, baked_ctx);
            });
        }

        private void doRegisterItemModelModifyAfterBake_1_21_5(Context ctx) {
            ctx.modifyItemModelAfterBake().register((baked, baked_ctx) -> {
                return DOG_BED_HOOK.onModifyItemModel_1_21_5(baked, baked_ctx);
            });
        }

    }

    private static class DogBedModelHook {

        // public void addModels(Context ctx) {
        //     var bedKey = DoggyBlocks.DOG_BED.getId();
        //     var bedModelKey = makeBlockodelLoc(bedKey);
        //     ctx.addModels(List.of(bedModelKey));
        // }

        private ResourceLocation makeBlockodelLoc(ResourceLocation inLoc) {
            return Util.getResource(inLoc.getNamespace(), "block/" + inLoc.getPath());
        }

        private BlockStateModel onModifyModel(BlockStateModel current_model, net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier.AfterBakeBlock.Context
        ctx) {
            if (!isTargetingDogBed(ctx))
                return current_model;
            
            if (ConfigHandler.CLIENT.DOGBED_FORCE_DEFAULT_MODEL.get())
                return current_model;
            
            var bakery = ctx.baker();
                
            var bedKey = DoggyBlocks.DOG_BED.getId();
            var unbaked_bedModelKey = makeBlockodelLoc(bedKey);
            var modelUnbaked = bakery.getModel(unbaked_bedModelKey);
            if (!(modelUnbaked.wrapped() instanceof BlockModel block_modelUnbaked))
                return current_model;

            var modedBaked = current_model;
            var dogBedModel = new DogBedModel(bakery, block_modelUnbaked, modedBaked, ConfigHandler.CLIENT.MAX_DOG_BED_MODEL_CACHE.get());
            resolveDogBedItemModel_1_21_5(dogBedModel, modelUnbaked, modedBaked);
            return dogBedModel;
        }

        private boolean isTargetingDogBed(net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier.AfterBakeBlock.Context ctx) {
            // var targetId = ctx.topLevelId();
            // if (targetId == null)
            //     return false;
            // var namespace = targetId.id().getNamespace();
            // if (!namespace.equals(Constants.MOD_ID))
            //     return false;
            // var modelLoc = targetId;
            
            // var modelLocTarget = modelLoc.id().getPath();
            // var modelLocNamespace = modelLoc.id().getNamespace();
            // var dogBedLoc = DoggyBlocks.DOG_BED.getId();
            // if (!modelLocNamespace.equals(dogBedLoc.getNamespace()))
            //     return false;
            // if (!modelLocTarget.equals(dogBedLoc.getPath()))
            //     return false;
            // return true;
            return ctx.state().getBlock() == DoggyBlocks.DOG_BED.get();
        }

        public ItemModel onModifyItemModel_1_21_5(ItemModel old_model, net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier.AfterBakeItem.Context ctx) {
            var bed_item_id = BuiltInRegistries.ITEM.getKey(DoggyBlocks.DOG_BED.get().asItem());
            if (!ctx.itemId().equals(bed_item_id))
                return old_model;
            return late_resolve_item_model_1_21_5;
        };

        public void resolveDogBedItemModel_1_21_5(DogBedModel dog_bed_model, ResolvedModel resolved_model, BlockStateModel default_baked_model) {
            var render_props = DogBedModifyingBakingResult_1_21_5.renderPopsForDogBedItemModel(resolved_model, default_baked_model);
            var resolved =  new DogBedItemModel_1_21_5(dog_bed_model, render_props);
            late_resolve_item_model_1_21_5.resolve(resolved);
        }

        private static final LateResolveItemModel_1_21_5 late_resolve_item_model_1_21_5 = new LateResolveItemModel_1_21_5(); 
    }
}
