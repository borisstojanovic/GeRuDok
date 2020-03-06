package gerudok.controller;

import gerudok.app.MyJFrame;
import gerudok.model.Document;
import gerudok.model.Page;
import gerudok.model.Project;
import gerudok.model.workspace.DocumentContainer;
import gerudok.model.workspace.Workspace;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WorkspaceTreeMouseController implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        //ako se desio jedan klik uzima koordinate i postavlja ih kao novi path
        //radi i za levi i za desni klik
        if(mouseEvent.getClickCount() == 1){
            TreePath selPath = MyJFrame.getInstance().getWorkspaceTree().
                    getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
            MyJFrame.getInstance().getWorkspaceTree().setSelectionPath(selPath);

            Object object = MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
            if(object instanceof Workspace){
                MyJFrame.getInstance().getActionManager().getNewNodeAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getRenameNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getDeleteNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getCopyNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getCutNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getRestoreNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getPasteNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getSaveProjectAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getSaveProjectAsAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getOpenProjectAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getShareNodeAction().setEnabled(false);

            }else if(object instanceof DocumentContainer){
                MyJFrame.getInstance().getActionManager().getNewNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getRenameNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getDeleteNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getCopyNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getCutNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getRestoreNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getPasteNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getSaveProjectAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getSaveProjectAsAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getOpenProjectAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getShareNodeAction().setEnabled(false);

            }else if(object instanceof Project) {
                MyJFrame.getInstance().getActionManager().getNewNodeAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getRenameNodeAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getDeleteNodeAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getCopyNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getCutNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getRestoreNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getPasteNodeAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getSaveProjectAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getSaveProjectAsAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getOpenProjectAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getShareNodeAction().setEnabled(false);

            }else if(object instanceof Document){
                Object parent = MyJFrame.getInstance().getWorkspaceTree().getSelectionPath().getParentPath().getLastPathComponent();
                if (parent instanceof DocumentContainer) {
                    MyJFrame.getInstance().getActionManager().getNewNodeAction().setEnabled(false);
                    MyJFrame.getInstance().getActionManager().getRenameNodeAction().setEnabled(true);
                    MyJFrame.getInstance().getActionManager().getDeleteNodeAction().setEnabled(true);
                    MyJFrame.getInstance().getActionManager().getCopyNodeAction().setEnabled(false);
                    MyJFrame.getInstance().getActionManager().getCutNodeAction().setEnabled(false);
                    MyJFrame.getInstance().getActionManager().getRestoreNodeAction().setEnabled(true);
                    MyJFrame.getInstance().getActionManager().getPasteNodeAction().setEnabled(false);
                    MyJFrame.getInstance().getActionManager().getSaveProjectAction().setEnabled(false);
                    MyJFrame.getInstance().getActionManager().getSaveProjectAsAction().setEnabled(false);
                    MyJFrame.getInstance().getActionManager().getOpenProjectAction().setEnabled(true);
                    MyJFrame.getInstance().getActionManager().getShareNodeAction().setEnabled(false);
                } else {
                    MyJFrame.getInstance().getActionManager().getNewNodeAction().setEnabled(true);
                    MyJFrame.getInstance().getActionManager().getRenameNodeAction().setEnabled(true);
                    MyJFrame.getInstance().getActionManager().getDeleteNodeAction().setEnabled(true);
                    MyJFrame.getInstance().getActionManager().getCopyNodeAction().setEnabled(true);
                    MyJFrame.getInstance().getActionManager().getCutNodeAction().setEnabled(true);
                    MyJFrame.getInstance().getActionManager().getRestoreNodeAction().setEnabled(false);
                    MyJFrame.getInstance().getActionManager().getPasteNodeAction().setEnabled(false);
                    MyJFrame.getInstance().getActionManager().getSaveProjectAction().setEnabled(true);
                    MyJFrame.getInstance().getActionManager().getSaveProjectAsAction().setEnabled(true);
                    MyJFrame.getInstance().getActionManager().getOpenProjectAction().setEnabled(true);
                    MyJFrame.getInstance().getActionManager().getShareNodeAction().setEnabled(true);
                }
            }else if(object instanceof Page){
                MyJFrame.getInstance().getActionManager().getNewNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getRenameNodeAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getDeleteNodeAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getCopyNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getCutNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getRestoreNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getPasteNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getSaveProjectAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getSaveProjectAsAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getOpenProjectAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getShareNodeAction().setEnabled(false);
            }else{
                MyJFrame.getInstance().getActionManager().getNewNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getRenameNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getDeleteNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getCopyNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getCutNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getRestoreNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getPasteNodeAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getSaveProjectAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getSaveProjectAsAction().setEnabled(false);
                MyJFrame.getInstance().getActionManager().getOpenProjectAction().setEnabled(true);
                MyJFrame.getInstance().getActionManager().getShareNodeAction().setEnabled(false);
            }
        }
        if(mouseEvent.getClickCount() == 2) {
            TreePath path = MyJFrame.getInstance().getWorkspaceTree().getSelectionPath();
            if (path != null){
                if (path.getLastPathComponent() instanceof Project) {
                    //ako je dupli klik na projekat ponovo se otvaraju
                    //svi zatvoreni dokumenti i postavlja se u fokus
                    MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().initialiseJTabbedPane();

                } else if (path.getLastPathComponent() instanceof Document) {
                    //menja trenutni projectview na onaj kojem pripada odgovarajuci dokumentview
                    if(((Document) path.getLastPathComponent()).getParent() instanceof DocumentContainer)return;

                    if(path.getParentPath().getLastPathComponent() instanceof Project){
                        Project parent = (Project) path.getParentPath().getLastPathComponent();
                        MyJFrame.getInstance().getWorkspaceView().changeProjectView(parent);
                    }
                    
                    //menja selektovani tab na trenutnom projectview
                    MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().
                            setSelectedTab((Document) path.getLastPathComponent());
                }
            }
        }
    }


    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
