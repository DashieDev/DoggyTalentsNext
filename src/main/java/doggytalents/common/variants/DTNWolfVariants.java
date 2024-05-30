package doggytalents.common.variants;

import doggytalents.common.util.Util;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class DTNWolfVariants {
    
    // public static final ResourceKey<WolfVariant> CHERRY = createKey("cherry");
    // public static final ResourceKey<WolfVariant> LEMONY_LIME = createKey("lemony_lime");
    // public static final ResourceKey<WolfVariant> HIMALAYAN_SALT = createKey("himalayan_salt");
    // public static final ResourceKey<WolfVariant> BAMBOO = createKey("bamboo");

    // private static ResourceKey<WolfVariant> createKey(String name) {
    //     return ResourceKey.create(Registries.WOLF_VARIANT, Util.getResource(name));
    // }

    // public static void bootstrap(BootstrapContext<WolfVariant> ctx) {
    //     register(ctx, CHERRY, "cherry", Biomes.CHERRY_GROVE);
    //     register(ctx, LEMONY_LIME, "lemony_lime", Biomes.BEACH);
    //     register(ctx, HIMALAYAN_SALT, "himalayan_salt", Biomes.JAGGED_PEAKS);
    //     register(ctx, BAMBOO, "bamboo", Biomes.BAMBOO_JUNGLE);
    // }

    // private static void register(BootstrapContext<WolfVariant> ctx, ResourceKey<WolfVariant> key, 
    //     String name, ResourceKey<Biome> biome) {
    //         //src\main\resources\assets\doggytalents\textures\entity\dog\classical\compl\vanilla_only\wolf_cherry_angry.png
    //     var textureLoc = Util.getResource("entity/dog/classical/compl/vanilla_only/wolf_" + name + "_tame" );
    //     var textureLoc_wild = Util.getResource("entity/dog/classical/compl/vanilla_only/wolf_" + name);
    //     var textureLoc_angry = Util.getResource("entity/dog/classical/compl/vanilla_only/wolf_" + name + "_angry" );

    //     var reg = ctx.lookup(Registries.BIOME);
    //     var biome_set = HolderSet.direct(reg.getOrThrow(biome));
    //     var variant = new WolfVariant(textureLoc_wild, textureLoc, textureLoc_angry, biome_set);
    //     ctx.register(key, variant);
    // }
}
