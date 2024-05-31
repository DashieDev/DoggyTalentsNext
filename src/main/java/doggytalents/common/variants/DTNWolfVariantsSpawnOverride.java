package doggytalents.common.variants;

import java.util.Optional;

import doggytalents.common.config.ConfigHandler;
import doggytalents.common.util.DogUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class DTNWolfVariantsSpawnOverride {
    
    // public static void onWolfSpawn(MobSpawnEvent.FinalizeSpawn event) {
    //     if (!ConfigHandler.SERVER.WOLF_VARIANT_OVERRIDE_EXCLUSIVE.get())
    //         return;

    //     var spawn_type = event.getSpawnType();
    //     boolean is_target_spawn_type =
    //         spawn_type == MobSpawnType.CHUNK_GENERATION
    //         || spawn_type == MobSpawnType.SPAWN_EGG
    //         || spawn_type == MobSpawnType.MOB_SUMMONED;
    //     if (!is_target_spawn_type)
    //         return;
        
    //     var entity = event.getEntity();
    //     if (!(entity instanceof Wolf wolf))
    //         return;
        
    //     boolean already_have_pack = event.getSpawnData() != null;
    //     if (already_have_pack)
    //         return;
        
    //     var level = wolf.level();
    //     var spawn_pos = wolf.blockPosition();
    //     var biome = level.getBiome(spawn_pos);
    //     var ossia_holder_optional = checkForOssiaVariant(wolf.registryAccess(), biome, spawn_type);
    //     if (!ossia_holder_optional.isPresent())
    //         return;
        
    //     var ossia_holder = ossia_holder_optional.get();
    //     var ossia_spawn_pack = new Wolf.WolfPackData(ossia_holder);
    //     event.setSpawnData(ossia_spawn_pack);
    // }

    // private static Optional<Holder<WolfVariant>> checkForOssiaVariant(HolderLookup.Provider prov, Holder<Biome> biome, MobSpawnType spawn_type) {
    //     if (isBamboWolfSpawn(biome, spawn_type)) {
    //         return DogUtil.getWolfVariantHolderIfLoaded(prov, DTNWolfVariants.BAMBOO);
    //     }
    //     return Optional.empty();
    // }

    // private static boolean isBamboWolfSpawn(Holder<Biome> biome, MobSpawnType spawn_type) {
    //     //Rusty Wolf only chunk-generated spawn in Biomes.SPARSE_JUNGLE
    //     if (biome.is(Biomes.BAMBOO_JUNGLE))
    //         return true;
    //     if (biome.is(Biomes.JUNGLE) && spawn_type == MobSpawnType.CHUNK_GENERATION)
    //         return true;
    //     return false;
    // }

}
