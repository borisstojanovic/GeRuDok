package gerudok.model;

import gerudok.observer.MyObservable;
import gerudok.observer.MyObserver;
import gerudok.observer.ObserverEnum;

import javax.print.Doc;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Document extends DefaultMutableTreeNode implements MyObservable, MyObserver, Serializable {

    private transient List<MyObserver> observers;
    private List<Page> pages;
    private String name;

    public Document(String name){
        this.name = name;
    }

    public Document(Document d){
        this.name = new String(d.name);
        this.pages = new ArrayList<>();
        for(int i = 0; i < d.getPageCount(); i++){
            Page tmp = d.getPage(i).clone();
            tmp.addObserver(this);
            this.add(tmp);
        }
    }


    public Document clone(){
        return new Document(this);
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public void add(MutableTreeNode newChild){
        if(pages == null){
            pages = new ArrayList<>();
        }
        if(newChild != null && newChild instanceof Page && !pages.contains((Page)newChild)){
            Page page = (Page)newChild;
            pages.add(page);
            super.add(newChild);
            page.setName(page.getName());
            page.addObserver(this);
            notifyObservers(page, ObserverEnum.ADD);
        }
    }

    public void removePage(Page page){
        if(page != null && pages != null && !pages.isEmpty()) {
            pages.remove(page);
            super.remove(page);
            notifyObservers(page, ObserverEnum.REMOVE);
        }
    }

    //koristi se kada korisnik postavlja ime zbog handle-ovanja error message-a
    public void renameDocument(String name){
        this.name = name;
        notifyObservers(name, ObserverEnum.NAME_CHANGE);
    }

    //koristim kada ja setujem ime
    public void setName(String name){
        if(name == null || name.isEmpty()){
            return;
        }
        //prolazi kroz while petlju dok god postoji isto ime i generise novo ime
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

    public boolean checkIfNameExists(String name) {
        if(this.getParent() instanceof Project) {
            Project project = (Project) this.getParent();
            List<Document> documents = project.getDocuments();

            for (Document d : documents) {
                if (d.toString().equals(name) && !d.equals(this)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Page> getPages() {
        return pages;
    }

    public String getName() {
        return name;
    }

    public Page getPage(int arg0){
        return pages.get(arg0);
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

    @Override
    public void update(Object var1, ObserverEnum changeType) {
        notifyObservers(var1, ObserverEnum.UPDATE);
    }

    public int getPageCount() {
        if(pages == null)return 0;
        return pages.size();
    }
}
