package gerudok.gui;

import gerudok.app.MyJFrame;

import javax.swing.*;

public class MyPopupMenu {
    WorkspaceTree tree = MyJFrame.getInstance().getWorkspaceTree();
    JPopupMenu popup;

    public MyPopupMenu() {
        popup = new JPopupMenu();
        PopupHandler handler = new PopupHandler(tree, popup);

        popup.add(MyJFrame.getInstance().getActionManager().getNewNodeAction());

        popup.addSeparator();

        popup.add(MyJFrame.getInstance().getActionManager().getCopyNodeAction());
        popup.add(MyJFrame.getInstance().getActionManager().getCutNodeAction());
        popup.add(MyJFrame.getInstance().getActionManager().getPasteNodeAction());

        popup.addSeparator();

        popup.add(MyJFrame.getInstance().getActionManager().getSaveProjectAsAction());
        popup.add(MyJFrame.getInstance().getActionManager().getSaveProjectAction());

        popup.addSeparator();

        popup.add(MyJFrame.getInstance().getActionManager().getDeleteNodeAction());
        popup.add(MyJFrame.getInstance().getActionManager().getRenameNodeAction());
    }
}

