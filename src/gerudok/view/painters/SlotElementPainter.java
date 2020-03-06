package gerudok.view.painters;

import gerudok.model.elements.SlotElement;

import java.awt.*;
import java.io.Serializable;

public abstract class SlotElementPainter implements Serializable {
    protected Shape shape;

    public SlotElementPainter(SlotElement slotElement){

    }
    public abstract void paint(Graphics2D g, SlotElement slotElement);
    public abstract void paintElement();
    public abstract boolean isElementAt(Point point);

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
