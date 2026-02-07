package doggytalents.client.entity.model.accessories;

import java.util.Optional;

import doggytalents.client.entity.model.SyncedAccessoryModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class HeadBowModel extends SyncedAccessoryModel {

    public HeadBowModel(ModelPart part) {
        super(part);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.head = Optional.of(box.getChild("head"));
        this.realHead = Optional.of(head.get().getChild("real_head"));
    }

    public static LayerDefinition createBowtieLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot ();
    	var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0F, 13.5F, -7F));
		var real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.ZERO);
        real_head.addOrReplaceChild("bowtie", CubeListBuilder.create().texOffs(3, 1).addBox(2.9805F, -7.2389F, -4.75F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		    .texOffs(1, 1).addBox(1.9805F, -7.7389F, -5.25F, 1.0F, 2.0F, 2.0F, CubeDeformation.NONE)
		    .texOffs(1, 1).addBox(3.9805F, -7.7389F, -5.25F, 1.0F, 2.0F, 2.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-3.2F, 4.75F, 2.45F, 0.0F, 0.0F, -0.3491F));
		return LayerDefinition.create(meshdefinition, 16, 16);
    }
}

