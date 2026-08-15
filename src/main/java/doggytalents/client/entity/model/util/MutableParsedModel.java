package doggytalents.client.entity.model.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import doggytalents.client.entity.model.util.DTNModelCodec.ParsedModelResult;
import doggytalents.client.entity.model.util.DTNModelCodec.ParsedPart;

public class MutableParsedModel {

    private final Map<String, Node> roots = new HashMap<>();

    private MutableParsedModel() {}

    public static MutableParsedModel create() {
        return new MutableParsedModel();
    }

    public boolean isEmpty() {
        return this.roots.isEmpty();
    }

    public void addPath(ParsedModelPath path) {
        final var components = path.chain();
        Map<String, Node> p_childrens = roots;
        
        for (var component : components) {
            final var node = p_childrens
                .computeIfAbsent(component.id(), k -> new Node(component));
            p_childrens = node.children;
        }
    }

    public Optional<ParsedModelResult> buildHeadlessCopyFrom(ParsedModelResult result) {
        if (this.isEmpty())
            return Optional.empty();
        var parts = buildHeadLessCopyRoot();
        return Optional.of(result.copyWithParts(parts));
    }

    public List<ParsedPart> buildHeadLessCopyRoot() {
        if (roots.isEmpty())
            return List.of();
        var parts = new ArrayList<ParsedPart>(roots.size());
        for (var root : roots.values()) {
            parts.add(buildHeadlessCopyNode(root));
        }
        return parts;
    }

    public ParsedPart buildHeadlessCopyNode(Node node) {
        // I think we should return the part with its subtree intact 
        // since currently in DTNModelCodec::addParsedPartToDefinition we cut of
        // parsing the part to the main LayerDefinition immediately when it is a translucent part.
        // Therefore currently this translucent props automatically propagate its effect down its sub-tree
        if (node.part.props().translucent()) {
            //Because we are not making any copy here, I think it is safe to reuse the old one since this one is record
            return node.part; 
        }
        var children = node.children.values().stream()
            .map(this::buildHeadlessCopyNode)
            .toList();
        return node.part.headlessCopyWithChildren(children);
    }

    private static class Node {
        ParsedPart part;
        Map<String, Node> children;

        public Node(ParsedPart part) {
            this.part = part;
            this.children = new HashMap<>();
        }
    }

}
