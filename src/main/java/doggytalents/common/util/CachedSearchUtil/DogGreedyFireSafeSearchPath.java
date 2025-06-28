package doggytalents.common.util.CachedSearchUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.nav.DogNodeEvaluator;
import doggytalents.common.entity.ai.nav.DogPathNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class DogGreedyFireSafeSearchPath extends Path {

    private ArrayList<Node> nodes;
    private Dog dog;
    public boolean finished;
    private int maxLength;
    private Node startNode;
    private int walkableCount = 0;

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
        ret.startNode = start_node.get();
        var node_optional = scanSurroundingForNextPos(ret);
        if (!node_optional.isPresent())
            return null;
        initNodes.clear();
        initNodes.add(node_optional.get());
        return ret;
    }

    private static Optional<Node> getStartNode(Dog dog) {
        var dog_b0 = BlockPos.containing(DogPathNavigation.getTempDogPos(dog));
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
        var pos_below = pos.below();
        var state_below = dog.level().getBlockState(pos_below);
        return state_below.isCollisionShapeFullBlock(dog.level(), pos_below) 
            || DogNodeEvaluator.dogGetPathTypeFromState(
                dog.level(), pos_below) == PathType.BLOCKED;
    }

    private static Optional<Node> blockPosToNodeOptional(BlockPos pos) {
        return Optional.of(new Node(pos.getX(), pos.getY(), pos.getZ()));
    }

    @Override
    public void advance() {
        super.advance();
        if (finished) return;
        boolean append_result = tryAppendPath();
        if (!append_result)
            this.finished = true;
    }

    public int getWalkableCount() {
        return this.walkableCount;
    }

    @Override
    public boolean isDone() {
        if (this.finished)
            return true;
        return this.getNextNodeIndex() >= this.nodes.size();
    }

    public boolean tryAppendPath() {
        if (this.getNextNodeIndex() >= this.maxLength)
            return false;
        
        var node_optional = scanSurroundingForNextPos(this);
        if (!node_optional.isPresent())
            return false;
        var node = node_optional.get();
        if (node.type != PathType.WALKABLE && this.walkableCount > 0)
            return false;
        
        this.nodes.add(node);
        if (node.type == PathType.WALKABLE)
            ++this.walkableCount;
        return true;
    }

    private boolean containNode(Node node) {
        for (var node1 : this.nodes) {
            if (node1.equals(node)) 
                return true;
        }
        return false;
    }

    private static Optional<Node> scanSurroundingForNextPos(DogGreedyFireSafeSearchPath path) {
        if (path.nodes.isEmpty()) 
            return Optional.empty();
        var b0 = path.nodes.get(path.nodes.size()-1).asBlockPos();
        float malus_min = Float.MAX_VALUE;
        Node node_chosen = null;
        boolean[] BLOCKED_0_Z = new boolean[2];
        boolean[] BLOCKED_X_0 = new boolean[2];
        var pathtype_above = WalkNodeEvaluator.getPathTypeStatic(path.dog, b0.above());
        Node last_resort = null;
        //Cross XZ
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i == 0 && j == 0) 
                    continue;
                if (i*j != 0) 
                    continue;
                var node = findDogNode(path.dog, b0.offset(i, 0, j));
                boolean require_jump = node.y > b0.getY();
                boolean is_diagonally_block = 
                    path.dog.getPathfindingMalus(node.type) < 0 
                    || require_jump;
                if (is_diagonally_block) {
                    if (i != 0) {
                        BLOCKED_X_0[i > 0 ? 1 : 0] = true;
                    } else {
                        BLOCKED_0_Z[j > 0 ? 1 : 0] = true;
                    }
                }

                if (!canAddNodeToPath(path, node))
                    continue;
                if (require_jump && pathtype_above == PathType.BLOCKED)
                    continue;
                boolean clear_walkable =
                    node.type == PathType.WALKABLE
                    && !(require_jump && pathtype_above != PathType.OPEN);
                if (clear_walkable) {
                    return Optional.of(node);
                }
                boolean is_last_resort = 
                    require_jump && pathtype_above != PathType.BLOCKED;
                if (is_last_resort) {
                    last_resort = node;
                }
                var malus = path.dog.getPathfindingMalus(node.type);
                if (node_chosen == null || malus < malus_min) {
                    node_chosen = node;
                    malus_min = malus;
                }
            }
        }
        //Corner XZ
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i == 0 && j == 0)
                    continue;
                if (i*j == 0)
                    continue;
                boolean diagonal_blocked = 
                    BLOCKED_0_Z[j > 0 ? 1 : 0]
                    && BLOCKED_X_0[i > 0 ? 1 : 0];
                if (diagonal_blocked)
                    continue;
                var node = findDogNode(path.dog, b0.offset(i, 0, j));
                boolean require_jump = node.y > b0.getY();
                
                if (!canAddNodeToPath(path, node))
                    continue;
                if (require_jump && pathtype_above == PathType.BLOCKED)
                    continue;
                boolean clear_walkable =
                    node.type == PathType.WALKABLE
                    && !(require_jump && pathtype_above != PathType.OPEN);
                if (clear_walkable) {
                    return Optional.of(node);
                }
                boolean is_last_resort = 
                    require_jump && pathtype_above != PathType.BLOCKED;
                if (is_last_resort) {
                    last_resort = node;
                }

                var malus = path.dog.getPathfindingMalus(node.type);
                if (node_chosen == null || malus < malus_min) {
                    node_chosen = node;
                    malus_min = malus;
                }
            }
        }
        if (node_chosen != null) {
            return Optional.of(node_chosen);
        } else if (last_resort != null) {
            return Optional.of(last_resort);
        } else {
            return Optional.empty();
        }
    }

    private static Node findDogNode(Dog dog, BlockPos pos) {
        var b1 = pos.mutable();
        var b1_type = WalkNodeEvaluator.getPathTypeStatic(dog, b1.mutable());
        int offsetY = 0;
        if (b1_type == PathType.BLOCKED) {
                offsetY= 1;
        } else if (b1_type == PathType.OPEN) {
            offsetY = -1;
        }
        if (offsetY != 0) {
            b1.move(0, offsetY, 0);
            b1_type = WalkNodeEvaluator.getPathTypeStatic(dog, b1.mutable());
        }
        var ret_node = new Node(b1.getX(), b1.getY(), b1.getZ());
        ret_node.type = b1_type;
        return ret_node;
    }

    private static boolean canAddNodeToPath(DogGreedyFireSafeSearchPath path, Node node) {
        boolean already_in_path = 
            path.containNode(node) || path.startNode.equals(node);
        if (already_in_path)
            return false;
        if (node.type == PathType.OPEN)
            return false;
        if (path.dog.getPathfindingMalus(node.type) < 0)
            return false;
        return true;
    }

}
