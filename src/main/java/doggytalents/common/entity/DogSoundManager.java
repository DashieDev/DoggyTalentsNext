package doggytalents.common.entity;

import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.function.Consumer;

import doggytalents.client.entity.sound.DogInterruptibleSoundInstance;
import doggytalents.common.network.PacketDistributor;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogInterruptibleSoundData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class DogSoundManager {
    
    private final Dog dog;
    private boolean ambientVoiceLock = false;

    public DogSoundManager(Dog dog) {
        this.dog = dog;
    }

    public void setAmbientLocked(boolean val) {
        this.ambientVoiceLock = val;
    }

    public boolean isAmbientLocked() {
        return this.ambientVoiceLock;
    }

    public void tick() {
        
    }

    public void playInterruptible(SoundEvent event, float volume, float pitch) {
        if (dog.isSilent())
            return; 
        var data = new DogInterruptibleSoundData(dog.getId(), Optional.of(event), volume, pitch);
        if (dog.level().isClientSide()) {
            onDogInterruptableSoundUpdate(data);
        } else {
            PacketHandler.send(PacketDistributor.TRACKING_ENTITY.with(() -> dog), data);
        }
    }

    public void playNonInterruptible(SoundEvent event, float volume, float pitch) {
        if (dog.isSilent())
            return;
        if (dog.level().isClientSide())
            return;
        dog.level().playSound(null, dog, event, SoundSource.AMBIENT, volume, pitch);
    }

    public void interuptPlaying() {
        var data = DogInterruptibleSoundData.stop(dog.getId());
        if (dog.level().isClientSide()) {
            onDogInterruptableSoundUpdate(data);
        } else {
            PacketHandler.send(PacketDistributor.TRACKING_ENTITY.with(() -> dog), data);
        }
    }

    //client
    private final DogInterruptibleSoundInstanceRef interruptListener = new DogInterruptibleSoundInstanceRef();

    //client
    public void onDogInterruptableSoundUpdate(DogInterruptibleSoundData data) {
        var clientSound = interruptListener.getOrNull();
        if (clientSound != null)
            clientSound.dogInterupt();
        if (!data.sound().isPresent()) {
            interruptListener.resetRef();
            return;
        }
        clientSound = DogInterruptibleSoundInstance
            .createAndPlayClient(dog, data.sound().get(), data.volume(), data.pitch());
        interruptListener.setRef(clientSound);
    }

    //client
    private static class DogInterruptibleSoundInstanceRef {

        private WeakReference<DogInterruptibleSoundInstance> ref = null;
        
        public DogInterruptibleSoundInstanceRef() {}
        
        public DogInterruptibleSoundInstance getOrNull() {
            if (ref == null)
                return null;
            var inst = ref.get();
            if (inst == null)
                resetRef();
            return inst;
        }
        
        public void setRef(DogInterruptibleSoundInstance inst) {
            this.ref = inst != null ? new WeakReference<>(inst) : null;
        }
        
        public void resetRef() {
            setRef(null);
        }
        
    }


}
