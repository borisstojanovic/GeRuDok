package gerudok.view.painters;

import gerudok.model.elements.RectangleElement;
import gerudok.model.elements.SlotDevice;
import gerudok.model.elements.SlotElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
///RecatnglePainter zaduzen za definisanje Shape objekta koji predstavlja dati element
public class RectanglePainter extends SlotDevicePainter {

    private RectangleElement rectangleElement;

    public RectanglePainter(SlotElement slotElement) {
        super(slotElement);
        rectangleElement = (RectangleElement) slotElement;
        paintElement();
    }

    @Override
    public void paintElement() {
        shape = new GeneralPath();

        ((GeneralPath) shape).moveTo(rectangleElement.getPosition().getX(), rectangleElement.getPosition().getY());

        ((GeneralPath) shape).lineTo(rectangleElement.getPosition().getX() + rectangleElement.getSize().width,
                rectangleElement.getPosition().getY());

        ((GeneralPath) shape).lineTo(rectangleElement.getPosition().getX() + rectangleElement.getSize().width,
                rectangleElement.getPosition().getY() + rectangleElement.getSize().height);

        ((GeneralPath) shape).lineTo(rectangleElement.getPosition().getX(),
                rectangleElement.getPosition().getY() + rectangleElement.getSize().height);

        ((GeneralPath) shape).closePath();

        AffineTransform affineTransform = AffineTransform.getRotateInstance(rectangleElement.getAngle(), rectangleElement.getPosition().getX() + rectangleElement.getSize().width/2,
                rectangleElement.getPosition().getY() + rectangleElement.getSize().height/2);
        shape = affineTransform.createTransformedShape(shape);


    }
}
