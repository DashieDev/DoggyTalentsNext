package doggytalents.common.event;

import java.util.Optional;
import java.util.function.Consumer;

import doggytalents.common.fabric_helper.pack_handler.FabricPackHandler;
import doggytalents.common.backward_imitate.DataUtil_1_21_9;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.BuiltInPackSource;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.Pack.Position;
import net.minecraft.server.packs.repository.PackSource;

public class PackHandler { // Reflect change in FabricPackHandler

    public static final String BUILTIN_PREFIX = "mod/" + Util.getResource("builtin");
    public static final String ALT_RECIPE_1 = "alt_recipe_1";

    // public static void onAddPackFinder(AddPackFindersEvent event) {
    //     if (event.getPackType() == PackType.SERVER_DATA) {
    //         event.addRepositorySource(PackHandler::onRegisterServerPack);
    //     }
    // }

    // private static void onRegisterServerPack(Consumer<Pack> pack_consumer) {
    //     findAndCreatePack(PackType.SERVER_DATA, ALT_RECIPE_1,
    //         Component.literal("DTN Recipe Pack I."), 
    //         PackSource.FEATURE, false, Position.TOP)
    //         .ifPresent(pack_consumer::accept);
    // }

    public static String getBuiltinPackLocationNamespace(String id) {
        return "data/" + Constants.MOD_ID + "/datapacks/" + id;
    }

    public static Optional<ResourceLocation> onPackLoadIcon(Pack pack) {
        var pack_id = pack.getId();
        if (isBuiltinPack(pack_id)) {
            return Optional.of(Resources.DTN_PACK_ICON);
        }
        return Optional.empty();
    }

    // private static Optional<Pack> findAndCreatePack(PackType type, String id, Component display_name,
    //     PackSource source, boolean always_active, Pack.Position position) {
        
    //     var mod_path = getBuiltinPackLocationNamespace(id);
        
    //     var mod_info = ModList.get().getModContainerById(Constants.MOD_ID)
    //         .orElseThrow().getModInfo();
    //     var version = mod_info.getVersion().toString();
        
        // var res_path = mod_info.getOwningFile().getFile()
        //     .findResource(mod_path);
        
    //     var known_pack_info = 
    //         new KnownPack(Constants.MOD_ID, id, version);
    //     var pack_location_info = new PackLocationInfo(
    //         BUILTIN_PREFIX + "/" + id, display_name, 
    //         source, Optional.of(known_pack_info));
        
        // var pack = Pack.readMetaAndCreate(
        //     pack_location_info,
        //     BuiltInPackSource.fromName(
        //         DataUtil_1_21_9.getPackResourceSupplier(mod_info, mod_path)),
        //     type,
        //     new PackSelectionConfig(always_active, position, false));
        // return Optional.ofNullable(pack);
    //}

    public static boolean isBuiltinPack(String id) {
        return FabricPackHandler.isBuiltinPack(id);
    }

}
