package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class HotDogModel extends SyncedAccessoryModel{

    public HotDogModel(ModelPart root) {
        super(root);
    }
    
    @Override
	protected void populatePart(ModelPart box) {
		this.body = Optional.of(box.getChild("body"));
		this.mane = Optional.of(box.getChild("upper_body"));
	}
    public static LayerDefinition createHotDogLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition hotdogmane = upper_body.addOrReplaceChild("hotdogmane", CubeListBuilder.create().texOffs(46, 40).addBox(3.0F, -4.1564F, -4.401F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.3F))
		.texOffs(46, 40).addBox(-3.0F, -4.1564F, -4.401F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 2.0F, -1.5F));

		PartDefinition body_rotation_r1 = hotdogmane.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(46, 47).addBox(-8.1F, -7.5F, -3.5F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(1.0F, 3.3436F, 3.749F, 0.0F, -1.5708F, 0.0F));

		PartDefinition body_rotation_r2 = hotdogmane.addOrReplaceChild("body_rotation_r2", CubeListBuilder.create().texOffs(46, 13).addBox(2.1F, -9.25F, -2.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(1.0F, 5.3436F, -1.401F, 0.0F, -1.5708F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 2.75F, 1.5708F, 0.0F, 0.0F));

		PartDefinition hottodoggubody = body.addOrReplaceChild("hottodoggubody", CubeListBuilder.create().texOffs(46, 46).addBox(-4.0F, -1.7F, -3.35F, 2.0F, 11.0F, 7.0F, new CubeDeformation(0.1F))
		.texOffs(46, 46).addBox(2.0F, -1.7F, -3.35F, 2.0F, 11.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.8968F, -2.3213F));

		PartDefinition body_rotation_r3 = hottodoggubody.addOrReplaceChild("body_rotation_r3", CubeListBuilder.create().texOffs(46, 47).addBox(4.3F, -3.25F, -3.5F, 2.0F, 7.0F, 7.0F, new CubeDeformation(-0.1F))
		.texOffs(46, 47).addBox(-5.6F, -1.5F, -3.5F, 2.0F, 3.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 3.5F, 0.0F, 1.5708F, 0.0F, -1.5708F));

		PartDefinition body_rotation_r4 = hottodoggubody.addOrReplaceChild("body_rotation_r4", CubeListBuilder.create().texOffs(46, 19).addBox(1.9F, -3.75F, -3.5F, 2.0F, 11.0F, 7.0F, new CubeDeformation(0.2F))
		.texOffs(46, 46).addBox(-2.8F, -2.25F, -3.5F, 2.0F, 10.0F, 7.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 1.5F, -0.25F, 0.0F, -1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
    }
}
