package doggytalents.client.entity.model.util;

import java.util.Optional;

import javax.annotation.Nullable;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.anim.AltDogAnimationSequences;
import doggytalents.api.anim.DogAnimation;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.api.registry.Accessory.AccessoryRenderType;
import doggytalents.client.entity.model.dog.CustomDogModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.dog.GlowingEyeDogModel;
import doggytalents.client.entity.model.util.DTNModelCodec.DogModelAccessoryProps;
import doggytalents.client.entity.model.util.DTNModelCodec.DogModelProps;
import doggytalents.client.entity.model.util.DTNModelCodec.ParsedModelResult;
import doggytalents.common.entity.Dog;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.util.Mth;

public class ParsedDogModel {

    public static DogModel create(ParsedModelResult result, DogModelProps props) {
        return props.legacyGlowingEyes() ?
            glowingEyesLegacy(result, props)
            : normal(result, props);
    }
    
    public static DogModel normal(ParsedModelResult result, DogModelProps props) {
        var layer = DTNModelCodec.layerDefinitionFromParsed(result);
        var baked = layer.bakeRoot();
        
        final var render_type = props.renderType();

        final var root_pivot = props.rootPivot().orElse(null);
        final boolean has_custom_scale = !Mth.equal(props.scale(), 1);
        final float custom_scale = props.scale();

        final boolean scale_baby = props.scaleBabyDog();
        final boolean wet_shade = props.wetShade();

        final boolean render_incap = props.renderIncap();
        final boolean render_talent = props.renderTalentModel();

        var accessory_props = props.accessoryProps();
        final var accessory_compat = accessory_props.compatabilityState();
        final boolean use_default_model = accessory_props.useDefaultModel();

        final boolean accesory_render = 
            accessory_compat != DogModel.AccessoryState.NON_COMPATIBLE;
        final boolean only_model_accessory = 
            accessory_compat == DogModel.AccessoryState.MODEL_ONLY;

        final var legacy_props = props.legacyProps();
        final boolean warn_head_legacy =
            legacy_props.warnHeadAccessory();
        final boolean use_alt_howl =
            legacy_props.useAltVictoryHowl();
        

        return new DogModel(baked, render_type) {
            @Override
            public @Nullable Vector3f getCustomRootPivotPoint() {
                return root_pivot;
            }
            @Override
            public boolean hasDefaultScale() {
                return has_custom_scale;
            }
            @Override
            public float getDefaultScale() {
                return custom_scale;
            }

            @Override
            public boolean scaleBabyDog() {
                return scale_baby;
            }
            @Override
            public boolean renderDogWetShade() {
                return wet_shade;
            }

            @Override
            public boolean incapShouldRender(Dog dog) {
                return render_incap;
            }
            @Override
            public boolean armorShouldRender(Dog dog) {
                return render_talent;
            }

            @Override
            public AccessoryState getAccessoryState() {
                return accessory_compat;
            }
            @Override
            public boolean useDefaultModelForAccessories() {
                return use_default_model;
            }
            @Override
            public boolean acessoryShouldRender(Dog dog, AccessoryInstance inst) {
                if (!only_model_accessory)
                    return inst.getAccessory()
                        .getAccessoryRenderType() == AccessoryRenderType.MODEL; 
                return accesory_render;
            }

            @Override
            public boolean warnAccessory(Dog dog, Accessory inst) {
                if (warn_head_legacy) {
                    return inst.getType() == DoggyAccessoryTypes.HEAD.get();
                }
                return super.warnAccessory(dog, inst);
            }
            @Override
            protected AnimationDefinition getAnimationSequence(DogAnimation anim) {
                if (use_alt_howl && anim == DogAnimation.HOWL)
                    return AltDogAnimationSequences.VICTORY_HOWL_ALT;
                return super.getAnimationSequence(anim);
            }
        };
    }

    public static DogModel glowingEyesLegacy(ParsedModelResult result, DogModelProps props) {
        var layer = DTNModelCodec.layerDefinitionFromParsed(result);
        var baked = layer.bakeRoot();

        final var render_type = props.renderType();

        final var root_pivot = props.rootPivot().orElse(null);
        final boolean has_custom_scale = !Mth.equal(props.scale(), 1);
        final float custom_scale = props.scale();

        final boolean scale_baby = props.scaleBabyDog();
        final boolean wet_shade = props.wetShade();

        final boolean render_incap = props.renderIncap();
        final boolean render_talent = props.renderTalentModel();

        var accessory_props = props.accessoryProps();
        final var accessory_compat = accessory_props.compatabilityState();
        final boolean use_default_model = accessory_props.useDefaultModel();

        final boolean accesory_render = 
            accessory_compat != DogModel.AccessoryState.NON_COMPATIBLE;
        final boolean only_model_accessory = 
            accessory_compat == DogModel.AccessoryState.MODEL_ONLY;

        final var legacy_props = props.legacyProps();
        final boolean warn_head_legacy =
            legacy_props.warnHeadAccessory();
        final boolean use_alt_howl =
            legacy_props.useAltVictoryHowl();
        

        return new GlowingEyeDogModel(baked, render_type) {
            @Override
            public @Nullable Vector3f getCustomRootPivotPoint() {
                return root_pivot;
            }
            @Override
            public boolean hasDefaultScale() {
                return has_custom_scale;
            }
            @Override
            public float getDefaultScale() {
                return custom_scale;
            }

            @Override
            public boolean scaleBabyDog() {
                return scale_baby;
            }
            @Override
            public boolean renderDogWetShade() {
                return wet_shade;
            }

            @Override
            public boolean incapShouldRender(Dog dog) {
                return render_incap;
            }
            @Override
            public boolean armorShouldRender(Dog dog) {
                return render_talent;
            }

            @Override
            public AccessoryState getAccessoryState() {
                return accessory_compat;
            }
            @Override
            public boolean useDefaultModelForAccessories() {
                return use_default_model;
            }
            @Override
            public boolean acessoryShouldRender(Dog dog, AccessoryInstance inst) {
                if (!only_model_accessory)
                    return inst.getAccessory()
                        .getAccessoryRenderType() == AccessoryRenderType.MODEL; 
                return accesory_render;
            }

            @Override
            public boolean warnAccessory(Dog dog, Accessory inst) {
                if (warn_head_legacy) {
                    return inst.getType() == DoggyAccessoryTypes.HEAD.get();
                }
                return super.warnAccessory(dog, inst);
            }
            @Override
            protected AnimationDefinition getAnimationSequence(DogAnimation anim) {
                if (use_alt_howl && anim == DogAnimation.HOWL)
                    return AltDogAnimationSequences.VICTORY_HOWL_ALT;
                return super.getAnimationSequence(anim);
            }
        };
    }

    public static DogModelProps propsFrom(DogModel model) {
        final var render_type = model.dogModelRendserType;

        var model_root_pivot = model.getCustomRootPivotPoint();
        if (model_root_pivot != null) {
            model_root_pivot = vec(model_root_pivot);
            DTNModelCodec.sanitizeEncodeVecMut(model_root_pivot);
        }
        final var root_pivot = Optional.ofNullable(model_root_pivot);
            
        final float scale = model.hasDefaultScale() ? 
            DTNModelCodec.roundModel(model.getDefaultScale())
            : 1;

        final boolean scale_baby = model.scaleBabyDog();
        final boolean wet_shade = model.renderDogWetShade();
        final boolean glowing_legacy =
            model instanceof GlowingEyeDogModel
            || (
                model instanceof CustomDogModel customModel
                && customModel.props.glowingEyes
            );

        final var accessory_compat = model.getAccessoryState();
        final boolean use_default_model = model.useDefaultModelForAccessories();

        final var default_props = DogModelProps.DEFAULT;
        return new DogModelProps(
            render_type, root_pivot, scale, 
            scale_baby, wet_shade, glowing_legacy, 
            default_props.renderIncap(), default_props.renderTalentModel(),
            new DogModelAccessoryProps(accessory_compat, use_default_model),
            default_props.legacyProps()
        );
    }

    private static Vector3f vec(Vector3fc vec) {
        return new Vector3f(vec);
    }
}
