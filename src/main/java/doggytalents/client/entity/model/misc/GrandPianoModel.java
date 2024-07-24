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

public class GrandPianoModel extends EntityModel<Piano> {

    public ModelPart piano;
	public ModelPart fallBoard;
	public ModelPart fallBoardStick;

	public GrandPianoModel(ModelPart root) {
		this.piano = root.getChild("piano");
		this.fallBoard = piano.getChild("lid");
		this.fallBoardStick = fallBoard.getChild("lid_prop");
	}

	public static LayerDefinition creatPianoLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition piano = partdefinition.addOrReplaceChild("piano", CubeListBuilder.create().texOffs(101, 132).addBox(12.9F, -12.5F, -11.5F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.1F))
		.texOffs(0, 0).addBox(-29.9F, -11.5F, -12.5F, 46.0F, 2.0F, 25.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 209).addBox(-29.9F, -12.5F, -0.5F, 46.0F, 3.0F, 13.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 64).addBox(-29.7F, -12.25F, -12.25F, 45.0F, 2.0F, 6.0F, new CubeDeformation(-0.3F))
		.texOffs(56, 124).addBox(-9.0F, -18.25F, 12.5F, 11.0F, 7.0F, 4.4F, new CubeDeformation(0.0F))
		.texOffs(29, 121).addBox(-9.0F, -19.0F, 16.5F, 2.0F, 8.0F, 11.6F, new CubeDeformation(-0.1F))
		.texOffs(106, 67).addBox(-32.0F, -18.75F, 25.85F, 24.0F, 8.0F, 2.4F, new CubeDeformation(-0.1F))
		.texOffs(45, 105).addBox(-30.0F, -11.5F, 11.5F, 22.0F, 2.0F, 16.4F, new CubeDeformation(0.1F))
		.texOffs(110, 28).addBox(-18.0F, -13.5F, 15.5F, 10.0F, 2.0F, 11.4F, new CubeDeformation(0.1F))
		.texOffs(114, 32).addBox(-25.25F, -13.5F, 20.5F, 10.0F, 2.0F, 7.4F, new CubeDeformation(0.0F))
		.texOffs(133, 132).addBox(-14.0F, -13.5F, 10.5F, 6.0F, 2.0F, 6.4F, new CubeDeformation(0.0F))
		.texOffs(34, 86).addBox(-10.0F, -13.5F, 7.5F, 23.0F, 2.0F, 5.0F, new CubeDeformation(-0.1F))
		.texOffs(34, 103).addBox(2.0F, -18.25F, 10.5F, 11.0F, 7.0F, 2.4F, new CubeDeformation(0.1F))
		.texOffs(0, 48).addBox(-31.9F, -18.5F, -6.75F, 47.8F, 7.0F, 8.0F, new CubeDeformation(0.1F))
		.texOffs(0, 73).addBox(-31.1F, -19.0F, -5.75F, 46.0F, 1.0F, 5.0F, new CubeDeformation(0.1F))
		.texOffs(0, 80).addBox(-31.1F, -19.75F, -3.75F, 46.0F, 1.0F, 4.0F, new CubeDeformation(0.2F))
		.texOffs(0, 85).addBox(-32.0F, -18.5F, 0.25F, 4.0F, 7.0F, 28.0F, new CubeDeformation(0.2F))
		.texOffs(70, 64).addBox(-31.1F, -20.0F, -6.25F, 1.0F, 7.0F, 33.0F, new CubeDeformation(0.1F))
		.texOffs(103, 105).addBox(14.5F, -20.0F, -6.25F, 1.0F, 7.0F, 19.0F, new CubeDeformation(0.1F))
		.texOffs(34, 94).addBox(1.5F, -20.0F, 11.75F, 13.0F, 7.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(0, 86).addBox(0.5F, -20.0F, 11.75F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(17, 121).addBox(-8.5F, -20.0F, 14.75F, 10.0F, 7.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(0, 86).addBox(-8.5F, -20.0F, 14.75F, 1.0F, 7.0F, 12.0F, new CubeDeformation(0.1F))
		.texOffs(106, 78).addBox(-30.5F, -20.0F, 25.75F, 23.0F, 7.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(0, 121).addBox(13.0F, -18.5F, 0.5F, 2.9F, 7.0F, 12.0F, new CubeDeformation(0.2F))
		.texOffs(125, 105).addBox(13.75F, -12.25F, -13.75F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.1F))
		.texOffs(106, 105).addBox(14.0F, -16.25F, -10.75F, 2.0F, 4.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(14, 12).addBox(14.0F, -14.25F, -12.75F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F))
		.texOffs(163, 0).addBox(14.0F, -18.25F, -8.75F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(42, 175).addBox(-23.75F, -10.5F, 20.75F, 9.0F, 2.0F, 4.0F, new CubeDeformation(0.85F))
		.texOffs(0, 0).addBox(-20.25F, -9.5F, 22.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(21, 141).addBox(-20.25F, -4.0F, 22.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(0, 12).addBox(-30.9F, -12.5F, -11.5F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.1F))
		.texOffs(14, 0).addBox(-32.0F, -14.25F, -12.75F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 28).addBox(-32.0F, -16.25F, -10.75F, 2.0F, 4.0F, 4.0F, new CubeDeformation(-0.1F))
		.texOffs(162, 31).addBox(-32.0F, -18.25F, -8.75F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 0).addBox(-31.75F, -12.25F, -13.75F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.1F))
		.texOffs(15, 86).addBox(-29.75F, -10.5F, -10.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(101, 136).addBox(-29.75F, -4.0F, -10.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(72, 136).addBox(-29.75F, -2.0F, -12.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(72, 136).mirror().addBox(11.75F, -2.0F, -12.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(101, 136).mirror().addBox(11.75F, -4.0F, -10.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false)
		.texOffs(15, 86).mirror().addBox(11.75F, -10.5F, -10.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(59, 136).addBox(-20.25F, -2.0F, 21.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		PartDefinition cube_r1 = piano.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(87, 90).mirror().addBox(-1.0F, 0.5F, -2.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(87, 124).mirror().addBox(-1.0F, -2.25F, 0.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false)
		.texOffs(87, 124).addBox(-42.5F, -2.25F, 0.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(87, 90).addBox(-42.5F, 0.5F, -2.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(12.75F, -10.5F, -8.5858F, -0.7854F, 0.0F, 0.0F));

		PartDefinition lid = piano.addOrReplaceChild("lid", CubeListBuilder.create(), PartPose.offset(-32.95F, -19.5F, 17.1F));

		PartDefinition cube_r2 = lid.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(106, 52).addBox(1.2F, -1.0F, 2.4F, 25.0F, 2.0F, 12.4F, new CubeDeformation(0.1F))
		.texOffs(0, 28).addBox(1.3F, -1.0F, -13.1F, 46.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -4.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition lid_prop = lid.addOrReplaceChild("lid_prop", CubeListBuilder.create(), PartPose.offset(39.4F, -16.0F, -9.0F));

		PartDefinition cube_r3 = lid_prop.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(124, 132).addBox(41.45F, -0.5F, -6.1F, 2.0F, 23.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-39.4F, 16.0F, 5.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition key = piano.addOrReplaceChild("key", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition white = key.addOrReplaceChild("white", CubeListBuilder.create().texOffs(154, 35).addBox(-27.25F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(105, 153).addBox(-26.45F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(152, 128).addBox(-28.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(152, 121).addBox(-28.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(81, 152).addBox(-24.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(97, 151).addBox(-25.65F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(141, 150).addBox(-21.65F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(150, 88).addBox(-22.45F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(73, 150).addBox(-23.25F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(60, 150).addBox(-24.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(47, 150).addBox(-15.25F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(149, 148).addBox(-16.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(149, 141).addBox(-16.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(133, 148).addBox(-17.65F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(39, 148).addBox(-20.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(148, 28).addBox(-20.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(26, 148).addBox(-18.45F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(148, 21).addBox(-19.25F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(148, 14).addBox(-14.45F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(13, 148).addBox(-13.65F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 148).addBox(-12.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(147, 102).addBox(-12.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(147, 95).addBox(-8.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(89, 147).addBox(-9.65F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(146, 114).addBox(-10.45F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(109, 146).addBox(-11.25F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(146, 38).addBox(-2.45F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(81, 145).addBox(-3.25F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(144, 124).addBox(-4.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(101, 144).addBox(-4.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(144, 45).addBox(-8.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(141, 143).addBox(-7.25F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(73, 143).addBox(-5.65F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(60, 143).addBox(-6.45F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(47, 143).addBox(-1.65F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(133, 141).addBox(-0.85F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(39, 141).addBox(0.75F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(26, 141).addBox(-0.05F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(13, 141).addBox(3.95F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(0, 141).addBox(3.15F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(93, 140).addBox(2.35F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(140, 31).addBox(1.55F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(140, 24).addBox(10.35F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(140, 17).addBox(9.55F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(139, 107).addBox(11.95F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(139, 100).addBox(11.15F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(139, 93).addBox(8.75F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(138, 117).addBox(7.95F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(85, 138).addBox(4.75F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(77, 136).addBox(5.55F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(64, 136).addBox(7.15F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F))
		.texOffs(47, 143).addBox(6.35F, -12.5F, -11.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.15F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition black = key.addOrReplaceChild("black", CubeListBuilder.create().texOffs(0, 162).addBox(-28.45F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(143, 161).addBox(-26.85F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(161, 124).addBox(-26.05F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(161, 105).addBox(-24.45F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(104, 161).addBox(-23.65F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(160, 116).addBox(-22.85F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(121, 159).addBox(-17.25F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(159, 91).addBox(-20.45F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(77, 159).addBox(-18.85F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(159, 67).addBox(-18.05F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(157, 158).addBox(-21.25F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(158, 151).addBox(-11.65F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(158, 144).addBox(-12.45F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(136, 158).addBox(-13.25F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(97, 158).addBox(-14.85F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(157, 137).addBox(-15.65F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(66, 157).addBox(-10.075F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(55, 157).addBox(-9.275F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(44, 157).addBox(-7.675F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(157, 24).addBox(-6.875F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(157, 17).addBox(-6.075F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(114, 156).addBox(-0.475F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(156, 98).addBox(-3.675F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(150, 155).addBox(-2.075F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(129, 155).addBox(-1.275F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(90, 155).addBox(-4.475F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(155, 81).addBox(5.125F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(155, 74).addBox(4.325F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(155, 42).addBox(3.525F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(33, 155).addBox(1.925F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(22, 155).addBox(1.125F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(11, 155).addBox(6.725F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(0, 155).addBox(7.525F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(154, 109).addBox(9.125F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(45, 124).addBox(9.925F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F))
		.texOffs(0, 121).addBox(10.725F, -13.0F, -10.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition sheet_stand = piano.addOrReplaceChild("sheet_stand", CubeListBuilder.create(), PartPose.offset(13.0F, -12.5F, -6.0F));

		PartDefinition cube_r4 = sheet_stand.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -0.75F, 0.25F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(-21.0F, -9.5F, 3.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r5 = sheet_stand.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 37).addBox(-12.0F, -9.75F, -1.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(103, 48).addBox(7.0F, -9.75F, -1.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(-0.45F))
		.texOffs(106, 87).addBox(-11.0F, -8.75F, -1.0F, 22.0F, 3.0F, 2.0F, new CubeDeformation(-0.35F))
		.texOffs(118, 0).addBox(-10.0F, -6.75F, -1.0F, 20.0F, 6.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(118, 9).addBox(-11.0F, -0.75F, -1.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(-21.0F, -6.5F, 2.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition pedal = piano.addOrReplaceChild("pedal", CubeListBuilder.create().texOffs(122, 43).addBox(-12.0F, -10.5F, -4.5F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.5F))
		.texOffs(126, 47).addBox(-12.0F, -9.75F, 2.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.5F))
		.texOffs(0, 106).addBox(-12.0F, -9.0F, -4.5F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(118, 14).addBox(-12.0F, -1.75F, -4.5F, 8.0F, 2.0F, 5.0F, new CubeDeformation(-0.1F))
		.texOffs(0, 190).addBox(-12.0F, -1.25F, -4.5F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(115, 163).addBox(-11.0F, -9.0F, -2.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(88, 162).addBox(-9.75F, -10.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(38, 162).addBox(-8.5F, -10.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(33, 162).addBox(-7.25F, -10.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(63, 94).addBox(-6.0F, -9.0F, -2.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 162).addBox(-9.0F, -1.25F, -6.75F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition cube_r6 = pedal.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(2.0F, -5.5F, -0.25F, 2.0F, 11.0F, 2.0F, new CubeDeformation(-0.75F)).mirror(false)
		.texOffs(0, 0).addBox(-4.0F, -5.5F, -0.25F, 2.0F, 11.0F, 2.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(-8.0F, -5.6157F, 1.1613F, -0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r7 = pedal.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 128).addBox(-1.0F, -0.75F, -1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-11.0F, -0.5F, -5.75F, 0.0F, 0.3927F, 0.0F));

		PartDefinition cube_r8 = pedal.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(11, 162).addBox(-1.0F, -0.75F, -1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-5.0F, -0.5F, -5.75F, 0.0F, -0.3927F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	public void preparePianoModel(Piano piano) {
		this.setFallboard(!piano.isFallboardClosed());
	}

	@Override
	public void setupAnim(Piano entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int unused) {
		piano.render(poseStack, vertexConsumer, packedLight, packedOverlay, 0xffffffff);
	}

	public void setFallboard(boolean open) {
		if (open) {
			this.fallBoard.setRotation(0, 0, 0);
			this.fallBoardStick.setRotation(0, 0, 0);
		} else {
			this.fallBoard.setRotation(0.0F, 0.0F, 0.3927F);
			this.fallBoardStick.setRotation(0.0F, 0.0F, 1.3963F);
		}
		
	}
}
