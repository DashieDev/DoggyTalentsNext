package doggytalents.client.entity.model.dog;

import doggytalents.api.anim.AltDogAnimationSequences;
import doggytalents.api.anim.DogAnimation;
import doggytalents.api.enu.forward_imitate.anim.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SamoyedModel extends DogModel{

    public SamoyedModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 8.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(0, 0).addBox(-3.0F, -3.55F, -2.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(48, 11).addBox(-1.5F, 0.4F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 1.48F, -2.5F, 0.8465F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, 0.5F, -1.5F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 1.48F, -2.5F, 0.7854F, 0.0F, 0.0F));

		PartDefinition head_r3 = real_head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.5F, -1.75F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0F, 1.48F, -2.5F, 0.2182F, 0.0F, 0.0F));

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
		.texOffs(19, 54).addBox(-2.75F, -2.98F, 0.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(18, 59).addBox(1.25F, -1.73F, 1.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.23F, -1.25F, -0.3491F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = bone4.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(18, 55).addBox(-2.75F, -6.75F, 11.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.55F))
		.texOffs(19, 54).addBox(-2.75F, -6.25F, 12.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(-0.45F))
		.texOffs(18, 55).addBox(0.25F, -5.75F, 12.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 14.02F, 9.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition headfur = real_head.addOrReplaceChild("headfur", CubeListBuilder.create().texOffs(29, 24).mirror().addBox(-4.0F, -13.8F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(29, 24).mirror().addBox(-1.6F, -13.55F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.4F)).mirror(false)
		.texOffs(28, 23).mirror().addBox(-4.75F, -14.8F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(29, 24).mirror().addBox(-5.25F, -15.3F, -8.35F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(31, 23).mirror().addBox(-4.75F, -16.55F, -8.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(31, 21).mirror().addBox(-3.75F, -17.55F, -8.65F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(28, 23).mirror().addBox(-4.5F, -17.3F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offset(0.0F, 15.5F, 7.3F));

		PartDefinition head_r4 = headfur.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(29, 24).mirror().addBox(-4.0F, -0.625F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.4F)).mirror(false)
		.texOffs(29, 24).mirror().addBox(-1.6F, -0.375F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offsetAndRotation(0.0F, -11.675F, -6.25F, -0.0873F, 0.0F, 0.0F));

		PartDefinition headfur2 = real_head.addOrReplaceChild("headfur2", CubeListBuilder.create().texOffs(29, 24).addBox(1.0F, -13.8F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(28, 23).addBox(1.75F, -14.8F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(29, 24).addBox(4.25F, -15.3F, -8.35F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(31, 23).addBox(2.75F, -16.55F, -8.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(31, 21).addBox(2.75F, -17.55F, -8.65F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(28, 23).addBox(1.5F, -17.3F, -8.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 15.5F, 7.3F));

		PartDefinition head_r5 = headfur2.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(29, 24).addBox(1.0F, -0.625F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, -11.675F, -6.25F, -0.0873F, 0.0F, 0.0F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, -3.0F, 0.5F, 0.0F, 0.0F, 0.1745F));

		PartDefinition head_r6 = left_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(45, 0).mirror().addBox(1.4988F, -0.6934F, -0.9275F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(45, 0).mirror().addBox(1.3498F, -0.228F, -0.9429F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(45, 0).mirror().addBox(0.5998F, -0.228F, -0.9429F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(52, 0).mirror().addBox(0.8498F, -0.928F, -0.9429F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.3F)).mirror(false)
		.texOffs(45, 0).mirror().addBox(0.8498F, -0.728F, -0.9429F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(0.2905F, 0.1687F, -1.625F, -0.7854F, -1.4835F, -2.1817F));

		PartDefinition head_r7 = left_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(45, 0).mirror().addBox(0.25F, -1.75F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(45, 0).mirror().addBox(-0.5F, -1.75F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.8149F, 0.4545F, -0.3552F, -0.7854F, -1.4835F, -3.098F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, -3.0F, 0.5F, 0.0F, 0.0F, -0.1745F));

		PartDefinition head_r8 = right_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(45, 0).addBox(-2.4988F, -0.6934F, -0.9275F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(45, 0).addBox(-2.3498F, -0.228F, -0.9429F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(45, 0).addBox(-1.5998F, -0.228F, -0.9429F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(52, 0).addBox(-1.8498F, -0.928F, -0.9429F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.3F))
		.texOffs(45, 0).addBox(-1.8498F, -0.728F, -0.9429F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-0.2905F, 0.1687F, -1.625F, -0.7854F, 1.4835F, 2.1817F));

		PartDefinition head_r9 = right_ear.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(45, 0).addBox(-1.25F, -1.75F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(45, 0).addBox(-0.5F, -1.75F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8149F, 0.4545F, -0.3552F, -0.7854F, 1.4835F, 3.098F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, -5.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone10 = upper_body.addOrReplaceChild("bone10", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.0781F, 3.1251F, -1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r2 = bone10.addOrReplaceChild("mane_rotation_r2", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -1.8941F, -2.3884F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 3.7689F, -0.2835F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone8 = bone10.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offset(0.0F, 3.7689F, -0.2835F));

		PartDefinition bone = bone8.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(1, 62).addBox(-2.75F, -5.847F, 6.1584F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.6F))
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
		.texOffs(0, 57).addBox(1.0F, -4.697F, 1.5084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 4.25F, 2.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone2 = bone8.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(1, 62).addBox(-2.75F, -5.847F, 5.9084F, 2.0F, 1.0F, 1.0F, new CubeDeformation(1.1F))
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
		.texOffs(0, 57).addBox(0.25F, -5.397F, 0.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.5F))
		.texOffs(0, 57).addBox(1.0F, -4.697F, 1.5084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(2.25F, 4.75F, 0.25F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone3 = bone8.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(1, 62).mirror().addBox(0.75F, -5.847F, 7.4084F, 2.0F, 1.0F, 1.0F, new CubeDeformation(1.1F)).mirror(false)
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
		.texOffs(0, 57).mirror().addBox(-2.25F, -5.397F, 0.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
		.texOffs(0, 57).mirror().addBox(-3.0F, -4.697F, 1.5084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offsetAndRotation(-2.25F, 4.75F, 0.25F, 1.5708F, -1.5708F, 0.0F));

		PartDefinition bone12 = bone10.addOrReplaceChild("bone12", CubeListBuilder.create(), PartPose.offsetAndRotation(0.1667F, -0.396F, 3.0867F, -0.6545F, 0.0F, 0.0F));

		PartDefinition head_r10 = bone12.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(48, 55).addBox(-1.0F, -1.45F, -7.4F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.95F))
		.texOffs(52, 55).addBox(1.0F, -1.4F, -7.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.7F))
		.texOffs(48, 54).addBox(-2.5F, -1.4F, -7.25F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.7F))
		.texOffs(48, 54).addBox(-2.5F, -2.65F, -7.25F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.2F))
		.texOffs(52, 55).addBox(1.0F, -2.65F, -7.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-0.1667F, 1.0417F, 4.8833F, 0.0F, 0.0F, 0.0F));

		PartDefinition head_r11 = bone12.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(48, 55).addBox(-1.0F, -3.45F, -7.4F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.45F)), PartPose.offsetAndRotation(-0.1667F, 1.0417F, 4.8833F, 0.0436F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone11 = body.addOrReplaceChild("bone11", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = bone11.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(20, 14).addBox(-3.0F, -12.75F, -2.25F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, 2.0F, 10.0F, 1.5272F, 0.0F, 0.0F));

		PartDefinition bone9 = bone11.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 10.0F));

		PartDefinition bone7 = bone9.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -7.27F, -20.25F, -0.3491F, 0.0F, 0.0F));

		PartDefinition head_r12 = bone7.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(0, 40).addBox(1.25F, -0.9F, -2.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
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

		PartDefinition bone5 = bone9.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(46, 59).mirror().addBox(1.5938F, -0.6875F, -0.175F, 3.0F, 1.0F, 2.0F, new CubeDeformation(1.0F)).mirror(false)
		.texOffs(31, 37).mirror().addBox(0.8438F, -0.5375F, -2.825F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F)).mirror(false)
		.texOffs(46, 55).mirror().addBox(3.8409F, -0.6684F, -2.575F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F)).mirror(false)
		.texOffs(33, 37).mirror().addBox(-1.6563F, -0.2875F, -2.075F, 2.0F, 2.0F, 4.0F, new CubeDeformation(1.0F)).mirror(false)
		.texOffs(31, 37).mirror().addBox(-2.4063F, -1.0375F, -2.825F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.75F)).mirror(false)
		.texOffs(47, 60).mirror().addBox(2.8438F, -0.9875F, 1.575F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.85F)).mirror(false)
		.texOffs(32, 42).mirror().addBox(-2.6563F, -0.9875F, 1.075F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.65F)).mirror(false)
		.texOffs(12, 40).mirror().addBox(-1.6563F, -0.6875F, -0.175F, 1.0F, 1.0F, 2.0F, new CubeDeformation(1.1F)).mirror(false), PartPose.offsetAndRotation(-1.8438F, -0.925F, -6.0125F, 1.5708F, 1.5272F, 0.0F));

		PartDefinition bone6 = bone9.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(46, 59).addBox(-4.5938F, -0.6875F, -0.175F, 3.0F, 1.0F, 2.0F, new CubeDeformation(1.0F))
		.texOffs(31, 37).addBox(-2.8438F, -0.7875F, -2.575F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.85F))
		.texOffs(46, 55).addBox(-5.8409F, -0.9184F, -2.325F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.75F))
		.texOffs(33, 37).addBox(-0.3438F, -0.2875F, -2.075F, 2.0F, 2.0F, 4.0F, new CubeDeformation(1.0F))
		.texOffs(31, 37).addBox(0.4063F, -1.0375F, -2.825F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.75F))
		.texOffs(47, 60).addBox(-4.5938F, -0.9875F, 1.575F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.85F))
		.texOffs(32, 42).addBox(0.6563F, -0.9875F, 1.075F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.65F))
		.texOffs(31, 41).addBox(0.6563F, -0.6875F, -0.175F, 1.0F, 1.0F, 2.0F, new CubeDeformation(1.1F)), PartPose.offsetAndRotation(1.8438F, -0.925F, -6.0125F, 1.5708F, -1.5272F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(-1.0F, -2.1F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(44, 18).addBox(-1.0F, -2.0F, -0.75F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.7F)), PartPose.offset(-2.0F, 18.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(44, 18).addBox(-1.0F, -2.1F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(44, 18).addBox(-1.0F, -2.0F, -0.75F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.7F)), PartPose.offset(2.0F, 18.0F, -4.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(0, 18).addBox(-0.65F, -1.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.65F)), PartPose.offset(1.5F, 17.5F, 7.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(0, 18).addBox(-1.45F, -1.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.65F)), PartPose.offset(-1.5F, 17.5F, 7.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.1F, 6.5F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(9, 20).mirror().addBox(-1.3183F, -5.1182F, -2.6866F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.8F)).mirror(false), PartPose.offsetAndRotation(0.25F, 4.0726F, 3.1183F, 0.6533F, 0.0F, -0.0494F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(9, 20).addBox(0.1544F, -5.3804F, -2.2143F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(0.25F, 4.0726F, 3.1183F, 0.8708F, 0.0643F, -0.059F));

		PartDefinition tail_r3 = real_tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(9, 23).mirror().addBox(-2.4957F, -1.7528F, -1.3899F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(0.25F, 1.2443F, 4.3844F, -0.0364F, 0.1427F, 0.1008F));

		PartDefinition tail_r4 = real_tail.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(10, 23).mirror().addBox(-2.2674F, 0.6783F, 0.4851F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.25F, 4.0726F, 3.1183F, 0.0859F, -0.0151F, 0.1739F));

		PartDefinition tail_r5 = real_tail.addOrReplaceChild("tail_r5", CubeListBuilder.create().texOffs(10, 22).mirror().addBox(-2.2674F, -1.051F, -2.6533F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.25F, 4.0726F, 3.1183F, 0.915F, -0.0151F, 0.1739F));

		PartDefinition tail_r6 = real_tail.addOrReplaceChild("tail_r6", CubeListBuilder.create().texOffs(10, 23).mirror().addBox(-2.2674F, -0.6982F, 0.5254F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(0.25F, 4.0726F, 3.1183F, 0.5223F, -0.0151F, 0.1739F));

		PartDefinition tail_r7 = real_tail.addOrReplaceChild("tail_r7", CubeListBuilder.create().texOffs(9, 23).addBox(0.4957F, -1.7528F, -1.3899F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.25F, 1.2443F, 4.3844F, -0.0364F, -0.1427F, -0.1008F));

		PartDefinition tail_r8 = real_tail.addOrReplaceChild("tail_r8", CubeListBuilder.create().texOffs(10, 23).addBox(0.2674F, 0.6783F, 0.4851F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 4.0726F, 3.1183F, 0.0859F, 0.0151F, -0.1739F));

		PartDefinition tail_r9 = real_tail.addOrReplaceChild("tail_r9", CubeListBuilder.create().texOffs(10, 22).addBox(-0.225F, -1.1096F, -2.5893F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.25F, 4.0726F, 3.1183F, 0.915F, 0.0151F, -0.1739F));

		PartDefinition tail_r10 = real_tail.addOrReplaceChild("tail_r10", CubeListBuilder.create().texOffs(10, 23).addBox(0.2674F, -0.6982F, 0.5254F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.25F, 4.0726F, 3.1183F, 0.5223F, 0.0151F, -0.1739F));

		PartDefinition tail_r11 = real_tail.addOrReplaceChild("tail_r11", CubeListBuilder.create().texOffs(10, 23).addBox(-1.25F, 0.4566F, 0.953F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.25F, 4.0726F, 3.1183F, 0.3927F, 0.0F, 0.0F));

		PartDefinition tail_r12 = real_tail.addOrReplaceChild("tail_r12", CubeListBuilder.create().texOffs(10, 22).addBox(-1.25F, -0.8558F, -2.1737F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.25F, 4.0726F, 3.1183F, 1.2217F, 0.0F, 0.0F));

		PartDefinition tail_r13 = real_tail.addOrReplaceChild("tail_r13", CubeListBuilder.create().texOffs(10, 23).addBox(-1.25F, -1.2013F, 2.0431F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.25F, 4.0726F, 3.1183F, 0.0436F, 0.0F, 0.0F));

		PartDefinition tail_r14 = real_tail.addOrReplaceChild("tail_r14", CubeListBuilder.create().texOffs(9, 22).addBox(-1.25F, -1.552F, 1.0495F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.25F, 1.2443F, 4.3844F, 0.2618F, 0.0F, 0.0F));

		PartDefinition tail_r15 = real_tail.addOrReplaceChild("tail_r15", CubeListBuilder.create().texOffs(9, 22).addBox(-1.25F, -3.052F, -0.4505F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.25F, 1.2443F, 4.3844F, 0.1309F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	protected AnimationDefinition getAnimationSequence(DogAnimation anim) {
		if (anim == DogAnimation.HOWL) {
			return AltDogAnimationSequences.VICTORY_HOWL_ALT;
		}
		return super.getAnimationSequence(anim);
	}

}
