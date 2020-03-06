package gerudok.model.elements;

import gerudok.view.painters.CirclePainter;
import gerudok.view.painters.RectanglePainter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

public class RectangleElement extends SlotDevice{

    public RectangleElement(Point2D position, Dimension dimension, Stroke stroke, Paint paint, double angle) {
        super(position, dimension, stroke, paint, angle);
        slotPainter = new RectanglePainter(this);
    }

    public RectangleElement(RectangleElement rectangle){
        super(rectangle);
        setName("rectangle");
        slotPainter = new RectanglePainter(this);
    }

    public static SlotDevice createDefault(Point2D position){
        Point2D pos = (Point2D) position.clone();
        Paint paint = new Color(255,255,255);//boja unutar shapa

        //kreiranje circle elementa
        RectangleElement rectangleElement = new RectangleElement(pos, new Dimension(70,70), new BasicStroke(2f), paint, 0);
        return rectangleElement;
    }

    @Override
    public SlotElement clone() {
        return new RectangleElement(this);
    }
}
