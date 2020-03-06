package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.gui.WorkspaceTree;
import gerudok.model.workspace.Workspace;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class RenameNodeAction extends MyAbstractAction implements ActionListener {

    public RenameNodeAction() {
        super();
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        putValue(NAME, "Rename");
        putValue(SMALL_ICON, loadIcon("Actions-document-edit-icon.png"));
        putValue(SHORT_DESCRIPTION, "Rename");
    }

    public void actionPerformed(ActionEvent e) {
        WorkspaceTree tree = MyJFrame.getInstance().getWorkspaceTree();
        TreePath path = tree.getSelectionPath();

        tree.startEditingAtPath(path);

    }
}


