package doggytalents.forge_imitate.event.client;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.forge_imitate.event.Event;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.state.LevelRenderState;

public class RenderLevelStageEvent extends Event {

    public static enum Stage {
        AFTER_TRANSLUCENT_BLOCKS
    }

    private Stage stage = Stage.AFTER_TRANSLUCENT_BLOCKS;
    private final PoseStack stack;
    private DeltaTracker pTicks;
    private LevelRenderState levelRenderState_1_21_10;

    public RenderLevelStageEvent(PoseStack stack, DeltaTracker pTicks, LevelRenderState levelRenderState_1_21_10) {
        this.stack =stack;
        this.pTicks = pTicks;
        this.levelRenderState_1_21_10 = levelRenderState_1_21_10;
    }

    public Stage getStage() {
        return this.stage;
    }

    // public PoseStack getPoseStack() {
    //     return this.stack; // Unavailable in 1.21.10+
    // }

    public DeltaTracker getPartialTick() {
        return this.pTicks;
    }

    public LevelRenderState getLevelRenderState_1_21_10() {
        return this.levelRenderState_1_21_10;
    }

}
