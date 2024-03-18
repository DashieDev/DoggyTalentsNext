package doggytalents.forge_imitate.event.client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import doggytalents.common.util.Util;
import doggytalents.forge_imitate.event.Event;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

public class RegisterClientReloadListenersEvent extends Event {

    public RegisterClientReloadListenersEvent() {
    }

    public void registerReloadListener(PreparableReloadListener listener, String name) {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES)
            .registerReloadListener(new FabricReloadListenerWrapper(listener, Util.getResource(name)));
    }

    public static class FabricReloadListenerWrapper implements IdentifiableResourceReloadListener {

        private final PreparableReloadListener wrapped;
        private final ResourceLocation id;

        public FabricReloadListenerWrapper(PreparableReloadListener wrapped, ResourceLocation loc) {
            this.wrapped = wrapped;
            this.id = loc;
        }
        
        @Override
        public CompletableFuture<Void> reload(PreparationBarrier var1, ResourceManager var2, ProfilerFiller var3,
                ProfilerFiller var4, Executor var5, Executor var6) {
            return this.wrapped.reload(var1, var2, var3, var4, var5, var6);
        }

        @Override
        public ResourceLocation getFabricId() {
            return this.id;
        }

    }

}
