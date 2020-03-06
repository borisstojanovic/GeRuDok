package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.model.Document;
import gerudok.model.Project;
import gerudok.model.factory.*;
import gerudok.model.workspace.Workspace;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewNodeAction extends MyAbstractAction {

    public NewNodeAction(){
        putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(NAME, "Add");
        putValue(SMALL_ICON, loadIcon("new-file-icon.png"));
        putValue(SHORT_DESCRIPTION, "Add");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object path = MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
        NodeFactory nodeFactory = FactoryProducer.returnFactory(path);
        MutableTreeNode node;

        if (nodeFactory != null) {
            node = nodeFactory.deliverNode();
            if(path instanceof DefaultMutableTreeNode){
                ((DefaultMutableTreeNode)path).add(node);
            }

            SwingUtilities.updateComponentTreeUI(MyJFrame.getInstance().getWorkspaceTree());
            MyJFrame.getInstance().getWorkspaceTree().expandPath(MyJFrame.getInstance().getWorkspaceTree().getSelectionPath());
        }
    }

}
