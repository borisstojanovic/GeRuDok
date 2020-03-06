package gerudok.actions;

import gerudok.gui.MyDialogAbout;

import java.awt.event.ActionEvent;

public class AboutAction extends MyAbstractAction{

    public AboutAction(){
        putValue(NAME, "About");
        putValue(SHORT_DESCRIPTION, "About");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new MyDialogAbout();
    }
}
