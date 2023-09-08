package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ArcanineModel extends DogModel {

    public ArcanineModel(ModelPart box) {
        super(box);
    }

    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

        var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), 
            PartPose.offset(0F, 13.5F, -7.0F));
		var real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 31).addBox(-1.0F, -7.3F, -1.65F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 40).addBox(0.5F, -7.3F, -1.4F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 40).addBox(1.25F, -6.85F, 0.1F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 40).addBox(-3.25F, -6.85F, 0.1F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 47).addBox(-1.0F, -8.2F, -1.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 40).addBox(-2.75F, -7.3F, -1.4F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 40).addBox(-2.75F, -6.5F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 40).addBox(1.0F, -6.5F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 30).addBox(-4.0F, -5.25F, -0.25F, 8.0F, 6.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(42, 36).addBox(3.0F, -0.85F, -1.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(23, 34).addBox(2.75F, -4.55F, -1.3F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(47, 44).addBox(2.75F, -2.8F, -1.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(43, 34).addBox(2.0F, -1.8F, -1.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(48, 31).addBox(2.0F, -5.95F, 2.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(51, 34).addBox(0.75F, -7.2F, 0.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(42, 36).addBox(0.0F, -3.0F, -0.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(47, 44).addBox(1.25F, -5.25F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(43, 34).addBox(0.5F, -6.5F, -0.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(51, 34).addBox(-1.5F, -6.95F, 2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(42, 36).addBox(-3.0F, -3.0F, -0.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(47, 44).addBox(-4.25F, -5.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(43, 34).addBox(-3.5F, -6.5F, -0.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(42, 36).addBox(-4.0F, -0.85F, -1.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(42, 37).addBox(-4.5F, -5.3F, -1.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(40, 41).addBox(2.75F, -4.55F, -1.3F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(40, 41).addBox(-4.75F, -4.55F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(41, 41).addBox(-4.75F, -4.8F, -0.95F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(47, 35).addBox(4.0F, -3.3F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(47, 35).addBox(-5.0F, -3.3F, -1.4F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(47, 44).addBox(-3.25F, -0.3F, -0.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(47, 44).addBox(-5.75F, -2.8F, -1.75F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(43, 34).addBox(-5.0F, -1.8F, -1.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-1.5F, -3.02F, -4.25F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(16, 14).addBox(0.3F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 14).addBox(1.8F, -0.75F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 14).addBox(0.8F, -1.25F, -0.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 14).addBox(2.3F, -1.5F, -0.2F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(52, 13).mirror().addBox(0.8F, -1.75F, -0.1F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(2.0F, -5.0F, -1.5F));

		real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(16, 14).addBox(-2.3F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
		.texOffs(16, 14).addBox(-3.8F, -0.75F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(52, 13).addBox(-4.8F, -1.75F, -0.1F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(16, 14).addBox(-2.8F, -1.25F, -0.2F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(16, 14).addBox(-4.3F, -1.5F, -0.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(-2.0F, -5.0F, -1.5F));

		var body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.25F, -2.7F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));
        var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));

		upper_body.addOrReplaceChild("real_mane", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -5.5F, -0.5F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.25F))
		.texOffs(47, 40).addBox(-1.75F, -4.75F, 4.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(47, 40).addBox(-2.5F, -6.0F, 4.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.45F))
		.texOffs(43, 30).addBox(-2.75F, -5.5F, 3.5F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.3F))
		.texOffs(43, 30).addBox(-2.75F, -6.0F, 0.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(32, 36).addBox(-4.75F, -6.5F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(32, 36).addBox(-5.25F, -7.0F, 1.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(32, 36).addBox(-2.75F, -7.5F, 2.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(32, 36).addBox(0.5F, -7.5F, 2.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(31, 35).addBox(-1.25F, -8.5F, 1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F))
		.texOffs(32, 36).mirror().addBox(3.0F, -7.0F, 1.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(32, 36).mirror().addBox(2.75F, -6.5F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(0, 40).addBox(1.0F, -6.35F, -0.9F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 40).addBox(0.25F, -7.3F, -1.65F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(51, 34).addBox(0.5F, -7.2F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(0, 31).addBox(-1.25F, -6.8F, -2.65F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 47).addBox(-1.25F, -8.2F, -1.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.25F))
		.texOffs(51, 34).addBox(-3.75F, -7.2F, 1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 40).addBox(-3.0F, -7.05F, -1.65F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 40).addBox(-3.5F, -6.35F, -0.4F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.05F))
		.texOffs(0, 40).addBox(-3.5F, -6.35F, -2.15F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F))
		.texOffs(0, 40).mirror().addBox(-2.75F, -7.05F, -2.9F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(0, 31).addBox(-1.25F, -6.55F, -3.4F, 2.0F, 2.0F, 6.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 40).addBox(0.25F, -7.05F, -2.9F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 40).addBox(1.0F, -6.35F, -2.15F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offset(1f, 2.5f, -2.5f));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(0.5F, 16.0F, -4.0F));

		PartDefinition bone3 = left_front_leg.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(1, 48).addBox(1.1F, -4.0F, -3.9F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F))
		.texOffs(1, 48).addBox(1.1F, -4.75F, -3.4F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.15F))
		.texOffs(1, 48).addBox(1.1F, -4.0F, -2.4F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(1, 48).addBox(1.1F, -4.75F, -1.4F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.05F))
		.texOffs(1, 48).addBox(1.6F, -5.25F, -1.9F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 55).addBox(2.1F, -5.75F, -2.65F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 55).addBox(2.1F, -3.75F, -2.15F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offset(-1.0F, 8.0F, 3.5F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(43, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.5F, 16.0F, 7.0F));

		PartDefinition bone8 = right_hind_leg.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(1, 48).mirror().addBox(-2.6F, -3.5F, 5.6F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(0, 49).mirror().addBox(-2.85F, -2.75F, 4.6F, 4.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.1F, -4.5F, 6.85F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.3F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.1F, -4.0F, 8.35F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.4F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.1F, -4.75F, 9.35F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(0, 50).mirror().addBox(-3.35F, -5.5F, 8.85F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.35F, -7.0F, 8.95F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(1, 50).mirror().addBox(-3.35F, -5.75F, 10.7F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(1, 50).mirror().addBox(-3.35F, -6.25F, 9.95F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(0, 55).mirror().addBox(-3.1F, -5.25F, 7.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(0, 55).mirror().addBox(-3.1F, -3.75F, 7.85F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(2.0F, 8.0F, -6.25F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(43, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.5F, 16.0F, 7.0F));

		PartDefinition bone7 = left_hind_leg.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(1, 48).addBox(0.6F, -3.5F, 5.6F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(0, 49).addBox(-0.15F, -2.75F, 4.6F, 4.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F))
		.texOffs(1, 48).addBox(1.1F, -4.5F, 6.85F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.3F))
		.texOffs(1, 48).addBox(1.1F, -4.0F, 8.35F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.4F))
		.texOffs(1, 48).addBox(1.1F, -4.75F, 9.35F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(0, 50).addBox(1.35F, -5.5F, 8.85F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.2F))
		.texOffs(1, 48).addBox(1.35F, -7.0F, 8.95F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F))
		.texOffs(1, 50).addBox(1.35F, -5.75F, 10.7F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.05F))
		.texOffs(1, 50).addBox(1.35F, -6.25F, 9.95F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 55).addBox(2.1F, -5.25F, 7.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(0, 55).addBox(2.1F, -3.75F, 7.85F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(-1.0F, 8.0F, -6.25F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(-2.5F, 16.0F, -4.0F));

		PartDefinition bone4 = right_front_leg.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(1, 48).mirror().addBox(-3.1F, -4.0F, -3.9F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.1F, -4.75F, -3.4F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.15F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.1F, -4.0F, -2.4F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.1F, -4.75F, -1.4F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false)
		.texOffs(1, 48).mirror().addBox(-3.6F, -5.25F, -1.9F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(0, 55).mirror().addBox(-3.1F, -5.75F, -2.65F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)).mirror(false)
		.texOffs(0, 55).mirror().addBox(-3.1F, -3.75F, -2.15F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false), PartPose.offset(2.0F, 8.0F, 3.5F));

        var tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, 1.8326F, 0.0F, 0.0F));


        var real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.ZERO);
		real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(54, 56).addBox(-0.625F, 3.525F, 14.125F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(54, 56).addBox(-0.625F, 4.225F, 14.875F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(54, 56).addBox(-0.625F, 5.525F, 16.125F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(54, 56).addBox(-0.625F, 3.775F, 15.125F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 56).addBox(-0.625F, 0.775F, 9.625F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(54, 56).addBox(-0.625F, 0.525F, 8.125F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(54, 56).addBox(-0.625F, 6.225F, 14.875F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.55F))
		.texOffs(54, 56).addBox(-0.375F, 5.475F, 15.375F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.45F))
		.texOffs(54, 56).addBox(-0.625F, 1.225F, 8.875F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(53, 55).addBox(-0.625F, 1.475F, 7.125F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(53, 55).addBox(-0.625F, 5.725F, 11.875F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.45F))
		.texOffs(53, 55).addBox(-0.625F, 6.475F, 13.125F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.35F))
		.texOffs(52, 55).addBox(-1.125F, 4.475F, 11.125F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.35F))
		.texOffs(41, 53).addBox(-1.125F, 3.475F, 8.125F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.25F))
		.texOffs(24, 52).addBox(-1.625F, 1.725F, 6.125F, 3.0F, 4.0F, 4.0F, new CubeDeformation(-0.35F))
		.texOffs(24, 52).addBox(-1.625F, 2.475F, 6.125F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(24, 40).addBox(-2.125F, 3.475F, 5.125F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(23, 30).addBox(-2.625F, 6.475F, 2.575F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.45F))
		.texOffs(23, 30).addBox(-2.625F, 5.475F, 3.875F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.125F, -3.725F, 7.875F, -1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
    
}
