package doggytalents.forge_imitate.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;

public class MobSpawnEvent {
    
    public static class FinalizeSpawn extends Event {
        
        private final MobSpawnType type;
        private final LivingEntity entity;
        private SpawnGroupData data;

        public FinalizeSpawn(LivingEntity entity, MobSpawnType spawnType,
            SpawnGroupData data) {
            this.type = spawnType;
            this.entity = entity;
            this.data = data;
        }

        public MobSpawnType getSpawnType() {
            return this.type;
        }

        public LivingEntity getEntity() {
            return this.entity;
        }

        public SpawnGroupData getSpawnData() {
            return this.data;
        }

        public void setSpawnData(SpawnGroupData data) {
            this.data = data;
        }

    }

}
