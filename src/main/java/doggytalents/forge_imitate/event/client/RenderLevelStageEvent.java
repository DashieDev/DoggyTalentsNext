package doggytalents.forge_imitate.event.client;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.forge_imitate.event.Event;
import net.minecraft.client.Camera;

public class RenderLevelStageEvent extends Event {

    public static enum Stage {
        AFTER_TRANSLUCENT_BLOCKS
    }

    private Stage stage = Stage.AFTER_TRANSLUCENT_BLOCKS;
    private final PoseStack stack;
    private float pTicks;
    private Camera camera;

    public RenderLevelStageEvent(PoseStack stack, float pTicks, Camera camera) {
        this.stack =stack;
        this.pTicks = pTicks;
        this.camera = camera;
    }

    public Stage getStage() {
        return this.stage;
    }

    public PoseStack getPoseStack() {
        return this.stack;
    }

    public float getPartialTick() {
        return this.pTicks;
    }

    public Camera getCamera() {
        return this.camera;
    }

}
