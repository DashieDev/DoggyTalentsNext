package doggytalents.client.entity.model.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import doggytalents.common.entity.misc.DogPlushie;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DogPlushieModel extends EntityModel<DogPlushie> {

    public ModelPart root;

	public DogPlushieModel(ModelPart box) {
		this.root = box.getChild("root");
	}

    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.25F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.15F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition snout = real_head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.5F, -2.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition snout_upper = snout.addOrReplaceChild("snout_upper", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -0.7283F, -3.0687F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -0.52F, 0.0F));

		PartDefinition snout_lower = snout.addOrReplaceChild("snout_lower", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -0.5783F, -3.0687F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.98F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -3.0F, 0.5F, 0.0F, 0.0F, 0.1745F));

		PartDefinition head_r1 = left_ear.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(16, 14).addBox(-0.9F, -1.75F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, -3.0F, 0.5F, 0.0F, 0.0F, -0.1745F));

		PartDefinition head_r2 = right_ear.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.1F, -1.75F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

		PartDefinition upper_body = root.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.25F, -2.8F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0349F, 0.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -6.75F, -2.5F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.45F)), PartPose.offsetAndRotation(0.0F, -1.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition right_front_leg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-2.1F, -1.75F, -1.5F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offsetAndRotation(-1.75F, 1.0F, -4.5F, 0.7854F, 0.0F, 1.5708F));

		PartDefinition left_front_leg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.1F, -1.75F, -1.5F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(1.75F, 1.0F, -4.5F, 0.7854F, 0.0F, -1.5708F));

		PartDefinition right_hind_leg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-2.6521F, -4.1874F, -0.2532F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.55F)), PartPose.offsetAndRotation(-0.5F, 1.0F, 5.0F, 1.4835F, -0.3054F, 0.0F));

		PartDefinition left_hind_leg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(0.6521F, -4.1874F, -0.2532F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.55F)).mirror(false), PartPose.offsetAndRotation(0.5F, 1.0F, 5.0F, 1.4835F, 0.3054F, 0.0F));

		PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, 1.7424F, -2.1035F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, -2.5F, -5.4F, 0.8727F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

    @Override
    public void setupAnim(DogPlushie p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_,
            float p_102623_) {
        
    }

    @Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int unused) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, 0xffffffff);
	}
    
}
