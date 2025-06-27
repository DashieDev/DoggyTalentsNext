package doggytalents.common.data.forge_data;

import java.util.concurrent.CompletableFuture;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

public class ForgeDatapackProviderUtil {

    public static PackGenerator getBuiltinDatapack(DataGenerator gen, boolean run,
        String modId, String packId) {

        var path = gen.getPackOutput().getOutputFolder(PackOutput.Target.DATA_PACK)
             .resolve(modId).resolve("datapacks").resolve(packId);
        return new PackGenerator(gen, run, packId, new PackOutput(path));
    }

    public static class PackGenerator {
        private final DataGenerator parent;
        private final boolean toRun;
        private final String providerPrefix;
        private final PackOutput output;

        public PackGenerator(DataGenerator parent, boolean run, String providerPrefix, PackOutput output) {
            this.parent = parent;
            this.toRun = run;
            this.providerPrefix = providerPrefix;
            this.output = output;
        }

        public <T extends DataProvider> T addProvider(DataProvider.Factory<T> p_254382_) {
            var provider = p_254382_.create(this.output);
            parent.addProvider(toRun, new WrappedDataProviderWithPrefix(this.providerPrefix, provider));
            return provider;
        }
    }

    public static class WrappedDataProviderWithPrefix implements DataProvider {

        private final String prefix;
        private final DataProvider provider;

        public WrappedDataProviderWithPrefix(String prefix, DataProvider provider) {
            this.prefix = prefix;
            this.provider = provider;
        }

        @Override
        public CompletableFuture<?> run(CachedOutput output) {
            return this.provider.run(output);
        }

        @Override
        public String getName() {
            return this.prefix + "/" + this.provider.getName();
        }
    }    
}
