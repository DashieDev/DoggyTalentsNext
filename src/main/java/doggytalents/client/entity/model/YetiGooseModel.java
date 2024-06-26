package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class YetiGooseModel extends SyncedAccessoryModel{

    public YetiGooseModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.body = Optional.of(box.getChild("body"));
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition fowl_body = body.addOrReplaceChild("fowl_body", CubeListBuilder.create(), PartPose.offset(0.1667F, -3.5667F, -3.5F));

		PartDefinition goose_body = fowl_body.addOrReplaceChild("goose_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 7.0F, 9.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition goose_body_main = goose_body.addOrReplaceChild("goose_body_main", CubeListBuilder.create(), PartPose.offset(-0.1667F, -1.4333F, -0.5F));

		PartDefinition goose_nolegs = goose_body_main.addOrReplaceChild("goose_nolegs", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, -1.0F));

		PartDefinition goose_nolegs_bounce = goose_nolegs.addOrReplaceChild("goose_nolegs_bounce", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition goose_body_rot = goose_nolegs_bounce.addOrReplaceChild("goose_body_rot", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-3.0F, -4.5F, -4.25F, 6.0F, 9.0F, 7.0F, new CubeDeformation(-0.01F)).mirror(false)
		.texOffs(44, 14).mirror().addBox(-3.0F, -6.5F, -2.25F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -6.3542F, 2.8472F, -0.1745F, 0.0F, 0.0F));

		PartDefinition goose_tail_bone = goose_body_rot.addOrReplaceChild("goose_tail_bone", CubeListBuilder.create().texOffs(9, 18).mirror().addBox(-2.0F, -1.4743F, -1.3491F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.5F, 1.25F, 0.1745F, 0.0F, 0.0F));

		PartDefinition goose_tail_feather = goose_tail_bone.addOrReplaceChild("goose_tail_feather", CubeListBuilder.create(), PartPose.offset(0.0F, 2.9F, -2.0F));

		PartDefinition goose_tail_feather_rot = goose_tail_feather.addOrReplaceChild("goose_tail_feather_rot", CubeListBuilder.create().texOffs(54, 20).mirror().addBox(-2.0F, -0.0787F, -1.1561F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -3.15F, 3.75F, -0.3927F, 0.0F, 0.0F));

		PartDefinition goose_right_wing = goose_body_rot.addOrReplaceChild("goose_right_wing", CubeListBuilder.create(), PartPose.offset(-3.0F, -0.5F, 1.75F));

		PartDefinition goose_right_wing_rot = goose_right_wing.addOrReplaceChild("goose_right_wing_rot", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-0.5F, -3.3147F, -3.7585F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(47, 20).mirror().addBox(-0.5F, 0.6853F, -2.7585F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 0.0F, -3.25F, -1.5708F, 0.0F, 0.0F));

		PartDefinition goose_left_wing = goose_body_rot.addOrReplaceChild("goose_left_wing", CubeListBuilder.create(), PartPose.offset(3.0F, -0.5F, 1.75F));

		PartDefinition goose_left_wing_rot = goose_left_wing.addOrReplaceChild("goose_left_wing_rot", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, -3.3147F, -3.7585F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(47, 20).addBox(-0.5F, 0.6853F, -1.7585F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, -3.25F, -1.5708F, 0.0F, 0.0F));

		PartDefinition goose_thighs_rot = goose_nolegs_bounce.addOrReplaceChild("goose_thighs_rot", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -13.0F, -15.15F, -1.5708F, 0.0F, 0.0F));

		PartDefinition goose_thighs = goose_thighs_rot.addOrReplaceChild("goose_thighs", CubeListBuilder.create().texOffs(27, 22).mirror().addBox(-4.25F, -2.35F, -1.5F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.75F, -12.15F, 6.5F));

		PartDefinition goose_head = goose_nolegs_bounce.addOrReplaceChild("goose_head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -11.15F, 5.55F, 0.0F, 0.0F, 1.1781F));

		PartDefinition goose_head_rot = goose_head.addOrReplaceChild("goose_head_rot", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 0.25F, 6.3125F, -1.5708F, 0.0F, 0.0F));

		PartDefinition goose_neck = goose_head_rot.addOrReplaceChild("goose_neck", CubeListBuilder.create().texOffs(48, 6).mirror().addBox(-1.25F, -4.6729F, -1.5602F, 2.0F, 5.0F, 3.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offset(-0.25F, 6.4F, 0.1875F));

		PartDefinition goose_noneck = goose_head_rot.addOrReplaceChild("goose_noneck", CubeListBuilder.create().texOffs(28, 14).mirror().addBox(-1.0F, -4.0F, -2.5F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-0.5F, 1.9771F, 0.1273F));

		PartDefinition goose_eye_right = goose_noneck.addOrReplaceChild("goose_eye_right", CubeListBuilder.create().texOffs(20, 19).mirror().addBox(-1.25F, -13.9229F, 2.9398F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offset(0.0F, 10.9229F, -4.1898F));

		PartDefinition goose_eye_left = goose_noneck.addOrReplaceChild("goose_eye_left", CubeListBuilder.create().texOffs(20, 19).addBox(1.25F, -13.9229F, 2.9398F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.15F)), PartPose.offset(-1.0F, 10.9229F, -4.1898F));

		PartDefinition goose_beak = goose_noneck.addOrReplaceChild("goose_beak", CubeListBuilder.create(), PartPose.offset(1.0F, 10.9229F, -4.1898F));

		PartDefinition goose_beak_top = goose_beak.addOrReplaceChild("goose_beak_top", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition goose_beak_top_rot = goose_beak_top.addOrReplaceChild("goose_beak_top_rot", CubeListBuilder.create().texOffs(40, 21).mirror().addBox(-1.0F, 0.1781F, -0.9204F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.01F)).mirror(false)
		.texOffs(16, 23).mirror().addBox(-1.0F, -0.8019F, 1.0796F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -12.9229F, -0.3102F, 0.0873F, 0.0F, 0.0F));

		PartDefinition goose_beak_bottom = goose_beak.addOrReplaceChild("goose_beak_bottom", CubeListBuilder.create(), PartPose.offset(-1.0F, -11.5F, 1.45F));

		PartDefinition goose_beak_bottom_rot = goose_beak_bottom.addOrReplaceChild("goose_beak_bottom_rot", CubeListBuilder.create().texOffs(38, 14).mirror().addBox(-1.0F, -0.4945F, -0.1668F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(0.0F, -0.1729F, -2.1602F, -0.0436F, 0.0F, 0.0F));

		PartDefinition goose_right_leg = goose_body_main.addOrReplaceChild("goose_right_leg", CubeListBuilder.create(), PartPose.offset(-1.5F, -2.4F, -0.5F));

		PartDefinition chicken_right_leg4 = goose_right_leg.addOrReplaceChild("chicken_right_leg4", CubeListBuilder.create().texOffs(60, 14).mirror().addBox(-2.25F, -4.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(1.5F, 0.5F, -4.75F, -1.5708F, 0.0F, 0.0F));

		PartDefinition bone8 = chicken_right_leg4.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.75F, -2.6F, -0.3F, 0.2182F, 0.0F, 0.0F));

		PartDefinition body_sub_18 = bone8.addOrReplaceChild("body_sub_18", CubeListBuilder.create().texOffs(60, 15).mirror().addBox(-0.5F, -0.0119F, -0.6753F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.019F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition goose_right_foot = chicken_right_leg4.addOrReplaceChild("goose_right_foot", CubeListBuilder.create().texOffs(53, 9).addBox(-1.5F, -0.3436F, -3.1464F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, -1.0F, -0.5F, 0.7854F, 0.0F, 0.0F));

		PartDefinition goose_left_leg = goose_body_main.addOrReplaceChild("goose_left_leg", CubeListBuilder.create(), PartPose.offset(1.5F, -2.4F, -0.25F));

		PartDefinition chicken_left_leg4 = goose_left_leg.addOrReplaceChild("chicken_left_leg4", CubeListBuilder.create().texOffs(60, 14).addBox(1.25F, -4.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(-1.5F, 0.5F, -4.75F, -1.5708F, 0.0F, 0.0F));

		PartDefinition bone5 = chicken_left_leg4.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offsetAndRotation(1.75F, -2.6F, -0.3F, 0.2182F, 0.0F, 0.0F));

		PartDefinition body_sub_3 = bone5.addOrReplaceChild("body_sub_3", CubeListBuilder.create().texOffs(60, 15).addBox(-0.5F, -0.0119F, -0.6753F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.019F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition goose_left_foot = chicken_left_leg4.addOrReplaceChild("goose_left_foot", CubeListBuilder.create().texOffs(53, 9).mirror().addBox(-1.5F, -0.3436F, -3.1464F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.75F, -1.0F, -0.5F, 0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
    
}
