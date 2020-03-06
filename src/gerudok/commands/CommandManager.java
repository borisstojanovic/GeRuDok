package gerudok.commands;

import gerudok.app.MyJFrame;

import java.util.ArrayList;

public class CommandManager {

    //lista koja predstavlja stek na kome se nalaze izvrsene komande
    private ArrayList<AbstractCommand> commands = new ArrayList<AbstractCommand>();
    //redni broj komande za undo i redu operaciju - pokazivac na stek
    private int currentCommand = 0;

    //dodaje komandu na stek i poziva izvrsavanje komande
    //smesta objekte AddDeviceCommand na stek
    public void addCommand(AbstractCommand command){
        while(currentCommand < commands.size()){
            commands.remove(currentCommand);
        }
        commands.add(command);
        doCommand();
    }

    //metoda koja poziva izvrsavanje konkretne komande
    //ponovaljamo komandu u redosledu u kome su izvrsene
    //pokazivac steka currentCommand se pomera unapred
    public void doCommand(){
        if(currentCommand < commands.size()){
            commands.get(currentCommand++).doCommand();//uzima metodu iz klase AddDeviceCommand
            MyJFrame.getInstance().getActionManager().getUndoAction().setEnabled(true);
        }
        if(currentCommand == commands.size()){
            MyJFrame.getInstance().getActionManager().getRedoAction().setEnabled(false);
        }
    }

    //metoda koja poziva redo
    //ponistavamo komande u redosledu u kome su izvrsene
    //pokazivac steka currentCommand se pomera unazad
    public void undoCommand(){
        if(currentCommand > 0){
            MyJFrame.getInstance().getActionManager().getRedoAction().setEnabled(true);
            commands.get(--currentCommand).undoCommand();//uzima metodu iz klase AddDeviceCommand
        }
        if(currentCommand==0){
            MyJFrame.getInstance().getActionManager().getUndoAction().setEnabled(false);
        }
    }

}
