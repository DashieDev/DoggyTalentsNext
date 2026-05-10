package doggytalents.client.entity.render.world;

import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.UUID;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.DoggyItems;
import doggytalents.client.entity.render.RenderUtil;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.CanineTrackerItem;
import doggytalents.common.lib.Resources;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.CanineTrackerData.RequestPosUpdateData;
import doggytalents.common.util.ItemUtil;
import doggytalents.common.util.NBTUtil;
import doggytalents.common.util.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import doggytalents.common.network.PacketDistributor;

public class CanineTrackerLocateRenderer {

    private static Identifier DEFAULT_0 = Util.getVanillaResource("default/0");

    private static boolean locating;
    private static UUID locatingUUID;
    private static String locatingName;
    private static BlockPos locatingPos;
    private static int locateColor;

    private static WeakReference<Dog> cachedDog = new WeakReference<Dog>(null);
    
    public static void onWorldRenderLast(RenderLevelStageEvent.AfterTranslucentBlocks event) {
        if (!locating) return;
        var mc = Minecraft.getInstance();
        var player = mc.player;
        if (player != null && player.isSpectator()) return;
        var camera = mc.gameRenderer.getMainCamera();
        float partialTick = mc.getDeltaTracker().getGameTimeDeltaPartialTick(false);
        Vec3 dog_pos = null;
        if (cachedDog.get() != null) {
            double d0 = Mth.lerp((double)partialTick, cachedDog.get().xOld, cachedDog.get().getX());
            double d1 = Mth.lerp((double)partialTick, cachedDog.get().yOld, cachedDog.get().getY());
            double d2 = Mth.lerp((double)partialTick, cachedDog.get().zOld, cachedDog.get().getZ());
            dog_pos = new Vec3(d0, d1 + 1, d2);
        } else {
            dog_pos = new Vec3(locatingPos.getX(), locatingPos.getY() + 1, locatingPos.getZ());
        }
        var camera_pos = camera.position().add(0, -0.2, 0);
        var off_dog_camera = dog_pos.subtract(camera_pos);
        var d_dog_camera = off_dog_camera.length();
        var off_txt = off_dog_camera;
        if (d_dog_camera > 5) {
            off_txt = off_txt
                .normalize()
                .scale(5);
        }
        drawFloatingDistanceText(locatingName, d_dog_camera, off_txt, camera);
    }

    public static void onWorldLogOut() {
        stopLocating();
    }

    public static void drawFloatingDistanceText(String name, double distance, Vec3 off_from_player, Camera camera) {
        var text_mat = new Matrix4f();
        text_mat.translate((float)off_from_player.x(), (float)off_from_player.y(), (float)off_from_player.z());
        text_mat.rotate(camera.rotation());
        text_mat.scale(0.02F, -0.02F, 0.02F);
        var font = Minecraft.getInstance().font;

        var dog_name = name;
        if (dog_name.length() > 14) {
            dog_name = dog_name.substring(0, 14) + "..";
        }

        int hl_color = getHighlightColor(distance);

        var line1 = Component.translatable("item.doggytalents.radar.locate.line1", 
            Component.literal(dog_name).withStyle(
                Style.EMPTY.withBold(true)
            )  
        );
        MutableComponent line2 = null;
        if (distance < 5) {
            line2 = Component.translatable("item.doggytalents.radar.locate.line2.close")
                .withStyle(
                    Style.EMPTY.withBold(true)
                    .withColor(hl_color)
                );
        } else {
            line2 = Component.translatable("item.doggytalents.radar.locate.line2.far",
                Component.literal("" + Mth.ceil(distance)).withStyle(
                    Style.EMPTY.withBold(true)
                    .withColor(hl_color)
                )
            );
        }

        var bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();

        // This line is required for the below lines to work (pipeline flush)
        bufferSource.getBuffer(net.minecraft.client.renderer.rendertype.RenderTypes.text(Resources.SMALL_WIDGETS));

        float tX = (float)(-font.width(line1) / 2);
        float tY = 0;
        font.drawInBatch(line1, tX, tY, 0xffffffff, false, text_mat, bufferSource, DisplayMode.SEE_THROUGH, 0, 15728880);
        tX = (float)(-font.width(line2) / 2);
        tY += font.lineHeight + 3;
        font.drawInBatch(line2, tX, tY, 0xffffffff, false, text_mat, bufferSource, DisplayMode.SEE_THROUGH, 0, 15728880);;
        bufferSource.endLastBatch();
    }
    
    public static int getHighlightColor(double distance) {
        int main_color = locateColor == 0 ? 
            0xffffea2e : locateColor;
        int[] main_color3 = Util.rgbIntToIntArray(main_color);

        if (distance >= 160) return 0xffffffff;
        distance -= 32;
        if (distance <= 0) return main_color; // 255, 234, 46

        double progress = Mth.clamp((128d-distance)/128d, 0, 1);

        int[] color = {0xff, 0xff, 0xff};
        color[0] += (main_color3[0]-255)*progress;
        color[1] += (main_color3[1]-255)*progress;
        color[2] += (main_color3[2]-255)*progress;
        
        return 0xff000000 | RenderUtil.rgbToInt(color);
    }

    public static void tickUpdate(ClientTickEvent.Post event) {
        //if (event.phase != Phase.END) return;
        var mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null || mc.level == null) return;
        if (player.tickCount % 4 != 0) return;
        var tagOptional = getRadarTagIfHoldingAndExist(player);
        if (locating) {
            if (tagOptional.isEmpty()) {
                stopLocating(); return;
            }
            var tag = tagOptional.get();
            if (!NBTUtil.getUniqueId(tag, "uuid").equals(locatingUUID)) {
                setLocating(tag);
            }
            updateCache(player);
            if (player.tickCount % 256 == 0)
                requestServerForPosUpdate();
        } else {
            if (tagOptional.isEmpty()) return;
            var tag = tagOptional.get();
            setLocating(tag);
        }
    }

    private static void updateCache(Player player) {
        if (cachedDog.get() != null) {
            if (
                cachedDog.get().isRemoved()
                || !cachedDog.get().getUUID().equals(locatingUUID)
            )
                cachedDog = new WeakReference<Dog>(null);
        }
        if (locatingUUID == null || player.tickCount % 8 != 0) return;
        var dogs = player.level().getEntitiesOfClass(Dog.class, 
            // consider wider search radius
            player.getBoundingBox().inflate(24, 8, 24),
            dog -> dog.getUUID().equals(locatingUUID));
        if (!dogs.isEmpty()) {
            cachedDog = new WeakReference<Dog>(dogs.get(0));
        }
    }

    private static Optional<CompoundTag> getRadarTagIfHoldingAndExist(Player player) {
        var item_main = player.getItemInHand(InteractionHand.MAIN_HAND);
        var item_off = player.getItemInHand(InteractionHand.OFF_HAND);
        ItemStack radar = null;
        if (item_main.getItem() instanceof CanineTrackerItem) 
            radar = item_main;
        else if (item_off.getItem() instanceof CanineTrackerItem)
            radar = item_off;
        if (radar == null) return Optional.empty();
        if (!ItemUtil.hasTag(radar)) return Optional.empty();
        var tag = ItemUtil.getTag(radar);
        if (tag == null || !NBTUtil.hasUniqueId(tag, "uuid")) return Optional.empty();
        return Optional.of(tag);
    }

    private static void requestServerForPosUpdate() {
        if (locatingUUID == null) return;
        if (locatingPos == null) return;

        PacketHandler.send(
            PacketDistributor.SERVER.noArg(), 
            new RequestPosUpdateData(locatingUUID, locatingPos)
        );
    }

    public static void correctPos(UUID uuid, BlockPos pos) {
        if (!locating) return;
        if (uuid == null) return;
        if (!uuid.equals(locatingUUID)) return;
        locatingPos = pos;
    }

    public static void setLocating(CompoundTag tag) {
        var uuid = NBTUtil.getUniqueId(tag, "uuid");
        var name = tag.getStringOr("name", "");
        var posX = tag.getIntOr("posX", 0);
        var posY = tag.getIntOr("posY", 0);
        var posZ = tag.getIntOr("posZ", 0);
        var color = tag.getIntOr("locateColor", 0);
        setLocating(uuid, name, new BlockPos(posX, posY, posZ), color);
    }

    public static void setLocating(UUID id, String name, BlockPos pos, int color) {
        if (id == null || name == null || pos == null) return;
        locating = true;
        locatingUUID = id;
        locatingName = name;
        locatingPos = pos;
        locateColor = color;
        
        if (cachedDog.get() != null) {
            if (
                cachedDog.get().isRemoved()
                || !cachedDog.get().getUUID().equals(locatingUUID)
            )
                cachedDog = new WeakReference<Dog>(null);
        }
    }

    public static void stopLocating() {
        locating = false;
        locatingUUID = null;
        locatingName = null;
        locatingPos = null;
        locateColor = 0;
        cachedDog = new WeakReference<Dog>(null);
    }

}
