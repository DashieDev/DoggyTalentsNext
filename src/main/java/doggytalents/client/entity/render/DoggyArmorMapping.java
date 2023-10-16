package doggytalents.client.entity.render;

import com.google.common.collect.ImmutableMap;
import doggytalents.DoggyAccessories;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.lib.Resources;
import net.minecraft.resources.ResourceLocation;
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

public class DoggyArmorMapping {

    private static final Map<Item, ResourceLocation> MAPPING = new ImmutableMap.Builder<Item, ResourceLocation>()
        .put(Items.IRON_HELMET,      Resources.IRON_HELMET)
        .put(Items.DIAMOND_HELMET,   Resources.DIAMOND_HELMET)
        .put(Items.GOLDEN_HELMET,    Resources.GOLDEN_HELMET)
        .put(Items.CHAINMAIL_HELMET, Resources.CHAINMAIL_HELMET)
        .put(Items.TURTLE_HELMET,    Resources.TURTLE_HELMET)
        .put(Items.NETHERITE_HELMET, Resources.NETHERITE_HELMET)
        .put(Items.IRON_BOOTS,     Resources.IRON_BODY_PIECE)
        .put(Items.DIAMOND_BOOTS,     Resources.DIAMOND_BODY_PIECE)
        .put(Items.GOLDEN_BOOTS,     Resources.GOLDEN_BODY_PIECE)
        .put(Items.CHAINMAIL_BOOTS,     Resources.CHAINMAIL_BODY_PIECE)
        .put(Items.NETHERITE_BOOTS,     Resources.NETHERITE_BODY_PIECE)
        .put(Items.IRON_CHESTPLATE,  Resources.IRON_BODY_PIECE)
        .put(Items.DIAMOND_CHESTPLATE, Resources.DIAMOND_BODY_PIECE)
        .put(Items.GOLDEN_CHESTPLATE, Resources.GOLDEN_BODY_PIECE)
        .put(Items.CHAINMAIL_CHESTPLATE, Resources.CHAINMAIL_BODY_PIECE)
        .put(Items.NETHERITE_CHESTPLATE, Resources.NETHERITE_BODY_PIECE)
        .put(Items.LEATHER_HELMET,   Resources.LEATHER_HELMET)
        .put(Items.LEATHER_BOOTS,   Resources.LEATHER_BOOTS)
        .put(Items.LEATHER_CHESTPLATE,   Resources.LEATHER_BODY_PIECE)
        .put(Items.LEATHER_LEGGINGS, Resources.IRON_BODY_PIECE)
        .put(Items.IRON_LEGGINGS,  Resources.IRON_BODY_PIECE)
        .put(Items.DIAMOND_LEGGINGS, Resources.DIAMOND_BODY_PIECE)
        .put(Items.GOLDEN_LEGGINGS, Resources.GOLDEN_BODY_PIECE)
        .put(Items.CHAINMAIL_LEGGINGS, Resources.CHAINMAIL_BODY_PIECE)
        .put(Items.NETHERITE_LEGGINGS, Resources.NETHERITE_BODY_PIECE)
       .build();

    public static ResourceLocation getMappedResource(Item item) {
        var x = MAPPING.get(item);
        if (x != null) return x;
        var slot = LivingEntity.getEquipmentSlotForItem(new ItemStack(item));
        switch (slot) {
            case CHEST:
                return Resources.IRON_BODY_PIECE; 
            case FEET:
                return Resources.IRON_BOOTS;
            case HEAD:
                return Resources.IRON_HELMET;
            case LEGS:
                return Resources.IRON_BODY_PIECE;
            default:
                return Resources.IRON_BODY_PIECE;
        }
    }
}
