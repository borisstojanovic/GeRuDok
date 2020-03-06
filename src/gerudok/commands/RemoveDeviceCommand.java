package gerudok.commands;

import gerudok.model.Page;
import gerudok.model.PageSelectionModel;
import gerudok.model.elements.SlotElement;

import java.util.ArrayList;
import java.util.List;

public class RemoveDeviceCommand extends AbstractCommand {

    private Page page;
    private List<SlotElement> selected;

    public RemoveDeviceCommand(Page page, List<SlotElement> selected){
        this.page = page;
        this.selected = new ArrayList<>(selected);
    }


    @Override
    public void doCommand() {
        for (SlotElement slotElement : selected) {
            page.getSelectionModel().removeFromSelectionList(slotElement);
            page.removeSlotElement(slotElement);
        }
    }

    @Override
    public void undoCommand() {
        for (SlotElement slotElement : selected) {
            page.addSlotElement(slotElement);
        }
    }
}
