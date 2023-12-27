package doggytalents.client.entity.model;

import java.util.Optional;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DogArmorModel extends SyncedAccessoryModel {

    private ModelPart bootHindLeft;
    private ModelPart bootHindRight;
    private ModelPart bootFrontLeft;
    private ModelPart bootFrontRight;
    private ModelPart leggingHindLeft;
    private ModelPart leggingHindRight;
    private ModelPart leggingFrontLeft;
    private ModelPart leggingFrontRight;
    private ModelPart leggingCube;
    private ModelPart chestCube;

    public DogArmorModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.head = Optional.of(box.getChild("head"));
        this.realHead = Optional.of(head.get().getChild("real_head"));
        this.body = Optional.of(box.getChild("body"));
        this.mane = Optional.of(box.getChild("upper_body"));
        this.legBackLeft = Optional.of(box.getChild("left_hind_leg"));
        this.legBackRight = Optional.of(box.getChild("right_hind_leg"));
        this.legFrontLeft = Optional.of(box.getChild("left_front_leg"));
        this.legFrontRight = Optional.of(box.getChild("right_front_leg"));
        this.tail = Optional.of(box.getChild("tail"));
        this.realTail = Optional.of(tail.get().getChild("real_tail"));
        
        this.bootFrontLeft = this.legFrontLeft.get().getChild("boot");
        this.leggingFrontLeft = this.legFrontLeft.get().getChild("leg");
        this.bootFrontRight = this.legFrontRight.get().getChild("boot");
        this.leggingFrontRight = this.legFrontRight.get().getChild("leg");
        this.bootHindLeft = this.legBackLeft.get().getChild("boot");
        this.leggingHindLeft = this.legBackLeft.get().getChild("leg");
        this.bootHindRight = this.legBackRight.get().getChild("boot");
        this.leggingHindRight = this.legBackRight.get().getChild("leg");
        this.chestCube = this.body.get().getChild("chestplates");
        this.leggingCube = this.body.get().getChild("leggings");
    }

    private void resetVisible() {
        this.head.get().visible = false;
        this.realHead.get().visible = false;
        this.body.get().visible = true;
        this.mane.get().visible = false;
        this.legBackLeft.get().visible = true;
        this.legBackRight.get().visible = true;
        this.legFrontLeft.get().visible = true;
        this.legFrontRight.get().visible = true;
        this.tail.get().visible = false;
        this.realTail.get().visible = false;

        this.bootFrontLeft.visible = false;
        this.bootFrontRight.visible = false;
        this.bootHindLeft.visible = false;
        this.bootHindRight.visible = false;

        this.leggingFrontLeft.visible = false;
        this.leggingFrontRight.visible = false;
        this.leggingHindLeft.visible = false;
        this.leggingHindRight.visible = false;

        this.leggingCube.visible = false;
        this.chestCube.visible = false;
    }

    public void setHelmet() {
        resetVisible();
        this.head.get().visible = true;
        this.realHead.get().visible = true;
    }

    public void setChestplate() {
        resetVisible();
        this.mane.get().visible = true;
        this.chestCube.visible = true;
    }

    public void setLeggings() {
        resetVisible();
        this.leggingFrontLeft.visible = true;
        this.leggingFrontRight.visible = true;
        this.leggingHindLeft.visible = true;
        this.leggingHindRight.visible = true;

        this.leggingCube.visible = true;
        this.tail.get().visible = true;
        this.realTail.get().visible = true;
    }

    public void setBoot() {
        resetVisible();
        this.bootFrontLeft.visible = true;
        this.bootFrontRight.visible = true;
        this.bootHindLeft.visible = true;
        this.bootHindRight.visible = true;
    }

    public static LayerDefinition createLegacyLayer() {
		var meshdefinition = new MeshDefinition();
		var partdefinition = meshdefinition.getRoot();

        var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));
		var real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.6F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.ZERO);
		var body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));
        var leggings = body.addOrReplaceChild("leggings", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, 1.5F, -3.5F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.3F)), PartPose.ZERO);
		var chestplates = body.addOrReplaceChild("chestplates", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.15F)), PartPose.ZERO);
        var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));
		var right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-1.5F, 16.0F, 7.0F));
		var leg = right_hind_leg.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(30, 21).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.ZERO);
		var boot = right_hind_leg.addOrReplaceChild("boot", CubeListBuilder.create().texOffs(34, 20).addBox(-1.0F, 6.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.ZERO);
		var left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(1.5F, 16.0F, 7.0F));
		var leg2 = left_hind_leg.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(30, 21).addBox(-1F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.ZERO);
		var boot2 = left_hind_leg.addOrReplaceChild("boot", CubeListBuilder.create().texOffs(34, 20).mirror().addBox(2.0F, 6.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.35F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 0.0F));
		var right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-1.5F, 16.0F, -4.0F));
		var boot3 = right_front_leg.addOrReplaceChild("boot", CubeListBuilder.create().texOffs(34, 20).addBox(-1F, 6.0F, -12.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offset(0.0F, 0.0F, 11.0F));
		var leg3 = right_front_leg.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(34, 20).mirror().addBox(-4.0F, 0.0F, -12.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(3.0F, 0.0F, 11.0F));
		var left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(1.5F, 16.0F, -4.0F));
		var boot4 = left_front_leg.addOrReplaceChild("boot", CubeListBuilder.create().texOffs(34, 20).mirror().addBox(2.0F, 6.0F, -12.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.35F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 11.0F));
		var leg4 = left_front_leg.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(34, 20).addBox(-1F, 0.0F, -12.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 11.0F));
		var tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0F, 12.0F, 8.0F));
        tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(31, 7).addBox(-1F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.45F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
    
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		var tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 8.0F));
		var real_tail = tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(2, 26).mirror().addBox(-1.5F, 0.25F, -1.6F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
		var right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-1.5F, 16.0F, 7.0F));
		var leg = right_hind_leg.addOrReplaceChild("boot", CubeListBuilder.create().texOffs(0, 26).addBox(-1.5F, 5.25F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		var boot = right_hind_leg.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(40, 16).addBox(-2.25F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(-0.55F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		var left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(1.5F, 16.0F, 7.0F));
		var leg2 = left_hind_leg.addOrReplaceChild("boot", CubeListBuilder.create().texOffs(0, 26).mirror().addBox(1.5F, 5.25F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 0.0F));
		var boot2 = left_hind_leg.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(1.25F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(-0.55F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 0.0F));
		var right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-1.5F, 16.0F, -4.0F));
		var leg3 = right_front_leg.addOrReplaceChild("boot", CubeListBuilder.create().texOffs(0, 26).addBox(-1.5F, 5.25F, -12.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 11.0F));
		var boot3 = right_front_leg.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(40, 16).addBox(-2.25F, -0.5F, -13.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(-0.65F)), PartPose.offset(0.0F, 0.0F, 11.0F));
		var left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(1.5F, 16.0F, -4.0F));
		var leg4 = left_front_leg.addOrReplaceChild("boot", CubeListBuilder.create().texOffs(0, 26).mirror().addBox(1.5F, 5.25F, -12.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 11.0F));
		var boot4 = left_front_leg.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(1.25F, -0.5F, -13.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(-0.65F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 11.0F));
		var body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));
		var leggings = body.addOrReplaceChild("leggings", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		var HEADGEAR_r1 = leggings.addOrReplaceChild("HEADGEAR_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.05F, -16.8F, 8.0F, 5.0F, 8.0F, new CubeDeformation(-0.401F)), PartPose.offsetAndRotation(0.0F, -1.6F, 12.75F, 0.0F, 0.0F, 3.1416F));
		var chestplates = body.addOrReplaceChild("chestplates", CubeListBuilder.create().texOffs(17, 22).addBox(0.0F, 3.45F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.501F))
		.texOffs(17, 22).mirror().addBox(-3.01F, 3.45F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
		.texOffs(17, 22).mirror().addBox(-3.0F, 1.45F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(17, 22).addBox(0.0F, 1.45F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		var mane_rotation_r1 = chestplates.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(17, 22).addBox(0.02F, -1.5F, -0.99F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.2001F))
		.texOffs(17, 22).mirror().addBox(-3.01F, -1.5F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.2001F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.95F, 0.0F, -3.1416F, 0.0F, 0.0F));
		var mane_rotation_r2 = chestplates.addOrReplaceChild("mane_rotation_r2", CubeListBuilder.create().texOffs(17, 22).mirror().addBox(-3.0F, 0.125F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.201F)).mirror(false)
		.texOffs(17, 22).addBox(0.0F, 0.125F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.201F)), PartPose.offsetAndRotation(0.0F, 1.575F, -1.0F, -3.1416F, 0.0F, 0.0F));
		var mane_rotation_r3 = chestplates.addOrReplaceChild("mane_rotation_r3", CubeListBuilder.create().texOffs(17, 22).addBox(0.0F, 0.125F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.5001F)), PartPose.offsetAndRotation(0.0F, 3.325F, -1.0F, 3.1416F, 0.0F, 3.1416F));
		var mane_rotation_r4 = chestplates.addOrReplaceChild("mane_rotation_r4", CubeListBuilder.create().texOffs(17, 22).mirror().addBox(-3.0F, 0.125F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.502F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.325F, -1.0F, 3.1416F, 0.0F, -3.1416F));
		var mane_rotation_r5 = chestplates.addOrReplaceChild("mane_rotation_r5", CubeListBuilder.create().texOffs(17, 22).mirror().addBox(-3.01F, -1.5F, -1.99F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(17, 22).addBox(0.0F, -1.5F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, -0.05F, 1.0F, 0.0F, 0.0F, -3.1416F));
		var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));
		var mane_rotation_r6 = upper_body.addOrReplaceChild("mane_rotation_r6", CubeListBuilder.create().texOffs(17, 23).addBox(-4.0F, 0.1F, -1.5F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.1F, -1.5F, 0.0F, 0.0F, 0.0F));
		var mane_rotation_r7 = upper_body.addOrReplaceChild("mane_rotation_r7", CubeListBuilder.create().texOffs(28, 22).addBox(3.01F, -1.29F, 0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1001F)), PartPose.offsetAndRotation(0.0F, 1.5F, 1.5F, -3.1416F, 0.0F, 3.1416F));
		var mane_rotation_r8 = upper_body.addOrReplaceChild("mane_rotation_r8", CubeListBuilder.create().texOffs(28, 22).mirror().addBox(-4.01F, -1.3F, 0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1001F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.5F, 1.5F, -3.1416F, 0.0F, -3.1416F));
		var HEADGEAR_r2 = upper_body.addOrReplaceChild("HEADGEAR_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.55F, -4.1F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.16F)), PartPose.offsetAndRotation(0.0F, 0.7F, 0.05F, 0.0F, 0.0F, 0.0F));
		var mane_rotation_r9 = upper_body.addOrReplaceChild("mane_rotation_r9", CubeListBuilder.create().texOffs(17, 23).addBox(-3.98F, -1.29F, -1.5F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 1.5F, 2.5F, -3.1416F, 0.0F, 3.1416F));
		var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));
		var real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(2, 2).addBox(-4.0F, -4.85F, -3.25F, 8.0F, 8.0F, 6.0F, new CubeDeformation(-0.9F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

    
}
