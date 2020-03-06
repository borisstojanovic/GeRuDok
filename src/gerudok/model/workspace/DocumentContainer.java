package gerudok.model.workspace;

import gerudok.model.Document;
import gerudok.observer.MyObservable;
import gerudok.observer.MyObserver;
import gerudok.observer.ObserverEnum;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DocumentContainer extends DefaultMutableTreeNode implements MyObservable, Serializable {

    private List<Document> documents = new ArrayList<>();
    private List<MyObserver> observers;
    private String name;

    public DocumentContainer(){
        this.name = "Document Container";
    }

    public void add(Document document){
        if(documents == null){
            documents = new ArrayList<>();
        }
        if(document != null && !documents.contains(document)){

            documents.add(document);
            super.add(document);

            notifyObservers(document, ObserverEnum.ADD);
        }
    }
    public void removeDocument(Document document){

        if(document != null && documents !=null && !documents.isEmpty()) {
            notifyObservers(document, ObserverEnum.REMOVE);
            super.remove(document);
            documents.remove(document);
        }
    }

    public Document getDocument(int arg0){
        return documents.get(arg0);
    }

    public List<Document> getDocuments() {
        return documents;
    }



    @Override
    public String toString() {
        return name;
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

    public int getDocumentCount() {
        if(documents == null)return 0;
        return documents.size();
    }

}