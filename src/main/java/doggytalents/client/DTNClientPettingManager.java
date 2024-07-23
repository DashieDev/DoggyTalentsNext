package doggytalents.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.lwjgl.glfw.GLFW;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.mojang.math.Axis;

import doggytalents.client.screen.PetSelectScreen;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogPettingManager.DogPettingState;
import doggytalents.common.entity.DogPettingManager.DogPettingType;
import doggytalents.common.fabric_helper.util.FabricUtil;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogPettingData;
import doggytalents.forge_imitate.event.client.ClientTickEvent;
import doggytalents.forge_imitate.event.client.ClientTickEvent.Phase;
import doggytalents.forge_imitate.event.client.ComputeCameraAngles;
import doggytalents.forge_imitate.event.client.InputEvent;
import doggytalents.forge_imitate.event.client.MovementInputUpdateEvent;
import doggytalents.forge_imitate.event.client.RenderArmEvent;
import doggytalents.forge_imitate.event.client.RenderPlayerEvent;
import doggytalents.forge_imitate.network.PacketDistributor;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;

public class DTNClientPettingManager {

    private static DTNClientPettingManager INSTANCE = new DTNClientPettingManager();
    public static DTNClientPettingManager get() {
        return INSTANCE;
    }
    
    private DogPettingType selectedType = null;
    private Dog dog = null;
    private boolean isPetting;
    private float pet_camera_xRot_add = 0;
    private float pet_camera_yRot_add = 0;
    private float pet_camera_xRot0_add = 0;
    private float pet_camera_yRot0_add = 0;
    
    private final Map<UUID, Dog> petterMap = Maps.newConcurrentMap();

    public synchronized void setPetMode(DogPettingType type) {
        this.selectedType = type;
    }

    public DogPettingType getPetMode() {
        return this.selectedType;
    }

    public synchronized void setPetting(Dog dog) {
        isPetting = dog != null;
        this.dog = dog;
        if (!isPetting)
            return;
        
        var mc = Minecraft.getInstance();

        var player = mc.player;
        if (player == null)
            return;
        player.yBodyRot = player.yHeadRot;
        pet_camera_xRot_add = 0;
        pet_camera_yRot_add = 0;
        pet_camera_xRot0_add = 0;
        pet_camera_yRot0_add = 0;
    }

    //@SubscribeEvent
    public void tickClient(ClientTickEvent event) {
        if (event.phase != Phase.END)
            return;

        if (isPetting)
            updatePetCameraRotation();
        invalidatePetterCache();
    }

    private final List<UUID> toRemoveForPetter = new ArrayList<>();
    public void invalidatePetterCache() {
        if (this.petterMap.isEmpty())
            return;
        for (var entry : this.petterMap.entrySet()) {
            var dog = entry.getValue();
            if (!dog.isAlive())
                toRemoveForPetter.add(entry.getKey());
        }
        if (toRemoveForPetter.isEmpty())
            return;
        for (var player : toRemoveForPetter) {
            removePetterFromMap(player);
        }
        toRemoveForPetter.clear();
    }

    private void updatePetCameraRotation() {
        var mc = Minecraft.getInstance();
        var options = mc.options;
        var view_type = options.getCameraType();
        if (view_type.isFirstPerson())
            return;

        this.pet_camera_xRot0_add = this.pet_camera_xRot_add;
        this.pet_camera_yRot0_add = this.pet_camera_yRot_add;
        final float add_amount = 3;
        if (options.keyUp.isDown()) {
            this.pet_camera_xRot_add += add_amount;
        } else if (options.keyDown.isDown()) {
            this.pet_camera_xRot_add -= add_amount;
        } else if (options.keyLeft.isDown()) {
            this.pet_camera_yRot_add += add_amount;
        } else if (options.keyRight.isDown()) {
            this.pet_camera_yRot_add -= add_amount;
        }
        this.pet_camera_xRot_add = Mth.clamp(this.pet_camera_xRot_add, -50, 50);
        float y_rot_change = this.pet_camera_yRot_add - this.pet_camera_yRot0_add;
        this.pet_camera_yRot_add = Mth.wrapDegrees(this.pet_camera_yRot_add);
        this.pet_camera_yRot0_add = pet_camera_yRot_add - y_rot_change;
    }

    //@SubscribeEvent
    public void onRenderHand(RenderArmEvent event) {
        if (event.getArm() != HumanoidArm.RIGHT)
            return;
        if (!isPetting)
            return;
        //configurable
        var stack = event.getPoseStack();
        var mc = Minecraft.getInstance();

        var pTicks = FabricUtil.getPartialTick(mc);
        
        var player = event.getPlayer();
        float anim_timeline = (float)(player.tickCount + player.getId() + pTicks ) * 0.04f;
        float occill = Mth.sin(anim_timeline * 2.0F * (float) Math.PI);
        //occill = (occill + 1)/2;
        float rotating_x = -15.0F * occill;
        var petting_type = getPettingTypeFor(player);
        if (petting_type == DogPettingType.HUG) {        
            stack.mulPose(Axis.XP.rotationDegrees(-20));
            stack.mulPose(Axis.ZP.rotationDegrees(0));
            stack.mulPose(Axis.YP.rotationDegrees(60));
            stack.mulPose(Axis.XP.rotationDegrees(-10 + rotating_x));
            stack.translate(.0, 0.2, -.6);
        } else {
            stack.translate(0, 0.4, 0);
            stack.mulPose(Axis.XP.rotationDegrees(-57));
            stack.mulPose(Axis.ZP.rotationDegrees(0));
            stack.mulPose(Axis.YP.rotationDegrees(-30F));
            stack.mulPose(Axis.XP.rotationDegrees(rotating_x));
        }
    }

    //@SubscribeEvent
    public void onMouseInput(InputEvent.MouseButton.Pre event) {
        var button = event.getButton();
        if (selectedType == null)
            return;
        if (button != GLFW.GLFW_MOUSE_BUTTON_RIGHT)
            return;
        var mc = Minecraft.getInstance();
        if (mc.screen != null || mc.getOverlay() != null || mc.player == null)
            return;
        var action = event.getAction();
        if (handleStopPetting(action)) {
            event.setCanceled(true);
            return;
        }
        var dog_optional = getLookingDog();
        if (!dog_optional.isPresent())
            return;
        if (handleStartPetting(action, dog_optional.get(), mc.player)) {
            event.setCanceled(true);
            return;
        }
        if (handleOpenPettingScreen(action, dog_optional.get(), mc.player)) {
            event.setCanceled(true);
            return;
        }
    }

    private boolean handleStopPetting(int action) {
        if (!isPetting)
            return false;
        if (action == GLFW.GLFW_PRESS)
            return false;
        if (this.dog != null) {
            this.requestPetting(dog, false);
        } else {
            this.setPetting(null);
        }
        return true;
    }

    private boolean handleStartPetting(int action, Dog dog, Player player) {
        if (isPetting)
            return false;
        if (action != GLFW.GLFW_PRESS)
            return false;
        if (!player.isShiftKeyDown())
            return false;
        if (!dog.pettingManager.canPet(player))
            return false;

        requestPetting(dog, true);
        return true;
    }

    private boolean handleOpenPettingScreen(int action, Dog dog, Player player) {
        if (isPetting)
            return false;
        if (action != GLFW.GLFW_PRESS)
            return false;
        if (player.isShiftKeyDown())
            return false;
        if (!dog.pettingManager.isInPetDistance(dog, player))
            return false;

        PetSelectScreen.open();
        return true;
    }

    private Optional<Dog> getLookingDog() {
        var mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null)
            return Optional.empty();
        var hitResult = mc.hitResult;
        if (hitResult == null)
            return Optional.empty();
        if (!(hitResult instanceof EntityHitResult entityHit))
            return Optional.empty();
        var entity = entityHit.getEntity();
        if (!(entity instanceof Dog dog))
            return Optional.empty();
        return Optional.of(dog);
    }

    private DogPettingType getPettingTypeFor(LivingEntity player) {
        var id = player.getUUID();
        var dog = this.petterMap.get(id);
        if (dog == null)
            return DogPettingType.FACERUB;
        return dog.getPettingState().type();
    }

    //@SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Pre event) {
        if (!isPettingPlayer(event.getEntity()))
            return;
        
        //var renderer = event.getRenderer();
        //var model = renderer.getModel();
        
        // model.leftArmPose = PettingArmPose.VALUE;
        // model.rightArmPose = PettingArmPose.VALUE;
        PettingArmPose.activate = true;
    }

    private boolean isPettingPlayer(Player player) {
        if (this.petterMap.isEmpty())
            return false;
        var dog = this.petterMap.get(player.getUUID());
        return dog != null && dog.pettingManager.isInPetDistance(dog, player);
    }

    public void applyTransform(HumanoidModel<?> model, LivingEntity player, HumanoidArm arm) {
        var mc = Minecraft.getInstance();

        var pTicks = FabricUtil.getPartialTick(mc);
        float anim_timeline = (float)(player.getId() + player.tickCount + pTicks ) * 0.04f;
        float occill, rotating_x;
        var petting_type = getPettingTypeFor(player);
        if (petting_type == DogPettingType.HUG) {
            if (arm == HumanoidArm.RIGHT) {
                occill = Mth.sin(anim_timeline * 12);
                //occill = (occill + 1)/2;
                rotating_x = 15.0F * occill;
                model.rightArm.xRot = -Mth.HALF_PI + (15 * Mth.DEG_TO_RAD);
                model.rightArm.xRot += rotating_x * Mth.DEG_TO_RAD;
                model.rightArm.yRot = Mth.DEG_TO_RAD * -4;
                //AnimationUtils.bobModelPart(model.rightArm, player.tickCount + pTicks, -1);
            } else if (arm == HumanoidArm.LEFT) {
                occill = Mth.sin(Mth.PI + anim_timeline * 12);
                //occill = (occill + 1)/2;
                rotating_x = 15.0F * occill;
                model.leftArm.xRot = -Mth.HALF_PI + (15 * Mth.DEG_TO_RAD);
                model.leftArm.xRot += rotating_x * Mth.DEG_TO_RAD;
                //
                model.leftArm.yRot = Mth.DEG_TO_RAD * 4;
                //AnimationUtils.bobModelPart(model.leftArm, player.tickCount + pTicks, 1);
            }    
        } else {
            if (arm == HumanoidArm.RIGHT) {
                occill = Mth.sin(anim_timeline * 12);
                //occill = (occill + 1)/2;
                rotating_x = 15.0F * occill;
                //model.rightArm.xRot = -Mth.HALF_PI + (15 * Mth.DEG_TO_RAD);
                
                model.rightArm.xRot = -Mth.HALF_PI - 25 * Mth.DEG_TO_RAD;
                model.rightArm.yRot = -30 * Mth.DEG_TO_RAD;
                model.rightArm.yRot += rotating_x * Mth.DEG_TO_RAD;
                //AnimationUtils.bobModelPart(model.rightArm, player.tickCount + pTicks, -1);
                // if (arm == HumanoidArm.RIGHT) {
                //     occill = Mth.sin(anim_timeline * 12);
                //     //occill = (occill + 1)/2;
                //     rotating_x = 15.0F * occill;
                //     model.rightArm.xRot = -Mth.HALF_PI + (15 * Mth.DEG_TO_RAD);
                //     model.rightArm.xRot += rotating_x * Mth.DEG_TO_RAD;
                //     model.rightArm.yRot = Mth.DEG_TO_RAD * -4;
                //     //AnimationUtils.bobModelPart(model.rightArm, player.tickCount + pTicks, -1);
                // }
            } 
        }
    }

    //@SubscribeEvent
    public void modifyCameraAngle(ComputeCameraAngles event) {
        if (!this.isPetting)
            return;
        
        var mc = Minecraft.getInstance();
        var view_type = mc.options.getCameraType();
        if (view_type.isFirstPerson())
            return;
        
        var pTicks = FabricUtil.getPartialTick(mc);
        float camera_xRot = (float) Mth.lerp(pTicks, pet_camera_xRot0_add, pet_camera_xRot_add);
        camera_xRot = Mth.clamp(camera_xRot, -75, 75);
        event.setPitch(camera_xRot);
        event.setYaw(event.getYaw() + (float) Mth.lerp(pTicks, pet_camera_yRot0_add, pet_camera_yRot_add));
    
        //1.20 under
        fixCameraPosition_1_20_under(event.getCamera(), FabricUtil.getPartialTick(mc), event.getYaw(), event.getPitch());
    }

    //@SubscribeEvent
    public void onMovementInput(MovementInputUpdateEvent event) {
        if (!this.isPetting)
            return;
        var mc = Minecraft.getInstance();
        var options = mc.options;
        var view_type = options.getCameraType();
        if (view_type.isFirstPerson())
            return;
        var input = event.getInput();
        input.forwardImpulse = 0;
        input.leftImpulse = 0;
        input.up = false;
        input.down = false;
        input.left = false;
        input.right = false;
    }

    public void onPettingUpdate(Dog dog, DogPettingState state) {
        if (!state.is_petting()) {
            clearPettingFor(dog);
            return;
        }
        var petter_id = state.petting_id();
        addPetterToMap(petter_id, dog);
    }

    private void clearPettingFor(Dog dog) {
        var toRemove = new ArrayList<UUID>();
        for (var entry : this.petterMap.entrySet()) {
            if (entry.getValue() == dog) {
                toRemove.add(entry.getKey());
            }
        }
        for (var key : toRemove) {
            removePetterFromMap(key);
        }
    }

    private void addPetterToMap(UUID petter, Dog dog) {
        if (dog == null || petter == null)
            return;
        this.petterMap.put(petter, dog);
        if (isSelfUUID(petter))
            this.setPetting(dog);
    }

    private void removePetterFromMap(UUID petter) {
        if (petter == null)
            return;
        this.petterMap.remove(petter);
        if (isSelfUUID(petter))
            this.setPetting(null);
    }

    private boolean isSelfUUID(UUID petter) {
        var mc = Minecraft.getInstance();
        var player = mc.player;
        return player != null && Objects.equal(player.getUUID(), petter);
    }

    private void requestPetting(Dog dog, boolean pet) {
        if (pet) 
            PacketHandler.send(PacketDistributor.SERVER.noArg(), new DogPettingData(dog.getId(), true, this.selectedType));
        else
            PacketHandler.send(PacketDistributor.SERVER.noArg(), new DogPettingData(dog.getId(), false, null));
    }

    //1.20 and under
    private void fixCameraPosition_1_20_under(Camera camera, double pTicks, float new_yRot, float new_xRot) {
        var mc = Minecraft.getInstance();
        var camera_entity = mc.getCameraEntity();
        if (camera_entity == null)
            camera_entity = mc.player;
        if (camera_entity == null)
            return;
        
        camera.setRotation(new_yRot, new_xRot);
        camera.setPosition(
            Mth.lerp(pTicks, camera_entity.xo, camera_entity.getX()),
            Mth.lerp(pTicks, camera_entity.yo, camera_entity.getY()) + camera_entity.getEyeHeight(),
            Mth.lerp(pTicks, camera_entity.zo, camera_entity.getZ())
        );
        float scale = (camera_entity instanceof LivingEntity living) ?
            living.getScale() : 1;
        camera.move(-camera.getMaxZoom(4.0F * scale), 0.0f, 0.0f);
        
    }


}
