package doggytalents.common.network.packet.data;

import java.util.Optional;

import net.minecraft.sounds.SoundEvent;

public record DogInterruptibleSoundData(
    int dogId, Optional<SoundEvent> sound,
    float volume, float pitch
) {
    public static DogInterruptibleSoundData stop(int dogId) {
        return new DogInterruptibleSoundData(dogId, Optional.empty(), 0, 0);
    }
}
