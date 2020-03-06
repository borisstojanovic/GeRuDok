package gerudok.actions;

import javax.swing.*;
import java.io.File;

public abstract class MyAbstractAction extends AbstractAction {


    /**
     * Kreira ikonu na osnovnu zadate putanje(imane ikone)
     * @param icon
     * @return
     */
    public ImageIcon loadIcon(String icon) {
        String path = "images/" + icon;

        if(new File(path).exists()) {
            return new ImageIcon(path);
        }
        else{
            System.err.println("Icon" + path +  "not found!");
            return null;
        }
    }
}
