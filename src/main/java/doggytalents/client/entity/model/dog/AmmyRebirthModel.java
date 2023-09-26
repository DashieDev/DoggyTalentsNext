package doggytalents.client.entity.model.dog;

import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class AmmyRebirthModel extends DogModel {

	

    public AmmyRebirthModel(ModelPart box) {
		super(box, RenderType::entityTranslucent);
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 12.0F, 10.0F, 1.8326F, 0.0F, 0.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(32, 36).addBox(0.0F, 0.0F, -2.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(45, 5).addBox(0.0F, 7.8299F, -3.0947F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(45, 5).addBox(0.0F, 9.1799F, -2.8447F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(26, 44).addBox(0.0F, 2.15F, -1.35F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(45, 5).addBox(0.0F, 8.9F, 1.65F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(54, 9).mirror().addBox(-1.5F, 2.25F, -1.75F, 1.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)).mirror(false)
		.texOffs(54, 9).addBox(-1.5F, 2.25F, -1.75F, 1.0F, 12.0F, 4.0F, new CubeDeformation(1.0F))
		.texOffs(43, 25).addBox(-2.0F, 4.75F, -1.5F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(2.0F, -1.5F, 0.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, 15.75F, 7.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition leg5_r1 = right_hind_leg.addOrReplaceChild("leg5_r1", CubeListBuilder.create().texOffs(52, 36).mirror().addBox(-0.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(-0.4596F, 4.5741F, 4.7157F, 0.0268F, -0.2181F, -0.0058F));

		PartDefinition leg3_r1 = right_hind_leg.addOrReplaceChild("leg3_r1", CubeListBuilder.create().texOffs(40, 36).mirror().addBox(-0.9F, -3.5626F, -0.8346F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.15F, 0.6459F, -0.3841F, 0.7854F, 0.0F, 0.0F));

		PartDefinition leg4_r1 = right_hind_leg.addOrReplaceChild("leg4_r1", CubeListBuilder.create().texOffs(18, 14).mirror().addBox(-1.1F, 0.7281F, 0.0065F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(0.05F, 3.8959F, 1.1159F, 1.1781F, 0.0F, 0.0F));

		PartDefinition leg5_r2 = right_hind_leg.addOrReplaceChild("leg5_r2", CubeListBuilder.create().texOffs(16, 45).mirror().addBox(-1.1F, -1.8855F, 1.7679F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(0.05F, 5.1459F, 0.8659F, -0.3054F, 0.0F, 0.0F));

		PartDefinition leg6_r1 = right_hind_leg.addOrReplaceChild("leg6_r1", CubeListBuilder.create().texOffs(18, 14).mirror().addBox(-1.0984F, -2.5068F, -0.9334F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(0.05F, 8.1459F, 1.6159F, -1.1415F, -0.0136F, 0.0461F));

		PartDefinition leg4_r2 = right_hind_leg.addOrReplaceChild("leg4_r2", CubeListBuilder.create().texOffs(0, 45).mirror().addBox(-0.85F, -3.5436F, 1.2027F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.15F, 0.6459F, -0.3841F, 0.1309F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(1.5F, 15.75F, 7.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition leg2_r1 = left_hind_leg.addOrReplaceChild("leg2_r1", CubeListBuilder.create().texOffs(40, 36).addBox(-1.1F, -3.5626F, -0.8346F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.15F, 0.6459F, -0.3841F, 0.7854F, 0.0F, 0.0F));

		PartDefinition leg3_r2 = left_hind_leg.addOrReplaceChild("leg3_r2", CubeListBuilder.create().texOffs(18, 14).addBox(-0.9F, 0.7281F, 0.0065F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-0.05F, 3.8959F, 1.1159F, 1.1781F, 0.0F, 0.0F));

		PartDefinition leg4_r3 = left_hind_leg.addOrReplaceChild("leg4_r3", CubeListBuilder.create().texOffs(16, 45).addBox(-0.9F, -1.8855F, 1.7679F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-0.05F, 5.1459F, 0.8659F, -0.3054F, 0.0F, 0.0F));

		PartDefinition leg5_r3 = left_hind_leg.addOrReplaceChild("leg5_r3", CubeListBuilder.create().texOffs(18, 14).addBox(-0.9016F, -2.5068F, -0.9334F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-0.05F, 8.1459F, 1.6159F, -1.1415F, 0.0136F, -0.0461F));

		PartDefinition leg3_r3 = left_hind_leg.addOrReplaceChild("leg3_r3", CubeListBuilder.create().texOffs(0, 45).addBox(-1.15F, -3.5436F, 1.2027F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.15F, 0.6459F, -0.3841F, 0.1309F, 0.0F, 0.0F));

		PartDefinition leg5_r4 = left_hind_leg.addOrReplaceChild("leg5_r4", CubeListBuilder.create().texOffs(52, 36).addBox(-0.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.4596F, 4.5741F, 4.7157F, 0.0268F, 0.2181F, 0.0058F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.0F, -2.0F));

		PartDefinition leg5_r5 = right_front_leg.addOrReplaceChild("leg5_r5", CubeListBuilder.create().texOffs(52, 36).mirror().addBox(-1.0F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(-1.65F, 7.6901F, -0.2603F, 0.0268F, -0.2181F, -0.0058F));

		PartDefinition leg7_r1 = right_front_leg.addOrReplaceChild("leg7_r1", CubeListBuilder.create().texOffs(8, 45).mirror().addBox(-1.0F, -1.25F, -1.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 2.2F, 0.6F, -0.2618F, 0.0F, 0.0F));

		PartDefinition leg6_r2 = right_front_leg.addOrReplaceChild("leg6_r2", CubeListBuilder.create().texOffs(44, 16).mirror().addBox(-1.0F, -3.25F, -1.5F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 4.3F, 1.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition leg5_r6 = right_front_leg.addOrReplaceChild("leg5_r6", CubeListBuilder.create().texOffs(36, 16).mirror().addBox(-3.0F, -5.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(1.0F, 6.7166F, -1.2862F, 0.0262F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(1.0F, 13.0F, -2.0F));

		PartDefinition leg6_r3 = left_front_leg.addOrReplaceChild("leg6_r3", CubeListBuilder.create().texOffs(8, 45).addBox(-1.0F, -1.25F, -1.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.2F, 0.6F, -0.2618F, 0.0F, 0.0F));

		PartDefinition leg5_r7 = left_front_leg.addOrReplaceChild("leg5_r7", CubeListBuilder.create().texOffs(44, 16).addBox(-1.0F, -3.25F, -1.5F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(1.0F, 4.3F, 1.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition leg5_r8 = left_front_leg.addOrReplaceChild("leg5_r8", CubeListBuilder.create().texOffs(52, 36).addBox(0.0F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(1.65F, 7.6901F, -0.2603F, 0.0268F, 0.2181F, 0.0058F));

		PartDefinition leg4_r4 = left_front_leg.addOrReplaceChild("leg4_r4", CubeListBuilder.create().texOffs(36, 16).addBox(1.0F, -5.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(-1.0F, 6.7166F, -1.2862F, 0.0262F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 3.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(24, 7).addBox(-3.0F, -5.05F, -2.4F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 9.0F, 1.2217F, 0.0F, 0.0F));

		PartDefinition body_rotation_r2 = body.addOrReplaceChild("body_rotation_r2", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -13.8F, -1.5F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 2.0F, 9.0F, 1.4399F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = upper_body.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(17, 22).addBox(-3.0F, -3.05F, -1.7F, 6.0F, 7.0F, 7.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(1.0F, -2.0F, 2.5F, -0.7418F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r2 = upper_body.addOrReplaceChild("mane_rotation_r2", CubeListBuilder.create().texOffs(36, 42).mirror().addBox(-2.0F, -4.5F, -6.0F, 2.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 2.65F, 1.15F, 0.0F, 0.0F, 0.2618F));

		PartDefinition mane_rotation_r3 = upper_body.addOrReplaceChild("mane_rotation_r3", CubeListBuilder.create().texOffs(36, 42).addBox(0.0F, -4.5F, -6.0F, 2.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 2.65F, 1.15F, 0.0F, 0.0F, -0.2618F));

		PartDefinition mane_rotation_r4 = upper_body.addOrReplaceChild("mane_rotation_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.6F, -2.85F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.75F, -5.5F, -0.0436F, 0.0F, 0.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 32).addBox(-2.5F, -2.37F, -1.35F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.002F)), PartPose.offset(0.0F, -0.5F, -1.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(33, 2).addBox(-1.5F, -0.164F, -3.357F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 2.297F, -0.5009F, 0.1745F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 41).addBox(-2.5F, -0.05F, -1.6F, 5.0F, 2.0F, 2.0F, new CubeDeformation(-0.101F)), PartPose.offsetAndRotation(0.0F, 1.73F, -2.5F, 2.138F, 0.0F, 0.0F));

		PartDefinition head_r3 = real_head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(35, 47).addBox(-0.5F, -0.75F, 1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.39F))
		.texOffs(35, 47).addBox(-0.5F, -0.65F, 1.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.29F))
		.texOffs(14, 41).addBox(-2.5F, -0.35F, 0.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.73F, -2.5F, 1.4835F, 0.0F, 0.0F));

		PartDefinition head_r4 = real_head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(18, 36).addBox(-2.5F, -1.6F, 1.9F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.73F, -2.5F, 0.9163F, 0.0F, 0.0F));

		PartDefinition head_r5 = real_head.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(0, 32).addBox(-2.5F, -1.0F, -2.7F, 5.0F, 5.0F, 4.0F, new CubeDeformation(-0.098F)), PartPose.offsetAndRotation(0.0F, 0.13F, 3.65F, -0.7854F, 0.0F, 0.0F));

		PartDefinition head_r6 = real_head.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(24, 16).mirror().addBox(0.0932F, -1.7044F, -1.2714F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.197F, -2.3009F, 0.5412F, 0.4595F, 0.2605F));

		PartDefinition head_r7 = real_head.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(24, 16).addBox(-2.0932F, -1.7044F, -1.2714F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 2.197F, -2.3009F, 0.5412F, -0.4595F, -0.2605F));

		PartDefinition head_r8 = real_head.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(23, 0).addBox(-1.5F, -1.7044F, -2.1188F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.099F)), PartPose.offsetAndRotation(0.0F, 2.197F, -2.3009F, 0.48F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(43, 0).addBox(-1.4F, -0.75F, -1.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
		.texOffs(43, 0).addBox(-0.4F, -0.6001F, -1.7565F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
		.texOffs(43, 0).addBox(-0.5F, -1.65F, -1.65F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(43, 0).addBox(-0.9F, -2.4F, -1.85F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(43, 0).addBox(-0.9F, -3.05F, -2.15F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F))
		.texOffs(43, 0).addBox(-1.25F, -1.6587F, -1.7496F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(43, 0).addBox(-1.25F, -2.15F, -1.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F))
		.texOffs(43, 0).addBox(-0.5F, -2.15F, -1.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(-1.5F, -2.5F, 0.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition cube_r1 = right_ear.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(51, 0).addBox(-1.4F, -3.4F, -1.95F, 3.0F, 4.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(-0.25F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(43, 0).mirror().addBox(-0.6F, -0.75F, -1.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(43, 0).mirror().addBox(-1.6F, -0.6001F, -1.7565F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)).mirror(false)
		.texOffs(43, 0).mirror().addBox(-1.5F, -1.65F, -1.65F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(43, 0).mirror().addBox(-1.1F, -2.4F, -1.85F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(43, 0).mirror().addBox(-1.1F, -3.05F, -2.15F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).mirror(false)
		.texOffs(43, 0).mirror().addBox(-0.75F, -1.6587F, -1.7496F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(43, 0).mirror().addBox(-0.75F, -2.15F, -1.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false)
		.texOffs(43, 0).mirror().addBox(-1.5F, -2.15F, -1.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).mirror(false), PartPose.offsetAndRotation(1.5F, -2.5F, 0.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition cube_r2 = left_ear.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(51, 0).mirror().addBox(-1.6F, -3.4F, -1.95F, 3.0F, 4.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offsetAndRotation(0.25F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	public static void createAugment() {
		
	}

	@Override
    public boolean useDefaultModelForAccessories() {
        return true;
    }

	@Override
	public boolean tickClient() { return true; }

	@Override
    public void doTickClient(Dog dog) {
	}
    
}
