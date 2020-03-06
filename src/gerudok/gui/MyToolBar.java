package gerudok.gui;

import gerudok.app.MyJFrame;

import javax.swing.*;

public class MyToolBar extends JToolBar {

    public MyToolBar() {
        super();
        add(MyJFrame.getInstance().getActionManager().getNewNodeAction());
        add(MyJFrame.getInstance().getActionManager().getRestoreNodeAction());
        add(MyJFrame.getInstance().getActionManager().getOpenProjectAction());
        add(MyJFrame.getInstance().getActionManager().getCopyNodeAction());
        add(MyJFrame.getInstance().getActionManager().getPasteNodeAction());
        add(MyJFrame.getInstance().getActionManager().getCutNodeAction());
        add(MyJFrame.getInstance().getActionManager().getShareNodeAction());

        addSeparator();

        add(MyJFrame.getInstance().getActionManager().getSaveProjectAsAction());
        add(MyJFrame.getInstance().getActionManager().getSaveProjectAction());

        addSeparator();

        add(MyJFrame.getInstance().getActionManager().getUndoAction());
        add(MyJFrame.getInstance().getActionManager().getRedoAction());

        addSeparator();

        add(MyJFrame.getInstance().getActionManager().getDeleteNodeAction());
        add(MyJFrame.getInstance().getActionManager().getRenameNodeAction());
        add(MyJFrame.getInstance().getActionManager().getCloseAction());
        add(MyJFrame.getInstance().getActionManager().getCloseAllAction());

    }
}
