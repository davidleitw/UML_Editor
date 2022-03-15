package Component;

import Object.*;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

public class Canvas extends JPanel {
    public Canvas(ButtonToolBar toolBar) {
        this.adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePressedHandler(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseReleasedHandler(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseDraggedHandler(e);
            }
        };
        this.addMouseListener(adapter);
        this.addMouseMotionListener(adapter);
        

        this.toolBar = toolBar;
        this.clearBoard = Color.WHITE;
        this.defaultBoard = new Color(16777216);
        this.BaseObjects = new ArrayList<BaseObject>();
    }

    private void mousePressedHandler(MouseEvent e) {
        if (toolBar.getCurrentBtn() == null) {
            return;
        }
        toolBar.getCurrentBtn().mousePressed(this, e);
    }

    private void mouseReleasedHandler(MouseEvent e) {
        if (toolBar.getCurrentBtn() == null) {
            return;
        }
        toolBar.getCurrentBtn().mouseReleased(this, e);
    }

    private void mouseDraggedHandler(MouseEvent e) {
        if (toolBar.getCurrentBtn() == null) {
            return;
        }
        toolBar.getCurrentBtn().mouseDragged(this, e);
    }

    public void addObject(BaseObject obj) {
        BaseObjects.add(obj);
    }

    public void selectObjectByPoint(Point p) {
        int x = p.x;
        int y = p.y;
        BaseObject selectObj = null;

        for (BaseObject obj: BaseObjects) {
            if (obj.include(x, y)) {
                selectObj = obj;
            }        
        }

        if (selectObj != null) {
            // 將原先選擇的物件取消
            if (this.selectingObject != null && this.selectingObject != selectObj) {
                System.out.println("cancel selecting obj select!");
                this.selectingObject.Selected(false);
            }
            // 新選擇的物件標注
            selectObj.Selected(true);
        }
        this.selectingObject = selectObj;
        repaint();
    }

    @Override
    public void paint(Graphics graph) {
        graph.setColor(clearBoard);
        graph.fillRect(0, 0, getSize().width, getSize().height);
        graph.setColor(defaultBoard);

        if (getStrategy().getSelectAreaExist()) {
            graph.setColor(new Color(20, 20, 10));
            Graphics2D graph2D = (Graphics2D)graph;
            graph2D.setStroke(new BasicStroke(2));
            graph.drawRoundRect(getStrategy().originPoint.x, 
                           getStrategy().originPoint.y,
                           getStrategy().offsetX,
                           getStrategy().offsetY, 20, 20);
            
            graph.setColor(new Color(145, 209, 181));
            graph.fillRoundRect(getStrategy().originPoint.x, 
                           getStrategy().originPoint.y,
                           getStrategy().offsetX,
                           getStrategy().offsetY, 20, 20);
            graph.setColor(defaultBoard);
        }

        for (int i = 0; i < BaseObjects.size(); i++) {
            BaseObjects.get(i).draw(graph);
        }

    }
    
    private Color clearBoard;
    private Color defaultBoard;

    public MouseAdapter adapter; 
    private BaseObject selectingObject;
    private ArrayList<BaseObject> BaseObjects;
    private ButtonToolBar toolBar;
    private Strategy strategy = null;
    
    
    public Strategy getStrategy() {
        if (strategy == null) 
            strategy = new Strategy();
        return strategy;
    }

    public class Strategy {
        public void addSelectableObject(BaseObject obj) {
            BaseObjects.add(obj);
            repaint();
        }

        public void selectObjectByPoint(Point p) {
            int x = p.x;
            int y = p.y;
            BaseObject selectObj = null;

            for (BaseObject obj: BaseObjects) {
                if (obj.include(x, y)) {
                    selectObj = obj;
                }     
            }

            if (selectObj != null) {
                // 將原先選擇的物件取消
                if (selectingObject != null && selectingObject != selectObj) {
                    selectingObject.Selected(false);
                }
                // 新選擇的物件標注
                selectObj.Selected(true);
            }
            selectingObject = selectObj;
            repaint();
        }
        
        private boolean selectArea;
        public void setSelectArea(boolean exist) {
            selectArea = exist;
        }

        public boolean getSelectAreaExist() {
            return selectArea;
        }

        private Point originPoint;
        private int offsetX;
        private int offsetY;
        public void selectObjectByArea(Point origin, Point p) {
            originPoint = origin;
            offsetX = p.x - originPoint.x;
            offsetY = p.y - originPoint.y;
            repaint();
        }
    }
}
