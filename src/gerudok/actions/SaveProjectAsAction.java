package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.model.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;

public class SaveProjectAsAction extends MyAbstractAction {

    public SaveProjectAsAction() {

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_F12, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("Save-as-icon.png"));
        putValue(NAME, "Save project as");
        putValue(SHORT_DESCRIPTION, "Save project as");
    }

    public void actionPerformed(ActionEvent arg0) {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new ProjectFileFilter());

        Project project = MyJFrame.getInstance().getWorkspaceTree().getCurrentProject();
        if(!(project instanceof Project)) {
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.WRONG_SELECTED_SAVE);
            return;
        }
        if (jfc.showSaveDialog(MyJFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
            File projectFile = jfc.getSelectedFile();

            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(projectFile));
                oos.writeObject(project);
                project.setProjectFile(projectFile);
                project.setChanged(false);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }else {
            return;
        }
    }
}