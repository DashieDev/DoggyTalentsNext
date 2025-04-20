package doggytalents.client.entity.model.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import doggytalents.common.entity.misc.DogPlushie;
import doggytalents.common.entity.misc.SamoyedPlushie;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SamoyedPlushieModel extends EntityModel<SamoyedPlushie>{

    public ModelPart root;

	public SamoyedPlushieModel(ModelPart box) {
		this.root = box.getChild("root");
	}
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 0.0F));

		PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.7228F, 7.0575F, 1.7533F, -0.0895F, -0.0986F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition samoyed_tail_r1 = real_tail.addOrReplaceChild("samoyed_tail_r1", CubeListBuilder.create().texOffs(2368, 17).addBox(-1.6969F, -7.7345F, -2.0816F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8064F, -1.1307F, -1.0489F, -3.1339F, -0.1744F, -1.6151F));

		PartDefinition samoyed_tail_r2 = real_tail.addOrReplaceChild("samoyed_tail_r2", CubeListBuilder.create().texOffs(2356, 15).addBox(1.3265F, -8.862F, 5.2489F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.8009F, 3.6863F, 8.5916F, -3.1339F, -0.1744F, -1.6151F));

		PartDefinition right_hind_leg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, 4.0F, 4.2888F, 1.2221F, 0.0F, 0.0F));

		PartDefinition samoyed_leg3_sit4 = right_hind_leg.addOrReplaceChild("samoyed_leg3_sit4", CubeListBuilder.create().texOffs(2441, 0).mirror().addBox(-6.5F, 11.15F, 0.4F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(2399, 3).mirror().addBox(-6.5F, 9.15F, 4.4F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 7.4F, -12.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, 4.0F, 4.2888F, 1.2221F, 0.0F, 0.0F));

		PartDefinition samoyed_leg3_sit3 = left_hind_leg.addOrReplaceChild("samoyed_leg3_sit3", CubeListBuilder.create().texOffs(2441, 0).addBox(3.5F, 11.15F, 0.4F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(2399, 3).addBox(3.5F, 9.15F, 4.4F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 7.4F, -12.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition right_front_leg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, 4.8904F, -4.0F, -1.6098F, 0.0F, 0.0F));

		PartDefinition samoyed_leg9 = right_front_leg.addOrReplaceChild("samoyed_leg9", CubeListBuilder.create().texOffs(2455, 0).mirror().addBox(-0.75F, -2.0F, -5.05F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(2399, -5).mirror().addBox(-0.75F, 0.0F, -5.05F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.75F, 0.45F, 0.65F, 1.5708F, 0.0F, 0.0F));

		PartDefinition left_front_leg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 4.8904F, -4.0F, -1.6098F, 0.0F, 0.0F));

		PartDefinition samoyed_leg1 = left_front_leg.addOrReplaceChild("samoyed_leg1", CubeListBuilder.create().texOffs(2455, 0).addBox(-2.25F, -2.0F, -5.05F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(2399, -5).addBox(0.75F, 0.0F, -5.05F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, 0.45F, 0.65F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.835F, 0.6939F, 1.6651F, 0.0F, 0.0F));

		PartDefinition sad = body.addOrReplaceChild("sad", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition samoyed_body2 = sad.addOrReplaceChild("samoyed_body2", CubeListBuilder.create().texOffs(2372, 0).addBox(-4.5F, -3.6996F, -4.3823F, 9.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.1409F, 1.9086F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = root.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(2408, 0).addBox(-5.5F, -2.85F, -5.65F, 11.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.3904F, -2.5747F, 1.3353F, 0.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -5.5F, -7.0F, -0.1609F, 0.0F, 0.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(16, 14).addBox(-7.0F, 0.5F, -1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.0235F, 1.5F, -0.7555F, 0.052F, -0.0599F));

		PartDefinition samoyed_ear_right = right_ear.addOrReplaceChild("samoyed_ear_right", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 0.15F, 0.5F, 1.5708F, -0.0436F, 0.0F));

		PartDefinition samoyed_ear_right_sub = samoyed_ear_right.addOrReplaceChild("samoyed_ear_right_sub", CubeListBuilder.create(), PartPose.offset(-0.6501F, 6.9933F, -2.9573F));

		PartDefinition samoyed_ear_right_anim = samoyed_ear_right_sub.addOrReplaceChild("samoyed_ear_right_anim", CubeListBuilder.create().texOffs(2354, 8).mirror().addBox(-2.3335F, -8.5934F, 4.0916F, 4.0F, 2.0F, 5.0F, new CubeDeformation(-0.01F)).mirror(false)
		.texOffs(2345, 3).mirror().addBox(-1.3335F, -8.5934F, 9.0704F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offset(1.0F, 0.0F, -1.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(16, 14).addBox(5.0F, 0.5F, -1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0235F, 1.5F, -0.7555F, -0.052F, 0.0599F));

		PartDefinition samoyed_ear_left2 = left_ear.addOrReplaceChild("samoyed_ear_left2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 3.0F, 6.1F, 1.5708F, 0.0436F, 0.0F));

		PartDefinition samoyed_ear_left_sub2 = samoyed_ear_left2.addOrReplaceChild("samoyed_ear_left_sub2", CubeListBuilder.create(), PartPose.offset(0.9554F, 0.0F, 1.0427F));

		PartDefinition samoyed_ear_left_anim2 = samoyed_ear_left_sub2.addOrReplaceChild("samoyed_ear_left_anim2", CubeListBuilder.create().texOffs(2354, 8).addBox(-1.7276F, -7.1947F, 2.9416F, 4.0F, 2.0F, 5.0F, new CubeDeformation(-0.01F))
		.texOffs(2345, 3).addBox(-0.7276F, -7.1947F, 7.9204F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offset(-1.0F, 0.0F, -1.0F));

		PartDefinition samoyed_head2 = real_head.addOrReplaceChild("samoyed_head2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.9F, 2.9F, 1.5708F, 0.0F, 0.0F));

		PartDefinition samoyed_head_sub2 = samoyed_head2.addOrReplaceChild("samoyed_head_sub2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition samoyed_head_anim2 = samoyed_head_sub2.addOrReplaceChild("samoyed_head_anim2", CubeListBuilder.create().texOffs(2323, 0).addBox(-4.0F, -4.4F, -1.25F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(2351, 0).addBox(-3.5F, -4.4F, 4.75F, 7.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(2346, 21).addBox(-2.0F, -3.4F, 6.75F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition samoyed_tuft_left2 = samoyed_head_anim2.addOrReplaceChild("samoyed_tuft_left2", CubeListBuilder.create(), PartPose.offset(0.0F, 11.1F, -2.3F));

		PartDefinition samoyed_tuft_left_sub2 = samoyed_tuft_left2.addOrReplaceChild("samoyed_tuft_left_sub2", CubeListBuilder.create(), PartPose.offset(5.45F, -11.5F, 5.0F));

		PartDefinition samoyed_tuft_left_anim2 = samoyed_tuft_left_sub2.addOrReplaceChild("samoyed_tuft_left_anim2", CubeListBuilder.create().texOffs(2038, 4).mirror().addBox(-1.5F, -2.0F, -0.95F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition samoyed_tuft_right2 = samoyed_head_anim2.addOrReplaceChild("samoyed_tuft_right2", CubeListBuilder.create(), PartPose.offset(0.0F, 11.1F, -2.3F));

		PartDefinition samoyed_tuft_right_sub2 = samoyed_tuft_right2.addOrReplaceChild("samoyed_tuft_right_sub2", CubeListBuilder.create(), PartPose.offset(-5.45F, -11.5F, 5.0F));

		PartDefinition samoyed_tuft_right_anim2 = samoyed_tuft_right_sub2.addOrReplaceChild("samoyed_tuft_right_anim2", CubeListBuilder.create().texOffs(2038, 4).addBox(-0.5F, -2.0F, -0.95F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition samoyed_eye_right2 = samoyed_head_anim2.addOrReplaceChild("samoyed_eye_right2", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.35F, -4.25F, 4.3F, 0.0F, 0.0436F, 0.0F));

		PartDefinition samoyed_eye_right_anim2 = samoyed_eye_right2.addOrReplaceChild("samoyed_eye_right_anim2", CubeListBuilder.create().texOffs(2277, 6).addBox(-2.3586F, -15.45F, 6.1509F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)), PartPose.offset(2.1936F, 15.35F, -6.601F));

		PartDefinition samoyed_eye_left2 = samoyed_head_anim2.addOrReplaceChild("samoyed_eye_left2", CubeListBuilder.create(), PartPose.offsetAndRotation(2.45F, -4.25F, 4.3F, 0.0F, -0.0436F, 0.0F));

		PartDefinition samoyed_eye_left_anim2 = samoyed_eye_left2.addOrReplaceChild("samoyed_eye_left_anim2", CubeListBuilder.create().texOffs(2277, 6).mirror().addBox(1.3586F, -15.45F, 6.1509F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offset(-2.2935F, 15.35F, -6.5966F));

		PartDefinition samoyed_beard_bottom2 = samoyed_head_anim2.addOrReplaceChild("samoyed_beard_bottom2", CubeListBuilder.create(), PartPose.offset(2.5167F, -2.5167F, 2.5667F));

		PartDefinition samoyed_beard_bottom_sub2 = samoyed_beard_bottom2.addOrReplaceChild("samoyed_beard_bottom_sub2", CubeListBuilder.create(), PartPose.offset(-2.5167F, 1.6167F, -4.8667F));

		PartDefinition samoyed_beard_bottom_anim2 = samoyed_beard_bottom_sub2.addOrReplaceChild("samoyed_beard_bottom_anim2", CubeListBuilder.create().texOffs(2338, 12).mirror().addBox(-3.0F, -2.5F, -0.95F, 6.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition samoyed_mouth_open2 = samoyed_head_anim2.addOrReplaceChild("samoyed_mouth_open2", CubeListBuilder.create().texOffs(2369, 0).addBox(-2.0F, -0.9939F, -0.4504F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(2367, 0).addBox(-0.5F, -1.0539F, -0.5496F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -6.15F, 1.65F, 0.1222F, 0.0F, 0.0F));

		PartDefinition bone2 = samoyed_mouth_open2.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(2367, 0).addBox(-2.55F, -15.6F, 3.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
		.texOffs(2367, 0).mirror().addBox(1.55F, -15.6F, 3.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(0.0F, 16.5585F, -5.9485F, -0.1222F, 0.0F, 0.0F));

		PartDefinition bone3 = samoyed_mouth_open2.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(2343, 26).addBox(-1.5F, -1.5052F, -1.6499F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.9F, -0.0873F, 0.0F, 0.0F));

		PartDefinition bone4 = samoyed_mouth_open2.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(2318, 0).addBox(-1.0F, -1.5543F, -1.6775F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5654F, 1.4972F, 0.829F, 0.0F, 0.0F));

		PartDefinition samoyed_jaw2 = samoyed_mouth_open2.addOrReplaceChild("samoyed_jaw2", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, -1.0F));

		PartDefinition samoyed_jaw_rot2 = samoyed_jaw2.addOrReplaceChild("samoyed_jaw_rot2", CubeListBuilder.create().texOffs(2328, 12).addBox(-2.0F, -2.1451F, -0.0845F, 4.0F, 3.0F, 1.0F, new CubeDeformation(-0.15F))
		.texOffs(2225, 14).addBox(-2.0F, -2.1451F, -0.6345F, 4.0F, 3.0F, 1.0F, new CubeDeformation(-0.13F))
		.texOffs(2258, 19).addBox(-2.0F, -2.1451F, 0.4155F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.16F))
		.texOffs(2258, 19).mirror().addBox(1.0F, -2.1451F, 0.4155F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.16F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition samoyed_tongue2 = samoyed_jaw_rot2.addOrReplaceChild("samoyed_tongue2", CubeListBuilder.create(), PartPose.offset(0.0F, -1.018F, -0.308F));

		PartDefinition samoyed_tongue_rot2 = samoyed_tongue2.addOrReplaceChild("samoyed_tongue_rot2", CubeListBuilder.create().texOffs(2320, 12).addBox(-1.5F, -2.8644F, 0.6335F, 3.0F, 5.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.9599F, 0.0F, 0.0F));

		PartDefinition samoyed_mouth_closed2 = samoyed_head_anim2.addOrReplaceChild("samoyed_mouth_closed2", CubeListBuilder.create().texOffs(2380, 17).addBox(-2.0F, -0.9956F, -0.4502F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(2349, 0).addBox(-0.5F, -1.2056F, -0.5994F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -6.0F, 1.7F, 0.0873F, 0.0F, 0.0F));

		PartDefinition bone5 = samoyed_mouth_closed2.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(2380, 22).addBox(-1.5F, -1.5F, -1.55F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.9F, -0.0873F, 0.0F, 0.0F));

		PartDefinition bone8 = samoyed_mouth_closed2.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(2392, 17).addBox(-1.0F, -1.5554F, -1.6761F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5654F, 1.7471F, 0.829F, 0.0F, 0.0F));

		PartDefinition bone9 = samoyed_mouth_closed2.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(2409, 3).addBox(-2.0F, -2.4956F, -0.2002F, 4.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 1.5F, -1.0F));

		return LayerDefinition.create(meshdefinition, 3002, 32);
	}


    @Override
    public void setupAnim(SamoyedPlushie p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_,
            float p_102623_) {
        
    }

    @Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color_overlay) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color_overlay);
	}
    
}
