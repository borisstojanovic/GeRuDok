package gerudok.model;

import java.awt.datatransfer.*;
import java.io.IOException;

public class DocumentSelection implements Transferable, ClipboardOwner {

    public static DataFlavor dataFlavor;


    private DataFlavor[] supportedFlavors = { dataFlavor };
    public Document document;

    public DocumentSelection(Document document) {
        this.document = document;
        dataFlavor = new DataFlavor(Document.class, "Documents");
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {

        if (flavor.equals(dataFlavor))
            return (document);
        else
            throw new UnsupportedFlavorException(dataFlavor);
    }

    public DataFlavor[] getTransferDataFlavors() {
        return (supportedFlavors);
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return (flavor.equals(dataFlavor));
    }

    public void lostOwnership(Clipboard clipboard, Transferable contents) {

    }
}
