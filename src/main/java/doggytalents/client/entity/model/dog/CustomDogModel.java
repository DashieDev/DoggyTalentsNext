package doggytalents.client.entity.model.dog;

import javax.annotation.Nullable;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import doggytalents.api.events.RegisterCustomDogModelsEvent.DogModelProps;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;

public class CustomDogModel extends DogModel {

    private final DogModelProps props;
    private ModelPart glowingEyes;
    private ModelPart realGlowingEyes;


    public CustomDogModel(ModelPart box, DogModelProps props) {
        super(box);
        this.props = props;
        if (this.props.glowingEyes)
            setupGlowingEyes();
    }

    private void setupGlowingEyes() {
        this.glowingEyes = this.root.getChild("glowing_eyes");
        this.realGlowingEyes = glowingEyes.getChild("real_glowing_eyes");
    }

    @Override
    public boolean acessoryShouldRender(Dog dog, AccessoryInstance inst) {
        return props.shouldRenderAccessories;
    }

    @Override
    public boolean incapShouldRender(Dog dog) {
        return props.shouldRenderIncapacitated;
    }

    @Override
    public boolean scaleBabyDog() {
        return false;
    }

    @Override
    public @Nullable Vector3f getCustomRootPivotPoint() {
        return this.props.customRootPivot;
    }

    @Override
    public boolean hasDefaultScale() {
        return this.props.hasDefaultScale;
    }

    @Override
    public float getDefaultScale() {
        return this.props.defaultScale;
    }

    @Override
    public boolean armorShouldRender(Dog dog) {
        return false;
    }

    @Override
    public void setupAnim(Dog dog, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch) {
        super.setupAnim(dog, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (this.props.glowingEyes) {
            this.glowingEyes.copyFrom(this.head);
            this.realGlowingEyes.copyFrom(this.realHead);
            this.glowingEyes.visible = this.head.visible;
            this.realGlowingEyes.visible = this.realHead.visible;
        }
    }

    @Override
    public void renderToBuffer(PoseStack p_102034_, VertexConsumer p_102035_, int p_102036_, int p_102037_, float p_102038_, float p_102039_, float p_102040_, float p_102041_) {
        var pivot = DEFAULT_ROOT_PIVOT;
        var custom_pivot = getCustomRootPivotPoint();
        if (custom_pivot != null) {
            pivot = custom_pivot;
        }
        p_102034_.pushPose();
        p_102034_.translate((double)(root.x / 16.0F), (double)(root.y / 16.0F), (double)(root.z / 16.0F));
        p_102034_.translate((double)(pivot.x / 16.0F), (double)(pivot.y / 16.0F), (double)(pivot.z / 16.0F));
        if (root.zRot != 0.0F) {
            p_102034_.mulPose(Axis.ZP.rotation(root.zRot));
        }

        if (root.yRot != 0.0F) {
            p_102034_.mulPose(Axis.YP.rotation(root.yRot));
        }

        if (root.xRot != 0.0F) {
            p_102034_.mulPose(Axis.XP.rotation(root.xRot));
        }
        float xRot0 = root.xRot, yRot0 = root.yRot, zRot0 = root.zRot;
        float x0 = root.x, y0 = root.y, z0 = root.z;
        root.xRot = 0; root.yRot = 0; root.zRot = 0;
        root.x = 0; root.y = 0; root.z = 0;
        p_102034_.pushPose();
        p_102034_.translate((double)(-pivot.x / 16.0F), (double)(-pivot.y / 16.0F), (double)(-pivot.z / 16.0F));
        
        if (this.young && this.scaleBabyDog()) {

            boolean headVisible0 = this.head.visible;
            
            this.head.visible = false;
            p_102034_.pushPose();
            //float f1 = 1.0F / 2f;
            //p_102034_.scale(f1, f1, f1);
            //p_102034_.translate(0.0D, (double)(24 / 16.0F), 0.0D);
            this.root.render(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
            p_102034_.popPose();
            
            this.head.visible = headVisible0;
            p_102034_.pushPose();
            //p_102034_.translate(0.0D, (double)(5f / 16.0F), (double)(2f / 16.0F));
            p_102034_.scale(2, 2, 2);
            p_102034_.translate(0, -0.5, 0.15);
            this.head.render(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
            p_102034_.popPose();            
        } else {
            boolean glowingEyes_visible0 = false;
            if (this.props.glowingEyes) {
                glowingEyes_visible0 = this.glowingEyes.visible;
                this.glowingEyes.render(p_102034_, p_102035_, 15728880, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
                this.glowingEyes.visible = false;
            }
            this.root.render(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
            if (this.props.glowingEyes) {
                this.glowingEyes.visible = glowingEyes_visible0;
            }
        }

        p_102034_.popPose();
        p_102034_.popPose();
        root.xRot = xRot0; root.yRot = yRot0; root.zRot = zRot0;
        root.x = x0; root.y = y0; root.z = z0;
    }
    
}
