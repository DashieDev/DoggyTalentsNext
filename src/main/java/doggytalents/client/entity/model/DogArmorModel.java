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
    
    public static LayerDefinition createBodyLayer() {
		var meshdefinition = new MeshDefinition();
		var partdefinition = meshdefinition.getRoot();

        var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));
		var real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.6F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.ZERO);
		var body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));
        var leggings = body.addOrReplaceChild("leggings", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, 1.5F, -3.5F, 8.0F, 6.0F, 7.0F, new CubeDeformation(-0.3F)), PartPose.ZERO);
		var chestplates = body.addOrReplaceChild("chestplates", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.15F)), PartPose.ZERO);
        var upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));
		var right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-2.5F, 16.0F, 7.0F));
		var leg = right_hind_leg.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(30, 21).mirror().addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.ZERO);
		var boot = right_hind_leg.addOrReplaceChild("boot", CubeListBuilder.create().texOffs(34, 20).addBox(0.0F, 6.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.ZERO);
		var left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(0.5F, 16.0F, 7.0F));
		var leg2 = left_hind_leg.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(30, 21).addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.ZERO);
		var boot2 = left_hind_leg.addOrReplaceChild("boot", CubeListBuilder.create().texOffs(34, 20).mirror().addBox(3.0F, 6.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.35F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 0.0F));
		var right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-2.5F, 16.0F, -4.0F));
		var boot3 = right_front_leg.addOrReplaceChild("boot", CubeListBuilder.create().texOffs(34, 20).addBox(0.0F, 6.0F, -12.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offset(0.0F, 0.0F, 11.0F));
		var leg3 = right_front_leg.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(34, 20).mirror().addBox(-3.0F, 0.0F, -12.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(3.0F, 0.0F, 11.0F));
		var left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(0.5F, 16.0F, -4.0F));
		var boot4 = left_front_leg.addOrReplaceChild("boot", CubeListBuilder.create().texOffs(34, 20).mirror().addBox(3.0F, 6.0F, -12.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.35F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 11.0F));
		var leg4 = left_front_leg.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(34, 20).addBox(0.0F, 0.0F, -12.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 11.0F));
		var tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-1.0F, 12.0F, 8.0F));
        tail.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(31, 7).addBox(0.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.45F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

    
}
