package doggytalents.fabric_mixin;

import java.util.HashMap;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import doggytalents.client.backward_imitate.fabric_util.FabricClientAttachmentHolder_1_21_10;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.util.context.ContextKey;

@Mixin(EntityRenderState.class)
public class EntityRenderStateMixin_1_21_10 implements FabricClientAttachmentHolder_1_21_10 {

    @Unique
    private final Map<ContextKey<?>, Object> doggytalentsnext_attachments 
        = new Reference2ObjectOpenHashMap<>();

    @Unique
    @Override
    public Map<ContextKey<?>, Object> doggytalentsnext__getAttachment() {
        return doggytalentsnext_attachments;
    }
}
