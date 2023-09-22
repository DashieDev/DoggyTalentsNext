package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CollieRoughModel extends DogModel {

    public CollieRoughModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 8.75F, -7.25F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -0.75F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(0, 0).addBox(-3.0F, -3.55F, -0.75F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, -0.25F, -1.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-1.5F, -0.5F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 10).addBox(-1.5F, -1.5F, -2.75F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 1.48F, -1.25F, 0.2182F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(29, 36).addBox(-3.0F, -1.25F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 1.0F, 2.25F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone4 = real_head.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(24, 55).addBox(2.0F, -2.43F, 5.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(19, 54).addBox(-4.0F, -1.73F, 2.25F, 8.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(19, 55).addBox(-1.0F, -3.78F, 0.85F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(23, 55).addBox(-2.5F, -3.33F, 2.6F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(23, 55).addBox(0.5F, -3.78F, 1.1F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(19, 58).addBox(0.5F, -2.98F, 1.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(23, 55).addBox(0.25F, -3.33F, 2.6F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(18, 59).addBox(-4.25F, -1.98F, 1.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(19, 58).addBox(-3.5F, -2.98F, 1.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(23, 55).addBox(-2.75F, -3.78F, 1.1F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(23, 55).addBox(1.0F, -2.98F, 0.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(19, 54).addBox(-2.75F, -2.98F, 0.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(18, 59).addBox(1.25F, -1.73F, 1.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.23F, 0.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = bone4.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(18, 55).addBox(-2.75F, -6.75F, 11.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.55F))
		.texOffs(19, 54).addBox(-2.75F, -6.25F, 12.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(-0.45F))
		.texOffs(18, 55).addBox(0.25F, -5.75F, 12.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 14.02F, 9.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition headfur = real_head.addOrReplaceChild("headfur", CubeListBuilder.create().texOffs(29, 24).addBox(2.0F, -13.8F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(28, 23).addBox(2.75F, -14.8F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(29, 24).addBox(4.25F, -15.3F, -8.6F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(31, 23).addBox(2.75F, -16.55F, -8.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(31, 21).addBox(2.75F, -17.55F, -8.65F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(28, 23).addBox(1.5F, -17.3F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(29, 24).mirror().addBox(-5.0F, -13.8F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(28, 23).mirror().addBox(-5.75F, -14.8F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(29, 24).mirror().addBox(-5.25F, -15.3F, -8.35F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(31, 23).mirror().addBox(-4.75F, -16.55F, -8.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(31, 21).mirror().addBox(-3.75F, -17.55F, -8.65F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(28, 23).mirror().addBox(-4.5F, -17.3F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offset(0.0F, 15.5F, 8.55F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, -2.5F, -0.25F, 0.0F, 0.0F, -0.1745F));

		PartDefinition head_r3 = right_ear.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(45, 3).addBox(-1.9587F, -1.9214F, -0.9505F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(-1.1885F, -0.4974F, 0.375F, -1.3552F, 0.0612F, 1.0574F));

		PartDefinition head_r4 = right_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(45, 0).addBox(-2.6101F, -1.086F, -0.9505F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.1885F, -0.4974F, 0.375F, -1.0313F, 1.1421F, 1.5427F));

		PartDefinition head_r5 = right_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(45, 0).addBox(-2.9988F, -1.1934F, -1.4275F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(45, 0).addBox(-2.3498F, -1.228F, -1.4429F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-1.1885F, -0.4974F, 0.375F, -0.7854F, 1.4835F, 2.0944F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(1.5F, -2.5F, -0.25F, 0.0F, 0.0F, 0.1745F));

		PartDefinition head_r6 = left_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(45, 3).mirror().addBox(0.9587F, -1.9214F, -0.9505F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(1.2319F, -0.2512F, 0.375F, -1.3552F, -0.0612F, -1.0574F));

		PartDefinition head_r7 = left_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(45, 0).mirror().addBox(1.6101F, -1.086F, -0.9505F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(1.2319F, -0.2512F, 0.375F, -1.0313F, -1.1421F, -1.5427F));

		PartDefinition head_r8 = left_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(45, 0).mirror().addBox(1.9988F, -1.1934F, -1.4275F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(45, 0).mirror().addBox(1.3498F, -1.228F, -1.4429F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(1.2319F, -0.2512F, 0.375F, -0.7854F, -1.4835F, -2.0944F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0781F, -3.8749F));

		PartDefinition mane_rotation_r2 = upper_body.addOrReplaceChild("mane_rotation_r2", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -1.8941F, -2.3884F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 3.7689F, -0.2835F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone = upper_body.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(1, 62).addBox(-2.75F, -5.847F, 6.1584F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.6F))
		.texOffs(1, 62).addBox(0.5F, -5.847F, 6.1584F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.6F))
		.texOffs(0, 61).addBox(-1.25F, -6.847F, 5.6584F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.35F))
		.texOffs(0, 57).addBox(1.0F, -4.697F, 2.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.35F))
		.texOffs(0, 57).addBox(0.25F, -5.647F, 2.0084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(0, 61).addBox(0.5F, -5.547F, 4.6584F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.6F))
		.texOffs(0, 55).addBox(-1.25F, -5.147F, 1.0084F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.5F))
		.texOffs(0, 57).addBox(-1.25F, -6.547F, 2.1584F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.25F))
		.texOffs(0, 61).addBox(-3.75F, -5.547F, 4.9084F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.5F))
		.texOffs(0, 57).addBox(-3.0F, -5.397F, 2.0084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(0, 57).addBox(-3.5F, -4.697F, 3.2584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.45F))
		.texOffs(0, 57).addBox(-3.5F, -4.697F, 1.5084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(0, 57).mirror().addBox(-2.75F, -5.397F, 0.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 55).addBox(-1.25F, -4.897F, 0.2584F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.25F))
		.texOffs(0, 57).addBox(0.25F, -5.397F, 0.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 57).addBox(1.0F, -4.697F, 1.5084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 8.0189F, 2.2165F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone2 = upper_body.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(1, 62).addBox(-2.75F, -5.847F, 5.9084F, 2.0F, 1.0F, 1.0F, new CubeDeformation(1.1F))
		.texOffs(1, 62).addBox(0.5F, -5.847F, 6.1584F, 2.0F, 1.0F, 1.0F, new CubeDeformation(1.1F))
		.texOffs(0, 61).addBox(-1.25F, -6.847F, 4.9084F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.85F))
		.texOffs(0, 57).addBox(1.0F, -4.697F, 2.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F))
		.texOffs(0, 57).addBox(0.25F, -5.647F, 2.0084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.75F))
		.texOffs(0, 61).addBox(0.5F, -5.547F, 4.6584F, 1.0F, 1.0F, 2.0F, new CubeDeformation(1.1F))
		.texOffs(0, 55).addBox(-1.25F, -5.147F, 1.0084F, 2.0F, 2.0F, 6.0F, new CubeDeformation(1.0F))
		.texOffs(0, 57).addBox(0.0F, -6.547F, 4.1584F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.75F))
		.texOffs(0, 61).addBox(-3.75F, -5.547F, 4.6584F, 3.0F, 1.0F, 2.0F, new CubeDeformation(1.0F))
		.texOffs(0, 57).addBox(-3.0F, -5.397F, 2.0084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F))
		.texOffs(0, 57).addBox(-3.5F, -4.697F, 3.2584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.95F))
		.texOffs(0, 57).addBox(-3.5F, -4.697F, 1.5084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.6F))
		.texOffs(0, 57).mirror().addBox(-2.75F, -5.397F, 0.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
		.texOffs(0, 55).addBox(-1.25F, -4.897F, 0.2584F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.75F))
		.texOffs(0, 57).addBox(0.25F, -5.397F, 0.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.5F))
		.texOffs(0, 57).addBox(1.0F, -4.697F, 1.5084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(2.25F, 8.5189F, -0.0335F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone3 = upper_body.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(1, 62).mirror().addBox(0.75F, -5.847F, 7.4084F, 2.0F, 1.0F, 1.0F, new CubeDeformation(1.1F)).mirror(false)
		.texOffs(1, 62).mirror().addBox(-2.5F, -5.847F, 6.1584F, 2.0F, 1.0F, 1.0F, new CubeDeformation(1.1F)).mirror(false)
		.texOffs(0, 61).mirror().addBox(-0.75F, -6.847F, 4.9084F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.85F)).mirror(false)
		.texOffs(0, 57).mirror().addBox(-3.0F, -4.697F, 2.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F)).mirror(false)
		.texOffs(0, 57).mirror().addBox(-2.25F, -5.647F, 2.0084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.75F)).mirror(false)
		.texOffs(0, 61).mirror().addBox(-1.5F, -5.547F, 4.6584F, 1.0F, 1.0F, 2.0F, new CubeDeformation(1.1F)).mirror(false)
		.texOffs(0, 55).mirror().addBox(-0.75F, -5.147F, 1.0084F, 2.0F, 2.0F, 6.0F, new CubeDeformation(1.0F)).mirror(false)
		.texOffs(0, 57).mirror().addBox(-1.5F, -6.547F, 5.4084F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.75F)).mirror(false)
		.texOffs(0, 61).mirror().addBox(0.75F, -5.547F, 4.6584F, 3.0F, 1.0F, 2.0F, new CubeDeformation(1.0F)).mirror(false)
		.texOffs(0, 57).mirror().addBox(1.0F, -5.397F, 2.0084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F)).mirror(false)
		.texOffs(0, 57).mirror().addBox(1.5F, -4.697F, 3.2584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(1.15F)).mirror(false)
		.texOffs(0, 57).mirror().addBox(1.5F, -4.697F, 1.5084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.6F)).mirror(false)
		.texOffs(0, 57).addBox(0.75F, -5.397F, 0.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.5F))
		.texOffs(0, 55).mirror().addBox(-0.75F, -4.897F, 0.0084F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.75F)).mirror(false)
		.texOffs(0, 57).mirror().addBox(-2.25F, -5.397F, 0.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
		.texOffs(0, 57).mirror().addBox(-3.0F, -4.697F, 1.5084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offsetAndRotation(-2.25F, 8.5189F, -0.0335F, 1.5708F, -1.5708F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 2.0F));

		PartDefinition body_rotation_r1 = body.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(20, 14).addBox(-3.0F, -12.75F, -2.25F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, 2.0F, 10.0F, 1.5272F, 0.0F, 0.0F));

		PartDefinition bone7 = body.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -5.27F, -10.25F, -0.3491F, 0.0F, 0.0F));

		PartDefinition head_r9 = bone7.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(0, 40).addBox(1.25F, -0.9F, -2.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(48, 54).addBox(-2.5F, -2.15F, -7.25F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(48, 55).addBox(-1.0F, -2.2F, -7.4F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.15F))
		.texOffs(52, 55).addBox(1.0F, -2.15F, -7.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(3, 35).addBox(-2.75F, -2.15F, -3.75F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.05F))
		.texOffs(7, 36).addBox(1.0F, -2.15F, -3.75F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.05F))
		.texOffs(5, 36).addBox(-2.75F, -2.95F, -3.15F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(1, 39).addBox(-3.5F, -2.15F, -2.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 40).addBox(-4.25F, -1.15F, -2.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(9, 39).addBox(-1.5F, -2.6F, 0.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(5, 36).addBox(0.75F, -2.5F, -1.65F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(1, 39).addBox(0.5F, -2.15F, -2.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(5, 36).addBox(0.5F, -2.95F, -3.15F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(5, 36).addBox(-3.25F, -2.5F, -1.65F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(3, 36).addBox(-1.0F, -2.95F, -3.4F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.2F))
		.texOffs(1, 35).addBox(-4.0F, -0.9F, -2.0F, 8.0F, 6.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(7, 38).addBox(2.0F, -1.6F, 0.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, -1.5396F, 14.8582F, 0.3491F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r3 = bone7.addOrReplaceChild("mane_rotation_r3", CubeListBuilder.create().texOffs(0, 36).addBox(-2.75F, -1.5F, -3.1F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.55F))
		.texOffs(0, 36).addBox(0.25F, -0.5F, -2.85F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -1.5396F, 14.8582F, 1.9199F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r4 = bone7.addOrReplaceChild("mane_rotation_r4", CubeListBuilder.create().texOffs(3, 35).addBox(-3.0F, -1.5F, -1.75F, 6.0F, 5.0F, 4.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(0.25F, -1.2539F, 16.5585F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone5 = body.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(46, 59).mirror().addBox(1.5938F, -0.6875F, -0.175F, 3.0F, 1.0F, 2.0F, new CubeDeformation(1.0F)).mirror(false)
		.texOffs(31, 37).mirror().addBox(0.8438F, -0.5375F, -2.825F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F)).mirror(false)
		.texOffs(46, 55).mirror().addBox(3.8409F, -0.6684F, -2.575F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F)).mirror(false)
		.texOffs(33, 37).mirror().addBox(-1.6563F, -0.2875F, -2.075F, 2.0F, 2.0F, 4.0F, new CubeDeformation(1.0F)).mirror(false)
		.texOffs(31, 37).mirror().addBox(-2.4063F, -0.7875F, -2.825F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.65F)).mirror(false)
		.texOffs(47, 60).mirror().addBox(2.8438F, -0.9875F, 1.575F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.85F)).mirror(false)
		.texOffs(32, 42).mirror().addBox(-2.6563F, -0.9875F, 1.075F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.65F)).mirror(false)
		.texOffs(12, 40).mirror().addBox(-1.6563F, -0.6875F, -0.175F, 1.0F, 1.0F, 2.0F, new CubeDeformation(1.1F)).mirror(false), PartPose.offsetAndRotation(-1.8438F, 1.075F, 3.9875F, 1.5708F, 1.5272F, 0.0F));

		PartDefinition bone6 = body.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(46, 59).addBox(-4.5938F, -0.6875F, -0.175F, 3.0F, 1.0F, 2.0F, new CubeDeformation(1.0F))
		.texOffs(31, 37).addBox(-2.8438F, -0.7875F, -2.575F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F))
		.texOffs(46, 55).addBox(-5.8409F, -0.9184F, -2.325F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.75F))
		.texOffs(33, 37).addBox(-0.3438F, -0.2875F, -2.075F, 2.0F, 2.0F, 4.0F, new CubeDeformation(1.0F))
		.texOffs(31, 37).addBox(0.4063F, -0.7875F, -2.825F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.65F))
		.texOffs(47, 60).addBox(-4.5938F, -0.9875F, 1.575F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.85F))
		.texOffs(32, 42).addBox(0.6563F, -0.9875F, 1.075F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.65F))
		.texOffs(31, 41).addBox(0.6563F, -0.6875F, -0.175F, 1.0F, 1.0F, 2.0F, new CubeDeformation(1.1F)), PartPose.offsetAndRotation(1.8438F, 1.075F, 3.9875F, 1.5708F, -1.5272F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(-1.5F, -2.0F, -2.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(-1.5F, 18.0F, -3.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(-0.5F, -2.0F, -2.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(1.5F, 18.0F, -3.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(2.5F, 16.0F, 7.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 13.1F, 7.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(-1.0F, -1.35F, -3.0F));

		PartDefinition tail2 = real_tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(9, 20).addBox(0.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.5F))
		.texOffs(10, 23).addBox(0.0F, 6.2F, 0.7F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(10, 22).addBox(0.0F, 3.3F, -0.1F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(10, 23).addBox(0.0F, 7.4F, 1.3F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.05F))
		.texOffs(9, 23).addBox(0.0F, 8.5F, 1.8F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offset(0.0F, 1.0F, 3.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
