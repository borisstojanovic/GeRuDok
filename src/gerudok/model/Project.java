package gerudok.model;

import gerudok.app.MyJFrame;
import gerudok.model.workspace.Workspace;
import gerudok.observer.MyObservable;
import gerudok.observer.MyObserver;
import gerudok.observer.ObserverEnum;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Project extends DefaultMutableTreeNode implements MyObservable, MyObserver, Serializable {

    private String name;
    private List<Document> documents;
    private File projectFile;
    private transient boolean changed;
    private transient List<MyObserver> observers;

    public Project(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void addShared(Document document){
        if(documents == null){
            documents = new ArrayList<>();
        }
        if(document != null && !documents.contains(document)){

            documents.add(document);
            if(super.children == null){
                super.children = new Vector<>();
            }
            super.children.add(document);

            document.addObserver(this);

            notifyObservers(document, ObserverEnum.ADD);
            this.changed = true;
        }
    }

    @Override
    public void add(MutableTreeNode newChild){
        if(documents == null){
            documents = new ArrayList<>();
        }
        if(newChild != null && newChild instanceof Document && !documents.contains((Document) newChild)){
            Document document = (Document) newChild;

            documents.add(document);
            super.add(document);

            document.addObserver(this);
            document.setName(document.getName());

            notifyObservers(document, ObserverEnum.ADD);
            this.changed = true;
        }
    }

    //koristi se kada korisnik postavlja ime zbog handle-ovanja error message-a
    public void renameProject(String name){
        this.name = name;
        notifyObservers(name, ObserverEnum.NAME_CHANGE);
    }

    //koristim kada ja setujem ime
    public void setName(String name){
        if(name == null || name.isEmpty()){
            return;
        }

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
        this.changed = true;
    }

    //proverava da li ime vec postoji u listi projekata i vraca true ako postoji
    public boolean checkIfNameExists(String name) {
        Workspace workspace = (Workspace) this.getParent();
        List<Project> projects = workspace.getProjects();

        for (Project p : projects) {
            if (p.toString().equals(name) && !p.equals(this)) {
                return true;
            }
        }
        return false;
    }


    public void removeDocument(Document document){

        if(document != null && documents !=null && !documents.isEmpty()) {
            notifyObservers(document, ObserverEnum.REMOVE);
            if(document.getParent().equals(this)) {
                super.remove(document);

                /*Workspace parent = (Workspace) this.getParent();

                for (Project project : parent.getProjects()) {
                    if(project.equals(this))continue;
                    if(project.getDocumentCount() != 0 && project.getDocuments().contains(document)){
                        document.setParent(project);
                        return;
                    }
                }

                 */
            }else{
                super.children.remove(document);
            }
            documents.remove(document);
            this.changed = true;
        }
    }

    public Document getDocument(int arg0){
        return documents.get(arg0);
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setProjectFile(File projectFile) {
        this.projectFile = projectFile;
    }

    public File getProjectFile() {
        return projectFile;
    }

    public String getName() {
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

    public boolean isChanged() {
        return this.changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public int getDocumentCount() {
        if(documents == null)return 0;
        return documents.size();
    }

    @Override
    public void update(Object var1, ObserverEnum changeType) {
        this.setChanged(true);
        notifyObservers(var1, ObserverEnum.UPDATE);
    }

    public void  paste (){

        Transferable clipboardContent = MyJFrame.getInstance().getClipboard().getContents (MyJFrame.getInstance());
        if ((clipboardContent != null) &&
                (clipboardContent.isDataFlavorSupported (DocumentSelection.dataFlavor))) {
            try {
                Document document = null;
                Document temp = (Document) clipboardContent.getTransferData (DocumentSelection.dataFlavor);
                document = temp.clone();
                this.add(document);
                document.addObserver(this);
            }catch (Exception ex) {
                ex.printStackTrace ();
            }
        }
    }

}
