package doggytalents.common.util.CachedSearchUtil;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.nav.DogNodeEvaluator;
import doggytalents.common.entity.ai.nav.DogPathNavigation;
import doggytalents.common.util.DogUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class DogGreedyFireSafeSearchPath {

    private final Path path;
    private final int walkableCount;

    private DogGreedyFireSafeSearchPath(Path path, int walkableCount) {
        this.path = path;
        this.walkableCount = walkableCount;
    }

    public Path getPath() {
        return path;
    }

    public int getWalkableCount() {
        return this.walkableCount;
    }

    public boolean isDone() {
        return this.path.isDone();
    }

    public Node getNode(int index) {
        return this.path.getNode(index);
    }

    public Node getEndNode() {
        return this.path.getEndNode();
    }

    public static DogGreedyFireSafeSearchPath create(Dog dog, int maxLength) {
        var start_node_pair = getStartNode(dog);
        if (!start_node_pair.isPresent())
            return null;
        boolean skip_start = start_node_pair.get().getLeft();
        var start_node = start_node_pair.get().getRight();
        var nodes = new ArrayList<Node>(maxLength);
        nodes.add(start_node);

        // Build path upfront greedily
        Node current = start_node;
        Node startRef = start_node;
        int walkableCount = 0;
        boolean finished = false;

        for (int i = 1; i < maxLength && !finished; i++) {
            var node_optional = scanSurroundingForNextPos(dog, current, nodes, startRef);
            if (!node_optional.isPresent()) {
                finished = true;
                break;
            }
            var node = node_optional.get();
            if (node.type != PathType.WALKABLE && walkableCount > 0) {
                finished = true;
                break;
            }
            nodes.add(node);
            if (node.type == PathType.WALKABLE)
                ++walkableCount;
            if (current.y == node.y || nodes.size() <= 2)
                node.type = PathType.WALKABLE;
            current = node;
        }

        if (skip_start && !nodes.isEmpty()) {
            nodes.remove(0);
        }

        if (nodes.isEmpty())
            return null;

        nodes.get(0).type = PathType.WALKABLE;
        var targetPos = nodes.get(nodes.size() - 1).asBlockPos();
        var path = new Path(nodes, targetPos, false);
        return new DogGreedyFireSafeSearchPath(path, walkableCount);
    }

    private static Optional<Pair<Boolean, Node>> getStartNode(Dog dog) {
        var dog_b0 = BlockPos.containing(DogPathNavigation.getTempDogPos(dog));
        if (!dog.onGround()) {
            var dog_b0_under = dog_b0.below();
            if (DogUtil.isWalkNodeEvaluatorOpenPos(dog, dog_b0_under))
                dog_b0 = dog_b0_under;
        }
        if (isValidStart(dog, dog_b0))
            return blockPosToNodeOptional(true, dog_b0);

        var dog_bb = dog.getBoundingBox();
        var check_pos = new BlockPos.MutableBlockPos()
            .setY(dog_b0.getY());
        if (setAndCheckValidStart(dog, check_pos, dog_bb.minX, dog_bb.minZ))
            return blockPosToNodeOptional(false, check_pos);
        if (setAndCheckValidStart(dog, check_pos, dog_bb.minX, dog_bb.maxZ))
            return blockPosToNodeOptional(false, check_pos);
        if (setAndCheckValidStart(dog, check_pos, dog_bb.maxX, dog_bb.minZ))
            return blockPosToNodeOptional(false, check_pos);
        if (setAndCheckValidStart(dog, check_pos, dog_bb.maxX, dog_bb.maxZ))
            return blockPosToNodeOptional(false, check_pos);
        return Optional.empty();
    }

    private static boolean setAndCheckValidStart(Dog dog, BlockPos.MutableBlockPos pos_mut, double x, double z) {
        int y = pos_mut.getY();
        pos_mut.set(x, y, z);
        return isValidStart(dog, pos_mut);
    }

    private static boolean isValidStart(Dog dog, BlockPos pos) {
        var pos_below = pos.below();
        var state_below = dog.level().getBlockState(pos_below);
        return state_below.isCollisionShapeFullBlock(dog.level(), pos_below)
            || DogNodeEvaluator.dogGetPathTypeFromState(
                dog.level(), pos_below) == PathType.BLOCKED;
    }

    private static Optional<Pair<Boolean, Node>> blockPosToNodeOptional(boolean skipFirstNode, BlockPos pos) {
        var ret_node = new Node(pos.getX(), pos.getY(), pos.getZ());
        ret_node.type = PathType.WALKABLE;
        return Optional.of(Pair.of(skipFirstNode, ret_node));
    }

    private static boolean containNode(ArrayList<Node> nodes, Node startNode, Node node) {
        if (startNode.equals(node)) return true;
        for (var node1 : nodes) {
            if (node1.equals(node))
                return true;
        }
        return false;
    }

    private static Optional<Node> scanSurroundingForNextPos(Dog dog, Node current, ArrayList<Node> nodes, Node startNode) {
        var b0 = current.asBlockPos();
        float malus_min = Float.MAX_VALUE;
        Node node_chosen = null;
        boolean[] BLOCKED_0_Z = new boolean[2];
        boolean[] BLOCKED_X_0 = new boolean[2];
        var pathtype_above = WalkNodeEvaluator.getPathTypeStatic(dog, b0.above());
        Node last_resort = null;
        //Cross XZ
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i == 0 && j == 0)
                    continue;
                if (i * j != 0)
                    continue;
                var node = findDogNode(dog, b0.offset(i, 0, j));
                boolean require_jump = node.y > b0.getY();
                boolean is_diagonally_block =
                    dog.getPathfindingMalus(node.type) < 0
                    || require_jump;
                if (is_diagonally_block) {
                    if (i != 0) {
                        BLOCKED_X_0[i > 0 ? 1 : 0] = true;
                    } else {
                        BLOCKED_0_Z[j > 0 ? 1 : 0] = true;
                    }
                }

                if (!canAddNodeToPath(dog, nodes, startNode, node))
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
                } else {
                    var malus = dog.getPathfindingMalus(node.type);
                    if (node_chosen == null || malus < malus_min) {
                        node_chosen = node;
                        malus_min = malus;
                    }
                }
            }
        }
        //Corner XZ
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i == 0 && j == 0)
                    continue;
                if (i * j == 0)
                    continue;
                boolean diagonal_blocked =
                    BLOCKED_0_Z[j > 0 ? 1 : 0]
                    && BLOCKED_X_0[i > 0 ? 1 : 0];
                if (diagonal_blocked)
                    continue;
                var node = findDogNode(dog, b0.offset(i, 0, j));
                boolean require_jump = node.y > b0.getY();

                if (!canAddNodeToPath(dog, nodes, startNode, node))
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
                } else {
                    var malus = dog.getPathfindingMalus(node.type);
                    if (node_chosen == null || malus < malus_min) {
                        node_chosen = node;
                        malus_min = malus;
                    }
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
            offsetY = 1;
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

    private static boolean canAddNodeToPath(Dog dog, ArrayList<Node> nodes, Node startNode, Node node) {
        if (containNode(nodes, startNode, node))
            return false;
        if (node.type == PathType.OPEN)
            return false;
        if (dog.getPathfindingMalus(node.type) < 0)
            return false;
        return true;
    }
}
