package doggytalents.forge_imitate.event.client;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.forge_imitate.event.Event;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;

public class RenderArmEvent extends Event {
    
    private final PoseStack stack;
    private final Player player;
    private final HumanoidArm arm;

    public RenderArmEvent(PoseStack stack, Player player, HumanoidArm arm) {
        this.stack = stack;
        this.player = player;
        this.arm = arm;
    }

    public PoseStack getPoseStack() {
        return this.stack;
    }

    public Player getPlayer() {
        return this.player;
    }

    public HumanoidArm getArm() {
        return this.arm;
    } 

}
