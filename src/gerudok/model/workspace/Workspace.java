package gerudok.model.workspace;

import gerudok.model.Document;
import gerudok.model.Project;
import gerudok.observer.MyObservable;
import gerudok.observer.MyObserver;
import gerudok.observer.ObserverEnum;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Workspace extends DefaultMutableTreeNode implements MyObservable, MyObserver, Serializable {

    private DocumentContainer documentContainer;
    private List<Project> projects;
    private transient List<MyObserver> observers;
    private File workspaceFile;
    private transient boolean changed;

    public Workspace(){
        super();

        documentContainer = new DocumentContainer();
        documentContainer.addObserver(this);
        super.add(documentContainer);

        projects = new ArrayList<>();
        changed = false;
    }

    @Override
    public void add(MutableTreeNode newChild){
        if(projects == null){
            projects = new ArrayList<>();
        }
        if(newChild instanceof Project) {
            Project project = (Project) newChild;
            if (!projects.contains(project)) {
                projects.add(project);
                super.add(project);

                project.setName(project.toString());
                project.addObserver(this);
                notifyObservers(project, ObserverEnum.ADD);
                this.changed = true;
            }
        }
    }

    public void removeProject(Project project){
        if(project != null && projects != null && !projects.isEmpty()){
            notifyObservers(project, ObserverEnum.REMOVE);
            projects.remove(project);

            for (int i = 0; i < project.getDocumentCount(); i++){
                boolean flag = true;
                if(project.getDocument(i).getParent().equals(project)) {
                    for (Project project1 : projects) {
                        if(project1.getDocumentCount() != 0 && project1.getDocuments().contains(project.getDocument(i))){
                            project.getDocument(i).setParent(project1);
                            flag = false;
                            break;
                        }
                    }
                    if(flag) {
                        documentContainer.add(project.getDocument(i));
                    }
                }
            }
            super.remove(project);
            this.changed = true;
        }
    }

    public void removeSharedDocument(Document document){
        for (Project project : projects) {
            if(document.getParent().equals(project))continue;

            if(project.getDocumentCount() != 0 && project.getDocuments().contains(document)){
                document.setParent(project);
                return;
            }
        }
    }

    public List<Project> getProjects() {
        return projects;
    }

    public String toString(){
        return "Workspace";
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
        if(observers != null && !observers.isEmpty() && var1 != null){
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

    public File getWorkspaceFile(){
        return workspaceFile;
    }

    public void setWorkspaceFile(File workspaceFile) {
        this.workspaceFile = workspaceFile;
    }

    public int getProjectCount(){
        return projects.size();
    }

    public Project getProject(int i){
        return projects.get(i);
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    @Override
    public void update(Object var1, ObserverEnum changeType) {
        this.setChanged(true);
        if(var1 instanceof Document && changeType == ObserverEnum.REMOVE){
            removeSharedDocument((Document) var1);
        }
    }
}
