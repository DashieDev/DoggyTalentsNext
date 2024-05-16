package doggytalents.common.entity;

import java.util.ArrayList;
import java.util.UUID;

import doggytalents.DoggyAdvancementTriggers;
import doggytalents.DoggyBlocks;
import doggytalents.DoggyItems;
import doggytalents.api.anim.DogAnimation;
import doggytalents.api.feature.EnumMode;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.screen.DogNewInfoScreen.screen.DogCannotInteractWithScreen;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.config.ConfigHandler.ClientConfig;
import doggytalents.common.entity.ai.triggerable.DogBounceAction;
import doggytalents.common.entity.ai.triggerable.DogFaintStandAction;
import doggytalents.common.entity.anim.DogPose;
import doggytalents.common.network.packet.ParticlePackets;
import doggytalents.common.util.DogUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForgeMod;

public class DogIncapacitatedMananger {

    private final Dog dog;

    private int recoveryMultiplier = 1;
    private static final UUID INCAP_MOVEMENT = UUID.fromString("9576c796-c7c7-4995-90d5-f60eafc58805");
    private boolean appliedIncapChanges = false;
    
    private static final int MAX_BANDAID_COUNT = 8;
    private float partialRecoverVal = 0;
    private int bandagesCount = 0;
    private int bandageCooldown = 0;
    private int drownPoseTick = 0;
    private BlockState atBlockState = null;
    
    public static final int MAX_INCAP_MSG_LEN = 256;
    private String incapMsg = "";

    public DogIncapacitatedMananger(Dog dog) {
        this.dog = dog;
    }


    public void onModeUpdate(EnumMode mode) {
        if (mode != EnumMode.INJURED
            && this.appliedIncapChanges) {
            this.appliedIncapChanges = false;
            this.onExitingIncapMode();
        } else if (mode == EnumMode.INJURED
            && !this.appliedIncapChanges) {
            this.appliedIncapChanges = true;
            this.onEnteringIncapMode();
        } 
    }

    private void onEnteringIncapMode() {
        recoveryMultiplier = 1;
        this.bandagesCount = 0;
        this.dog.setAttributeModifier(Attributes.MOVEMENT_SPEED, INCAP_MOVEMENT,
            (d, u) -> new AttributeModifier(u, "Defeated Slowness", -0.5f, Operation.ADD_MULTIPLIED_TOTAL)
        );
        this.dog.setDrunkTicks(0);
    }

    private void onExitingIncapMode() {
        recoveryMultiplier = 1;
        this.bandagesCount = 0;
        this.dog.removeAttributeModifier(Attributes.MOVEMENT_SPEED, INCAP_MOVEMENT);
        this.incapMsg = "";
    }

    public void tick() {
        if (this.dog.level().isClientSide) 
            incapacitatedClientTick();
        else
            incapacitatedTick();
    }

    public InteractionResult interact(ItemStack stack, Player player, InteractionHand hand) {
        
        if (proccessBandage(stack, player).shouldSwing())
            return InteractionResult.SUCCESS;

        var item = stack.getItem();
        if (item == Items.TOTEM_OF_UNDYING) {
            useTotem(stack, player);
            return InteractionResult.SUCCESS;
        }
        
        if (handleOwnerRide(stack, player).shouldSwing())
            return InteractionResult.SUCCESS;
        if (proccessSitStandOrder(player).shouldSwing())
            return InteractionResult.SUCCESS;

        if (this.dog.level().isClientSide && player == this.dog.getOwner())
            DogCannotInteractWithScreen.open(this.dog);
        return InteractionResult.SUCCESS;
    }

    private InteractionResult proccessBandage(ItemStack stack, Player player) {
        boolean wagyu = needWagyu();
        boolean isBandage =
            (wagyu && stack.getItem() == DoggyItems.GOLDEN_A_FIVE_WAGYU.get())
            || (!wagyu && stack.getItem() == DoggyItems.BANDAID.get());
        if (isBandage) {
            useBandage(stack, player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private void useBandage(ItemStack stack, Player player) {
        if (this.dog.level().isClientSide)
            return;
        if (this.bandageCooldown > 0f)
            return;
        if (this.bandagesCount >= MAX_BANDAID_COUNT)
            return;

        this.bandageCooldown = 10;
        player.getCooldowns().addCooldown(stack.getItem(), 11);
        ++this.bandagesCount;
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        if (this.bandagesCount >= MAX_BANDAID_COUNT) {
            this.dog.triggerAnimationAction(new DogFaintStandAction(dog, getFaintStandAnim()));
        }
        if (player instanceof ServerPlayer sP) {
            DoggyAdvancementTriggers.DOG_BANDAID_APPLY_TRIGGER.trigger(dog, sP);
        }
    }

    private void useTotem(ItemStack stack, Player player) {
        if (this.dog.level().isClientSide)
            return;
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        var defeatedDogs = DogUtil.getOtherIncapacitatedDogNearby(this.dog);
        
        for (var d : defeatedDogs) {
            d.setDogIncapValue(0);
            incapacitatedExit();

            d.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
            d.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
            d.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
            //Earraper.
            //d.level.broadcastEntityEvent(d, (byte)35);
        }
        this.dog.level().broadcastEntityEvent(this.dog, (byte)35);
    }

    private InteractionResult handleOwnerRide(ItemStack stack, Player player) {
        if (player.hasPassenger(dog)) {
            if (!dog.level().isClientSide)
                dog.unRide();
            return InteractionResult.SUCCESS;
        }
        var item = stack.getItem();
        if (item != Items.BONE)
            return InteractionResult.PASS;
        if (dog.isVehicle())
            return InteractionResult.PASS;
        if (dog.getOwner() != player)
            return InteractionResult.PASS;
        if (!dog.level().isClientSide) {
            if (dog.startRiding(player))
            player.displayClientMessage(
                Component.translatable(
                    "talent.doggytalents.bed_finder.dog_mount", 
                    dog.getGenderPronoun()), true);
        }
        return InteractionResult.SUCCESS;
    }

    private InteractionResult proccessSitStandOrder(Player player) {
        if (!this.canMove())
            return InteractionResult.PASS;
        var owner_uuid = this.dog.getOwnerUUID();
        if (owner_uuid == null)
            return InteractionResult.PASS;
        if (!owner_uuid.equals(player.getUUID()))
            return InteractionResult.PASS;
        this.dog.setOrderedToSit(!this.dog.isOrderedToSit());
        this.dog.getNavigation().stop();
        return InteractionResult.SUCCESS;
    }

    public void onHurt() {
        if (!ConfigHandler.SERVER.INCAP_VAL_RESET_WHEN_HURT.get()) {
            this.dropBandages();
        }
    }

    private void dropBandages() {
        if (this.needWagyu())
            return;
        float keep_precentage = 
            0.8f - dog.getRandom().nextInt(4)*0.1f;
        int keep_amount = Mth.floor(keep_precentage*this.bandagesCount);
        for (int i = 0; i < keep_amount; ++i) {
            Containers.dropItemStack(dog.level(), dog.getX(), dog.getY(), dog.getZ(), 
                new ItemStack(DoggyItems.BANDAID.get(), 1));
        }
        bandageCooldown = 200;

        this.bandagesCount = 0;

        //Update.
        var incap_state = dog.getIncapSyncState();
        var new_incap_state = incap_state.updateBandaid(bandagesCount);
        if (new_incap_state != incap_state) {
            dog.setIncapSyncState(new_incap_state);
        }
    }

    private void displayToastIncapacitated(Player player) {
        player.displayClientMessage(
            Component.translatable("doggui.invalid_dog.incapacitated.title")
            .withStyle(ChatFormatting.RED) 
        , true);
    }

    public void incapacitatedClientTick() {
        if (this.isDogDeepInFluid()) {
            ++drownPoseTick;
        } else {
            drownPoseTick = 0;
        }

        var sync_state = this.dog.getIncapSyncState();
        var type = sync_state.type;
        switch (type) {
        case BURN:
            if (dog.getDogIncapValue() >= dog.getDefaultInitIncapVal() - 10) {
                for (int i = 0; i < 2; ++i) {
                    float f1 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.8F;
                    float f2 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.8F;
                    dog.level().addParticle(ParticleTypes.ASH,
                    dog.getX() + f1,
                    dog.getY() + 0.4,
                    dog.getZ() + f2,
                    0, -0.05 , 0 );
                }
    
                if (dog.getRandom().nextInt(3) == 0) {
                    float f1 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.5F;
                    float f2 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.5F;
                    dog.level().addParticle(ParticleTypes.SMOKE,
                    dog.getX() + f1,
                    dog.getY() + dog.getEyeHeight(),
                    dog.getZ() + f2,
                    0, 0.05 , 0 );
                }
            }
            break;
        case BLOOD:
            if (dog.getDogIncapValue() >= dog.getDefaultInitIncapVal() - 10 && dog.tickCount % 8 == 0) {
                for (int i = 0; i < 2; ++i) {
                    float f1 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.8F;
                    float f2 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.8F;
                    dog.level().addParticle(
                        new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.NETHER_WART)),
                        dog.getX() + f1,
                        dog.getY() + 0.4,
                        dog.getZ() + f2,
                        0, -0.05 , 0 
                    );
                }
            }
            break;
        case DROWN:
            if (dog.getDogIncapValue() >= dog.getDefaultInitIncapVal() - 10 && dog.tickCount % 8 == 0) {
                for (int i = 0; i < 2; ++i) {
                    float f1 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.8F;
                    float f2 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.8F;
                    dog.level().addParticle(
                        ParticleTypes.SPLASH,
                        dog.getX() + f1,
                        dog.getY() + 0.4,
                        dog.getZ() + f2,
                        0, -0.05 , 0 
                    );
                }
            }
            break;
        default:
            break;
        }
    }

    /**
     * When dogs enter Incapacitated Mode, hunger now acts as incapacitated units.
     * 
     * When "hunger" reaches Zero, it will reset and the dog will exit I Mode
     * 
     */
    public void incapacitatedTick() {
        if (this.dog.getDogIncapValue() <= 0) {
            incapacitatedExit();
            return;
        }

        // 3 days max 60 min = 72 000 ticks

        if (this.partialRecoverVal >= 1f) {
            this.dog.setDogIncapValue(this.dog.getDogIncapValue() - 1);
            this.partialRecoverVal = 0;
        }
        
        if (this.bandageCooldown > 0) --bandageCooldown;

        var incap_state = dog.getIncapSyncState();
        var new_incap_state = incap_state.updateBandaid(bandagesCount);
        if (new_incap_state != incap_state) {
            dog.setIncapSyncState(new_incap_state);
        }

        if (!ConfigHandler.SERVER.INCAP_VAL_RESET_WHEN_HURT.get() && 
            this.bandagesCount > 0 && isConditionDestroyBandages(dog)) {
            this.dropBandages();
            return;
        }

        healWithBandaid(new_incap_state.bandaid);
        var owner = this.dog.getOwner();
        var dog_b0_state = this.dog.level().getBlockState(this.dog.blockPosition());
        var dog_b0_block = dog_b0_state.getBlock();

        if (dog_b0_block == Blocks.AIR) {
            this.partialRecoverVal += (0.001f*this.recoveryMultiplier);
        } else if (dog_b0_block == DoggyBlocks.DOG_BED.get()
            || dog_b0_state.is(BlockTags.BEDS)) {
            incapacitatedHealWithBed(owner);
        }
    }

    private boolean isConditionDestroyBandages(Dog dog) {
        if (!dog.fireImmune() && dog.isOnFire())
            return true;
        if (!dog.canSwimUnderwater() && dog.isInWater())
            return true;
        if (!dog.dogFallImmune() && dog.fallDistance > 3)
            return true;
        return false;
    }

    private void incapacitatedExit() {
        int bandaid_count0 = this.bandagesCount;
        this.dog.maxHealth();
        this.dog.setMode(EnumMode.DOCILE);
        this.dog.setDogHunger(this.dog.getMaxHunger());
        this.dog.setDogIncapValue(0);
        this.dog.setOrderedToSit(true);
        this.dog.setIncapSyncState(IncapacitatedSyncState.NONE);
        this.dog.triggerAnimationAction(new DogBounceAction(dog));
        this.dog.removeAllEffects();

        if (this.dog.level() instanceof ServerLevel sL) {
            sL.sendParticles(
                ParticleTypes.HEART, 
                this.dog.getX(), this.dog.getY(), this.dog.getZ(), 
                24, 
                this.dog.getBbWidth(), 0.8f, this.dog.getBbWidth(), 
                0.1
            );
        }
        if (bandaid_count0 > 0) {
            var owner = this.dog.getOwner();
            triggerAdvancement(owner, bandaid_count0);
        }
    }

    private void triggerAdvancement(LivingEntity player, int bandages_count0) {
        if (!(player instanceof ServerPlayer sP))
            return;
        DoggyAdvancementTriggers.DOG_RECOVERED_TRIGGER.trigger(dog, sP, false);
        if (isBestRecoveryCondition(bandages_count0))
            DoggyAdvancementTriggers.DOG_RECOVERED_TRIGGER.trigger(dog, sP, true);
    }

    private boolean isBestRecoveryCondition(int bandaid_count0) {
        if (bandaid_count0 < MAX_BANDAID_COUNT)
            return false;
        var dog_b0_state = this.dog.level().getBlockState(this.dog.blockPosition());
        var dog_b0_block = dog_b0_state.getBlock();
        boolean is_laying_bed = dog_b0_block == DoggyBlocks.DOG_BED.get()
            || dog_b0_state.is(BlockTags.BEDS);
        
        return is_laying_bed;
    }

    private void incapacitatedHealWithBed(LivingEntity owner) {
        this.partialRecoverVal += (0.002f*this.recoveryMultiplier);

        if (owner == null) return;
        if (this.dog.distanceToSqr(owner) > 100) return;
        this.partialRecoverVal += (0.02f*this.recoveryMultiplier);

        if (!(this.dog.level() instanceof ServerLevel)) return;
        if (this.dog.tickCount % 10 != 0) return;
        ((ServerLevel) this.dog.level()).sendParticles(
            ParticleTypes.HEART, 
            this.dog.getX(), this.dog.getY(), this.dog.getZ(), 
            1, 
            this.dog.getBbWidth(), 0.8f, this.dog.getBbWidth(), 
            0.1
        );
    }

    private void healWithBandaid(BandaidState state) {
        switch (state) {
        case FULL:
            this.partialRecoverVal += (0.02f*this.recoveryMultiplier);
            break;
        case HALF:
            this.partialRecoverVal += (0.01f*this.recoveryMultiplier);
            break;
        default:
            break;
        }
    }

    public boolean canMove() {
        return this.dog.getIncapSyncState().bandaid == BandaidState.FULL;
    }

    public void save(CompoundTag tag) {
        var tg0 = new CompoundTag();
        var syncState = this.dog.getIncapSyncState();
        tg0.putInt("type", syncState.type.getId());
        tg0.putInt("bandaid", this.bandagesCount);
        tg0.putInt("poseid", syncState.poseId);
        tg0.putString("incapMsg", incapMsg);
        tag.put("doggyIncapacitated", tg0);
    }

    public void load(CompoundTag tag) {
        var tg0 = tag.getCompound("doggyIncapacitated");
        var type = DefeatedType.byId(tg0.getInt("type"));
        var bandaid_count = tg0.getInt("bandaid");
        var poseId = tg0.getInt("poseid");
        this.incapMsg = tg0.getString("incapMsg");
        this.bandagesCount = bandaid_count;
        dog.setIncapSyncState(new IncapacitatedSyncState(type, 
            BandaidState.getState(bandaid_count), poseId));
    }

    public DogPose getPose() {
        int id = this.dog.getIncapSyncState().poseId;
        if (this.showDrownPose())
            return DogPose.DROWN;
        switch (id) {
        default:
            return DogPose.FAINTED;
        case 1:
            return DogPose.FAINTED_2;
        }
    }

    public DogAnimation getAnim() {
        int id = this.dog.getIncapSyncState().poseId;
        if (showDrownPose())
            return DogAnimation.DROWN;
        switch (id) {
        default:
            return DogAnimation.FAINT;
        case 1:
            return DogAnimation.FAINT_2;
        }
    }

    private boolean isDogDeepInFluid() {
        var type = this.dog.getMaxHeightFluidType();
        if (type.isAir()) return false;
        if (type == NeoForgeMod.LAVA_TYPE.value() && dog.fireImmune())
            return false;
        double height = this.dog.getFluidTypeHeight(type);
        return height > 0.5;
    }

    private boolean showDrownPose() {
        return this.drownPoseTick > 20;
    }

    public DogAnimation getFaintStandAnim() {
        int id = this.dog.getIncapSyncState().poseId;
        if (this.dog.isInWater() || this.dog.isInLava())
            return DogAnimation.NONE;
        switch (id) {
        default:
            return DogAnimation.FAINT_STAND_1;
        case 1:
            return DogAnimation.FAINT_STAND_2;
        }
    }

    public void setIncapMsg(String s) {
        if (s == null) return;
        if (s.isEmpty()) return;
        if (s.length() > MAX_INCAP_MSG_LEN) {
            s = s.substring(0, MAX_INCAP_MSG_LEN - 6) + "..";
        }
        this.incapMsg = s;
    }

    public String getIncapMsg() {
        return this.incapMsg;
    }

    private boolean needWagyu() {
        return this.dog.getIncapSyncState().type == DefeatedType.STARVE;
    }


    public static class IncapacitatedSyncState {

        public static IncapacitatedSyncState NONE = 
            new IncapacitatedSyncState(DefeatedType.NONE);
        public DefeatedType type = DefeatedType.NONE;
        public BandaidState bandaid = BandaidState.NONE;
        public int poseId = 0;

        public IncapacitatedSyncState(DefeatedType type) {
            this.type = type;
        }

        public IncapacitatedSyncState(DefeatedType type, BandaidState bandaid) {
            this.type = type;
            this.bandaid = bandaid;
        }

        public IncapacitatedSyncState(DefeatedType type, BandaidState bandaid, int poseId) {
            this.type = type;
            this.bandaid = bandaid;
            this.poseId = poseId;
        }

        public IncapacitatedSyncState copy() {
            return new IncapacitatedSyncState(type, bandaid, poseId);
        }

        public IncapacitatedSyncState updateBandaid(int bandaid_count) {
            var new_state = BandaidState.getState(bandaid_count);
            if (new_state == this.bandaid) return this;
            return new IncapacitatedSyncState(type, new_state, poseId);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof IncapacitatedSyncState state))
                return false;
            if (this.type != state.type)
                return false;
            if (this.bandaid != state.bandaid)
                return false;
            if (this.poseId != state.poseId) {
                return false;
            }
            return true;
        }

    }

    public static enum BandaidState { 
        NONE(0), HALF(1), FULL(2);
        
        private final int id;
        private BandaidState(int id) {
            this.id = id;
        }

        public static BandaidState byId(int i) {
            var values = BandaidState.values();
            if (i < 0) return NONE;
            if (i >= values.length) return NONE;
            return values[i];
        };
        public static BandaidState getState(int bandaid_count) {
            if (bandaid_count <= 3) {
                return NONE;
            }
            if (bandaid_count <= 7) {
                return HALF;
            }
            return FULL;
        }

        public int getId() { return this.id; }
        

    }
    public static enum DefeatedType { 
        NONE(0), BLOOD(1), BURN(2), POISON(3), DROWN(4), STARVE(5);

        private final int id;
        private DefeatedType(int id) {
            this.id = id;
        }

        public static DefeatedType byId(int i) {
            var values = DefeatedType.values();
            if (i < 0) return NONE;
            if (i >= values.length) return NONE;
            return values[i];
        };

        public int getId() { return this.id; }

        
    }

}
