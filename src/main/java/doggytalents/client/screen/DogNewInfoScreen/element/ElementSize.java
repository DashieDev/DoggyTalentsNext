package doggytalents.client.screen.DogNewInfoScreen.element;

import doggytalents.client.screen.DogNewInfoScreen.element.ElementPosition.PosType;
import net.minecraft.util.Mth;

public class ElementSize {
    AbstractElement element;
    int width;
    int height;
    float widthRatio;
    float heightRatio;
    SizeType type;
    boolean isDynamic = false;

    private ElementSize(AbstractElement element) {
        this.element = element;
    }

    public ElementSize(AbstractElement element, int width, int height) {
        this.element = element;
        this.type = SizeType.ABSOLUTE;
        this.width = width;
        this.height = height;
    }

    public ElementSize(AbstractElement element, float ratioX, float ratioY) {
        this.element = element;
        this.type = SizeType.RELATIVE;
        this.widthRatio = ratioX;
        this.heightRatio = ratioY;
        var p = element.getParent();
        if (p != null) {
            this.width = Mth.floor(p.getSize().width * this.widthRatio);
            this.height = Mth.floor(p.getSize().height * this.heightRatio);
        }
    }

    public ElementSize(AbstractElement element, int width, float ratioY) {
        this.element = element;
        this.type = SizeType.RELATIVE;
        this.heightRatio = ratioY;
        var p = element.getParent();
        if (p != null) {
            this.height = Mth.floor(p.getSize().height * this.heightRatio);
        }
        this.width = width;
    }

    public ElementSize(AbstractElement element, float ratioX, int height) {
        this.element = element;
        this.type = SizeType.RELATIVE;
        this.widthRatio = ratioX;
        var p = element.getParent();
        if (p != null) {
            this.width = Mth.floor(p.getSize().width * this.widthRatio);
        }
        this.height = height;
    }

    public ElementSize(AbstractElement element, int size) {
        this.element = element;
        this.type = SizeType.ABSOLUTE;
        this.width = size;
        this.height = size;
    }

    public static ElementSize createDynamicX(AbstractElement element, int sizeY) {
        var ret = new ElementSize(element);
        ret.type = SizeType.DYNAMIC_X;
        ret.height = sizeY;
        ret.isDynamic = true;
        return ret;
    }

    public static ElementSize createDynamicY(AbstractElement element, int sizeX) {
        var ret = new ElementSize(element);
        ret.type = SizeType.DYNAMIC_Y;
        ret.width = sizeX;
        ret.isDynamic = true;
        return ret;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void updateSize() {
        if (this.type == SizeType.DYNAMIC_X) {
            this.width = 0;
            var childs = this.element.children();
            for (var c : childs) {
                if (!(c instanceof AbstractElement)) continue;
                var element = (AbstractElement)c;
                if (element.getPosition().getType() != PosType.RELATIVE) continue;
                this.width += element.getSizeX();   
            }
            var p = this.element.getParent();
            if (p == null) return;
            var p_size = p.getSize();
            if (p_size.isDynamic()) {
                p_size.updateSize();
            }
        } else if (this.type == SizeType.DYNAMIC_Y) {
            this.height = 0;
            var childs = this.element.children();
            for (var c : childs) {
                if (!(c instanceof AbstractElement)) continue;
                var element = (AbstractElement)c;
                if (element.getPosition().getType() != PosType.RELATIVE) continue;
                this.height += element.getSizeY();   
            }
            var p = this.element.getParent();
            if (p == null) return;
            var p_size = p.getSize();
            if (p_size.isDynamic()) {
                p_size.updateSize();
            }
        }
    }

    public static enum SizeType {
        ABSOLUTE,
        RELATIVE,
        DYNAMIC_X,
        DYNAMIC_Y
    }

    public static ElementSize getDefault(AbstractElement element) {
        return new ElementSize(element, 0);
    }
}
