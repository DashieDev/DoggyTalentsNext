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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;

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
        
        if (checkLemonyLimeWolfSpawn(level, spawn_pos))
            return true;

        return false;
    }

    public static boolean checkLemonyLimeWolfSpawn(LevelAccessor level, BlockPos spawn_pos) {
        if (!isWolfVariantRegistered(level.registryAccess(), DTNWolfVariants.LEMONY_LIME))
            return false;
        
        var spawn_state = level.getBlockState(spawn_pos.below());
        if (!spawn_state.is(Blocks.SAND))
            return false;

        var spawn_biome = level.getBiome(spawn_pos);
        if (!spawn_biome.is(Biomes.BEACH))
            return false;
        
        return true;
    }

    private static boolean isWolfVariantRegistered(HolderLookup.Provider prov, 
        ResourceKey<WolfVariant> variant) {
        var reg = prov.lookupOrThrow(Registries.WOLF_VARIANT);
        var variantOptional = reg.get(variant);
        return variantOptional.isPresent();
    }

}
