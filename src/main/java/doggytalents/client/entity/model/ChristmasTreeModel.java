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
		this.head = Optional.of(box.getChild("head"));
        this.realHead = Optional.of(head.get().getChild("real_head"));
		this.tail = Optional.of(box.getChild("tail"));
        this.realTail = Optional.of(tail.get().getChild("real_tail"));
		this.body = Optional.of(box.getChild("body"));
		this.mane = Optional.of(box.getChild("upper_body"));
    }

    public static LayerDefinition createBodyLayer() {
		var meshdefinition = new MeshDefinition();
		var partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));
		PartDefinition bone4 = body.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(0, 17).addBox(-3.5F, 4.0F, -16.5F, 7.0F, 3.0F, 6.0F, new CubeDeformation(0.7F))
		.texOffs(48, 0).mirror().addBox(-5.5F, 4.0F, -13.95F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false)
		.texOffs(40, 0).mirror().addBox(-5.5F, 7.0F, -17.45F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false)
		.texOffs(56, 0).mirror().addBox(-5.5F, 7.3F, -11.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false)
		.texOffs(48, 0).mirror().addBox(-2.1F, 4.65F, -10.45F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false)
		.texOffs(40, 0).mirror().addBox(-3.75F, 8.25F, -10.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false)
		.texOffs(56, 0).mirror().addBox(1.75F, 7.25F, -10.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false)
		.texOffs(56, 0).addBox(3.5F, 4.0F, -12.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(0, 8).addBox(-3.5F, 7.6F, -17.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(1.0F))
		.texOffs(40, 0).addBox(3.5F, 8.0F, -17.45F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(48, 0).addBox(3.5F, 6.75F, -14.45F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)), PartPose.offset(0.0F, -3.25F, 15.25F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));
		PartDefinition bone = upper_body.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.85F, -3.0F, 8.0F, 1.0F, 7.0F, new CubeDeformation(0.25F))
		.texOffs(40, 0).addBox(4.5F, -1.0F, 1.05F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(56, 0).addBox(1.45F, -0.5F, 3.55F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(48, 0).addBox(5.0F, 2.25F, 0.05F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(38, 15).addBox(-3.0F, 2.0F, -1.0F, 8.0F, 3.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offset(-1.0F, 0.0F, 0.0F));
		PartDefinition bone2 = upper_body.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(39, 5).addBox(-5.0F, -2.0F, 0.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.15F))
		.texOffs(56, 0).mirror().addBox(-7.0F, 2.75F, -0.45F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false)
		.texOffs(40, 0).mirror().addBox(-6.5F, -1.0F, 1.05F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false)
		.texOffs(48, 0).mirror().addBox(-4.5F, -1.5F, 3.55F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false)
		.texOffs(40, 0).mirror().addBox(-1.65F, 3.25F, 3.8F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offset(1.0F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));
		var real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		var bone3 = real_head.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(-0.0622F, -5.7034F, 0.0F));
		var head_r1 = bone3.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(41, 24).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.77F)).mirror(false), PartPose.offsetAndRotation(0.2122F, -0.0466F, 0.0F, -1.5708F, 0.0F, -2.3736F));
		var head_r2 = bone3.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(54, 25).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(0.0622F, 0.7034F, 0.0F, -1.5708F, 0.0F, 0.0F));
		var head_r3 = bone3.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(41, 24).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F)).mirror(false), PartPose.offsetAndRotation(0.4122F, -1.0466F, 0.0F, -1.5708F, 0.0F, -1.5882F));
		var head_r4 = bone3.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(41, 24).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.78F)), PartPose.offsetAndRotation(-0.0878F, -0.0466F, 0.0F, -1.5708F, 0.0F, 2.3736F));
		var head_r5 = bone3.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(29, 12).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.66F)), PartPose.offsetAndRotation(-0.3878F, -0.7966F, 0.0F, -1.5708F, 0.0F, 0.3578F));
		var head_r6 = bone3.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(41, 24).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(-0.2878F, -1.0466F, 0.0F, -1.5708F, 0.0F, 1.5882F));
		var head_r7 = bone3.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(41, 24).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(0.0622F, 0.2034F, 0.0F, -1.5708F, 0.0F, 0.0F));
		var head_r8 = bone3.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(29, 12).mirror().addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.65F)).mirror(false), PartPose.offsetAndRotation(0.5122F, -0.7966F, 0.0F, -1.5708F, 0.0F, -0.3578F));
		var head_r9 = bone3.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(27, 21).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.65F)), PartPose.offsetAndRotation(0.0622F, 1.2034F, 0.0F, -1.5708F, 0.0F, 0.0F));

		var tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 8.0F));
		var real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));


		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
