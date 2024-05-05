package doggytalents.forge_imitate.registry;

import java.util.function.Supplier;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

public class RegistryObject<T> implements Supplier<T> {
    
    private T value = null;
    private Holder<T> holder = null;
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

    public void resolve(Holder<T> holder) {
        this.value = holder.value();
        this.holder = holder;
    }

    public boolean isPresent() {
        return this.value != null;
    }

    public Holder<T> holder() {
        return this.holder;
    }

}
