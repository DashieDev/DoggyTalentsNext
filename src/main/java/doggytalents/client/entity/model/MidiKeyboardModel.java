package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class MidiKeyboardModel extends SyncedAccessoryModel{
    public MidiKeyboardModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
		this.mane = Optional.of(box.getChild("upper_body"));
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition piano_rot = upper_body.addOrReplaceChild("piano_rot", CubeListBuilder.create().texOffs(0, 0).addBox(-6.5F, -5.5F, -0.5F, 13.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(1, 13).addBox(-6.5F, -5.5F, 0.0F, 13.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(13, 12).addBox(5.5F, -3.95F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F))
		.texOffs(13, 12).mirror().addBox(-6.5F, -3.95F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(18, 8).addBox(-4.5F, -3.5F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 8).addBox(-2.5F, -3.5F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 8).addBox(-0.5F, -3.5F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 8).addBox(1.5F, -3.5F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 8).addBox(3.5F, -3.5F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 3.75F, -0.1745F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 16);
	}
}
