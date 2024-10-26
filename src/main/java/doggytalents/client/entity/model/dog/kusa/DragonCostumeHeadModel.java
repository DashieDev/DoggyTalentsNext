package doggytalents.client.entity.model.dog.kusa;

import java.util.Optional;

import doggytalents.client.entity.model.SyncedAccessoryModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DragonCostumeHeadModel extends SyncedAccessoryModel{
    	public DragonCostumeHeadModel(ModelPart root) {
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

		PartDefinition head2 = real_head.addOrReplaceChild("head2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition real_head2 = head2.addOrReplaceChild("real_head2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone3 = real_head2.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, 10.25F, 7.5F));

		PartDefinition jaw2 = bone3.addOrReplaceChild("jaw2", CubeListBuilder.create().texOffs(0, 27).addBox(-3.0F, -1.0F, -8.0F, 6.0F, 2.0F, 8.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -8.25F, -13.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition head3 = bone3.addOrReplaceChild("head3", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -3.75F, -23.0F, 6.0F, 2.5F, 8.0F, new CubeDeformation(0.01F))
		.texOffs(0, 0).addBox(-4.0F, -7.25F, -16.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.11F))
		.texOffs(28, 16).addBox(1.5F, -9.25F, -13.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.01F))
		.texOffs(28, 21).addBox(1.5F, -4.75F, -22.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -8.0F, 2.0F));

		PartDefinition mirrored2 = head3.addOrReplaceChild("mirrored2", CubeListBuilder.create().texOffs(28, 21).mirror().addBox(-2.5F, -10.75F, -46.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false)
		.texOffs(28, 16).mirror().addBox(-2.5F, -15.25F, -37.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(0.0F, 6.0F, 24.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
