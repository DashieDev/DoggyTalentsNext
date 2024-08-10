package doggytalents.common.variant;

import java.util.Optional;

import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.resources.ResourceLocation;

public class DogVariant {
    
    public static final DogVariant PALE = new DogVariant(DogVariant.propsVanilla("pale")
        .icon(Util.getResource("textures/entity/dog/classical_icon/pale.png"))
        .guiColor(0xffdad7d8));

    private final ResourceLocation id;
    private final ResourceLocation texture;
    private final String translation;
    private final boolean isVanila;
    private Optional<ResourceLocation> icon;
    private int guiColor;
    private Optional<ResourceLocation> glowingOverlay;
    private Optional<ResourceLocation> customInjuredTexture;
    private final boolean swimUnderwater;
    private final boolean burnsPetter;

    public DogVariant(Props props) {
        this.id = props.name;
        this.texture = createTextureLoc(props);
        this.translation = createTranslationKey(props);
        this.isVanila = props.namespaceType == NamespaceType.VANILLA;
        this.icon = props.icon;
        this.guiColor = props.guiColor;
        this.glowingOverlay = props.glowingOverlay;
        this.customInjuredTexture = props.customInjuredTexture;
        this.canSwimUnderWater = props.swimUnderwater;
        this.burnsPetter = props.burnsPetter; 
    }

    private static ResourceLocation createTextureLoc(Props props) {
        if (props.namespaceType == NamespaceType.DTN)
            return Util.getResource("textures/entity/dog/classical/compl/wolf_" + props.name.getPath() + ".png");
        if (props.namespaceType == NamespaceType.VANILLA)
            return Util.getResource("textures/entity/dog/classical/wolf_" + props.name.getPath() + ".png");
        if (props.customTexture.isPresent())
            return Util.modifyPath(props.customTexture.get(), x -> x + ".png");
        return Util.getResource(props.name.getNamespace(), "textures/entity/dog/custom_variants/wolf_" 
            + props.name.getPath() + ".png");
    }

    private static String createTranslationKey(Props props) {
        if (props.namespaceType == NamespaceType.DTN)
            return "dog.classical.variant.compl." + props.name.getPath();
        if (props.namespaceType == NamespaceType.VANILLA)
            return "dog.classical.variant." + props.name.getPath();
        if (props.customTranslation.isPresent())
            return props.customTranslation.get();
        return "dog.classical.variant.custom." + props.name.getNamespace() + "." + props.name.getPath();
    }

    public ResourceLocation id() {
        return this.id;
    }

    public ResourceLocation texture() {
        return this.texture;
    }

    public Optional<ResourceLocation> icon() {
        return this.icon;
    }

    public int guiColor() {
        return this.guiColor;
    }

    public String translation() {
        return this.translation;
    }

    public Optional<ResourceLocation> glowingOverlay() {
        return this.glowingOverlay;
    }

    public Optional<ResourceLocation> customInjuredTexture() {
        return this.customInjuredTexture;
    }

    public boolean burnsPetter() {
        return this.burnsPetter;
    }

    public boolean swimUnderwater() {
        return this.swimUnderwater;
    }

    public static Props propsVanilla(String name) {
        return new Props(Util.getVanillaResource(name), NamespaceType.VANILLA);
    }

    public static Props props(String name) {
        return new Props(Util.getResource(name), NamespaceType.DTN);
    }

    public static Props props(ResourceLocation name) {
        if (name.getNamespace().equals(Constants.MOD_ID))
            return props(name.getPath());
        if (name.getNamespace().equals("minecraft"))
            return propsVanilla(name.getPath());
        return new Props(name, NamespaceType.OTHER);
    }

    public static class Props {
        
        private final NamespaceType namespaceType;
        private final ResourceLocation name;
        private Optional<ResourceLocation> icon = Optional.empty();
        private int guiColor = 0xffdad7d8;
        private Optional<ResourceLocation> customTexture = Optional.empty();
        private Optional<String> customTranslation = Optional.empty();
        private Optional<ResourceLocation> glowingOverlay = Optional.empty();
        private Optional<ResourceLocation> customInjuredTexture = Optional.empty();
        private boolean burnsPetter = false;
        private boolean swimUnderwater = false;

        private Props(ResourceLocation name, NamespaceType namespaceType) {
            this.name = name;
            this.namespaceType = namespaceType;
        }

        public Props icon(ResourceLocation icon) {
            this.customTexture = Optional.of(icon);
            return this;
        }

        public Props guiColor(int color) {
            this.guiColor = color;
            return this;
        }

        public Props customTexture(ResourceLocation texture) {
            this.customTexture = Optional.of(texture);
            return this;
        }

        public Props customTranslation(String string) {
            this.customTranslation = Optional.of(string);
            return this;
        }

        public Props glowingOverlay(ResourceLocation overlay) {
            if (overlay == null)
                return this;
            this.glowingOverlay = Optional.of(overlay);
            return this;
        }

        public Props customInjuredTexture(ResourceLocation texture) {
            if (texture == null)
                return this;
            this.customInjuredTexture = Optional.of(texture);
            return this;
        }

        public Props burnsPetter() {
            this.burnsPetter = true;
            return this;
        }

        public Props swimUnderwater() {
            this.swimUnderwater = true;
            return this;
        }

    }

    public static enum NamespaceType { VANILLA, DTN, OTHER }

}
