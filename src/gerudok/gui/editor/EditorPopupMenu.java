package gerudok.gui.editor;

import gerudok.app.MyJFrame;

import javax.swing.*;

public class EditorPopupMenu {

    JPopupMenu popup;

    public EditorPopupMenu() {
        popup = new JPopupMenu();

        popup.add(MyJFrame.getInstance().getActionManager().getEditorImageAction());
        popup.add(MyJFrame.getInstance().getActionManager().getEditorTextAction());
    }
}
