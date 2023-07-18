package doggytalents.common.artifacts;

import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import doggytalents.api.inferface.IDogAlteration;
import doggytalents.common.item.DoggyArtifactItem;

/**
 * @Author DashieDev
 */
public class DoggyArtifact implements IDogAlteration {

    public static final int ARTIFACTS_LIMIT = 3;

    @Nonnull 
    private final Supplier<DoggyArtifactItem> returnItemSupplier;

    public DoggyArtifact(@Nonnull Supplier<DoggyArtifactItem> factory) {
        this.returnItemSupplier = factory;
    }

}
