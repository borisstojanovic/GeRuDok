package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.view.ProjectView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class CloseAllAction extends MyAbstractAction{

    public CloseAllAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        putValue(NAME, "Close all");
        putValue(SMALL_ICON, loadIcon("Actions-project-development-close-all-icon.png"));
        putValue(SHORT_DESCRIPTION, "Closes all tabs");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
            MyJFrame.getInstance().getWorkspaceView().closeAllProjectViewTabs();
    }
}
