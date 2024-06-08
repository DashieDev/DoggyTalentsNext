package doggytalents.forge_imitate.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;

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

    public static class PositionCheck extends Event {

        private final Entity entity;
        private Result result = Result.PASS;
        private final ServerLevelAccessor level;
        private final MobSpawnType spawnType;

        public PositionCheck(Entity entity, ServerLevelAccessor sLevelAccessor, MobSpawnType spawnType) {
            this.entity = entity;
            this.spawnType = spawnType;
            this.level = sLevelAccessor;
        }

        public Entity getEntity() {
            return this.entity;
        }

        public ServerLevelAccessor getLevel() {
            return this.level;
        }

        public MobSpawnType getSpawnType() {
            return this.spawnType;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public Result getResult() {
            return this.result;
        }

        public static enum Result {
            ALLOW, PASS
        }
        

    }

}
