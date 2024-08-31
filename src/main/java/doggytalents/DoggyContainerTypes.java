package doggytalents;

import doggytalents.common.entity.Dog;
import doggytalents.common.inventory.container.DogArmorContainer;
import doggytalents.common.inventory.container.DogInventoriesContainer;
import doggytalents.common.inventory.container.DoggyToolsMenu;
import doggytalents.common.inventory.container.FoodBowlContainer;
import doggytalents.common.inventory.container.PackPuppyContainer;
import doggytalents.common.inventory.container.RiceMillMenu;
import doggytalents.common.inventory.container.TreatBagContainer;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.NetworkUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.common.extensions.IMenuTypeExtension;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DoggyContainerTypes {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(BuiltInRegistries.MENU, Constants.MOD_ID);

    public static final Supplier<MenuType<FoodBowlContainer>> FOOD_BOWL = register("food_bowl", (windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new FoodBowlContainer(windowId, inv.player.level(), pos, inv, inv.player);
    });
    public static final Supplier<MenuType<PackPuppyContainer>> PACK_PUPPY = register("pack_puppy", (windowId, inv, data) -> {
        Entity entity = inv.player.level().getEntity(data.readInt());
        return entity instanceof Dog ? new PackPuppyContainer(windowId, inv, (Dog) entity) : null;
    });
    public static final Supplier<MenuType<TreatBagContainer>> TREAT_BAG = register("treat_bag", (windowId, inv, data) -> {
        int slotId = data.readByte();
        return new TreatBagContainer(windowId, inv, slotId, NetworkUtil.readItemFromBuf(data));
    });
    public static final Supplier<MenuType<DogInventoriesContainer>> DOG_INVENTORIES = register("dog_inventories", (windowId, inv, data) -> {
        int noDogs = data.readInt();
        List<Dog> dogs = new ArrayList<>(noDogs);
        for (int i = 0; i < noDogs; ++i) {
            Entity entity = inv.player.level().getEntity(data.readInt());
            if (entity instanceof Dog) {
                dogs.add((Dog) entity);
            }
        }
        return !dogs.isEmpty() ? new DogInventoriesContainer(windowId, inv, dogs) : null;
    });

    public static final Supplier<MenuType<DogArmorContainer>> DOG_ARMOR = register("dog_armor", (windowId, inv, data) -> {
        int dogId = data.readInt();
        var e = inv.player.level().getEntity(dogId);
        if (!(e instanceof Dog)) return null;
        return new DogArmorContainer(windowId, inv, (Dog) e);
    });

    public static final Supplier<MenuType<DoggyToolsMenu>> DOG_TOOLS = register("dog_tools", (windowId, inv, data) -> {
        int dogId = data.readInt();
        var e = inv.player.level().getEntity(dogId);
        if (!(e instanceof Dog)) return null;
        return new DoggyToolsMenu(windowId, inv, (Dog) e);
    });

    public static final Supplier<MenuType<RiceMillMenu>> RICE_MILL = register("rice_mill", (windowId, inv, data) -> {
        var pos = data.readBlockPos();
        return new RiceMillMenu(windowId, inv, pos);
    });

    private static <X extends AbstractContainerMenu, T extends MenuType<X>> Supplier<MenuType<X>> register(final String name, final IContainerFactory<X> factory) {
        return register(name, () -> IMenuTypeExtension.create(factory));
    }

    private static <T extends MenuType<?>> Supplier<T> register(final String name, final Supplier<T> sup) {
        return CONTAINERS.register(name, sup);
    }
}
