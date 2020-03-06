package gerudok.errorhandler;

import gerudok.app.MyJFrame;

import javax.swing.*;

public abstract class ErrorHandlerSimpleFactory {

    public static void generateError(ExceptionEnum exceptionEnum){
        switch (exceptionEnum){
            case NO_SELECTED_DELETE:
                JOptionPane.showMessageDialog(MyJFrame.getInstance(),
                        "You haven't selected anything!", "No selection", JOptionPane.WARNING_MESSAGE);
                break;
            case NO_SELECTED_TAB:
                JOptionPane.showMessageDialog(MyJFrame.getInstance(),
                        "You haven't selected a tab!", "No selected tab", JOptionPane.WARNING_MESSAGE);
                break;
            case NODE_SAME_NAME:
                JOptionPane.showMessageDialog(MyJFrame.getInstance(),
                        "Name already exists.", "No selection", JOptionPane.INFORMATION_MESSAGE);
                break;
            case WORKSPACE_DELETE:
                JOptionPane.showMessageDialog(MyJFrame.getInstance(),
                        "You can't delete this item.", "Wrong selection", JOptionPane.ERROR_MESSAGE);
                break;
            case WORKSPACE_RENAME:
                JOptionPane.showMessageDialog(MyJFrame.getInstance(),
                        "This item can't be renamed.", "Wrong selection", JOptionPane.INFORMATION_MESSAGE);
                break;
            case NODE_RENAME_EMPTY:
                JOptionPane.showMessageDialog(MyJFrame.getInstance(),
                        "Name can't be empty!", "Empty name", JOptionPane.WARNING_MESSAGE);
                break;
            case NO_SELECTED_PAGE:
                JOptionPane.showMessageDialog(MyJFrame.getInstance(),
                        "Select a page!", "No selection", JOptionPane.WARNING_MESSAGE);
                break;
            case LOAD_ERROR:
                JOptionPane.showMessageDialog(MyJFrame.getInstance(), "Couldn't open the selected file.",
                        "Loading Error", JOptionPane.INFORMATION_MESSAGE);
                break;
            case CONTEXT_ERROR:
                JOptionPane.showMessageDialog(MyJFrame.getInstance(), "Context doesn't exist.", "Context error", JOptionPane.INFORMATION_MESSAGE);
                break;
            case MULTIPLE_SELECTION:
                JOptionPane.showMessageDialog(MyJFrame.getInstance(), "Please select one slot.", "Wrong selection", JOptionPane.INFORMATION_MESSAGE);
                break;
            case WRONG_SELECTED_SAVE:
                JOptionPane.showMessageDialog(MyJFrame.getInstance(), "Wrong selection", "No slection", JOptionPane.WARNING_MESSAGE);
                break;
            case NO_SELECTED_DOCUMENT:
                JOptionPane.showMessageDialog(MyJFrame.getInstance(), "Please select a document.", "Wrong selection", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
