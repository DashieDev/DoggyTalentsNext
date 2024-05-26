package doggytalents.client.entity.model.dog;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class NorfolkTerrierModel extends DogModel{

    public NorfolkTerrierModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(36, 6).addBox(-1.0F, 1.781F, -0.327F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(30, 19).addBox(-1.0F, 3.3123F, 0.3568F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 11.5F, -6.75F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(27, 7).addBox(-3.0F, 1.5F, -2.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-3.0F, -3.7F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(44, 14).addBox(-0.525F, -1.65F, -1.375F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.775F, -0.75F, -0.525F, 0.0F, 0.0F, 0.4363F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(44, 14).addBox(-0.675F, -1.9F, -1.125F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.775F, -1.5F, -0.475F, 0.0F, 0.0F, 0.4363F));

		PartDefinition head_r3 = real_head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(44, 14).mirror().addBox(-0.475F, -0.65F, -0.375F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(2.525F, 3.3F, -1.925F, 0.0F, 0.0F, 2.9234F));

		PartDefinition head_r4 = real_head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(44, 14).addBox(-0.525F, -0.65F, -0.375F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.525F, 3.3F, -1.925F, 0.0F, 0.0F, -2.9234F));

		PartDefinition head_r5 = real_head.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(44, 14).addBox(-0.525F, -0.65F, -0.375F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.025F, 2.55F, -1.925F, 0.0F, 0.0F, -2.7489F));

		PartDefinition head_r6 = real_head.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(44, 14).addBox(-0.675F, -0.9F, -0.125F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-3.275F, 1.45F, -1.975F, 0.0F, 0.0F, -2.7489F));

		PartDefinition head_r7 = real_head.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(44, 14).mirror().addBox(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(2.8211F, -1.4338F, -1.0F, 0.0F, 0.0F, 2.8362F));

		PartDefinition head_r8 = real_head.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(44, 14).mirror().addBox(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.2617F, -2.1756F, -1.0F, 0.0F, 0.0F, 1.7453F));

		PartDefinition head_r9 = real_head.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(44, 14).mirror().addBox(-0.5F, -1.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(0.1117F, -1.0756F, -1.0F, 0.0F, 0.0F, 0.5672F));

		PartDefinition head_r10 = real_head.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(25, 14).mirror().addBox(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(1.1117F, -2.1756F, -1.0F, 0.0F, 0.0F, 1.0036F));

		PartDefinition head_r11 = real_head.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(44, 14).addBox(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-2.8211F, -1.4338F, -1.0F, 0.0F, 0.0F, -2.8362F));

		PartDefinition head_r12 = real_head.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(44, 14).addBox(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2617F, -2.1756F, -1.0F, 0.0F, 0.0F, -1.7453F));

		PartDefinition head_r13 = real_head.addOrReplaceChild("head_r13", CubeListBuilder.create().texOffs(44, 14).addBox(-0.5F, -1.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.1117F, -1.0756F, -1.0F, 0.0F, 0.0F, -0.5672F));

		PartDefinition head_r14 = real_head.addOrReplaceChild("head_r14", CubeListBuilder.create().texOffs(25, 14).addBox(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.1117F, -2.1756F, -1.0F, 0.0F, 0.0F, -1.0036F));

		PartDefinition head_r15 = real_head.addOrReplaceChild("head_r15", CubeListBuilder.create().texOffs(44, 14).addBox(0.975F, 0.1F, -1.125F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.025F, -1.25F, -1.125F, 0.0F, 0.0F, -1.5272F));

		PartDefinition head_r16 = real_head.addOrReplaceChild("head_r16", CubeListBuilder.create().texOffs(46, 16).addBox(0.975F, 0.1F, -1.125F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.525F, -1.75F, -1.125F, 0.0F, 0.0F, -1.4835F));

		PartDefinition head_r17 = real_head.addOrReplaceChild("head_r17", CubeListBuilder.create().texOffs(44, 14).mirror().addBox(-1.975F, 0.1F, -1.125F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.025F, -1.25F, -1.125F, 0.0F, 0.0F, 1.5272F));

		PartDefinition head_r18 = real_head.addOrReplaceChild("head_r18", CubeListBuilder.create().texOffs(46, 16).mirror().addBox(-1.975F, 0.1F, -1.125F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(2.525F, -1.75F, -1.125F, 0.0F, 0.0F, 1.4835F));

		PartDefinition head_r19 = real_head.addOrReplaceChild("head_r19", CubeListBuilder.create().texOffs(44, 14).mirror().addBox(-0.475F, -0.65F, -0.375F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.025F, 2.55F, -1.925F, 0.0F, 0.0F, 2.7489F));

		PartDefinition head_r20 = real_head.addOrReplaceChild("head_r20", CubeListBuilder.create().texOffs(44, 14).mirror().addBox(-0.325F, -0.9F, -0.125F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(3.275F, 1.45F, -1.975F, 0.0F, 0.0F, 2.7489F));

		PartDefinition snout = real_head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.5F, -1.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition snout_upper = snout.addOrReplaceChild("snout_upper", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.52F, 0.0F));

		PartDefinition bone3 = snout_upper.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(-1.75F, 1.0F, -1.0F));

		PartDefinition head_r21 = bone3.addOrReplaceChild("head_r21", CubeListBuilder.create().texOffs(1, 11).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.05F, 0.0F, 0.1F, 0.0F, 0.0F, -0.0436F));

		PartDefinition head_r22 = bone3.addOrReplaceChild("head_r22", CubeListBuilder.create().texOffs(1, 11).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-0.25F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition head_r23 = bone3.addOrReplaceChild("head_r23", CubeListBuilder.create().texOffs(22, 8).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(-0.25F, -0.5F, 0.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition head_r24 = bone3.addOrReplaceChild("head_r24", CubeListBuilder.create().texOffs(22, 8).addBox(-1.0F, -0.5F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(1.2575F, -2.0406F, 0.4F, 0.0F, 0.0F, 0.2182F));

		PartDefinition head_r25 = bone3.addOrReplaceChild("head_r25", CubeListBuilder.create().texOffs(22, 8).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.25F, -1.25F, 0.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition bone4 = snout_upper.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(1.75F, 1.0F, -1.0F));

		PartDefinition head_r26 = bone4.addOrReplaceChild("head_r26", CubeListBuilder.create().texOffs(1, 11).mirror().addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.25F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition head_r27 = bone4.addOrReplaceChild("head_r27", CubeListBuilder.create().texOffs(22, 8).mirror().addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(0.25F, -0.5F, 0.0F, 0.0F, 0.0F, -0.6981F));

		PartDefinition head_r28 = bone4.addOrReplaceChild("head_r28", CubeListBuilder.create().texOffs(22, 8).mirror().addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(-0.05F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0436F));

		PartDefinition head_r29 = bone4.addOrReplaceChild("head_r29", CubeListBuilder.create().texOffs(22, 8).mirror().addBox(-1.0F, -0.5F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-1.2575F, -2.0406F, 0.4F, 0.0F, 0.0F, -0.1309F));

		PartDefinition head_r30 = bone4.addOrReplaceChild("head_r30", CubeListBuilder.create().texOffs(22, 8).mirror().addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.25F, -1.25F, 0.0F, 0.0F, 0.0F, -0.48F));

		PartDefinition snout_lower = snout.addOrReplaceChild("snout_lower", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -0.75F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.98F, 0.0F));

		PartDefinition head_r31 = snout_lower.addOrReplaceChild("head_r31", CubeListBuilder.create().texOffs(27, 8).mirror().addBox(-0.5076F, -0.4571F, -1.5019F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition head_r32 = snout_lower.addOrReplaceChild("head_r32", CubeListBuilder.create().texOffs(27, 8).mirror().addBox(-0.4943F, -0.4568F, -1.5019F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.1309F));

		PartDefinition head_r33 = snout_lower.addOrReplaceChild("head_r33", CubeListBuilder.create().texOffs(27, 8).addBox(-0.5113F, -0.4579F, -1.5019F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -1.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.25F, -3.0F, 0.5F, -0.0172F, 0.1298F, -0.132F));

		PartDefinition head_r34 = right_ear.addOrReplaceChild("head_r34", CubeListBuilder.create().texOffs(16, 14).addBox(-0.9985F, -0.1251F, -0.8446F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-0.8401F, 0.4395F, -0.9189F, -1.0297F, 1.0472F, -0.2531F));

		PartDefinition head_r35 = right_ear.addOrReplaceChild("head_r35", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0015F, -1.181F, -0.8493F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.8401F, 0.4395F, -0.9189F, -0.7854F, 1.0472F, -0.2531F));

		PartDefinition head_r36 = right_ear.addOrReplaceChild("head_r36", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0015F, -1.0057F, 0.4357F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8401F, 0.4395F, -0.9189F, 0.3927F, 1.0472F, -0.2531F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(2.25F, -3.0F, 0.5F, -0.0172F, -0.1298F, 0.132F));

		PartDefinition head_r37 = left_ear.addOrReplaceChild("head_r37", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.0015F, -0.1251F, -0.8446F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(0.8401F, 0.4395F, -0.9189F, -1.0297F, -1.0472F, 0.2531F));

		PartDefinition head_r38 = left_ear.addOrReplaceChild("head_r38", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.9985F, -1.181F, -0.8493F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.8401F, 0.4395F, -0.9189F, -0.7854F, -1.0472F, 0.2531F));

		PartDefinition head_r39 = left_ear.addOrReplaceChild("head_r39", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.9985F, -1.0057F, 0.4357F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.8401F, 0.4395F, -0.9189F, 0.3927F, -1.0472F, 0.2531F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
    @Override
    public boolean useDefaultModelForAccessories() {
        return true;
    }

    @Override
    public boolean acessoryShouldRender(Dog dog, AccessoryInstance inst) {
        return true;
    }
}
