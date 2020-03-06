package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.view.DocumentView;
import gerudok.view.PageView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RectangleAction extends MyAbstractAction{

    public RectangleAction(){
        putValue(NAME, "Rectangle");
        putValue(SMALL_ICON, loadIcon("Editing-Rectangle-Stroked-icon.png"));
        putValue(SHORT_DESCRIPTION, "Rectangle");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().
                    getSelectedDocumentView().getFocusPage().startRectangleState();
        }catch (NullPointerException e1){
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.NO_SELECTED_PAGE);
        }
    }
}
