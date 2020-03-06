package gerudok.actions;

import gerudok.app.MyJFrame;

import java.awt.event.ActionEvent;

public class MoveAction extends MyAbstractAction {

    public MoveAction(){
        putValue(NAME, "Move");
        putValue(SMALL_ICON, loadIcon("Cursor-Move-2-icon.png"));
        putValue(SHORT_DESCRIPTION, "Move");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().
                getSelectedDocumentView().getFocusPage().startMoveState();
    }
}
