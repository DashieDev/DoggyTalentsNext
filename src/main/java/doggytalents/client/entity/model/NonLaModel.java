package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class NonLaModel extends SyncedAccessoryModel{

    public NonLaModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.head = Optional.of(box.getChild("head"));
        this.realHead = Optional.of(head.get().getChild("real_head"));
    }

    public static LayerDefinition createNonLaLayerDefinition() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F));

		PartDefinition realHead = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition bone = realHead.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(15, 42).addBox(-1.0F, -1.9F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(15, 42).addBox(-1.0F, -2.4F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(15, 42).addBox(-1.0F, -3.2F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(0.9F, -2.5057F, 0.1F, -0.3491F, 0.0F, 0.0F));

		PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(1, 36).addBox(-6.2F, -1.7F, -0.8F, 5.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.7809F, 5.6574F, 0.5771F, -0.2339F, -0.1052F, 2.8615F));

		PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(1, 36).addBox(-2.2F, -1.4F, -0.6F, 5.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-2.1357F, 6.7454F, 0.5617F, 0.2549F, -0.0243F, 0.2281F));

		PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(1, 36).mirror().addBox(-2.8F, 0.4F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(4.4729F, 3.0465F, 0.3874F, 0.3054F, -0.0158F, -0.8673F));

		PartDefinition cube_r4 = bone.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(1, 36).addBox(-2.3F, 0.5F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-4.2729F, 3.0465F, 0.3874F, 0.3054F, 0.0158F, 0.8673F));

		PartDefinition cube_r5 = bone.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(10, 49).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, 2.8514F, -0.3879F, -2.4733F));

		PartDefinition cube_r6 = bone.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(10, 41).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, -0.2902F, 0.3879F, -0.6682F));

		PartDefinition cube_r7 = bone.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(10, 57).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, 0.8338F, -0.8134F, -0.9878F));

		PartDefinition cube_r8 = bone.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(10, 57).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, -2.3078F, 0.8134F, -2.1538F));

		PartDefinition cube_r9 = bone.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(10, 41).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, 2.4997F, -0.7074F, -2.2864F));

		PartDefinition cube_r10 = bone.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(10, 49).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, -0.6419F, 0.7074F, -0.8551F));

		PartDefinition cube_r11 = bone.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(10, 57).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, 0.4146F, -0.5221F, -0.7231F));

		PartDefinition cube_r12 = bone.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(10, 41).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, -2.727F, 0.5221F, -2.4185F));

		PartDefinition cube_r13 = bone.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(10, 57).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, 2.0078F, -0.9128F, -1.9249F));

		PartDefinition cube_r14 = bone.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(10, 57).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, -1.1338F, 0.9128F, -1.2167F));

		PartDefinition cube_r15 = bone.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(10, 49).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, 0.1525F, -0.2136F, -0.6272F));

		PartDefinition cube_r16 = bone.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(10, 49).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, -2.9891F, 0.2136F, -2.5143F));

		PartDefinition cube_r17 = bone.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(10, 57).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F))
		.texOffs(10, 57).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, 3.1416F, 0.0F, -2.5307F));

		PartDefinition cube_r18 = bone.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(10, 57).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F))
		.texOffs(10, 57).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition cube_r19 = bone.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(10, 57).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F))
		.texOffs(10, 57).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, -1.5708F, 0.9599F, -1.5708F));

		PartDefinition cube_r20 = bone.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(10, 57).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F))
		.texOffs(10, 57).addBox(-9.0F, -1.0F, -2.5F, 10.0F, 2.0F, 5.0F, new CubeDeformation(-0.9F)), PartPose.offsetAndRotation(0.0F, -2.2943F, 0.0F, 1.5708F, -0.9599F, -1.5708F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
    
}
