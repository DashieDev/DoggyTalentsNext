package doggytalents.client.entity.model;

import java.util.Optional;

import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class PropellerHatModel extends SyncedAccessoryModel {

    public ModelPart propellar;
    public ModelPart spinna;

    public PropellerHatModel(ModelPart root) {
        super(root);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.head = Optional.of(box.getChild("head"));
        this.realHead = Optional.of(head.get().getChild("real_head"));
        this.propellar = this.realHead.get().getChild("propellar");
        this.spinna = this.propellar.getChild("spin");
    }

    @Override
    public void setupAnim(Dog entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.spinna.yRot = 24*ageInTicks % 360 * Mth.DEG_TO_RAD;
    }
    
    public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, -7.0F));

		PartDefinition real_head = head.addOrReplaceChild("real_head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition propellar = real_head.addOrReplaceChild("propellar", CubeListBuilder.create().texOffs(2, 36).addBox(-2.25F, -14.75F, -9.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(0, 46).addBox(-3.5F, -13.5F, -11.0F, 7.0F, 1.0F, 4.0F, new CubeDeformation(-0.05F))
		.texOffs(2, 41).addBox(-3.25F, -14.05F, -8.85F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(2, 41).mirror().addBox(1.25F, -14.025F, -8.85F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false)
		.texOffs(2, 41).mirror().addBox(1.25F, -14.05F, -6.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false)
		.texOffs(2, 41).addBox(-3.25F, -14.05F, -6.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(11, 36).addBox(0.25F, -14.75F, -9.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(2, 36).addBox(0.25F, -14.7F, -6.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(11, 36).addBox(-2.25F, -14.75F, -6.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 10.5F, 7.0F));

		PartDefinition spin = propellar.addOrReplaceChild("spin", CubeListBuilder.create().texOffs(20, 43).addBox(-2.9667F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F))
		.texOffs(20, 36).addBox(-0.9667F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F))
		.texOffs(20, 43).mirror().addBox(-1.0667F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.8F)).mirror(false), PartPose.offset(0.0167F, -16.4F, -6.8F));

		PartDefinition bone2 = propellar.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(20, 36).addBox(-0.75F, -15.75F, -9.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.8F)), PartPose.offset(-0.2F, -1.4F, 1.2F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
