package doggytalents.forge_imitate.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import com.google.common.collect.Maps;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class DeferredRegister<T> {
    
    private Map<RegistryObject<T>, Supplier<? extends T>> toResolve = Maps.newHashMap();
    private final List<RegistryObject<T>> orderedView = new ArrayList<>();
    private Supplier<Registry<T>> registry;
    private String modid = "";

    private DeferredRegister(Supplier<Registry<T>> reg, String modid) {
        this.registry = reg;
        this.modid = modid;
    }

    public void initAll() {
        for (var entry : toResolve.entrySet()) {
            var regObj = entry.getKey();
            var value = entry.getValue().get();
            var registry = this.registry.get();
            var holder = Registry.registerForHolder(registry, regObj.getId(), value);
            regObj.resolve(holder);
        }
    }

    public <T1 extends T> RegistryObject<T1> register(String name, Supplier<T1> sup) {
        var regObj = new RegistryObject<T1>(new ResourceLocation(modid, name));
        toResolve.put((RegistryObject<T>) regObj, sup);
        orderedView.add((RegistryObject<T>) regObj);
        return regObj;
    }

    public Collection<RegistryObject<T>> getEntries() {
        return Collections.unmodifiableList(this.orderedView);
    }

    public static <T> DeferredRegister<T> create(Supplier<Registry<T>> registry, String modid) {
        return new DeferredRegister<T>(registry, modid);
    }

}
