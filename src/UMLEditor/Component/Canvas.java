package Component;

import Object.*;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

public class Canvas extends JPanel {
    public Canvas(ButtonToolBar toolBar) {
        MouseAdapter adapter = new MouseAdapter() {
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

    @Override
    public void paint(Graphics graph) {
        strategy().paint(graph);
    }

    private Color clearBoard;
    private Color defaultBoard;
    private ButtonToolBar toolBar;
    private ArrayList<BaseObject> BaseObjects;
    private Strategy strategy = null;
    
    public Strategy strategy() {
        if (strategy == null) 
            strategy = new Strategy();
        return strategy;
    }

    public class Strategy {
        public Strategy() {
            selectingObjects = new ArrayList<BaseObject>();
        }

        public void paint(Graphics graph) {
            graph.setColor(clearBoard);
            graph.fillRect(0, 0, getSize().width, getSize().height);
            graph.setColor(defaultBoard);

            if (selectAreaExist()) {
                int lx = originPoint.x;
                int ly = originPoint.y;
                int offsetx = offsetPoint.x - lx;
                int offsety = offsetPoint.y - ly;

                graph.drawRoundRect(lx, ly, offsetx, offsety, 20, 20);
                graph.setColor(new Color(145, 209, 181));
                graph.fillRoundRect(lx, ly, offsetx, offsety, 20, 20);
                graph.setColor(defaultBoard);
            }

            for (int i = 0; i < BaseObjects.size(); i++) {
                BaseObjects.get(i).draw(graph);
            }
        }

        public Strategy addSelectableObject(BaseObject obj) {
            BaseObjects.add(obj);
            repaint();
            return this;
        }
        
        public Strategy setSelectArea(boolean exist) {
            selectArea = exist;
            repaint();
            return this;
        }

        private boolean selectAreaExist() {
            return selectArea;
        }

        public boolean overlapObject(Point p) {
            boolean overlap = false;
            for (BaseObject object: BaseObjects) {
                if (object.contain(p)) {
                    overlap = true;
                    topSelectObject = object;
                }
            }
            return overlap;
        }

        public void selectTopObject() {
            topSelectObject.select(true);
            repaint();
        }

        public Strategy clearSelectObject() {
            if (topSelectObject != null) {
                topSelectObject.select(false);
            }

            for (BaseObject selectobj: selectingObjects) {
                selectobj.select(false);
            }
            selectingObjects.clear();
            repaint();
            return this;
        }

        public void selectObjectByArea(Point origin, Point offset) {
            originPoint = origin;
            offsetPoint = offset;

            for (BaseObject object: BaseObjects) {
                if (object.contain(origin, offset)) {
                    object.select(true);
                    selectingObjects.add(object);
                }
            }
            repaint();
        }

        private Point originPoint;
        private Point offsetPoint;
        private boolean selectArea;
        private BaseObject topSelectObject;
        private ArrayList<BaseObject> selectingObjects;
    }
}
