package gerudok.gui.editor;

import gerudok.app.MyJFrame;
import gerudok.model.elements.SlotElement;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ImageEditor extends JDialog implements IEditor{

    private JDialog frame;
    private JPanel editor;
    private SlotElement slotElement;
    private String img;

    public ImageEditor(SlotElement slotElement) {
        this.slotElement = slotElement;
    }

    @Override
    public void showEditor() {

        frame = new JDialog();
        editor = new JPanel();
        JScrollPane editorScrollPane = new JScrollPane(editor);

        JButton insertPictureButton = new JButton("Picture Insert");
        insertPictureButton.addActionListener(new PictureInsertActionListener());
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveActionLisener());

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1.add(insertPictureButton);
        panel1.add(saveButton);



        JPanel toolBarPanel = new JPanel();
        toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.PAGE_AXIS));
        toolBarPanel.add(panel1);

        frame.add(toolBarPanel, BorderLayout.NORTH);
        frame.add(editorScrollPane, BorderLayout.CENTER);

        frame.setSize(600, 500);
        frame.setLocation(150, 80);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        editor.requestFocusInWindow();
        ImageIcon imageIcon = new ImageIcon(slotElement.getContent().getFilePath());
        Image image = imageIcon.getImage().getScaledInstance(400,400, 1);
        imageIcon = new ImageIcon(image);
        editor.add(new JLabel(imageIcon));

        frame.setVisible(true);
    }

    private class PictureInsertActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            File pictureFile = choosePictureFile();
            if (pictureFile == null) {
                editor.requestFocusInWindow();
                return;
            }

            ImageIcon imageIcon = new ImageIcon(pictureFile.toString());
            Image image = imageIcon.getImage().getScaledInstance(400,400, 1);
            imageIcon = new ImageIcon(image);

            editor.requestFocusInWindow();
            editor.removeAll();
            editor.add(new JLabel(imageIcon));
            editor.repaint();
            frame.revalidate();
            MyJFrame.getInstance().revalidate();
        }

        private File choosePictureFile() {

            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "PNG, JPG & GIF Images", "png", "jpg", "gif");
            chooser.setFileFilter(filter);

            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                img = chooser.getSelectedFile().getAbsolutePath();
                return chooser.getSelectedFile();
            }
            else {
                return null;
            }
        }
    }

    private class SaveActionLisener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            slotElement.setContentPath(img);
        }
    }
}
