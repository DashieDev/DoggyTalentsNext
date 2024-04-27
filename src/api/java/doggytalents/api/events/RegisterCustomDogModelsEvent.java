package doggytalents.api.events;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.joml.Vector3f;

import com.google.common.collect.Maps;

import doggytalents.api.anim.DogAnimation;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

public class RegisterCustomDogModelsEvent extends Event implements IModBusEvent {

    private List<DogModelProps> entries;

    public RegisterCustomDogModelsEvent(List<DogModelProps> entries) {
        this.entries = entries;
    }

    public void register(DogModelProps.Builder builder) {
        this.entries.add(builder.build());
    }

    // @Override
    // public boolean isCancelable() {
    //     return false;
    // }

    public static class DogModelProps {
        public final ResourceLocation id;
        public final ModelLayerLocation layer;
        public final boolean shouldRenderAccessories;
        public final boolean shouldRenderIncapacitated;
        public final boolean hasDefaultScale;
        public final Vector3f customRootPivot;
        public final float defaultScale;
        public final boolean glowingEyes;
        private Map<DogAnimation, AnimationDefinition> aninationOverride = Maps.newConcurrentMap();

        private DogModelProps(ResourceLocation id, ModelLayerLocation layer, boolean accessory, boolean incap,
            Vector3f customPivot, float defaulScale, boolean glowingEyes) {
            this.id = id;
            this.layer = layer;
            this.shouldRenderAccessories = accessory;
            this.shouldRenderIncapacitated = incap;
            this.defaultScale = defaulScale;
            this.hasDefaultScale = this.defaultScale != 1f;
            this.customRootPivot = customPivot;
            this.glowingEyes = glowingEyes;
        }

        public Map<DogAnimation, AnimationDefinition> getAnimOverride() {
            return this.aninationOverride;
        }

        public static class Builder {
            public final ResourceLocation id;
            public final ModelLayerLocation layer;
            private boolean accessory = false, incap = false;
            private Vector3f customRootPivot = null;
            private float defaultScale = 1f;
            private boolean glowingEyes = false;
            private Map<DogAnimation, AnimationDefinition> animationOverride = Maps.newConcurrentMap();

            public Builder(ResourceLocation id, ModelLayerLocation layer) {
                this.id = id;
                this.layer = layer;
            }

            public Builder withAccessory() {
                this.accessory = true;
                return this;
            }

            public Builder withIncap() {
                this.incap = true;
                return this;
            }

            public Builder withCustomRootPivot(Vector3f pivot) {
                this.customRootPivot = pivot;
                return this;
            }

            public Builder withDefaultScale(float scale) {
                this.defaultScale = scale;
                return this;
            }

            public Builder withGlowingEyes() {
                this.glowingEyes = true;
                return this;
            }

            public Builder withCustomAnim(DogAnimation anim, AnimationDefinition seq) {
                this.animationOverride.put(anim, seq);
                return this;
            }

            public DogModelProps build() {
                var ret =  new DogModelProps(id, layer, accessory, incap, customRootPivot, defaultScale, glowingEyes);
                ret.aninationOverride = this.animationOverride;
                return ret;
            }

        }
    }
}
