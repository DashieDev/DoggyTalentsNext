package doggytalents.common.entity.accessory;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.config.ConfigHandler;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class Band extends Accessory {

    public Band(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.RADIO_COLLAR_LEGACY, itemIn);
    }

    @Override
    public byte getRenderLayer() {
        return AccessoryInstance.RENDER_TOP;
    }

    @Override
    public boolean isDogStillNakedWhenWear() {
        return true;
    }

    @Override
    public boolean shouldRender() {
        return ConfigHandler.CLIENT.RENDER_RADIO_COLLAR.get();
    }
}
