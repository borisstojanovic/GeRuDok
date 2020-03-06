package gerudok.gui.editor;


import gerudok.app.MyJFrame;
import gerudok.model.elements.SlotElement;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextEditor extends JDialog implements IEditor{

    private JDialog frame;
    private JTextPane editor;
    private SlotElement slotElement;

    public TextEditor(SlotElement slotElement){
        this.slotElement = slotElement;
    }

    @Override
    public void showEditor() {

        frame = new JDialog();
        editor = new JTextPane();

        frame.setModalityType(ModalityType.APPLICATION_MODAL);
        JScrollPane editorScrollPane = new JScrollPane(editor);

        editor.setDocument(new DefaultStyledDocument());

        EditButtonActionListener editButtonActionListener = new EditButtonActionListener();

        JButton boldButton = new JButton(new StyledEditorKit.BoldAction());
        boldButton.setHideActionText(false);
        boldButton.setText("Bold");
        boldButton.addActionListener(editButtonActionListener);
        JButton italicButton = new JButton(new StyledEditorKit.ItalicAction());
        italicButton.setHideActionText(true);
        italicButton.setText("Italic");
        italicButton.addActionListener(editButtonActionListener);
        JButton underlineButton = new JButton(new StyledEditorKit.UnderlineAction());
        underlineButton.setHideActionText(true);
        underlineButton.setText("Underline");
        underlineButton.addActionListener(editButtonActionListener);
        JButton colorButton = new JButton("Set Color");
        colorButton.addActionListener(new ColorActionListener());
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveActionLisener());

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1.add(new JSeparator(SwingConstants.VERTICAL));
        panel1.add(boldButton);
        panel1.add(italicButton);
        panel1.add(underlineButton);
        panel1.add(new JSeparator(SwingConstants.VERTICAL));
        panel1.add(colorButton);
        panel1.add(new JSeparator(SwingConstants.VERTICAL));
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

        if(slotElement.getContent().getFilePath() != null) {
            File file = new File(slotElement.getContent().getFilePath());
            try {
                ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
                StyledDocument styledDocument = (StyledDocument) is.readObject();
                editor.setStyledDocument(styledDocument);
            } catch (IOException | ClassNotFoundException e) {

            }
        }
        frame.setVisible(true);
    }

    private StyledDocument getEditorDocument() {

        StyledDocument doc = (DefaultStyledDocument) editor.getDocument();
        return doc;
    }

    private class EditButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            editor.requestFocusInWindow();
        }
    }

    private class ColorActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            Color newColor = JColorChooser.showDialog(frame, "Choose a color",
                    Color.BLACK);
            if (newColor == null) {


                editor.requestFocusInWindow();
                return;
            }

            SimpleAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setForeground(attr, newColor);
            editor.setCharacterAttributes(attr, false);
            editor.requestFocusInWindow();
        }
    }

    private class SaveActionLisener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            File file = null;
            if(slotElement.getContent().getFilePath() == null){
                try {
                    file = File.createTempFile("cont", ".rtf");
                    slotElement.getContent().setFilePath(file.getPath());
                } catch (IOException ex) {

                }
            }else{
                file = new File(slotElement.getContent().getFilePath());
            }
            if(file == null)return;
            ObjectOutputStream out = null;
            try {
                out = new ObjectOutputStream(new FileOutputStream(file));
                StyledDocument doc = editor.getStyledDocument();
                out.writeObject(doc);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //todo napravi observer
            frame.revalidate();
            MyJFrame.getInstance().revalidate();
        }
    }
}
