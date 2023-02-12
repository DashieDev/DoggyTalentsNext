package doggytalents.common.util.CachedSearchUtil;

import java.util.ArrayList;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class DogGreedyFireSafeSearchPath extends Path {

    private ArrayList<Node> nodes;
    private Dog dog;
    public boolean finished;
    private int maxLength;

    private DogGreedyFireSafeSearchPath(Dog dog, ArrayList<Node> nodes, int maxLength) {
        super(nodes, dog.blockPosition(), false);
        this.nodes = nodes;
        this.dog = dog;
        this.maxLength = maxLength;
    }
    
    public static DogGreedyFireSafeSearchPath create(Dog dog, int maxLength) {
        var initNodes = new ArrayList<Node>(maxLength);
        var dog_b0 = dog.blockPosition();
        initNodes.add(new Node(dog_b0.getX(), dog_b0.getY(), dog_b0.getZ()));
        var ret = new DogGreedyFireSafeSearchPath(dog, initNodes, maxLength);
        var pos = scanSurroundingForNextPos(ret);
        if (pos == null) return null;
        initNodes.clear();
        initNodes.add(pos);
        return ret;
    }

    @Override
    public void advance() {
        super.advance();
        if (finished) return;
        if (this.getNextNodeIndex() >= this.maxLength) return;
        tryAppendPath();
    }

    @Override
    public boolean isDone() {
        return super.isDone();
    }

    public void tryAppendPath() {
        scanSurroundingForNextPos(this);
    }

    private boolean containNode(BlockPos node0) {
        for (var node : this.nodes) {
            if (node.equals(new Node(node0.getX(), node0.getY(), node0.getZ()))) 
                return true;
        }
        return false;
    }

    private static Node scanSurroundingForNextPos(DogGreedyFireSafeSearchPath path) {
        if (path.nodes.isEmpty()) return null;
        var b0 = path.nodes.get(path.nodes.size()-1).asBlockPos();
        float malus_min = Float.MAX_VALUE;
        BlockPos b_chosen = null; 
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i == 0 && j == 0) continue;
                var b1 = b0.offset(i, 0, j).mutable();
                var b1_type = WalkNodeEvaluator.getBlockPathTypeStatic(path.dog.level, b1.mutable());
                int offsetY = 0;
                if (b1_type == BlockPathTypes.BLOCKED) {
                     offsetY= 1;
                } else if (b1_type == BlockPathTypes.OPEN) {
                    offsetY = -1;
                }
                if (offsetY != 0) {
                    b1.move(0, offsetY, 0);
                    b1_type = WalkNodeEvaluator.getBlockPathTypeStatic(path.dog.level, b1.mutable());
                }
                if (b1_type == BlockPathTypes.WALKABLE) {
                    path.finished = true;
                    return new Node(b1.getX(), b1.getY(), b1.getZ());
                }
                if (b1_type == BlockPathTypes.OPEN) continue;
                if (path.containNode(b1)) continue;
                float malus = path.dog.getPathfindingMalus(b1_type);
                if (malus < 0) continue;
                if (b_chosen == null) {
                    b_chosen = b1.immutable();
                    malus_min = malus;
                } else if (malus < malus_min) {
                    b_chosen = b1.immutable();
                    malus_min = malus;
                }
            }
        }
        if (b_chosen != null) {
            return new Node(b_chosen.getX(), b_chosen.getY(), b_chosen.getZ());
        } else {
            path.finished = true;
            return null;
        }
    }



}
