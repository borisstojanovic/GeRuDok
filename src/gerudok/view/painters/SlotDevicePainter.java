package gerudok.view.painters;

import gerudok.model.elements.SlotDevice;
import gerudok.model.elements.SlotElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class SlotDevicePainter extends SlotElementPainter {

    public SlotDevicePainter(SlotElement slotElement) {
        super(slotElement);
    }

    @Override
    //sluzi za iscrtavanje elemenata u Graphics2D klasi
    public void paint(Graphics2D g, SlotElement slotElement) {
        SlotDevice slotDevice = (SlotDevice) slotElement;
        g.setPaint(Color.BLACK);
        g.setStroke(slotElement.getStroke());
        g.draw(getShape());
        g.setPaint(slotElement.getPaint());
        g.fill(getShape());
        slotDevice.getSlotPainter().paintElement();
    }

    @Override
    //proverava da li se element nalazi na zadatoj poziciji
    public boolean isElementAt(Point point) {
        return getShape().contains(point);
    }
}
