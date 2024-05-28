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

public class DTNWolfVariantsProvider {
    
    // public static void start(GatherDataEvent event) {
    //     var wolf_variant_set = new RegistrySetBuilder()
    //         .add(Registries.WOLF_VARIANT, DTNWolfVariants::bootstrap)
    //         .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, DTNWolfVariantsProvider::registerWolfModifier);
            
    //     var datagen = event.getGenerator();
    //     datagen.addProvider(event.includeServer(),
    //         new DatapackBuiltinEntriesProvider(
    //             datagen.getPackOutput(), event.getLookupProvider(), 
    //             wolf_variant_set, Set.of(Constants.MOD_ID) 
    //         )
    //     );
    // }

    // private static void registerWolfModifier(BootstrapContext<BiomeModifier> ctx) {
    //     registerCherryWolfModifier(ctx);
    //     registerLemonyLimeWolfModifier(ctx);
    //     registerHimalayanSaltWolfModifier(ctx);
    // }

    // private static void registerCherryWolfModifier(BootstrapContext<BiomeModifier> ctx) {
    //     var cherry_wolf_spawn_id = ResourceKey.create(
    //         NeoForgeRegistries.Keys.BIOME_MODIFIERS, 
    //         Util.getResource("cherry_wolf_spawn_modifier")) ;
    //     var cherry_wolf_spawn_biome = HolderSet.direct(
    //         ctx.lookup(Registries.BIOME).getOrThrow(Biomes.CHERRY_GROVE)
    //     );
    //     var cherry_wolf_spawner_data = new MobSpawnSettings
    //         .SpawnerData(EntityType.WOLF, 1, 1, 1);
    //     var cherry_wolf_spawn_modifier = BiomeModifiers.AddSpawnsBiomeModifier
    //         .singleSpawn(cherry_wolf_spawn_biome, cherry_wolf_spawner_data);
    //     ctx.register(cherry_wolf_spawn_id, cherry_wolf_spawn_modifier);
    // }

    // private static void registerLemonyLimeWolfModifier(BootstrapContext<BiomeModifier> ctx) {
    //     var lemony_lime_wolf_spawn_id = ResourceKey.create(
    //         NeoForgeRegistries.Keys.BIOME_MODIFIERS, 
    //         Util.getResource("lemony_lime_wolf_spawn_modifier")) ;
    //     var lemony_lime_wolf_spawn_biome = HolderSet.direct(
    //         ctx.lookup(Registries.BIOME).getOrThrow(Biomes.BEACH)
    //     );
    //     var lemony_lime_wolf_spawner_data = new MobSpawnSettings
    //         .SpawnerData(EntityType.WOLF, 1, 1, 1);
    //     var lemony_lime_wolf_spawn_modifier = BiomeModifiers.AddSpawnsBiomeModifier
    //         .singleSpawn(lemony_lime_wolf_spawn_biome, lemony_lime_wolf_spawner_data);
    //     ctx.register(lemony_lime_wolf_spawn_id, lemony_lime_wolf_spawn_modifier);
    // }

    // private static void registerHimalayanSaltWolfModifier(BootstrapContext<BiomeModifier> ctx) {
    //     var himalayan_salt_wolf_spawn_id = ResourceKey.create(
    //         NeoForgeRegistries.Keys.BIOME_MODIFIERS, 
    //         Util.getResource("himalayan_salt_wolf_spawn_modifier")) ;
    //     var himalayan_salt_wolf_spawn_biome = HolderSet.direct(
    //         ctx.lookup(Registries.BIOME).getOrThrow(Biomes.JAGGED_PEAKS)
    //     );
    //     var himalayan_salt_wolf_spawner_data = new MobSpawnSettings
    //         .SpawnerData(EntityType.WOLF, 1, 1, 1);
    //     var himalayan_salt_wolf_spawn_modifier = BiomeModifiers.AddSpawnsBiomeModifier
    //         .singleSpawn(himalayan_salt_wolf_spawn_biome, himalayan_salt_wolf_spawner_data);
    //     ctx.register(himalayan_salt_wolf_spawn_id, himalayan_salt_wolf_spawn_modifier);
    // }

}
