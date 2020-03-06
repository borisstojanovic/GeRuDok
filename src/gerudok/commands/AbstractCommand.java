package gerudok.commands;

//deklarise interfejs za izvrsavanje operacija
public abstract class AbstractCommand {

    public abstract void doCommand();
    public abstract void undoCommand();
}
