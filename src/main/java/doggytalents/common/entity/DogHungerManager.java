package doggytalents.common.entity;

import java.util.UUID;

import doggytalents.common.config.ConfigHandler;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class DogHungerManager {
    
    public static final UUID HUNGER_MOVEMENT = UUID.fromString("50671f49-1dfd-4397-242b-78bb6b178115");

    private final Dog dog;

    private int hungerTick0 = 0;
    private int hungerTick = 0;
    private int saturation = 0;
    private int saturationHealingTick = 0;
    private int hungerDamageTick = 0;

    private boolean lowHunger = false;
    private boolean zeroHunger = false;

    public DogHungerManager(Dog dog) {
        this.dog = dog;
    }

    public void tick() {
        if (ConfigHandler.SERVER.DISABLE_HUNGER.get())
            return;

        hungerTick0 = hungerTick;

        if (!dog.isVehicle() && !dog.isInSittingPose()) {
            this.hungerTick += 1;
        }

        for (var alter : dog.getAlterations()) {
            var result = alter.hungerTick(dog, this.hungerTick - this.hungerTick0);

            if (result.getResult().shouldSwing()) {
                this.hungerTick = result.getObject() + this.hungerTick0;
            }
        }

        int tickPerDec = ConfigHandler.SERVER.TICK_PER_HUNGER_DEC.get();

        if (this.hungerTick > tickPerDec) {
            if (this.saturation > 0) {
                --this.saturation;
            } else {
                dog.setDogHunger(dog.getDogHunger() - 1);
            }
            
            this.hungerTick -= tickPerDec;
        }

        if (this.zeroHunger)
            this.handleZeroHunger();

        if (saturation > 0) {
            if (dog.getHealth() < dog.getMaxHealth()) {
                if (--this.saturationHealingTick <= 0) {
                    this.saturationHealingTick = 10;
                    dog.heal(1.0f);
                    saturation -= 3; // -3 saturation per health healed
                }
            }
        }
        
    }

    private void handleZeroHunger() {
        ++this.hungerDamageTick;
        int hurt_interval = -1;
        boolean hurt_last_health = false;
        switch (dog.level().getDifficulty()) {
            case EASY: {
                hurt_interval = 125;
                break;
            }
            case NORMAL: {
                hurt_interval = 100;
                break;
            }
            case HARD: {
                hurt_interval = 75;
                hurt_last_health = true;
                break;
            }
            default: {
                hurt_interval = -1;
                break;
            }
        }

        if (hurt_interval >= 0 && ++this.hungerDamageTick >= hurt_interval
                && (hurt_last_health || dog.getHealth() > 1f)) {
            dog.hurt(dog.damageSources().starve(), 0.5f);
            
            this.hungerDamageTick = 0;
        }
    }

    public int saturation() {
        return this.saturation;
    }
 
    public boolean isLowHunger() {
        return this.lowHunger;
    }

    public void onHungerUpdated(float new_hunger) {
        if (this.dog.level().isClientSide)
            return;
        boolean updated_is_low = new_hunger <= 10;
        if (this.lowHunger && !updated_is_low) {
            this.hungerLowToHigh();
        }
        if (!this.lowHunger && updated_is_low) {
            this.hungerHighToLow();
        }
        this.zeroHunger = new_hunger <= 0;
    }
    
    private void hungerHighToLow() {
        if (!dog.isDefeated())
        dog.setAttributeModifier(Attributes.MOVEMENT_SPEED, HUNGER_MOVEMENT,
                (d, u) -> new AttributeModifier(u, "Hunger Slowness", -0.35f, Operation.MULTIPLY_TOTAL)
        );

    }

    private void hungerLowToHigh() {
        dog.removeAttributeModifier(Attributes.MOVEMENT_SPEED, HUNGER_MOVEMENT);
    }

    public void onBeingIncapacitated() {
        dog.removeAttributeModifier(Attributes.MOVEMENT_SPEED, HUNGER_MOVEMENT);
    }

    public void addHunger(float add) {
        var h0 = dog.getDogHunger();
        var h1 = h0 + add;
        var h2 = (int) (h1 - dog.getMaxHunger());
        if (h2 > 0) {
            this.saturation = Math.max(this.saturation, h2);
        }
        dog.setDogHunger(h0 + add);
    }

}
