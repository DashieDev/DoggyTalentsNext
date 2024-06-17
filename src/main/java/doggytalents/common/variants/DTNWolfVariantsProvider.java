package doggytalents.common.variants;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import doggytalents.common.data.DTMusicProvider;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
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
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, DTNWolfVariantsProvider::registerWolfModifier)
            .add(Registries.JUKEBOX_SONG, DTMusicProvider::bootstrap);;
            
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
        registerLemonyLimeWolfModifier(ctx);
        registerHimalayanSaltWolfModifier(ctx);
        registerBambooWolfModifier(ctx);
        registerCrimsonWolfModifier(ctx);
        registerWarpedWolfModifier(ctx);
        registerBirchWolfModifier(ctx);
        registerPistachioWolfModifier(ctx);
        registerGuacamoleWolfModifier(ctx);
    }

    private static void registerCherryWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_cherry", 
            Biomes.CHERRY_GROVE, 
            new MobSpawnSettings
                .SpawnerData(EntityType.WOLF, 1, 1, 1)
        );
    }

    private static void registerLemonyLimeWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_lemony_lime", 
            Biomes.BEACH, 
            new MobSpawnSettings
                .SpawnerData(EntityType.WOLF, 1, 1, 1)
        );
    }

    private static void registerHimalayanSaltWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_himalayan_salt", 
            Biomes.JAGGED_PEAKS, 
            new MobSpawnSettings
                .SpawnerData(EntityType.WOLF, 1, 1, 1)
        );
    }

    private static void registerBambooWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_bamboo", 
            Biomes.JUNGLE, 
            new MobSpawnSettings
                .SpawnerData(EntityType.WOLF, 1, 1, 1)
        );
        registerSingleSpawnModifier(
            ctx, "wolf_bamboo_dedicated", 
            Biomes.BAMBOO_JUNGLE,
            new MobSpawnSettings
                .SpawnerData(EntityType.WOLF, 60, 1, 1)
        );
    }

    private static void registerCrimsonWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_crimson", 
            Biomes.CRIMSON_FOREST, 
            new MobSpawnSettings
                .SpawnerData(EntityType.WOLF, 20, 1, 1)
        );
    }

    private static void registerWarpedWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_warped", 
            Biomes.WARPED_FOREST, 
            new MobSpawnSettings
                .SpawnerData(EntityType.WOLF, 40, 1, 1)
        );
    }

    private static void registerBirchWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_birch", 
            Biomes.BIRCH_FOREST, 
            new MobSpawnSettings
                .SpawnerData(EntityType.WOLF, 1, 1, 1)
        );
    }

    private static void registerPistachioWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_pistachio", 
            Biomes.MANGROVE_SWAMP, 
            new MobSpawnSettings
                .SpawnerData(EntityType.WOLF, 1, 1, 1)
        );
    }

    private static void registerGuacamoleWolfModifier(BootstrapContext<BiomeModifier> ctx) {
        registerSingleSpawnModifier(
            ctx, "wolf_guacamole", 
            Biomes.MEADOW, 
            new MobSpawnSettings
                .SpawnerData(EntityType.WOLF, 1, 1, 1)
        );
    }

    private static void registerSingleSpawnModifier(BootstrapContext<BiomeModifier> ctx,
        String name, ResourceKey<Biome> biome, MobSpawnSettings.SpawnerData spawner_data) {
        
        registerSingleSpawnModifier(ctx, name, List.of(biome), spawner_data);
    }

    private static void registerSingleSpawnModifier(BootstrapContext<BiomeModifier> ctx,
        String name, List<ResourceKey<Biome>> biomes, MobSpawnSettings.SpawnerData spawner_data) {
        
        var spawn_id = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS, 
            Util.getResource(name));
        
        var biome_reg = ctx.lookup(Registries.BIOME);
        var biome_holders = biomes.stream()
            .map(x -> biome_reg.get(x))
            .filter(x -> x.isPresent())
            .map(x -> x.get())
            .collect(Collectors.toList());
        if (biome_holders.isEmpty())
            return;
        var spawn_biomes = HolderSet.direct(biome_holders);
        var spawn_modifier = BiomeModifiers.AddSpawnsBiomeModifier
            .singleSpawn(spawn_biomes, spawner_data);
        
        ctx.register(spawn_id, spawn_modifier);
    }

}
