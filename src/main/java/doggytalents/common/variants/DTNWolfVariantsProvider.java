package doggytalents.common.variants;

import java.util.Set;

import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class DTNWolfVariantsProvider {
    
    public static void start(GatherDataEvent event) {
        var wolf_variant_set = new RegistrySetBuilder()
            .add(Registries.WOLF_VARIANT, DTNWolfVariants::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, DTNWolfVariantsProvider::registerWolfModifier);
            
        var datagen = event.getGenerator();
        datagen.addProvider(event.includeServer(),
            new DatapackBuiltinEntriesProvider(
                datagen.getPackOutput(), event.getLookupProvider(), 
                wolf_variant_set, Set.of(Constants.MOD_ID) 
            )
        );
    }

    private static void registerWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerCherryWolfModifier(ctx);
    }

    private static void registerCherryWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        var cherry_wolf_spawn_id = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS, 
            Util.getResource("cherry_wolf_spawn_modifier")) ;
        var cherry_wolf_spawn_biome = HolderSet.direct(
            ctx.lookup(Registries.BIOME).getOrThrow(Biomes.CHERRY_GROVE)
        );
        var cherry_wolf_spawner_data = new MobSpawnSettings
            .SpawnerData(EntityType.WOLF, 1, 1, 1);
        var cherry_wolf_spawn_modifier = BiomeModifiers.AddSpawnsBiomeModifier
            .singleSpawn(cherry_wolf_spawn_biome, cherry_wolf_spawner_data);
        ctx.register(cherry_wolf_spawn_id, cherry_wolf_spawn_modifier);
    }

}
