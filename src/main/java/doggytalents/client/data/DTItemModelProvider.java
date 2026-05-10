package doggytalents.client.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

/**
 * Stub datagen provider. Item model JSON files are committed to the repo; re-run datagen with
 * ./gradlew runData to regenerate them using the new net.minecraft.client.data.models API.
 */
public class DTItemModelProvider implements DataProvider {

    public DTItemModelProvider(PackOutput output) {
        // stub
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public String getName() {
        return "DoggyTalents Item Models (stub - needs rewrite for 26.1)";
    }
}
