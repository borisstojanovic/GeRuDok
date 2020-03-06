package gerudok.view;

import gerudok.actions.MyAbstractAction;
import gerudok.actions.RenameNodeAction;
import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.model.Document;
import gerudok.model.Page;
import gerudok.model.Project;
import gerudok.model.workspace.DocumentContainer;
import gerudok.model.workspace.Workspace;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;
import java.util.List;

public class WorkspaceTreeEditor extends DefaultTreeCellEditor implements ActionListener {

    private Object object = null;
    private JTextField edit = null;

    public WorkspaceTreeEditor(JTree tree, DefaultTreeCellRenderer renderer) {
        super(tree, renderer);
    }

    public Component getTreeCellEditorComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean arg4, int arg5) {

        object = arg1;

        edit = new JTextField(arg1.toString());
        edit.addActionListener(this);
        return edit;
    }

    @Override
    public boolean isCellEditable(EventObject e) {

        if (e instanceof MouseEvent) {
            if (((MouseEvent) e).getClickCount() == 3) {
                if(MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent() instanceof Workspace ||
                MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent() instanceof DocumentContainer) {
                    //ako je workspace ne sme da se menja ime
                    ErrorHandlerSimpleFactory.generateError(ExceptionEnum.WORKSPACE_RENAME);
                    return false;
                }
                //ako nije workspace a bila su 3 klika dozvoljavamo editovanje
                return true;
            }
            //ako je bio bilo koji drugi mouse event ne dozvoljava editovanje
            return false;
        }

        //ako se desio neki drugi event dok je selektovan workspace
        //ne dozvoljavam editovanje
        if(MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent() instanceof Workspace){
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.WORKSPACE_RENAME);
            return false;
        }
        //ako je bio neki drugi event koji je trazio zovemo default metodu za taj event
        return super.isCellEditable(e);
    }

   public void actionPerformed(ActionEvent e){
       String newName = e.getActionCommand();
       Object object = MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();

       if(newName == null || newName.isEmpty() || newName.isBlank()){
           ErrorHandlerSimpleFactory.generateError(ExceptionEnum.NODE_RENAME_EMPTY);
           return;
       }

       if (object instanceof Project) {
           if(((Project) object).checkIfNameExists(newName)){
               ErrorHandlerSimpleFactory.generateError(ExceptionEnum.NODE_SAME_NAME);
               return;
           }
           ((Project) object).renameProject(newName);
       } else if (object instanceof Document) {
           if(((Document) object).checkIfNameExists(newName)){
               ErrorHandlerSimpleFactory.generateError(ExceptionEnum.NODE_SAME_NAME);
               return;
           }
           ((Document) object).renameDocument(newName);
       } else if (object instanceof Page) {
           if(((Page) object).checkIfNameExists(newName)){
               ErrorHandlerSimpleFactory.generateError(ExceptionEnum.NODE_SAME_NAME);
               return;
           }
           ((Page) object).renamePage(newName);
       }

       //zaustavlja editovanje nakon sto je pritisnut enter
       stopCellEditing();
    }

}
