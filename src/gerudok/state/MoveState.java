package gerudok.state;

import gerudok.app.MyJFrame;
import gerudok.commands.MoveDeviceCommand;
import gerudok.model.elements.SlotDevice;
import gerudok.model.elements.SlotElement;
import gerudok.view.PageView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class MoveState extends State {

    private PageView mediator;
    private Point2D startingPoint;
    private Point2D startPoint;

    public MoveState(PageView pageView){
        this.mediator = pageView;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int n = mediator.getPage().getSelectionModel().getSelectionListSize();
        for(int i = 0; i < n; i++){
            SlotElement slotElement = mediator.getPage().getSelectionModel().getElementFromSelectionListAt(i);
            if(slotElement.getSlotPainter().getShape().contains(e.getPoint())){
                startingPoint = e.getPoint();
                startPoint = e.getPoint();
                return;
            }
        }

        if(startingPoint == null){
            mediator.startSelectState();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if(startingPoint == null){
            int n = mediator.getPage().getSelectionModel().getSelectionListSize();
            for(int i = 0; i < n; i++){
                SlotElement slotElement = mediator.getPage().getSelectionModel().getElementFromSelectionListAt(i);
                if(slotElement.getSlotPainter().getShape().contains(e.getPoint())){
                    startingPoint = e.getPoint();
                    startPoint = e.getPoint();
                    return;
                }
            }
            return;
        }

        Point2D currentPoint = e.getPoint();

        Dimension pointChange = new Dimension((int)(currentPoint.getX() - startingPoint.getX()),
                (int)(currentPoint.getY() - startingPoint.getY()));

        int n = mediator.getPage().getSelectionModel().getSelectionListSize();

        for(int i = 0; i < n; i++){
            SlotElement slotElement = mediator.getPage().getSelectionModel().getElementFromSelectionListAt(i);
            if(slotElement instanceof SlotDevice){
                SlotDevice slotDevice = (SlotDevice)slotElement;
                Point2D position = slotDevice.getPosition();
                Point2D currPosition = new Point((int) position.getX() + pointChange.width, (int) position.getY() + pointChange.height);
                slotDevice.setPosition(currPosition);
            }
        }
        startingPoint = currentPoint;
        mediator.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(startPoint != null){
            MyJFrame.getInstance().getCommandManager().addCommand(new MoveDeviceCommand(mediator.getPage(),
                    (Point) startPoint, e.getPoint()));
        }
        mediator.repaint();
        startingPoint = null;
        mediator.startSelectState();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
