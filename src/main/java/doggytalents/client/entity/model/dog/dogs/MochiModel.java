package doggytalents.client.entity.model.dog.dogs;

import doggytalents.client.entity.model.dog.DogModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class MochiModel extends DogModel {

    public MochiModel(ModelPart box) {
        super(box);
    }
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 15.25F, 5.75F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(18, 20).addBox(-1.0F, -0.0905F, -1.3881F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0218F, 0.4995F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(24, 24).addBox(-1.0F, -1.75F, -1.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.9095F, 2.1619F, -2.0944F, 0.0F, 0.0F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(24, 23).addBox(-1.0F, -2.25F, -1.25F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 4.1595F, 0.8619F, 0.6981F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 1.25F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 17.75F, 5.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 1.25F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 17.75F, 5.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, 2.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offset(-0.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 2.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offset(0.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -1.0F, -3.5F, 6.0F, 8.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 16.25F, -0.25F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -2.0F, -3.5F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(0.0F, 16.75F, -4.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone8 = upper_body.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, -7.25F, -1.5708F, 0.0F, 0.0F));

		PartDefinition bone = bone8.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(34, 10).addBox(-2.75F, -18.847F, 6.1584F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(34, 10).addBox(0.5F, -18.847F, 6.1584F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(33, 9).addBox(-1.25F, -19.847F, 5.6584F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(33, 5).addBox(1.0F, -17.697F, 2.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(33, 5).addBox(0.0F, -18.647F, 3.0084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(33, 9).addBox(0.5F, -18.547F, 4.6584F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(33, 5).addBox(-1.25F, -19.547F, 3.1584F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.25F))
		.texOffs(33, 9).addBox(-3.75F, -18.547F, 4.9084F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(33, 5).addBox(-3.0F, -18.397F, 2.0084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(33, 5).addBox(-3.5F, -17.697F, 3.2584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.05F))
		.texOffs(33, 5).addBox(-3.5F, -17.697F, 1.5084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F))
		.texOffs(33, 5).mirror().addBox(-2.75F, -18.397F, 0.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(33, 5).addBox(0.25F, -18.397F, 0.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.5F))
		.texOffs(33, 5).addBox(1.0F, -17.697F, 1.5084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -2.403F, 11.5916F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone2 = bone8.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(34, 10).addBox(13.5F, -5.847F, 6.1584F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.6F))
		.texOffs(29, 2).addBox(14.0F, -4.447F, 3.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(33, 9).addBox(13.5F, -5.547F, 4.6584F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.6F))
		.texOffs(33, 5).addBox(14.0F, -3.947F, 2.0084F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(2.25F, -1.903F, 9.3416F, 1.5708F, 1.5708F, 0.0F));

		PartDefinition bone3 = bone8.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(34, 10).mirror().addBox(-15.5F, -5.847F, 6.1584F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.6F)).mirror(false)
		.texOffs(31, 2).mirror().addBox(-16.0F, -4.697F, 2.7584F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(33, 9).mirror().addBox(-14.5F, -5.547F, 4.6584F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offsetAndRotation(-2.25F, -1.903F, 9.3416F, 1.5708F, -1.5708F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 14.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-1.5F, -0.02F, -4.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.2F))
		.texOffs(11, 11).addBox(-0.5F, -0.12F, -4.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 0).mirror().addBox(-2.5F, -3.9F, -2.25F, 5.0F, 2.0F, 4.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(44, 14).mirror().addBox(-0.475F, -1.65F, -1.375F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(3.025F, -1.0F, -1.125F, 0.0F, 0.0F, -0.3054F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(44, 14).mirror().addBox(-1.975F, 0.1F, -1.125F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.025F, -1.0F, -1.125F, 0.0F, 0.0F, 1.5272F));

		PartDefinition head_r3 = real_head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(46, 16).mirror().addBox(-1.975F, 0.1F, -1.125F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.675F, -1.2F, -0.475F, 0.0F, 0.0F, 1.7017F));

		PartDefinition head_r4 = real_head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(44, 14).mirror().addBox(-1.975F, 0.1F, -0.375F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.025F, 3.0F, -2.125F, 0.0F, 0.0F, 2.7489F));

		PartDefinition head_r5 = real_head.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(3, 4).mirror().addBox(-0.1856F, -2.1766F, -1.25F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(1.1856F, 0.6766F, -2.25F, 0.0F, 0.0F, -1.5708F));

		PartDefinition head_r6 = real_head.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(44, 14).mirror().addBox(-0.65F, -1.2F, -1.75F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(1.1856F, 0.6766F, -1.75F, 0.0F, 0.0F, 2.3998F));

		PartDefinition head_r7 = real_head.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(44, 14).mirror().addBox(-1.225F, -0.4F, -0.375F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.025F, 2.45F, -2.425F, 0.0F, 0.0F, 2.9234F));

		PartDefinition head_r8 = real_head.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(44, 14).addBox(0.225F, -0.4F, -0.375F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.025F, 2.45F, -2.425F, 0.0F, 0.0F, -2.9234F));

		PartDefinition head_r9 = real_head.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(44, 14).addBox(0.975F, 0.1F, -0.375F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.025F, 3.0F, -2.125F, 0.0F, 0.0F, -2.7489F));

		PartDefinition head_r10 = real_head.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(44, 14).addBox(-0.35F, -1.2F, -1.75F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.1856F, 0.6766F, -1.75F, 0.0F, 0.0F, -2.3998F));

		PartDefinition head_r11 = real_head.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(44, 14).addBox(0.975F, 0.1F, -1.125F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.025F, -1.0F, -1.125F, 0.0F, 0.0F, -1.5272F));

		PartDefinition head_r12 = real_head.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(44, 14).addBox(-0.525F, -1.65F, -1.375F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-3.025F, -1.0F, -1.125F, 0.0F, 0.0F, 0.3054F));

		PartDefinition head_r13 = real_head.addOrReplaceChild("head_r13", CubeListBuilder.create().texOffs(45, 15).addBox(0.975F, 0.1F, -1.125F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.675F, -1.2F, -0.475F, 0.0F, 0.0F, -1.7017F));

		PartDefinition head_r14 = real_head.addOrReplaceChild("head_r14", CubeListBuilder.create().texOffs(8, 19).addBox(0.5F, -0.5F, -1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.5F, 2.68F, -3.65F, 0.4363F, 0.0F, 0.0F));

		PartDefinition head_r15 = real_head.addOrReplaceChild("head_r15", CubeListBuilder.create().texOffs(44, 24).addBox(-0.5F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.0F, 2.18F, -1.9F, 0.3054F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-3.0F, -2.0F, 0.0F));

		PartDefinition head_r16 = right_ear.addOrReplaceChild("head_r16", CubeListBuilder.create().texOffs(44, 14).addBox(-1.275F, 0.0F, -1.375F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0431F, 1.2807F, -0.375F, 0.0F, 0.0F, -0.6109F));

		PartDefinition head_r17 = right_ear.addOrReplaceChild("head_r17", CubeListBuilder.create().texOffs(44, 14).addBox(-1.275F, 0.0F, -1.375F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.725F, -0.75F, -0.625F, 0.0F, 0.0F, 0.2182F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(3.0F, -2.0F, 0.0F));

		PartDefinition head_r18 = left_ear.addOrReplaceChild("head_r18", CubeListBuilder.create().texOffs(44, 14).mirror().addBox(0.275F, 0.0F, -1.375F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0431F, 1.2807F, -0.375F, 0.0F, 0.0F, 0.6109F));

		PartDefinition head_r19 = left_ear.addOrReplaceChild("head_r19", CubeListBuilder.create().texOffs(44, 14).mirror().addBox(0.275F, 0.0F, -1.375F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.725F, -0.75F, -0.625F, 0.0F, 0.0F, -0.2182F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
