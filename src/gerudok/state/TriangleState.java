package gerudok.state;

import gerudok.app.MyJFrame;
import gerudok.commands.AddDeviceCommand;
import gerudok.commands.SlotEnum;
import gerudok.model.Page;
import gerudok.model.elements.CircleElement;
import gerudok.model.elements.SlotDevice;
import gerudok.model.elements.TriangleElement;
import gerudok.view.PageView;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TriangleState extends State {

    private PageView mediator;

    public TriangleState(PageView pageView){
        mediator = pageView;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point position = e.getPoint();

        if (e.getButton()==MouseEvent.BUTTON1){
            if (mediator.getPage().getElementAtPosition(position) == -1){
                MyJFrame.getInstance().getCommandManager().addCommand(new AddDeviceCommand(mediator.getPage(), mediator.getPage().getSelectionModel(), position, SlotEnum.TRIANGLE));
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
