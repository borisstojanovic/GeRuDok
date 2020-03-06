package gerudok.model.elements;

import gerudok.observer.MyObservable;
import gerudok.observer.MyObserver;
import gerudok.observer.ObserverEnum;
import gerudok.view.painters.SlotElementPainter;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class SlotElement implements Serializable, MyObservable {

    protected Paint paint;//unutrasnjost elementa
    protected SerializableStroke stroke;//definise izgled linije za iscrtavanje
    protected String name;
    protected SlotElementPainter slotPainter;//instanciranje SlotElemPaintera izvrsavaju elementi prilikom svoje konstrukcije
    protected Content content;
    protected transient List<MyObserver> observers;

    //meootda za kolinranje elemenata
    abstract public SlotElement clone();

    public SlotElement(Paint paint, Stroke stroke){
        this.content = new Content();
        this.paint = paint;
        this.stroke = new SerializableStroke(stroke);
    }

    public SlotElement(SlotElement element){
        this.content = element.getContent().clone();
        this.paint = element.paint;
        this.stroke = element.stroke;
        this.name = element.name;
        this.slotPainter = element.slotPainter;
    }

    public SlotElementPainter getSlotPainter() {
        return slotPainter;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = new SerializableStroke(stroke);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Content getContent() {
        return content;
    }

    public void setContentType(ContentTypeEnum contentType){
        content.setContentTypeEnum(contentType);
        notifyObservers(this, ObserverEnum.UPDATE);
    }

    public void setContentPath(String path){
        content.setFilePath(path);
        notifyObservers(this, ObserverEnum.UPDATE);
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void setSlotPainter(SlotElementPainter slotPainter) {
        this.slotPainter = slotPainter;
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
