package gerudok.view;

import gerudok.app.MyJFrame;
import gerudok.model.Page;
import gerudok.model.elements.*;
import gerudok.observer.MyObserver;
import gerudok.observer.ObserverEnum;

import javax.swing.*;
import java.awt.*;

public class PageScrollView extends JPanel implements MyObserver {

    private JLabel pageName;
    private Page page;

    public PageScrollView(Page page) {
        this.page = page;
        page.addObserver(this);
        setBackground(Color.WHITE);
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMaximumSize(new Dimension(190,150));
        pageName = new JLabel(page.getName());
        this.add(pageName);
        pageContent();
    }

    @Override
    public void update(Object var1, ObserverEnum changeType) {
        if(var1 instanceof String){
            String newName = (String)var1;
            pageName.setText(newName);
        }
        if(var1 instanceof SlotDevice && changeType == ObserverEnum.DRAW){
            pageContent();
            repaint();
        }
        MyJFrame.getInstance().revalidate();
        MyJFrame.getInstance().repaint();
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    private void pageContent(){
        this.removeAll();
        pageName = new JLabel((page.getName()));
        this.add(pageName);
        int circleCounter = 0, rectangleCounter = 0, triangleCounter = 0;
        for(SlotElement slotElement : page.getSlotElements()){
            if(slotElement instanceof CircleElement){
                circleCounter++;
            }
            if(slotElement instanceof TriangleElement){
                triangleCounter++;
            }
            if(slotElement instanceof RectangleElement){
                rectangleCounter++;
            }
        }
        if(circleCounter != 0){
            JLabel lblC = new JLabel(circleCounter + " circles");
            this.add(lblC);
        }
        if(triangleCounter != 0){
            JLabel lblT = new JLabel(triangleCounter + " triangles");
            this.add(lblT);
        }
        if(rectangleCounter != 0){
            JLabel lblR = new JLabel(rectangleCounter + " rectangles");
            this.add(lblR);
        }
    }
}

