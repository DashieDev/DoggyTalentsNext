package doggytalents.common.data;

import java.util.Set;

import doggytalents.DoggySounds;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;

public class DTMusicProvider {

    public static ResourceKey<JukeboxSong> CHOPIN_OP64_NO_1 = key("chopin_op64_1");

    public static void bootstrap(BootstrapContext<JukeboxSong> ctx) {
        register(ctx, CHOPIN_OP64_NO_1, DoggySounds.CHOPIN_OP64_NO1.get(), 
            "item.doggytalents.disc_chopin_op64_no1.desc", 132*20);
    }

    private static void register(BootstrapContext<JukeboxSong> ctx, ResourceKey<JukeboxSong> key, SoundEvent soundEvent, 
        String translation, int length) {
        ctx.register(key, new JukeboxSong(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(soundEvent),
            Component.translatable(translation), length, 13));
    }

    private static ResourceKey<JukeboxSong> key(String id) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, Util.getResource(id));
    }

}
