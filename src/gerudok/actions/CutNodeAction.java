package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.model.Document;
import gerudok.model.DocumentSelection;
import gerudok.model.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CutNodeAction extends MyAbstractAction {

    public CutNodeAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("cut-icon.png"));
        putValue(NAME, "Cut");
        putValue(SHORT_DESCRIPTION, "Cut");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent() instanceof Document) {
            Document tmp = (Document) MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
            DocumentSelection content;
            content = new DocumentSelection(tmp);
            MyJFrame.getInstance().getClipboard().setContents(content, MyJFrame.getInstance());
            if(MyJFrame.getInstance().getWorkspaceTree().getSelectionPath().
                    getParentPath().getLastPathComponent() instanceof Project){
                ((Project) MyJFrame.getInstance().getWorkspaceTree().getSelectionPath().
                        getParentPath().getLastPathComponent()).removeDocument(tmp);
                MyJFrame.getInstance().getWorkspaceTree().updateUI();
            }
        }else{
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.NO_SELECTED_DOCUMENT);
        }
    }

}
