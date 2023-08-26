package doggytalents.client.entity.model;

import java.util.List;
import java.util.Optional;

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

public class DogFrontLegsSeperate extends SyncedAccessoryModel {

    public DogFrontLegsSeperate(ModelPart box) {
        super(box);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.legFrontRight = Optional.of(box.getChild("right_front_leg"));
        this.legFrontLeft = Optional.of(box.getChild("left_front_leg"));
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
    
}
