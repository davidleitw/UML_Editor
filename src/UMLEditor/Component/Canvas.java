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

            if (draggingExist() && !selecting) {
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

        private void debugMode() {
            System.out.printf("origin = (%d, %d) - offset = (%d, %d) - dragging = %b - selecting = %b\n", originPoint.x,
                    originPoint.y, offsetPoint.x, offsetPoint.y, dragging, selecting);
        }

        public Strategy addSelectableObject(BaseObject obj) {
            baseObjects.add(obj);
            repaint();
            return this;
        }

        public Strategy mouseDragging(boolean exist) {
            dragging = exist;
            return this;
        }

        private boolean draggingExist() {
            return dragging;
        }

        public void selectMousePressed(Point p) {
            clearSelectObject();
            // if (overlapObject(p)) {
            // selecting = true;
            // setObjectSelectTag();
            // }
            selecting = overlapObject(p);
            repaint();
        }

        public void selectMouseDragged(Point origin, Point offset) {
            mouseDragging(true);
            originPoint = origin;
            offsetPoint = offset;

            if (selecting) {
                moveSelectingObjects();
                debugMode();
                repaint();
                return;
            }

            clearSelectObject();
            for (BaseObject obj : baseObjects) {
                if (obj.contain(origin, offset)) {
                    selectingObjects.add(obj);
                }
            }
            setObjectSelectTag();
            debugMode();
            repaint();
        }

        public void selectMouseReleased() {
            originPoint = null;
            offsetPoint = null;
            mouseDragging(false);
            repaint();
        }

        public boolean overlapObject(Point p) {
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
            return selecting;
        }

        private void setObjectSelectTag() {
            for (BaseObject selectobj : selectingObjects) {
                selectobj.select(true);
            }
        }

        public Strategy clearSelectObject() {
            selecting = false;
            for (BaseObject selectobj : selectingObjects) {
                selectobj.select(false);
            }
            selectingObjects.clear();
            return this;
        }

        public void selectObjectByArea(Point origin, Point offset) {
            originPoint = origin;
            offsetPoint = offset;
            if (selecting) {
                moveSelectingObjects();
            } else {
                clearSelectObject();
                for (BaseObject object : baseObjects) {
                    if (object.contain(origin, offset)) {
                        selectingObjects.add(object);
                    }
                }
                setObjectSelectTag();
            }
            repaint();
        }

        private void moveSelectingObjects() {
            for (BaseObject selectobj : selectingObjects) {
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
