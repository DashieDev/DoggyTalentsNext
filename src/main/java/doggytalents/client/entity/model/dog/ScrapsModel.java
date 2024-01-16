package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class ScrapsModel extends GlowingEyeDogModel {

    public ScrapsModel(ModelPart box) {
        super(box, RenderType::entityTranslucent);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 6.75F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(10, 19).addBox(-0.5F, 0.2713F, -0.7723F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(10, 19).addBox(-0.5F, 1.1021F, -0.7092F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
		.texOffs(10, 19).addBox(-0.5F, 0.1021F, -0.7092F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
		.texOffs(10, 19).addBox(-0.5F, -0.3979F, -0.7092F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 4.0508F, 0.6094F, 0.3927F, 0.0F, 0.0F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(10, 19).addBox(-0.5F, 1.2081F, -1.2763F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(0.0F, 4.0508F, 0.6094F, 0.6981F, 0.0F, 0.0F));

		PartDefinition tail_r3 = real_tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(10, 19).addBox(-0.5F, -0.8751F, -0.7119F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0F, 4.0508F, 0.6094F, 0.3054F, 0.0F, 0.0F));

		PartDefinition tail_r4 = real_tail.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(10, 19).addBox(-0.5F, -1.8266F, -0.7989F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0F, 4.0508F, 0.6094F, 0.2182F, 0.0F, 0.0F));

		PartDefinition tail_r5 = real_tail.addOrReplaceChild("tail_r5", CubeListBuilder.create().texOffs(10, 19).addBox(-0.5F, -2.3266F, -0.6989F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 4.0508F, 0.6094F, 0.2618F, 0.0F, 0.0F));

		PartDefinition tail_r6 = real_tail.addOrReplaceChild("tail_r6", CubeListBuilder.create().texOffs(10, 19).addBox(-0.5F, -2.7794F, -1.0317F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0F, 4.0508F, 0.6094F, 0.1309F, 0.0F, 0.0F));

		PartDefinition tail_r7 = real_tail.addOrReplaceChild("tail_r7", CubeListBuilder.create().texOffs(10, 19).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.7713F, -0.2723F, 0.0436F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.1F, 1.75F, -1.35F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false), PartPose.offset(-1.5F, 16.5F, 6.5F));

		PartDefinition leg7_r1 = right_hind_leg.addOrReplaceChild("leg7_r1", CubeListBuilder.create().texOffs(10, 4).mirror().addBox(-1.5F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.7F)).mirror(false), PartPose.offsetAndRotation(-0.1F, 7.4002F, -1.2229F, 0.1415F, 0.3892F, 0.054F));

		PartDefinition leg6_r1 = right_hind_leg.addOrReplaceChild("leg6_r1", CubeListBuilder.create().texOffs(10, 4).mirror().addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.7F)).mirror(false), PartPose.offsetAndRotation(-0.1F, 7.4002F, -1.2229F, 0.1309F, 0.0F, 0.0F));

		PartDefinition leg5_r1 = right_hind_leg.addOrReplaceChild("leg5_r1", CubeListBuilder.create().texOffs(10, 4).mirror().addBox(-0.5F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.7F)).mirror(false), PartPose.offsetAndRotation(-0.1F, 7.4002F, -1.2229F, 0.1443F, -0.4323F, -0.0608F));

		PartDefinition leg6_r2 = right_hind_leg.addOrReplaceChild("leg6_r2", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, -4.5F, -0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offsetAndRotation(-0.1F, 5.75F, -0.35F, 0.0848F, 0.0278F, -0.0074F));

		PartDefinition leg5_r2 = right_hind_leg.addOrReplaceChild("leg5_r2", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, -2.5F, -1.15F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offsetAndRotation(-0.1F, 5.75F, -0.35F, -0.0873F, 0.0F, 0.0F));

		PartDefinition leg4_r1 = right_hind_leg.addOrReplaceChild("leg4_r1", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, -2.0F, -1.15F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false), PartPose.offsetAndRotation(-0.1F, 5.75F, -0.35F, -0.1309F, 0.0F, 0.0F));

		PartDefinition leg4_r2 = right_hind_leg.addOrReplaceChild("leg4_r2", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, -1.5F, -1.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.6F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.25F, 0.0F, 0.0801F, 0.056F, 0.0496F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-0.9F, 1.75F, -1.35F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.6F)), PartPose.offset(1.5F, 16.5F, 6.5F));

		PartDefinition leg4_r3 = left_hind_leg.addOrReplaceChild("leg4_r3", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(0.0F, 0.25F, 0.0F, 0.0801F, -0.056F, -0.0496F));

		PartDefinition leg7_r2 = left_hind_leg.addOrReplaceChild("leg7_r2", CubeListBuilder.create().texOffs(10, 4).addBox(-0.5F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(0.1F, 7.4002F, -1.2229F, 0.1415F, -0.3892F, -0.054F));

		PartDefinition leg6_r3 = left_hind_leg.addOrReplaceChild("leg6_r3", CubeListBuilder.create().texOffs(10, 4).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(0.1F, 7.4002F, -1.2229F, 0.1309F, 0.0F, 0.0F));

		PartDefinition leg5_r3 = left_hind_leg.addOrReplaceChild("leg5_r3", CubeListBuilder.create().texOffs(10, 4).addBox(-1.5F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(0.1F, 7.4002F, -1.2229F, 0.1443F, 0.4323F, 0.0608F));

		PartDefinition leg6_r4 = left_hind_leg.addOrReplaceChild("leg6_r4", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -4.5F, -0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.1F, 5.75F, -0.35F, 0.0848F, -0.0278F, 0.0074F));

		PartDefinition leg5_r4 = left_hind_leg.addOrReplaceChild("leg5_r4", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -2.5F, -1.15F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.1F, 5.75F, -0.35F, -0.0873F, 0.0F, 0.0F));

		PartDefinition leg4_r4 = left_hind_leg.addOrReplaceChild("leg4_r4", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -2.0F, -1.15F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(0.1F, 5.75F, -0.35F, -0.1309F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(1, 19).mirror().addBox(-1.0F, 1.5F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition mane_rotation_r1 = right_front_leg.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-2.8376F, -1.0F, 1.0653F, 3.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(-0.3157F, -0.4623F, -0.3021F, 1.6007F, -0.082F, -1.9211F));

		PartDefinition mane_rotation_r2 = right_front_leg.addOrReplaceChild("mane_rotation_r2", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-1.0378F, -1.0872F, 0.7579F, 3.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(-0.3157F, 0.5377F, -0.3021F, 1.5176F, -0.0692F, -0.9145F));

		PartDefinition mane_rotation_r3 = right_front_leg.addOrReplaceChild("mane_rotation_r3", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(0.3582F, -1.0F, 0.3869F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(-0.3157F, -0.4623F, -0.3021F, 1.4993F, -0.05F, -0.6091F));

		PartDefinition leg7_r3 = right_front_leg.addOrReplaceChild("leg7_r3", CubeListBuilder.create().texOffs(1, 19).mirror().addBox(-0.5F, -1.75F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(1, 19).mirror().addBox(-0.5F, -0.75F, -0.25F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.0F, -0.3F, 0.3491F, 0.0F, 0.0F));

		PartDefinition leg7_r4 = right_front_leg.addOrReplaceChild("leg7_r4", CubeListBuilder.create().texOffs(10, 4).mirror().addBox(-1.5F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.7F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 7.6502F, -0.7229F, 0.1415F, 0.3892F, 0.054F));

		PartDefinition leg6_r5 = right_front_leg.addOrReplaceChild("leg6_r5", CubeListBuilder.create().texOffs(10, 4).mirror().addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.7F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 7.6502F, -0.7229F, 0.1309F, 0.0F, 0.0F));

		PartDefinition leg5_r5 = right_front_leg.addOrReplaceChild("leg5_r5", CubeListBuilder.create().texOffs(10, 4).mirror().addBox(-0.5F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.7F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 7.6502F, -0.7229F, 0.1443F, -0.4323F, -0.0608F));

		PartDefinition leg7_r5 = right_front_leg.addOrReplaceChild("leg7_r5", CubeListBuilder.create().texOffs(1, 19).mirror().addBox(-0.5F, 0.25F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.0F, -0.3F, -0.5236F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(1, 19).addBox(0.0F, 1.5F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition mane_rotation_r4 = left_front_leg.addOrReplaceChild("mane_rotation_r4", CubeListBuilder.create().texOffs(28, 10).addBox(-0.1624F, -1.0F, 1.0653F, 3.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.3157F, -0.4623F, -0.3021F, 1.6007F, 0.082F, 1.9211F));

		PartDefinition mane_rotation_r5 = left_front_leg.addOrReplaceChild("mane_rotation_r5", CubeListBuilder.create().texOffs(28, 10).addBox(-1.1719F, -1.0F, 0.1514F, 3.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.3157F, -0.4623F, -0.3021F, 1.5176F, 0.0692F, 0.9145F));

		PartDefinition mane_rotation_r6 = left_front_leg.addOrReplaceChild("mane_rotation_r6", CubeListBuilder.create().texOffs(28, 10).addBox(-2.3582F, -1.0F, 0.3869F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.3157F, -0.4623F, -0.3021F, 1.4993F, 0.05F, 0.6091F));

		PartDefinition leg7_r6 = left_front_leg.addOrReplaceChild("leg7_r6", CubeListBuilder.create().texOffs(1, 19).addBox(-0.5F, -1.75F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(1, 19).addBox(-0.5F, -0.75F, -0.25F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.5F, 5.0F, -0.3F, 0.3491F, 0.0F, 0.0F));

		PartDefinition leg7_r7 = left_front_leg.addOrReplaceChild("leg7_r7", CubeListBuilder.create().texOffs(10, 4).addBox(-0.5F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(0.5F, 7.6502F, -0.7229F, 0.1415F, -0.3892F, -0.054F));

		PartDefinition leg6_r6 = left_front_leg.addOrReplaceChild("leg6_r6", CubeListBuilder.create().texOffs(10, 4).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(0.5F, 7.6502F, -0.7229F, 0.1309F, 0.0F, 0.0F));

		PartDefinition leg5_r6 = left_front_leg.addOrReplaceChild("leg5_r6", CubeListBuilder.create().texOffs(10, 4).addBox(-1.5F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(0.5F, 7.6502F, -0.7229F, 0.1443F, 0.4323F, 0.0608F));

		PartDefinition leg7_r8 = left_front_leg.addOrReplaceChild("leg7_r8", CubeListBuilder.create().texOffs(1, 19).addBox(-0.5F, 0.25F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, -0.3F, -0.5236F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 15.25F, 2.25F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(23, 21).mirror().addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(1.0F, 4.3127F, 0.2477F, -0.0464F, 0.3487F, -0.0159F));

		PartDefinition body_rotation_r2 = body.addOrReplaceChild("body_rotation_r2", CubeListBuilder.create().texOffs(23, 21).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.0F, 4.3127F, 0.2477F, -0.0464F, -0.3487F, 0.0159F));

		PartDefinition body_rotation_r3 = body.addOrReplaceChild("body_rotation_r3", CubeListBuilder.create().texOffs(24, 22).addBox(-1.5F, 4.5F, -1.25F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -1.0169F, 1.6952F, -0.0436F, 0.0F, 0.0F));

		PartDefinition body_rotation_r4 = body.addOrReplaceChild("body_rotation_r4", CubeListBuilder.create().texOffs(23, 19).addBox(-0.5F, 4.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(23, 19).addBox(-0.5F, 5.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(23, 19).addBox(-0.5F, 5.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(23, 19).addBox(-0.5F, 4.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(23, 19).addBox(-0.5F, 3.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(23, 19).addBox(-0.5F, 2.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(23, 19).addBox(-0.5F, 2.0F, -0.25F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, -1.5169F, 1.0452F, -0.1745F, 0.0F, 0.0F));

		PartDefinition body_rotation_r5 = body.addOrReplaceChild("body_rotation_r5", CubeListBuilder.create().texOffs(23, 19).addBox(-0.5F, 1.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(23, 19).addBox(-0.5F, 0.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(23, 19).addBox(-0.5F, -1.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(23, 19).addBox(-0.5F, -2.0F, -0.25F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 16.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r7 = upper_body.addOrReplaceChild("mane_rotation_r7", CubeListBuilder.create().texOffs(28, 10).addBox(-0.9899F, -0.5068F, 0.3154F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.9602F, 2.4621F, -0.754F, -1.4046F, 1.5538F, -1.325F));

		PartDefinition mane_rotation_r8 = upper_body.addOrReplaceChild("mane_rotation_r8", CubeListBuilder.create().texOffs(28, 10).addBox(-2.5895F, -0.4763F, 0.5174F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.9602F, 2.4621F, -0.754F, -0.0609F, 0.8709F, -0.0128F));

		PartDefinition mane_rotation_r9 = upper_body.addOrReplaceChild("mane_rotation_r9", CubeListBuilder.create().texOffs(28, 10).addBox(-0.9899F, -0.5068F, 0.3154F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.4454F, 2.9622F, -0.6312F, 2.9595F, 1.3117F, 3.0354F));

		PartDefinition mane_rotation_r10 = upper_body.addOrReplaceChild("mane_rotation_r10", CubeListBuilder.create().texOffs(28, 10).addBox(-2.5895F, -0.4763F, 0.5174F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.4602F, 2.9621F, -0.754F, 0.0306F, 1.1319F, 0.0592F));

		PartDefinition mane_rotation_r11 = upper_body.addOrReplaceChild("mane_rotation_r11", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-0.4105F, -0.4763F, 0.5174F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(-0.9602F, 2.4621F, -0.754F, -0.0609F, -0.8709F, 0.0128F));

		PartDefinition mane_rotation_r12 = upper_body.addOrReplaceChild("mane_rotation_r12", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-1.0101F, -0.5068F, 0.3154F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(-0.9602F, 2.4621F, -0.754F, -1.4046F, -1.5538F, 1.325F));

		PartDefinition mane_rotation_r13 = upper_body.addOrReplaceChild("mane_rotation_r13", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-1.3265F, -0.4927F, 0.4791F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(-0.9602F, 2.4621F, -0.754F, -3.1122F, -0.6565F, 3.0535F));

		PartDefinition mane_rotation_r14 = upper_body.addOrReplaceChild("mane_rotation_r14", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-0.4105F, -0.4763F, 0.5174F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(-0.4602F, 2.9621F, -0.754F, 0.0306F, -1.1319F, -0.0592F));

		PartDefinition mane_rotation_r15 = upper_body.addOrReplaceChild("mane_rotation_r15", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-1.0101F, -0.5068F, 0.3154F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(-0.4454F, 2.9622F, -0.6312F, 2.9595F, -1.3117F, -3.0354F));

		PartDefinition mane_rotation_r16 = upper_body.addOrReplaceChild("mane_rotation_r16", CubeListBuilder.create().texOffs(28, 10).addBox(-0.6735F, -0.4927F, 0.4791F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.9602F, 2.4621F, -0.754F, -3.1122F, 0.6565F, -3.0535F));

		PartDefinition mane_rotation_r17 = upper_body.addOrReplaceChild("mane_rotation_r17", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-1.4726F, -0.4829F, 0.5735F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(-1.386F, 1.805F, -0.915F, -3.0637F, -0.3905F, 3.0614F));

		PartDefinition mane_rotation_r18 = upper_body.addOrReplaceChild("mane_rotation_r18", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-2.1857F, -0.4511F, 0.7803F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(-1.687F, 1.1072F, -0.2612F, -3.0922F, -0.6547F, 3.0777F));

		PartDefinition mane_rotation_r19 = upper_body.addOrReplaceChild("mane_rotation_r19", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-1.4198F, -0.4829F, 0.2822F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(-1.386F, 1.805F, -0.915F, -3.0478F, -0.6948F, 3.0309F));

		PartDefinition mane_rotation_r20 = upper_body.addOrReplaceChild("mane_rotation_r20", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-0.9779F, -0.4829F, 0.0641F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(-1.386F, 1.805F, -0.915F, -0.5088F, -1.4225F, 0.4536F));

		PartDefinition mane_rotation_r21 = upper_body.addOrReplaceChild("mane_rotation_r21", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(0.159F, -0.4829F, 0.2459F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(-1.5343F, 1.805F, -1.0157F, -0.1385F, -0.9562F, 0.0762F));

		PartDefinition mane_rotation_r22 = upper_body.addOrReplaceChild("mane_rotation_r22", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-2.1857F, -0.4511F, 0.7803F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(-1.3933F, 1.1143F, 0.174F, -3.0732F, -0.9596F, 3.0518F));

		PartDefinition mane_rotation_r23 = upper_body.addOrReplaceChild("mane_rotation_r23", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-1.74F, -0.4511F, -0.2762F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(-1.3933F, 1.1143F, 0.174F, -0.1019F, -1.1754F, 0.0603F));

		PartDefinition mane_rotation_r24 = upper_body.addOrReplaceChild("mane_rotation_r24", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-0.383F, -0.4511F, -0.2952F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(-1.3498F, 1.1218F, 0.245F, -0.0609F, -0.8709F, 0.0128F));

		PartDefinition mane_rotation_r25 = upper_body.addOrReplaceChild("mane_rotation_r25", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-2.6096F, -1.5404F, 0.5706F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-2.0119F, 1.0423F, -1.4911F, -2.9994F, -0.4737F, 3.0423F));

		PartDefinition mane_rotation_r26 = upper_body.addOrReplaceChild("mane_rotation_r26", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-1.6096F, -1.5404F, 0.5706F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-1.7219F, 1.089F, -1.0461F, -2.9242F, -0.9465F, 2.9302F));

		PartDefinition mane_rotation_r27 = upper_body.addOrReplaceChild("mane_rotation_r27", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-1.2537F, -1.5404F, 0.097F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-1.7304F, 1.0992F, -0.9681F, -0.3222F, -1.1613F, 0.2631F));

		PartDefinition mane_rotation_r28 = upper_body.addOrReplaceChild("mane_rotation_r28", CubeListBuilder.create().texOffs(28, 10).mirror().addBox(-0.0313F, -1.5404F, 0.207F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-1.5998F, 1.1218F, -0.755F, -0.1957F, -0.8653F, 0.1157F));

		PartDefinition mane_rotation_r29 = upper_body.addOrReplaceChild("mane_rotation_r29", CubeListBuilder.create().texOffs(28, 10).addBox(0.4726F, -0.4829F, 0.5735F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(1.386F, 1.805F, -0.915F, -3.0637F, 0.3905F, -3.0614F));

		PartDefinition mane_rotation_r30 = upper_body.addOrReplaceChild("mane_rotation_r30", CubeListBuilder.create().texOffs(28, 10).addBox(1.1857F, -0.4511F, 0.7803F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(1.687F, 1.1072F, -0.2612F, -3.0922F, 0.6547F, -3.0777F));

		PartDefinition mane_rotation_r31 = upper_body.addOrReplaceChild("mane_rotation_r31", CubeListBuilder.create().texOffs(28, 10).addBox(-0.5802F, -0.4829F, 0.2822F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(1.386F, 1.805F, -0.915F, -3.0478F, 0.6948F, -3.0309F));

		PartDefinition mane_rotation_r32 = upper_body.addOrReplaceChild("mane_rotation_r32", CubeListBuilder.create().texOffs(28, 10).addBox(-1.0221F, -0.4829F, 0.0641F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(1.386F, 1.805F, -0.915F, -0.5088F, 1.4225F, -0.4536F));

		PartDefinition mane_rotation_r33 = upper_body.addOrReplaceChild("mane_rotation_r33", CubeListBuilder.create().texOffs(28, 10).addBox(-3.159F, -0.4829F, 0.2459F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(1.5343F, 1.805F, -1.0157F, -0.1385F, 0.9562F, -0.0762F));

		PartDefinition mane_rotation_r34 = upper_body.addOrReplaceChild("mane_rotation_r34", CubeListBuilder.create().texOffs(28, 10).addBox(0.1857F, -0.4511F, 0.7803F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(1.3933F, 1.1143F, 0.174F, -3.0732F, 0.9596F, -3.0518F));

		PartDefinition mane_rotation_r35 = upper_body.addOrReplaceChild("mane_rotation_r35", CubeListBuilder.create().texOffs(28, 10).addBox(-0.26F, -0.4511F, -0.2762F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(1.3933F, 1.1143F, 0.174F, -0.1019F, 1.1754F, -0.0603F));

		PartDefinition mane_rotation_r36 = upper_body.addOrReplaceChild("mane_rotation_r36", CubeListBuilder.create().texOffs(28, 10).addBox(-2.617F, -0.4511F, -0.2952F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(1.3498F, 1.1218F, 0.245F, -0.0609F, 0.8709F, -0.0128F));

		PartDefinition mane_rotation_r37 = upper_body.addOrReplaceChild("mane_rotation_r37", CubeListBuilder.create().texOffs(28, 10).addBox(0.6096F, -1.5404F, 0.5706F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(2.0119F, 1.0423F, -1.4911F, -2.9994F, 0.4737F, -3.0423F));

		PartDefinition mane_rotation_r38 = upper_body.addOrReplaceChild("mane_rotation_r38", CubeListBuilder.create().texOffs(28, 10).addBox(-0.3904F, -1.5404F, 0.5706F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.7219F, 1.089F, -1.0461F, -2.9242F, 0.9465F, -2.9302F));

		PartDefinition mane_rotation_r39 = upper_body.addOrReplaceChild("mane_rotation_r39", CubeListBuilder.create().texOffs(28, 10).addBox(-0.7463F, -1.5404F, 0.097F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.7304F, 1.0992F, -0.9681F, -0.3222F, 1.1613F, -0.2631F));

		PartDefinition mane_rotation_r40 = upper_body.addOrReplaceChild("mane_rotation_r40", CubeListBuilder.create().texOffs(28, 10).addBox(-2.9687F, -1.5404F, 0.207F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.5998F, 1.1218F, -0.755F, -0.1957F, 0.8653F, -0.1157F));

		PartDefinition mane_rotation_r41 = upper_body.addOrReplaceChild("mane_rotation_r41", CubeListBuilder.create().texOffs(27, 6).addBox(0.5F, 3.25F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F))
		.texOffs(27, 6).addBox(0.5F, 1.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F))
		.texOffs(27, 6).addBox(0.5F, 0.5F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -1.2447F, 2.1839F, -0.3491F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r42 = upper_body.addOrReplaceChild("mane_rotation_r42", CubeListBuilder.create().texOffs(27, 6).addBox(0.5F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F))
		.texOffs(27, 6).addBox(0.5F, -2.75F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F))
		.texOffs(27, 6).addBox(0.5F, -3.0F, 1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 1.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 12.25F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(17, 30).addBox(-3.0F, -2.5F, -2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(19, 20).addBox(-2.5F, 0.25F, -2.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(50, 28).addBox(-1.5F, -0.75F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(0.0F, 4.7874F, -3.3646F, 0.2618F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(38, 0).addBox(-1.55F, -0.4F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(38, 0).addBox(-1.25F, -0.4F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(38, 0).addBox(-0.5F, -0.4F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(38, 0).addBox(-0.5F, -0.4F, -1.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(38, 0).addBox(-0.5F, -0.4F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(38, 0).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(38, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(38, 0).mirror().addBox(-1.9F, -0.5F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(-2.3F, -0.4F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(-2.3F, -0.4F, -1.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(-2.3F, -0.4F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(-2.3F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(-2.3F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(-2.85F, -1.75F, -0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(-2.85F, -1.75F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).addBox(0.05F, -1.75F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(38, 0).addBox(0.05F, -1.75F, -0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(38, 0).addBox(-0.9F, -0.5F, -2.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.9F, 4.1537F, -2.9053F, 0.2182F, 0.0F, 0.0F));

		PartDefinition head_r3 = real_head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(38, 0).addBox(0.5F, -0.2132F, -3.2917F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
		.texOffs(38, 0).addBox(0.5F, -0.2132F, -2.6917F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
		.texOffs(38, 0).mirror().addBox(-1.5F, -0.2132F, -2.0917F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(-1.5F, -0.2132F, -2.6917F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(-1.5F, -0.2132F, -3.2917F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false)
		.texOffs(38, 0).addBox(0.5F, -0.2132F, -2.0917F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
		.texOffs(38, 8).addBox(-1.5F, 0.1368F, -3.2917F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 2.2409F, 0.1482F, 0.48F, 0.0F, 0.0F));

		PartDefinition head_r4 = real_head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(38, 0).mirror().addBox(1.1F, -0.7F, -2.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(0.7F, -0.7F, -2.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(0.3F, -0.7F, -2.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(-0.2F, -0.7F, -2.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(-0.2F, -0.7F, -2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(-0.2F, -0.7F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(-0.2F, -0.7F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(-0.2F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).addBox(1.5F, -0.7F, -2.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
		.texOffs(38, 0).addBox(1.5F, -0.7F, -2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(38, 0).addBox(1.5F, -0.7F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(38, 0).addBox(1.5F, -0.7F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(38, 0).addBox(1.5F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-1.15F, 3.3554F, -5.0796F, 0.1309F, 0.0F, 0.0F));

		PartDefinition head_r5 = real_head.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(38, 0).mirror().addBox(-0.75F, -1.75F, -1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).addBox(1.55F, -1.75F, -1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-0.9F, 4.1537F, -2.9053F, 0.1745F, 0.0F, 0.0F));

		PartDefinition head_r6 = real_head.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(38, 0).mirror().addBox(-0.85F, -1.65F, -1.35F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).mirror().addBox(-0.85F, -1.65F, -0.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(38, 0).addBox(1.65F, -1.65F, -0.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
		.texOffs(38, 0).addBox(1.65F, -1.65F, -1.35F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-0.9F, 4.1537F, -2.9053F, 0.1309F, 0.0F, 0.0F));

		PartDefinition head_r7 = real_head.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.0F, -3.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 2.23F, -4.5F, 0.1309F, 0.0F, 0.0F));

		PartDefinition head_r8 = real_head.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(-1.6F, -1.0F, 0.2F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.23F, -4.5F, 0.1443F, -0.4323F, -0.0608F));

		PartDefinition head_r9 = real_head.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(0, 31).addBox(-1.4F, -1.0F, 0.2F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 2.23F, -4.5F, 0.1443F, 0.4323F, 0.0608F));

		PartDefinition head_r10 = real_head.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(33, 5).addBox(-1.5F, -1.05F, -0.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 2.23F, -4.5F, 0.2618F, 0.0F, 0.0F));

		PartDefinition glowing_eyes = partdefinition.addOrReplaceChild("glowing_eyes", CubeListBuilder.create(), PartPose.offset(0.0F, 12.25F, -7.0F));

		PartDefinition real_glowing_eyes = glowing_eyes.addOrReplaceChild("real_glowing_eyes", CubeListBuilder.create().texOffs(0, 39).addBox(-3.0F, -2.25F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 39).mirror().addBox(1.0F, -2.25F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	@Override
    public boolean useDefaultModelForAccessories() {
        return true;
    }

}
