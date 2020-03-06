package gerudok.state;

import gerudok.model.Page;
import gerudok.view.PageView;

//cuva reference za sva moguca stanja kao i trenutno aktivno stanje
public class ManagerState {

    private State currentState;
    private CircleState circleState;
    private RectangleState rectangleState;
    private TriangleState triangleState;
    private RotateState rotateState;
    private ResizeState resizeState;
    private SelectState selectState;
    private LassoState lassoState;
    private MoveState moveState;

    public ManagerState(PageView mediator){
        circleState = new CircleState(mediator);
        rectangleState = new RectangleState(mediator);
        triangleState = new TriangleState(mediator);
        rotateState = new RotateState(mediator);
        resizeState = new ResizeState(mediator);
        selectState = new SelectState(mediator);
        lassoState = new LassoState(mediator);
        moveState = new MoveState(mediator);
        currentState = selectState;
    }

    //metode koje menjaju trenutno stanje currentState
    //iz Page modela cemo pozivati start.......State i setovati ManagerStatu state
    //koji je aktiviran putem akcije (circle,triangle,rectangle)Action
    public void setCircleState() {
        currentState = circleState;
    }

    public void setRectangleState() {
        currentState = rectangleState;
    }

    public void setTriangleState() {
        currentState = triangleState;
    }

    public void setRotateState() {
        currentState = rotateState;
        System.out.println("ROTATE");
    }

    public void setResizeState() {
        currentState = resizeState;
        System.out.println("RESIZE");
    }

    public void setSelectState() {
        currentState = selectState;
        System.out.println("Select");
    }

    public void setLassoState() {
        currentState = lassoState;
    }

    public void setMoveState() {
        currentState = moveState;
    }

    public State getCurrentState() {
        return currentState;
    }

}
