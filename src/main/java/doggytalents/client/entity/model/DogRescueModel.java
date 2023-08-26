package doggytalents.client.entity.model;

import java.util.Optional;

import com.google.common.collect.ImmutableList;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DogRescueModel extends SyncedAccessoryModel {

    public DogRescueModel(ModelPart box) {
        super(box);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.mane = Optional.of(box.getChild("rescue_box"));
    }

    public static LayerDefinition createRescueBoxLayer() {
        MeshDefinition var0 = new MeshDefinition();
        PartDefinition var1 = var0.getRoot();

        var1.addOrReplaceChild("rescue_box", CubeListBuilder.create().texOffs(0, 0).addBox(-1F, -4F, -4.5F, 4, 2, 2), PartPose.offsetAndRotation(-1F, 14F, -3F, (float) (Math.PI / 2), 0F, 0F));

        return LayerDefinition.create(var0, 64, 32);
    }
}
