package gerudok.state;

import gerudok.app.MyJFrame;
import gerudok.commands.ResizeDeviceCommand;
import gerudok.model.Page;
import gerudok.model.elements.SlotDevice;
import gerudok.model.elements.SlotElement;
import gerudok.view.PageView;
import gerudok.view.painters.handle.Handle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class ResizeState extends State {

    private PageView mediator;
    private Handle handle = null;
    private SlotElement selected = null;
    //koristi se prilikom mouse dragged da ne moze da se promeni handle u toku resize-a
    private Point2D firstLocation = null;

    //cuva se zbog pravljenja komande na mouse released
    private Dimension startSize = null;

    public ResizeState(PageView pageView){
        mediator = pageView;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        handle = mediator.getDeviceAndHandleForPoint(e.getPoint());
        if(handle == Handle.ROTATE){
            mediator.startRotateState();
            return;
        }
        selected = mediator.getElementForHandleAndPoint(handle, e.getPoint());
        startSize = ((SlotDevice)selected).getSize();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(firstLocation == null){
            firstLocation = mediator.getLastPosition();
        }
        if(handle == null || selected == null){
            handle = mediator.getDeviceAndHandleForPoint(firstLocation);
            selected = mediator.getElementForHandleAndPoint(handle, (Point) firstLocation);
            startSize = ((SlotDevice)selected).getSize();
        }

        Page page = mediator.getPage();

        if(page.getSelectionModel().getSelectionList().isEmpty()){
            return;
        }

        SlotDevice tmp = (SlotDevice) selected;

        Dimension currSize = tmp.getSize();
        Point point = e.getPoint();

        Point position = (Point) tmp.getPosition();
        Point center = new Point((int)(position.getX() + currSize.getWidth()/2.0),
                (int)(position.getY() + currSize.getHeight()/2.0));

        rotatePoint(point, center, Math.toRadians(-Math.toDegrees(tmp.getAngle())));


        switch (handle){
            case SOUTH_WEST:
                tmp.setSize(new Dimension(currSize.width + position.x - point.x,
                        point.y - position.y));
                tmp.setPosition(new Point(point.x, position.y));
                break;
            case SOUTH:
                tmp.setSize(new Dimension(currSize.width, point.y - position.y));
                tmp.setPosition(position);
                break;
            case SOUTH_EAST:
                tmp.setSize(new Dimension(point.x - position.x, point.y - position.y));
                tmp.setPosition(position);
                break;
            case EAST:
                tmp.setSize(new Dimension(point.x - position.x, currSize.height));
                tmp.setPosition(position);
                break;
            case NORTH_EAST:
                tmp.setSize(new Dimension(point.x - position.x, currSize.height + position.y - point.y));
                tmp.setPosition(new Point(position.x, point.y));
                break;
            case NORTH:
                tmp.setSize(new Dimension(currSize.width, currSize.height + position.y - point.y));
                tmp.setPosition(new Point(position.x, point.y));
                break;
            case NORTH_WEST:
                tmp.setSize(new Dimension(currSize.width + position.x - point.x,
                        currSize.height + position.y - point.y));
                tmp.setPosition(point);
                break;
            case WEST:
                tmp.setSize(new Dimension(currSize.width + position.x - point.x, currSize.height));
                tmp.setPosition(new Point(point.x, position.y));
                break;
        }

        for(int i = 0; i < mediator.getPage().getSelectionModel().getSelectionListSize(); i++){
            SlotElement slotElement = page.getSelectionModel().getElementFromSelectionListAt(i);
            if(slotElement instanceof SlotDevice){
                SlotDevice slotDevice = (SlotDevice) slotElement;
                if(slotDevice != selected) {
                    slotDevice.setSize(new Dimension(slotDevice.getSize().width + tmp.getSize().width - currSize.width,
                            slotDevice.getSize().height + tmp.getSize().height - currSize.height));
                }
            }
        }

        mediator.repaint();

    }


    private void rotatePoint(Point point, Point pivot, double angle) {

        double s = Math.sin(angle);
        double c = Math.cos(angle);

        point.setLocation(point.getX() - pivot.getX(), point.getY() - pivot.getY());

        double newx = point.getX() * c - point.getY() * s;
        double newy = point.getX() * s + point.getY() * c;

        point.setLocation(newx + pivot.getX(), newy + pivot.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if(selected != null) {
            MyJFrame.getInstance().getCommandManager().addCommand(new ResizeDeviceCommand(mediator.getPage(),
                    (SlotDevice) selected, startSize));
        }
        selected = null;
        firstLocation = null;
        startSize = null;
        handle = null;
        mediator.startSelectState();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point2D point = e.getPoint();
        mediator.setMouseCursor(point);
    }
}
