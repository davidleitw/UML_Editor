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
        this.toolBar = toolBar;
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

    private ButtonToolBar toolBar;
    private Strategy strategy = null;

    public Strategy strategy() {
        if (strategy == null)
            strategy = new Strategy();
        return strategy;
    }

    public class Strategy {
        public Strategy() {
            LineObjects = new ArrayList<Line>();
            BasicObjects = new ArrayList<BasicObject>();
            selectingObjects = new ArrayList<BasicObject>();

            cleanBackground = Color.WHITE;
            defaultBackground = new Color(16777216);
            draggingBackground = new Color(145, 209, 181);
        }

        public void paint(Graphics graph) {
            graph.setColor(cleanBackground);
            graph.fillRect(0, 0, getSize().width, getSize().height);
            graph.setColor(defaultBackground);

            if (!selecting && mouseDragging()) {
                int lx = originPoint.x;
                int ly = originPoint.y;
                int offsetx = offsetPoint.x - lx;
                int offsety = offsetPoint.y - ly;

                graph.drawRoundRect(lx, ly, offsetx, offsety, 20, 20);
                graph.setColor(draggingBackground);
                graph.fillRoundRect(lx, ly, offsetx, offsety, 20, 20);
                graph.setColor(defaultBackground);
            }

            for (BasicObject obj : BasicObjects) {
                obj.draw(graph);
            }

            for (Line line : LineObjects) {
                line.draw(graph);
            }
        }

        private void setOriginPoint(Point p) {
            originPoint = p;
        }

        private void setOffsetPoint(Point p) {
            offsetPoint = p;
        }

        private BasicObject pressedOverlapObject(Point p) {
            for (BasicObject object : BasicObjects) {
                if (object.contain(p)) {
                    System.out.println("Find overlap object");
                    return object;
                }
            }
            return null;
        }

        public void selectMousePressed(Point p) {
            clearSelectObject();
            setOriginPoint(p);
            setOffsetPoint(p);
            BasicObject obj = pressedOverlapObject(p);
            if (obj != null) {
                selecting = true;
                selectingObjects.add(obj);
                setObjectSelectTag();
            }
            repaint();
        }

        public void selectMouseDragged(Point offset) {
            setmouseDragging(true);
            int ox = offset.x - offsetPoint.x;
            int oy = offset.y - offsetPoint.y;
            setOffsetPoint(offset);

            if (selecting) {
                moveSelectingObjects(ox, oy);
                repaint();
                return;
            }

            clearSelectObject();
            for (BasicObject obj : BasicObjects) {
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

        private void setObjectSelectTag() {
            for (BasicObject selectobj : selectingObjects) {
                selectobj.select(true);
            }
        }

        private void setmouseDragging(boolean exist) {
            dragging = exist;
        }

        private boolean mouseDragging() {
            return dragging;
        }

        public void addSelectableObject(BasicObject obj) {
            BasicObjects.add(obj);
            repaint();
        }

        private void clearSelectObject() {
            selecting = false;
            for (BasicObject selectobj : selectingObjects) {
                selectobj.select(false);
            }
            selectingObjects.clear();
        }

        private void moveSelectingObjects(int offsetx, int offsety) {
            for (BasicObject selectobj : selectingObjects) {
                selectobj.move(offsetx, offsety);
            }
        }

        public void createLineMousePressed(Point p) {
            BasicObject source = pressedOverlapObject(p);
            if (source == null) {
                return;
            }
            int closestIndex = source.getClosestPortIndex(p);
            creatingLine = new Line(source, closestIndex);
        }

        public void createLineMouseReleased(Point p) {
            BasicObject destination = pressedOverlapObject(p);
            if (creatingLine == null || destination == null) {
                System.out.println("miss: "+creatingLine+destination);
                creatingLine = null;
                return;
            }
            int closeIndex = destination.getClosestPortIndex(p);
            System.out.println(closeIndex);
            creatingLine.setDestination(destination, closeIndex);
            LineObjects.add(creatingLine);
            creatingLine = null;
            repaint();
        }

        private boolean dragging;
        private boolean selecting;
        private Point originPoint;
        private Point offsetPoint;
        private Line creatingLine;
        private Color cleanBackground;
        private Color defaultBackground;
        private Color draggingBackground;
        private ArrayList<Line> LineObjects;
        private ArrayList<BasicObject> BasicObjects;
        private ArrayList<BasicObject> selectingObjects;
    }
}
