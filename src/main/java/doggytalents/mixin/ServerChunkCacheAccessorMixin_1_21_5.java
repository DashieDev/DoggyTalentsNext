package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.level.TicketStorage;

@Mixin(ServerChunkCache.class)
public interface ServerChunkCacheAccessorMixin_1_21_5 {
    
    @Accessor("ticketStorage")
    public TicketStorage dtn__getTicketStorage();

}
