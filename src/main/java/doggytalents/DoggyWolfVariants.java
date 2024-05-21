package doggytalents;

import doggytalents.common.util.Util;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class DoggyWolfVariants {
    
    public static final ResourceKey<WolfVariant> CHERRY = createKey("cherry");

    private static ResourceKey<WolfVariant> createKey(String name) {
        return ResourceKey.create(Registries.WOLF_VARIANT, Util.getResource(name));
    }

    public static void bootstrap(BootstrapContext<WolfVariant> ctx) {
        register(ctx, CHERRY, "cherry", Biomes.CHERRY_GROVE);
    }

    private static void register(BootstrapContext<WolfVariant> ctx, ResourceKey<WolfVariant> key, 
        String name, ResourceKey<Biome> biome) {
            //src\main\resources\assets\doggytalents\textures\entity\dog\classical\compl\vanilla_only\wolf_cherry_angry.png
        var textureLoc = Util.getResource("entity/dog/classical/compl/vanilla_only/wolf_" + name + "_tame" );
        var textureLoc_wild = Util.getResource("entity/dog/classical/compl/vanilla_only/wolf_" + name);
        var textureLoc_angry = Util.getResource("entity/dog/classical/compl/vanilla_only/wolf_" + name + "_angry" );

        var reg = ctx.lookup(Registries.BIOME);
        var biome_set = HolderSet.direct(reg.getOrThrow(biome));
        var variant = new WolfVariant(textureLoc_wild, textureLoc, textureLoc_angry, biome_set);
        ctx.register(key, variant);
    }
}
