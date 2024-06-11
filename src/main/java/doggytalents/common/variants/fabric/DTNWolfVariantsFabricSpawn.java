package doggytalents.common.variants.fabric;

import java.util.function.Predicate;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class DTNWolfVariantsFabricSpawn {
    
    public static void init() {
        addCherryWolfSpawn();
        addHimalayanSaltWolfSpawn();
        addLemonyLimeWolfSpawn();
        addBambooWolfSpawn();
        addCrimsonWolfSpawn();
        addWarpedWolfModifier();
        addBirchWolfModifier();
        addPistachioWolfModifier();
        addGuacamoleWolfModifier();
    } 

    private static void addCherryWolfSpawn() {
        BiomeModifications.addSpawn(selectForKey(Biomes.CHERRY_GROVE),
            MobCategory.CREATURE, EntityType.WOLF, 1, 1, 1);
    }

    private static void addHimalayanSaltWolfSpawn() {
        BiomeModifications.addSpawn(selectForKey(Biomes.JAGGED_PEAKS),
            MobCategory.CREATURE, EntityType.WOLF, 1, 1, 1);
    }

    private static void addLemonyLimeWolfSpawn() {
        BiomeModifications.addSpawn(selectForKey(Biomes.BEACH),
            MobCategory.CREATURE, EntityType.WOLF, 1, 1, 1);
    }

    private static void addBambooWolfSpawn() {
        BiomeModifications.addSpawn(selectForKey(Biomes.JUNGLE),
            MobCategory.CREATURE, EntityType.WOLF, 1, 1, 1);
        BiomeModifications.addSpawn(selectForKey(Biomes.BAMBOO_JUNGLE),
            MobCategory.CREATURE, EntityType.WOLF, 60, 1, 1);
    }

    private static void addCrimsonWolfSpawn() {
        BiomeModifications.addSpawn(selectForKey(Biomes.CRIMSON_FOREST),
            MobCategory.CREATURE, EntityType.WOLF, 20, 1, 1);
    }

    private static void addWarpedWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.WARPED_FOREST),
            MobCategory.CREATURE, EntityType.WOLF, 40, 1, 1);
    }

    private static void addBirchWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.BIRCH_FOREST),
            MobCategory.CREATURE, EntityType.WOLF, 1, 1, 1);
    }

    private static void addPistachioWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.MANGROVE_SWAMP),
            MobCategory.CREATURE, EntityType.WOLF, 1, 1, 1);
    }

    private static void addGuacamoleWolfModifier() {
        BiomeModifications.addSpawn(selectForKey(Biomes.MEADOW),
            MobCategory.CREATURE, EntityType.WOLF, 1, 1, 1);
    }

    private static Predicate<BiomeSelectionContext> selectForKey(ResourceKey<Biome> biomeKey) {
        return ctx -> ctx.getBiomeKey().equals(biomeKey);
    }

}
