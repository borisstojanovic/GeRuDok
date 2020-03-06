package gerudok.actions;

import gerudok.app.MyJFrame;
import gerudok.errorhandler.ErrorHandlerSimpleFactory;
import gerudok.errorhandler.ExceptionEnum;
import gerudok.gui.editor.IEditor;
import gerudok.gui.editor.ImageEditor;
import gerudok.gui.editor.TextEditor;
import gerudok.model.Page;
import gerudok.model.elements.ContentTypeEnum;
import gerudok.model.elements.SlotDevice;
import gerudok.model.elements.SlotElement;
import gerudok.view.PageView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ShowEditorAction extends MyAbstractAction {

    public ShowEditorAction(){
        putValue(NAME, "Editor");
        putValue(SMALL_ICON, loadIcon("Programming-Edit-Property-icon.png"));
        putValue(SHORT_DESCRIPTION, "Editor");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        PageView pageView = MyJFrame.getInstance().getWorkspaceView().getCurrentProjectView().getSelectedDocumentView().getFocusPage();
        if(pageView == null){
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.NO_SELECTED_PAGE);
            return;
        }
        Page page = pageView.getPage();

        JPopupMenu popup = new JPopupMenu();
        popup.add(MyJFrame.getInstance().getActionManager().getEditorImageAction());
        popup.add(MyJFrame.getInstance().getActionManager().getEditorTextAction());

        if(page.getSelectionModel().getSelectionListSize() != 1){
            ErrorHandlerSimpleFactory.generateError(ExceptionEnum.MULTIPLE_SELECTION);
            return;
        }
        SlotElement selected = page.getSelectionModel().getElementFromSelectionListAt(0);
        Point position = (Point) ((SlotDevice)selected).getPosition();
        if(selected.getContent().getContentTypeEnum() == null){
            popup.show(pageView, position.x, position.y);
        }

        IEditor editor = createEditor(selected);
        if(editor == null)return;
        editor.showEditor();
    }

    private IEditor createEditor(SlotElement selected) {
        ContentTypeEnum contentTypeEnum = selected.getContent().getContentTypeEnum();
        IEditor editor = null;
        if(contentTypeEnum == ContentTypeEnum.IMAGE){
            editor = new ImageEditor(selected);
        }else if(contentTypeEnum == ContentTypeEnum.TEXT){
            editor = new TextEditor(selected);
        }
        return editor;
    }

}
