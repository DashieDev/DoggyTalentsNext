package doggytalents.api.feature;

import java.util.Arrays;
import java.util.Comparator;

import doggytalents.api.inferface.AbstractDog;

public enum EnumMode {

    INJURED(-1, "incapacitated", false, false),
    DOCILE(0, "docile", true, false),
    WANDERING(1, "wandering", false, false, true),
    AGGRESIVE(2, "aggressive"),
    BERSERKER(3, "berserker"),
    BERSERKER_MINOR(4, "berserker_minor"),
    TACTICAL(5, "tactical"),
    PATROL(6, "patrol", false, true, true),
    GUARD(7, "guard"),
    GUARD_FLAT(8, "guard_flat"),
    GUARD_MINOR(9, "guard_minor");

    private int index;
    private boolean shouldFollowOwner = true;
    private boolean shouldAttack = true;
    private boolean canWander = false;
    private String saveName;
    private String unlocalisedTip;
    private String unlocalisedName;
    private String unlocalisedInfo;

    public static final EnumMode[] VALUES = 
        Arrays.stream(EnumMode.values())
        .filter(x ->x.getIndex() >= 0)
        .sorted(Comparator.comparingInt(EnumMode::getIndex))
        .toArray(size -> {
            return new EnumMode[size];
        });

    private EnumMode(int index, String name) {
        this(index, name, true, true);
    }
    
    private EnumMode(int index, String name, boolean shouldFollowOwner, boolean shouldAttack) {
        this(index, name, "dog.mode." + name, "dog.mode." + name + ".indicator", "dog.mode." + name + ".description", shouldFollowOwner, shouldAttack);
    }

    private EnumMode(int index, String name, boolean shouldFollowOwner, boolean shouldAttack, boolean canWander) {
        this(index, name, "dog.mode." + name, "dog.mode." + name + ".indicator", "dog.mode." + name + ".description", shouldFollowOwner, shouldAttack);
        this.canWander = canWander;
    }

    private EnumMode(int index, String mode, String unlocalisedName, String tip, String info, boolean shouldFollowOwner, boolean shouldAttack) {
        this.index = index;
        this.saveName = mode;
        this.unlocalisedName = unlocalisedName;
        this.unlocalisedTip = tip;
        this.unlocalisedInfo = info;
        this.shouldFollowOwner = shouldFollowOwner;
        this.shouldAttack = shouldAttack;
    }

    public int getIndex() {
        return this.index;
    }

    public String getSaveName() {
        return this.saveName;
    }

    public String getTip() {
        return this.unlocalisedTip;
    }

    public String getUnlocalisedName() {
        return this.unlocalisedName;
    }

    public String getUnlocalisedInfo() {
        return this.unlocalisedInfo;
    }

    public void onModeSet(AbstractDog dog, EnumMode prev) {
        switch(prev) {
        default:
            dog.getNavigation().stop();
            dog.setTarget(null);
            dog.setLastHurtByMob(null);
            break;
        }
    }

    public EnumMode previousMode() {
        int i = this.getIndex() - 1;
        if (i < 0) {
            i = VALUES.length - 1;
        }
        return VALUES[i];
    }

    public EnumMode nextMode() {
        int i = this.getIndex() + 1;
        if (i >= VALUES.length) {
            i = 0;
        }
        return VALUES[i];
    }

    public boolean shouldFollowOwner() {
        return shouldFollowOwner;
    }

    public boolean shouldAttack() {
        return shouldAttack;
    }

    public boolean canWander() {
        return canWander;
    }

    public static EnumMode byIndex(int i) {
        if (i == -1) {
            return EnumMode.INJURED;
        }
        if (i < 0 || i >= VALUES.length) {
            i = EnumMode.DOCILE.getIndex();
        }
        return VALUES[i];
    }

    public static EnumMode bySaveName(String saveName) {
        for (EnumMode gender : EnumMode.values()) {
            if (gender.saveName.equals(saveName)) {
                return gender;
            }
        }

        return DOCILE;
    }
}