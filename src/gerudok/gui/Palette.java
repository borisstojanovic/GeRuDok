package gerudok.gui;

import gerudok.app.MyJFrame;

import javax.swing.*;

public class Palette extends JToolBar {

    public Palette(){
        super(JToolBar.VERTICAL);
        add(MyJFrame.getInstance().getActionManager().getCircleAction());
        add(MyJFrame.getInstance().getActionManager().getTriangleAction());
        add(MyJFrame.getInstance().getActionManager().getRectangleAction());
        add(MyJFrame.getInstance().getActionManager().getRemoveDeviceAction());
        add(MyJFrame.getInstance().getActionManager().getRotateAction());
        add(MyJFrame.getInstance().getActionManager().getResizeAction());
        add(MyJFrame.getInstance().getActionManager().getSelectAction());
        add(MyJFrame.getInstance().getActionManager().getMoveAction());
        add(MyJFrame.getInstance().getActionManager().getShowEditorAction());

        addSeparator();

        add(MyJFrame.getInstance().getActionManager().getCopyAction());
        add(MyJFrame.getInstance().getActionManager().getPasteAction());
        add(MyJFrame.getInstance().getActionManager().getCutAction());

    }
}
