package gerudok.actions.manager;

import gerudok.actions.*;

public class ActionManager {

    private AboutAction aboutAction;

    private RestoreNodeAction restoreNodeAction;

    private CloseAction closeAction;
    private CloseAllAction closeAllAction;

    private DeleteNodeAction deleteNodeAction;

    private NewNodeAction newNodeAction;

    private RenameNodeAction renameNodeAction;

    private SaveProjectAsAction saveProjectAsAction;
    private SaveProjectAction saveProjectAction;
    private OpenProjectAction openProjectAction;

    private SaveWorkspaceAction saveWorkspaceAction;
    private ChangeWorkspaceAction changeWorkspaceAction;

    private TriangleAction triangleAction;
    private CircleAction circleAction;
    private RectangleAction rectangleAction;
    private RotateAction rotateAction;
    private ResizeAction resizeAction;
    private SelectAction selectAction;
    private MoveAction moveAction;
    private RemoveDeviceAction removeDeviceAction;

    private UndoAction undoAction;
    private RedoAction redoAction;

    private CopyAction copyAction;
    private PasteAction pasteAction;
    private CutAction cutAction;
    private ShowEditorAction showEditorAction;

    private CopyNodeAction copyNodeAction;
    private CutNodeAction cutNodeAction;
    private PasteNodeAction pasteNodeAction;
    private ShareNodeAction shareNodeAction;

    private EditorImageAction editorImageAction;
    private EditorTextAction editorTextAction;

    public ActionManager() {
        removeDeviceAction = new RemoveDeviceAction();
        restoreNodeAction = new RestoreNodeAction();
        cutNodeAction = new CutNodeAction();
        shareNodeAction = new ShareNodeAction();
        copyNodeAction = new CopyNodeAction();
        pasteNodeAction = new PasteNodeAction();
        selectAction = new SelectAction();
        resizeAction = new ResizeAction();
        rotateAction = new RotateAction();
        aboutAction = new AboutAction();
        closeAction = new CloseAction();
        closeAllAction = new CloseAllAction();
        deleteNodeAction = new DeleteNodeAction();
        newNodeAction = new NewNodeAction();
        renameNodeAction = new RenameNodeAction();
        saveProjectAsAction = new SaveProjectAsAction();
        saveProjectAction = new SaveProjectAction();
        openProjectAction = new OpenProjectAction();
        saveWorkspaceAction = new SaveWorkspaceAction();
        changeWorkspaceAction = new ChangeWorkspaceAction();
        triangleAction = new TriangleAction();
        circleAction = new CircleAction();
        rectangleAction = new RectangleAction();
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        copyAction = new CopyAction();
        pasteAction = new PasteAction();
        cutAction = new CutAction();
        moveAction = new MoveAction();
        showEditorAction = new ShowEditorAction();
        editorImageAction = new EditorImageAction();
        editorTextAction = new EditorTextAction();
    }

    public RemoveDeviceAction getRemoveDeviceAction() {
        return removeDeviceAction;
    }

    public RestoreNodeAction getRestoreNodeAction() {
        return restoreNodeAction;
    }

    public CutNodeAction getCutNodeAction() {
        return cutNodeAction;
    }

    public ShareNodeAction getShareNodeAction() {
        return shareNodeAction;
    }

    public CopyNodeAction getCopyNodeAction() {
        return copyNodeAction;
    }

    public PasteNodeAction getPasteNodeAction() {
        return pasteNodeAction;
    }

    public MoveAction getMoveAction() {
        return moveAction;
    }

    public ShowEditorAction getShowEditorAction() {
        return showEditorAction;
    }

    public SelectAction getSelectAction() {
        return selectAction;
    }

    public void setSelectAction(SelectAction selectAction) {
        this.selectAction = selectAction;
    }

    public ResizeAction getResizeAction() {
        return resizeAction;
    }

    public void setResizeAction(ResizeAction resizeAction) {
        this.resizeAction = resizeAction;
    }

    public RotateAction getRotateAction() {
        return rotateAction;
    }

    public void setRotateAction(RotateAction rotateAction) {
        this.rotateAction = rotateAction;
    }

    public AboutAction getAboutAction() {
        return aboutAction;
    }

    public void setAboutAction(AboutAction aboutAction) {
        this.aboutAction = aboutAction;
    }

    public CloseAction getCloseAction() {
        return closeAction;
    }

    public void setCloseAction(CloseAction closeAction) {
        this.closeAction = closeAction;
    }

    public CloseAllAction getCloseAllAction() {
        return closeAllAction;
    }

    public void setCloseAllAction(CloseAllAction closeAllAction) {
        this.closeAllAction = closeAllAction;
    }

    public DeleteNodeAction getDeleteNodeAction() {
        return deleteNodeAction;
    }

    public void setDeleteNodeAction(DeleteNodeAction deleteNodeAction) {
        this.deleteNodeAction = deleteNodeAction;
    }

    public NewNodeAction getNewNodeAction() {
        return newNodeAction;
    }

    public void setNewNodeAction(NewNodeAction newNodeAction) {
        this.newNodeAction = newNodeAction;
    }

    public RenameNodeAction getRenameNodeAction() {
        return renameNodeAction;
    }

    public void setRenameNodeAction(RenameNodeAction renameNodeAction) {
        this.renameNodeAction = renameNodeAction;
    }

    public SaveProjectAction getSaveProjectAction() {
        return saveProjectAction;
    }

    public void setSaveProjectAction(SaveProjectAction saveProjectAction) {
        this.saveProjectAction = saveProjectAction;
    }

    public OpenProjectAction getOpenProjectAction() {
        return openProjectAction;
    }

    public void setOpenProjectAction(OpenProjectAction openProjectAction) {
        this.openProjectAction = openProjectAction;
    }

    public SaveProjectAsAction getSaveProjectAsAction() {
        return saveProjectAsAction;
    }

    public void setSaveProjectAsAction(SaveProjectAsAction saveProjectAsAction) {
        this.saveProjectAsAction = saveProjectAsAction;
    }

    public SaveWorkspaceAction getSaveWorkspaceAction() {
        return saveWorkspaceAction;
    }

    public void setSaveWorkspaceAction(SaveWorkspaceAction saveWorkspaceAction) {
        this.saveWorkspaceAction = saveWorkspaceAction;
    }

    public ChangeWorkspaceAction getChangeWorkspaceAction() {
        return changeWorkspaceAction;
    }

    public void setChangeWorkspaceAction(ChangeWorkspaceAction changeWorkspaceAction) {
        this.changeWorkspaceAction = changeWorkspaceAction;
    }

    public CircleAction getCircleAction() {
        return circleAction;
    }

    public RectangleAction getRectangleAction() {
        return rectangleAction;
    }

    public TriangleAction getTriangleAction() {
        return triangleAction;
    }

    public RedoAction getRedoAction() {
        return redoAction;
    }

    public UndoAction getUndoAction() {
        return undoAction;
    }

    public CopyAction getCopyAction() {
        return copyAction;
    }

    public CutAction getCutAction() {
        return cutAction;
    }

    public PasteAction getPasteAction() {
        return pasteAction;
    }

    public EditorImageAction getEditorImageAction() {
        return editorImageAction;
    }

    public EditorTextAction getEditorTextAction() {
        return editorTextAction;
    }
}
