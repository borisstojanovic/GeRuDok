package gerudok.model;

import gerudok.model.elements.SlotDevice;
import gerudok.model.elements.SlotElement;
import gerudok.observer.MyObservable;
import gerudok.observer.MyObserver;
import gerudok.observer.ObserverEnum;
import gerudok.state.ManagerState;
import gerudok.view.painters.handle.Handle;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page extends DefaultMutableTreeNode implements MyObservable, MyObserver, Serializable {

    private String name;
    private transient List<MyObserver> observers;
    private  ArrayList<SlotElement> slotElements = new ArrayList<>();
    private PageSelectionModel selectionModel;


    public Page(String name){
        this.name = name;
    }

    public Page(Page p){
        this.name = p.getName();
        this.slotElements = new ArrayList<>();
        for(int i = 0; i < p.getSlotElements().size(); i++){
            SlotElement element = p.getElementAt(i).clone();
            element.addObserver(this);
            slotElements.add(element);
        }
    }

    public Page clone(){
        return new Page(this);
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return name;
    }

    //koristi se kada korisnik postavlja ime zbog handle-ovanja error message-a
    public void renamePage(String name){
        this.name = name;
        notifyObservers(name, ObserverEnum.NAME_CHANGE);
    }

    //koristim kada ja setujem ime
    public void setName(String name){

        if(name == null || name.isEmpty()) {
            return;
        }
        //ako ne postoji postavlja ime
        int sameNameCounter = 1;
        while (checkIfNameExists(name)){
            if(sameNameCounter == 1){
                name = name + " - " + ++sameNameCounter;
                continue;
            }
            name = name.substring(0, name.indexOf('-')) + "- " + ++sameNameCounter;
        }
        this.name = name;
        notifyObservers(name, ObserverEnum.NAME_CHANGE);
    }

    //proverava da li ime vec postoji u istom dokumentu
    public boolean checkIfNameExists(String name) {
        Document document = (Document) this.getParent();
        List<Page> pages = document.getPages();
        for (Page p : pages) {
            if (p.toString().equals(name) && !p.equals(this)) {
                return true;
            }
        }
        return false;
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
        if(observers != null && !observers.isEmpty()){
            for (MyObserver observer : observers) {
                observer.update(var1, changeType);
            }
        }
    }

    public void addSlotElement(SlotElement slotElement){
        if(slotElements == null){
            slotElements = new ArrayList<>();
        }
        if(slotElements != null && !slotElements.contains(slotElement)) {
            slotElements.add(slotElement);
            slotElement.addObserver(this);
            notifyObservers(slotElement, ObserverEnum.DRAW);
        }
    }

    public SlotElement getElementAt(int i){
        return slotElements.get(i);
    }

    public int getElementsCount(){
        return slotElements.size();
    }

    public void removeSlotElement(SlotElement slotElement){
        slotElements.remove(slotElement);
        slotElement.removeObserver(this);
        notifyObservers(slotElement, ObserverEnum.DRAW);
    }

    public ArrayList<SlotElement> getSlotElements() {
        return slotElements;
    }

    public int getElementAtPosition(Point point) {
        for(int i=getElementsCount()-1;i>=0;i--){
            SlotElement element = getElementAt(i);
            if(element.getSlotPainter().isElementAt(point)){
                return i;
            }
        }
        return -1;
    }

    public PageSelectionModel getSelectionModel() {
        if(selectionModel == null)
            selectionModel = new PageSelectionModel();
        return selectionModel;
    }

    @Override
    public void update(Object var1, ObserverEnum changeType) {
        notifyObservers(var1, changeType);
    }

}
