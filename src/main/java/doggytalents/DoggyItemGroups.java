package doggytalents;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.common.util.DogBedUtil;
import doggytalents.common.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static doggytalents.DoggyItems.*;

public class DoggyItemGroups {

    public static CreativeModeTab GENERAL;
    public static CreativeModeTab DOG_BED; 

    public static void onCreativeTabRegister(CreativeModeTabEvent.Register ev) {
        Consumer<CreativeModeTab.Builder> GENERAL_BUILDER = builder ->
            builder.title(Component.translatable("itemGroup.doggytalents"))
            .icon(() -> {
                return new ItemStack(DoggyItems.TRAINING_TREAT.get());
            })
            .displayItems((a, b, c) -> {
                b.accept(THROW_BONE.get());
                b.accept(THROW_BONE_WET.get());
                b.accept(THROW_STICK.get());
                b.accept(THROW_STICK_WET.get());
                b.accept(TRAINING_TREAT.get());
                b.accept(SUPER_TREAT.get());
                b.accept(MASTER_TREAT.get());
                b.accept(DIRE_TREAT.get());
                b.accept(BREEDING_BONE.get());
                b.accept(COLLAR_SHEARS.get());
                b.accept(DOGGY_CHARM.get());
                b.accept(RADIO_COLLAR.get());
                b.accept(WOOL_COLLAR.get());
                b.accept(CREATIVE_COLLAR.get());
                b.accept(SPOTTED_COLLAR.get());
                b.accept(MULTICOLOURED_COLLAR.get());
                b.accept(RADAR.get());
                b.accept(CREATIVE_RADAR.get());
                b.accept(WHISTLE.get());
                b.accept(TREAT_BAG.get());
                b.accept(CHEW_STICK.get());
                b.accept(CAPE.get());
                b.accept(CAPE_COLOURED.get());
                b.accept(SUNGLASSES.get());
                b.accept(GUARD_SUIT.get());
                b.accept(LEATHER_JACKET.get());
                b.accept(TINY_BONE.get());
                b.accept(BIG_BONE.get());
                b.accept(OWNER_CHANGE.get());
            });

        GENERAL = ev.registerCreativeModeTab(Util.getResource("tabgeneral"), GENERAL_BUILDER);

        Consumer<CreativeModeTab.Builder> DOGBED_BUILDER = builder ->
            builder.title(Component.translatable("itemGroup.doggytalents.dogbed"))
            .icon(() -> {
                return new ItemStack(DoggyItems.TRAINING_TREAT.get());
            })
            .displayItems((a, b, c) -> {
                for (var beddingId : DoggyTalentsAPI.BEDDING_MATERIAL.get().getValues()) {
                    for (var casingId : DoggyTalentsAPI.CASING_MATERIAL.get().getValues()) {
                        b.accept(DogBedUtil.createItemStack(casingId, beddingId));
                    }
                }
            });

        DOG_BED = ev.registerCreativeModeTab(Util.getResource("tabdogbed"), DOGBED_BUILDER);
    }
}
