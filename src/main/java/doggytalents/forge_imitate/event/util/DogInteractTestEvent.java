package doggytalents.forge_imitate.event.util;

import doggytalents.common.entity.Dog;
import doggytalents.forge_imitate.event.Event;

public class DogInteractTestEvent extends Event {

    public final Dog dog;

    public DogInteractTestEvent(Dog dog) {
        this.dog = dog;
    }

}
