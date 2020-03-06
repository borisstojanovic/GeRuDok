package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.model.Document;
import gerudok.model.DocumentSelection;
import gerudok.model.PageElementsSelection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CopyNodeAction extends MyAbstractAction {

    public CopyNodeAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("Copy-icon.png"));
        putValue(NAME, "Copy");
        putValue(SHORT_DESCRIPTION, "Copy");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent() instanceof Document) {
            DocumentSelection content;
            content = new DocumentSelection((Document) MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent());
            MyJFrame.getInstance().getClipboard().setContents(content, MyJFrame.getInstance());
        }else{
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.NO_SELECTED_DOCUMENT);
        }
    }
}
