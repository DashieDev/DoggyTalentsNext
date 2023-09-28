package doggytalents.client.entity.skin;

import javax.annotation.Nonnull;

import com.google.common.base.Supplier;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.model.DogModelRegistry.DogModelHolder;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import net.minecraft.resources.ResourceLocation;

public class DogSkin {

    public static final DogSkin CLASSICAL = (new DogSkin(Resources.ENTITY_WOLF){
        @Override
        public ResourceLocation getPath() {
            if (ConfigHandler.CLIENT.USE_PROVIDED_COPY_FOR_CLASSICAL.get())
                return Resources.DOG_CLASSICAL;
            return super.getPath();
        }

        @Override
        public boolean hasExtraInfo() {
            return true;
        }
        @Override
        public String getDesc() {
            return "The Default Skin. Mhmmmm! Classy!";
        }
    }).setName("Classical");
    public static final DogSkin MISSING = (new DogSkin(Resources.ENTITY_WOLF){
        @Override
        public ResourceLocation getPath() {
            if (ConfigHandler.CLIENT.USE_PROVIDED_COPY_FOR_CLASSICAL.get())
                return Resources.DOG_CLASSICAL;
            return super.getPath();
        }
    }).setName("<Missing>");

    private String name = "";
    private ResourceLocation texturePath;
    private boolean useCustomModel;
    private DogModelHolder customModelHolder;
    private byte tail = 0, ear = 0;

    //Extra info
    private boolean hasExtraInfo = false;
    private String basedOn = "";
    private String author = "";
    private String fromPack = "";
    private String description = "";
    private String tags = "";

    public DogSkin(ResourceLocation path) {
        this.texturePath = path;
        this.useCustomModel = false;
    }

    public DogSkin(ResourceLocation path, @Nonnull DogModelHolder model) {
        this.texturePath = path;
        this.useCustomModel = true;
        this.customModelHolder = model;
    }

    public DogSkin setName(String s) {
        if (s == null) this.name = "";
        else this.name = s;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setTail(byte val) {
        this.tail = val;
    }

    public byte getTail() {
        return this.tail;
    }

    public void setEar(byte val) {
        this.ear = val;
    } 

    public byte getEar() {
        return this.ear;
    }

    public boolean useCustomModel() {
        return this.useCustomModel;
    }

    public DogModelHolder getCustomModel() {
        return this.customModelHolder;
    }

    public ResourceLocation getPath() {
        return this.texturePath;
    }

    public void setAuthor(String author) {
        this.hasExtraInfo = true;
        if (author == null) this.author = "";
        else this.author = author;
    }

    public void setBasedOn(String basedOn) {
        this.hasExtraInfo = true;
        if (basedOn == null) this.basedOn = "";
        else this.basedOn = basedOn;
    }

    public void setDesc(String desc) {
        this.hasExtraInfo = true;
        if (desc == null) this.description = "";
        else this.description = desc;
    }

    public void setTags(String tags) {
        this.hasExtraInfo = true;
        if (tags == null) this.tags = "";
        else this.tags = tags;
    }

    public void setPack(String fromPack) {
        this.hasExtraInfo = true;
        if (fromPack == null) this.fromPack = "";
        else this.fromPack = fromPack;
    }

    public boolean hasExtraInfo() { return this.hasExtraInfo; }
    public String getBasedOn() { return this.basedOn; }
    public String getAuthor() { return this.author; }
    public String getPack() { return this.fromPack; }
    public String getDesc() { return this.description; }
    public String getTags() { return this.tags; }

}
