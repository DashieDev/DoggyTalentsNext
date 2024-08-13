package doggytalents;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.common.block.DogBedBlock;
import doggytalents.common.block.DogBedMaterialManager;
import doggytalents.common.item.itemgroup.DTNItemCategory;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.DogBedUtil;
import doggytalents.common.util.Util;
import doggytalents.forge_imitate.registry.DeferredRegister;
import doggytalents.forge_imitate.registry.RegistryObject;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.Row;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static doggytalents.DoggyItems.*;
import static doggytalents.DoggyBlocks.*;

public class DoggyItemGroups {

    //TODO using vanilla key, not forge's key ??? 
    public static final DeferredRegister<CreativeModeTab> ITEM_GROUP = DeferredRegister.create(() -> BuiltInRegistries.CREATIVE_MODE_TAB, Constants.MOD_ID);
    public static RegistryObject<CreativeModeTab> MAIN
        = register("main", () -> FabricItemGroup.builder()
            .title(Component.translatable("doggytalents.item_group.main"))
            .icon(() -> {
                return new ItemStack(DoggyItems.DOGGY_CHARM.get());
            })
            .displayItems((a, b) -> {
                DTNItemCategory.getAllItemOfCategory(DTNItemCategory::isMain)
                    .forEach(b::accept);
            }).build());

    public static RegistryObject<CreativeModeTab> AGRI
        = register("agri", () -> FabricItemGroup.builder()
            .title(Component.translatable("doggytalents.item_group.agri"))
            .icon(() -> {
                return new ItemStack(DoggyItems.RICE_WHEAT.get());
            })
            //.withTabsBefore(MAIN.getKey())
            .displayItems((a, b) -> {
                DTNItemCategory.getAllItemOfCategory(DTNItemCategory::isAgri)
                    .forEach(b::accept);
            }).build());

    public static RegistryObject<CreativeModeTab> STYLE
        = register("style", () -> FabricItemGroup.builder()
            .title(Component.translatable("doggytalents.item_group.style"))
            .icon(() -> {
                return new ItemStack(DoggyItems.DIVINE_RETRIBUTON.get());
            })
            //.withTabsBefore(AGRI.getKey())
            .displayItems((a, b) -> {
                DTNItemCategory.getAllItemOfCategory(DTNItemCategory::isStyle)
                    .forEach(b::accept);
            }).build());

    public static RegistryObject<CreativeModeTab> DOG_BED
        = register("dog_bed", () -> FabricItemGroup.builder()
            .title(Component.translatable("doggytalents.item_group.dog_bed"))
            .icon(
                DogBedUtil::createRandomBed
            )
            //.withTabsBefore(STYLE.getKey())
            .displayItems((a, b) -> {
                DTNItemCategory.getRandomBedsForTab().forEach(b::accept);
            }).build()); 

    public static RegistryObject<CreativeModeTab> MISC
        = register("misc", () -> FabricItemGroup.builder()
            .title(Component.translatable("doggytalents.item_group.misc"))
            .icon(() -> {
                return new ItemStack(DoggyItems.DOG_PLUSHIE_TOY.get());
            })
            //.withTabsBefore(DOG_BED.getId())
            .displayItems((a, b) -> {
                DTNItemCategory.getAllItemOfCategory(DTNItemCategory::isMisc)
                    .forEach(b::accept);
            }).build());

    public static RegistryObject<CreativeModeTab> register(String name, Supplier<CreativeModeTab> sup) {
        return ITEM_GROUP.register(name, sup);
    }
}
