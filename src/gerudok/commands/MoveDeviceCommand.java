package gerudok.commands;

import gerudok.app.MyJFrame;
import gerudok.model.Page;
import gerudok.model.elements.SlotDevice;
import gerudok.model.elements.SlotElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MoveDeviceCommand extends AbstractCommand {

    private Page page;
    private List<SlotElement> slotElements;
    private Point startPosition;
    private Point endPosition;
    private boolean firstChange = true;

    public MoveDeviceCommand(Page page, Point startPosition, Point endPosition) {
        this.page = page;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.slotElements = new ArrayList<>(page.getSelectionModel().getSelectionList());
    }


    @Override
    public void doCommand() {
        if(!firstChange){
            page.getSelectionModel().removeAllFromSelectionList();
        }
        for(int i = 0; i < slotElements.size(); i++){
            SlotDevice slotDevice = (SlotDevice) slotElements.get(i);
            page.removeSlotElement(slotDevice);
            if(!firstChange) {
                slotDevice.setPosition(new Point((int)(slotDevice.getPosition().getX() + endPosition.getX() - startPosition.getX()),
                        (int)(slotDevice.getPosition().getY() + endPosition.getY() - startPosition.getY())));
            }
            page.addSlotElement(slotDevice);
            slotDevice.getSlotPainter().paintElement();
        }
        firstChange = false;
    }

    @Override
    public void undoCommand() {
        for(int i = 0; i < slotElements.size(); i++){
            page.getSelectionModel().removeAllFromSelectionList();
            SlotDevice slotDevice = (SlotDevice) slotElements.get(i);
            page.removeSlotElement(slotDevice);

            slotDevice.setPosition(new Point((int)(slotDevice.getPosition().getX() - endPosition.getX() + startPosition.getX()),
                    (int)(slotDevice.getPosition().getY() - endPosition.getY() + startPosition.getY())));

            page.addSlotElement(slotDevice);
            slotDevice.getSlotPainter().paintElement();
        }
    }
}
