package gerudok.model.factory;

import gerudok.model.Document;
import gerudok.model.Project;
import gerudok.model.workspace.Workspace;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class ProjectFactory extends NodeFactory {

    @Override
    public MutableTreeNode createNode() {
        Project proj = new Project("Project");
        return proj;
    }
}
