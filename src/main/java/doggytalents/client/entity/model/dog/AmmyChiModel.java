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

public class AmmyChiModel extends DogModel {

	

    public AmmyChiModel(ModelPart box) {
		super(box, RenderType::entityTranslucent);
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-1.0F, 14.25F, 8.5F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(24, 23).addBox(0.0F, -0.75F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.4F))
		.texOffs(24, 24).addBox(0.0F, 4.75F, 2.3F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(24, 24).addBox(0.0F, 1.55F, 3.6F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 24).addBox(0.0F, 1.15F, 2.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 24).addBox(0.0F, 3.75F, 1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.35F))
		.texOffs(24, 24).addBox(0.0F, 3.75F, 4.1F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.35F))
		.texOffs(33, 24).mirror().addBox(-0.15F, -0.3F, -0.9F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.4F)).mirror(false)
		.texOffs(33, 24).addBox(1.15F, -0.3F, -0.9F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 1.25F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -4.5F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(0.0F, 0.8482F, 2.5721F, 1.3963F, 0.0F, 0.0F));

		PartDefinition body_rotation_r2 = body.addOrReplaceChild("body_rotation_r2", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -12.0F, -0.5F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, 2.0F, 10.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone9 = body.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(44, 0).addBox(2.75F, -5.978F, -13.9756F, 1.0F, 10.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(44, 0).mirror().addBox(-3.85F, -5.978F, -13.2756F, 1.0F, 10.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 10.75F));

		PartDefinition body_rotation_r3 = bone9.addOrReplaceChild("body_rotation_r3", CubeListBuilder.create().texOffs(51, 54).mirror().addBox(-1.6F, -4.6F, -1.25F, 4.0F, 9.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -5.5F, -9.2F, 1.5708F, 0.0F, -0.3927F));

		PartDefinition body_rotation_r4 = bone9.addOrReplaceChild("body_rotation_r4", CubeListBuilder.create().texOffs(51, 54).addBox(-2.3F, -4.6F, -0.95F, 4.0F, 9.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(3.0F, -5.5F, -8.5F, 1.5708F, 0.0F, 0.3927F));

		PartDefinition bone7 = bone9.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -7.27F, -20.25F, -0.3491F, 0.0F, 0.0F));

		PartDefinition head_r1 = bone7.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(4, 24).mirror().addBox(-3.3F, -0.4F, -1.25F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.35F)).mirror(false)
		.texOffs(4, 24).addBox(0.35F, -0.4F, -1.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(3, 19).addBox(-2.5F, -1.25F, -7.25F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(3, 20).addBox(-1.0F, -1.3F, -7.4F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.15F))
		.texOffs(4, 22).addBox(1.0F, -1.25F, -7.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(3, 19).addBox(-2.75F, -1.65F, -3.75F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.05F))
		.texOffs(4, 22).addBox(1.0F, -1.65F, -3.75F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.05F))
		.texOffs(3, 21).addBox(-2.25F, -2.45F, -3.15F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(4, 24).addBox(-3.5F, -1.65F, -2.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(2, 24).addBox(-3.5F, -0.65F, -2.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(9, 23).addBox(-1.5F, -2.1F, 0.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(3, 22).addBox(0.75F, -2.0F, -1.65F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(1, 23).addBox(0.5F, -1.65F, -2.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(2, 22).addBox(0.5F, -2.45F, -3.15F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(2, 20).addBox(-3.25F, -2.0F, -1.65F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(1, 20).addBox(-1.0F, -2.45F, -4.4F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.2F))
		.texOffs(2, 23).mirror().addBox(-2.5F, 1.6F, -4.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(1.2F)).mirror(false)
		.texOffs(2, 23).addBox(0.5F, 1.6F, -5.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(1.2F))
		.texOffs(7, 22).mirror().addBox(-3.0F, -1.1F, 0.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(7, 22).addBox(2.0F, -1.1F, 0.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, -1.5396F, 14.8582F, 0.3491F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = bone7.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(3, 21).addBox(-1.75F, -0.75F, -2.35F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.65F))
		.texOffs(0, 20).mirror().addBox(-4.0F, -0.5F, -3.35F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(0, 20).addBox(0.0F, -0.5F, -3.35F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -1.5396F, 14.8582F, 1.9199F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r2 = bone7.addOrReplaceChild("mane_rotation_r2", CubeListBuilder.create().texOffs(2, 21).addBox(-2.25F, -0.0005F, -1.7718F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.45F)), PartPose.offsetAndRotation(0.25F, -1.2539F, 16.5585F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone5 = bone9.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(1, 24).mirror().addBox(1.5937F, -0.6875F, -0.675F, 3.0F, 1.0F, 2.0F, new CubeDeformation(1.0F)).mirror(false)
		.texOffs(3, 20).mirror().addBox(0.8437F, -0.5375F, -3.325F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F)).mirror(false)
		.texOffs(1, 20).mirror().addBox(3.8409F, -0.6684F, -3.075F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F)).mirror(false)
		.texOffs(5, 20).mirror().addBox(-1.6563F, -0.2875F, -2.575F, 2.0F, 2.0F, 4.0F, new CubeDeformation(1.0F)).mirror(false)
		.texOffs(3, 20).mirror().addBox(-2.4063F, -0.7875F, -3.325F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.65F)).mirror(false)
		.texOffs(5, 21).mirror().addBox(2.8437F, -0.9875F, 1.075F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.85F)).mirror(false)
		.texOffs(6, 23).mirror().addBox(-2.6563F, -0.9875F, 0.575F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.65F)).mirror(false)
		.texOffs(8, 23).mirror().addBox(-1.4063F, -0.6875F, -0.675F, 1.0F, 1.0F, 2.0F, new CubeDeformation(1.1F)).mirror(false), PartPose.offsetAndRotation(-1.8438F, -0.925F, -6.0125F, 1.5708F, 1.5272F, 0.0F));

		PartDefinition bone6 = bone9.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(1, 24).addBox(-4.5937F, -0.6875F, -0.675F, 3.0F, 1.0F, 2.0F, new CubeDeformation(1.0F))
		.texOffs(3, 20).addBox(-2.8437F, -0.7875F, -3.075F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F))
		.texOffs(1, 20).addBox(-5.8409F, -0.9184F, -2.825F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.75F))
		.texOffs(5, 20).addBox(-0.3437F, -0.2875F, -2.575F, 2.0F, 2.0F, 4.0F, new CubeDeformation(1.0F))
		.texOffs(3, 20).addBox(0.4063F, -0.7875F, -3.325F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.65F))
		.texOffs(5, 21).addBox(-4.5937F, -0.9875F, 1.075F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.85F))
		.texOffs(6, 23).addBox(0.6563F, -0.9875F, 0.575F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.65F))
		.texOffs(6, 21).addBox(0.6563F, -0.6875F, -0.675F, 1.0F, 1.0F, 2.0F, new CubeDeformation(1.1F)), PartPose.offsetAndRotation(1.8438F, -0.925F, -6.0125F, 1.5708F, -1.5272F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 12.92F, -7.375F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(24, 13).addBox(-3.0F, -3.42F, -1.375F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(24, 13).addBox(-3.0F, -3.92F, -1.375F, 6.0F, 1.0F, 4.0F, new CubeDeformation(-0.2F))
		.texOffs(24, 18).addBox(-3.0F, 2.33F, -1.375F, 6.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(23, 0).addBox(-1.5F, -0.49F, -3.875F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(11, 10).mirror().addBox(-3.25F, 1.53F, -0.625F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 10).mirror().addBox(-4.25F, 0.78F, -0.875F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(10, 9).mirror().addBox(-5.0F, -0.22F, -1.125F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 10).mirror().addBox(-4.5F, -0.72F, -0.975F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(13, 9).mirror().addBox(-4.0F, -1.97F, -0.875F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(13, 7).mirror().addBox(-3.0F, -2.97F, -1.025F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(10, 9).mirror().addBox(-3.75F, -2.72F, -0.875F, 3.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(10, 9).addBox(0.75F, -2.72F, -0.875F, 3.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(11, 10).addBox(2.25F, 1.53F, -0.625F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(11, 10).addBox(1.25F, 0.78F, -0.875F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 9).addBox(2.0F, -0.22F, -1.125F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(11, 10).addBox(3.5F, -0.72F, -0.975F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(13, 9).addBox(2.0F, -1.97F, -0.875F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(13, 7).addBox(2.0F, -2.97F, -1.025F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 13).addBox(0.15F, -1.2F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 13).addBox(0.45F, -0.7F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 13).addBox(-0.15F, -0.7F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(18, 13).addBox(-1.25F, -1.4F, -1.25F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.7F))
		.texOffs(0, 0).addBox(-0.45F, 0.1F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.6753F, -3.87F, 0.3411F, 0.0F, -0.5236F, 0.0F));

		PartDefinition head_r3 = real_head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(0, 13).mirror().addBox(-1.45F, -0.7F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(0, 13).mirror().addBox(-0.85F, -0.7F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(18, 13).mirror().addBox(-1.75F, -1.4F, -1.25F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.7F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(-1.55F, 0.1F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(0, 13).mirror().addBox(-1.15F, -1.2F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-1.6753F, -3.87F, 0.3411F, 0.0F, 0.5236F, 0.0F));

		PartDefinition headfur = real_head.addOrReplaceChild("headfur", CubeListBuilder.create(), PartPose.offset(0.0F, 10.08F, 7.625F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(1.6753F, -3.87F, 0.3411F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-1.6753F, -3.87F, 0.3411F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-1.5F, 18.0F, 7.0F));

		PartDefinition leg1 = right_hind_leg.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 3.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(33, 43).mirror().addBox(-0.1F, 1.7F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(-1.0F, -2.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(1.5F, 18.0F, 7.0F));

		PartDefinition leg2 = left_hind_leg.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 3.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(33, 43).addBox(1.1F, 1.7F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.0F, -2.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-1.5F, 18.0F, -4.0F));

		PartDefinition leg3 = right_front_leg.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(33, 43).mirror().addBox(-1.35F, 1.7F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(-1.0F, -2.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(1.5F, 18.0F, -4.0F));

		PartDefinition leg4 = left_front_leg.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(33, 43).addBox(1.35F, 1.7F, -1.4F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.0F, -2.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 16.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane = upper_body.addOrReplaceChild("mane", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 1.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation = mane.addOrReplaceChild("mane_rotation", CubeListBuilder.create().texOffs(4, 4).addBox(-4.0F, -5.5F, -1.75F, 8.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).mirror().addBox(-5.6F, -7.5F, -2.5F, 1.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 32).addBox(4.6F, -7.5F, -2.5F, 1.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.5F, 2.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r3 = mane_rotation.addOrReplaceChild("mane_rotation_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.45F, -2.4F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, -2.5F, 3.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition bone8 = mane.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offset(1.25F, -0.903F, -1.1584F));

		PartDefinition bone = bone8.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(5, 10).addBox(-2.75F, -5.847F, 4.1584F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.6F))
		.texOffs(5, 10).addBox(0.5F, -5.847F, 4.1584F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.6F))
		.texOffs(4, 9).addBox(-1.25F, -6.847F, 3.6584F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.35F))
		.texOffs(4, 5).addBox(1.0F, -4.697F, 0.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.35F))
		.texOffs(4, 5).addBox(0.25F, -5.647F, 0.0084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(4, 9).addBox(0.5F, -5.547F, 2.6584F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.6F))
		.texOffs(4, 3).addBox(-1.25F, -5.147F, -0.9916F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.5F))
		.texOffs(4, 5).addBox(-1.25F, -6.547F, 0.1584F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.25F))
		.texOffs(4, 9).addBox(-3.75F, -5.547F, 2.9084F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.5F))
		.texOffs(4, 5).addBox(-3.0F, -5.397F, 0.0084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(4, 5).addBox(-3.5F, -4.697F, 1.2584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.45F))
		.texOffs(4, 5).addBox(-3.5F, -4.697F, -0.4916F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(4, 5).mirror().addBox(-2.75F, -5.397F, -1.2416F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(4, 3).addBox(-1.25F, -4.897F, -1.7416F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.25F))
		.texOffs(4, 5).addBox(0.25F, -5.397F, -1.2416F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(4, 5).addBox(1.0F, -4.697F, -0.4916F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 4.25F, 2.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone2 = bone8.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 56).addBox(-3.25F, -6.647F, 0.1584F, 6.0F, 1.0F, 7.0F, new CubeDeformation(0.85F))
		.texOffs(0, 56).addBox(-3.25F, 3.353F, -0.3416F, 6.0F, 1.0F, 7.0F, new CubeDeformation(0.85F))
		.texOffs(5, 10).addBox(-3.5F, -5.847F, 5.4084F, 2.0F, 1.0F, 1.0F, new CubeDeformation(1.1F))
		.texOffs(5, 10).addBox(-3.0F, -6.097F, 4.9084F, 2.0F, 1.0F, 1.0F, new CubeDeformation(1.1F))
		.texOffs(5, 10).addBox(-3.0F, 0.153F, 5.1584F, 2.0F, 1.0F, 1.0F, new CubeDeformation(1.1F))
		.texOffs(5, 10).addBox(0.5F, -5.847F, 4.1584F, 2.0F, 1.0F, 1.0F, new CubeDeformation(1.1F))
		.texOffs(4, 9).addBox(-1.25F, -6.597F, 2.9084F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.85F))
		.texOffs(4, 5).addBox(1.0F, -4.697F, 0.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F))
		.texOffs(4, 5).addBox(0.25F, -5.647F, 0.0084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.75F))
		.texOffs(4, 9).addBox(0.5F, -5.547F, 2.6584F, 1.0F, 1.0F, 2.0F, new CubeDeformation(1.1F))
		.texOffs(4, 3).addBox(-1.25F, -5.147F, -0.9916F, 2.0F, 2.0F, 6.0F, new CubeDeformation(1.0F))
		.texOffs(4, 5).addBox(0.0F, -6.547F, 2.1584F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.75F))
		.texOffs(4, 5).addBox(0.0F, -0.547F, 2.1584F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.75F))
		.texOffs(4, 9).addBox(-3.75F, -5.547F, 2.6584F, 3.0F, 1.0F, 2.0F, new CubeDeformation(1.0F))
		.texOffs(4, 5).addBox(-3.0F, -5.397F, 0.0084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F))
		.texOffs(4, 5).addBox(-3.5F, -4.697F, 1.2584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.95F))
		.texOffs(4, 5).addBox(-3.5F, -4.697F, -0.4916F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.6F))
		.texOffs(4, 5).mirror().addBox(-2.75F, -5.397F, -1.2416F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
		.texOffs(4, 3).addBox(-1.25F, -4.897F, -1.7416F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.75F))
		.texOffs(4, 5).addBox(0.25F, -5.397F, -1.2416F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.5F))
		.texOffs(4, 5).addBox(1.0F, -4.697F, -0.4916F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(2.25F, 4.75F, 0.25F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone3 = bone8.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(5, 10).mirror().addBox(-2.5F, -5.847F, 4.1584F, 2.0F, 1.0F, 1.0F, new CubeDeformation(1.1F)).mirror(false)
		.texOffs(4, 9).mirror().addBox(-0.75F, -6.147F, 2.9084F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.85F)).mirror(false)
		.texOffs(4, 5).mirror().addBox(-3.0F, -4.697F, 0.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F)).mirror(false)
		.texOffs(4, 5).mirror().addBox(-2.25F, -5.647F, 0.0084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.75F)).mirror(false)
		.texOffs(4, 9).mirror().addBox(-1.5F, -5.547F, 2.6584F, 1.0F, 1.0F, 2.0F, new CubeDeformation(1.1F)).mirror(false)
		.texOffs(4, 3).mirror().addBox(-0.75F, -5.147F, -0.9916F, 2.0F, 2.0F, 6.0F, new CubeDeformation(1.0F)).mirror(false)
		.texOffs(4, 9).mirror().addBox(0.75F, -5.547F, 2.6584F, 3.0F, 1.0F, 2.0F, new CubeDeformation(1.0F)).mirror(false)
		.texOffs(4, 5).mirror().addBox(1.0F, -5.397F, 0.0084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F)).mirror(false)
		.texOffs(4, 5).mirror().addBox(1.5F, -4.697F, 1.2584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(1.15F)).mirror(false)
		.texOffs(4, 5).mirror().addBox(1.5F, -4.697F, -0.4916F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.6F)).mirror(false)
		.texOffs(4, 5).addBox(0.75F, -5.397F, -1.2416F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.5F))
		.texOffs(4, 3).mirror().addBox(-0.75F, -4.897F, -1.9916F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.75F)).mirror(false)
		.texOffs(4, 5).mirror().addBox(-2.25F, -5.397F, -1.2416F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
		.texOffs(4, 5).mirror().addBox(-3.0F, -4.697F, -0.4916F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offsetAndRotation(-2.25F, 4.75F, 0.25F, 1.5708F, -1.5708F, 0.0F));

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
