package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.model.Document;
import gerudok.model.Page;
import gerudok.model.Project;
import gerudok.model.workspace.Workspace;
import gerudok.model.workspace.WorkspaceModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;

public class ChangeWorkspaceAction extends MyAbstractAction {
    public ChangeWorkspaceAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("arrow-switch-icon.png"));
        putValue(NAME, "Change Workspace");
        putValue(SHORT_DESCRIPTION, "Change Workspace");
    }

    public void actionPerformed(ActionEvent arg0) {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new WorkspaceFileFilter());
        if(jfc.showOpenDialog(MyJFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
            try {
                ObjectInputStream os = new ObjectInputStream(new FileInputStream(jfc.getSelectedFile()));
                Workspace workspace = (Workspace) os.readObject();

                MyJFrame.getInstance().changeWorkspaceModel(new WorkspaceModel(workspace));

                //u foru se uspostavlja update observer
                for(int i = 0; i < workspace.getProjectCount(); i++){
                    Project project = workspace.getProject(i);
                    project.addObserver(workspace);
                    for(int j = 0; j < project.getDocumentCount(); j++){
                        Document document = project.getDocument(j);
                        document.addObserver(project);
                        for(int k = 0; k < document.getPageCount(); k++){
                            Page page = document.getPage(k);
                            page.addObserver(document);
                            for(int l = 0; l < page.getElementsCount(); l++){
                                page.getElementAt(l).addObserver(page);
                            }
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException| ClassCastException e) {
                ErrorHandlerSimpleFactory.generateError(ExceptionEnum.LOAD_ERROR);
            }
        }
/*
        if(jfc.showOpenDialog(MyJFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(jfc.getSelectedFile()));
                MyJFrame.getInstance().updateContextFile(jfc.getSelectedFile());

                Workspace workspace = new Workspace();
                workspace.setWorkspaceFile(jfc.getSelectedFile());
                MyJFrame.getInstance().changeWorkspaceModel(new WorkspaceModel(workspace));

                String filePath = bufferedReader.readLine();
                while(filePath != null){
                    ObjectInputStream os = new ObjectInputStream(new FileInputStream(new File(filePath)));
                    Project p = null;
                    try {
                        p = (Project) os.readObject();
                    } catch (ClassNotFoundException e) {
                        // TODO errorhandler
                        e.printStackTrace();
                    }
                    MyJFrame.getInstance().getWorkspaceTree().addProject(p);
                    //u for-u se dodaje update observer
                    for (int i = 0; i < p.getDocumentCount(); i++) {
                        Document document = p.getDocument(i);
                        document.addUpdateObserver(p);
                        for (int j = 0; j < document.getPageCount(); j++){
                            document.getPage(j).addUpdateObserver(document);
                        }
                    }
                    p.setProjectFile(new File(filePath));

                    filePath = bufferedReader.readLine();
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

 */
    }
}
