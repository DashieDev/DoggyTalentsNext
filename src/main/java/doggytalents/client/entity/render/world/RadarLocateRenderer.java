package doggytalents.client.entity.render.world;

import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.UUID;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.DoggyItems;
import doggytalents.client.entity.render.RenderUtil;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.RadarItem;
import doggytalents.common.lib.Resources;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.RadarData.RequestPosUpdateData;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.network.PacketDistributor;

public class RadarLocateRenderer {

    private static ResourceLocation DEFAULT_0 = new ResourceLocation("default/0");

    private static boolean locating;
    private static UUID locatingUUID;
    private static String locatingName;
    private static BlockPos locatingPos;

    private static WeakReference<Dog> cachedDog = new WeakReference<Dog>(null);
    
    public static void onWorldRenderLast(RenderLevelStageEvent event) {
        if (event.getStage() != Stage.AFTER_TRANSLUCENT_BLOCKS) 
            return;
        if (!locating) return;
        var player = Minecraft.getInstance().player;
        if (player != null && player.isSpectator()) return;
        Vec3 dog_pos = null;
        if (cachedDog.get() != null) {
            double d0 = Mth.lerp((double)event.getPartialTick(), cachedDog.get().xOld, cachedDog.get().getX());
            double d1 = Mth.lerp((double)event.getPartialTick(), cachedDog.get().yOld, cachedDog.get().getY());
            double d2 = Mth.lerp((double)event.getPartialTick(), cachedDog.get().zOld, cachedDog.get().getZ());
            dog_pos = new Vec3(d0, d1 + 1, d2);
        } else {
            dog_pos = new Vec3(locatingPos.getX(), locatingPos.getY() + 1, locatingPos.getZ());
        }
        var camera = event.getCamera();
        var camera_pos = camera.getPosition().add(0, -0.2, 0);
        var off_dog_camera = dog_pos.subtract(camera_pos);
        var d_dog_camera = off_dog_camera.length();
        var off_txt = off_dog_camera;
        if (d_dog_camera > 5) {
            off_txt = off_txt
                .normalize()
                .scale(5);
        }
        drawFloatingDistanceText(locatingName, event.getPoseStack(), d_dog_camera, off_txt, camera);
    }

    public static void drawFloatingDistanceText(String name, PoseStack stack, double distance, Vec3 off_from_player, Camera camera) {
        stack.pushPose();
        stack.translate(off_from_player.x(), off_from_player.y(), off_from_player.z());
        stack.mulPose(camera.rotation());
        stack.scale(-0.02F, -0.02F, 0.02F);
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

        //TODO For some reason, this line is required for the below lines to work...
        bufferSource.getBuffer(RenderType.text(Resources.SMALL_WIDGETS));

        float tX = (float)(-font.width(line1) / 2);
        float tY = 0;
        font.drawInBatch(line1, tX, tY, 0xffffffff, false, stack.last().pose(), bufferSource, DisplayMode.SEE_THROUGH, 0, 15728880);
        tX = (float)(-font.width(line2) / 2);
        tY += font.lineHeight + 3;
        font.drawInBatch(line2, tX, tY, 0xffffffff, false, stack.last().pose(), bufferSource, DisplayMode.SEE_THROUGH, 0, 15728880);;
        bufferSource.endLastBatch();

        stack.popPose();
    }
    
    public static int getHighlightColor(double distance) {
        if (distance >= 84) return 0xffffffff;
        distance -= 20;
        if (distance <= 0) return 0xff00ff5e;

        double progress = Mth.clamp((64-distance)/64d, 0, 1);

        int[] color = {0xff, 0xff, 0xff};
        color[0] += -255*progress;
        //color[1] += 0;
        color[2] += -161*progress;
        
        return 0xff000000 | RenderUtil.rgbToInt(color);
    }

    public static void tickUpdate(ClientTickEvent event) {
        if (event.phase != Phase.END) return;
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
            if (!tag.getUUID("uuid").equals(locatingUUID)) {
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
        var dogs = player.level.getEntitiesOfClass(Dog.class, 
            //TODO wider ?
            player.getBoundingBox().inflate(12, 0, 12),
            dog -> dog.getUUID().equals(locatingUUID));
        if (!dogs.isEmpty()) {
            cachedDog = new WeakReference<Dog>(dogs.get(0));
        }
    }

    private static Optional<CompoundTag> getRadarTagIfHoldingAndExist(Player player) {
        var item_main = player.getItemInHand(InteractionHand.MAIN_HAND);
        var item_off = player.getItemInHand(InteractionHand.OFF_HAND);
        ItemStack radar = null;
        if (item_main.getItem() instanceof RadarItem) 
            radar = item_main;
        else if (item_off.getItem() instanceof RadarItem)
            radar = item_off;
        if (radar == null) return Optional.empty();
        if (!radar.hasTag()) return Optional.empty();
        var tag = radar.getTag();
        if (tag == null || !tag.hasUUID("uuid")) return Optional.empty();
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
        var uuid = tag.getUUID("uuid");
        var name = tag.getString("name");
        var posX = tag.getInt("posX");
        var posY = tag.getInt("posY");
        var posZ = tag.getInt("posZ");
        setLocating(uuid, name, new BlockPos(posX, posY, posZ));
    }

    public static void setLocating(UUID id, String name, BlockPos pos) {
        if (id == null || name == null || pos == null) return;
        locating = true;
        locatingUUID = id;
        locatingName = name;
        locatingPos = pos;
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
    }

}
