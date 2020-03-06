package gerudok.view;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.model.Project;
import gerudok.model.workspace.Workspace;
import gerudok.observer.MyObserver;
import gerudok.observer.ObserverEnum;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorkspaceView extends JPanel implements MyObserver {

    private Workspace workspace;
    private ProjectView currentProjectView;
    private List<ProjectView> projectViews;

    public WorkspaceView(Workspace workspace){

        this.workspace = workspace;
        initialiseProjectViews();
        this.workspace.addObserver(this);
    }


    //Samo menja projectView koji je trenutno u panelu
    public void changeProjectView(Project project){
        if(currentProjectView != null && currentProjectView.getProject().equals(project))return;
        for (ProjectView projectView : projectViews) {
            if(project.equals(projectView.getProject())){
                this.removeAll();
                currentProjectView = projectView;
                this.add(currentProjectView);
                break;
            }
        }
        MyJFrame.getInstance().revalidate();
        repaint();
        MyJFrame.getInstance().setVisible(true);
    }


    //pravi novi project view i ubacuje ga u listu ali ga ne stavlja u panel
    private void initialiseProjectView(Project project) {

        ProjectView projectView = new ProjectView(project);
        this.projectViews.add(projectView);
    }

    //pravi view za svaki projekat iz workspace-a
    private void initialiseProjectViews(){
        if(projectViews == null){
            projectViews = new ArrayList<>();
        }
        for(int i = 0; i < workspace.getProjectCount(); i++){
            Project p = workspace.getProject(i);
            this.initialiseProjectView(p);
        }
    }

    @Override
    public void update(Object var1, ObserverEnum changeType) {
        if(var1 instanceof Project){
            Project project = (Project)var1;

            if(changeType == ObserverEnum.REMOVE) {
                if (projectViews != null && !projectViews.isEmpty()) {
                    Iterator i = projectViews.iterator();
                     while(i.hasNext()){
                         ProjectView projectView = (ProjectView) i.next();

                        if (projectView.getProject().equals(project)) {
                            this.remove(projectView);
                            i.remove();
                        }
                    }
                }
            }else if(changeType == ObserverEnum.ADD){
                initialiseProjectView(project);
            }
        }
        MyJFrame.getInstance().revalidate();
        MyJFrame.getInstance().repaint();
    }


    public void closeAllProjectViewTabs(){
        if(currentProjectView != null){
            currentProjectView.closeAllTabs();
            return;
        }
    }

    public void closeProjectViewTab() {
        //proverava da li postoji trenutni project view
        //ako postoji poziva njegovu metodu za zatvqranje tabova
        //ako ne postoji izbacuje poruku korisniku
        if(currentProjectView != null) {
            currentProjectView.closeTab();
            return;
        }else{
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.NO_SELECTED_TAB);
            return;
        }
    }

    public ProjectView getCurrentProjectView() {
        return currentProjectView;
    }
}
