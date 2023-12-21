package doggytalents.common.util;

import com.google.common.collect.Lists;

import doggytalents.common.block.DogBathBlock;
import doggytalents.common.lib.Constants;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Util {

    private static final DecimalFormat dfShort = new DecimalFormat("0.0");
    private static final DecimalFormat dfShortDouble = new DecimalFormat("0.00");

    public static String format1DP(double value) {
        return Util.dfShort.format(value);
    }

    public static String format2DP(double value) {
        return Util.dfShortDouble.format(value);
    }

    public static boolean isPointInRegion(int x, int y, int width, int height, double mouseX, double mouseY) {
        return mouseX >= x - 1 && mouseX < x + width + 1 && mouseY >= y - 1 && mouseY < y + height + 1;
    }

    public static float[] rgbIntToFloatArray(int rgbInt) {
        int r = (rgbInt >> 16) & 255;
        int g = (rgbInt >> 8) & 255;
        int b = (rgbInt >> 0) & 255;

        return new float[] {r / 255F, g / 255F, b / 255F};
    }

    public static int[] rgbIntToIntArray(int rgbInt) {
        int r = (rgbInt >> 16) & 255;
        int g = (rgbInt >> 8) & 255;
        int b = (rgbInt >> 0) & 255;

        return new int[] {r, g, b};
    }

    public static int[] srgbArrayToIntArray(float[] color_srgb) {
        int[] color_rgb = new int[color_srgb.length]; 
        for (int i = 0; i < color_srgb.length; ++i) {
            color_rgb[i] = (int)(color_srgb[i]*255);
        }
        return color_rgb;
    }

    public static int srgbArrayToInt(float[] color_srgb) {
        var color_rgb = srgbArrayToIntArray(color_srgb);
        return color_rgb[0] << 16
             | color_rgb[1] << 8
             | color_rgb[2];
    }

    public static float getRelativeLuminance(float sr, float sg, float sb) {
        sr = sr < 0.03928f ? sr/12.92f : (float) Math.pow((sr + 0.055) / 1.055 , 2.4);
        sg = sg < 0.03928f ? sg/12.92f : (float) Math.pow((sg + 0.055) / 1.055 , 2.4);
        sb = sb < 0.03928f ? sb/12.92f : (float) Math.pow((sb + 0.055) / 1.055 , 2.4);
        return sr + sg + sb;
    }

    public static float getRelativeLuminance(int color) {
        var arr = rgbIntToFloatArray(color);
        return getRelativeLuminance(arr[0], arr[1], arr[2]);
    }

    public static int getTextBlackOrWhite(int background_color) {
        float luminance = getRelativeLuminance(background_color);
        float black_contrast = (luminance + 0.05f) / 0.05f;
        float white_contrast = (1.0f + 0.05f) / (luminance + 0.05f);
        return black_contrast > white_contrast ? 0 : 0xffffffff;
    }

    public static int colorDye(int startColor, DyeColor dye) {
        return colorDye(startColor, Lists.newArrayList(dye));
    }

    public static int colorDye(int startColor, Collection<DyeColor> dyes) {
        var colors = new ArrayList<int[]>(dyes.size());
        for (var dye : dyes) {
            var color_srgb = dye.getTextureDiffuseColors();
            var color_rgb = new int[color_srgb.length];
            for (int i = 0; i < color_srgb.length; ++i) {
                color_rgb[i] = (int)(color_srgb[i]*255);
            }
            colors.add(color_rgb);
        }
        
        if (startColor != -1) {
            colors.add(0, rgbIntToIntArray(startColor));
        }

        return colorDye(colors);
    }

    public static int colorDye(Collection<int[]> colors) {
        int[] temp = new int[3];
        int maxCompSum = 0;

        for (int[] color : colors) {
            maxCompSum += Math.max(color[0], Math.max(color[1], color[2]));
            temp[0] += color[0];
            temp[1] += color[1];
            temp[2] += color[2];
         }

        int redAve = temp[0] / colors.size();
        int greenAve = temp[1] / colors.size();
        int blueAve = temp[2] / colors.size();

        float maxAve = (float) maxCompSum / (float) colors.size();
        float max = Math.max(redAve, Math.max(greenAve, blueAve));

        redAve = (int)(redAve * maxAve / max);
        greenAve = (int)(greenAve * maxAve / max);
        blueAve = (int)(blueAve * maxAve / max);

        int finalColor = (redAve << 8) + greenAve;
        finalColor = (finalColor << 8) + blueAve;
        return finalColor;
    }

    /**
     * @param name The path of the resource
     */
    public static ResourceLocation getResource(String name) {
        return getResource(Constants.MOD_ID, name);
    }

    public static ResourceLocation getResource(String modId, String name) {
        return new ResourceLocation(modId, name);
    }

    public static String getResourcePath(String name) {
        return getResourcePath(Constants.MOD_ID, name);
    }

    /**
     * @param modId The namespace
     * @param name The path
     * @return The total path of the resource e.g "minecraft:air"
     */
    public static String getResourcePath(String modId, String name) {
        return getResource(modId, name).toString();
    }

    public static FriendlyByteBuf createBuf() {
        return new FriendlyByteBuf(Unpooled.buffer());
    }

    // From net.minecraft.util.Util
    public static <T> T make(Supplier<T> supplier) {
        return supplier.get();
    }

    // From net.minecraft.util.Util
    public static <T> T make(T object, Consumer<T> consumer) {
        consumer.accept(object);
        return object;
    }

    // From net.minecraft.util.Util but for RegistryObject
    public static <T> RegistryObject<T> acceptOrElse(RegistryObject<T> opt, Consumer<T> consumer, Runnable orElse) {
        if (opt.isPresent()) {
            consumer.accept(opt.get());
        } else {
            orElse.run();
        }

        return opt;
    }

    public static <T> Optional<T> acceptOrElse(Optional<T> opt, Consumer<T> consumer, Runnable orElse) {
        if (opt.isPresent()) {
            consumer.accept(opt.get());
        } else {
            orElse.run();
        }

        return opt;
    }

    public static <T> boolean allMatch(Iterable<T> input, Predicate<T> matcher) {
        Objects.requireNonNull(matcher);
        for (T e : input) {
            if (!matcher.test(e)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean anyMatch(Iterable<T> input, Predicate<T> matcher) {
        Objects.requireNonNull(matcher);
        for (T e : input) {
            if (matcher.test(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes various registry related objects and returns the
     * registry id of the object it is representing
     */
    public static ResourceLocation getRegistryId(Object obj) {
        if (obj instanceof ResourceLocation) {
            return (ResourceLocation) obj;
        }

        if (obj instanceof String) {
            // Returns null when namespace or path contain invalid
            // characters
            return ResourceLocation.tryParse((String) obj);
        }

        // if (obj instanceof IForgeRegistryEntry) {
        //     return ((IForgeRegistryEntry) obj).getRegistryName();
        // }

        // if (obj instanceof IRegistryDelegate) {
        //     return ((IRegistryDelegate) obj).name();
        // }

        if (obj instanceof RegistryObject) {
            return ((RegistryObject) obj).getId();
        }


        return null;
    }

    public static BlockPos containing(double x, double y, double z) {
        return new BlockPos(x, y, z);
    }
    
    public static Vec3 atLowerCornerWithOffset(Vec3i iVec, double offX, double offY, double offZ) {
        return Vec3.atLowerCornerOf(iVec).add(offX, offY, offZ);
    }

    public static long tickMayWithPartialToMillis(double tickMayWithPartial) {
        double second = tickMayWithPartial/20D;
        return Mth.lfloor(second*1000D);
    }

    public static double millisToTickMayWithPartial(long millis) {
        double second = ((double)millis)/1000D;
        return second*20D;
    }

}
