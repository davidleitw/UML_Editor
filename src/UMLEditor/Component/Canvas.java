package Component;

import Object.*;

import java.util.HashSet;
import java.util.Iterator;
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
            SelectingObjects = new ArrayList<BasicObject>();

            cleanBackground = Color.WHITE;
            defaultBackground = new Color(16777216);
            draggingBackground = new Color(145, 209, 181);
        }

        public void paint(Graphics graph) {
            setObjectSelectTag();
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

            if (mouseDrawing()) {
                graph.drawLine(originPoint.x, originPoint.y, offsetPoint.x, offsetPoint.y);
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
                if (object.contain(p.x, p.y)) {
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
                SelectingObjects.add(obj);
            }
            repaint();
        }

        public void selectMouseDragged(Point offset) {
            int ox = offset.x - offsetPoint.x;
            int oy = offset.y - offsetPoint.y;
            setmouseDragging(true);
            setOffsetPoint(offset);

            if (selecting) {
                moveSelectingObjects(ox, oy);
                repaint();
                return;
            }

            clearSelectObject();
            for (BasicObject obj : BasicObjects) {
                if (obj.contained(originPoint.x, originPoint.y, offsetPoint.x - originPoint.x,
                        offsetPoint.y - originPoint.y)) {
                    SelectingObjects.add(obj);
                }
            }
            repaint();
        }

        public void selectMouseReleased() {
            setOriginPoint(null);
            setOffsetPoint(null);
            setmouseDragging(false);
            repaint();
        }

        private void setObjectSelectTag() {
            for (BasicObject selectobj : SelectingObjects) {
                selectobj.select(true);
            }
        }

        private void setmouseDragging(boolean exist) {
            dragging = exist;
        }

        private boolean mouseDragging() {
            return dragging;
        }

        private void setmouseDrawing(boolean exist) {
            drawing = exist;
        }

        private boolean mouseDrawing() {
            return drawing;
        }

        public void addSelectableObject(BasicObject obj) {
            BasicObjects.add(obj);
            repaint();
        }

        private void clearSelectObject() {
            selecting = false;
            for (BasicObject selectobj : SelectingObjects) {
                selectobj.select(false);
            }
            SelectingObjects.clear();
        }

        private void moveSelectingObjects(int offsetx, int offsety) {
            for (BasicObject selectobj : SelectingObjects) {
                selectobj.move(offsetx, offsety);
            }
        }

        public void createLineMousePressed(Point p, Line line) {
            setOriginPoint(p);
            clearSelectObject();
            BasicObject source = pressedOverlapObject(p);
            if (source == null || source instanceof GroupObject) {
                return;
            }
            line.setSource(source, source.getClosestPortIndex(p));
            creatingLine = line;
            repaint();
        }

        public void createLineMouseDragged(Point p) {
            setmouseDrawing(true);
            if (originPoint != null) {
                setOffsetPoint(p);
            }
            repaint();
        }

        public void createLineMouseReleased(Point p) {
            setmouseDrawing(false);
            BasicObject destination = pressedOverlapObject(p);
            if (creatingLine == null || destination == null || creatingLine.getSource() == destination
                    || destination instanceof GroupObject) {
                creatingLine = null;
                repaint();
                return;
            }
            creatingLine.setDestination(destination, destination.getClosestPortIndex(p));
            LineObjects.add(creatingLine);
            creatingLine = null;
            setOriginPoint(null);
            setOffsetPoint(null);
            repaint();
        }

        public boolean canChangeClassName() {
            return (SelectingObjects.size()) == 1 && (SelectingObjects.get(0) instanceof ClassObject);
        }

        public boolean canChangeCaseName() {
            return (SelectingObjects.size()) == 1 && (SelectingObjects.get(0) instanceof UseCaseObject);
        }

        public String getClassName() {
            return ((ClassObject) SelectingObjects.get(0)).getClassName();
        }

        public String getCaseName() {
            return ((UseCaseObject) SelectingObjects.get(0)).getCaseName();
        }

        public void changeObjectName(String name) {
            ((ClassObject) SelectingObjects.get(0)).setClassName(name);
            repaint();
        }

        public void changeCaseName(String name) {
            ((UseCaseObject) SelectingObjects.get(0)).setCaseName(name);
            repaint();
        }

        public void groupObject() {
            if (SelectingObjects.size() < 2) {
                return;
            }

            Iterator<BasicObject> iter = BasicObjects.iterator();
            HashSet<BasicObject> selectingObjs = new HashSet<BasicObject>(SelectingObjects);
            while (iter.hasNext()) {
                if (selectingObjs.contains(iter.next())) {
                    iter.remove();
                }
            }

            GroupObject group = new GroupObject();
            group.makeGroup(SelectingObjects);
            BasicObjects.add(group);
            SelectingObjects.add(group);
            repaint();
        }

        public void ungroupObject() {
            if (SelectingObjects.size() != 1) {
                return;
            }

            if (SelectingObjects.get(0) instanceof GroupObject) {
                GroupObject group = (GroupObject) SelectingObjects.get(0);
                clearSelectObject();
                group.decompose(BasicObjects);

                Iterator<Line> iter = LineObjects.iterator();
                while (iter.hasNext()) {
                    Line l = iter.next();
                    if (l.getSource() == group || l.getDestination() == group) {
                        iter.remove();
                    }
                }
            }
            repaint();
        }

        private boolean drawing;
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
        private ArrayList<BasicObject> SelectingObjects;
    }
}
