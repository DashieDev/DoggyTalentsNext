package doggytalents.client.entity.model.dog.dogs.kusa;

import java.util.Optional;

import doggytalents.client.entity.model.SyncedAccessoryModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DragonCostumeSuitModel extends SyncedAccessoryModel{
    	public DragonCostumeSuitModel(ModelPart root) {
        super(root);
    }
	@Override
    protected void populatePart(ModelPart box) {
        this.body = Optional.of(box.getChild("body"));
        this.mane = Optional.of(box.getChild("upper_body"));
        this.legBackLeft = Optional.of(box.getChild("left_hind_leg"));
        this.legBackRight = Optional.of(box.getChild("right_hind_leg"));
        this.legFrontLeft = Optional.of(box.getChild("left_front_leg"));
        this.legFrontRight = Optional.of(box.getChild("right_front_leg"));
        this.tail = Optional.of(box.getChild("tail"));
        this.realTail = Optional.of(tail.get().getChild("real_tail"));
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition faketail = real_tail.addOrReplaceChild("faketail", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.35F))
		.texOffs(24, 23).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(30, 0).addBox(-1.0F, 3.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.35F))
		.texOffs(30, 0).addBox(-1.0F, 6.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.35F))
		.texOffs(12, 28).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.15F))
		.texOffs(12, 28).addBox(-0.5F, 3.0F, -0.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.15F))
		.texOffs(12, 28).addBox(-0.5F, 6.0F, -0.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.15F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(24, 13).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(0, 28).mirror().addBox(-1.0F, 7.0F, -3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(-1.6F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(0, 28).addBox(-1.0F, 7.0F, -3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(1.6F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(0, 28).mirror().addBox(-1.0F, 7.0F, -3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(0, 28).addBox(-1.0F, 7.0F, -3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mirrored_r1 = body.addOrReplaceChild("mirrored_r1", CubeListBuilder.create().texOffs(12, 28).addBox(-0.5F, -15.5F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.19F))
		.texOffs(12, 28).addBox(-0.5F, -15.5F, -4.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.19F)), PartPose.offsetAndRotation(0.0F, 2.5F, -11.5F, -1.5708F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mirrored_r2 = upper_body.addOrReplaceChild("mirrored_r2", CubeListBuilder.create().texOffs(12, 28).addBox(-0.5F, -11.5F, -4.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(-0.19F)), PartPose.offsetAndRotation(0.0F, 2.5F, -6.5F, -1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

}
