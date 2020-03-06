package gerudok.commands;

import gerudok.model.Page;
import gerudok.model.PageElementsSelection;
import gerudok.model.elements.*;
import gerudok.view.painters.CirclePainter;
import gerudok.view.painters.RectanglePainter;
import gerudok.view.painters.TrianglePainter;

import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PasteDeviceCommand extends AbstractCommand {

    Transferable content;
    Point position;
    Page page;
    ArrayList<SlotElement> slotElements;

    public PasteDeviceCommand(Transferable content, Point position, Page page){
        this.content = content;
        this.position = position;
        this.page = page;
        slotElements = new ArrayList<>();
    }

    @Override
    public void doCommand() {
        try {
            SlotDevice device = null;
            ArrayList<SlotElement> tempElements = (ArrayList<SlotElement>) content.getTransferData (PageElementsSelection.elementFlavor);
            for(int i=0 ; i < tempElements.size(); i++){
                if(tempElements.get(i) instanceof SlotDevice){
                    device=(SlotDevice) tempElements.get(i).clone();
                    Point2D newLocation = (Point2D) device.getPosition().clone();
                    newLocation.setLocation(position.getX() + device.getSize().width * i,
                                position.getY() + device.getSize().height * i);
                    device.setPosition(newLocation);

                    if (device instanceof CircleElement){
                        device.setSlotPainter(new CirclePainter(device));
                    }else if (device instanceof RectangleElement){
                        device.setSlotPainter(new RectanglePainter(device));
                    }else if(device instanceof TriangleElement){
                        device.setSlotPainter(new TrianglePainter(device));
                    }
                    slotElements.add(device);
                    page.addSlotElement(device);
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace ();
        }
    }

    @Override
    public void undoCommand() {
        try {
            for(int i = 0; i < slotElements.size(); i++){
                page.removeSlotElement(slotElements.get(i));
            }
        }catch (Exception e){

        }
    }
}
