package doggytalents.common.entity;

import java.util.UUID;

import doggytalents.common.config.ConfigHandler;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class DogHungerManager {
    
    public static final UUID HUNGER_MOVEMENT = UUID.fromString("50671f49-1dfd-4397-242b-78bb6b178115");
    public static final float MAX_HUNGER_TICK = 800;

    private final Dog dog;

    private float hungerTick = 0;
    private int saturation = 0;
    private int saturationHealingTick = 0;
    private int hungerDamageTick = 0;

    private boolean lowHunger = false;
    private boolean zeroHunger = false;

    public DogHungerManager(Dog dog) {
        this.dog = dog;
    }

    public void tick() {
        handleHunger();
        mayHealWithSaturation();
    }

    private void handleHunger() {
        if (ConfigHandler.SERVER.DISABLE_HUNGER.get())
            return;

        this.hungerTick += this.getIncreaseHungerTick();

        if (this.hungerTick >= MAX_HUNGER_TICK) {
            if (this.saturation > 0) {
                --this.saturation;
            } else {
                dog.setDogHunger(dog.getDogHunger() - 1);
            }
            
            this.hungerTick = 0;
        }

        if (this.zeroHunger)
            this.handleZeroHunger();
    }

    private float getIncreaseHungerTick() {
        float inc_tick = 0;
        if (!this.dog.isVehicle() && !this.dog.isInSittingPose())
            inc_tick = 1;
        if (!this.dog.getNavigation().isDone())
            inc_tick = 2;
        for (var alter : dog.getAlterations()) {
            var result = alter.hungerTick(dog, inc_tick);

            if (result.getResult().shouldSwing()) {
                inc_tick = result.getObject();
            }
        }
        float modifier = ConfigHandler.SERVER.HUNGER_MODIFIER.get().floatValue();
        return inc_tick * modifier;
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

    private void mayHealWithSaturation() {
        if (saturation <= 0)
            return;
        if (dog.getHealth() >= dog.getMaxHealth())
            return;
        if (--this.saturationHealingTick <= 0) {
            this.saturationHealingTick = 10;
            dog.heal(2.0f);
            saturation -= 3; // -3 saturation per health healed
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
        boolean lowHunger_updated = new_hunger <= 10;
        if (this.lowHunger && !lowHunger_updated) {
            this.hungerLowToHigh();
        }
        if (!this.lowHunger && lowHunger_updated) {
            this.hungerHighToLow();
        }
        this.lowHunger = lowHunger_updated;
        this.zeroHunger = new_hunger <= 0;
    }
    
    private void hungerHighToLow() {
        if (!dog.isDefeated())
        dog.setAttributeModifier(Attributes.MOVEMENT_SPEED, HUNGER_MOVEMENT,
                (d, u) -> new AttributeModifier(u, "Hunger Slowness", -0.35f, Operation.ADD_MULTIPLIED_TOTAL)
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
