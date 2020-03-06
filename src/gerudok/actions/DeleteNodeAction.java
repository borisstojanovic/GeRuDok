package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.model.Document;
import gerudok.model.Page;
import gerudok.model.Project;
import gerudok.model.workspace.DocumentContainer;
import gerudok.model.workspace.Workspace;
import gerudok.view.DocumentView;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DeleteNodeAction extends MyAbstractAction {

    public DeleteNodeAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        putValue(NAME, "Delete");
        putValue(SMALL_ICON, loadIcon("Actions-document-close-icon.png"));
        putValue(SHORT_DESCRIPTION, "Delete");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object object = MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
        TreePath path = MyJFrame.getInstance().getWorkspaceTree().getSelectionPath();

        //proverava da li je nesto selektovano
        //ako nije generise gresku
        if (path == null) {
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.NO_SELECTED_DELETE);
            return;
        }

        //workspace ne moze da se obrise
        if (object instanceof Workspace || object instanceof DocumentContainer) {
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.WORKSPACE_DELETE);
            return;
        }

        if (!(object instanceof Workspace) && object instanceof TreeNode){
            //vraca 1 ako je izabrao da ili 0 za no i cancel
            int answ = nodeDeleteQuestion(object.toString());

            if (answ == 1) {
                if (object instanceof Project) {
                    Workspace workspace = (Workspace) ((Project) object).getParent();
                    workspace.removeProject((Project) object);

                } else if (object instanceof Document) {
                    Object parent = path.getParentPath().getLastPathComponent();
                    if(parent instanceof Project) {
                        Project project = (Project) parent;
                        project.removeDocument((Document) object);
                    }else if(parent instanceof DocumentContainer){
                        DocumentContainer documentContainer = (DocumentContainer) parent;
                        documentContainer.removeDocument((Document) object);
                    }

                } else if (object instanceof Page) {
                    Document document = (Document) ((Page) object).getParent();
                    document.removePage((Page) object);
                }
                //izbacuje izbrisani node sa path-a
                MyJFrame.getInstance().getWorkspaceTree().setSelectionPath(null);
            }
            MyJFrame.getInstance().getWorkspaceTree().updateUI();
            SwingUtilities.updateComponentTreeUI(MyJFrame.getInstance().getWorkspaceTree());
        }
    }

    public int nodeDeleteQuestion(String message){
        int answ = JOptionPane.showConfirmDialog(null ,
                "Are you sure you want to delete " + message +"?");
        if(answ == JOptionPane.YES_OPTION)
            return 1;
        else
            return 0;
    }
}
