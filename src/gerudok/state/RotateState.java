package gerudok.state;

import gerudok.app.MyJFrame;
import gerudok.commands.RotateDeviceCommand;
import gerudok.model.Page;
import gerudok.model.elements.SlotDevice;
import gerudok.model.elements.SlotElement;
import gerudok.view.PageView;
import gerudok.view.painters.handle.Handle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Iterator;

public class RotateState extends State {

    private PageView mediator;
    private Handle handle = null;
    private SlotElement selected = null;
    private double startAngle = 0;

    public RotateState(PageView mediator){
        this.mediator = mediator;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if(mediator.getPage().getSelectionModel().getSelectionList().isEmpty()){
            return;
       }

        if(handle == null || selected == null) {
            handle = mediator.getDeviceAndHandleForPoint(mediator.getLastPosition());
            if(handle == Handle.ROTATE) {
                selected = mediator.getElementForHandleAndPoint(Handle.ROTATE, (Point) mediator.getLastPosition());
            }else if(handle != null){
                mediator.startResizeState();
                return;
            }
            startAngle = ((SlotDevice)selected).getAngle();
            return;
        }

        double angle = Math.atan2(selected.getSlotPainter().getShape().getBounds().getCenterY() - e.getY(),
                selected.getSlotPainter().getShape().getBounds().getCenterX() - e.getX()) - Math.PI / 2;

        if(selected instanceof SlotDevice) {
            for (int i = 0; i < mediator.getPage().getSelectionModel().getSelectionListSize(); i++) {

                if (mediator.getPage().getSelectionModel().getElementFromSelectionListAt(i) != selected &&
                        mediator.getPage().getSelectionModel().getElementFromSelectionListAt(i) instanceof SlotDevice) {

                    SlotDevice temp = (SlotDevice) mediator.getPage().getSelectionModel().getElementFromSelectionListAt(i);
                    temp.setAngle(temp.getAngle() + angle - ((SlotDevice)selected).getAngle());
                }
            }
            ((SlotDevice) selected).setAngle(angle);
        }
        mediator.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {


        if(selected != null){
            double endAngle = ((SlotDevice)selected).getAngle();
            MyJFrame.getInstance().getCommandManager().addCommand(new RotateDeviceCommand(mediator.getPage(), startAngle, endAngle));
        }
        startAngle = 0;
        selected = null;
        handle = null;
        mediator.repaint();
        mediator.startSelectState();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point2D point = e.getPoint();
        mediator.setMouseCursor(point);
    }
}
