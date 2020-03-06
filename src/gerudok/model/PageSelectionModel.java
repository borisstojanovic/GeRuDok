package gerudok.model;

import gerudok.model.elements.SlotDevice;
import gerudok.model.elements.SlotElement;
import gerudok.observer.MyObservable;
import gerudok.observer.MyObserver;
import gerudok.observer.ObserverEnum;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

public class PageSelectionModel extends DefaultSingleSelectionModel implements MyObservable {

    private ArrayList<SlotElement> selectionList = new ArrayList<>();
    private transient ArrayList<MyObserver> observers = new ArrayList<>();


    public void addToSelectionList(SlotElement element) {
        selectionList.add(element);
        notifyObservers(element, ObserverEnum.UPDATE);
    }

    public void addAllToSelectionList(ArrayList<SlotElement> list) {
        selectionList.addAll(list);
        notifyObservers(list, ObserverEnum.UPDATE);
    }


    public int getSelectionListSize() {
        return selectionList.size();
    }

    public SlotElement getElementFromSelectionListAt(int index) {
        return (SlotElement) selectionList.get(index);
    }

    public int getIndexByObject(SlotElement element) {
        return selectionList.indexOf(element);
    }

    public void removeFromSelectionList(SlotElement element) {
        selectionList.remove(element);
        notifyObservers(element, ObserverEnum.UPDATE);
    }

    public void removeAllFromSelectionList() {
        selectionList.clear();
        notifyObservers(null, ObserverEnum.UPDATE);
    }

    public ArrayList<SlotElement>  getSelectionList() {
        return selectionList;
    }

    public Iterator<SlotElement> getSelectionListIterator(){
        return selectionList.iterator();
    }

    public boolean isElementSelected(SlotElement element){
        return selectionList.contains(element);

    }

    public void selectElements(Rectangle2D rec, ArrayList<SlotElement> elements){
        Iterator<SlotElement> it = elements.iterator();
        while(it.hasNext()){
            SlotElement element=it.next();
            if (element instanceof SlotDevice){
                SlotDevice device=(SlotDevice)element;

                Point center = new Point((int)(device.getPosition().getX() + device.getSize().width/2),
                        (int)(device.getPosition().getY() + device.getSize().height/2));

                Shape rectangle = new Rectangle2D.Double(device.getPosition().getX(), device.getPosition().getY(),
                        device.getSize().getWidth(), device.getSize().getHeight());

                AffineTransform affineTransform = AffineTransform.getRotateInstance(device.getAngle(),
                        center.getX(), center.getY());
                rectangle = affineTransform.createTransformedShape(rectangle);

                if(rectangle.intersects(rec)){
                    if(!isElementSelected(device))
                        selectionList.add(device);
                }
            }
        }
    }

    @Override
    public void addObserver(MyObserver var1) {
        if(observers == null){
            observers = new ArrayList<>();
        }
        if(var1 != null && !observers.contains(var1)){
            observers.add(var1);
        }
    }

    @Override
    public void removeObserver(MyObserver var1) {
        if(var1 != null && observers != null){
            observers.remove(var1);
        }
    }

    @Override
    public void notifyObservers(Object var1, ObserverEnum changeType) {
        if(observers != null){
            for (MyObserver observer : observers) {
                observer.update(var1, changeType);
            }
        }
    }
}
