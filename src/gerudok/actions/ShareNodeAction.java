package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.model.Document;
import gerudok.model.Project;
import gerudok.model.workspace.Workspace;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class ShareNodeAction extends MyAbstractAction {

    public ShareNodeAction() {

        putValue(SMALL_ICON, loadIcon("Share-icon.png"));
        putValue(NAME, "Share");
        putValue(SHORT_DESCRIPTION, "Share");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent() instanceof Document){
            Document tmp = (Document) MyJFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
            Project p = showDialog();
            if(!tmp.getParent().equals(p)){
                p.addShared(tmp);
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
