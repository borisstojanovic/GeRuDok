package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.model.workspace.Workspace;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ExitWindowAction implements WindowListener {

    @Override
    public void windowOpened(java.awt.event.WindowEvent e) {

    }

    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {
        int answ = exitAppAction();
        if(answ == 1){
            Workspace workspace = (Workspace) MyJFrame.getInstance().getWorkspaceModel().getRoot();
            if(workspace.isChanged()) {
                answ = saveWorkspaceQuestion();
                if (answ == 1) {
                    saveWorkspace(workspace);
                }else if(answ == 2){
                    //ako je korisnik odabrao cancel aplikacija nastavlja sa radom
                    MyJFrame.getInstance().setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                    return;
                }
            }
            MyJFrame.getInstance().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        else{
            MyJFrame.getInstance().setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        }
    }

    private void saveWorkspace(Workspace workspace) {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new WorkspaceFileFilter());

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
            workspace.setWorkspaceFile(workspaceFile);
            MyJFrame.getInstance().updateContextFile(workspaceFile);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int saveWorkspaceQuestion() {
        int choice = JOptionPane.showConfirmDialog(MyJFrame.getInstance(), "Do you want to save the current workspace?");
        if(choice == JOptionPane.YES_OPTION) {
            return 1;
        }
        else if(choice == JOptionPane.CANCEL_OPTION) {
            return 2;
        }
        return 0;
    }

    public int exitAppAction(){
        int answ = JOptionPane.showConfirmDialog(MyJFrame.getInstance(), "Are you sure you want to close application");
        if(answ == JOptionPane.YES_OPTION) {
            return 1;
        }
        else
            return 0;
    }

    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {

    }

    @Override
    public void windowIconified(java.awt.event.WindowEvent e) {

    }

    @Override
    public void windowDeiconified(java.awt.event.WindowEvent e) {

    }

    @Override
    public void windowActivated(java.awt.event.WindowEvent e) {

    }

    @Override
    public void windowDeactivated(java.awt.event.WindowEvent e) {

    }
}
