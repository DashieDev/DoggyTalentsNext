package doggytalents.client.entity.model;

import net.minecraft.client.model.geom.ModelPart;
import com.google.common.collect.ImmutableList;
import doggytalents.api.inferface.AbstractDog;
import net.minecraft.client.model.ColorableAgeableListModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class DogArmorModel<T extends AbstractDog> extends DogModel<T> {

    public ModelPart leggingHindRight;
    public ModelPart leggingHindLeft;
    public ModelPart leggingFrontRight;
    public ModelPart leggingFrontLeft;
    public ModelPart bootHindRight;
    public ModelPart bootHindLeft;
    public ModelPart bootFrontRight;
    public ModelPart bootFrontLeft;

    public DogArmorModel(ModelPart box) {
        super(box);
        leggingHindRight = this.legBackRight.getChild("right_hind_leggings");
        leggingHindLeft = this.legBackLeft.getChild("left_hind_leggings");
        leggingFrontRight = this.legFrontRight.getChild("right_front_leggings");
        leggingFrontLeft = this.legFrontLeft.getChild("left_front_leggings");
        bootHindRight = this.legBackRight.getChild("right_hind_boots");
        bootHindLeft = this.legBackLeft.getChild("left_hind_boots");
        bootFrontRight = this.legFrontRight.getChild("right_front_boots");
        bootFrontLeft = this.legFrontLeft.getChild("left_front_boots");
        this.legBackRight.visible = true;
        this.legBackLeft.visible = true;
        this.legFrontRight.visible = true;
        this.legFrontLeft.visible = true;


    }
    
    public static LayerDefinition createArmorLayer() {
        return createArmorLayerInternal(new CubeDeformation(0.4F, 0.4F, 0.4F));
    }

    
    private static LayerDefinition createArmorLayerInternal(CubeDeformation scale) {
        MeshDefinition var0 = new MeshDefinition();
        PartDefinition var1 = var0.getRoot();
        float var2 = 13.5F;
        PartDefinition var3 = var1.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F));
        var3.addOrReplaceChild("real_head", CubeListBuilder.create()
                // Head
                .texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, scale)
                // Ears Normal
                .texOffs(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, scale)
                .texOffs(16, 14).addBox(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, scale)
                // Nose
                .texOffs(0, 10).addBox(-0.5F, 0.0F, -5.0F, 3.0F, 3.0F, 4.0F, scale)
                // Ears Boni
                .texOffs(52, 0).addBox(-3.0F, -3.0F, -1.5F, 1, 5, 3, scale)
                .texOffs(52, 0).addBox(4.0F, -3.0F, -1.5F, 1, 5, 3, scale)
                // Ears Small
                .texOffs(18, 0).addBox(-2.8F, -3.5F, -1.0F, 2, 1, 2, scale)
                .texOffs(18, 0).addBox(2.8F, -3.5F, -1.0F, 2, 1, 2, scale)
        , PartPose.ZERO);
        var1.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, scale)
        , PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
        
        var1.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, scale), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
        
        CubeListBuilder var4 = CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, scale);

        var leg1 = var1.addOrReplaceChild("right_hind_leg", var4, PartPose.offset(-2.5F, 16.0F, 7.0F));
        
        leg1.addOrReplaceChild("right_hind_leggings", CubeListBuilder.create().texOffs(30, 21).addBox(3.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

		leg1.addOrReplaceChild("right_hind_boots", CubeListBuilder.create().texOffs(34, 20).addBox(3.0F, 6.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offset(-3.0F, 0.0F, 0.0F));
        
        var leg2 = var1.addOrReplaceChild("left_hind_leg", var4, PartPose.offset(0.5F, 16.0F, 7.0F));
        
        leg2.addOrReplaceChild("left_hind_leggings", CubeListBuilder.create().texOffs(30, 21).addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		leg2.addOrReplaceChild("left_hind_boots", CubeListBuilder.create().texOffs(34, 20).addBox(0.0F, 6.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offset(0.0F, 0.0F, 0.0F));       

        var leg3 = var1.addOrReplaceChild("right_front_leg", var4, PartPose.offset(-2.5F, 16.0F, -4.0F));
        
		leg3.addOrReplaceChild("right_front_leggings", CubeListBuilder.create().texOffs(30, 21).addBox(3.0F, 0.0F, -12.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(-3.0F, 0.0F, 11.0F));

		leg3.addOrReplaceChild("right_front_boots", CubeListBuilder.create().texOffs(34, 20).addBox(3.0F, 6.0F, -12.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offset(-3.0F, 0.0F, 11.0F));

        var leg4 = var1.addOrReplaceChild("left_front_leg", var4, PartPose.offset(0.5F, 16.0F, -4.0F));
        
		leg4.addOrReplaceChild("left_front_leggings", CubeListBuilder.create().texOffs(30, 21).addBox(0.0F, 0.0F, -12.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 11.0F));

		leg4.addOrReplaceChild("left_front_boots", CubeListBuilder.create().texOffs(34, 20).addBox(0.0F, 6.0F, -12.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offset(0.0F, 0.0F, 11.0F));

        PartDefinition var5 = var1.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, 0.62831855F, 0.0F, 0.0F));
        
        var5.addOrReplaceChild("real_tail", CubeListBuilder.create()
                .texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, scale)
        , PartPose.ZERO);
        var5.addOrReplaceChild("real_tail_2", CubeListBuilder.create()
                .texOffs(45, 0).addBox(0.0F, 0.0F, 0.0F, 2, 3, 1, scale)
        , PartPose.offset(0.0F, -2.0F, 0.0F));
        var5.addOrReplaceChild("real_tail_bushy", CubeListBuilder.create()
                .texOffs(43, 19).addBox(-1.0F, 0F, -2F, 3, 10, 3, scale)
                , PartPose.ZERO);
        return LayerDefinition.create(var0, 64, 32);
    }


    public void setVisible(boolean visible) {
        this.head.visible = visible;
        this.body.visible = visible;
        this.tail.visible = visible;
        this.mane.visible = visible;
        this.leggingHindRight.visible = visible;
        this.leggingHindLeft.visible = visible;
        this.leggingFrontRight.visible = visible;
        this.leggingFrontLeft.visible = visible;
        this.bootHindRight.visible = visible;
        this.bootHindLeft.visible = visible;
        this.bootFrontRight.visible = visible;
        this.bootFrontLeft.visible = visible;
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft, this.tail, this.mane
        );
    }
}
