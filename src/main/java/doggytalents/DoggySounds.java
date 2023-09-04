package doggytalents;

import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class DoggySounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.Keys.SOUND_EVENTS, Constants.MOD_ID);

    public static final RegistryObject<SoundEvent> WHISTLE_SHORT = register("whistle_short");
    public static final RegistryObject<SoundEvent> WHISTLE_LONG = register("whistle_long");

    /* 
        Recordings obtained via The Open Well-Tempered Clavier Project
        by Kimiko Ishizaka, which, according to the project, licensed
        with the CC0 - Public Domain. Project website:
        https://www.welltemperedclavier.org/index.html
    */
    public static final RegistryObject<SoundEvent> BWV_849_FUGUE_KIMIKO = register("bwv_849_fugue_kimiko");
    
    /* 
        Recordings obtained via The Art Of Fugue Recordings Set
        by Kimiko Ishizaka, which, according to the set, licensed
        with the CC0 - Public Domain. Project website:
        https://kimikoishizaka.bandcamp.com/album/j-s-bach-the-art-of-the-fugue-kunst-der-fuge-bwv-1080
    */
    public static final RegistryObject<SoundEvent> BWV_1080_FUGUE_11_KIMIKO = register("bwv_1080_fugue_11_kimiko");

    public static final RegistryObject<SoundEvent> OKAMI_RYOSHIMA_COAST_ARR = register("okami_ryoshima_coast_arr");

    public static final RegistryObject<SoundEvent> CHOPIN_OP64_NO1 = register("chopin_op64_no1");

    private static RegistryObject<SoundEvent> register(final String name) {
        //TODO 1.19.3 ??
        return register(name, () -> SoundEvent.createVariableRangeEvent(Util.getResource(name)));
    }

    private static <T extends SoundEvent> RegistryObject<T> register(final String name, final Function<ResourceLocation, T> factory) {
        return register(name, () -> factory.apply(Util.getResource(name)));
    }

    private static <T extends SoundEvent> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return SOUNDS.register(name, sup);
    }
}
