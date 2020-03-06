package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.view.DocumentView;

import java.awt.event.ActionEvent;

public class CircleAction extends MyAbstractAction {

    public CircleAction(){
        putValue(NAME, "Circle");
        putValue(SMALL_ICON, loadIcon("circle-outline.png"));
        putValue(SHORT_DESCRIPTION, "Circle");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().
                    getSelectedDocumentView().getFocusPage().startCircleState();
        }catch (NullPointerException e1){
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.NO_SELECTED_PAGE);
        }
    }
}
