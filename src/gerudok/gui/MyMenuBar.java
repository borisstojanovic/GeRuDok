package gerudok.gui;

import gerudok.app.MyJFrame;

import javax.swing.*;

public class MyMenuBar extends JMenuBar {

    public MyMenuBar() {
        JMenu fileMenu = new JMenu("File");

        fileMenu.add(MyJFrame.getInstance().getActionManager().getNewNodeAction());
        fileMenu.add(MyJFrame.getInstance().getActionManager().getOpenProjectAction());
        fileMenu.add(MyJFrame.getInstance().getActionManager().getCopyNodeAction());
        fileMenu.add(MyJFrame.getInstance().getActionManager().getPasteNodeAction());
        fileMenu.add(MyJFrame.getInstance().getActionManager().getCutNodeAction());
        fileMenu.add(MyJFrame.getInstance().getActionManager().getShareNodeAction());

        fileMenu.addSeparator();

        fileMenu.add(MyJFrame.getInstance().getActionManager().getSaveProjectAsAction());
        fileMenu.add(MyJFrame.getInstance().getActionManager().getSaveProjectAction());

        fileMenu.addSeparator();

        fileMenu.add(MyJFrame.getInstance().getActionManager().getDeleteNodeAction());
        fileMenu.add(MyJFrame.getInstance().getActionManager().getRenameNodeAction());
        fileMenu.add(MyJFrame.getInstance().getActionManager().getCloseAction());
        fileMenu.add(MyJFrame.getInstance().getActionManager().getCloseAllAction());
        fileMenu.add(MyJFrame.getInstance().getActionManager().getSaveWorkspaceAction());
        fileMenu.add(MyJFrame.getInstance().getActionManager().getChangeWorkspaceAction());

        fileMenu.addSeparator();
        fileMenu.add(MyJFrame.getInstance().getActionManager().getUndoAction());
        fileMenu.add(MyJFrame.getInstance().getActionManager().getRedoAction());

        add(fileMenu);

        JMenu helpMenu=new JMenu("Help");
        helpMenu.add(MyJFrame.getInstance().getActionManager().getAboutAction());
        add(helpMenu);

    }
}
