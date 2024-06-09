package doggytalents.common.variants;

import doggytalents.common.util.Util;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class DTNWolfVariants {
    
    // public static final ResourceKey<WolfVariant> CHERRY = createKey("cherry");
    // public static final ResourceKey<WolfVariant> LEMONY_LIME = createKey("lemony_lime");
    // public static final ResourceKey<WolfVariant> HIMALAYAN_SALT = createKey("himalayan_salt");
    // public static final ResourceKey<WolfVariant> BAMBOO = createKey("bamboo");
    // public static final ResourceKey<WolfVariant> CRIMSON = createKey("crimson");
    // public static final ResourceKey<WolfVariant> BIRCH = createKey("birch");
    // public static final ResourceKey<WolfVariant> WARPED = createKey("warped");

    // public static final ResourceKey<WolfVariant> VSCODE = createKey("vscode");
    

    // private static ResourceKey<WolfVariant> createKey(String name) {
    //     return ResourceKey.create(Registries.WOLF_VARIANT, Util.getResource(name));
    // }

    // public static void bootstrap(BootstrapContext<WolfVariant> ctx) {
    //     register(ctx, CHERRY, "cherry", Biomes.CHERRY_GROVE);
    //     register(ctx, LEMONY_LIME, "lemony_lime", Biomes.BEACH);
    //     register(ctx, HIMALAYAN_SALT, "himalayan_salt", Biomes.JAGGED_PEAKS);
    //     register(ctx, BAMBOO, "bamboo", Biomes.BAMBOO_JUNGLE);
    //     register(ctx, CRIMSON, "crimson", Biomes.CRIMSON_FOREST);
    //     register(ctx, BIRCH, "birch", Biomes.BIRCH_FOREST);
    //     register(ctx, WARPED, "warped", Biomes.WARPED_FOREST);

    //     register(ctx, VSCODE, "vscode", HolderSet.empty());
    // }

    // private static void register(BootstrapContext<WolfVariant> ctx, ResourceKey<WolfVariant> key, 
    //     String name, ResourceKey<Biome> biome) {
       
    //     var reg = ctx.lookup(Registries.BIOME);
    //     var biome_set = HolderSet.direct(reg.getOrThrow(biome));
        
    //     register(ctx, key, name, biome_set);
    // }

    // private static void register(BootstrapContext<WolfVariant> ctx, ResourceKey<WolfVariant> key, 
    //     String name, HolderSet<Biome> biome_set) {

    //     var textureLoc = Util.getResource("entity/dog/classical/compl/vanilla_only/wolf_" + name + "_tame" );
    //     var textureLoc_wild = Util.getResource("entity/dog/classical/compl/vanilla_only/wolf_" + name);
    //     var textureLoc_angry = Util.getResource("entity/dog/classical/compl/vanilla_only/wolf_" + name + "_angry" );

    //     var variant = new WolfVariant(textureLoc_wild, textureLoc, textureLoc_angry, biome_set);
    //     ctx.register(key, variant);
    // }
}
