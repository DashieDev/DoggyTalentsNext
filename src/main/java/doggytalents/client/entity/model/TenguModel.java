package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class TenguModel extends SyncedAccessoryModel   {

    public TenguModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
		this.head = Optional.of(box.getChild("head"));
		this.realHead = Optional.of(head.get().getChild("real_head"));
    }
    public static LayerDefinition createTenguLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone3 = real_head.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(54, 15).addBox(-0.5F, 0.1471F, -4.3429F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(42, 16).addBox(-2.0F, -1.8329F, -0.6929F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(48, 24).addBox(-2.0F, 2.9171F, -1.0929F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(56, 4).addBox(-2.15F, -2.8329F, -0.3429F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(56, 4).mirror().addBox(0.15F, -2.8329F, -0.3429F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(58, 8).addBox(-2.5F, -2.0329F, -0.3429F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(58, 8).mirror().addBox(1.5F, -2.0329F, -0.3429F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.1671F, -4.2071F, -0.1309F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
    
}
