package doggytalents.common.entity.texture;

import java.util.Arrays;
import java.util.Comparator;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class DogSkinData {
    
    public static DogSkinData NULL = new DogSkinData("", Version.VERSION_0);
    private String hash = "";
    private Version version = Version.VERSION_0;

    public DogSkinData(String hash, Version version) {
        this.hash = hash == null ? "" : hash;
        this.version = version == null ? Version.VERSION_0 : version;
    }

    public String getHash() {
        return this.hash;
    }

    public Version getVersion() {
        return this.version;
    }

    public DogSkinData copy() {
        return new DogSkinData(hash, version);
    }

    
    public void save(CompoundTag tag) {
        var tag0 = new CompoundTag();
        tag0.putInt("version", this.version.getId());
        tag0.putString("hash", this.hash);
        tag.put("doggytalents_dog_skin", tag0);
    }
    
    public static DogSkinData readFromTag(CompoundTag compound) {
        if (compound.contains("doggytalents_dog_skin", Tag.TAG_COMPOUND)) {
            return readNewer(compound.getCompound("doggytalents_dog_skin"));    
        }
        if (compound.contains("customSkinHash", Tag.TAG_STRING)) {
            return readFromString(compound.getString("customSkinHash"));
        }
        return DogSkinData.NULL;
    }

    private static DogSkinData readNewer(CompoundTag tag) {
        int version_int = tag.getInt("version");
        String hash = tag.getString("hash");
        return new DogSkinData(hash, Version.fromId(version_int));
    }

    private static DogSkinData readFromString(String hash) {
        return new DogSkinData(hash, Version.VERSION_0);
    }

    public static enum Version {
        /**
         * Version 0 the hash is computed from the texture bytes.
         */
        VERSION_0(0),
        /**
         * Version 1 the hash is computed from the skin.id appended by the texture png bytes
         */
        VERSION_1(1);

        private final int id;
        private static final Version[] VALUES =
            Arrays.stream(Version.values())
            .sorted(
                Comparator.comparingInt(Version::getId)
            ).toArray(size -> {
                return new Version[size];
            });

        private Version(int id) {
            this.id = id;
        }

        public int getId() { return this.id; }
        
        public static Version fromId(int id) {
            if (id < 0 || id >= VALUES.length)
                id = 3;
            return VALUES[id];
        }

    }

}
