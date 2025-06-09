package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.entity.EntityTickList;

@Mixin(ClientLevel.class)
public interface ClientLevelMixinAccessor {
    
    @Accessor("tickingEntities")
    EntityTickList dtn__getTickingEntities();

}
