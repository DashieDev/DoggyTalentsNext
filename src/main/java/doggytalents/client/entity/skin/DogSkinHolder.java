package doggytalents.client.entity.skin;

import doggytalents.client.DogTextureManager;
import doggytalents.common.entity.Dog;

public class DogSkinHolder {

    private static final DogSkinHolder NONE = new DogSkinHolder(null);

    private DogSkin skin = null;

    private DogSkinHolder(DogSkin skin) {
        this.skin = skin;
    }

    public DogSkin getOrElse(DogSkin skin) {
        return this.skin == null ? skin : this.skin;
    }

    public void invalidate() {
        this.skin = null;
    }

    public boolean isNone() {
        return this == NONE;
    }

    public static DogSkinHolder getNone() {
        return NONE;
    }

    public static DogSkinHolder update(Dog dog, DogSkinHolder current) {
        if (current.isNone())
            return current;
        if (current.skin != null)
            return current;

        var hash = dog.getSkinData().getHash();
        if (hash != null)
            return resolve(hash);

        return getNone();
    }

    private static DogSkinHolder resolve(String hash) {
        return DogTextureManager.INSTANCE.getDogSkin(hash);
    }

    public static DogSkinHolder resolved(DogSkin skin) {
        if (skin == null)
            return getNone();
        return new DogSkinHolder(skin);
    }

    public static DogSkinHolder pendingResolve() {
        return new DogSkinHolder(null);
    }

}