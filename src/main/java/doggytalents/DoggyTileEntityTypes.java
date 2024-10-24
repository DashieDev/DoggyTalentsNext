package doggytalents;

import doggytalents.common.block.tileentity.DogBathBlockEntity;
import doggytalents.common.block.tileentity.DogBedTileEntity;
import doggytalents.common.block.tileentity.FoodBowlTileEntity;
import doggytalents.common.block.tileentity.RiceMillBlockEntity;
import doggytalents.common.lib.Constants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class DoggyTileEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, Constants.MOD_ID);

    public static final Supplier<BlockEntityType<DogBedTileEntity>> DOG_BED = register("dog_bed", DogBedTileEntity::new, DoggyBlocks.DOG_BED);
    public static final Supplier<BlockEntityType<FoodBowlTileEntity>> FOOD_BOWL = register("food_bowl", FoodBowlTileEntity::new, DoggyBlocks.FOOD_BOWL);
    public static final Supplier<BlockEntityType<RiceMillBlockEntity>> RICE_MILL = register("rice_mill", RiceMillBlockEntity::new, DoggyBlocks.RICE_MILL);
    public static final Supplier<BlockEntityType<DogBathBlockEntity>> DOG_BATH = register("dog_bath", DogBathBlockEntity::new, DoggyBlocks.DOG_BATH);

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(final String name, final BlockEntityType.BlockEntitySupplier<T> sup, Supplier<? extends Block> validBlock) {
        return register(name, () -> BlockEntityType.Builder.of(sup, validBlock.get()).build(null));
    }

    private static <T extends BlockEntityType<?>> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return TILE_ENTITIES.register(name, sup);
    }
}
