package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.model.Document;
import gerudok.model.Project;
import gerudok.model.workspace.DocumentContainer;
import gerudok.model.workspace.Workspace;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class RestoreNodeAction extends MyAbstractAction {

    public RestoreNodeAction() {

        putValue(SMALL_ICON, loadIcon("Restore-Window-icon.png"));
        putValue(NAME, "Restore");
        putValue(SHORT_DESCRIPTION, "Restore");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent() instanceof Document){
            Document document = (Document) MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
            if(document.getParent() instanceof DocumentContainer){
                DocumentContainer documentContainer = (DocumentContainer) document.getParent();
                documentContainer.removeDocument(document);
                Project project = showDialog();
                project.add(document);
                MyJFrame.getInstance().getWorkspaceTree().updateUI();
            }
        }
    }
    private Project showDialog() {

        List<Project> projects = ((Workspace)MyJFrame.getInstance().getWorkspaceModel().getRoot()).getProjects();
        String[] projectNames = new String[projects.size()];
        int i = 0;

        for (Project project : projects) {
            projectNames[i++] = project.getName();
        }

        String input = (String) JOptionPane.showInputDialog(null, "Choose a project...",
                "Choice", JOptionPane.QUESTION_MESSAGE, null, projectNames, projectNames[0]);

        for (Project project : projects) {
            if(project.getName().equals(input)){
                return project;
            }
        }

        return null;
    }
}
