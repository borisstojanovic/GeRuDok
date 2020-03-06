package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.model.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;

public class SaveProjectAction extends MyAbstractAction {
    public SaveProjectAction() {

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("Save-icon2.png"));
        putValue(NAME, "Save project");
        putValue(SHORT_DESCRIPTION, "Save project");
    }

    public void actionPerformed(ActionEvent arg0) {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new ProjectFileFilter());

        Project project = MyJFrame.getInstance().getWorkspaceTree().getCurrentProject();
        File projectFile = null;
        if(project instanceof Project) {
            projectFile = project.getProjectFile();
        }else{
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.WRONG_SELECTED_SAVE);
            return;
        }

        if (!project.isChanged()){
            return;
        }

        if (project.getProjectFile()==null) {
            if (jfc.showSaveDialog(MyJFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                projectFile = jfc.getSelectedFile();
            } else {
                return;
            }
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(projectFile));
            oos.writeObject(project);
            project.setProjectFile(projectFile);
            project.setChanged(false);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

}
