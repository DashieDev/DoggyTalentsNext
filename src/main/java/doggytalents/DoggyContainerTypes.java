package doggytalents;

import doggytalents.common.entity.Dog;
import doggytalents.common.fabric_helper.entity.network.container_sync.data.BlockPosData;
import doggytalents.common.fabric_helper.entity.network.container_sync.data.IntArrayData;
import doggytalents.common.fabric_helper.entity.network.container_sync.data.IntData;
import doggytalents.common.fabric_helper.entity.network.container_sync.data.ItemAndSlotData;
import doggytalents.common.inventory.container.DogArmorContainer;
import doggytalents.common.inventory.container.DogInventoriesContainer;
import doggytalents.common.inventory.container.DoggyToolsMenu;
import doggytalents.common.inventory.container.FoodBowlContainer;
import doggytalents.common.inventory.container.PackPuppyContainer;
import doggytalents.common.inventory.container.RiceMillMenu;
import doggytalents.common.inventory.container.TreatBagContainer;
import doggytalents.common.lib.Constants;
import doggytalents.forge_imitate.registry.DeferredRegister;
import doggytalents.forge_imitate.registry.RegistryObject;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType.ExtendedFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DoggyContainerTypes {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(() -> BuiltInRegistries.MENU, Constants.MOD_ID);

    public static final RegistryObject<MenuType<FoodBowlContainer>> FOOD_BOWL = register("food_bowl", BlockPosData.class, (windowId, inv, data) -> {
        BlockPos pos = data.val;
        return new FoodBowlContainer(windowId, inv.player.level(), pos, inv, inv.player);
    }, BlockPosData.CODEC);
    public static final RegistryObject<MenuType<PackPuppyContainer>> PACK_PUPPY = register("pack_puppy", IntData.class, (windowId, inv, data) -> {
        Entity entity = inv.player.level().getEntity(data.val);
        return entity instanceof Dog ? new PackPuppyContainer(windowId, inv, (Dog) entity) : null;
    }, IntData.CODEC);
    public static final Supplier<MenuType<TreatBagContainer>> TREAT_BAG = register("treat_bag", ItemAndSlotData.class, (windowId, inv, data) -> {
        int slotId = data.slot;
        return new TreatBagContainer(windowId, inv, slotId, data.val);
    }, ItemAndSlotData.CODEC);
    public static final RegistryObject<MenuType<DogInventoriesContainer>> DOG_INVENTORIES = register("dog_inventories", IntArrayData.class, (windowId, inv, data) -> {
        int noDogs = data.val.size();
        List<Dog> dogs = new ArrayList<>(noDogs);
        for (int i = 0; i < noDogs; ++i) {
            Entity entity = inv.player.level().getEntity(data.val.get(i));
            if (entity instanceof Dog) {
                dogs.add((Dog) entity);
            }
        }
        return !dogs.isEmpty() ? new DogInventoriesContainer(windowId, inv, dogs) : null;
    }, IntArrayData.CODEC);

    public static final RegistryObject<MenuType<DogArmorContainer>> DOG_ARMOR = register("dog_armor", IntData.class, (windowId, inv, data) -> {
        int dogId = data.val;
        var e = inv.player.level().getEntity(dogId);
        if (!(e instanceof Dog)) return null;
        return new DogArmorContainer(windowId, inv, (Dog) e);
    }, IntData.CODEC);

    public static final RegistryObject<MenuType<DoggyToolsMenu>> DOG_TOOLS = register("dog_tools", IntData.class, (windowId, inv, data) -> {
        int dogId = data.val;
        var e = inv.player.level().getEntity(dogId);
        if (!(e instanceof Dog)) return null;
        return new DoggyToolsMenu(windowId, inv, (Dog) e);
    }, IntData.CODEC);

    public static final RegistryObject<MenuType<RiceMillMenu>> RICE_MILL = register("rice_mill", BlockPosData.class, (windowId, inv, data) -> {
        var pos = data.val;
        return new RiceMillMenu(windowId, inv, pos);
    }, BlockPosData.CODEC);

    private static <X extends AbstractContainerMenu, D> RegistryObject<MenuType<X>> register(final String name, Class<D> dataType, final ExtendedFactory<X, D> factory, final StreamCodec<RegistryFriendlyByteBuf, D> codec) {
        return register(name, () -> new ExtendedScreenHandlerType<X, D>(factory, codec));
    }

    private static <T extends MenuType<?>> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return CONTAINERS.register(name, sup);
    }
}
