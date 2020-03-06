package gerudok.actions;

import gerudok.app.MyJFrame;

import java.awt.event.ActionEvent;

public class RotateAction extends MyAbstractAction {

    public RotateAction(){
        putValue(NAME, "Rotate");
        putValue(SMALL_ICON, loadIcon("Rotate-Gesture-3-icon.png"));
        putValue(SHORT_DESCRIPTION, "Rotate");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().
                    getSelectedDocumentView().getFocusPage().startRotateState();
        }catch (NullPointerException e){
            //todo errorhandler
        }
    }
}
