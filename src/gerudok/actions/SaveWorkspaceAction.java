package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.model.Project;
import gerudok.model.workspace.Workspace;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class SaveWorkspaceAction extends MyAbstractAction {
    public SaveWorkspaceAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_F9, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("Actions-document-save-iconworksapce.png"));
        putValue(NAME, "Save Workspace");
        putValue(SHORT_DESCRIPTION, "Save Workspace");
    }

    public void actionPerformed(ActionEvent arg0) {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new WorkspaceFileFilter());

        Workspace workspace = (Workspace) MyJFrame.getInstance().getWorkspaceModel().getRoot();

        if(!workspace.isChanged()){
            return;
        }

        File workspaceFile = workspace.getWorkspaceFile();
        if (workspaceFile == null){
            if(jfc.showSaveDialog(MyJFrame.getInstance())==JFileChooser.APPROVE_OPTION){
                workspaceFile = jfc.getSelectedFile();
            }else{
                return;
            }
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(workspaceFile));
            oos.writeObject(workspace);
            workspace.setChanged(false);
            workspace.setWorkspaceFile(workspaceFile);
            MyJFrame.getInstance().updateContextFile(workspaceFile);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*JFileChooser jfc = new JFileChooser();
        Workspace workspace = (Workspace)MyJFrame.getInstance().getWorkspaceModel().getRoot();

        File workspaceFile = workspace.getWorkspaceFile();


        if (workspaceFile == null){
            if(jfc.showSaveDialog(MyJFrame.getInstance())==JFileChooser.APPROVE_OPTION){
                workspaceFile = jfc.getSelectedFile();
            }else{
                return;
            }
        }
        ArrayList<String> filePaths = new ArrayList<>();
        for(int i = 0; i < workspace.getProjectCount(); i++) {
            Project p = workspace.getProject(i);
            File projectFile = p.getProjectFile();

            if (projectFile == null) {
                if (jfc.showSaveDialog(MyJFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                    projectFile = jfc.getSelectedFile();
                } else {
                    continue;
                }
            }

            if (!p.isChanged()) {
                filePaths.add(p.getProjectFile().toString());
                continue;
            }
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(projectFile));
                oos.writeObject(p);
                p.setProjectFile(projectFile);
                p.setChanged(false);
                filePaths.add(projectFile.toString());
                oos.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(workspaceFile));
            MyJFrame.getInstance().updateContextFile(workspaceFile);

            for (String filePath : filePaths) {
                bufferedWriter.write(filePath);
                bufferedWriter.newLine();
            }

            workspace.setWorkspaceFile(workspaceFile);
            bufferedWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }

         */
    }
}
