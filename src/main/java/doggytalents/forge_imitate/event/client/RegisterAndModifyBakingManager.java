package doggytalents.forge_imitate.event.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import doggytalents.DoggyBlocks;
import doggytalents.client.block.model.DogBedModel;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import doggytalents.forge_imitate.event.Event;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin.Context;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
        public void onInitializeModelLoader(Context ctx) {
            doRegisterLoc(ctx);
            doRegisterModifyAfterBake(ctx);
        }

        private void doRegisterLoc(Context ctx) {
            DOG_BED_HOOK.addModels(ctx);
        }

        private void doRegisterModifyAfterBake(Context ctx) {
            ctx.modifyModelAfterBake().register((baked, baked_ctx) -> {
                return DOG_BED_HOOK.onModifyModel(baked, baked_ctx);
            });
        }

    }

    private static class DogBedModelHook {

        public void addModels(Context ctx) {
            var bedKey = DoggyBlocks.DOG_BED.getId();
            var bedModelKey = makeBlockodelLoc(bedKey);
            ctx.addModels(List.of(bedModelKey));
        }

        private ResourceLocation makeBlockodelLoc(ResourceLocation inLoc) {
            return Util.getResource(inLoc.getNamespace(), "block/" + inLoc.getPath());
        }

        private BakedModel onModifyModel(BakedModel current_model, net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier.AfterBake.Context ctx) {
            if (!isTargetingDogBed(ctx))
                return current_model;
            
            if (ConfigHandler.CLIENT.DOGBED_FORCE_DEFAULT_MODEL.get())
                return current_model;
            
            var bakery = ctx.loader();
                
            var bedKey = DoggyBlocks.DOG_BED.getId();
            var unbaked_bedModelKey = makeBlockodelLoc(bedKey);
            var modelUnbaked = bakery.getModel(unbaked_bedModelKey);
            if (!(modelUnbaked instanceof BlockModel block_modelUnbaked))
                return current_model;

            var modedBaked = current_model;
            var dogBedModel = new DogBedModel(bakery, block_modelUnbaked, modedBaked);
            return dogBedModel;
        }

        private boolean isTargetingDogBed(net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier.AfterBake.Context ctx) {
            var targetId = ctx.topLevelId();
            if (targetId == null)
                return false;
            var namespace = targetId.id().getNamespace();
            if (!namespace.equals(Constants.MOD_ID))
                return false;
            var modelLoc = targetId;
            
            var modelLocTarget = modelLoc.id().getPath();
            var modelLocNamespace = modelLoc.id().getNamespace();
            var dogBedLoc = DoggyBlocks.DOG_BED.getId();
            if (!modelLocNamespace.equals(dogBedLoc.getNamespace()))
                return false;
            if (!modelLocTarget.equals(dogBedLoc.getPath()))
                return false;
            return true;
        }

    }

}
