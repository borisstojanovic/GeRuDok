package gerudok.app;

import gerudok.actions.ExitWindowAction;
import gerudok.actions.manager.ActionManager;
import gerudok.commands.CommandManager;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.gui.*;
import gerudok.model.Document;
import gerudok.model.Page;
import gerudok.model.Project;
import gerudok.model.workspace.Workspace;
import gerudok.model.workspace.WorkspaceModel;
import gerudok.view.WorkspaceView;
import javafx.scene.web.HTMLEditorSkin;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.io.*;


public class MyJFrame extends JFrame implements ClipboardOwner {

    private static MyJFrame instance = null;
    private CommandManager commandManager = new CommandManager();
    private ActionManager actionManager;
    private MyMenuBar myMenuBar;
    private MyToolBar myToolBar;
    private JScrollPane jScrollPaneLeft;
    private JPanel panelCenter;
    private MyPopupMenu myPopupMenu;
    private Palette palette;

    private WorkspaceView workspaceView;
    private WorkspaceModel workspaceModel;
    private WorkspaceTree workspaceTree;

    private Clipboard clipboard = new Clipboard("GeRuDok clipboard");

    //file u kome se cuva putanja do default workspace-a
    private File contextFile;


    private MyJFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        //Dimension screenSize = kit.getScreenSize();
        // int screenHeight = screenSize.height;
        //int screenWidth = screenSize.width;
        //this.setSize(screenWidth / 2, screenHeight / 2);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //pack();
        setSize(screenSize.width, screenSize.height);
        this.setTitle("GeRuDok");
        this.setDefaultCloseOperation(3);
        this.addWindowListener(new ExitWindowAction());
        this.setLocationRelativeTo((Component) null);
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public static MyJFrame getInstance() {
        if (instance == null) {
            instance = new MyJFrame();
            instance.initialise();
        }
        return instance;
    }

    private void initialise() {
        actionManager = new ActionManager();

        initialiseWorkspaceTree();
        initialiseGUI();

        if (contextFile == null) {
            //ako file ne postoji pravimo novi(prvo pokretanje ili obrisan file)
            new File("context").mkdir();
            contextFile = new File("context/contextFile");
        }
        int choice = contextQuestion();
        if(choice == 1) {
            initialiseContext();
        }
    }

    private void initialiseContext() {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(contextFile));
            String workspacePath = bufferedReader.readLine();
            if(workspacePath == null)return;

            ObjectInputStream os = new ObjectInputStream(new FileInputStream(new File(workspacePath)));
            Workspace workspace = (Workspace) os.readObject();
            changeWorkspaceModel(new WorkspaceModel(workspace));
            for(int i = 0; i < workspace.getProjectCount(); i++){
                Project project = workspace.getProject(i);
                project.addObserver(workspace);
                for(int j = 0; j < project.getDocumentCount(); j++){
                    Document document = project.getDocument(j);
                    document.addObserver(project);
                    for(int k = 0; k < document.getPageCount(); k++){
                        Page page = document.getPage(k);
                        page.addObserver(document);
                        for(int l = 0; l < page.getElementsCount(); l++){
                            page.getElementAt(l).addObserver(page);
                        }
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.CONTEXT_ERROR);
        }

        /*try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(contextFile));
            String workspacePath = bufferedReader.readLine();

            if (workspacePath == null) {
                return;
            }
            //postavljamo path u workspace
            workspaceModel.setWorkspaceFilePath(workspacePath);
            bufferedReader.close();

            //u ovom fajlu nalazi se lista putanja do svih projekata u workspace-u
            bufferedReader = new BufferedReader(new FileReader(new File(workspacePath)));
            String projectPath = bufferedReader.readLine();
            while (projectPath != null) {
                ObjectInputStream os = new ObjectInputStream(new FileInputStream(new File(projectPath)));
                Project p = null;
                    try {
                        p = (Project) os.readObject();
                    } catch (ClassNotFoundException e) {
                        // TODO errorhandler
                        e.printStackTrace();
                    }
                    //kada se doda u stablo preko observer-a se pravi sav potrebni view
                    MyJFrame.getInstance().getWorkspaceTree().addProject(p);
                    //u for-u se dodaje update observer
                    for (int i = 0; i < p.getDocumentCount(); i++) {
                        Document document = p.getDocument(i);
                        document.addUpdateObserver(p);
                        for (int j = 0; j < document.getPageCount(); j++) {
                            document.getPage(j).addUpdateObserver(document);
                        }
                    }
                    p.setProjectFile(new File(projectPath));
                    projectPath = bufferedReader.readLine();
                }
                bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

         */
    }

    private int contextQuestion() {
        int answ = JOptionPane.showConfirmDialog(null ,
                "Do you want to reinitialise the context?");
        if(answ == JOptionPane.YES_OPTION)
            return 1;
        else
            return 0;
    }

    private void initialiseWorkspaceTree() {
        workspaceTree = new WorkspaceTree();
        workspaceModel = new WorkspaceModel();
        workspaceTree.setModel(workspaceModel);
    }

    private void initialiseGUI() {

        myMenuBar = new MyMenuBar();
        myToolBar = new MyToolBar();
        myPopupMenu = new MyPopupMenu();
        palette = new Palette();

        palette.setFloatable(true);
        palette.setBackground(Color.LIGHT_GRAY);
        add(palette, BorderLayout.EAST);

        this.setJMenuBar(myMenuBar);
        this.getContentPane().add(myToolBar, BorderLayout.NORTH);

        panelCenter = new JPanel(new BorderLayout());
        panelCenter.setBackground(Color.WHITE);

        initialiseWorkspaceView();

        this.add(panelCenter, BorderLayout.CENTER);

        jScrollPaneLeft = new JScrollPane(workspaceTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPaneLeft.setMinimumSize(new Dimension(100, 150));
        this.add(jScrollPaneLeft);

        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jScrollPaneLeft, panelCenter);
        jScrollPaneLeft.setMinimumSize(new Dimension(100, 100));

        this.add(jSplitPane, BorderLayout.CENTER);
        jSplitPane.setDividerLocation(200);

    }

    //koristi trenutni workspaceModel da napravi workspace view
    private void initialiseWorkspaceView() {
        panelCenter.removeAll();
        Workspace workspace = (Workspace) workspaceModel.getRoot();
        workspaceView = new WorkspaceView(workspace);
        panelCenter.add(workspaceView);
        revalidate();
    }

    public WorkspaceModel getWorkspaceModel() {
        return workspaceModel;
    }

    public WorkspaceTree getWorkspaceTree() {
        return workspaceTree;
    }

    public WorkspaceView getWorkspaceView() {
        return workspaceView;
    }

    public void updateContextFile(File selectedFile) {
        String filePath = selectedFile.getPath();
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(contextFile));
            bufferedWriter.write(filePath);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeWorkspaceModel(WorkspaceModel workspaceModel) {
        this.workspaceModel = workspaceModel;
        this.workspaceTree.setModel(workspaceModel);
        initialiseWorkspaceView();
    }

    public Clipboard getClipboard() {
        return clipboard;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        System.out.println("lostOwnership");
    }

}