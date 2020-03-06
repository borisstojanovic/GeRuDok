package gerudok.state;

import java.awt.event.MouseEvent;


// opisuje ponašanje zajedničko za sva stanja
// sadrži sve metode bez njihove implementacije
public abstract class State {

    public abstract void mousePressed(MouseEvent e);
    public abstract void mouseDragged(MouseEvent e);
    public abstract void mouseReleased(MouseEvent e);
    public abstract void mouseMoved(MouseEvent e);

}
