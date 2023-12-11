package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class GermanPointerWirehaired extends DogModel{

    public GermanPointerWirehaired(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 12.5F, 7.75F, 1.6144F, 0.0F, 0.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -3.0F, -3.25F, 6.0F, 10.0F, 6.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -2.0F, -3.5F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(0.0F, 14.0F, -4.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 12.25F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(-0.35F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.5F, -2.25F, 5.0F, 4.0F, 4.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(0.5F, 1.0F, 2.0F, -0.6545F, 0.0F, 0.0F));

		PartDefinition snout = real_head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, -2.0F));

		PartDefinition snout_upper = snout.addOrReplaceChild("snout_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, 9.0F));

		PartDefinition head_r2 = snout_upper.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(2, 12).mirror().addBox(1.0F, -0.5F, -0.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(2, 12).mirror().addBox(1.0F, -0.5F, -1.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(2, 12).mirror().addBox(1.0F, -0.75F, -2.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(2, 12).addBox(-2.0F, -0.75F, -2.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(2, 12).addBox(-2.0F, -0.5F, -1.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(2, 12).addBox(-2.0F, -0.5F, -0.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 10).addBox(-1.5F, -1.0F, -2.25F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -10.02F, -9.5F, 0.0873F, 0.0F, 0.0F));

		PartDefinition snout_lower = snout.addOrReplaceChild("snout_lower", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -9.27F, -11.5F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 9.0F, 9.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(17, 13).addBox(-1.55F, -0.5F, -1.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(17, 13).addBox(-2.05F, 0.25F, -1.25F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(17, 13).addBox(-1.3F, -1.15F, -1.25F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(17, 13).addBox(-0.95F, -1.4F, -1.25F, 1.0F, 3.0F, 2.0F, new CubeDeformation(-0.01F))
		.texOffs(16, 16).addBox(-0.75F, -2.1F, -1.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offset(-3.0F, -1.0F, 0.5F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(17, 13).mirror().addBox(0.6F, -0.5F, -1.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(17, 13).mirror().addBox(1.1F, 0.25F, -1.25F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(17, 13).mirror().addBox(0.35F, -1.15F, -1.25F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(17, 13).mirror().addBox(-0.05F, -1.4F, -1.25F, 1.0F, 3.0F, 2.0F, new CubeDeformation(-0.01F)).mirror(false)
		.texOffs(16, 16).mirror().addBox(-1.25F, -2.1F, -1.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false), PartPose.offset(3.0F, -1.0F, 0.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	@Override
    public boolean useDefaultModelForAccessories() {
        return true;
    }
}
