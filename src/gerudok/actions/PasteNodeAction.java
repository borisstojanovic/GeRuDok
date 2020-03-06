package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.model.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class PasteNodeAction extends MyAbstractAction {

    public PasteNodeAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("Paste-icon.png"));
        putValue(NAME, "Paste");
        putValue(SHORT_DESCRIPTION, "Paste");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent() instanceof Project){
            ((Project)MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent()).paste();
            MyJFrame.getInstance().getWorkspaceTree().updateUI();
        }
    }
}
