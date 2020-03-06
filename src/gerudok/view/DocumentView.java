package gerudok.view;

import gerudok.app.MyJFrame;
import gerudok.model.Document;
import gerudok.model.Page;
import gerudok.observer.MyObserver;
import gerudok.observer.ObserverEnum;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DocumentView extends JPanel implements MyObserver {

    private Document document;
    private List<PageView> pageViewList;
    private List<PageScrollView> pageScrollViews;

    private PageView pageFocus = null;

    private JLabel labelName;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JScrollPane panelLeftScrollPane;

    public DocumentView(Document document) {

        super();

        this.document = document;
        document.addObserver(this);
        pageViewList = new ArrayList<>();
        pageScrollViews = new ArrayList<>();

        String name = new String(this.document.toString());
        setName(name);

        initialiseView();

        initialisePageViews();

        initialisePageScrollViews();

        revalidate();
    }

    private void initialisePageScrollViews() {
        panelLeft.removeAll();
        pageScrollViews = new ArrayList<>();
        for(int i = 0; i < document.getPageCount(); i++){
            PageScrollView pageScrollView = new PageScrollView(document.getPage(i));
            pageScrollViews.add(pageScrollView);
            panelLeft.add(pageScrollView);
            panelLeft.setPreferredSize(new Dimension(200, 150*pageViewList.size()+10));
            panelLeft.add(Box.createRigidArea(new Dimension(200,10)));
            MyJFrame.getInstance().revalidate();
        }

    }

    private void initialiseView(){
        this.setPreferredSize(new Dimension(500, 600));
        labelName = new JLabel(this.document.toString());
        labelName.setPreferredSize(new Dimension(100,20));

        this.setLayout(new BorderLayout());

        panelLeft = new JPanel();
        panelLeft.setPreferredSize(new Dimension(200, 500));
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.PAGE_AXIS));

        panelLeftScrollPane = new JScrollPane(panelLeft, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panelLeftScrollPane.setPreferredSize(new Dimension(220, 1000));
        panelLeftScrollPane.setBorder(new BevelBorder(2));
        panelLeftScrollPane.addMouseListener(new MouseClickController());

        panelRight = new JPanel();
        panelRight.setPreferredSize(new Dimension(700, 500));


        this.add(panelLeftScrollPane, BorderLayout.WEST);
        this.add(panelRight, BorderLayout.CENTER);


    }

    private void initialisePageViews() {
        if(pageViewList == null){
            pageViewList = new ArrayList<>();
        }
        pageViewList.clear();

        int n = this.document.getChildCount();
        for (int i = 0; i < n; i++) {
            Page page = this.document.getPage(i);
            initialisePageView(page);
        }
        MyJFrame.getInstance().revalidate();
    }

    public Document getDocument() {
        return document;
    }

    public JLabel getLabelName() {
        return labelName;
    }

    @Override
    public void update(Object var1, ObserverEnum changeType) {
        if(var1 instanceof String && changeType == ObserverEnum.NAME_CHANGE){
            this.labelName.setText((String)var1);

        }else if(var1 instanceof Page){

            Page page = (Page)var1;

            if(changeType == ObserverEnum.REMOVE){
                Iterator i = pageViewList.iterator();

                while(i.hasNext()) {
                    PageView pageView = (PageView) i.next();

                    if(pageView.getPage().equals(page)){
                        i.remove();
                        if(pageFocus == pageView){
                            panelRight.remove(pageFocus);
                            pageFocus = null;
                        }
                    }
                }

                i = pageScrollViews.iterator();

                while (i.hasNext()){
                    PageScrollView pageScrollView = (PageScrollView)i.next();
                    if(pageScrollView.getPage().equals(page)){
                        i.remove();
                        panelLeft.remove(pageScrollView);
                    }
                }

            }else if(changeType == ObserverEnum.ADD){
                initialisePageView(page);
                initialisePageScrollViews();
            }
            else if(ObserverEnum.DRAW == changeType){
                PageView pageView = new PageView(page);
                pageViewList.add(pageView);
                this.add(pageView);
            }
        }
        MyJFrame.getInstance().revalidate();
        MyJFrame.getInstance().repaint();
    }

    public void initialisePageView(Page page) {
        if(pageViewList == null){
            pageViewList = new ArrayList<>();
        }
        PageView pageView = new PageView(page);
        this.pageViewList.add(pageView);

        MyJFrame.getInstance().revalidate();
    }

    public void setFocusPage(PageView fp) {
        this.pageFocus = fp;
        panelRight.removeAll();
        panelRight.add(pageFocus);
        revalidate();
        repaint();
    }
    public PageView getFocusPage() {
        return pageFocus;
    }


    private class MouseClickController implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            Point point = mouseEvent.getPoint();
            Point position = panelLeftScrollPane.getViewport().getViewPosition();
            if(panelLeft.getComponentAt(point.x, point.y + position.y) instanceof PageScrollView){
                PageScrollView pageScrollView = (PageScrollView) panelLeft.getComponentAt(point.x, point.y + position.y);
                for (PageView pageView : pageViewList) {
                    if(pageView.getPage() == pageScrollView.getPage()){
                        setFocusPage(pageView);
                        return;
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

}
