package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.model.Page;
import gerudok.view.PageView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RedoAction extends MyAbstractAction {

    public RedoAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        putValue(NAME, "Redo");
        putValue(SMALL_ICON, loadIcon("Redo-icon.png"));
        putValue(SHORT_DESCRIPTION, "Redo");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            MyJFrame.getInstance().getCommandManager().doCommand();
        }catch (NullPointerException e1){
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.NO_SELECTED_PAGE);
        }
    }
}
