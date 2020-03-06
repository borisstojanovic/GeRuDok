package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.model.elements.ContentTypeEnum;
import gerudok.model.elements.SlotElement;
import gerudok.view.PageView;

import java.awt.event.ActionEvent;


public class EditorImageAction extends MyAbstractAction {

    public EditorImageAction(){
        putValue(NAME, "Add image");
        putValue(SHORT_DESCRIPTION, "Add image");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().getSelectedDocumentView().getFocusPage().getPage().getSelectionModel().getSelectionListSize() != 1){
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.MULTIPLE_SELECTION);
            return;
        }
        SlotElement slotElement = ((PageView)MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().
                getSelectedDocumentView().getFocusPage()).getPage().getSelectionModel().getElementFromSelectionListAt(0);
        slotElement.setContentType(ContentTypeEnum.IMAGE);
    }

}
