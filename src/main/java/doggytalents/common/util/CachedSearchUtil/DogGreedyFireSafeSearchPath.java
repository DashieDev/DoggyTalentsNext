package doggytalents.common.util.CachedSearchUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.util.Mth;
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
        var start_node = getStartNode(dog);
        if (!start_node.isPresent())
            return null;
        var initNodes = new ArrayList<Node>(maxLength);
        initNodes.add(start_node.get());
        var ret = new DogGreedyFireSafeSearchPath(dog, initNodes, maxLength);
        var pos = scanSurroundingForNextPos(ret);
        if (pos == null) return null;
        initNodes.clear();
        initNodes.add(pos);
        return ret;
    }

    private static Optional<Node> getStartNode(Dog dog) {
        var dog_b0 = dog.blockPosition();
        if (isValidStart(dog, dog_b0))
            return blockPosToNodeOptional(dog_b0);
        
        var dog_bb = dog.getBoundingBox();
        int min_x = Mth.floor(dog_bb.minX);
        int min_z = Mth.floor(dog_bb.minZ);
        int max_x = Mth.floor(dog_bb.maxX);
        int max_z = Mth.floor(dog_bb.maxZ);
        for (int i = min_x; i <= max_x; ++i) {
            for (int j = min_z; j <= max_z; ++j) {
                var check_b0 = new BlockPos(i, dog_b0.getY(), j);
                if (isValidStart(dog, check_b0))
                    return blockPosToNodeOptional(check_b0);
            }
        }
        return Optional.empty();
    }

    private static boolean isValidStart(Dog dog, BlockPos pos) {
        var pos_type = WalkNodeEvaluator.getBlockPathTypeStatic(dog.level(), pos.mutable());
        if (pos_type == BlockPathTypes.OPEN)
            return false;
        var malus = dog.getPathfindingMalus(pos_type);
        return malus >= 0;
    }

    private static Optional<Node> blockPosToNodeOptional(BlockPos pos) {
        return Optional.of(new Node(pos.getX(), pos.getY(), pos.getZ()));
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
        var node = scanSurroundingForNextPos(this);
        if (node != null) {
            this.nodes.add(node);
        } 
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
        Node node_chosen = null;
        // X = 0 && Y = (i > 0 ? 1 : 0)
        boolean[] X0_BLOCKED = new boolean[2];
        // Y = 0 && X = (i > 0 ? 1 : 0)
        boolean[] Y0_BLOCKED = new boolean[2];
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i == 0 && j == 0) continue;
                //Search the cross first
                if (i*j != 0) continue;
                var node = checkPos(path, b0.offset(i, 0, j));
                if (node == null) continue;
                if (node.type == BlockPathTypes.WALKABLE) {
                    return node;
                }
                if (node.type == BlockPathTypes.BLOCKED) {
                    if (i != 0) {
                        Y0_BLOCKED[i > 0 ? 1 : 0] = true;
                    } else {
                        X0_BLOCKED[j > 0 ? 1 : 0] = true;
                    }
                    continue;
                }
                var malus = path.dog.getPathfindingMalus(node.type);
                if (node_chosen == null || malus < malus_min) {
                    node_chosen = node;
                    malus_min = malus;
                }
            }
        }
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i == 0 && j == 0) continue;
                //Then the corner
                if (i*j == 0) continue;
                boolean diagonal_blocked = 
                    X0_BLOCKED[j > 0 ? 1 : 0]
                    && Y0_BLOCKED[i > 0 ? 1 : 0];
                if (diagonal_blocked) continue;
                var node = checkPos(path, b0.offset(i, 0, j));
                if (node == null) continue;
                if (node.type == BlockPathTypes.WALKABLE) {
                    return node;
                }
                if (node.type == BlockPathTypes.BLOCKED) {
                    if (i != 0) {
                        Y0_BLOCKED[i > 0 ? 1 : 0] = true;
                    } else {
                        X0_BLOCKED[j > 0 ? 1 : 0] = true;
                    }
                    continue;
                }
                var malus = path.dog.getPathfindingMalus(node.type);
                if (node_chosen == null || malus < malus_min) {
                    node_chosen = node;
                    malus_min = malus;
                }
            }
        }
        if (node_chosen != null) {
            return node_chosen;
        } else {
            path.finished = true;
            return null;
        }
    }

    private static Node checkPos(DogGreedyFireSafeSearchPath path, BlockPos pos) {
        var b1 = pos.mutable();
        var b1_type = WalkNodeEvaluator.getBlockPathTypeStatic(path.dog.level(), b1.mutable());
        int offsetY = 0;
        if (b1_type == BlockPathTypes.BLOCKED) {
                offsetY= 1;
        } else if (b1_type == BlockPathTypes.OPEN) {
            offsetY = -1;
        }
        if (offsetY != 0) {
            b1.move(0, offsetY, 0);
            b1_type = WalkNodeEvaluator.getBlockPathTypeStatic(path.dog.level(), b1.mutable());
        }
        if (b1_type == BlockPathTypes.WALKABLE 
            || b1_type == BlockPathTypes.BLOCKED) {
            var ret_node = new Node(b1.getX(), b1.getY(), b1.getZ());
            ret_node.type = b1_type;
            return ret_node;
        }
        if (b1_type == BlockPathTypes.OPEN) return null;
        if (path.containNode(b1)) return null;
        float malus = path.dog.getPathfindingMalus(b1_type);
        if (malus < 0) return null;
        var ret_node = new Node(b1.getX(), b1.getY(), b1.getZ());
        ret_node.type = b1_type;
        return ret_node;
    }

}
