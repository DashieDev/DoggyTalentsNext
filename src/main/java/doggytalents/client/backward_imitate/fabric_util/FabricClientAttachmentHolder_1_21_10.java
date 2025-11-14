package doggytalents.client.backward_imitate.fabric_util;

import java.util.Map;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.util.context.ContextKey;

public interface FabricClientAttachmentHolder_1_21_10 {
    
    public Map<ContextKey<?>, Object> doggytalentsnext__getAttachment();

    private static Map<ContextKey<?>, Object> getAttachmentFromState(Object state) {
        return ((FabricClientAttachmentHolder_1_21_10) state)
            .doggytalentsnext__getAttachment();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAttachment(EntityRenderState state, ContextKey<T> key) {
        var attachment = getAttachmentFromState(state);
        return (T) attachment.get(key);
    }

    public static <T> void putAttachement(EntityRenderState state, ContextKey<T> key, T object) {
        var attachment = getAttachmentFromState(state);
        attachment.put(key, object);
    }
}
