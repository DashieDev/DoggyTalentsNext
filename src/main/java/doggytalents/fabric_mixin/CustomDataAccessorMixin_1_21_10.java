package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;

@Mixin(CustomData.class)
public interface CustomDataAccessorMixin_1_21_10 {
    
    @Accessor("tag")
    CompoundTag dtn__getInnerTag();

}
