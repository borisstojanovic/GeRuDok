package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.model.Page;
import gerudok.model.PageElementsSelection;
import gerudok.view.PageView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CopyAction extends MyAbstractAction {

    public CopyAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("Copy-icon.png"));
        putValue(NAME, "Copy");
        putValue(SHORT_DESCRIPTION, "Copy");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!(MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().getSelectedDocumentView().getFocusPage().getPage().getSelectionModel().getSelectionListSize() == 0)) {
            PageElementsSelection contents;
            contents = new PageElementsSelection(MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().getSelectedDocumentView().getFocusPage().getPage().getSelectionModel().getSelectionList());
            MyJFrame.getInstance().getClipboard().setContents(contents, MyJFrame.getInstance());
        }
    }
}
