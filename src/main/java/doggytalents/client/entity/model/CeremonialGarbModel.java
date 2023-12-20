package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CeremonialGarbModel extends SyncedAccessoryModel{

    public CeremonialGarbModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
		this.mane = Optional.of(box.getChild("upper_body"));
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 13.5F, -2.75F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tosa = upper_body.addOrReplaceChild("tosa", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition bone2 = tosa.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(27, 0).addBox(-4.5F, -3.5F, -3.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 3).addBox(-4.5F, -3.5F, 0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F))
		.texOffs(0, 3).addBox(-4.5F, -3.5F, -1.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F))
		.texOffs(0, 3).mirror().addBox(3.5F, -3.5F, -1.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(0, 3).mirror().addBox(3.5F, -3.5F, 0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(27, 0).mirror().addBox(3.5F, -3.5F, -4.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 28).addBox(-3.5F, -3.5F, -4.5F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(0, 3).mirror().addBox(-0.5F, -3.5F, -4.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.4F)).mirror(false)
		.texOffs(0, 3).mirror().addBox(-0.5F, -3.5F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.4F)).mirror(false)
		.texOffs(0, 28).mirror().addBox(-3.5F, -3.5F, 2.5F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = bone2.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(0, 3).addBox(-0.25F, -1.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.3F))
		.texOffs(0, 3).addBox(-2.0F, -1.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(4.0F, -2.5F, 3.5F, 0.0F, 0.3491F, 0.0F));

		PartDefinition mane_rotation_r2 = bone2.addOrReplaceChild("mane_rotation_r2", CubeListBuilder.create().texOffs(0, 0).addBox(2.0548F, -0.5F, -1.4087F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2217F, -1.25F, 7.1585F, -0.1862F, -0.9385F, 0.389F));

		PartDefinition mane_rotation_r3 = bone2.addOrReplaceChild("mane_rotation_r3", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.3019F, -0.5F, 0.2834F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.9717F, 1.5F, 6.9085F, -0.6806F, -0.4077F, 0.0192F));

		PartDefinition mane_rotation_r4 = bone2.addOrReplaceChild("mane_rotation_r4", CubeListBuilder.create().texOffs(4, 0).addBox(-2.1853F, -0.5F, 2.0659F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-2.9717F, 1.5F, 6.9085F, -0.6931F, -0.4415F, 0.0495F));

		PartDefinition mane_rotation_r5 = bone2.addOrReplaceChild("mane_rotation_r5", CubeListBuilder.create().texOffs(4, 0).addBox(-2.1511F, -0.5F, 0.4064F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-2.9717F, 1.5F, 6.9085F, -0.8239F, -0.6645F, 0.2961F));

		PartDefinition mane_rotation_r6 = bone2.addOrReplaceChild("mane_rotation_r6", CubeListBuilder.create().texOffs(26, 12).mirror().addBox(-2.0783F, -0.5F, -5.6585F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.9717F, 1.5F, 6.9085F, -0.9785F, -0.8006F, 0.5262F));

		PartDefinition mane_rotation_r7 = bone2.addOrReplaceChild("mane_rotation_r7", CubeListBuilder.create().texOffs(4, 0).addBox(-2.1909F, -0.5F, -1.5408F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-2.9717F, 1.5F, 6.9085F, -1.0588F, -0.8464F, 0.6356F));

		PartDefinition mane_rotation_r8 = bone2.addOrReplaceChild("mane_rotation_r8", CubeListBuilder.create().texOffs(0, 0).addBox(1.3019F, -0.5F, 0.2834F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2217F, 1.5F, 6.9085F, -0.6806F, 0.4077F, -0.0192F));

		PartDefinition mane_rotation_r9 = bone2.addOrReplaceChild("mane_rotation_r9", CubeListBuilder.create().texOffs(4, 0).mirror().addBox(1.1853F, -0.5F, 2.0659F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(2.2217F, 1.5F, 6.9085F, -0.6931F, 0.4415F, -0.0495F));

		PartDefinition mane_rotation_r10 = bone2.addOrReplaceChild("mane_rotation_r10", CubeListBuilder.create().texOffs(4, 0).mirror().addBox(1.1511F, -0.5F, 0.4064F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(2.2217F, 1.5F, 6.9085F, -0.8239F, 0.6645F, -0.2961F));

		PartDefinition mane_rotation_r11 = bone2.addOrReplaceChild("mane_rotation_r11", CubeListBuilder.create().texOffs(4, 0).mirror().addBox(1.1909F, -0.5F, -1.5408F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(2.2217F, 1.5F, 6.9085F, -1.0588F, 0.8464F, -0.6356F));

		PartDefinition mane_rotation_r12 = bone2.addOrReplaceChild("mane_rotation_r12", CubeListBuilder.create().texOffs(26, 12).addBox(1.0783F, -0.5F, -5.6585F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2217F, 1.5F, 6.9085F, -0.9785F, 0.8006F, -0.5262F));

		PartDefinition mane_rotation_r13 = bone2.addOrReplaceChild("mane_rotation_r13", CubeListBuilder.create().texOffs(4, 0).mirror().addBox(1.1511F, -0.5F, 0.4064F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(4, 0).mirror().addBox(-1.5989F, -0.5F, 1.9064F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false)
		.texOffs(4, 0).mirror().addBox(-1.3489F, -0.5F, -3.3436F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(2.2217F, -1.25F, 7.1585F, -0.1443F, 0.7061F, 0.1443F));

		PartDefinition mane_rotation_r14 = bone2.addOrReplaceChild("mane_rotation_r14", CubeListBuilder.create().texOffs(4, 0).mirror().addBox(1.1909F, -0.5F, -1.5408F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(2.2217F, -1.25F, 7.1585F, -0.2062F, 1.0071F, 0.0633F));

		PartDefinition mane_rotation_r15 = bone2.addOrReplaceChild("mane_rotation_r15", CubeListBuilder.create().texOffs(26, 12).addBox(1.0783F, -0.5F, -5.6585F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.9217F, -0.5F, -0.1585F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2217F, -1.25F, 7.1585F, -0.1819F, 0.9214F, 0.0928F));

		PartDefinition mane_rotation_r16 = bone2.addOrReplaceChild("mane_rotation_r16", CubeListBuilder.create().texOffs(0, 0).addBox(1.3019F, -0.5F, 0.2834F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2217F, -1.25F, 7.1585F, -0.1171F, 0.3599F, 0.1968F));

		PartDefinition mane_rotation_r17 = bone2.addOrReplaceChild("mane_rotation_r17", CubeListBuilder.create().texOffs(4, 0).mirror().addBox(1.1853F, -0.5F, 2.0659F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(2.2217F, -1.25F, 7.1585F, -0.1192F, 0.4033F, 0.1913F));

		PartDefinition mane_rotation_r18 = bone2.addOrReplaceChild("mane_rotation_r18", CubeListBuilder.create().texOffs(4, 0).mirror().addBox(-1.8449F, -0.5F, -0.7093F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offsetAndRotation(2.2217F, -1.25F, 7.1585F, -0.1392F, 0.6629F, 0.1522F));

		PartDefinition mane_rotation_r19 = bone2.addOrReplaceChild("mane_rotation_r19", CubeListBuilder.create().texOffs(26, 12).addBox(-1.7944F, -0.5F, -6.278F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2217F, -1.25F, 7.1585F, -0.1243F, 0.4899F, 0.1795F));

		PartDefinition mane_rotation_r20 = bone2.addOrReplaceChild("mane_rotation_r20", CubeListBuilder.create().texOffs(4, 0).mirror().addBox(1.1853F, -0.5F, 2.0659F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-2.2783F, -1.25F, 8.1585F, -0.3486F, -0.711F, 0.2329F));

		PartDefinition mane_rotation_r21 = bone2.addOrReplaceChild("mane_rotation_r21", CubeListBuilder.create().texOffs(4, 0).mirror().addBox(-1.5989F, -0.5F, 1.9064F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(4, 0).mirror().addBox(-1.3489F, -0.5F, -3.3436F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(4, 0).mirror().addBox(1.1511F, -0.5F, 0.4064F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-2.2783F, -1.25F, 8.1585F, -0.2875F, -0.4205F, 0.1201F));

		PartDefinition mane_rotation_r22 = bone2.addOrReplaceChild("mane_rotation_r22", CubeListBuilder.create().texOffs(4, 0).mirror().addBox(-1.8449F, -0.5F, -0.7093F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(-2.2783F, -1.25F, 8.1585F, -0.2934F, -0.4623F, 0.1339F));

		PartDefinition mane_rotation_r23 = bone2.addOrReplaceChild("mane_rotation_r23", CubeListBuilder.create().texOffs(4, 0).mirror().addBox(1.1909F, -0.5F, -1.5408F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-2.2783F, -1.25F, 8.1585F, -0.264F, -0.1264F, 0.0341F));

		PartDefinition mane_rotation_r24 = bone2.addOrReplaceChild("mane_rotation_r24", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(2.5F, -1.0F, 0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.65F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -2.5F, 3.5F, 0.0F, 0.0873F, 0.0F));

		PartDefinition mane_rotation_r25 = bone2.addOrReplaceChild("mane_rotation_r25", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.55F)).mirror(false)
		.texOffs(0, 3).mirror().addBox(-0.75F, -1.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -2.5F, 3.5F, 0.0F, -0.3491F, 0.0F));

		PartDefinition mane_rotation_r26 = bone2.addOrReplaceChild("mane_rotation_r26", CubeListBuilder.create().texOffs(0, 3).addBox(-2.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.3F))
		.texOffs(0, 3).addBox(-0.25F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(4.0F, -2.5F, -4.0F, 0.0F, -0.3491F, 0.0F));

		PartDefinition mane_rotation_r27 = bone2.addOrReplaceChild("mane_rotation_r27", CubeListBuilder.create().texOffs(2, 3).addBox(5.25F, -1.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(2, 3).addBox(6.0F, -1.0F, -0.75F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(2, 3).addBox(4.5F, -1.0F, 0.75F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(2, 3).addBox(3.0F, -1.0F, 2.25F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(2, 3).addBox(3.75F, -1.0F, 1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(2, 3).addBox(7.25F, -1.0F, 1.75F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.05F))
		.texOffs(2, 3).addBox(6.5F, -1.0F, 2.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(2, 3).addBox(5.75F, -1.0F, 3.25F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.05F))
		.texOffs(2, 3).addBox(5.0F, -1.0F, 4.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(2, 3).addBox(4.25F, -1.0F, 4.75F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.05F))
		.texOffs(2, 3).addBox(4.0F, -1.0F, -3.25F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.05F))
		.texOffs(2, 3).addBox(2.5F, -1.0F, -1.75F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.05F))
		.texOffs(2, 3).addBox(3.25F, -1.0F, -2.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(2, 3).addBox(1.75F, -1.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.15F))
		.texOffs(2, 3).addBox(1.0F, -1.0F, -0.25F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-4.0F, -2.5F, -4.0F, 0.0F, 0.9599F, 0.0F));

		PartDefinition mane_rotation_r28 = bone2.addOrReplaceChild("mane_rotation_r28", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(1.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false)
		.texOffs(0, 3).mirror().addBox(-0.75F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -2.5F, -4.0F, 0.0F, 0.3491F, 0.0F));

		PartDefinition mane_rotation_r29 = bone2.addOrReplaceChild("mane_rotation_r29", CubeListBuilder.create().texOffs(0, 0).addBox(-1.9217F, -0.5F, -0.1585F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(26, 12).addBox(1.0783F, -0.5F, -5.6585F, 1.0F, 1.0F, 7.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.2783F, -1.25F, 8.1585F, -0.2679F, -0.2106F, 0.0573F));

		PartDefinition mane_rotation_r30 = bone2.addOrReplaceChild("mane_rotation_r30", CubeListBuilder.create().texOffs(0, 0).addBox(2.0548F, -0.5F, -1.4087F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.2783F, -1.25F, 8.1585F, -2.6496F, -0.9909F, 2.7202F));

		PartDefinition mane_rotation_r31 = bone2.addOrReplaceChild("mane_rotation_r31", CubeListBuilder.create().texOffs(0, 0).addBox(1.3019F, -0.5F, 0.2834F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.2783F, -1.25F, 8.1585F, -0.3622F, -0.7519F, 0.2533F));

		PartDefinition mane_rotation_r32 = bone2.addOrReplaceChild("mane_rotation_r32", CubeListBuilder.create().texOffs(26, 12).addBox(-1.7944F, -0.5F, -6.278F, 1.0F, 1.0F, 7.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.2783F, -1.25F, 8.1585F, -0.3257F, -0.6286F, 0.196F));

		PartDefinition bone = tosa.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, -0.25F, 0.0F));

		PartDefinition mane_rotation_r33 = bone.addOrReplaceChild("mane_rotation_r33", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(-1.5F, -1.0F, -3.25F, 6.0F, 8.0F, 7.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(0, 12).addBox(-4.5F, -1.0F, -3.25F, 6.0F, 8.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r34 = bone.addOrReplaceChild("mane_rotation_r34", CubeListBuilder.create().texOffs(18, 20).addBox(-4.0F, -3.0F, -10.25F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r35 = bone.addOrReplaceChild("mane_rotation_r35", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-7.25F, -5.25F, -7.75F, 8.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1314F, 0.0865F, -1.5822F));

		PartDefinition mane_rotation_r36 = bone.addOrReplaceChild("mane_rotation_r36", CubeListBuilder.create().texOffs(0, 0).addBox(-0.75F, -5.25F, -7.75F, 8.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1314F, -0.0865F, 1.5822F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
