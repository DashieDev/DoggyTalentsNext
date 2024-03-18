package doggytalents.forge_imitate.registry;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;

public class RegistryObject<T> implements Supplier<T> {
    
    private T value = null;
    private ResourceLocation name;

    public RegistryObject(ResourceLocation name) {
        this.name = name;
    }

    @Override
    public T get() {
        return this.value;
    }

    public ResourceLocation getId() {
        return this.name;
    }

    public void resolve(T data) {
        this.value = data;
    }

    public boolean isPresent() {
        return this.value != null;
    }

}
