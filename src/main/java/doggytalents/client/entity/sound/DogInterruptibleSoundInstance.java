package doggytalents.client.entity.sound;

import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class DogInterruptibleSoundInstance extends AbstractTickableSoundInstance {

    private static final int FADE_OUT_TIME = 5; 

    private final Dog dog;
    private int stoppingTick = 0;
    private boolean isStopping = false;
    private float fadeSpeed = 0;

    public DogInterruptibleSoundInstance(Dog dog, SoundEvent event, float vol, float pitch) {
        super(event, SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
        this.dog = dog;
        this.volume = vol;
        this.pitch = pitch;
    }

    public void dogInterupt() {
        this.isStopping = true;
        this.volume /= 4;
        this.fadeSpeed = this.volume / FADE_OUT_TIME;
    }

    @Override
    public void tick() {
        if (isStopping) {
            tickStop();
            return;
        }
        updatePositionVolumeOrInvalidate();
    }

    private void updatePositionVolumeOrInvalidate() {
        if (!this.dog.isAlive()) {
            dogInterupt();
            return;
        }
        this.x = dog.getX();
        this.y = dog.getY();
        this.z = dog.getZ();
    }

    private void tickStop() {
        ++stoppingTick;
        this.volume = Math.max(0, this.volume - fadeSpeed);
    }

    @Override
    public boolean isStopped() {
        return super.isStopped() 
            || (isStopping && stoppingTick > FADE_OUT_TIME);
    }

    @Override
    public boolean isLooping() {
        return false;
    }

    @Override
    public boolean canPlaySound() {
        return !this.dog.isSilent();
    }

    public static DogInterruptibleSoundInstance createAndPlayClient(Dog dog, SoundEvent event, float vol, float pitch) {
        var inst = new DogInterruptibleSoundInstance(dog, event, vol, pitch);
        Minecraft.getInstance().getSoundManager().play(inst);
        return inst;
    }
}
