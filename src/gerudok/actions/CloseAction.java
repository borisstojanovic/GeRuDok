package gerudok.actions;

import gerudok.app.MyJFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class CloseAction extends MyAbstractAction{

    public CloseAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        putValue(NAME, "Close tab");
        putValue(SMALL_ICON, loadIcon("Actions-project-development-close-icon.png"));
        putValue(SHORT_DESCRIPTION, "Close tab");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
            MyJFrame.getInstance().getWorkspaceView().closeProjectViewTab();
    }
}
