package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class WitchHatModel extends SyncedAccessoryModel{

    public WitchHatModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
		this.head = Optional.of(box.getChild("head"));
        this.realHead = Optional.of(head.get().getChild("real_head"));
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition headwear = real_head.addOrReplaceChild("headwear", CubeListBuilder.create().texOffs(1, 29).addBox(0.0F, -0.25F, 0.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(-0.6F)), PartPose.offset(-5.0F, -4.05F, -5.0F));

		PartDefinition hat2 = headwear.addOrReplaceChild("hat2", CubeListBuilder.create().texOffs(1, 41).addBox(-0.1F, 1.5F, 0.0F, 7.0F, 4.0F, 7.0F, new CubeDeformation(-0.95F)), PartPose.offsetAndRotation(1.75F, -4.0F, 2.0F, -0.0524F, 0.0F, 0.0262F));

		PartDefinition hat3 = hat2.addOrReplaceChild("hat3", CubeListBuilder.create().texOffs(1, 52).addBox(0.0F, 2.75F, 0.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(1.75F, -4.0F, 2.0F, -0.1047F, 0.0F, 0.0524F));

		PartDefinition hat4 = hat3.addOrReplaceChild("hat4", CubeListBuilder.create().texOffs(1, 60).addBox(0.25F, 2.75F, 0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.75F, -2.0F, 2.0F, -0.2094F, 0.0F, 0.1047F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
