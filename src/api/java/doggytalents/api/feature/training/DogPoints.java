package doggytalents.api.feature.training;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;

public class DogPoints {
    
    private int firePoints;
    private int waterPoints;
    private int walkPoints; 
    private int treatPoints;
    private int kamiPoints;
    private int airPoints;
    private int attackPoints;

    public DogPoints() {

    }

    public int getPoint(PointType type) {
        switch (type) {
        case FIRE:
            return firePoints;
        case WATER:
            return waterPoints;
        case WALK:
            return walkPoints;
        case KAMI:
            return kamiPoints;
        case AIR:
            return airPoints;
        case ATTACK:
            return attackPoints;
        default:
            return treatPoints;
        }
    }

    public void setPoint(PointType type, int val) {
        switch (type) {
        case FIRE:
            firePoints = val;
            break;
        case WATER:
            waterPoints = val;
            break;
        case WALK:
            walkPoints = val;
            break;
        case KAMI:
            kamiPoints = val;
            break;
        case AIR:
            airPoints = val;
            break;
        case ATTACK:
            attackPoints = val;
            break;
        default:
            treatPoints = val;
            break;
        }
    }

    public void save(CompoundTag tag) {
        var pointTag = new CompoundTag();
        pointTag.putInt("fire", firePoints);
        pointTag.putInt("water", waterPoints);
        pointTag.putInt("walk", walkPoints);
        pointTag.putInt("treat", treatPoints);
        pointTag.putInt("kami", kamiPoints);
        pointTag.putInt("air", airPoints);
        pointTag.putInt("attack", attackPoints);
        tag.put("DTN_dogPoints", pointTag);
    }

    public void load(CompoundTag tag) {
        var pointTag = tag.getCompound("DTN_dogPoints");
        if (pointTag == null) 
            return;
        this.firePoints = pointTag.getInt("fire");
        this.waterPoints = pointTag.getInt("water");
        this.walkPoints = pointTag.getInt("walk");
        this.treatPoints = pointTag.getInt("treat");
        this.kamiPoints = pointTag.getInt("kami");
        this.airPoints = pointTag.getInt("air");
        this.attackPoints = pointTag.getInt("attack");
        if (tag.contains("level_normal", Tag.TAG_ANY_NUMERIC))
            this.treatPoints = tag.getInt("level_normal");
        if (tag.contains("level_kami", Tag.TAG_ANY_NUMERIC))
            this.kamiPoints = tag.getInt("level_kami");
        if (tag.contains("level_dire", Tag.TAG_ANY_NUMERIC))
            this.kamiPoints = tag.getInt("level_dire");    
    }

    public void writeToBuf(FriendlyByteBuf buf) {
        buf.writeInt(firePoints);
        buf.writeInt(waterPoints);
        buf.writeInt(walkPoints);
        buf.writeInt(treatPoints);
        buf.writeInt(kamiPoints);
        buf.writeInt(airPoints);
        buf.writeInt(attackPoints);
    }

    public void readFromBuf(FriendlyByteBuf buf) {
        this.firePoints = buf.readInt();
        this.waterPoints = buf.readInt();
        this.walkPoints = buf.readInt();
        this.treatPoints = buf.readInt();
        this.kamiPoints = buf.readInt();
        this.airPoints = buf.readInt();
        this.attackPoints = buf.readInt();
    }

    

    public static enum PointType {
        FIRE, WATER, WALK, TREAT, KAMI, AIR, ATTACK
    }


}
