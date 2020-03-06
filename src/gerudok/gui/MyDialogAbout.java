package gerudok.gui;

import javax.swing.*;
import java.awt.*;

public class MyDialogAbout extends JDialog {

    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lblPic1;
    private JLabel lblPic2;
    private Box boxLeft;
    private Box boxRight;

    public MyDialogAbout(){
        super();

        this.setTitle("Tim MiB");
        this.setDefaultCloseOperation(2);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        this.setSize(screenWidth / 3, screenHeight / 3);
        this.setLocationRelativeTo(null);

        this.setLayout(new GridLayout(1,2));

        lbl1 = new JLabel("Boris Stojanovic RN35-2018");
        lbl1.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lbl2 = new JLabel("Milos Maksimovic RN43-2018");
        lbl2.setAlignmentX(JLabel.CENTER_ALIGNMENT);


        //resizeuje sliku i dodaje u labelu
        ImageIcon myImage = new ImageIcon("images2/bstojanovic3518rn.jpg");
        Image resizedImage = myImage.getImage().getScaledInstance(200,200,1);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        ImageIcon milosImage = new ImageIcon("images2/mmaksimovic4318rn.jpg");
        Image milosResizedImage = milosImage.getImage().getScaledInstance(150,200,1);
        ImageIcon milosResizedIcon = new ImageIcon(milosResizedImage);

        lblPic1 = new JLabel(resizedIcon);
        lblPic1.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        lblPic2 = new JLabel(milosResizedIcon);
        lblPic2.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        boxLeft = Box.createVerticalBox();
        boxLeft.add(lbl2);
        boxLeft.add(lblPic2);

        boxRight = Box.createVerticalBox();
        boxRight.add(lbl1);
        boxRight.add(lblPic1);

        this.add(boxRight);
        this.add(boxLeft);

        this.setVisible(true);
    }


}
