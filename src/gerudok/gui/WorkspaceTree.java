package gerudok.gui;

import gerudok.controller.WorkspaceTreeController;
import gerudok.controller.WorkspaceTreeMouseController;
import gerudok.model.Project;
import gerudok.model.workspace.WorkspaceModel;
import gerudok.view.WorkspaceTreeCellRenderer;
import gerudok.view.WorkspaceTreeEditor;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

public class WorkspaceTree extends JTree {

    public WorkspaceTree() {

        addTreeSelectionListener(new WorkspaceTreeController());
        setCellEditor(new WorkspaceTreeEditor(this,new DefaultTreeCellRenderer()));
        setCellRenderer(new WorkspaceTreeCellRenderer());
        addMouseListener(new WorkspaceTreeMouseController());
        setEditable(true);
    }


    public void addProject(Project project){
        ((WorkspaceModel)getModel()).addProject(project);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void removeProject(Project project) {
        ((WorkspaceModel)getModel()).removeProject(project);
    }

    //uzima trenutno selektovani projekat ili
    //parent projekat od selektovanog dokumenta ili stranice
    public Project getCurrentProject() {

        TreePath path = getSelectionPath();

        if(path != null) {
            for (int i = 0; i < path.getPathCount(); i++) {
                if (path.getPathComponent(i) instanceof Project) {
                    return (Project) path.getPathComponent(i);
                }
            }
        }
        return null;
    }
}
