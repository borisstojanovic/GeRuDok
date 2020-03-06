package gerudok.commands;


import gerudok.app.MyJFrame;
import gerudok.model.Page;
import gerudok.model.PageSelectionModel;
import gerudok.model.elements.SlotDevice;
import gerudok.model.elements.SlotElement;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ResizeDeviceCommand extends AbstractCommand {

    private ArrayList<SlotElement> slotElements;
    private Page page;
    private SlotDevice selected;
    private Dimension startSize;
    private Dimension endSize;
    private boolean firstChange = true;

    public ResizeDeviceCommand(Page page,
                               SlotDevice selected, Dimension startSize){
        this.page = page;
        this.selected = selected;
        this.startSize = startSize;
        this.endSize = selected.getSize();
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
            if(slotDevice != selected){
                if(firstChange){
                    slotDevice.setSize(new Dimension(slotDevice.getSize().width, slotDevice.getSize().height));
                }else {
                    slotDevice.setSize(new Dimension(slotDevice.getSize().width + endSize.width - startSize.width,
                            slotDevice.getSize().height + endSize.height - startSize.height));
                }
            }else{
                selected.setSize(endSize);
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
            if(slotDevice != selected){
                slotDevice.setSize(new Dimension(slotDevice.getSize().width - endSize.width + startSize.width,
                        slotDevice.getSize().height - endSize.height + startSize.height));
            }else{
                selected.setSize(startSize);
            }
            page.addSlotElement(slotDevice);
            slotDevice.getSlotPainter().paintElement();
        }
    }
}
