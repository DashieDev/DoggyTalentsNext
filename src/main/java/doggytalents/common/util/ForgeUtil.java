package doggytalents.common.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import doggytalents.common.lib.Constants;
import net.neoforged.fml.ModList;

public class ForgeUtil {

    public static Optional<Path> getBundledModResource(String assetPath) {
        return getBundledModResource(Constants.MOD_ID, assetPath);
    }
    
    public static Optional<Path> getBundledModResource(String modId, String assetPath) {
        final var full_path_names = Stream.concat(
            List.of("assets", modId).stream(), 
            List.of(assetPath.split("/")).stream()
        ).toArray(String[]::new);

        final var mod_file = ModList.get().getModFileById(modId).getFile();
        final var result = mod_file.findResource(full_path_names);

        if (!Files.exists(result))
            return Optional.empty();

        return Optional.of(result);
    }

    

}
