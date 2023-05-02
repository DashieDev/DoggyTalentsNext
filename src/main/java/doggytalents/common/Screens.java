package doggytalents.common;

import doggytalents.DoggyItems;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.block.tileentity.FoodBowlTileEntity;
import doggytalents.common.entity.Dog;
import doggytalents.common.inventory.container.DogArmorContainer;
import doggytalents.common.inventory.container.DogInventoriesContainer;
import doggytalents.common.inventory.container.DoggyToolsMenu;
import doggytalents.common.inventory.container.PackPuppyContainer;
import doggytalents.common.inventory.container.TreatBagContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class Screens {

    public static class PackPuppyContainerProvider implements MenuProvider {

        private AbstractDog dog;

        public PackPuppyContainerProvider(AbstractDog dogIn) {
            this.dog = dogIn;
        }

        @Override
        public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
            return new PackPuppyContainer(windowId, inventory, this.dog);
        }

        @Override
        public Component getDisplayName() {
            return Component.translatable("container.doggytalents.pack_puppy");
        }
    }

    public static class DogInventoriesContainerProvider implements MenuProvider {

        private List<Dog> dogs;

        public DogInventoriesContainerProvider(List<Dog> dogIn) {
            this.dogs = dogIn;
        }

        @Override
        public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
            SimpleContainerData array = new SimpleContainerData(this.dogs.size());
            for (int i = 0; i < array.getCount(); i++) {
                array.set(i, this.dogs.get(i).getId());
            }
            return new DogInventoriesContainer(windowId, inventory, array);
        }

        @Override
        public Component getDisplayName() {
            return Component.translatable("container.doggytalents.dog_inventories");
        }
    }

    public static class TreatBagContainerProvider implements MenuProvider {

        private ItemStack stack;
        private int slotId;

        public TreatBagContainerProvider(ItemStack stackIn, int slotId) {
            this.stack = stackIn;
            this.slotId = slotId;
        }

        @Override
        public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
            return new TreatBagContainer(windowId, inventory, this.slotId, this.stack);
        }

        @Override
        public Component getDisplayName() {
            return Component.translatable("container.doggytalents.treat_bag");
        }
    }

    public static class DogArmorContainerProvider implements MenuProvider {

        private Dog dog;

        public DogArmorContainerProvider(Dog dog) {
            this.dog = dog;
        }

        @Override
        public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
            return new DogArmorContainer(windowId, inventory, dog);
        }

        @Override
        public Component getDisplayName() {
            return Component.translatable("container.doggytalents.dog_armor");
        }
    }

    public static class DoggyToolsMenuProvider implements MenuProvider {

        private Dog dog;

        public DoggyToolsMenuProvider(Dog dog) {
            this.dog = dog;
        }

        @Override
        public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
            return new DoggyToolsMenu(windowId, inventory, dog);
        }

        @Override
        public Component getDisplayName() {
            return Component.translatable("container.doggytalents.doggy_tools");
        }
    }

    public static void openPackPuppyScreen(ServerPlayer player, Dog dogIn) {
        if (dogIn.isAlive() && !dogIn.isDefeated()) {
            NetworkHooks.openScreen(player, new PackPuppyContainerProvider(dogIn), (buf) -> {
                buf.writeInt(dogIn.getId());
            });
        }
    }

    public static void openDogInventoriesScreen(ServerPlayer player, List<Dog> dogIn) {
        if (!dogIn.isEmpty()) {
            NetworkHooks.openScreen(player, new DogInventoriesContainerProvider(dogIn), (buf) -> {
                buf.writeInt(dogIn.size());
                for (Dog dog : dogIn) {
                    buf.writeInt(dog.getId());
                }
            });
        }
    }

    public static void openFoodBowlScreen(ServerPlayer player, FoodBowlTileEntity foodBowl) {
        NetworkHooks.openScreen(player, foodBowl, foodBowl.getBlockPos());
    }

    public static void openTreatBagScreen(ServerPlayer player, ItemStack stackIn, int slotId) {
        if (stackIn.getItem() == DoggyItems.TREAT_BAG.get()) {
            NetworkHooks.openScreen(player, new TreatBagContainerProvider(stackIn, slotId), buf -> {
                buf.writeVarInt(slotId);
                buf.writeItem(stackIn);
            });
        }
    }

    public static void openArmorScreen(ServerPlayer player, Dog dogIn) {
        if (dogIn.isAlive() && !dogIn.isDefeated()) {
            NetworkHooks.openScreen(player, new DogArmorContainerProvider(dogIn), (buf) -> {
                buf.writeInt(dogIn.getId());
            });
        }
    }

    public static void openDoggyToolsScreen(ServerPlayer player, Dog dogIn) {
        if (dogIn.isAlive() && !dogIn.isDefeated()) {
            NetworkHooks.openScreen(player, new DoggyToolsMenuProvider(dogIn), (buf) -> {
                buf.writeInt(dogIn.getId());
            });
        }
    }

}
