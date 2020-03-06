package gerudok.view.painters;

import gerudok.model.elements.CircleElement;
import gerudok.model.elements.SlotElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
///CirclePainter zaduzen za definisanje Shape objekta koji predstavlja dati element
public class CirclePainter extends SlotDevicePainter {

    private CircleElement circleElement;

    public CirclePainter(SlotElement slotElement){
        super(slotElement);
        circleElement = (CircleElement) slotElement;
        paintElement();
    }

    public void paintElement() {
        shape = new GeneralPath();
        //Point tmp = new Point((Point) circleElement.getPosition());
        //rotatePosition();

        ((GeneralPath) shape).moveTo(circleElement.getPosition().getX() + circleElement.getSize().getWidth() / 2,
                circleElement.getPosition().getY());

        ((GeneralPath) shape).quadTo(circleElement.getPosition().getX() + circleElement.getSize().getWidth(),
                circleElement.getPosition().getY(), circleElement.getPosition().getX() + circleElement.getSize().getWidth(),
                circleElement.getPosition().getY() + circleElement.getSize().getHeight() / 2);

        ((GeneralPath) shape).quadTo(circleElement.getPosition().getX() + circleElement.getSize().getWidth(),
                circleElement.getPosition().getY() + circleElement.getSize().getHeight(),
                circleElement.getPosition().getX() + circleElement.getSize().getWidth() / 2,
                circleElement.getPosition().getY() + circleElement.getSize().getHeight());

        ((GeneralPath) shape).quadTo(circleElement.getPosition().getX(), circleElement.getPosition().getY()+circleElement.
                getSize().getHeight(), circleElement.getPosition().getX(), circleElement.
                getPosition().getY() + circleElement.getSize().getHeight() / 2);

        ((GeneralPath) shape).quadTo(circleElement.getPosition().getX(), circleElement.getPosition().getY(),
                circleElement.getPosition().getX() + circleElement.getSize().getWidth() / 2, circleElement.getPosition().getY());

        //ako je rotiran prvo se crta kao da je pod pravim uglom pa tek onda se vrsi rotacija
        AffineTransform affineTransform = AffineTransform.getRotateInstance(circleElement.getAngle(),
                shape.getBounds().getCenterX(), shape.getBounds().getCenterY());
        shape = affineTransform.createTransformedShape(shape);
    }

}
