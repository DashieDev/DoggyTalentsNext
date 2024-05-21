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

public class RangaModel extends DogModel{

    public RangaModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, 1.8121F, -0.6155F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(9, 18).addBox(-1.0F, 0.3548F, -0.5221F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.05F))
		.texOffs(9, 18).addBox(-1.0F, 1.4777F, -1.3677F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(47, 25).addBox(-1.0F, 2.8171F, -1.5541F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(47, 25).addBox(-1.0F, 6.0589F, -3.2607F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.05F))
		.texOffs(10, 19).addBox(-1.0F, 4.3089F, -3.0107F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F))
		.texOffs(48, 25).addBox(0.0F, 5.3793F, -2.1333F, 1.0F, 4.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(48, 25).addBox(-0.75F, 5.8793F, -2.1333F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.15F))
		.texOffs(8, 17).addBox(-0.3579F, 3.0143F, -2.4389F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F))
		.texOffs(8, 17).addBox(-0.3579F, 3.4061F, -2.7688F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.4F))
		.texOffs(10, 18).addBox(0.6747F, 3.753F, -2.5454F, 1.0F, 3.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(10, 18).addBox(0.5747F, 5.503F, -2.5454F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(47, 25).addBox(-0.4572F, 5.9456F, -2.7574F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(7, 17).mirror().addBox(-1.7509F, 1.7761F, -1.9157F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(9, 18).addBox(-0.1443F, 1.2291F, -0.8968F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(48, 25).mirror().addBox(-1.5737F, 5.4391F, -2.5533F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(47, 25).mirror().addBox(-1.5377F, 5.8366F, -2.7623F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(10, 18).mirror().addBox(-1.6737F, 3.6891F, -2.3033F, 1.0F, 3.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(9, 17).mirror().addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(-0.6601F, 3.8187F, -0.6316F, 0.0F, 0.0149F, -0.041F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(10, 18).mirror().addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(-0.6204F, 4.8964F, -0.6815F, 0.0F, 0.0149F, -0.041F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(0, 29).mirror().addBox(-1.0F, 2.8F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(0, 29).mirror().addBox(-1.0F, 4.15F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(45, 16).mirror().addBox(-1.0F, 3.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(45, 16).mirror().addBox(-1.0F, 4.7F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 29).mirror().addBox(-1.0F, 5.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition leg4_r1 = right_hind_leg.addOrReplaceChild("leg4_r1", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-1.0F, -1.75F, -1.75F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -0.1439F, -1.1249F, 0.48F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(0, 29).addBox(-1.0F, 2.8F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(0, 29).addBox(-1.0F, 4.15F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(45, 16).addBox(-1.0F, 3.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(45, 16).addBox(-1.0F, 4.7F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-1.0F, 5.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition leg3_r1 = left_hind_leg.addOrReplaceChild("leg3_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -1.75F, -1.75F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.1439F, -1.1249F, 0.48F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 29).mirror().addBox(-1.0F, 5.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(45, 16).mirror().addBox(-1.0F, 4.7F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 29).mirror().addBox(-1.0F, 4.15F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(45, 16).mirror().addBox(-1.0F, 3.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 29).mirror().addBox(-1.0F, 2.8F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(0, 18).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition leg6_r1 = right_front_leg.addOrReplaceChild("leg6_r1", CubeListBuilder.create().texOffs(1, 21).mirror().addBox(-1.0F, -2.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.35F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition leg5_r1 = right_front_leg.addOrReplaceChild("leg5_r1", CubeListBuilder.create().texOffs(1, 21).mirror().addBox(-1.0F, -1.75F, -0.75F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.45F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 29).addBox(-1.0F, 5.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(45, 16).addBox(-1.0F, 4.7F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-1.0F, 4.15F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(45, 16).addBox(-1.0F, 3.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-1.0F, 2.8F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition leg5_r2 = left_front_leg.addOrReplaceChild("leg5_r2", CubeListBuilder.create().texOffs(1, 21).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition leg4_r2 = left_front_leg.addOrReplaceChild("leg4_r2", CubeListBuilder.create().texOffs(1, 21).addBox(-1.0F, -1.75F, -0.75F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.45F)), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(21, 24).addBox(-2.5F, 6.0F, -1.3F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.2F))
		.texOffs(22, 24).addBox(-2.5F, 5.5212F, -3.7857F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition bone = upper_body.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(55, 5).addBox(1.225F, -1.3902F, -4.2118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(55, 5).addBox(1.225F, -1.3902F, -4.9618F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(55, 5).addBox(0.625F, -2.3902F, -4.2118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(55, 5).addBox(0.875F, -2.2902F, -3.2118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(55, 5).addBox(1.125F, -2.3902F, -4.9618F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(56, 12).addBox(1.025F, -2.3902F, -5.7118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(55, 5).addBox(1.375F, -2.3902F, -4.2118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.55F))
		.texOffs(56, 12).addBox(1.125F, -1.6402F, -2.9618F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(55, 5).addBox(0.875F, -1.8902F, -1.7118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(55, 5).addBox(1.125F, -0.1402F, -1.9618F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(56, 12).addBox(1.125F, -0.1402F, -3.7118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.05F))
		.texOffs(55, 5).addBox(1.125F, -0.1402F, -4.9618F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(55, 5).addBox(0.43F, -2.9132F, -4.0467F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(56, 12).addBox(-0.9447F, -2.229F, -6.694F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.05F))
		.texOffs(55, 5).addBox(-1.9728F, -2.2764F, -7.6651F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(55, 5).addBox(-2.4728F, -2.3764F, -7.9151F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(55, 5).mirror().addBox(-4.0272F, -2.3764F, -7.9151F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false)
		.texOffs(55, 5).addBox(-2.9418F, -2.6132F, -4.884F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(55, 5).addBox(-2.5206F, -3.0622F, -7.3528F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(55, 5).addBox(-3.7706F, -2.8122F, -7.3528F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(55, 5).addBox(-2.0153F, -3.549F, -4.9501F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(55, 5).addBox(-2.5258F, -3.6283F, -6.1019F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(55, 5).addBox(-0.7442F, -2.7506F, -4.7675F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.15F))
		.texOffs(55, 5).addBox(0.5282F, -2.2733F, -5.7158F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(55, 5).addBox(-0.897F, 1.2555F, -1.2589F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(56, 12).addBox(0.6513F, 1.31F, -1.6719F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(55, 5).addBox(1.125F, 1.3598F, -3.7118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(55, 5).addBox(1.125F, 1.337F, -2.2223F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(55, 5).addBox(1.125F, 1.3598F, -4.9618F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(55, 5).addBox(0.875F, 0.3598F, -5.7118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.05F))
		.texOffs(56, 12).addBox(0.875F, -1.1402F, -6.2118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(55, 5).addBox(0.675F, -2.1402F, -6.7118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(54, 5).addBox(-3.1122F, 1.3809F, -1.1079F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F))
		.texOffs(56, 12).addBox(-0.897F, 1.7555F, -0.7589F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(54, 5).addBox(-3.1122F, 1.5809F, -0.6079F, 3.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(55, 5).addBox(0.6513F, 2.7771F, -1.8375F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(55, 5).addBox(-0.898F, 2.304F, -1.2373F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.05F))
		.texOffs(56, 12).addBox(-2.1122F, 2.8725F, -1.277F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offset(1.875F, -1.8598F, 4.2118F));

		PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(55, 5).addBox(-1.125F, -2.159F, 0.5339F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, -1.2217F, 0.0F));

		PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(54, 5).addBox(-1.75F, 0.25F, -0.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(54, 5).addBox(-1.75F, -1.0F, -0.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.5658F, -0.125F, 0.7029F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(55, 5).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-0.4569F, -0.4584F, 0.2993F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r4 = bone.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(55, 5).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.7834F, -0.375F, 0.0979F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r5 = bone.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(55, 5).addBox(-1.0F, 0.9943F, -0.1193F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(1.875F, 0.8598F, -5.2118F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r6 = bone.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(55, 5).addBox(-1.0F, 0.9715F, -1.3912F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(2.125F, 1.8598F, -0.9618F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r7 = bone.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(55, 5).addBox(-1.0F, 0.9943F, -0.8693F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(2.125F, 1.8598F, -3.9618F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r8 = bone.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(55, 5).addBox(-1.0F, 0.7443F, -0.8693F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(2.125F, 1.8598F, -2.7118F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r9 = bone.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(56, 12).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(1.6513F, 0.8598F, -0.468F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r10 = bone.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(55, 5).addBox(-1.0F, -1.0F, -0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.4767F, 0.8598F, -0.0405F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r11 = bone.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(55, 5).addBox(-1.175F, -2.2307F, 0.9408F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(-0.2F, -1.5F, -4.6632F, -1.0606F, 0.2748F, -1.3527F));

		PartDefinition cube_r12 = bone.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(55, 5).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(2.125F, 0.8598F, -0.7118F, 0.0F, -0.0344F, 0.0061F));

		PartDefinition cube_r13 = bone.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(55, 5).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(2.125F, 0.8598F, -4.9618F, -0.1745F, 0.0F, 0.0F));

		PartDefinition bone2 = upper_body.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(55, 5).mirror().addBox(-3.225F, -1.3902F, -4.2118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-3.225F, -1.3902F, -4.9618F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-2.625F, -2.3902F, -4.2118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(56, 12).mirror().addBox(-2.875F, -2.2902F, -3.2118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(56, 12).mirror().addBox(-3.125F, -2.3902F, -4.9618F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-3.025F, -2.3902F, -5.7118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-3.375F, -2.3902F, -4.2118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.55F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-3.125F, -1.6402F, -2.9618F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-2.875F, -1.8902F, -1.7118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-3.125F, -0.1402F, -1.9618F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false)
		.texOffs(56, 12).mirror().addBox(-3.125F, -0.1402F, -3.7118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-3.125F, -0.1402F, -4.9618F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-2.43F, -2.9132F, -4.0467F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-1.0553F, -2.179F, -6.694F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-0.0272F, -2.2764F, -7.1651F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(0.7228F, -2.6264F, -8.4151F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(0.9418F, -2.6132F, -4.884F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(0.5206F, -3.0122F, -6.6028F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(0.0153F, -4.049F, -4.9501F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(56, 12).mirror().addBox(0.0258F, -3.3783F, -6.1019F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-1.2558F, -2.7506F, -4.7675F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.15F)).mirror(false)
		.texOffs(56, 12).mirror().addBox(-2.5282F, -2.2733F, -5.7158F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-1.103F, 1.2555F, -1.2589F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false)
		.texOffs(56, 12).mirror().addBox(-1.103F, 1.7555F, -0.7589F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(56, 12).mirror().addBox(-2.6513F, 1.31F, -1.6719F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-3.125F, 1.3598F, -3.7118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-3.125F, 1.337F, -2.2223F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-3.125F, 1.3598F, -4.9618F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-2.875F, 0.3598F, -5.7118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(56, 12).mirror().addBox(-2.875F, -1.1402F, -6.2118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-2.675F, -2.1402F, -6.7118F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-2.6513F, 2.7771F, -1.8375F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(-1.102F, 2.304F, -1.2373F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(55, 5).mirror().addBox(0.1122F, 2.8725F, -1.277F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offset(-1.875F, -1.8598F, 4.2118F));

		PartDefinition cube_r14 = bone2.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(55, 5).mirror().addBox(-0.875F, -2.159F, 0.5339F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 1.2217F, 0.0F));

		PartDefinition cube_r15 = bone2.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(55, 5).mirror().addBox(-1.0F, 0.0F, -0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(0.4569F, -0.4584F, 0.2993F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r16 = bone2.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(55, 5).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(-0.7834F, -0.375F, 0.0979F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r17 = bone2.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(56, 12).mirror().addBox(-1.0F, 0.9943F, -0.1193F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(-1.875F, 0.8598F, -5.2118F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r18 = bone2.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(55, 5).mirror().addBox(-1.0F, 0.9715F, -1.3912F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false), PartPose.offsetAndRotation(-2.125F, 1.8598F, -0.9618F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r19 = bone2.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(55, 5).mirror().addBox(-1.0F, 0.9943F, -0.8693F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false), PartPose.offsetAndRotation(-2.125F, 1.8598F, -3.9618F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r20 = bone2.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(56, 12).mirror().addBox(-1.0F, 0.7443F, -0.8693F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(-2.125F, 1.8598F, -2.7118F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r21 = bone2.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(55, 5).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false), PartPose.offsetAndRotation(-1.6513F, 0.8598F, -0.468F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r22 = bone2.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(55, 5).mirror().addBox(-1.0F, -1.0F, -0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(-0.4767F, 0.8598F, -0.0405F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r23 = bone2.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(55, 5).mirror().addBox(-0.825F, -2.2307F, 0.9408F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false), PartPose.offsetAndRotation(0.2F, -1.5F, -4.6632F, -1.0606F, -0.2748F, 1.3527F));

		PartDefinition cube_r24 = bone2.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(56, 12).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(-2.125F, 0.8598F, -0.7118F, 0.0F, 0.0344F, -0.0061F));

		PartDefinition cube_r25 = bone2.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(55, 5).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.55F)).mirror(false), PartPose.offsetAndRotation(-2.125F, 0.8598F, -4.9618F, -0.1745F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 10.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(1, 3).addBox(-3.5F, -0.5F, -1.9F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(1, 3).mirror().addBox(2.5F, -0.5F, -1.9F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(31, 3).addBox(1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 5.0F, new CubeDeformation(-0.35F))
		.texOffs(31, 3).addBox(-2.75F, -2.0F, 0.0F, 2.0F, 4.0F, 5.0F, new CubeDeformation(-0.25F))
		.texOffs(31, 3).addBox(-1.5F, -2.1F, 0.2F, 2.0F, 4.0F, 5.0F, new CubeDeformation(-0.25F))
		.texOffs(31, 3).addBox(-0.25F, -2.0F, 0.0F, 2.0F, 4.0F, 5.0F, new CubeDeformation(-0.25F))
		.texOffs(31, 3).addBox(-1.85F, -1.75F, 0.75F, 2.0F, 3.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(31, 3).addBox(-2.85F, -1.5F, 0.75F, 2.0F, 3.0F, 5.0F, new CubeDeformation(-0.25F))
		.texOffs(31, 3).addBox(-0.25F, -1.5F, 0.45F, 2.0F, 3.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(31, 3).addBox(1.1F, -1.5F, 0.75F, 2.0F, 3.0F, 5.0F, new CubeDeformation(-0.25F))
		.texOffs(32, 4).addBox(-0.25F, -2.35F, 0.4F, 2.0F, 5.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(32, 4).addBox(-3.5F, -1.95F, 0.85F, 2.0F, 5.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(32, 4).addBox(-1.75F, -2.75F, 0.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(-0.35F))
		.texOffs(32, 4).addBox(-2.75F, -2.5F, 0.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(-0.45F))
		.texOffs(32, 4).addBox(1.15F, -2.5F, 0.2F, 2.0F, 5.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-3.0F, -3.45F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(58, 0).addBox(-2.8F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -1.6795F, -2.2052F, -1.5708F, -1.2654F, 1.5708F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(58, 0).addBox(0.3F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
		.texOffs(58, 0).addBox(-0.55F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
		.texOffs(58, 0).addBox(-1.55F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -1.6795F, -2.2052F, -1.5708F, 1.2654F, -1.5708F));

		PartDefinition head_r3 = real_head.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(58, 0).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -4.962F, -4.9296F, -1.5708F, -0.9599F, 1.5708F));

		PartDefinition head_r4 = real_head.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(58, 0).addBox(1.8F, -1.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
		.texOffs(58, 0).addBox(0.95F, -1.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
		.texOffs(58, 0).addBox(-0.05F, -1.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(58, 0).addBox(-1.3F, -1.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.25F, -2.8F, -1.5708F, 0.9599F, -1.5708F));

		PartDefinition snout = real_head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, -2.0F));

		PartDefinition snout_upper = snout.addOrReplaceChild("snout_upper", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -0.8918F, -2.5119F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, -0.52F, -1.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition snout_lower = snout.addOrReplaceChild("snout_lower", CubeListBuilder.create(), PartPose.offset(0.0F, 0.23F, 0.0F));

		PartDefinition head_r5 = snout_lower.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(0.0F, 0.5F, -0.5F, -3.1416F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-2.0F, -3.0F, 0.5F));

		PartDefinition head_r6 = right_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(59, 19).addBox(-0.1091F, -1.4466F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.2132F, -0.5916F, -0.1771F, -0.2351F, 0.5692F, -0.5927F));

		PartDefinition head_r7 = right_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(59, 19).addBox(-0.2091F, -1.4466F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.9632F, 0.1584F, -0.1771F, -0.2351F, 0.5692F, -0.5927F));

		PartDefinition head_r8 = right_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(59, 19).addBox(-1.0901F, -0.9737F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.2132F, 0.9584F, -0.1771F, -0.0305F, 0.6102F, -0.2278F));

		PartDefinition head_r9 = right_ear.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(59, 19).addBox(-0.9901F, -0.9737F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-1.2132F, -0.5916F, -0.1771F, 0.2351F, 0.5692F, 0.2436F));

		PartDefinition head_r10 = right_ear.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(59, 19).addBox(-0.2091F, -1.4466F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.9632F, 0.1584F, -0.4271F, -0.2351F, 0.5692F, -0.5927F));

		PartDefinition head_r11 = right_ear.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(58, 19).addBox(-1.128F, 0.0315F, -0.2278F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(22, 8).addBox(-1.3998F, 0.531F, -0.2278F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.2132F, -0.5916F, -0.4271F, -0.0305F, 0.6102F, -0.2278F));

		PartDefinition head_r12 = right_ear.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(22, 8).addBox(-1.0901F, -0.9737F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-1.2132F, -0.5916F, -0.4271F, 0.2351F, 0.5692F, 0.2436F));

		PartDefinition head_r13 = right_ear.addOrReplaceChild("head_r13", CubeListBuilder.create().texOffs(10, 14).addBox(-1.0901F, -0.9737F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.2132F, 0.9584F, -0.4271F, -0.0305F, 0.6102F, -0.2278F));

		PartDefinition head_r14 = right_ear.addOrReplaceChild("head_r14", CubeListBuilder.create().texOffs(16, 14).addBox(-0.5F, -0.25F, -0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(16, 14).addBox(-0.5F, -0.75F, -0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.7121F, 2.0582F, -0.1208F, -0.5451F, 0.2909F, -1.3036F));

		PartDefinition head_r15 = right_ear.addOrReplaceChild("head_r15", CubeListBuilder.create().texOffs(16, 14).addBox(-3.4277F, -1.2531F, -1.298F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(16, 14).addBox(-2.9277F, -1.2531F, -1.298F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(16, 14).addBox(-2.4723F, -1.2469F, -1.302F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(16, 14).addBox(-1.5723F, -1.2469F, -1.302F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(16, 14).addBox(-2.0277F, -1.2531F, -1.298F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.5796F, -1.2673F, 0.3576F, -0.6104F, -0.025F, -1.7811F));

		PartDefinition head_r16 = right_ear.addOrReplaceChild("head_r16", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0723F, -0.4969F, -1.202F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(16, 14).addBox(-1.5277F, -0.5031F, -1.198F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(16, 14).addBox(-0.1723F, -0.4969F, -1.202F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(16, 14).addBox(-0.6277F, -0.5031F, -1.198F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.6438F, -1.3339F, 0.3888F, -0.5334F, 0.3133F, -1.2643F));

		PartDefinition head_r17 = right_ear.addOrReplaceChild("head_r17", CubeListBuilder.create().texOffs(16, 14).addBox(-0.1723F, -0.4969F, -0.702F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.8938F, -1.8339F, -0.3612F, -0.2075F, 0.5788F, -0.542F));

		PartDefinition head_r18 = right_ear.addOrReplaceChild("head_r18", CubeListBuilder.create().texOffs(16, 14).addBox(-0.6277F, -0.5031F, -0.698F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.6438F, -1.8339F, -0.3612F, -0.2075F, 0.5788F, -0.542F));

		PartDefinition head_r19 = right_ear.addOrReplaceChild("head_r19", CubeListBuilder.create().texOffs(17, 14).addBox(0.1655F, -1.9487F, -0.834F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.1132F, -0.3416F, -0.4271F, -0.2351F, 0.5692F, -0.5927F));

		PartDefinition head_r20 = right_ear.addOrReplaceChild("head_r20", CubeListBuilder.create().texOffs(17, 14).addBox(0.0896F, -0.7694F, -0.6578F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(59, 19).addBox(-0.2091F, -1.4466F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.2132F, -0.5916F, -0.4271F, -0.2351F, 0.5692F, -0.5927F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(2.0F, -3.0F, 0.5F));

		PartDefinition head_r21 = left_ear.addOrReplaceChild("head_r21", CubeListBuilder.create().texOffs(59, 19).mirror().addBox(-0.8909F, -1.4466F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(1.2132F, -0.5916F, -0.1771F, -0.2351F, -0.5692F, 0.5927F));

		PartDefinition head_r22 = left_ear.addOrReplaceChild("head_r22", CubeListBuilder.create().texOffs(59, 19).mirror().addBox(-0.7909F, -1.4466F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(0.9632F, 0.1584F, -0.1771F, -0.2351F, -0.5692F, 0.5927F));

		PartDefinition head_r23 = left_ear.addOrReplaceChild("head_r23", CubeListBuilder.create().texOffs(59, 19).mirror().addBox(0.0901F, -0.9737F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(1.2132F, 0.9584F, -0.1771F, -0.0305F, -0.6102F, 0.2278F));

		PartDefinition head_r24 = left_ear.addOrReplaceChild("head_r24", CubeListBuilder.create().texOffs(59, 19).mirror().addBox(-0.0099F, -0.9737F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.002F)).mirror(false), PartPose.offsetAndRotation(1.2132F, -0.5916F, -0.1771F, 0.2351F, -0.5692F, -0.2436F));

		PartDefinition head_r25 = left_ear.addOrReplaceChild("head_r25", CubeListBuilder.create().texOffs(59, 19).mirror().addBox(-0.7909F, -1.4466F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(0.9632F, 0.1584F, -0.4271F, -0.2351F, -0.5692F, 0.5927F));

		PartDefinition head_r26 = left_ear.addOrReplaceChild("head_r26", CubeListBuilder.create().texOffs(58, 19).mirror().addBox(-0.872F, 0.0315F, -0.2278F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(22, 8).mirror().addBox(-0.6002F, 0.531F, -0.2278F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.2132F, -0.5916F, -0.4271F, -0.0305F, -0.6102F, 0.2278F));

		PartDefinition head_r27 = left_ear.addOrReplaceChild("head_r27", CubeListBuilder.create().texOffs(22, 8).mirror().addBox(0.0901F, -0.9737F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.002F)).mirror(false), PartPose.offsetAndRotation(1.2132F, -0.5916F, -0.4271F, 0.2351F, -0.5692F, -0.2436F));

		PartDefinition head_r28 = left_ear.addOrReplaceChild("head_r28", CubeListBuilder.create().texOffs(10, 14).mirror().addBox(0.0901F, -0.9737F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(1.2132F, 0.9584F, -0.4271F, -0.0305F, -0.6102F, 0.2278F));

		PartDefinition head_r29 = left_ear.addOrReplaceChild("head_r29", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.5F, -0.25F, -0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(-0.5F, -0.75F, -0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(1.7121F, 2.0582F, -0.1208F, -0.5451F, -0.2909F, 1.3036F));

		PartDefinition head_r30 = left_ear.addOrReplaceChild("head_r30", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(2.4277F, -1.2531F, -1.298F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(1.9277F, -1.2531F, -1.298F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(1.4723F, -1.2469F, -1.302F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(0.5723F, -1.2469F, -1.302F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(1.0277F, -1.2531F, -1.298F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(1.5796F, -1.2673F, 0.3576F, -0.6104F, 0.025F, 1.7811F));

		PartDefinition head_r31 = left_ear.addOrReplaceChild("head_r31", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(0.0723F, -0.4969F, -1.202F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(0.5277F, -0.5031F, -1.198F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(-0.8277F, -0.4969F, -1.202F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(16, 14).mirror().addBox(-0.3723F, -0.5031F, -1.198F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(1.6438F, -1.3339F, 0.3888F, -0.5334F, -0.3133F, 1.2643F));

		PartDefinition head_r32 = left_ear.addOrReplaceChild("head_r32", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.8277F, -0.4969F, -0.702F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(1.8938F, -1.8339F, -0.3612F, -0.2075F, -0.5788F, 0.542F));

		PartDefinition head_r33 = left_ear.addOrReplaceChild("head_r33", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.3723F, -0.5031F, -0.698F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(1.6438F, -1.8339F, -0.3612F, -0.2075F, -0.5788F, 0.542F));

		PartDefinition head_r34 = left_ear.addOrReplaceChild("head_r34", CubeListBuilder.create().texOffs(17, 14).mirror().addBox(-1.1655F, -1.9487F, -0.834F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(1.1132F, -0.3416F, -0.4271F, -0.2351F, -0.5692F, 0.5927F));

		PartDefinition head_r35 = left_ear.addOrReplaceChild("head_r35", CubeListBuilder.create().texOffs(17, 14).mirror().addBox(-1.0896F, -0.7694F, -0.6578F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(59, 19).mirror().addBox(-0.7909F, -1.4466F, -0.2278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(1.2132F, -0.5916F, -0.4271F, -0.2351F, -0.5692F, 0.5927F));

		PartDefinition bone3 = real_head.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(26, 7).addBox(-3.5F, -10.5F, -9.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(26, 7).addBox(-3.0F, -9.5F, -9.2F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(26, 7).addBox(-4.0F, -11.25F, -9.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.5F))
		.texOffs(26, 7).addBox(-3.25F, -12.25F, -9.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.5F)), PartPose.offset(-1.0F, 10.5F, 7.0F));

		PartDefinition bone4 = real_head.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(26, 7).mirror().addBox(-2.5F, -10.5F, -9.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(26, 7).mirror().addBox(-3.0F, -9.5F, -9.2F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(26, 7).mirror().addBox(-2.0F, -11.25F, -9.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(26, 7).mirror().addBox(-2.75F, -12.25F, -9.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offset(1.0F, 10.5F, 7.0F));

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

	@Override
	public boolean warnAccessory(Dog dog, Accessory inst)  {
        return inst.getType() == DoggyAccessoryTypes.HEAD.get();
    }
}
