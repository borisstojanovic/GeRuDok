package gerudok.model.elements;

import gerudok.view.painters.CirclePainter;
import gerudok.view.painters.TrianglePainter;

import java.awt.*;
import java.awt.geom.Point2D;

public class TriangleElement extends SlotDevice {
    public TriangleElement(Point2D position, Dimension dimension, Stroke stroke, Paint paint, double angle) {
        super(position, dimension, stroke, paint, angle);
        slotPainter = new TrianglePainter(this);
    }

    public TriangleElement(TriangleElement triangle){
        super(triangle);
        setName("triangle");
        slotPainter = new TrianglePainter(this);
    }

    public static SlotDevice createDefault(Point2D position){
        Point2D pos = (Point2D) position.clone();
        Paint paint = new Color(255,255,255);//boja unutar shapa

        //kreiranje circle elementa
        TriangleElement triangleElement = new TriangleElement(pos, new Dimension(70,70), new BasicStroke(2f), paint, 0);
        return triangleElement;
    }

    @Override
    public SlotElement clone() {
        return new TriangleElement(this);
    }
}
