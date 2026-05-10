package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.accessories.MidiKeyboardModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.DogRenderState;
import doggytalents.client.entity.render.layer.accessory.DefaultAccessoryRenderer;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class MidiKeyboardRenderEntry extends Entry{
    public static final ModelLayerLocation MIDI_KEYBOARD = new ModelLayerLocation(Util.getResource("midi_keyboard"), "main");
    public MidiKeyboardModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new MidiKeyboardModel(ctx.bakeLayer(MIDI_KEYBOARD));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MIDI_KEYBOARD, MidiKeyboardModel::createBodyLayer);
    }

    @Override
    public Identifier getResources(AccessoryInstance inst) {
        return Resources.MIDI_KEYBOARD;
    }

    @Override
    public boolean isDyable() {
        return true;
    }

    @Override
    public void renderAccessory(RenderLayer<DogRenderState, DogModel> layer, PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector, int packedLight, DogRenderState renderState, AccessoryInstance inst) {

        var model = this.getModel();
        var dogModel = layer.getParentModel();
        dogModel.copyPropertiesTo(model);
        model.setupAnim(renderState);
        model.sync(dogModel);

        float[] color = new float[]{1.0f, 1.0f, 1.0f};
        if (inst instanceof IColoredObject coloredObject)
            color = coloredObject.getColor();

        DefaultAccessoryRenderer.renderTranslucentModel(model, Resources.MIDI_KEYBOARD,
            poseStack, submitNodeCollector, packedLight, renderState, 1f, 1f, 1f, 1f);
        DefaultAccessoryRenderer.renderTranslucentModel(model, Resources.MIDI_KEYBOARD_OVERLAY,
            poseStack, submitNodeCollector, packedLight, renderState, color[0], color[1], color[2], 1f);
    }
}
