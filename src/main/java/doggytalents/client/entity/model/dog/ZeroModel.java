package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class ZeroModel extends GlowingEyeDogModel {

    public ZeroModel(ModelPart box) {
        super(box, RenderType::entityTranslucent);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 8.0F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = real_tail.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(46, 32).mirror().addBox(-0.5F, -1.5F, 0.9F, 1.0F, 3.0F, 2.0F, new CubeDeformation(-0.45F)).mirror(false), PartPose.offsetAndRotation(-0.0339F, 8.3117F, 3.6054F, 0.0F, 0.3927F, -1.5708F));

		PartDefinition mane_rotation_r2 = real_tail.addOrReplaceChild("mane_rotation_r2", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(-2.5F, -1.5F, -4.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(-0.0339F, 3.6264F, 2.024F, 0.0F, 1.5708F, -1.5708F));

		PartDefinition mane_rotation_r3 = real_tail.addOrReplaceChild("mane_rotation_r3", CubeListBuilder.create().texOffs(46, 32).addBox(-0.5F, -1.5F, -1.25F, 1.0F, 3.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(-0.0339F, 7.6159F, 4.6245F, 0.0F, -0.48F, -1.5708F));

		PartDefinition mane_rotation_r4 = real_tail.addOrReplaceChild("mane_rotation_r4", CubeListBuilder.create().texOffs(46, 32).addBox(1.4F, -1.5F, 1.1F, 1.0F, 3.0F, 2.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(-0.0339F, 7.8717F, 8.198F, -3.1416F, 1.0908F, 1.5708F));

		PartDefinition mane_rotation_r5 = real_tail.addOrReplaceChild("mane_rotation_r5", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(-0.5F, -1.5F, -4.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.15F)).mirror(false), PartPose.offsetAndRotation(-0.0339F, 3.7483F, 7.4397F, 0.0F, 0.3491F, -1.5708F));

		PartDefinition mane_rotation_r6 = real_tail.addOrReplaceChild("mane_rotation_r6", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(-0.5F, -1.5F, -1.25F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(-0.0339F, 4.7721F, 6.9772F, 0.0F, -0.6545F, -1.5708F));

		PartDefinition mane_rotation_r7 = real_tail.addOrReplaceChild("mane_rotation_r7", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(-0.5F, -1.5F, -7.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(-0.0339F, 12.5182F, 8.3891F, 0.0F, -1.5272F, -1.5708F));

		PartDefinition mane_rotation_r8 = real_tail.addOrReplaceChild("mane_rotation_r8", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(-0.5F, -1.5F, -1.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(-0.0339F, 8.8417F, 7.5207F, -3.1416F, -0.6109F, 1.5708F));

		PartDefinition mane_rotation_r9 = real_tail.addOrReplaceChild("mane_rotation_r9", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(-1.0F, -1.75F, -4.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(0.2161F, 9.8787F, 1.4311F, -3.1416F, 0.1309F, 1.5708F));

		PartDefinition mane_rotation_r10 = real_tail.addOrReplaceChild("mane_rotation_r10", CubeListBuilder.create().texOffs(40, 26).addBox(-0.5374F, -2.0F, -3.2966F, 1.0F, 4.0F, 8.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.2785F, 3.1438F, 0.4004F, 1.6386F, -0.8715F, -0.0519F));

		PartDefinition mane_rotation_r11 = real_tail.addOrReplaceChild("mane_rotation_r11", CubeListBuilder.create().texOffs(40, 26).mirror().addBox(-0.4626F, -2.0F, -3.2966F, 1.0F, 4.0F, 8.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-1.2785F, 3.1438F, 0.4004F, 1.6386F, 0.8715F, 0.0519F));

		PartDefinition mane_rotation_r12 = real_tail.addOrReplaceChild("mane_rotation_r12", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(0.974F, -0.75F, -6.1832F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(-1.0339F, 2.5348F, 0.1317F, 2.0852F, 0.7412F, 0.6604F));

		PartDefinition mane_rotation_r13 = real_tail.addOrReplaceChild("mane_rotation_r13", CubeListBuilder.create().texOffs(45, 31).addBox(-1.974F, -0.75F, -6.1832F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(1.0339F, 2.5348F, 0.1317F, 2.0852F, -0.7412F, -0.6604F));

		PartDefinition mane_rotation_r14 = real_tail.addOrReplaceChild("mane_rotation_r14", CubeListBuilder.create().texOffs(44, 30).mirror().addBox(-1.0F, -1.75F, -2.5F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.35F)).mirror(false), PartPose.offsetAndRotation(0.2161F, 7.8361F, 1.371F, 3.1416F, 1.0036F, 1.5708F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-1.6F, 16.0F, 7.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(1.5F, 16.0F, 7.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-1.5F, 16.0F, -4.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(1.5F, 16.0F, -4.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r15 = body.addOrReplaceChild("mane_rotation_r15", CubeListBuilder.create().texOffs(41, 27).mirror().addBox(-0.5F, -2.0F, -2.5F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.5F, 4.5F, -3.5F, 0.0F, 0.0F, -0.4363F));

		PartDefinition mane_rotation_r16 = body.addOrReplaceChild("mane_rotation_r16", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(1.5F, -2.0F, -5.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(-4.2277F, 4.8393F, -3.1003F, 0.0F, 0.4363F, -0.4363F));

		PartDefinition mane_rotation_r17 = body.addOrReplaceChild("mane_rotation_r17", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(-0.5F, -1.0F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.35F)).mirror(false), PartPose.offsetAndRotation(-4.6149F, 0.9937F, -7.4662F, -0.0258F, 0.4672F, -0.0982F));

		PartDefinition mane_rotation_r18 = body.addOrReplaceChild("mane_rotation_r18", CubeListBuilder.create().texOffs(41, 27).mirror().addBox(-1.1339F, -3.8905F, -2.5F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(-3.6599F, 2.9382F, -3.5F, 0.0F, 0.0F, 0.1309F));

		PartDefinition mane_rotation_r19 = body.addOrReplaceChild("mane_rotation_r19", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(-0.7702F, -3.8571F, -2.0419F, 1.0F, 4.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(-2.4306F, 2.9325F, 1.0545F, 0.0F, 1.1781F, 0.1309F));

		PartDefinition mane_rotation_r20 = body.addOrReplaceChild("mane_rotation_r20", CubeListBuilder.create().texOffs(46, 32).mirror().addBox(-0.4737F, -0.5393F, -1.1929F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-2.4306F, 2.9325F, 1.0545F, 0.0739F, 1.0543F, 0.0772F));

		PartDefinition mane_rotation_r21 = body.addOrReplaceChild("mane_rotation_r21", CubeListBuilder.create().texOffs(46, 32).mirror().addBox(-0.9638F, 0.8326F, -1.5249F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.4306F, 2.9325F, 1.0545F, 0.1206F, 0.921F, -0.4531F));

		PartDefinition mane_rotation_r22 = body.addOrReplaceChild("mane_rotation_r22", CubeListBuilder.create().texOffs(45, 31).addBox(-0.2298F, -3.8571F, -2.0419F, 1.0F, 4.0F, 3.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(2.4306F, 2.9325F, 1.0545F, 0.0F, -1.1781F, -0.1309F));

		PartDefinition mane_rotation_r23 = body.addOrReplaceChild("mane_rotation_r23", CubeListBuilder.create().texOffs(42, 28).mirror().addBox(-1.6339F, 0.1095F, -3.5F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(-2.9099F, 2.4382F, -2.5F, 0.0497F, 0.3055F, 0.076F));

		PartDefinition mane_rotation_r24 = body.addOrReplaceChild("mane_rotation_r24", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(-1.6339F, 0.1095F, -6.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(-2.0537F, 2.5462F, -3.6074F, 0.0619F, 0.6976F, 0.1008F));

		PartDefinition mane_rotation_r25 = body.addOrReplaceChild("mane_rotation_r25", CubeListBuilder.create().texOffs(45, 31).addBox(0.6339F, 0.1095F, -6.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(2.0537F, 2.5462F, -3.6074F, 0.0619F, -0.6976F, -0.1008F));

		PartDefinition mane_rotation_r26 = body.addOrReplaceChild("mane_rotation_r26", CubeListBuilder.create().texOffs(42, 28).addBox(0.6339F, 0.1095F, -3.5F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(2.9099F, 2.4382F, -2.5F, 0.0497F, -0.3055F, -0.076F));

		PartDefinition mane_rotation_r27 = body.addOrReplaceChild("mane_rotation_r27", CubeListBuilder.create().texOffs(41, 27).addBox(0.1339F, -3.8905F, -2.5F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(3.6599F, 2.9382F, -3.5F, 0.0F, 0.0F, -0.1309F));

		PartDefinition mane_rotation_r28 = body.addOrReplaceChild("mane_rotation_r28", CubeListBuilder.create().texOffs(45, 31).addBox(-0.5F, -1.0F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(4.6149F, 0.9937F, -7.4662F, -0.0278F, -0.598F, 0.1023F));

		PartDefinition mane_rotation_r29 = body.addOrReplaceChild("mane_rotation_r29", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(0.974F, -2.0F, -6.1832F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offsetAndRotation(-1.0339F, 7.7658F, -2.4534F, 0.0977F, 0.5347F, -0.827F));

		PartDefinition mane_rotation_r30 = body.addOrReplaceChild("mane_rotation_r30", CubeListBuilder.create().texOffs(41, 27).mirror().addBox(-0.4626F, -2.0F, -3.2966F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(-1.2785F, 7.4551F, -2.4534F, 0.0844F, 0.1002F, -0.8684F));

		PartDefinition mane_rotation_r31 = body.addOrReplaceChild("mane_rotation_r31", CubeListBuilder.create().texOffs(45, 31).addBox(-2.224F, -2.0F, -6.1832F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(1.0339F, 7.7658F, -2.4534F, 0.0977F, -0.5347F, 0.827F));

		PartDefinition mane_rotation_r32 = body.addOrReplaceChild("mane_rotation_r32", CubeListBuilder.create().texOffs(41, 27).addBox(-0.5374F, -2.0F, -3.2966F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(1.2785F, 7.4551F, -2.4534F, 0.0844F, -0.1002F, 0.8684F));

		PartDefinition mane_rotation_r33 = body.addOrReplaceChild("mane_rotation_r33", CubeListBuilder.create().texOffs(45, 31).addBox(-2.5781F, -2.4226F, -9.7119F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(2.5F, 4.5F, 0.75F, 0.1807F, -0.6389F, 0.3627F));

		PartDefinition mane_rotation_r34 = body.addOrReplaceChild("mane_rotation_r34", CubeListBuilder.create().texOffs(46, 32).mirror().addBox(-6.2F, -3.9F, 2.55F, 1.0F, 4.0F, 2.0F, new CubeDeformation(-0.3F)).mirror(false)
		.texOffs(46, 32).mirror().addBox(-5.7F, -3.9F, 1.55F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0361F, 1.1975F, -1.7142F, -3.1416F, 1.0472F, -1.5708F));

		PartDefinition mane_rotation_r35 = body.addOrReplaceChild("mane_rotation_r35", CubeListBuilder.create().texOffs(42, 28).mirror().addBox(-0.8F, -4.0F, -4.75F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.1361F, 4.1399F, 1.6472F, 0.0F, 1.5708F, 1.5708F));

		PartDefinition mane_rotation_r36 = body.addOrReplaceChild("mane_rotation_r36", CubeListBuilder.create().texOffs(46, 32).addBox(-0.5263F, -0.5393F, -1.1929F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(2.4306F, 2.9325F, 1.0545F, 0.0739F, -1.0543F, -0.0772F));

		PartDefinition mane_rotation_r37 = body.addOrReplaceChild("mane_rotation_r37", CubeListBuilder.create().texOffs(46, 32).addBox(-0.0362F, 0.8326F, -1.5249F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.4306F, 2.9325F, 1.0545F, 0.1206F, -0.921F, 0.4531F));

		PartDefinition mane_rotation_r38 = body.addOrReplaceChild("mane_rotation_r38", CubeListBuilder.create().texOffs(41, 27).addBox(0.4063F, -2.4226F, -6.75F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 4.5F, 0.75F, 0.148F, -0.2082F, 0.4404F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r39 = upper_body.addOrReplaceChild("mane_rotation_r39", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(-6.5F, -3.0F, 2.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.8528F, 0.4052F, -5.7584F, 0.0F, 0.9599F, 0.4363F));

		PartDefinition mane_rotation_r40 = upper_body.addOrReplaceChild("mane_rotation_r40", CubeListBuilder.create().texOffs(41, 27).mirror().addBox(-0.5F, -2.0F, -2.5F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.5F, -1.0F, -3.5F, 0.0F, 0.0F, 0.4363F));

		PartDefinition mane_rotation_r41 = upper_body.addOrReplaceChild("mane_rotation_r41", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(1.5F, -2.0F, -5.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(-4.2277F, -1.3393F, -3.1003F, 0.0F, 0.4363F, 0.4363F));

		PartDefinition mane_rotation_r42 = upper_body.addOrReplaceChild("mane_rotation_r42", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(-1.0F, -2.0F, -1.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.35F)).mirror(false), PartPose.offsetAndRotation(-4.6149F, 2.5063F, -7.4662F, 0.0321F, 0.7725F, 0.1091F));

		PartDefinition mane_rotation_r43 = upper_body.addOrReplaceChild("mane_rotation_r43", CubeListBuilder.create().texOffs(41, 27).mirror().addBox(-1.1339F, -0.1095F, -2.5F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(-3.6599F, 0.5618F, -3.5F, 0.0F, 0.0F, -0.1309F));

		PartDefinition mane_rotation_r44 = upper_body.addOrReplaceChild("mane_rotation_r44", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(-8.1339F, -0.1095F, 2.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(-4.0892F, 0.7192F, -7.3944F, 0.0F, 1.1781F, -0.1309F));

		PartDefinition mane_rotation_r45 = upper_body.addOrReplaceChild("mane_rotation_r45", CubeListBuilder.create().texOffs(45, 31).addBox(7.1339F, -0.1095F, 2.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(4.0892F, 0.7192F, -7.3944F, 0.0F, -1.1781F, 0.1309F));

		PartDefinition mane_rotation_r46 = upper_body.addOrReplaceChild("mane_rotation_r46", CubeListBuilder.create().texOffs(41, 27).addBox(0.1339F, -0.1095F, -2.5F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(3.6599F, 0.5618F, -3.5F, 0.0F, 0.0F, 0.1309F));

		PartDefinition mane_rotation_r47 = upper_body.addOrReplaceChild("mane_rotation_r47", CubeListBuilder.create().texOffs(45, 31).addBox(1.9156F, -1.7405F, -3.6018F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(1.6149F, 2.5063F, -7.4662F, 0.0287F, -0.6417F, -0.1038F));

		PartDefinition mane_rotation_r48 = upper_body.addOrReplaceChild("mane_rotation_r48", CubeListBuilder.create().texOffs(45, 31).mirror().addBox(0.974F, -1.0F, -6.1832F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(-1.0339F, -3.7658F, -2.7034F, 0.0F, 0.4363F, 0.8727F));

		PartDefinition mane_rotation_r49 = upper_body.addOrReplaceChild("mane_rotation_r49", CubeListBuilder.create().texOffs(43, 29).addBox(-1.5374F, -2.0F, 0.7034F, 2.0F, 4.0F, 5.0F, new CubeDeformation(-0.4F))
		.texOffs(41, 27).addBox(-0.5374F, -2.0F, -5.7966F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.2785F, -3.9551F, -0.2034F, 0.0F, 0.0F, -0.8727F));

		PartDefinition mane_rotation_r50 = upper_body.addOrReplaceChild("mane_rotation_r50", CubeListBuilder.create().texOffs(43, 29).mirror().addBox(-0.4626F, -2.0F, 0.7034F, 2.0F, 4.0F, 5.0F, new CubeDeformation(-0.4F)).mirror(false)
		.texOffs(41, 27).mirror().addBox(-0.4626F, -2.0F, -5.7966F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.2785F, -3.9551F, -0.2034F, 0.0F, 0.0F, 0.8727F));

		PartDefinition mane_rotation_r51 = upper_body.addOrReplaceChild("mane_rotation_r51", CubeListBuilder.create().texOffs(45, 31).addBox(-1.974F, -1.0F, -6.1832F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(1.0339F, -3.7658F, -2.7034F, 0.0F, -0.4363F, -0.8727F));

		PartDefinition mane_rotation_r52 = upper_body.addOrReplaceChild("mane_rotation_r52", CubeListBuilder.create().texOffs(45, 31).addBox(-2.5F, -2.0F, -5.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(4.2277F, -1.3393F, -3.1003F, 0.0F, -0.4363F, -0.4363F));

		PartDefinition mane_rotation_r53 = upper_body.addOrReplaceChild("mane_rotation_r53", CubeListBuilder.create().texOffs(40, 26).addBox(-2.0F, 0.0F, -5.0F, 2.0F, 4.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-1.8861F, -0.3977F, 1.9948F, 0.0F, 1.1781F, -1.5708F));

		PartDefinition mane_rotation_r54 = upper_body.addOrReplaceChild("mane_rotation_r54", CubeListBuilder.create().texOffs(46, 32).mirror().addBox(-0.5F, -2.0F, 0.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.35F)).mirror(false), PartPose.offsetAndRotation(-2.1361F, -1.6399F, 2.1472F, -2.0071F, 0.9163F, -1.5708F));

		PartDefinition mane_rotation_r55 = upper_body.addOrReplaceChild("mane_rotation_r55", CubeListBuilder.create().texOffs(46, 32).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(2.1361F, -1.6399F, 2.1472F, -2.0071F, -0.9163F, 1.5708F));

		PartDefinition mane_rotation_r56 = upper_body.addOrReplaceChild("mane_rotation_r56", CubeListBuilder.create().texOffs(45, 31).addBox(5.5F, -3.0F, 2.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8528F, 0.4052F, -5.7584F, 0.0F, -0.9599F, -0.4363F));

		PartDefinition mane_rotation_r57 = upper_body.addOrReplaceChild("mane_rotation_r57", CubeListBuilder.create().texOffs(41, 27).addBox(-0.5F, -2.0F, -2.5F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -1.0F, -3.5F, 0.0F, 0.0F, -0.4363F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 7.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F))
		.texOffs(2, 1).addBox(-1.5F, 2.0F, -1.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_r1 = real_head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(29, 21).addBox(-1.5F, -1.25F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.65F)), PartPose.offsetAndRotation(0.0F, 4.7761F, -7.0433F, 0.1309F, 0.0F, 0.0F));

		PartDefinition head_r2 = real_head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(44, 41).addBox(-1.5F, -1.0F, -5.5F, 3.0F, 2.0F, 7.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(0.0F, 2.48F, -1.5F, 0.3927F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, -2.25F, 1.0F, -0.5091F, -0.123F, -0.364F));

		PartDefinition head_r3 = right_ear.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(16, 14).addBox(-0.9957F, -0.3134F, -0.7326F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.4875F, -4.2418F, 1.0528F, -0.5612F, -0.3567F, -0.8125F));

		PartDefinition head_r4 = right_ear.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, -1.25F, -0.65F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(-3.0393F, -5.0713F, 2.288F, 0.0497F, -0.3567F, -0.8125F));

		PartDefinition head_r5 = right_ear.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0043F, -2.9363F, -0.9834F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-0.9742F, -3.3363F, 1.8696F, -0.343F, -0.3567F, -0.8125F));

		PartDefinition head_r6 = right_ear.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0043F, -1.4363F, -0.7334F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.4875F, -4.2418F, 1.0528F, -1.2157F, -0.3567F, -0.8125F));

		PartDefinition head_r7 = right_ear.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(16, 14).addBox(-1.0F, -3.4F, -0.75F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7426F, -1.4215F, -1.4787F, -0.6545F, 0.0F, 0.0F));

		PartDefinition head_r8 = right_ear.addOrReplaceChild("head_r8", CubeListBuilder.create().texOffs(16, 14).addBox(-0.5F, 0.1F, 0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7426F, -1.4215F, -1.4787F, 0.2597F, 0.0338F, -0.1265F));

		PartDefinition head_r9 = right_ear.addOrReplaceChild("head_r9", CubeListBuilder.create().texOffs(16, 14).addBox(-0.5F, -1.75F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.7426F, -1.4365F, -0.6203F, 0.0F, 0.0F, -0.3491F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offsetAndRotation(1.5F, -2.25F, 1.0F, -0.5091F, 0.123F, 0.364F));

		PartDefinition head_r10 = left_ear.addOrReplaceChild("head_r10", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.0043F, -0.3134F, -0.7326F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(1.4875F, -4.2418F, 1.0528F, -0.5612F, 0.3567F, 0.8125F));

		PartDefinition head_r11 = left_ear.addOrReplaceChild("head_r11", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.0F, -1.25F, -0.65F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.35F)).mirror(false), PartPose.offsetAndRotation(3.0393F, -5.0713F, 2.288F, 0.0497F, 0.3567F, 0.8125F));

		PartDefinition head_r12 = left_ear.addOrReplaceChild("head_r12", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.9957F, -2.9363F, -0.9834F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)).mirror(false), PartPose.offsetAndRotation(0.9742F, -3.3363F, 1.8696F, -0.343F, 0.3567F, 0.8125F));

		PartDefinition head_r13 = left_ear.addOrReplaceChild("head_r13", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-0.9957F, -1.4363F, -0.7334F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(1.4875F, -4.2418F, 1.0528F, -1.2157F, 0.3567F, 0.8125F));

		PartDefinition head_r14 = left_ear.addOrReplaceChild("head_r14", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.0F, -3.4F, -0.75F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.7426F, -1.4215F, -1.4787F, -0.6545F, 0.0F, 0.0F));

		PartDefinition head_r15 = left_ear.addOrReplaceChild("head_r15", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.5F, 0.1F, 0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.7426F, -1.4215F, -1.4787F, 0.2597F, -0.0338F, 0.1265F));

		PartDefinition head_r16 = left_ear.addOrReplaceChild("head_r16", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.5F, -1.75F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.7426F, -1.4365F, -0.6203F, 0.0F, 0.0F, 0.3491F));

		PartDefinition bone3 = real_head.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(27, 6).addBox(-1.475F, 0.24F, -4.8146F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.35F)), PartPose.offsetAndRotation(-0.025F, 0.49F, -1.1854F, -0.1309F, 0.0F, 0.0F));

		PartDefinition head_r17 = bone3.addOrReplaceChild("head_r17", CubeListBuilder.create().texOffs(26, 21).addBox(-1.525F, 3.874F, -8.3662F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.65F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition head_r18 = bone3.addOrReplaceChild("head_r18", CubeListBuilder.create().texOffs(27, 6).addBox(-1.475F, 0.3915F, -7.7106F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.55F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition head_r19 = bone3.addOrReplaceChild("head_r19", CubeListBuilder.create().texOffs(28, 7).addBox(-1.475F, -1.7202F, -2.1022F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.9599F, 0.0F, 0.0F));

		PartDefinition glowing_eyes = partdefinition.addOrReplaceChild("glowing_eyes", CubeListBuilder.create(), PartPose.offset(0.0F, 7.5F, -7.0F));

		PartDefinition real_glowing_eyes = glowing_eyes.addOrReplaceChild("real_glowing_eyes", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = real_glowing_eyes.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(48, 0).addBox(-2.0F, -2.0F, -1.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.25F)), PartPose.offsetAndRotation(0.0F, -2.2F, -10.25F, -0.1309F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	@Override
    public boolean useDefaultModelForAccessories() {
        return true;
    }

}
