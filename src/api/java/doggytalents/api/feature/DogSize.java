package doggytalents.api.feature;

import java.util.Arrays;
import java.util.Comparator;

import net.minecraft.util.Mth;

public enum DogSize {
    PPP(0, 1),
    PIANISSIMO(1, 1 * 0.3F + 0.1F),
    PIANO(2, 2 * 0.3F + 0.1F),
    MODERATO(3, 1),
    FORTE(4, 4 * 0.3F + 0.1F),
    FORTISSIMO(5, 5 * 0.3F + 0.1F);

    public static final DogSize[] VALUES = 
        Arrays.stream(DogSize.values())
        .sorted(
            Comparator.comparingInt(DogSize::getId)
        ).toArray(size -> {
            return new DogSize[size];
        });

    private final int id;
    private final float scale;

    private DogSize(int id, float scale) {
        this.id = id;
        this.scale = scale;
    }

    public int getId() { return this.id; }
    public float getScale() { return this.scale; }

    public static DogSize fromId(int id) {
        if (id < 0 || id >= VALUES.length)
            id = 3;
        return VALUES[id];
    }

    public DogSize grow() {
        return VALUES[Mth.clamp(id + 1, 0, VALUES.length - 1)];
    }

    public DogSize shrink() {
        return VALUES[Mth.clamp(id - 1, 0, VALUES.length - 1)];
    }
}
