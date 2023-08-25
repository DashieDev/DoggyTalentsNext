package doggytalents.client.entity.model;

import java.util.List;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DogFrontLegsSeperate extends ListModel<Dog> {

    public ModelPart root;
    public ModelPart legFrontRight;
    public ModelPart legFrontLeft;

    public DogFrontLegsSeperate(ModelPart box) {
        this.root = box;
        this.legFrontRight = box.getChild("right_front_leg");
        this.legFrontLeft = box.getChild("left_front_leg");
    }

    public static LayerDefinition createBodyLayer() {
        return createBodyLayerInternal(CubeDeformation.NONE);
    }

    private static LayerDefinition createBodyLayerInternal(CubeDeformation scale) {
        MeshDefinition var0 = new MeshDefinition();
        PartDefinition var1 = var0.getRoot();
        CubeListBuilder var4_1 = CubeListBuilder.create().texOffs(56, 0).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, scale);
        var1.addOrReplaceChild("right_front_leg", var4_1, PartPose.offset(-2.5F, 16.0F, -4.0F));
        var1.addOrReplaceChild("left_front_leg", var4_1, PartPose.offset(0.5F, 16.0F, -4.0F));
        return LayerDefinition.create(var0, 64, 32);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return List.of(this.root);
    }

    public void syncFromDogModel(DogModel<? extends AbstractDog> dogModel) {
        this.legFrontLeft.x = dogModel.legFrontLeft.x;
        this.legFrontRight.x = dogModel.legFrontRight.x;
        this.legFrontLeft.y = dogModel.legFrontLeft.y;
        this.legFrontRight.y = dogModel.legFrontRight.y;
        this.legFrontLeft.z = dogModel.legFrontLeft.z;
        this.legFrontRight.z = dogModel.legFrontRight.z;
        this.legFrontLeft.xRot = dogModel.legFrontLeft.xRot;
        this.legFrontRight.xRot = dogModel.legFrontRight.xRot;
        this.legFrontLeft.yRot = dogModel.legFrontLeft.yRot;
        this.legFrontRight.yRot = dogModel.legFrontRight.yRot;
        this.legFrontLeft.zRot = dogModel.legFrontLeft.zRot;
        this.legFrontRight.zRot = dogModel.legFrontRight.zRot;
        this.root.copyFrom(dogModel.root);
    }

    @Override
    public void setupAnim(Dog p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_,
            float p_102623_) {
    }
    
}
