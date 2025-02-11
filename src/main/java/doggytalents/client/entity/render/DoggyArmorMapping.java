package doggytalents.client.entity.render;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import doggytalents.DoggyAccessories;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.event.ClientEventHandler;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.ItemUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentModel;
import net.minecraft.world.item.equipment.EquipmentModel.LayerType;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DoggyArmorMapping {

    private static final Map<Item, ResourceLocation> LEGACY_MAPPING = new ImmutableMap.Builder<Item, ResourceLocation>()
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

    private static Map<Item, ResourceLocation> MAPPING = Maps.newConcurrentMap();

    private static ResourceLocation computeArmorTexture(Item item, Dog dog, ItemStack stack) {
        if (!(item instanceof ArmorItem armor))
            return Resources.DEFAULT_DOG_ARMOR;
        //1.21.3+
        var armor_layers_21_3 = getEquippimentLayer_21_3(stack); 

        // var preferedLocOptional = computePreferedArmorLoc(item, dog, stack, armor);
        // if (preferedLocOptional.isPresent())
        //     return preferedLocOptional.get();
        
        if (armor_layers_21_3.isEmpty())
            return Resources.DEFAULT_DOG_ARMOR; 

        var armorLoc = armor_layers_21_3
            .get(0)
            .getTextureLocation(LayerType.HUMANOID);
        if (!(ClientEventHandler.vertifyArmorTexture(armorLoc)))
            return Resources.DEFAULT_DOG_ARMOR;
        
        return armorLoc;
    }

    // private static Optional<ResourceLocation> computePreferedArmorLoc(Item item, Dog dog, ItemStack stack, ArmorItem armor) {
    //     if (armor.getMaterial().value().layers().isEmpty())
    //         return Optional.empty(); 
    //     var material_layer = armor.getMaterial().value().layers().get(0);
    //     var preferedLoc = armor.getArmorTexture(stack, dog, EquipmentSlot.CHEST, material_layer, false);
    //     if (preferedLoc == null) 
    //         preferedLoc = Resources.DEFAULT_DOG_ARMOR;
    //     if (preferedLoc.equals(Resources.DEFAULT_DOG_ARMOR))
    //         return Optional.empty();
    //     if (!(ClientEventHandler.vertifyArmorTexture(preferedLoc)))
    //         return Optional.empty();
    //     return Optional.ofNullable(preferedLoc);
    // }

    public static ResourceLocation getMappedResource(Item item, Dog dog, ItemStack stack) {
        if (ConfigHandler.CLIENT.USE_LEGACY_DOG_ARMOR_RENDER.get())
            return getLegacyMappedResource(dog, item);

        return MAPPING.computeIfAbsent(item, x -> computeArmorTexture(x, dog, stack));
    }

    public static ResourceLocation getLegacyMappedResource(Dog dog, Item item) {
        var x = LEGACY_MAPPING.get(item);
        if (x != null) return x;
        var slot = dog.getEquipmentSlotForItem(new ItemStack(item));
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



    private static List<EquipmentModel.Layer> getEquippimentLayer_21_3(ItemStack stack) {
        var equip = ItemUtil.getEquippable_1_21_3(stack);
        if (equip == null)
            return List.of();
        var model_optional = equip.model();
        if (!model_optional.isPresent())
            return List.of();
        var model_loc = model_optional.get();
        var mc = Minecraft.getInstance();
        var model = mc.getEquipmentModels().get(model_loc);
        if (model == null)
            return List.of();
        var model_layers = model.getLayers(EquipmentModel.LayerType.HUMANOID);
        if (model_layers == null)
            return List.of();
    
        return model_layers;
    }
}
