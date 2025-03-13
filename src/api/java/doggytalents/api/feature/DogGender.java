package doggytalents.api.feature;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import net.minecraft.util.RandomSource;

public enum DogGender {

    MALE(1, "male"),
    FEMALE(2, "female"),
    UNISEX(0, "unisex");

    private int index;
    private String saveName;
    private String unlocalisedTip;
    private String unlocalisedName;
    private String unlocalisedPronoun;
    private String unlocalisedSubject;
    private String unlocalisedPossessiveAdj;
    private String unlocalisedTitle;

    public static final DogGender[] VALUES = Arrays.stream(DogGender.values()).sorted(Comparator.comparingInt(DogGender::getIndex)).toArray(size -> {
        return new DogGender[size];
    });

    private DogGender(int index, String name) {
        this.index = index;
        this.saveName = name;
        this.unlocalisedName = "dog.gender." + name;
        this.unlocalisedTip = this.unlocalisedName + ".indicator";
        this.unlocalisedPronoun = this.unlocalisedName + ".pronoun";
        this.unlocalisedSubject = this.unlocalisedName + ".subject";
        this.unlocalisedPossessiveAdj = this.unlocalisedName + ".possessive_adjective";
        this.unlocalisedTitle = this.unlocalisedName + ".title";
    }

    public int getIndex() {
        return this.index;
    }

    public String getSaveName() {
        return this.saveName;
    }

    public String getUnlocalisedTip() {
        return this.unlocalisedTip;
    }

    public String getUnlocalisedName() {
        return this.unlocalisedName;
    }

    public String getUnlocalisedPronoun() {
        return this.unlocalisedPronoun;
    }

    public String getUnlocalisedSubject() {
        return this.unlocalisedSubject;
    }

    public String getUnlocalisedTitle() {
        return this.unlocalisedTitle;
    }

    public String getUnlocalisedPossessiveAdj() {
        return this.unlocalisedPossessiveAdj;
    }

    public boolean canMateWith(DogGender gender) {
        boolean equalGenders = this == gender;
        return (equalGenders && this == DogGender.UNISEX) || !equalGenders;
    }

    public static DogGender byIndex(int i) {
        if (i < 0 | i >= VALUES.length) {
            i = DogGender.UNISEX.getIndex();
        }
        return VALUES[i];
    }

    public static DogGender bySaveName(String saveName) {
        for (DogGender gender : DogGender.values()) {
            if (gender.getSaveName().equals(saveName)) {
                return gender;
            }
        }

        return UNISEX;
    }

    public static DogGender random(RandomSource rng) {
        return rng.nextBoolean() ? MALE : FEMALE;
    }

}