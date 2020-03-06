package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.model.PageElementsSelection;
import gerudok.model.elements.SlotElement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class CutAction extends MyAbstractAction {

    public CutAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("cut-icon.png"));
        putValue(NAME, "Cut");
        putValue(SHORT_DESCRIPTION, "Cut");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!(MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().getSelectedDocumentView().getFocusPage().getPage().getSelectionModel().getSelectionListSize() == 0)) {
            PageElementsSelection contents;
            ArrayList<SlotElement> selected = MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().
                    getSelectedDocumentView().getFocusPage().getPage().getSelectionModel().getSelectionList();

            contents = new PageElementsSelection(selected);
            MyJFrame.getInstance().getClipboard().setContents(contents, MyJFrame.getInstance());

            MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().getSelectedDocumentView().getFocusPage().
                    getPage().getSlotElements().removeAll(selected);


            MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().getSelectedDocumentView().getFocusPage().
                    getPage().getSelectionModel().getSelectionList().removeAll(selected);
            MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().
                    getSelectedDocumentView().getFocusPage().repaint();

        }
    }
}
