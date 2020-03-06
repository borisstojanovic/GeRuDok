package gerudok.view;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.model.Document;
import gerudok.model.Project;
import gerudok.observer.MyObserver;
import gerudok.observer.ObserverEnum;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProjectView extends JPanel implements MyObserver {

    private Project project;
    private JLabel labelTitle;
    private JTabbedPane jTabbedPane;
    private List<DocumentView> documentViewList;


    public ProjectView(Project project){
        this.project = project;
        this.project.addObserver(this);
        documentViewList = new ArrayList<>();

        jTabbedPane = new JTabbedPane();
        initialiseJTabbedPane();

        labelTitle = new JLabel();
        labelTitle.setText(project.toString());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        labelTitle.setPreferredSize(new Dimension(1000,50));


        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(labelTitle);
        this.add(jTabbedPane);
    }


    public Project getProject() {
        return project;
    }

    public void initialiseJTabbedPane() {
        jTabbedPane.removeAll();
        documentViewList.clear();
        int n = project.getChildCount();
        for(int i = 0; i < n; i++){
            initialiseDocumentView(project.getDocument(i));
        }
    }

    //pravi novi documentView i dodaje ga u listu i u tab
    private void initialiseDocumentView(Document document){
        if(documentViewList == null){
            documentViewList = new ArrayList<>();
        }
        DocumentView documentView = new DocumentView(document);
        jTabbedPane.addTab(documentView.getName(), documentView);
        jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount() - 1, documentView.getLabelName());
        documentViewList.add(documentView);
    }

    public JTabbedPane getJTabbedPane() {
        return jTabbedPane;
    }

    public void setSelectedTab(Document document){
        if(document != null){
            for (DocumentView documentView : documentViewList) {
                 if (documentView.getDocument().equals(document)) {
                     try {
                         jTabbedPane.setSelectedComponent(documentView);
                         return;
                     }
                     //ako tab nije u jTabbedPane upada u catch i dodaje tab u pane
                     catch (IllegalArgumentException e){
                         jTabbedPane.addTab(documentView.getName(), documentView);
                         jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount()-1,
                                 documentView.getLabelName());
                         jTabbedPane.setSelectedComponent(documentView);
                         return;
                     }
                }
            }
        }
    }

    //samo zatvara tab ali ne brise documentView iz liste
    public void closeTab() {
        //nadjem selektovani tab i uklonim ga iz view-a
        int index = jTabbedPane.getSelectedIndex();

        //ako postoji index ce biti veci ili jednak nuli
        if (index >= 0) {
            jTabbedPane.removeTabAt(index);
        } else {
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.NO_SELECTED_TAB);
            return;
        }
    }

    public void closeAllTabs() {
        this.jTabbedPane.removeAll();
    }

    @Override
    public void update(Object var1, ObserverEnum changeType) {

        //ako je promenjeno ime projekta
        if(var1 instanceof String && changeType == ObserverEnum.NAME_CHANGE){
            labelTitle.setText((String)var1);
        }else if(var1 instanceof Document){

            Document document = (Document)var1;

            //ako je uklonjen dokument iz liste u projektu trazim njegove documentView-ove i
            //sklanjam ih iz liste i tabova
            if(changeType == ObserverEnum.REMOVE){
                Iterator iterator = documentViewList.iterator();
                while(iterator.hasNext()) {
                    DocumentView documentView = (DocumentView) iterator.next();
                    if(document.equals(documentView.getDocument())){
                        iterator.remove();
                        jTabbedPane.remove(documentView);
                    }
                }

             //ako je dodat dokument u listu u projektu pravim novi dokumentView za taj dokument
            }else if(changeType == ObserverEnum.ADD){
                initialiseDocumentView(document);
            }
        }
        revalidate();
    }

    public DocumentView getSelectedDocumentView(){
        return (DocumentView) jTabbedPane.getSelectedComponent();
    }
}
