package gerudok.state;

import gerudok.model.elements.SlotElement;
import gerudok.view.PageView;
import gerudok.view.painters.handle.Handle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class SelectState extends State {

    private PageView mediator;

    private int elementInMotion = -1;
    private Handle handleInMotion = null;

    public SelectState(PageView mediator){
        this.mediator = mediator;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        Point position = e.getPoint();

        if (e.getButton()==MouseEvent.BUTTON1){

            handleInMotion = mediator.getDeviceAndHandleForPoint(position);
            if(handleInMotion == null){

                elementInMotion = mediator.getPage().getElementAtPosition(position);
                if(elementInMotion != -1){

                    //pogodjen je element, ukoliko je selektovan treba ga ukloniti iz liste,
                    //ako nije treba ga dodati u listu

                    SlotElement element=mediator.getPage().getElementAt(elementInMotion);

                    if (mediator.getPage().getSelectionModel().isElementSelected(element)){
                        if(e.isControlDown()){
                            //ako je pritisnut control izbacuje se selektovani element iz liste
                            mediator.getPage().getSelectionModel().removeFromSelectionList(element);
                        }else if(e.isShiftDown()){
                            //ako je pritisnut shift pomeraju se svi selektovani elementi
                            mediator.startMoveState();
                            return;
                        }else{
                            //ako nije pritisnut ni shift ni control izbacuje sve ostale elemente iz liste
                            //selektovanih i postavlja state na move
                            mediator.getPage().getSelectionModel().removeAllFromSelectionList();
                            mediator.getPage().getSelectionModel().addToSelectionList(element);
                            mediator.startMoveState();
                            return;
                        }
                    }else{
                        if(!e.isControlDown()){
                            mediator.getPage().getSelectionModel().removeAllFromSelectionList();
                        }
                        mediator.getPage().getSelectionModel().addToSelectionList(element);
                    }

                }else{
                    //nije pogodjen nijedan element
                    if(!e.isControlDown()){
                        mediator.getPage().getSelectionModel().removeAllFromSelectionList();
                    }
                }
            }else {
                if(handleInMotion == Handle.ROTATE){
                    mediator.startRotateState();
                    return;
                }else {
                    mediator.startResizeState();
                    return;
                }
            }
        }

        mediator.repaint();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(mediator.getPage().getElementAtPosition(e.getPoint()) == -1){
            mediator.startLassoState();
            return;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point2D point = e.getPoint();
        mediator.setMouseCursor(point);
    }
}
