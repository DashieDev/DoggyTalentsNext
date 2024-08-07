package doggytalents;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.common.block.DogBedBlock;
import doggytalents.common.block.DogBedMaterialManager;
import doggytalents.common.item.itemgroup.DTNItemCategory;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.DogBedUtil;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DoggyItemGroups {

    //TODO using vanilla key, not forge's key ??? 
    public static final DeferredRegister<CreativeModeTab> ITEM_GROUP = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);
    public static DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN
        = register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("doggytalents.item_group.main"))
            .icon(() -> {
                return new ItemStack(DoggyItems.DOGGY_CHARM.get());
            })
            .displayItems((a, b) -> {
                DTNItemCategory.getAllItemOfCategory(DTNItemCategory::isMain)
                    .forEach(b::accept);
            }).build());

    public static DeferredHolder<CreativeModeTab, CreativeModeTab> AGRI
        = register("agri", () -> CreativeModeTab.builder()
            .title(Component.translatable("doggytalents.item_group.agri"))
            .icon(() -> {
                return new ItemStack(DoggyItems.RICE_WHEAT.get());
            })
            .withTabsBefore(MAIN.getKey())
            .displayItems((a, b) -> {
                DTNItemCategory.getAllItemOfCategory(DTNItemCategory::isAgri)
                    .forEach(b::accept);
            }).build());

    public static DeferredHolder<CreativeModeTab, CreativeModeTab> STYLE
        = register("style", () -> CreativeModeTab.builder()
            .title(Component.translatable("doggytalents.item_group.style"))
            .icon(() -> {
                return new ItemStack(DoggyItems.DIVINE_RETRIBUTON.get());
            })
            .withTabsBefore(AGRI.getKey())
            .displayItems((a, b) -> {
                DTNItemCategory.getAllItemOfCategory(DTNItemCategory::isStyle)
                    .forEach(b::accept);
            }).build());

    public static DeferredHolder<CreativeModeTab, CreativeModeTab> DOG_BED
        = register("dog_bed", () -> CreativeModeTab.builder()
            .title(Component.translatable("doggytalents.item_group.dog_bed"))
            .icon(
                DogBedUtil::createRandomBed
            )
            .withTabsBefore(STYLE.getKey())
            .displayItems((a, b) -> {
                DTNItemCategory.getRandomBedsForTab().forEach(b::accept);
            }).build()); 

    public static DeferredHolder<CreativeModeTab, CreativeModeTab> MISC
        = register("misc", () -> CreativeModeTab.builder()
            .title(Component.translatable("doggytalents.item_group.misc"))
            .icon(() -> {
                return new ItemStack(DoggyItems.DOG_PLUSHIE_TOY.get());
            })
            .withTabsBefore(DOG_BED.getKey())
            .displayItems((a, b) -> {
                DTNItemCategory.getAllItemOfCategory(DTNItemCategory::isMisc)
                    .forEach(b::accept);
            }).build());

    public static DeferredHolder<CreativeModeTab, CreativeModeTab> register(String name, Supplier<CreativeModeTab> sup) {
        return ITEM_GROUP.register(name, sup);
    }
}
