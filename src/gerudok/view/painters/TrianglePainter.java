package gerudok.view.painters;

import gerudok.model.elements.SlotElement;
import gerudok.model.elements.TriangleElement;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
///TrianglePainter zaduzen za definisanje Shape objekta koji predstavlja dati element
public class TrianglePainter extends SlotDevicePainter {

    private TriangleElement triangleElement;

    public TrianglePainter(SlotElement slotElement) {
        super(slotElement);
        triangleElement = (TriangleElement) slotElement;
        paintElement();
    }

    @Override
    public void paintElement() {
        shape = new GeneralPath();

        ((GeneralPath)shape).moveTo(triangleElement.getPosition().getX(),
                triangleElement.getPosition().getY());

        ((GeneralPath)shape).moveTo(triangleElement.getPosition().getX(),
                triangleElement.getPosition().getY() + triangleElement.getSize().height);

        ((GeneralPath)shape).lineTo(triangleElement.getPosition().getX() + triangleElement.getSize().width,
                triangleElement.getPosition().getY() + triangleElement.getSize().height);

        ((GeneralPath)shape).lineTo(triangleElement.getPosition().getX() + triangleElement.getSize().width / 2,
                triangleElement.getPosition().getY());


        ((GeneralPath)shape).closePath();


        AffineTransform affineTransform = AffineTransform.getRotateInstance(triangleElement.getAngle(),
                shape.getBounds().getCenterX(), shape.getBounds().getCenterY());
        shape = affineTransform.createTransformedShape(shape);
    }
}
