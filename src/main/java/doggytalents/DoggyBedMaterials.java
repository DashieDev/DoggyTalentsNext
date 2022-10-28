package doggytalents;

import doggytalents.api.impl.BeddingMaterial;
import doggytalents.api.impl.CasingMaterial;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.common.lib.Constants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class DoggyBedMaterials {

    public static final DeferredRegister<IBeddingMaterial> BEDDINGS = DeferredRegister.create(DoggyRegistries.Keys.BEDDING_REGISTRY, Constants.VANILLA_ID);
    public static final DeferredRegister<ICasingMaterial> CASINGS = DeferredRegister.create(DoggyRegistries.Keys.CASING_REGISTRY, Constants.VANILLA_ID);

    public static final RegistryObject<ICasingMaterial> OAK_PLANKS = registerCasing(() -> Blocks.OAK_PLANKS);
    public static final RegistryObject<ICasingMaterial> SPRUCE_PLANKS = registerCasing(() -> Blocks.SPRUCE_PLANKS);
    public static final RegistryObject<ICasingMaterial> BIRCH_PLANKS = registerCasing(() -> Blocks.BIRCH_PLANKS);
    public static final RegistryObject<ICasingMaterial> JUNGLE_PLANKS = registerCasing(() -> Blocks.JUNGLE_PLANKS);
    public static final RegistryObject<ICasingMaterial> ACACIA_PLANKS = registerCasing(() -> Blocks.ACACIA_PLANKS);
    public static final RegistryObject<ICasingMaterial> DARK_OAK_PLANKS = registerCasing(() -> Blocks.DARK_OAK_PLANKS);
    public static final RegistryObject<ICasingMaterial> CRIMSON_PLANKS = registerCasing(() -> Blocks.CRIMSON_PLANKS);
    public static final RegistryObject<ICasingMaterial> WARPED_PLANKS = registerCasing(() -> Blocks.WARPED_PLANKS);

    public static final RegistryObject<IBeddingMaterial> WHITE_WOOL = registerBedding(() -> Blocks.WHITE_WOOL);
    public static final RegistryObject<IBeddingMaterial> ORANGE_WOOL = registerBedding(() -> Blocks.ORANGE_WOOL);
    public static final RegistryObject<IBeddingMaterial> MAGENTA_WOOL = registerBedding(() -> Blocks.MAGENTA_WOOL);
    public static final RegistryObject<IBeddingMaterial> LIGHT_BLUE_WOOL = registerBedding(() -> Blocks.LIGHT_BLUE_WOOL);
    public static final RegistryObject<IBeddingMaterial> YELLOW_WOOL = registerBedding(() -> Blocks.YELLOW_WOOL);
    public static final RegistryObject<IBeddingMaterial> LIME_WOOL = registerBedding(() -> Blocks.LIME_WOOL);
    public static final RegistryObject<IBeddingMaterial> PINK_WOOL = registerBedding(() -> Blocks.PINK_WOOL);
    public static final RegistryObject<IBeddingMaterial> GRAY_WOOL = registerBedding(() -> Blocks.GRAY_WOOL);
    public static final RegistryObject<IBeddingMaterial> LIGHT_GRAY_WOOL = registerBedding(() -> Blocks.LIGHT_GRAY_WOOL);
    public static final RegistryObject<IBeddingMaterial> CYAN_WOOL = registerBedding(() -> Blocks.CYAN_WOOL);
    public static final RegistryObject<IBeddingMaterial> PURPLE_WOOL = registerBedding(() -> Blocks.PURPLE_WOOL);
    public static final RegistryObject<IBeddingMaterial> BLUE_WOOL = registerBedding(() -> Blocks.BLUE_WOOL);
    public static final RegistryObject<IBeddingMaterial> BROWN_WOOL = registerBedding(() -> Blocks.BROWN_WOOL);
    public static final RegistryObject<IBeddingMaterial> GREEN_WOOL = registerBedding(() -> Blocks.GREEN_WOOL);
    public static final RegistryObject<IBeddingMaterial> RED_WOOL = registerBedding(() -> Blocks.RED_WOOL);
    public static final RegistryObject<IBeddingMaterial> BLACK_WOOL = registerBedding(() -> Blocks.BLACK_WOOL);

    private static RegistryObject<ICasingMaterial> registerCasing(final Supplier<Block> sup) {
        return CASINGS.register( ForgeRegistries.BLOCKS.getKey(sup.get()).getPath(), () -> new CasingMaterial(sup));
    }

    private static RegistryObject<IBeddingMaterial> registerBedding(final Supplier<Block> sup) {
        return BEDDINGS.register( ForgeRegistries.BLOCKS.getKey(sup.get()).getPath(), () -> new BeddingMaterial(sup));
    }
}
