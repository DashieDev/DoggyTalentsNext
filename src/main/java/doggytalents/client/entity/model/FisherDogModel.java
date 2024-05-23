package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class FisherDogModel extends SyncedAccessoryModel {

    public FisherDogModel(ModelPart box) {
        super(box);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.head = Optional.of(box.getChild("head"));
        this.realHead = Optional.of(this.head.get().getChild("real_head"));
    }

    public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hat = real_head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(14, 7).addBox(-3.4417F, -4.1346F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.2F))
		.texOffs(10, 0).addBox(-1.1917F, -4.3346F, -2.2F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.125F))
		.texOffs(18, 19).mirror().addBox(1.4417F, -4.1346F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(18, 19).mirror().addBox(1.4417F, -4.1346F, 0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.3F)).mirror(false)
		.texOffs(18, 19).addBox(-3.4417F, -4.1346F, 0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.4F))
		.texOffs(0, 7).addBox(-2.9417F, -5.1346F, -1.0F, 6.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 11).addBox(-2.4417F, -6.6346F, -1.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition head_r1 = hat.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.5F, -0.5F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, -3.25F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition head_r2 = hat.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -0.5F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.6964F, -3.1632F, 0.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition head_r3 = hat.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.75F, 0.5F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(2.1702F, -3.2468F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition head_r4 = hat.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(0, 0).addBox(2.0F, -0.1F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9417F, -2.8846F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition head_r5 = hat.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -0.5F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition bait = hat.addOrReplaceChild("bait", CubeListBuilder.create().texOffs(13, 13).addBox(-1.3679F, -1.5857F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-1.7F))
		.texOffs(13, 13).addBox(-1.3679F, -1.8607F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-1.7F))
		.texOffs(0, 16).addBox(-1.3679F, -1.8607F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-1.15F))
		.texOffs(9, 19).addBox(-1.3679F, -1.3607F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-1.1F))
		.texOffs(13, 13).addBox(-1.3679F, -1.1857F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-1.7F))
		.texOffs(13, 13).addBox(-1.6429F, -0.9607F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-1.7F))
		.texOffs(13, 13).addBox(-2.0179F, -1.1857F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-1.7F)), PartPose.offsetAndRotation(1.8679F, -5.1143F, -1.0F, -0.2182F, 0.0F, -0.1745F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}
}