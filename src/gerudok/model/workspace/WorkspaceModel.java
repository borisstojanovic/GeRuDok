package gerudok.model.workspace;

import gerudok.model.Project;

import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.util.List;

public class WorkspaceModel extends DefaultTreeModel {


    public WorkspaceModel() {
        super(new Workspace());
    }

    public WorkspaceModel(Workspace workspace){
        super(workspace);
    }


    public void addProject(Project project) {
        ((Workspace)getRoot()).add(project);
    }
    public void removeProject(Project project) {
        ((Workspace)getRoot()).removeProject(project);
    }


    public void setWorkspaceFilePath(String workspacePath) {
        Workspace workspace = (Workspace)this.getRoot();
        workspace.setWorkspaceFile(new File(workspacePath));
    }
}
