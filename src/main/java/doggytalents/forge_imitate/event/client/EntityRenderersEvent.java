package doggytalents.forge_imitate.event.client;

import java.util.function.Function;
import java.util.function.Supplier;

import doggytalents.client.backward_imitate.LegacyBlockEntityRendererUtil_1_21_9.BlockEntityRenderer_1_21_9;
import doggytalents.client.backward_imitate.LegacyBlockEntityRendererUtil_1_21_9.WrappedBlockEntityRenderState;
import doggytalents.forge_imitate.event.Event;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry.TexturedModelDataProvider;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class EntityRenderersEvent {
    
    public static class RegisterLayerDefinitions extends Event {

        public RegisterLayerDefinitions() {
            
        }

        public void registerLayerDefinition(ModelLayerLocation modelLayer, TexturedModelDataProvider provider) {
            EntityModelLayerRegistry.registerModelLayer(modelLayer, provider);
        }

    }

    public static class RegisterRenderers extends Event {
        
        public RegisterRenderers() {
            
        }

        public <E extends Entity> void registerEntityRenderer(EntityType<E> entityType, EntityRendererProvider<E> entityRendererFactory) {
            EntityRenderers.register(entityType, entityRendererFactory);
        }

        public <E extends BlockEntity> void registerBlockEntityRenderer(BlockEntityType<E> blockEntityType, Function<BlockEntityRendererProvider.Context, BlockEntityRenderer_1_21_9<E>> prov) {
            BlockEntityRenderers.register(blockEntityType, prov::apply);
        }
        
    }

}
