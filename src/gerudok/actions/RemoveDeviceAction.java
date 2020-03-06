package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.commands.RemoveDeviceCommand;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.model.Page;
import gerudok.model.elements.SlotElement;
import gerudok.view.PageView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class RemoveDeviceAction extends MyAbstractAction{

    public RemoveDeviceAction() {
        putValue(SMALL_ICON, loadIcon("Programming-Delete-Property-icon.png"));
        putValue(NAME, "Remove");
        putValue(SHORT_DESCRIPTION, "Remove");
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        try {
            Page page = MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().getSelectedDocumentView().
                    getFocusPage().getPage();
            MyJFrame.getInstance().getCommandManager().
                    addCommand(new RemoveDeviceCommand(page, page.getSelectionModel().getSelectionList()));
        }catch (NullPointerException e){
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.NO_SELECTED_PAGE);
        }
    }

}
