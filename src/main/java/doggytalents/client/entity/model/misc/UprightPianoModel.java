package doggytalents.client.entity.model.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import doggytalents.common.entity.misc.Piano;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class UprightPianoModel extends EntityModel<Piano> {

    public ModelPart piano;
	

	public UprightPianoModel(ModelPart root) {
		this.piano = root.getChild("piano");
	}

	public static LayerDefinition createPianoLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition piano = partdefinition.addOrReplaceChild("piano", CubeListBuilder.create().texOffs(104, 59).addBox(13.75F, -10.5F, -11.75F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.3F))
		.texOffs(0, 76).addBox(13.75F, -4.0F, -12.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(104, 26).addBox(13.75F, -2.0F, -14.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(93, 105).addBox(-31.75F, -12.25F, -13.75F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.1F))
		.texOffs(0, 0).addBox(-32.0F, -18.25F, -8.75F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(100, 0).addBox(-32.0F, -16.25F, -10.75F, 2.0F, 4.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(24, 124).addBox(-32.0F, -14.25F, -12.75F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F))
		.texOffs(104, 59).addBox(-30.9F, -12.5F, -11.5F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.1F))
		.texOffs(0, 35).addBox(14.0F, -18.25F, -8.75F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(34, 124).addBox(14.0F, -14.25F, -12.75F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F))
		.texOffs(106, 105).addBox(14.0F, -16.25F, -10.75F, 2.0F, 4.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(22, 107).addBox(13.75F, -12.25F, -13.75F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.1F))
		.texOffs(44, 107).addBox(14.5F, -20.0F, -6.25F, 1.0F, 7.0F, 7.0F, new CubeDeformation(0.1F))
		.texOffs(0, 72).addBox(-31.1F, -2.25F, -3.75F, 46.0F, 1.0F, 8.0F, new CubeDeformation(1.95F))
		.texOffs(0, 90).addBox(-31.1F, -28.25F, -3.75F, 46.0F, 1.0F, 8.0F, new CubeDeformation(1.7F))
		.texOffs(0, 81).addBox(-31.1F, -4.25F, -3.75F, 46.0F, 1.0F, 8.0F, new CubeDeformation(1.2F))
		.texOffs(94, 50).addBox(-31.1F, -25.25F, -3.75F, 46.0F, 1.0F, 8.0F, new CubeDeformation(1.2F))
		.texOffs(0, 0).addBox(-31.1F, -23.75F, -3.75F, 46.0F, 23.0F, 8.0F, new CubeDeformation(0.2F))
		.texOffs(96, 99).addBox(-31.1F, -19.0F, -5.75F, 46.0F, 1.0F, 5.0F, new CubeDeformation(0.1F))
		.texOffs(0, 35).addBox(-31.9F, -18.5F, -7.5F, 47.8F, 7.0F, 8.0F, new CubeDeformation(0.1F))
		.texOffs(0, 99).addBox(-29.7F, -12.25F, -12.25F, 45.0F, 2.0F, 6.0F, new CubeDeformation(-0.3F))
		.texOffs(0, 50).addBox(-27.9F, -11.5F, -6.5F, 42.0F, 12.0F, 10.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 107).addBox(12.9F, -12.5F, -11.5F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.1F))
		.texOffs(0, 50).addBox(-31.75F, -10.5F, -11.75F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.3F))
		.texOffs(0, 72).addBox(-31.75F, -4.0F, -12.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(100, 81).addBox(-31.75F, -2.0F, -14.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, 3.5F));

		PartDefinition cube_r1 = piano.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(100, 92).addBox(-1.0F, 0.5F, -2.3F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F))
		.texOffs(104, 37).addBox(44.5F, 0.5F, -2.3F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-30.75F, -10.5F, -10.5858F, -0.7854F, 0.0F, 0.0F));

		PartDefinition key = piano.addOrReplaceChild("key", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.75F));

		PartDefinition white = key.addOrReplaceChild("white", CubeListBuilder.create().texOffs(122, 13).addBox(-27.25F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(122, 7).addBox(-26.45F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(13, 107).addBox(-28.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(35, 107).addBox(-28.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(117, 121).addBox(-24.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(122, 1).addBox(-25.65F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(120, 80).addBox(-21.65F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(120, 105).addBox(-22.45F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(110, 120).addBox(-23.25F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(43, 121).addBox(-24.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(36, 118).addBox(-15.25F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(122, 93).addBox(-16.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(98, 122).addBox(-16.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(119, 37).addBox(-17.65F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(119, 43).addBox(-20.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(55, 119).addBox(-20.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(67, 119).addBox(-18.45F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(79, 119).addBox(-19.25F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(24, 118).addBox(-14.45F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(12, 118).addBox(-13.65F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 118).addBox(-12.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(117, 115).addBox(-12.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(117, 59).addBox(-8.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(117, 24).addBox(-9.65F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(103, 116).addBox(-10.45F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(91, 116).addBox(-11.25F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(115, 92).addBox(-2.45F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(122, 86).addBox(-3.25F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(86, 122).addBox(-4.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(115, 18).addBox(-4.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(115, 12).addBox(-8.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(115, 6).addBox(-7.25F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(115, 0).addBox(-5.65F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(110, 114).addBox(-6.45F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(113, 108).addBox(-1.65F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(84, 113).addBox(-0.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(113, 79).addBox(0.75F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(72, 113).addBox(-0.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(60, 113).addBox(3.95F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(112, 44).addBox(3.15F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(112, 38).addBox(2.35F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(108, 93).addBox(1.55F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(108, 15).addBox(10.35F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(124, 19).addBox(9.55F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(53, 107).addBox(11.95F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 124).addBox(11.15F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(12, 124).addBox(8.75F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(108, 9).addBox(7.95F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(108, 3).addBox(4.75F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(89, 107).addBox(5.55F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(77, 107).addBox(7.15F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(65, 107).addBox(6.35F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition black = key.addOrReplaceChild("black", CubeListBuilder.create().texOffs(83, 128).addBox(-28.45F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(112, 127).addBox(-26.85F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(127, 105).addBox(-26.05F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(127, 79).addBox(-24.45F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(40, 127).addBox(-23.65F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(125, 126).addBox(-22.85F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(126, 36).addBox(-17.25F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(126, 43).addBox(-20.45F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(126, 64).addBox(-18.85F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(77, 126).addBox(-18.05F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(106, 126).addBox(-21.25F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(125, 26).addBox(-11.65F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(51, 125).addBox(-12.45F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(61, 125).addBox(-13.25F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(71, 125).addBox(-14.85F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(126, 31).addBox(-15.65F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(67, 113).addBox(-10.075F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(79, 113).addBox(-9.275F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(113, 85).addBox(-7.675F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(98, 116).addBox(-6.875F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(117, 30).addBox(-6.075F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(7, 118).addBox(-0.475F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(19, 118).addBox(-3.675F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(31, 118).addBox(-2.075F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(62, 119).addBox(-1.275F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(74, 119).addBox(-4.475F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(93, 122).addBox(5.125F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(7, 124).addBox(4.325F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(124, 59).addBox(3.525F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(124, 111).addBox(1.925F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(124, 121).addBox(1.125F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(60, 107).addBox(6.725F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(20, 107).addBox(7.525F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(72, 107).addBox(9.125F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(84, 107).addBox(9.925F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(108, 21).addBox(10.725F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition sheet_stand = piano.addOrReplaceChild("sheet_stand", CubeListBuilder.create().texOffs(62, 23).addBox(-11.0F, 3.0252F, -2.9269F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.15F))
		.texOffs(62, 14).addBox(-10.0F, -2.7248F, -2.9269F, 20.0F, 6.0F, 2.0F, new CubeDeformation(-0.5F))
		.texOffs(56, 17).addBox(-11.0F, -4.4748F, -3.1769F, 22.0F, 3.0F, 2.0F, new CubeDeformation(-0.6F))
		.texOffs(78, 15).addBox(7.0F, -5.4748F, -3.1769F, 5.0F, 4.0F, 2.0F, new CubeDeformation(-0.7F))
		.texOffs(25, 40).addBox(-12.0F, -5.4748F, -3.1769F, 5.0F, 4.0F, 2.0F, new CubeDeformation(-0.7F))
		.texOffs(0, 12).addBox(-1.0F, 0.5252F, -0.9269F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(-8.0F, -22.2752F, -4.0731F, -0.1745F, 0.0F, 0.0F));

		PartDefinition pedal = piano.addOrReplaceChild("pedal", CubeListBuilder.create().texOffs(89, 128).addBox(-9.0F, -1.25F, -8.75F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r2 = pedal.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(124, 116).addBox(-1.0F, -0.75F, -1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-11.0F, -0.5F, -7.75F, 0.0F, 0.3927F, 0.0F));

		PartDefinition cube_r3 = pedal.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(99, 128).addBox(-1.0F, -0.75F, -1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-5.0F, -0.5F, -7.75F, 0.0F, -0.3927F, 0.0F));

		PartDefinition lid = piano.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(102, 70).addBox(-22.9F, -5.5F, -0.8F, 44.8F, 6.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(103, 71).addBox(-22.9F, -6.1F, -1.55F, 44.8F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, -12.0F, -6.75F, 0.2182F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Piano entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color_overlay) {
		piano.render(poseStack, vertexConsumer, packedLight, packedOverlay, color_overlay);
	}
}
