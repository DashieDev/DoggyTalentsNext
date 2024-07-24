package doggytalents.client.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class FieryReflectorModel extends ElytraCapeModel {

    public ModelPart reflector;
    public ModelPart divineCape;

    private boolean incapacitated;

    public FieryReflectorModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
        super.populatePart(box);
        this.reflector = this.elytra.getChild("disc");
        this.divineCape = this.elytra.getChild("divine_cape");
    }

    @Override
    public void prepareMobModel(Dog dogIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.prepareMobModel(dogIn, limbSwing, limbSwingAmount, partialTickTime);
        incapacitated = !dogIn.isDoingFine();
        lWing.visible = !incapacitated;
        rWing.visible = !incapacitated;
        divineCape.visible = !incapacitated;
    }

    @Override
    public void setupAnim(Dog dog, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch) {
        super.setupAnim(dog, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.reflector.yRot = 6*ageInTicks % 360 * Mth.DEG_TO_RAD;
    }

    public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition elytra_rot = upper_body.addOrReplaceChild("elytra_rot", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, -3.75F, -0.25F, -1.5708F, 0.0F, 0.0F));

		PartDefinition elytra = elytra_rot.addOrReplaceChild("elytra", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition divine_cape = elytra.addOrReplaceChild("divine_cape", CubeListBuilder.create().texOffs(30, 42).mirror().addBox(-5.8914F, 3.8079F, 2.3224F, 15.0F, 20.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 0.0F, -1.0F, 1.6144F, 0.0F, 0.0F));

		PartDefinition disc = elytra.addOrReplaceChild("disc", CubeListBuilder.create().texOffs(24, 29).addBox(-5.0F, -0.35F, -5.15F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-5.0F, -0.35F, -5.15F, 10.0F, 1.0F, 10.0F, new CubeDeformation(-0.099F))
		.texOffs(28, 33).addBox(-3.0F, -0.55F, -3.15F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.1F))
		.texOffs(32, 37).addBox(-1.0F, -1.6F, -1.15F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -5.9F, 7.4F));

		PartDefinition cube_r1 = disc.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 33).addBox(-3.0F, -0.5F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, -0.05F, -0.15F, 0.0F, -0.7854F, 0.0F));

		PartDefinition right_wing = elytra.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-2.0F, 1.0F, 2.0F));

		PartDefinition right_r1 = right_wing.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(0, 42).addBox(-6.6851F, 3.7889F, 2.3232F, 12.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, 1.6144F, -0.1309F, 0.0F));

		PartDefinition left_wing = elytra.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(2.0F, 1.0F, 2.0F));

		PartDefinition left_r1 = left_wing.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-4.8914F, 3.8079F, 2.3224F, 12.0F, 20.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, 1.6144F, 0.0873F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer p_103014_, int p_103015_, int p_103016_, int color_overlay) {
        super.renderToBuffer(stack, p_103014_, incapacitated ? p_103015_ :15728880, p_103016_, 0xffffffff);
    }
    
}
