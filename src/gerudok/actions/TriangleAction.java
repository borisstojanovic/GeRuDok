package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.view.DocumentView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class TriangleAction extends MyAbstractAction {

    public TriangleAction(){
        putValue(NAME, "Triangle");
        putValue(SMALL_ICON, loadIcon("Editing-Triangle-Stroked-icon.png"));
        putValue(SHORT_DESCRIPTION, "Triangle");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().
                    getSelectedDocumentView().getFocusPage().startTriangleState();
        }catch (NullPointerException e1){
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.NO_SELECTED_PAGE);
        }
    }
}
