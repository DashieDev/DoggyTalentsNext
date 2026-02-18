package doggytalents.client.entity.model.util;

import java.util.Optional;

import javax.annotation.Nullable;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import doggytalents.client.entity.model.dog.CustomDogModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.dog.GlowingEyeDogModel;
import doggytalents.client.entity.model.util.DTNModelCodec.DogModelAccessoryProps;
import doggytalents.client.entity.model.util.DTNModelCodec.DogModelProps;
import doggytalents.client.entity.model.util.DTNModelCodec.ParsedModelResult;
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

        var accessory_props = props.accessoryProps();
        final var accessory_compat = accessory_props.compatabilityState();
        final boolean use_default_model = accessory_props.useDefaultModel();

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
            public AccessoryState getAccessoryState() {
                return accessory_compat;
            }
            @Override
            public boolean useDefaultModelForAccessories() {
                return use_default_model;
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

        var accessory_props = props.accessoryProps();
        final var accessory_compat = accessory_props.compatabilityState();
        final boolean use_default_model = accessory_props.useDefaultModel();

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
            public AccessoryState getAccessoryState() {
                return accessory_compat;
            }
            @Override
            public boolean useDefaultModelForAccessories() {
                return use_default_model;
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

        return new DogModelProps(
            render_type, root_pivot, scale, 
            scale_baby, wet_shade, glowing_legacy, 
            new DogModelAccessoryProps(accessory_compat, use_default_model)
        );
    }

    private static Vector3f vec(Vector3fc vec) {
        return new Vector3f(vec);
    }
}
