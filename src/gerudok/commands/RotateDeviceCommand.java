package gerudok.commands;

import gerudok.model.Page;
import gerudok.model.elements.SlotDevice;
import gerudok.model.elements.SlotElement;

import java.util.ArrayList;
import java.util.List;

public class RotateDeviceCommand extends AbstractCommand {

    private Page page;
    private double startAngle;
    private double endAngle;
    private List<SlotElement> slotElements;
    private boolean firstChange = true;

    public RotateDeviceCommand(Page page, double startAngle, double endAngle){
        this.page = page;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        slotElements = new ArrayList<>(page.getSelectionModel().getSelectionList());
    }


    @Override
    public void doCommand() {

        if(!firstChange){
            page.getSelectionModel().removeAllFromSelectionList();
        }

        for(int i = 0; i < slotElements.size(); i++){
            SlotDevice slotDevice = (SlotDevice)slotElements.get(i);
            page.removeSlotElement(slotDevice);
            if(!firstChange){
                slotDevice.setAngle(slotDevice.getAngle() + endAngle - startAngle);
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
            SlotDevice slotDevice = (SlotDevice)slotElements.get(i);
            page.removeSlotElement(slotDevice);
            slotDevice.setAngle(slotDevice.getAngle() - endAngle + startAngle);
            page.addSlotElement(slotDevice);
            slotDevice.getSlotPainter().paintElement();
        }
    }
}
