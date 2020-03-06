package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.model.Document;
import gerudok.model.Page;
import gerudok.model.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;

public class OpenProjectAction extends MyAbstractAction {
    public OpenProjectAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("open-file-icon.png"));
        putValue(NAME, "Open project");
        putValue(SHORT_DESCRIPTION, "Open project");
    }

    public void actionPerformed(ActionEvent arg0) {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new ProjectFileFilter());

        if(jfc.showOpenDialog(MyJFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
            try {
                ObjectInputStream os = new ObjectInputStream(new FileInputStream(jfc.getSelectedFile()));

                Project p = null;
                try {
                    p = (Project) os.readObject();
                } catch (ClassNotFoundException | ClassCastException | InvalidClassException e) {
                    ErrorHandlerSimpleFactory.generateError(ExceptionEnum.LOAD_ERROR);
                }

                MyJFrame.getInstance().getWorkspaceTree().addProject(p);
                MyJFrame.getInstance().getWorkspaceView().changeProjectView(p);


                for (int i = 0; i < p.getDocumentCount(); i++){
                    Document d = p.getDocument(i);
                    p.getDocument(i).addObserver(p);
                    for(int j = 0; j < d.getPageCount(); j++){
                        d.getPage(j).addObserver(d);
                        Page pg = d.getPage(j);
                        for(int k = 0; k < pg.getElementsCount(); k++){
                            pg.getElementAt(k).addObserver(pg);
                        }
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
