package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BorzoiBDModel extends DogModel {

    public BorzoiBDModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-1.0F, 10.0F, 6.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition borzoi_tail_full2 = real_tail.addOrReplaceChild("borzoi_tail_full2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 0.5753F, 0.9418F, 0.2182F, 0.0F, 0.0F));

		PartDefinition borzoi_tail_full_rot2 = borzoi_tail_full2.addOrReplaceChild("borzoi_tail_full_rot2", CubeListBuilder.create().texOffs(1990, 23).addBox(-1.0F, -1.7823F, -5.3515F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.2F))
		.texOffs(2145, 5).addBox(-1.0F, -5.1823F, -5.3515F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, -0.65F, -1.25F, 0.7418F, 0.0F, 0.0F));

		PartDefinition borzoi_tail_bottom2 = borzoi_tail_full_rot2.addOrReplaceChild("borzoi_tail_bottom2", CubeListBuilder.create(), PartPose.offset(0.5F, 0.25F, -5.75F));

		PartDefinition borzoi_tail_bottom_rot2 = borzoi_tail_bottom2.addOrReplaceChild("borzoi_tail_bottom_rot2", CubeListBuilder.create().texOffs(1990, 9).addBox(-1.0F, -1.9106F, -5.2762F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.19F))
		.texOffs(2147, 20).addBox(-1.0F, -4.2906F, -5.2762F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.19F)), PartPose.offsetAndRotation(0.0F, 0.75F, 0.5F, 0.3491F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(1.75F, 15.0F, -7.5F));

		PartDefinition borzoi_leg7 = left_front_leg.addOrReplaceChild("borzoi_leg7", CubeListBuilder.create().texOffs(2193, 1).addBox(-1.0F, -2.0F, -7.05F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(2220, 0).addBox(-1.0F, 0.0F, -7.05F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.15F, 2.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-1.75F, 15.0F, -7.5F));

		PartDefinition borzoi_leg8 = right_front_leg.addOrReplaceChild("borzoi_leg8", CubeListBuilder.create().texOffs(2193, 1).mirror().addBox(-0.85F, -0.3F, -9.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(2220, 0).mirror().addBox(-0.85F, 1.7F, -9.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(1.5F, 15.0F, 5.0F));

		PartDefinition borzoi_leg6 = left_hind_leg.addOrReplaceChild("borzoi_leg6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.75F, 0.0F, -0.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition borzoi_leg3_side2 = borzoi_leg6.addOrReplaceChild("borzoi_leg3_side2", CubeListBuilder.create().texOffs(1990, 17).addBox(-1.9F, -0.25F, -11.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(2105, 0).addBox(-1.9F, -1.25F, -9.0F, 2.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition borzoi_leg3_coat2 = borzoi_leg3_side2.addOrReplaceChild("borzoi_leg3_coat2", CubeListBuilder.create().texOffs(2070, 0).addBox(-1.5535F, -2.5224F, -0.3561F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.5035F, -3.0439F, -1.9724F, -1.5708F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-1.5F, 15.0F, 5.0F));

		PartDefinition borzoi_leg5 = right_hind_leg.addOrReplaceChild("borzoi_leg5", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.75F, 0.0F, -0.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition borzoi_leg4_side2 = borzoi_leg5.addOrReplaceChild("borzoi_leg4_side2", CubeListBuilder.create().texOffs(1990, 17).mirror().addBox(-0.1F, -0.25F, -11.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(2105, 0).mirror().addBox(-0.1F, -1.25F, -9.0F, 2.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition borzoi_leg4_coat2 = borzoi_leg4_side2.addOrReplaceChild("borzoi_leg4_coat2", CubeListBuilder.create().texOffs(2070, 0).mirror().addBox(0.5535F, -2.5224F, -0.3561F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(-0.5035F, -3.0439F, -1.9724F, -1.5708F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 11.9F, 0.9F));

		PartDefinition borzoi_body_r1 = body.addOrReplaceChild("borzoi_body_r1", CubeListBuilder.create().texOffs(2072, 16).addBox(-2.5F, -0.6F, 9.6F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 12.1F, -0.9F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, -5.0F));

		PartDefinition borzoi_mane2 = upper_body.addOrReplaceChild("borzoi_mane2", CubeListBuilder.create().texOffs(2175, 18).addBox(-3.0F, -13.0F, 0.5F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.2F))
		.texOffs(2129, 0).addBox(-3.0F, -13.0F, -4.9F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.2F))
		.texOffs(2180, 4).addBox(2.0F, -13.0F, -4.9F, 1.0F, 8.0F, 5.0F, new CubeDeformation(0.2F))
		.texOffs(2180, 4).mirror().addBox(-3.0F, -13.0F, -4.9F, 1.0F, 8.0F, 5.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, 9.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition borzoi_mane_coat_left2 = borzoi_mane2.addOrReplaceChild("borzoi_mane_coat_left2", CubeListBuilder.create(), PartPose.offset(2.45F, -10.0F, 5.6F));

		PartDefinition borzoi_mane_coat_left_rot2 = borzoi_mane_coat_left2.addOrReplaceChild("borzoi_mane_coat_left_rot2", CubeListBuilder.create(), PartPose.offsetAndRotation(1.5267F, 1.0F, -0.4373F, -1.5708F, -0.0436F, 0.0F));

		PartDefinition borzoi_mane_coat_right2 = borzoi_mane2.addOrReplaceChild("borzoi_mane_coat_right2", CubeListBuilder.create(), PartPose.offset(-2.7F, -10.0F, 5.6F));

		PartDefinition borzoi_mane_coat_right_rot2 = borzoi_mane_coat_right2.addOrReplaceChild("borzoi_mane_coat_right_rot2", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.2767F, 1.0F, -0.4373F, -1.5708F, 0.0436F, 0.0F));

		PartDefinition borzoi_mane_coat_front2 = borzoi_mane2.addOrReplaceChild("borzoi_mane_coat_front2", CubeListBuilder.create(), PartPose.offset(0.2F, -12.85F, 4.35F));

		PartDefinition borzoi_mane_coat_front_rot2 = borzoi_mane_coat_front2.addOrReplaceChild("borzoi_mane_coat_front_rot2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.4F, -2.75F, -1.5708F, 0.0F, -1.5708F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 9.5F, -10.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(2.0F, -6.5F, 3.0F));

		PartDefinition borzoi_ear_left2 = left_ear.addOrReplaceChild("borzoi_ear_left2", CubeListBuilder.create(), PartPose.offset(0.5F, -1.0F, 0.75F));

		PartDefinition borzoi_ear_left_rot2 = borzoi_ear_left2.addOrReplaceChild("borzoi_ear_left_rot2", CubeListBuilder.create().texOffs(1933, 14).addBox(-0.8556F, -0.6131F, -2.3301F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4684F, -0.633F, -1.3565F, 3.1358F, 0.9915F, 2.1281F));

		PartDefinition borzoi_ear_left_fuzz2 = borzoi_ear_left_rot2.addOrReplaceChild("borzoi_ear_left_fuzz2", CubeListBuilder.create().texOffs(1918, 17).addBox(-1.0351F, -2.5097F, -2.871F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.474F, 1.7047F, -1.224F, 0.1852F, -0.5391F, -0.035F));

		PartDefinition borzoi_sideburns = left_ear.addOrReplaceChild("borzoi_sideburns", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.8F, -0.85F, -2.7F, -0.1745F, 0.0F, 0.0F));

		PartDefinition borzoi_sideburn_left_r1 = borzoi_sideburns.addOrReplaceChild("borzoi_sideburn_left_r1", CubeListBuilder.create().texOffs(2010, 0).mirror().addBox(2.1914F, -23.6094F, 18.2495F, 2.0F, 5.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(5.9993F, 22.35F, -16.4488F, 0.0F, -0.1745F, 0.0F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-2.0F, -6.5F, 3.0F));

		PartDefinition borzoi_ear_right2 = right_ear.addOrReplaceChild("borzoi_ear_right2", CubeListBuilder.create(), PartPose.offset(-0.5F, -1.0F, 0.75F));

		PartDefinition borzoi_ear_right_rot2 = borzoi_ear_right2.addOrReplaceChild("borzoi_ear_right_rot2", CubeListBuilder.create().texOffs(1933, 14).mirror().addBox(-0.1444F, -0.6131F, -2.3301F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.4684F, -0.633F, -1.3565F, 3.1358F, -0.9915F, -2.1281F));

		PartDefinition borzoi_ear_right_fuzz2 = borzoi_ear_right_rot2.addOrReplaceChild("borzoi_ear_right_fuzz2", CubeListBuilder.create().texOffs(1918, 17).mirror().addBox(0.0351F, -2.5097F, -2.871F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.474F, 1.7047F, -1.224F, 0.1852F, 0.5391F, 0.035F));

		PartDefinition borzoi_sideburn_right = right_ear.addOrReplaceChild("borzoi_sideburn_right", CubeListBuilder.create().texOffs(2010, 0).addBox(-2.0876F, -2.7363F, -0.0183F, 2.0F, 5.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.2F, 0.4559F, -0.7905F, -0.1745F, 0.1745F, 0.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition borzoi_head_rot = real_head.addOrReplaceChild("borzoi_head_rot", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 10.0F));

		PartDefinition borzoi_head_main = borzoi_head_rot.addOrReplaceChild("borzoi_head_main", CubeListBuilder.create().texOffs(1902, 6).addBox(-2.5F, -1.35F, -2.4F, 5.0F, 5.0F, 6.0F, new CubeDeformation(-0.15F)), PartPose.offset(0.0F, -21.85F, -8.8F));

		PartDefinition borzoi_head_beard = borzoi_head_rot.addOrReplaceChild("borzoi_head_beard", CubeListBuilder.create(), PartPose.offset(0.0F, -18.3F, -10.85F));

		PartDefinition borzoi_head_beard_rot = borzoi_head_beard.addOrReplaceChild("borzoi_head_beard_rot", CubeListBuilder.create().texOffs(2099, 16).addBox(-3.5F, -2.5F, -0.5F, 7.0F, 6.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.25F, 0.8F));

		PartDefinition borzoi_head_top = borzoi_head_rot.addOrReplaceChild("borzoi_head_top", CubeListBuilder.create().texOffs(1902, 2).mirror().addBox(-3.5F, -1.4039F, 0.5287F, 7.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.05F, -22.5632F, -7.875F, -0.48F, 0.0F, 0.0F));

		PartDefinition borzoi_neck = borzoi_head_rot.addOrReplaceChild("borzoi_neck", CubeListBuilder.create().texOffs(2089, 1).addBox(-2.0F, -1.5315F, -3.9672F, 3.0F, 5.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.5F, -18.85F, -6.4F, 0.3491F, 0.0F, 0.0F));

		PartDefinition borzoi_eye_right = borzoi_head_rot.addOrReplaceChild("borzoi_eye_right", CubeListBuilder.create().texOffs(1569, 23).addBox(-1.15F, 0.25F, -0.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offset(-1.3084F, -22.4931F, -10.85F));

		PartDefinition borzoi_pupil_right = borzoi_eye_right.addOrReplaceChild("borzoi_pupil_right", CubeListBuilder.create().texOffs(1496, 4).mirror().addBox(-1.8416F, -10.7431F, -9.55F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offset(0.7584F, 10.3931F, 8.75F));

		PartDefinition borzoi_eye_left = borzoi_head_rot.addOrReplaceChild("borzoi_eye_left", CubeListBuilder.create().texOffs(1569, 23).mirror().addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offset(1.9584F, -21.7431F, -10.65F));

		PartDefinition borzoi_pupil_left = borzoi_eye_left.addOrReplaceChild("borzoi_pupil_left", CubeListBuilder.create().texOffs(1496, 4).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.5F)), PartPose.offset(-0.5668F, -0.1F, -0.5F));

		PartDefinition borzoi_mouth = borzoi_head_rot.addOrReplaceChild("borzoi_mouth", CubeListBuilder.create().texOffs(2070, 2).addBox(-1.5F, 0.1681F, -5.819F, 3.0F, 2.0F, 6.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, -21.7162F, -11.1634F, 0.3142F, 0.0F, 0.0F));

		PartDefinition borzoi_jaw = borzoi_mouth.addOrReplaceChild("borzoi_jaw", CubeListBuilder.create().texOffs(1868, 14).addBox(-1.5F, -0.8728F, -4.6692F, 3.0F, 1.0F, 6.0F, new CubeDeformation(-0.22F))
		.texOffs(425, 7).addBox(-1.5F, -1.3728F, -4.6692F, 3.0F, 1.0F, 5.0F, new CubeDeformation(-0.23F)), PartPose.offset(0.0F, 2.5735F, -0.1806F));

		PartDefinition borzoi_jaw_bottom = borzoi_jaw.addOrReplaceChild("borzoi_jaw_bottom", CubeListBuilder.create().texOffs(2038, 25).addBox(-1.5F, -0.5937F, -2.965F, 3.0F, 1.0F, 6.0F, new CubeDeformation(-0.24F)), PartPose.offset(0.0F, 0.2417F, -1.6027F));

		PartDefinition borzoi_tongue_rot = borzoi_jaw.addOrReplaceChild("borzoi_tongue_rot", CubeListBuilder.create(), PartPose.offsetAndRotation(0.25F, -2.2736F, -0.9235F, 0.0436F, 0.0F, 0.0F));

		PartDefinition borzoi_tongue = borzoi_tongue_rot.addOrReplaceChild("borzoi_tongue", CubeListBuilder.create().texOffs(1448, 15).addBox(-1.4921F, -2.3241F, -3.2057F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offset(-0.2864F, 3.1239F, -1.5346F));

		PartDefinition borzoi_nose = borzoi_mouth.addOrReplaceChild("borzoi_nose", CubeListBuilder.create().texOffs(1855, 9).addBox(-1.5F, 0.2404F, -1.2273F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.65F)), PartPose.offsetAndRotation(0.0F, -0.529F, -5.412F, -0.0611F, 0.0F, 0.0F));

		PartDefinition borzoi_bridge = borzoi_head_rot.addOrReplaceChild("borzoi_bridge", CubeListBuilder.create().texOffs(1519, 0).addBox(-0.5F, 0.0101F, -1.9293F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -21.8698F, -11.3651F, 0.6109F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 3002, 32);
    }
}
