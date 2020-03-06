package gerudok.controller;

import gerudok.app.MyJFrame;
import gerudok.model.Project;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;


public class WorkspaceTreeController implements TreeSelectionListener {
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        if(path.getLastPathComponent() instanceof Project) {
            MyJFrame.getInstance().getWorkspaceView().changeProjectView((Project)path.getLastPathComponent());
        }
    }
}
