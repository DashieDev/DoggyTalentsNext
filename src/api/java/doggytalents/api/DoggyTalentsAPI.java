package doggytalents.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryType;
import doggytalents.api.registry.TalentOption;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.api.registry.Talent;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

/**
 * @author ProPercivalalb
 */
public class DoggyTalentsAPI {

    public static Supplier<Registry<Talent>> TALENTS;
    public static Supplier<Registry<Accessory>> ACCESSORIES;
    public static Supplier<Registry<AccessoryType>> ACCESSORY_TYPE;
    public static Supplier<IForgeRegistry<TalentOption<?>>> TALENT_OPTIONS;
    
    public static final Logger LOGGER = LogManager.getLogger("doggytalents");
}
