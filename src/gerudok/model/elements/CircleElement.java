package gerudok.model.elements;

import gerudok.view.painters.CirclePainter;

import java.awt.*;
import java.awt.geom.Point2D;

public class CircleElement extends SlotDevice{

    public CircleElement(Point2D position, Dimension dimension, Stroke stroke, Paint paint, double angle) {
        super(position, dimension, stroke, paint, angle);
        slotPainter = new CirclePainter(this);
    }

    public CircleElement(CircleElement circle){
        super(circle);
        setName("kopija");
        slotPainter = new CirclePainter(this);
    }

    public static SlotDevice createDefault(Point2D position){
        Point2D pos = (Point2D) position.clone();
        Paint paint = new Color(255,255,255);//boja unutar shapa

        //kreiranje circle elementa
        CircleElement circleElement = new CircleElement(pos, new Dimension(70,70), new BasicStroke(2f), paint, 0);
        return circleElement;
    }

    @Override
    public SlotElement clone() {
        return new CircleElement(this);
    }
}
