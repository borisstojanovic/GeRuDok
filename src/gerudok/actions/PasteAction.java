package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.commands.PasteDeviceCommand;
import gerudok.model.Page;
import gerudok.model.PageElementsSelection;
import gerudok.view.PageView;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class PasteAction extends MyAbstractAction {

    public PasteAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("Paste-icon.png"));
        putValue(NAME, "Paste");
        putValue(SHORT_DESCRIPTION, "Paste");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Transferable content = MyJFrame.getInstance().getClipboard().getContents(MyJFrame.getInstance());
        Point lastPosition = (Point) MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().getSelectedDocumentView().
                getFocusPage().getLastPosition();
        Page page = MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().getSelectedDocumentView().getFocusPage().getPage();

        if (content != null && content.isDataFlavorSupported(PageElementsSelection.elementFlavor)) {
            MyJFrame.getInstance().getCommandManager().addCommand(new PasteDeviceCommand(content, lastPosition, page));
        }
    }
}
