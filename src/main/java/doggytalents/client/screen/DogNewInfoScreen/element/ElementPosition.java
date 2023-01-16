package doggytalents.client.screen.DogNewInfoScreen.element;

public class ElementPosition {

    private AbstractElement element;
    private int x;
    private int y;
    private int originAbsoluteX;
    private int originAbsoluteY;
    private ChildDirection dir = ChildDirection.ROW;
    private PosType type;

    public ElementPosition(AbstractElement element, int x, int y, PosType type) {
        this.element = element;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getRealX() {
        if (this.type == PosType.ABSOLUTE) {
            var realX = this.x;
            var p = this.element.getParent();
            while(p != null && p.getPosition().type == PosType.RELATIVE) {
                realX += p.getPosition().x;
                p = p.getParent();
            }
            return realX;
        } else {
            return this.x;
        }
        
    }

    public int getRealY() {
        if (this.type == PosType.ABSOLUTE) {
            var realY = this.y;
            var p = this.element.getParent();
            while(p != null && p.getPosition().type == PosType.RELATIVE) {
                realY += p.getPosition().y;
                p = p.getParent();
            }
            return realY;
        } else {
            return this.y;
        }
    }

    public ChildDirection getChildDirection() {
        return this.dir;
    }
    
    public static enum PosType {
        RELATIVE,
        ABSOLUTE,
        FIXED
    }

    public static enum ChildDirection {
        COL,
        ROW
    }

    public static ElementPosition getDefault(AbstractElement element) {
        return new ElementPosition(element, 0, 0, PosType.RELATIVE);
    }
}
