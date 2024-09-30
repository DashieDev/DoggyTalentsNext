package doggytalents.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import doggytalents.api.enu.forward_imitate.registryfix.v1_18_2.TalentOptionEntry;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryType;
import doggytalents.api.registry.TalentOption;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.api.registry.Talent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

/**
 * @author ProPercivalalb
 */
public class DoggyTalentsAPI {

    public static Supplier<IForgeRegistry<Talent>> TALENTS;
    public static Supplier<IForgeRegistry<Accessory>> ACCESSORIES;
    public static Supplier<IForgeRegistry<AccessoryType>> ACCESSORY_TYPE;
    public static Supplier<IForgeRegistry<TalentOptionEntry>> TALENT_OPTIONS;
    
    public static final Logger LOGGER = LogManager.getLogger("doggytalents");
}
