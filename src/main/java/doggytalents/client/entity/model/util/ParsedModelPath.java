package doggytalents.client.entity.model.util;

import java.util.ArrayDeque;
import java.util.List;
import java.util.stream.Stream;

import doggytalents.client.entity.model.util.DTNModelCodec.ParsedPart;

public class ParsedModelPath {
    
    private final List<ParsedPart> chain;

    private ParsedModelPath(List<ParsedPart> chain) {
        this.chain = chain;
    }

    public static MutableParsedModelPath mutable() {
        return new MutableParsedModelPath();
    } 

    public ParsedPart target() {
        return chain.getLast();
    }

    public List<ParsedPart> chain() {
        return this.chain;
    }

    public static class MutableParsedModelPath {

        private final ArrayDeque<ParsedPart> entries;
        
        private MutableParsedModelPath() {
            this.entries = new ArrayDeque<>(3);
        }

        public void push(ParsedPart child) {
            this.entries.addLast(child);
        }

        public void pop() {
            this.entries.removeLast();
        }

        public ParsedModelPath immutable() {
            if (entries.isEmpty())
                throw new IllegalArgumentException("ModelPath cannot be empty");
            return new ParsedModelPath(entries.stream().toList());
        }

        public ParsedModelPath immutable(ParsedPart target) {
            return new ParsedModelPath(Stream.concat(entries.stream(), Stream.of(target)).toList());
        }
    }
}
