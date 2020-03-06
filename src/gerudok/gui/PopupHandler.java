package gerudok.gui;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PopupHandler {
    private JTree tree;
    private JPopupMenu popup;
    private Point locPoint;

    public PopupHandler(JTree tree, JPopupMenu popup) {
        this.tree = tree;
        this.popup = popup;
        tree.addMouseListener(mouseListener);
    }

    private MouseListener mouseListener = new MouseListener() {

        private void checkForPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                locPoint = e.getPoint();
                TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                if (path != null) {
                    popup.show(tree, locPoint.x, locPoint.y);
                }
            }
        }

        public void mousePressed(MouseEvent e) {
            checkForPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            checkForPopup(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        public void mouseClicked(MouseEvent e) {
            checkForPopup(e);
        }
    };
}
