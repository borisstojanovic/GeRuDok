package gerudok.view;

import gerudok.model.Document;
import gerudok.model.Page;
import gerudok.model.Project;
import gerudok.model.workspace.Workspace;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class WorkspaceTreeCellRenderer extends DefaultTreeCellRenderer {

    public WorkspaceTreeCellRenderer(){

    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expeanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expeanded, leaf, row, hasFocus);

        if (value instanceof Workspace) {
            Icon icon = new ImageIcon("images/Folder-worksapce.png");
            setIcon(icon);
        }else if (value instanceof Project ) {
            Icon icon = new ImageIcon("images/Folder-icon.png");
            setIcon(icon);
        } else if (value instanceof Document) {
            Icon icon = new ImageIcon("images/Document-Blank-icon.png");
            setIcon(icon);
        } else if (value instanceof Page) {
            Icon icon = new ImageIcon("images/Document-Write-icon.png");
            setIcon(icon);
        }
        return this;
    }

}
