package gerudok.commands;

import gerudok.model.Page;
import gerudok.model.PageSelectionModel;
import gerudok.model.elements.*;

import java.awt.geom.Point2D;

//implementira metode doCommand i undoCommand
public class AddDeviceCommand extends AbstractCommand {

    Page pageModel;
    Point2D lastPosition;
    SlotDevice slot = null;
    SlotEnum slotType;
    PageSelectionModel selectionModel;

    public AddDeviceCommand(Page page, PageSelectionModel selectionModel, Point2D lastPosition, SlotEnum slotEnum){
        this.pageModel = page;
        this.selectionModel = selectionModel;
        this.lastPosition = lastPosition;
        this.slotType = slotEnum;
    }

    @Override
    public void doCommand() {
        if(slot == null){
            if(slotType == SlotEnum.CIRCLE){
                slot = CircleElement.createDefault(lastPosition);
            }else if(slotType == SlotEnum.RECTANGLE){
                slot = RectangleElement.createDefault(lastPosition);
            }else if(slotType == SlotEnum.TRIANGLE){
                slot = TriangleElement.createDefault(lastPosition);
            }
        }
        pageModel.addSlotElement(slot);
    }

    @Override
    public void undoCommand() {
        selectionModel.removeAllFromSelectionList();
        pageModel.removeSlotElement(slot);
    }
}
