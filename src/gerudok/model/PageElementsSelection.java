package gerudok.model;

import gerudok.model.elements.SlotDevice;
import gerudok.model.elements.SlotElement;

import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PageElementsSelection implements Transferable, ClipboardOwner {

    static public DataFlavor elementFlavor;

    private DataFlavor[] supportedFlavors = { elementFlavor };
    public List<SlotElement> slotsElements = new ArrayList<SlotElement>();

    public PageElementsSelection(ArrayList<SlotElement> elements) {
        slotsElements = new ArrayList<>(elements);
        try {

            elementFlavor = new DataFlavor(Class.forName("java.util.ArrayList"), "Elements");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {

        if (flavor.equals(elementFlavor))
            return (slotsElements);
        else
            throw new UnsupportedFlavorException(elementFlavor);
    }

    public DataFlavor[] getTransferDataFlavors() {
        return (supportedFlavors);
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return (flavor.equals(elementFlavor));
    }

    public void lostOwnership(Clipboard clipboard, Transferable contents) {

    }
}
