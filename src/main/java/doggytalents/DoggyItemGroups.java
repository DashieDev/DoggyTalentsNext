package doggytalents;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.common.block.DogBedBlock;
import doggytalents.common.util.DogBedUtil;
import doggytalents.common.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static doggytalents.DoggyItems.*;
import static doggytalents.DoggyBlocks.*;

public class DoggyItemGroups {

    public static CreativeModeTab GENERAL;
    public static CreativeModeTab DOG_BED; 

    public static void onCreativeTabRegister(CreativeModeTabEvent.Register ev) {
        Consumer<CreativeModeTab.Builder> GENERAL_BUILDER = builder ->
            builder.title(Component.translatable("itemGroup.doggytalents"))
            .icon(() -> {
                return new ItemStack(DoggyItems.TRAINING_TREAT.get());
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
            });

        GENERAL = ev.registerCreativeModeTab(Util.getResource("tabgeneral"), GENERAL_BUILDER);

        Consumer<CreativeModeTab.Builder> DOGBED_BUILDER = builder ->
            builder.title(Component.translatable("itemGroup.doggytalents.dogbed"))
            .icon(DogBedUtil::createRandomBed)
            .displayItems((a, b) -> {
                for (var beddingId : DoggyTalentsAPI.BEDDING_MATERIAL.get().getValues()) {
                    for (var casingId : DoggyTalentsAPI.CASING_MATERIAL.get().getValues()) {
                        b.accept(DogBedUtil.createItemStack(casingId, beddingId));
                    }
                }
            });

        DOG_BED = ev.registerCreativeModeTab(
            Util.getResource("tabdogbed"),
            List.of(), List.of(GENERAL), 
            DOGBED_BUILDER
        );
    }
}
