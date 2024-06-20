package doggytalents.common.entity.personality;

import doggytalents.common.entity.Dog;

public class DogPersonality {
    
    //Clingy
    private int fearlessOfFire = 0;
    private int clingy = 0;
    private int flowerAppericate = 0;

    private DogPersonality() {}

    public static DogPersonality createPersonality(Dog dog) {
        var personality = new DogPersonality();
        personality.randomize(dog);
        return personality;
    }

    private void randomize(Dog dog) {
        var r = dog.getRandom();
        //All dimished
        this.fearlessOfFire = -1 + r.nextInt(3);
        this.clingy = r.nextInt(2);
        this.flowerAppericate = r.nextInt(2);
    }

    public int getClingy(Dog dog) {
        // var variant = dog.getClassicalVar();
        // switch (variant) {
        // case CHESTNUT:
        // case SPOTTED:
        // case SNOWY:
        //     return 1;
        // case STRIPED:
        // case RUSTY:
        //     return 0;
        // default:
        //     return this.clingy;
        // }
        return 0;
    }

    private int getFlowerAppreciate(Dog dog) {
        // var variant = dog.getClassicalVar();
        // switch (variant) {
        // case SPOTTED:
        // case SNOWY:
        // case WOOD:
        //     return 1;
        // case STRIPED:
        // case RUSTY:
        //     return -1;
        // default:
        //     return this.flowerAppericate;
        // }
        return 0;
    }

}
