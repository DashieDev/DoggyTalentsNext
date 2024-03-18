package doggytalents.forge_imitate.event;

import net.minecraft.world.damagesource.DamageSource;

public class LootingLevelEvent extends Event {
    
    private DamageSource source;
    private int currentLevel;

    public LootingLevelEvent(DamageSource source, int currentLevel) {
        this.source = source;
        this.currentLevel = currentLevel;
    }

    public void setLootingLevel(int val) {
        this.currentLevel = val;
    }

    public int getLootingLevel() {
        return this.currentLevel;
    }

    public DamageSource getDamageSource() {
        return this.source;
    }


}
