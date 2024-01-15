package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BoltModel extends DogModel{

    public BoltModel(ModelPart box) {
        super(box);
    }
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 13.7F, 2.9F));

		PartDefinition real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(58, 49).addBox(-1.0F, 0.0578F, -1.6746F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_r1 = real_tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(58, 49).addBox(-0.925F, -3.8847F, -2.6262F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-0.075F, 3.5048F, 2.6357F, 0.3927F, 0.0F, 0.0F));

		PartDefinition tail_r2 = real_tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(58, 49).addBox(-0.925F, -0.9792F, -1.9642F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.075F, 3.5048F, 2.6357F, 1.6144F, 0.0F, 0.0F));

		PartDefinition tail_r3 = real_tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(59, 50).addBox(-1.075F, 1.0271F, -3.5609F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.075F, 3.5048F, 2.6357F, 2.3126F, 0.0F, 0.0F));

		PartDefinition tail_r4 = real_tail.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(59, 50).addBox(-1.075F, 0.5271F, -4.0609F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.075F, 3.5048F, 2.6357F, 2.138F, 0.0F, 0.0F));

		PartDefinition tail_r5 = real_tail.addOrReplaceChild("tail_r5", CubeListBuilder.create().texOffs(58, 49).addBox(-1.075F, 1.2771F, -3.0609F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.075F, 3.5048F, 2.6357F, 1.9635F, 0.0F, 0.0F));

		PartDefinition tail_r6 = real_tail.addOrReplaceChild("tail_r6", CubeListBuilder.create().texOffs(58, 49).addBox(-0.925F, -2.1787F, -1.8762F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.075F, 3.5048F, 2.6357F, 0.9163F, 0.0F, 0.0F));

		PartDefinition tail_r7 = real_tail.addOrReplaceChild("tail_r7", CubeListBuilder.create().texOffs(58, 49).addBox(-1.0F, 6.0207F, -1.6623F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, 2.0346F, -5.2637F, 1.2217F, 0.0F, 0.0F));

		PartDefinition tail_r8 = real_tail.addOrReplaceChild("tail_r8", CubeListBuilder.create().texOffs(58, 49).addBox(-1.1F, 7.2207F, -1.5123F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.1F, 5.176F, -4.1567F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tail_r9 = real_tail.addOrReplaceChild("tail_r9", CubeListBuilder.create().texOffs(58, 49).addBox(-1.0F, 3.0207F, -1.6623F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, -0.3894F, -1.7454F, 0.5236F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(59, 19).mirror().addBox(-0.9F, 3.4346F, -0.1525F, 2.0F, 4.6F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 16.0F, 3.8133F));

		PartDefinition cube_r1 = right_hind_leg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(80, 20).mirror().addBox(-1.4F, -2.7663F, -1.3353F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.5F, 2.0009F, -0.3172F, 0.6545F, 0.0F, 0.0F));

		PartDefinition cube_r2 = right_hind_leg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(65, 5).mirror().addBox(-1.4F, -2.9663F, -0.5353F, 2.0F, 5.0F, 2.2F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.5F, 2.0009F, -0.3172F, 0.281F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(59, 19).addBox(-1.1F, 3.4346F, -0.1525F, 2.0F, 4.6F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 16.0F, 3.8133F));

		PartDefinition cube_r3 = left_hind_leg.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(80, 20).addBox(-0.6F, -2.7663F, -1.3353F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.5F, 2.0009F, -0.3172F, 0.6545F, 0.0F, 0.0F));

		PartDefinition cube_r4 = left_hind_leg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(65, 5).addBox(-0.6F, -2.9663F, -0.5353F, 2.0F, 5.0F, 2.2F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.5F, 2.0009F, -0.3172F, 0.281F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(57, 4).addBox(-1.2625F, -0.9654F, -0.7059F, 1.95F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.4875F, 17.0F, -5.7333F));

		PartDefinition cube_r5 = right_front_leg.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(71, 23).addBox(-4.3F, 0.0F, -1.7F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.9875F, 0.0346F, 0.2941F, 0.1745F, 0.0F, 0.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(57, 4).mirror().addBox(-0.6375F, -0.9654F, -0.7059F, 1.95F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.4875F, 17.0F, -5.7333F));

		PartDefinition cube_r6 = left_front_leg.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(71, 23).mirror().addBox(2.3F, 0.0F, -1.7F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-2.9875F, 0.0346F, 0.2941F, 0.1745F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 15.7065F, 0.1123F, 1.6581F, 0.0F, 0.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(29, 117).addBox(-3.0F, -1.2006F, -0.9444F, 6.0F, 5.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 3.4618F, -0.2743F, -1.789F, 0.0F, 0.0F));

		PartDefinition cube_r7 = body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 102).addBox(-2.95F, -0.77F, 0.0F, 5.9F, 1.8F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.2234F, -1.9737F, -1.5708F, 0.0F, 0.0F));

		PartDefinition body_r2 = body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(4, 122).addBox(-3.0F, -1.5539F, -1.8444F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, 3.4618F, -0.2743F, -2.4435F, 0.0F, 0.0F));

		PartDefinition body_r3 = body.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(2, 110).addBox(-3.0F, -3.0F, -5.5609F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(55, 80).addBox(-3.0F, -3.0F, -5.5609F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 4.1838F, -1.1206F, -1.8326F, 0.0F, 0.0F));

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 15.0F, -5.0F, 1.309F, 0.0F, 0.0F));

		PartDefinition cube_r8 = upper_body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 74).addBox(-3.0F, -2.4109F, -4.0388F, 6.0F, 4.0F, 7.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, -1.8534F, 0.0912F, -2.3126F, 0.0F, 0.0F));

		PartDefinition cube_r9 = upper_body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(19, 62).addBox(-2.55F, -0.2422F, -3.3F, 5.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.3313F, 3.3599F, -2.0071F, 0.0F, 0.0F));

		PartDefinition cube_r10 = upper_body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(21, 54).addBox(-3.0F, 0.0F, -0.5774F, 6.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -2.254F, -0.4916F, -1.2654F, 0.0F, 0.0F));

		PartDefinition upperBody_r1 = upper_body.addOrReplaceChild("upperBody_r1", CubeListBuilder.create().texOffs(54, 115).addBox(-2.95F, -3.0F, -2.0F, 6.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 56).addBox(-2.95F, -3.0F, -2.0F, 6.0F, 7.0F, 5.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.6687F, 0.6021F, -1.5708F, 0.0F, 0.0F));

		PartDefinition body_r4 = upper_body.addOrReplaceChild("body_r4", CubeListBuilder.create().texOffs(53, 96).addBox(-3.0F, -2.5F, -2.25F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(0, 1).addBox(-3.0F, -2.5F, -2.25F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 3.1569F, 0.8464F, -1.6144F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 11.2846F, -8.3891F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r11 = real_head.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 14).addBox(-2.5F, 0.7945F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -1.6291F, 2.2911F, -0.4363F, 0.0F, 0.0F));

		PartDefinition cube_r12 = real_head.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(11, 20).addBox(-2.5F, -0.551F, -3.2099F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4164F, 2.849F, -0.7418F, 0.0F, 0.0F));

		PartDefinition cube_r13 = real_head.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(13, 27).addBox(-1.6565F, -0.4439F, -1.2653F, 2.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.0575F, 2.2778F, -2.9237F, 0.0983F, -0.478F, -0.0453F));

		PartDefinition cube_r14 = real_head.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -0.5862F, -1.8423F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 2.2664F, -2.8979F, 0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r15 = real_head.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(13, 27).mirror().addBox(-0.3435F, -0.4439F, -1.2653F, 2.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0575F, 2.2778F, -2.9237F, 0.0983F, 0.478F, 0.0453F));

		PartDefinition cube_r16 = real_head.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(13, 33).addBox(-1.3553F, -0.7577F, -0.4564F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.9077F, -3.7949F, 0.1473F, -0.4755F, -0.0678F));

		PartDefinition cube_r17 = real_head.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(17, 11).mirror().addBox(-0.25F, -0.25F, -0.4276F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.2467F, -1.0033F, -1.9333F, -0.0202F, -0.1787F, -0.1329F));

		PartDefinition cube_r18 = real_head.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(17, 11).addBox(-1.75F, -0.25F, -0.4276F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.2467F, -1.0033F, -1.9333F, -0.0202F, 0.1787F, 0.1329F));

		PartDefinition cube_r19 = real_head.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(13, 33).mirror().addBox(-0.6447F, -0.7577F, -0.4564F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.9077F, -3.7949F, 0.1473F, 0.4755F, 0.0678F));

		PartDefinition cube_r20 = real_head.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(16, 17).addBox(-1.0F, -0.7423F, -1.7051F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.9077F, -3.7949F, 0.1309F, 0.0F, 0.0F));

		PartDefinition cube_r21 = real_head.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 33).addBox(-1.0F, -0.8456F, -1.5086F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.9077F, -3.7949F, 0.2182F, 0.0F, 0.0F));

		PartDefinition cube_r22 = real_head.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(27, 28).addBox(-1.0F, -0.7317F, -1.159F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5683F, -2.141F, 0.9599F, 0.0F, 0.0F));

		PartDefinition right_ear = real_head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(37, 20).addBox(-0.4683F, -1.9306F, -0.7641F, 2.0F, 2.5F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(39, 4).addBox(0.5518F, -4.1811F, -0.842F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4879F, -2.8533F, 0.2F, 0.0F, -0.3491F, 0.0F));

		PartDefinition cube_r23 = right_ear.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(38, 17).addBox(-0.3624F, -1.0292F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(1.1968F, 0.3467F, -0.352F, 0.0F, 0.0F, 0.5236F));

		PartDefinition cube_r24 = right_ear.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(39, 9).addBox(-1.4F, -1.6256F, -0.5F, 1.0F, 4.6F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(1.2231F, -2.1434F, -0.342F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r25 = right_ear.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(39, 14).addBox(-1.47F, -1.118F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(2.2007F, -3.15F, -0.332F, 0.0F, 0.0F, -0.1745F));

		PartDefinition left_ear = real_head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(37, 20).mirror().addBox(-1.5317F, -1.9306F, -0.7641F, 2.0F, 2.5F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(39, 4).mirror().addBox(-1.5518F, -4.1811F, -0.842F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.4879F, -2.8533F, 0.2F, 0.0F, 0.3491F, 0.0F));

		PartDefinition cube_r26 = left_ear.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(38, 17).mirror().addBox(-0.6376F, -1.0292F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(-1.1968F, 0.3467F, -0.352F, 0.0F, 0.0F, -0.5236F));

		PartDefinition cube_r27 = left_ear.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(39, 9).mirror().addBox(0.4F, -1.6256F, -0.5F, 1.0F, 4.6F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-1.2231F, -2.1434F, -0.342F, 0.0F, 0.0F, -0.3927F));

		PartDefinition cube_r28 = left_ear.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(39, 14).mirror().addBox(0.47F, -1.118F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-2.2007F, -3.15F, -0.332F, 0.0F, 0.0F, 0.1745F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}
