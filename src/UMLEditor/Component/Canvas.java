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
    private Strategy strategy = null;

    public Strategy strategy() {
        if (strategy == null)
            strategy = new Strategy();
        return strategy;
    }

    public class Strategy {
        public Strategy() {
            selectingObjects = new ArrayList<BaseObject>();
            baseObjects = new ArrayList<BaseObject>();
        }

        public void paint(Graphics graph) {
            graph.setColor(clearBoard);
            graph.fillRect(0, 0, getSize().width, getSize().height);
            graph.setColor(defaultBoard);

            if (mouseDragging() && !selecting) {
                int lx = originPoint.x;
                int ly = originPoint.y;
                int offsetx = offsetPoint.x - lx;
                int offsety = offsetPoint.y - ly;

                graph.drawRoundRect(lx, ly, offsetx, offsety, 20, 20);
                graph.setColor(new Color(145, 209, 181));
                graph.fillRoundRect(lx, ly, offsetx, offsety, 20, 20);
                graph.setColor(defaultBoard);
            }

            for (int i = 0; i < baseObjects.size(); i++) {
                baseObjects.get(i).draw(graph);
            }
        }

        public void addSelectableObject(BaseObject obj) {
            baseObjects.add(obj);
            repaint();
        }

        private void setmouseDragging(boolean exist) {
            dragging = exist;
        }

        private boolean mouseDragging() {
            return dragging;
        }

        private void setOriginPoint(Point p) {
            originPoint = p;
        }

        private void setOffsetPoint(Point p) {
            offsetPoint = p;
        }

        public void selectMousePressed(Point p) {
            clearSelectObject();
            setOriginPoint(p);
            overlapObject(p);
            repaint();
        }

        public void selectMouseDragged(Point offset) {
            setmouseDragging(true);
            setOffsetPoint(offset);

            if (selecting) {
                moveSelectingObjects();
                repaint();
                return;
            }

            clearSelectObject();
            for (BaseObject obj : baseObjects) {
                if (obj.contain(originPoint, offsetPoint)) {
                    selectingObjects.add(obj);
                }
            }
            setObjectSelectTag();
            repaint();
        }

        public void selectMouseReleased() {
            setOriginPoint(null);
            setOffsetPoint(null);
            setmouseDragging(false);
            repaint();
        }

        private void overlapObject(Point p) {
            BaseObject topSelectObject = null;
            for (BaseObject object : baseObjects) {
                if (object.contain(p)) {
                    selecting = true;
                    topSelectObject = object;
                }
            }
            if (selecting) {
                selectingObjects.add(topSelectObject);
            }
            setObjectSelectTag();
        }

        private void setObjectSelectTag() {
            for (BaseObject selectobj : selectingObjects) {
                selectobj.select(true);
            }
        }

        private void clearSelectObject() {
            selecting = false;
            for (BaseObject selectobj : selectingObjects) {
                selectobj.select(false);
            }
            selectingObjects.clear();
        }

        private void moveSelectingObjects() {
            for (BaseObject selectobj : selectingObjects) {
                // selectobj.move(offsetPoint.x - originPoint.x, offsetPoint.y - originPoint.y);
                selectobj.move(offsetPoint);
            }
        }

        private boolean dragging;
        private boolean selecting;
        private Point originPoint;
        private Point offsetPoint;
        private ArrayList<BaseObject> baseObjects;
        private ArrayList<BaseObject> selectingObjects;
    }
}
