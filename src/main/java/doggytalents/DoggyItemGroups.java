package doggytalents;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.common.block.DogBedBlock;
import doggytalents.common.block.DogBedMaterialManager;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.DogBedUtil;
import doggytalents.common.util.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.CreativeModeTabRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
    public static final DeferredRegister<CreativeModeTab> ITEM_GROUP = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);
    public static RegistryObject<CreativeModeTab> GENERAL
        = register("tabgeneral", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.doggytalents"))
            .icon(() -> {
                return new ItemStack(DoggyItems.DOGGY_CHARM.get());
            })
            .displayItems((a, b) -> {
                var allDTItemsIter = DoggyItems.ITEMS.getEntries();
                for (var val : allDTItemsIter) {
                    if (val.get() instanceof BlockItem blockItem) {
                        if (blockItem.getBlock() instanceof DogBedBlock) {
                            continue;
                        }
                    }
                    b.accept(val.get());
                }
            }).build());

    public static RegistryObject<CreativeModeTab> DOG_BED
        = register("tabdogbed", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.doggytalents.dogbed"))
            .icon(DogBedUtil::createRandomBed)
            .withTabsBefore(GENERAL.getKey())
            .displayItems((a, b) -> {
                final int maxBeddingEntries = 13;
                final int maxCasingEntries = 13;
                var beddingList = DogBedMaterialManager.getBeddings().entrySet().stream()
                    .map(x -> x.getValue())
                    .filter(x -> !(x instanceof DogBedMaterialManager.NaniBedding))
                    .collect(Collectors.toList());
                var casingList = DogBedMaterialManager.getCasings().entrySet().stream()
                    .map(x -> x.getValue())
                    .filter(x -> !(x instanceof DogBedMaterialManager.NaniCasing))
                    .collect(Collectors.toList());
                
                Collections.shuffle(beddingList);
                Collections.shuffle(casingList);
                for (int i = 0; i < Math.min(maxCasingEntries, casingList.size()); ++i) {
                    for (int j = 0; j < Math.min(maxBeddingEntries, beddingList.size()); ++j) {
                        var beddingId = beddingList.get(j);
                        var casingId = casingList.get(i);
                        b.accept(DogBedUtil.createItemStack(casingId, beddingId));
                    }
                }
            }).build()); 

    public static RegistryObject<CreativeModeTab> register(String name, Supplier<CreativeModeTab> sup) {
        return ITEM_GROUP.register(name, sup);
    }
}
