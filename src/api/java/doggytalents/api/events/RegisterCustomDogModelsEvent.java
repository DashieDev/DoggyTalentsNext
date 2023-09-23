package doggytalents.api.events;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;

public class RegisterCustomDogModelsEvent extends Event implements IModBusEvent {

    private List<Entry> entries;

    public RegisterCustomDogModelsEvent(List<Entry> entries) {
        this.entries = entries;
    }

    public void register(Entry entry) {
        this.entries.add(entry);
    }

    @Override
    public boolean isCancelable() {
        return false;
    }

    public static class Entry {
        public final ResourceLocation id;
        public final ModelLayerLocation layer;
        public final boolean shouldRenderAccessories;
        public final boolean shouldRenderIncapacitated;

        public Entry(ResourceLocation id, ModelLayerLocation layer, boolean accessory, boolean incap) {
            this.id = id;
            this.layer = layer;
            this.shouldRenderAccessories = accessory;
            this.shouldRenderIncapacitated = incap;
        }
    }
}
