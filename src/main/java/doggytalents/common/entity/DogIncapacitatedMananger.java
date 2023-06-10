package doggytalents.common.entity;

import java.util.ArrayList;
import java.util.UUID;

import doggytalents.DoggyBlocks;
import doggytalents.api.feature.EnumMode;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.screen.DogNewInfoScreen.screen.DogCannotInteractWithScreen;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.config.ConfigHandler.ClientConfig;
import doggytalents.common.entity.accessory.IncapacitatedLayer;
import doggytalents.common.network.packet.ParticlePackets;
import doggytalents.common.util.DogUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
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

public class DogIncapacitatedMananger {

    private final Dog dog;

    private int recoveryMultiplier = 1;
    private static final UUID INCAP_MOVEMENT = UUID.fromString("9576c796-c7c7-4995-90d5-f60eafc58805");
    private boolean hasMovementModifer = false;

    public DogIncapacitatedMananger(Dog dog) {
        this.dog = dog;
    }

    public void onBeingDefeated() {
        recoveryMultiplier = 1;
    }

    public void tick() {
        if (!this.dog.isDefeated()) {
            if (this.hasMovementModifer) {
                this.hasMovementModifer = false;
                this.dog.removeAttributeModifier(Attributes.MOVEMENT_SPEED, INCAP_MOVEMENT);
            }
            return;
        }
        if (this.dog.level.isClientSide) 
            incapacitatedClientTick();
        else
            incapacitatedTick();
    }

    public InteractionResult interact(ItemStack stack, Player player, InteractionHand hand) {
        var owner_uuid = dog.getOwnerUUID();
        if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
            if (!this.dog.level.isClientSide) {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }

                var defeatedDogs = DogUtil.getOtherIncapacitatedDogNearby(this.dog);
                
                for (var d : defeatedDogs) {
                    d.setDogHunger(this.dog.getMaxIncapacitatedHunger());

                    d.removeAllEffects();
                    d.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                    d.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                    d.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                    //Earraper.
                    //d.level.broadcastEntityEvent(d, (byte)35);
                }
                this.dog.level.broadcastEntityEvent(this.dog, (byte)35);
            }
            return InteractionResult.SUCCESS;
        } else if (stack.getItem() == Items.CAKE && recoveryMultiplier < 2) {

            if (this.dog.level instanceof ServerLevel) {
                ParticlePackets.DogEatingParticlePacket
                    .sendDogEatingParticlePacketToNearby(this.dog, new ItemStack(Items.CAKE));
            }
            this.dog.consumeItemFromStack(player, stack);
            this.dog.playSound(
                SoundEvents.GENERIC_EAT, 
                this.dog.getSoundVolume(), 
                (this.dog.getRandom().nextFloat() - this.dog.getRandom().nextFloat()) * 0.2F + 1.0F
            );

            this.recoveryMultiplier *= 2;
        } else if (stack.getItem() == Items.BONE && !this.dog.isPassenger() && !player.isVehicle()) {
            if (this.dog.getOwner() == player) {
                this.dog.startRiding(player);
            }
            return InteractionResult.SUCCESS;
        } else if (stack.getItem() == Items.STICK) {

            if (this.dog.level.isClientSide) {
                boolean useLegacyDogGui = 
                    ConfigHandler.ClientConfig.getConfig(ConfigHandler.CLIENT.USE_LEGACY_DOGGUI); 

                if (!useLegacyDogGui) {
                    DogCannotInteractWithScreen.open(this.dog);
                }
            }

            return InteractionResult.SUCCESS;
        } else if (this.dog.isPassenger()) {
            if(!this.dog.level.isClientSide) this.dog.stopRiding();
            return InteractionResult.SUCCESS;
        } else if (owner_uuid != null && owner_uuid.equals(player.getUUID())) {
            this.dog.setOrderedToSit(!this.dog.isOrderedToSit());
            this.dog.getNavigation().stop();
            return InteractionResult.SUCCESS;
        } else {
            if (this.dog.level.isClientSide) this.displayToastIncapacitated(player);
        }
        return InteractionResult.FAIL;
    }

    private void displayToastIncapacitated(Player player) {
        player.displayClientMessage(
            Component.translatable("doggui.invalid_dog.incapacitated.title")
            .withStyle(ChatFormatting.RED) 
        , true);
    }

    public void incapacitatedClientTick() {
        for (var i : this.dog.getAccessories()) {
            if (i.getAccessory() instanceof IncapacitatedLayer iL) {
                if (!ClientConfig.getConfig(ConfigHandler.CLIENT.RENDER_INCAPACITATED_TEXTURE)) return;
                boolean isLowGraphic = 
                    ClientConfig.getConfig(ConfigHandler.CLIENT.RENDER_INCAP_TXT_LESS_GRAPHIC);
                if (isLowGraphic) return;
                iL.tickClient(this.dog);
            }
        }
    }

    /**
     * When dogs enter Incapacitated Mode, hunger now acts as incapacitated units.
     * 
     * When "hunger" reaches Zero, it will reset and the dog will exit I Mode
     * 
     */
    public void incapacitatedTick() {
        // 3 days max 60 min = 72 000 ticks

        if (!this.hasMovementModifer) {
            this.dog.setAttributeModifier(Attributes.MOVEMENT_SPEED, INCAP_MOVEMENT,
                (d, u) -> new AttributeModifier(u, "Defeated Slowness", -0.35f, Operation.MULTIPLY_TOTAL)
            );
            this.hasMovementModifer = true;
        }

        var owner = this.dog.getOwner();
        var dog_b0_state = this.dog.level.getBlockState(this.dog.blockPosition());
        var dog_b0_block = dog_b0_state.getBlock();

        if (this.dog.getDogHunger() >= this.dog.getMaxIncapacitatedHunger()) {
            incapacitatedExit();
            return;
        }

        if (dog_b0_block == Blocks.AIR) {
            this.dog.addHunger(0.001f*this.recoveryMultiplier);
        } else if (dog_b0_block == DoggyBlocks.DOG_BED.get()) {
            incapacitatedHealWithBed(owner);
        }
    }
        

    private void incapacitatedExit() {
        this.dog.setHealth(20);
        this.dog.setMode(EnumMode.DOCILE);
        this.dog.setDogHunger(this.dog.getMaxHunger());
        this.dog.setOrderedToSit(true);

        if (this.hasMovementModifer) {
            this.hasMovementModifer = false;
            this.dog.removeAttributeModifier(Attributes.MOVEMENT_SPEED, INCAP_MOVEMENT);
        }

        var toRemove = new ArrayList<AccessoryInstance>();
        for (var i : this.dog.getAccessories()) {
            if (i.getAccessory() instanceof IncapacitatedLayer) {
                toRemove.add(i);
            }
        }
        for (var i : toRemove) {
            this.dog.getAccessories().remove(i);
        }
        this.dog.markAccessoriesDirty();

        if (this.dog.level instanceof ServerLevel sL) {
            sL.sendParticles(
                ParticleTypes.HEART, 
                this.dog.getX(), this.dog.getY(), this.dog.getZ(), 
                24, 
                this.dog.getBbWidth(), 0.8f, this.dog.getBbWidth(), 
                0.1
            );
        }
    }

    private void incapacitatedHealWithBed(LivingEntity owner) {
        this.dog.addHunger(0.002f*this.recoveryMultiplier);

        if (owner == null) return;
        if (this.dog.distanceToSqr(owner) > 100) return;
        this.dog.addHunger(0.02f*this.recoveryMultiplier);

        if (!(this.dog.level instanceof ServerLevel)) return;
        if (this.dog.tickCount % 10 != 0) return;
        ((ServerLevel) this.dog.level).sendParticles(
            ParticleTypes.HEART, 
            this.dog.getX(), this.dog.getY(), this.dog.getZ(), 
            1, 
            this.dog.getBbWidth(), 0.8f, this.dog.getBbWidth(), 
            0.1
        );
    }

    public boolean canMove() {
        return true;
    }
    
}
