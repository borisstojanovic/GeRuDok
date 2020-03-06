package gerudok.actions;

import gerudok.app.MyJFrame;

import java.awt.event.ActionEvent;

public class ResizeAction extends MyAbstractAction {

    public ResizeAction(){
        putValue(NAME, "Resize");
        putValue(SMALL_ICON, loadIcon("Resize-icon.png"));
        putValue(SHORT_DESCRIPTION, "Resize");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().
                getSelectedDocumentView().getFocusPage().startResizeState();
    }
}
