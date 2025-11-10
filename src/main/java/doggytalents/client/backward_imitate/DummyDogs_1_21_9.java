package doggytalents.client.backward_imitate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import doggytalents.common.entity.Dog;

public class DummyDogs_1_21_9 {

    public static enum Type { FIRST, SECOND, THIRD }
    private static final int DOG_LIST_SIZE = Type.values().length;
    private final List<Dog> dogs;

    private DummyDogs_1_21_9(List<Dog> dogs) {
        this.dogs = dogs;
    }

    public static DummyDogs_1_21_9 create(Supplier<Dog> dogCreator) {    
        var dogList = new ArrayList<Dog>(DOG_LIST_SIZE);
        for (int i = 0; i < DOG_LIST_SIZE; ++i)
            dogList.add(dogCreator.get());
        return new DummyDogs_1_21_9(dogList);
    }

    public Dog get(Type type) {
        return dogs.get(type.ordinal());
    }
}
