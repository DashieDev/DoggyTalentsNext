package doggytalents.common.entity;

import com.google.common.collect.ImmutableMap;
import doggytalents.DoggyAccessories;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class HelmetInteractHandler implements IDogItem {

    private static final Map<Item, RegistryObject<? extends Accessory>> MAPPING = new ImmutableMap.Builder<Item, RegistryObject<? extends Accessory>>()
        .put(Items.IRON_HELMET,      DoggyAccessories.IRON_HELMET)
        .put(Items.DIAMOND_HELMET,   DoggyAccessories.DIAMOND_HELMET)
        .put(Items.GOLDEN_HELMET,    DoggyAccessories.GOLDEN_HELMET)
        .put(Items.CHAINMAIL_HELMET, DoggyAccessories.CHAINMAIL_HELMET)
        .put(Items.TURTLE_HELMET,    DoggyAccessories.TURTLE_HELMET)
        .put(Items.NETHERITE_HELMET, DoggyAccessories.NETHERITE_HELMET)
        .put(Items.IRON_BOOTS,     DoggyAccessories.IRON_BOOTS)
        .put(Items.DIAMOND_BOOTS,     DoggyAccessories.DIAMOND_BOOTS)
        .put(Items.GOLDEN_BOOTS,     DoggyAccessories.GOLDEN_BOOTS)
        .put(Items.CHAINMAIL_BOOTS,     DoggyAccessories.CHAINMAIL_BOOTS)
        .put(Items.NETHERITE_BOOTS,     DoggyAccessories.NETHERITE_BOOTS)
        .put(Items.IRON_CHESTPLATE,  DoggyAccessories.IRON_BODY_PIECE)
        .put(Items.DIAMOND_CHESTPLATE, DoggyAccessories.DIAMOND_BODY_PIECE)
        .put(Items.GOLDEN_CHESTPLATE, DoggyAccessories.GOLDEN_BODY_PIECE)
        .put(Items.CHAINMAIL_CHESTPLATE, DoggyAccessories.CHAINMAIL_BODY_PIECE)
        .put(Items.NETHERITE_CHESTPLATE, DoggyAccessories.NETHERITE_BODY_PIECE)
        .put(Items.LEATHER_HELMET,   DoggyAccessories.LEATHER_HELMET)
        .put(Items.LEATHER_BOOTS,   DoggyAccessories.LEATHER_BOOTS)
        .put(Items.LEATHER_CHESTPLATE,   DoggyAccessories.LEATHER_BODY_PIECE)
        .put(Items.LEATHER_LEGGINGS, DoggyAccessories.IRON_BODY_PIECE)
        .put(Items.IRON_LEGGINGS,  DoggyAccessories.IRON_BODY_PIECE)
        .put(Items.DIAMOND_LEGGINGS, DoggyAccessories.DIAMOND_BODY_PIECE)
        .put(Items.GOLDEN_LEGGINGS, DoggyAccessories.GOLDEN_BODY_PIECE)
        .put(Items.CHAINMAIL_LEGGINGS, DoggyAccessories.CHAINMAIL_BODY_PIECE)
        .put(Items.NETHERITE_LEGGINGS, DoggyAccessories.NETHERITE_BODY_PIECE)
       .build();

    public static RegistryObject<? extends Accessory> getMappedResource(Item item) {
        var x = MAPPING.get(item);
        if (x != null) return x;
        var slot = LivingEntity.getEquipmentSlotForItem(new ItemStack(item));
        switch (slot) {
            case CHEST:
                return DoggyAccessories.IRON_BODY_PIECE; 
            case FEET:
                return DoggyAccessories.IRON_BOOTS;
            case HEAD:
                return DoggyAccessories.IRON_HELMET;
            case LEGS:
                return DoggyAccessories.IRON_BODY_PIECE;
            default:
                return DoggyAccessories.IRON_BODY_PIECE;
        }
    }

    @Override
    public InteractionResult processInteract(AbstractDog dogIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (dogIn.isTame() && dogIn.canInteract(playerIn)) {
            ItemStack stack = playerIn.getItemInHand(handIn);

            if (!stack.isEmpty()) {
                RegistryObject<? extends Accessory> associatedAccessory = MAPPING.get(stack.getItem());

                if (associatedAccessory != null) {
                    AccessoryInstance inst = associatedAccessory.get().createFromStack(stack.copy().split(1));

                    if (dogIn.addAccessory(inst)) {
                        dogIn.consumeItemFromStack(playerIn, stack);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }

        return InteractionResult.PASS;
    }

}
