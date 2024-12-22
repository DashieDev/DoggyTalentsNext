package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ChristmasTreeModel extends SyncedAccessoryModel {

    public ChristmasTreeModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
		this.tail = Optional.of(box.getChild("tail"));
        this.realTail = Optional.of(tail.get().getChild("real_tail"));
		this.body = Optional.of(box.getChild("body"));
		this.mane = Optional.of(box.getChild("upper_body"));
    }

    public static LayerDefinition createBodyLayer() {
		var meshdefinition = new MeshDefinition();
		var partdefinition = meshdefinition.getRoot();

		var tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 12.0F, 8.0F, 0.62831855F, 0.0F, 0.0F));
		var real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		var tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		var body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));
		var leaves2 = body.addOrReplaceChild("leaves2", CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, -0.15F, -1.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(1.0F))
		.texOffs(0, 17).addBox(-2.0F, -3.75F, 0.0F, 7.0F, 3.0F, 6.0F, new CubeDeformation(0.7F)), PartPose.offset(-1.5F, 4.5F, -1.25F));
		var red2 = body.addOrReplaceChild("red2", CubeListBuilder.create(), PartPose.offset(0.0F, 4.2F, -2.575F));
		var r5 = red2.addOrReplaceChild("r5", CubeListBuilder.create().texOffs(40, 0).mirror().addBox(-5.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offset(1.25F, 0.8F, 7.625F));
		var r6 = red2.addOrReplaceChild("r6", CubeListBuilder.create().texOffs(40, 0).mirror().addBox(-5.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offset(-0.5F, -0.45F, 0.375F));
		var r7 = red2.addOrReplaceChild("r7", CubeListBuilder.create().texOffs(40, 0).addBox(3.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)), PartPose.offset(0.5F, 0.55F, 0.375F));
		var r8 = red2.addOrReplaceChild("r8", CubeListBuilder.create(), PartPose.offset(-0.525F, 2.425F, -1.25F));
		var mane_rotation_r1 = r8.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(40, 0).mirror().addBox(-0.125F, 1.375F, -0.875F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));
		var r9 = red2.addOrReplaceChild("r9", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		var mane_rotation_r2 = r9.addOrReplaceChild("mane_rotation_r2", CubeListBuilder.create().texOffs(40, 0).mirror().addBox(-3.75F, 0.8F, -0.875F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		var white2 = body.addOrReplaceChild("white2", CubeListBuilder.create(), PartPose.offset(-2.0F, 1.45F, -3.825F));
		var w4 = white2.addOrReplaceChild("w4", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(-5.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offset(8.75F, 2.55F, 8.875F));
		var w5 = white2.addOrReplaceChild("w5", CubeListBuilder.create().texOffs(56, 0).addBox(3.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)), PartPose.offset(2.5F, -0.7F, 6.875F));
		var w6 = white2.addOrReplaceChild("w6", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(-5.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offset(1.5F, 2.6F, 7.375F));
		var w7 = white2.addOrReplaceChild("w7", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		var mane_rotation_r3 = w7.addOrReplaceChild("mane_rotation_r3", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(1.75F, -0.2F, -0.875F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		var yellow2 = body.addOrReplaceChild("yellow2", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.75F, 1.3F));
		var y4 = yellow2.addOrReplaceChild("y4", CubeListBuilder.create().texOffs(48, 0).mirror().addBox(-5.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offset(3.4F, 0.65F, 3.5F));
		var y5 = yellow2.addOrReplaceChild("y5", CubeListBuilder.create().texOffs(48, 0).addBox(3.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)), PartPose.offset(1.0F, 2.75F, -0.5F));
		var y6 = yellow2.addOrReplaceChild("y6", CubeListBuilder.create().texOffs(48, 0).mirror().addBox(-5.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
		var y7 = yellow2.addOrReplaceChild("y7", CubeListBuilder.create(), PartPose.offset(0.5F, 2.7F, -3.375F));
		var mane_rotation_r4 = y7.addOrReplaceChild("mane_rotation_r4", CubeListBuilder.create().texOffs(48, 0).mirror().addBox(-2.1F, -2.8F, -1.125F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		var leaves = upper_body.addOrReplaceChild("leaves", CubeListBuilder.create().texOffs(38, 15).addBox(-5.0F, 1.0F, -1.05F, 8.0F, 3.0F, 5.0F, new CubeDeformation(0.5F))
		.texOffs(39, 5).addBox(-5.0F, -3.0F, -0.05F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.15F))
		.texOffs(0, 0).addBox(-5.0F, -3.85F, -3.05F, 8.0F, 1.0F, 7.0F, new CubeDeformation(0.25F)), PartPose.offset(1.0F, 1.0F, 0.05F));

		var yellow = upper_body.addOrReplaceChild("yellow", CubeListBuilder.create(), PartPose.offset(1.975F, 1.875F, -1.575F));
		var y = yellow.addOrReplaceChild("y", CubeListBuilder.create().texOffs(48, 0).addBox(3.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)), PartPose.offset(-0.975F, 0.375F, 1.625F));
		var y2 = yellow.addOrReplaceChild("y2", CubeListBuilder.create().texOffs(48, 0).mirror().addBox(-5.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offset(-0.475F, -3.375F, 5.125F));
		var y3 = yellow.addOrReplaceChild("y3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		var mane_rotation_r5 = y3.addOrReplaceChild("mane_rotation_r5", CubeListBuilder.create().texOffs(48, 0).mirror().addBox(-2.975F, -3.375F, -1.125F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		var white = upper_body.addOrReplaceChild("white", CubeListBuilder.create(), PartPose.offset(-0.525F, 1.875F, -0.325F));
		var w = white.addOrReplaceChild("w", CubeListBuilder.create().texOffs(56, 0).addBox(3.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)), PartPose.offset(-2.025F, -2.375F, 3.875F));
		var w2 = white.addOrReplaceChild("w2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		var mane_rotation_r6 = w2.addOrReplaceChild("mane_rotation_r6", CubeListBuilder.create().texOffs(56, 0).addBox(0.975F, -2.375F, -1.125F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));
		var w3 = white.addOrReplaceChild("w3", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(-5.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offset(-0.475F, 0.875F, -0.125F));

		var red = upper_body.addOrReplaceChild("red", CubeListBuilder.create(), PartPose.offset(-0.5F, -1.0F, 1.05F));
		var r = red.addOrReplaceChild("r", CubeListBuilder.create().texOffs(40, 0).addBox(3.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)), PartPose.offset(1.0F, 0.0F, 0.0F));
		var r2 = red.addOrReplaceChild("r2", CubeListBuilder.create().texOffs(40, 0).mirror().addBox(-3.8F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offset(3.65F, 4.25F, 2.75F));
		var r3 = red.addOrReplaceChild("r3", CubeListBuilder.create(), PartPose.offset(-0.025F, 2.875F, -1.375F));
		var mane_rotation_r7 = r3.addOrReplaceChild("mane_rotation_r7", CubeListBuilder.create().texOffs(40, 0).mirror().addBox(-0.125F, 1.375F, -0.875F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));
		var r4 = red.addOrReplaceChild("r4", CubeListBuilder.create().texOffs(40, 0).mirror().addBox(-5.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
