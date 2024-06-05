package doggytalents.common.variants;

import doggytalents.common.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.Event.Result;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent.PositionCheck;

public class DTNWolfVariantsSpawnPlacements {
    
    public static void onRegisterSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(EntityType.WOLF, DTNWolfVariantsSpawnPlacements::DTNWolfVariantsSpawnableOn);
    }

    public static boolean DTNWolfVariantsSpawnableOn(EntityType<Wolf> wolf_type, 
        LevelAccessor level, MobSpawnType spawn_type, 
        BlockPos spawn_pos, RandomSource rand
    ) {
        if (!ConfigHandler.SERVER.EXTENDED_WOLVES_SPAWNABLE_BLOCK.get())
            return false;
        
        var spawn_state = level.getBlockState(spawn_pos.below());
        var spawn_biome = level.getBiome(spawn_pos);

        if (checkLemonyLimeWolfSpawn(level, spawn_pos, spawn_state, spawn_biome))
            return true;

        if (checkCrimsonWolfSpawn(level, spawn_pos, spawn_state, spawn_biome))
            return true;

        if (checkWarpedWolfSpawn(level, spawn_pos, spawn_state, spawn_biome))
            return true;

        return false;
    }

    public static boolean checkLemonyLimeWolfSpawn(LevelAccessor level, BlockPos spawn_pos,
        BlockState spawn_state, Holder<Biome> spawn_biome) {
        if (!isWolfVariantRegistered(level.registryAccess(), DTNWolfVariants.LEMONY_LIME))
            return false;
        if (!spawn_state.is(Blocks.SAND))
            return false;
        if (!spawn_biome.is(Biomes.BEACH))
            return false;
        
        return true;
    }

    public static boolean checkWarpedWolfSpawn(LevelAccessor level, BlockPos spawn_pos,
        BlockState spawn_state, Holder<Biome> spawn_biome) {
        if (!isWolfVariantRegistered(level.registryAccess(), DTNWolfVariants.WARPED))
            return false;
        
        if (!spawn_state.is(Blocks.WARPED_NYLIUM))
            return false;
        if (!spawn_biome.is(Biomes.WARPED_FOREST))
            return false;
        
        return true;
    }

    public static boolean checkCrimsonWolfSpawn(LevelAccessor level, BlockPos spawn_pos,
        BlockState spawn_state, Holder<Biome> spawn_biome) {
        if (!isWolfVariantRegistered(level.registryAccess(), DTNWolfVariants.CRIMSON))
            return false;
        
        if (!spawn_state.is(Blocks.CRIMSON_NYLIUM))
            return false;
        if (!spawn_biome.is(Biomes.CRIMSON_FOREST))
            return false;
        
        return true;
    }

    private static boolean isWolfVariantRegistered(HolderLookup.Provider prov, 
        ResourceKey<WolfVariant> variant) {
        var reg = prov.lookupOrThrow(Registries.WOLF_VARIANT);
        var variantOptional = reg.get(variant);
        return variantOptional.isPresent();
    }

    public static void onPositionCheck(PositionCheck event) {
        if (!ConfigHandler.SERVER.NETHER_WOLF_SPAWN_BYPASS.get())
            return;
        
        var entity = event.getEntity();
        if (event.getSpawnType() != MobSpawnType.CHUNK_GENERATION)
            return;
        
        if (!(entity instanceof Wolf wolf))
            return;
        
        var spawn_level = event.getLevel();

        if (!isSpawningInNether(spawn_level))
            return;
        
        if (wolf.checkSpawnObstruction(spawn_level))
            event.setResult(Result.ALLOW);
    }

    public static boolean isSpawningInNether(ServerLevelAccessor spawn_level) {
        var inner_level = spawn_level.getLevel();
        if (inner_level == null)
            return false;
        var dim = inner_level.dimension();
        if (dim == null)
            return false;
        return dim.equals(Level.NETHER);
    }

}
