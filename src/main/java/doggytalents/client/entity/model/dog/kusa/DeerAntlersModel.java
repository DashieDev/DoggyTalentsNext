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

public class DeerAntlersModel extends SyncedAccessoryModel {

    public DeerAntlersModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.head = Optional.of(box.getChild("head"));
        this.realHead = Optional.of(head.get().getChild("real_head"));
    }

    public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone = real_head.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 3).addBox(-3.05F, -11.45F, -8.1F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 7).addBox(-4.25F, -12.5F, -7.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 9).addBox(-4.75F, -12.95F, -6.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(5, 3).addBox(-7.5F, -13.75F, -7.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 7).addBox(-6.15F, -13.35F, -7.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(5, 3).addBox(-8.6F, -13.95F, -7.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(6, 7).addBox(-8.75F, -14.55F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 7).addBox(-8.75F, -15.25F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(6, 7).addBox(-7.5F, -14.55F, -6.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(6, 7).addBox(-6.25F, -14.4F, -6.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
		.texOffs(6, 7).mirror().addBox(5.75F, -14.4F, -6.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(0, 7).mirror().addBox(2.75F, -12.5F, -7.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 9).mirror().addBox(4.25F, -12.95F, -6.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 7).mirror().addBox(4.65F, -13.35F, -7.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(5, 3).mirror().addBox(6.1F, -13.95F, -7.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(6, 7).mirror().addBox(8.25F, -15.25F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(6, 7).mirror().addBox(7.0F, -14.55F, -6.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(5, 3).mirror().addBox(5.0F, -13.75F, -7.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(6, 7).mirror().addBox(8.25F, -14.55F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).addBox(-2.75F, -12.55F, -8.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(0, 3).mirror().addBox(2.55F, -11.45F, -8.1F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.25F, 9.5F, 7.5F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}
    
}