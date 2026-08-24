package doggytalents.client.forward_imitate;

import java.util.List;
import java.util.NoSuchElementException;

public class LangUtil_1_20_under {
    
    public static <T> T getLast(List<T> list) {
        if (list.isEmpty())
            throw new NoSuchElementException("List is empty.");
        return list.get(list.size() - 1);
    }

}
