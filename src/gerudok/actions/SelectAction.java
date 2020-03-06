package gerudok.actions;

import gerudok.app.MyJFrame;

import java.awt.event.ActionEvent;

public class SelectAction extends MyAbstractAction {

    public SelectAction(){
        putValue(NAME, "Select");
        putValue(SMALL_ICON, loadIcon("One-Finger-icon.png"));
        putValue(SHORT_DESCRIPTION, "Select");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().
                getSelectedDocumentView().getFocusPage().startSelectState();
    }
}
