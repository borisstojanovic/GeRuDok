package gerudok.view;

import gerudok.app.MyJFrame;
import gerudok.model.Page;
import gerudok.model.elements.SlotDevice;
import gerudok.model.elements.SlotElement;
import gerudok.observer.MyObserver;
import gerudok.observer.ObserverEnum;
import gerudok.state.ManagerState;
import gerudok.view.painters.SlotElementPainter;
import gerudok.view.painters.handle.Handle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

public class PageView extends JPanel implements MyObserver {

    private JLabel lblName;
    private Page page;

    private ManagerState managerState = new ManagerState(this);


    private Rectangle2D selectionRectangle=new Rectangle2D.Double();
    private Point2D lastPosition = null;
    private static final int handleSize = 7;
    private static final int rotateHandleSize = 20;


    public PageView(Page page){

        this.page = page;
        initialiseView();

        this.addMouseListener(new MouseController(this));
        this.addMouseMotionListener(new MouseMotionController());

        page.addObserver(this);
    }

    private void initialiseView(){

        this.setPreferredSize(new Dimension(900,580));
        setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        setBackground(Color.WHITE);
        lblName = new JLabel(page.toString());
        this.add(lblName);
    }

    public void setPage(Page page) {
        this.page = page;
        page.addObserver(this);
        setName(page.getName());
        page.getSelectionModel().addObserver(this);
        this.lblName.setText(page.getName());
    }

    public Page getPage() {
        return page;
    }

    public ManagerState getManagerState() {
        return managerState;
    }

    public Point2D getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(Point2D lastPosition) {
        this.lastPosition = lastPosition;
    }

    @Override
    public void update(Object var1, ObserverEnum changeType) {
        if(var1 instanceof String && changeType.equals(ObserverEnum.NAME_CHANGE)){
            lblName.setText(page.toString());
            MyJFrame.getInstance().revalidate();
        }
        else if(var1 instanceof SlotDevice && changeType == ObserverEnum.DRAW){
            repaint();
        }
    }

    //prolazimo kroz kolekciju grafickih objekata i iscrtavamo ih na panelu
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        for(int i = 0; i < page.getElementsCount(); i++){
            SlotElement slotElement = page.getElementAt(i);
            SlotElementPainter slotElementPainter = slotElement.getSlotPainter();
            slotElementPainter.paint(g2, slotElement);
        }
        paintSelectionHandles(g2);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke((float)1, BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_BEVEL, 1f, new float[]{(float)3, (float)6}, 0 ));
        g2.draw(selectionRectangle);
    }

    private void paintSelectionHandles(Graphics2D g2d) {

        Iterator<SlotElement> it = page.getSelectionModel().getSelectionListIterator();
        while(it.hasNext()){
            SlotElement element =  it.next();
            if (element instanceof SlotDevice){

                SlotDevice device=(SlotDevice)element;
                if(g2d == null)return;

                g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 1f, new float[] {3f,6f}, 0));
                g2d.setPaint(Color.BLUE);

                AffineTransform affineTransform = AffineTransform.getRotateInstance(device.getAngle(),
                        device.getPosition().getX() + device.getSize().width/2.0,
                        device.getPosition().getY() + device.getSize().height/2.0);

                Shape rectangle = new Rectangle((int)device.getPosition().getX(),(int)device.getPosition().getY(),
                        (int)device.getSize().getWidth(), (int)device.getSize().getHeight());

                rectangle = affineTransform.createTransformedShape(rectangle);

                g2d.draw(rectangle);

                for(Handle e: Handle.values()){
                    paintSelectionHandle(g2d, getHandlePoint(device.getPosition(), device.getSize(), device.getAngle(), e));
                }


            }
        }
    }


    private void paintSelectionHandle(Graphics2D g2, Point2D position){
        double size = handleSize;
        g2.fill(new Rectangle2D.Double(position.getX()-size/2, position.getY()-size/2,
                size, size));
    }


    private Point2D getHandlePoint(Point2D topLeft, Dimension2D size, double angle, Handle handlePosition ){
        double x=0, y=0;

        if(handlePosition == Handle.NORTH_WEST || handlePosition == Handle.NORTH || handlePosition == Handle.NORTH_EAST){
            y = topLeft.getY();
        }
        if(handlePosition == Handle.EAST || handlePosition == Handle.WEST){
            y = topLeft.getY()+size.getHeight()/2;
        }
        if(handlePosition == Handle.SOUTH_WEST || handlePosition == Handle.SOUTH || handlePosition == Handle.SOUTH_EAST){
            y = topLeft.getY() + size.getHeight();
        }

        if(handlePosition == Handle.NORTH_WEST || handlePosition == Handle.WEST || handlePosition == Handle.SOUTH_WEST){
            x = topLeft.getX();
        }
        if(handlePosition == Handle.NORTH || handlePosition == Handle.SOUTH){
            x = topLeft.getX() + size.getWidth()/2;
        }
        if(handlePosition == Handle.NORTH_EAST || handlePosition == Handle.EAST || handlePosition == Handle.SOUTH_EAST){
            x = topLeft.getX() + size.getWidth();
        }

        //todo
        if(handlePosition == Handle.ROTATE){
            x = topLeft.getX() + size.getWidth()/2;
            y = topLeft.getY() - rotateHandleSize;
        }

        Point center = new Point((int)(topLeft.getX() + size.getWidth()/2.0), (int)(topLeft.getY() + size.getHeight()/2.0));
        Point toRotate = new Point((int)x,(int)y);
        rotatePoint(toRotate, center, angle);
        return new Point2D.Double(toRotate.getX(),toRotate.getY());
    }

    private void rotatePoint(Point point, Point pivot, double angle) {

        double s = Math.sin(angle);
        double c = Math.cos(angle);

        point.setLocation(point.getX() - pivot.getX(), point.getY() - pivot.getY());

        double newx = point.getX() * c - point.getY() * s;
        double newy = point.getX() * s + point.getY() * c;

        point.setLocation(newx + pivot.getX(), newy + pivot.getY());
    }

    public void setMouseCursor(Point2D point){

        Handle handle = getDeviceAndHandleForPoint(point);

        if(handle != null){
            Cursor cursor = null;

            switch(handle){
                case NORTH: cursor = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);break;
                case SOUTH: cursor = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);break;
                case EAST: cursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);break;
                case WEST: cursor = Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);break;
                case SOUTH_EAST: cursor = Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);break;
                case NORTH_WEST: cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);break;
                case SOUTH_WEST: cursor = Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);break;
                case NORTH_EAST: cursor = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);break;
                case ROTATE:cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
            }
            this.setCursor(cursor);
        }
        else
            this.setCursor(Cursor.getDefaultCursor());
    }

    public Handle getDeviceAndHandleForPoint(Point2D point){
        SlotElement element;

        Iterator<SlotElement> it = page.getSelectionModel().getSelectionListIterator();

        while(it.hasNext()){
            element = it.next();
            Handle toReturn = getHandleForPoint(element, point);
            if(toReturn != null){
                return toReturn;
            }
        }
        return null;
    }

    public SlotElement getElementForHandleAndPoint(Handle handle, Point location){
        SlotElement toReturn = null;

        for(int i = 0; i < page.getSelectionModel().getSelectionListSize(); i++) {
            SlotElement tmp = page.getSelectionModel().getElementFromSelectionListAt(i);
            Handle handleTmp = getHandleForPoint(tmp, location);
            if(handleTmp == null || handleTmp != handle)continue;
            else {
                toReturn = tmp;
                break;
            }
        }
        return toReturn;
    }

    private Handle getHandleForPoint(SlotElement element, Point2D point){
        for(Handle h: Handle.values()){
            if(isPointInHandle(element, point, h)){
                return h;
            }
        }
        return null;
    }

    private boolean isPointInHandle(SlotElement element, Point2D point, Handle handle){
        if (element instanceof SlotDevice){
            SlotDevice device=(SlotDevice)element;
            Point2D handleCenter = getHandlePoint(device.getPosition(), device.getSize(), device.getAngle(), handle);
            return ( (Math.abs(point.getX()-handleCenter.getX())<=(double)handleSize/2) &&
                    (Math.abs(point.getY()-handleCenter.getY())<=(double)handleSize/2) );
        }else
            return false;
    }


    public void startCircleState() {
        page.getSelectionModel().removeAllFromSelectionList();
        managerState.setCircleState();
    }

    public void startTriangleState(){
        page.getSelectionModel().removeAllFromSelectionList();
        managerState.setTriangleState();
    }

    public void startRectangleState() {
        page.getSelectionModel().removeAllFromSelectionList();
        managerState.setRectangleState();
    }

    public void startResizeState(){
        managerState.setResizeState();
    }

    public void startRotateState(){
        managerState.setRotateState();
    }

    public void startSelectState() {
        managerState.setSelectState();
    }

    public void startLassoState() {
        managerState.setLassoState();
    }

    public void startMoveState(){
        managerState.setMoveState();
    }

    public void setSelectionRectangle(Rectangle2D selectionRectangle) {
        this.selectionRectangle = selectionRectangle;
    }

    public Rectangle2D getSelectionRectangle() {
        return selectionRectangle;
    }

    private class MouseController extends MouseAdapter{
        private PageView pageView;

        public MouseController(PageView pageView) {
            this.pageView = pageView;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            lastPosition = e.getPoint();
            managerState.getCurrentState().mousePressed(e);
        }
        @Override
        public void mouseReleased(MouseEvent e){
            managerState.getCurrentState().mouseReleased(e);
        }
    }

    private class MouseMotionController extends MouseMotionAdapter{

        @Override
        public void mouseMoved(MouseEvent e) {
            managerState.getCurrentState().mouseMoved(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            managerState.getCurrentState().mouseDragged(e);
        }
    }

}
