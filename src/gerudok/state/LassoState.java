package gerudok.state;

import gerudok.view.PageView;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class LassoState extends State {

    PageView mediator;
    Rectangle2D rect = new Rectangle2D.Double();

    public LassoState(PageView mediator){
        this.mediator = mediator;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point2D mousePos=e.getPoint();

        if(!e.isControlDown()){

            mediator.getPage().getSelectionModel().removeAllFromSelectionList();
        }

        double width=mousePos.getX() - mediator.getLastPosition().getX();
        double height=mousePos.getY() - mediator.getLastPosition().getY();
        if((width<0)&&(height<0)){
            rect.setRect(mousePos.getX(), mousePos.getY(),Math.abs(width),Math.abs(height));
        }
        else if((width<0)&&(height>=0)){
            rect.setRect(mousePos.getX(), mediator.getLastPosition().getY(),Math.abs(width),Math.abs(height));
        }
        else if((width>0)&&(height<0)){
            rect.setRect(mediator.getLastPosition().getX(), mousePos.getY(), Math.abs(width),Math.abs(height));
        }
        else{
            rect.setRect(mediator.getLastPosition().getX(), mediator.getLastPosition().getY(),Math.abs(width),Math.abs(height));
        }
        mediator.setSelectionRectangle(rect);


        mediator.getPage().getSelectionModel().selectElements(rect, mediator.getPage().getSlotElements());


        mediator.repaint();

    }

    public void mouseReleased(MouseEvent e) {
        mediator.setSelectionRectangle(new Rectangle2D.Double(0,0,0,0));
        mediator.repaint();
        mediator.startSelectState();
    }


    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
