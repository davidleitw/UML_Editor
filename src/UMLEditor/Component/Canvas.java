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

import Button.BaseButton;

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
        currentBtn = toolBar.getCurrentBtn();
        if (currentBtn != null) {
            currentBtn.mousePressedEvent(this, e);
        }
    }

    private void mouseReleasedHandler(MouseEvent e) {
        currentBtn = toolBar.getCurrentBtn();
        if (currentBtn != null) {
            currentBtn.mouseReleasedEvent(this, e);
        }
    }

    private void mouseDraggedHandler(MouseEvent e) {
        currentBtn = toolBar.getCurrentBtn();
        if (currentBtn != null) {
            toolBar.getCurrentBtn().mouseDraggedEvent(this, e);
        }
    }

    @Override
    public void paint(Graphics graph) {
        strategy().paint(graph);
    }
    
    private Strategy strategy = null;
    private BaseButton currentBtn = null;
    private ButtonToolBar toolBar = null;

    public Strategy strategy() {
        if (strategy == null)
            strategy = new Strategy();
        return strategy;
    }

    public class Strategy {
        public Strategy() {
            lineObjects = new ArrayList<Line>();
            basicObjects = new ArrayList<BasicObject>();
            selectingObjects = new ArrayList<BasicObject>();

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
                graph.drawRoundRect(lx, ly, ox, oy, 20, 20);
                graph.setColor(draggingBackground);
                graph.fillRoundRect(lx, ly, ox, oy, 20, 20);
                graph.setColor(defaultBackground);
            }

            if (creatingLine != null) {
                graph.drawLine(originPoint.x, originPoint.y, offsetPoint.x, offsetPoint.y);
            }

            for (BasicObject obj : basicObjects) {
                obj.draw(graph);
            }

            for (Line line : lineObjects) {
                line.draw(graph);
            }
        }

        private void setOriginPoint(Point p) {
            originPoint = p;
        }

        private void setOffsetPoint(Point p) {
            offsetPoint = p;
        }

        // ?????????????????????????????????????????????????????????????????????????????????
        private BasicObject getOverlapObject(Point p) {
            for (BasicObject object : basicObjects) {
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
            BasicObject obj = getOverlapObject(p);
            if (obj != null) {
                selecting = true;
                selectingObjects.add(obj);
            }
            repaint();
        }

        public void selectMouseDragged(Point offset) {
            // ????????? ox, oy ????????????????????? offsetPoint ?????????(???????????????????????????????????????)
            ox = offset.x - offsetPoint.x;
            oy = offset.y - offsetPoint.y;
            setmouseDragging(true);
            setOffsetPoint(offset);
            // ?????????????????????
            if (selecting) {
                moveselectingObjects(ox, oy);
                repaint();
                return;
            }
   
            clearSelectObject();
            // ????????? ox, oy ?????????????????? offsetPoint ??? originPoint ?????????
            ox = Math.abs(offsetPoint.x - originPoint.x);
            oy = Math.abs(offsetPoint.y - originPoint.y);
            // lx, ly ??????"?????????"??????????????????????????????????????????????????????????????????????????????
            lx = Math.min(originPoint.x, offsetPoint.x);
            ly = Math.min(originPoint.y, offsetPoint.y);
            for (BasicObject obj : basicObjects) {
                if (obj.contain(lx, ly, ox, oy)) {
                    selectingObjects.add(obj);
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

        private void clearSelectObject() {
            selecting = false;
            for (BasicObject selectobj : selectingObjects) {
                selectobj.select(false);
            }
            selectingObjects.clear();
        }

        private void moveselectingObjects(int offsetx, int offsety) {
            for (BasicObject selectobj : selectingObjects) {
                selectobj.move(offsetx, offsety);
            }
        }

        public void createBasicObject(BasicObject obj) {
            basicObjects.add(obj);
            repaint();
        }

        public void createLineMousePressed(Point p, Line line) {
            setOriginPoint(p);
            clearSelectObject();
            
            BasicObject source = getOverlapObject(p);
            if (source == null) {
                return;
            }
            line.setSource(source, source.getClosestPortIndex(p));
            creatingLine = line;
            repaint();
        }

        public void createLineMouseDragged(Point p) {
            if (originPoint != null) {
                setOffsetPoint(p);
            }
            repaint();
        }

        public void createLineMouseReleased(Point p) {
            BasicObject destination = getOverlapObject(p);

            // @creatingLine == null ????????????????????????????????????????????????
            // @destination == null ????????????????????????????????????????????????
            // @creatingLine.getSource() == destination ??????????????????????????????????????????????????????????????????????????????
            if (creatingLine == null || destination == null || creatingLine.getSource() == destination) {
                creatingLine = null;
                repaint();
                return;
            }

            creatingLine.setDestination(destination, destination.getClosestPortIndex(p));
            lineObjects.add(creatingLine);
            creatingLine = null;
            setOriginPoint(null);
            setOffsetPoint(null);
            repaint();
        }

        // ????????????????????????????????????????????????
        public boolean canChangeClassName() {
            return (selectingObjects.size()) == 1 && (selectingObjects.get(0) instanceof ClassObject);
        }

        // ????????????????????????????????????????????????
        public boolean canChangeCaseName() {
            return (selectingObjects.size()) == 1 && (selectingObjects.get(0) instanceof UseCaseObject);
        }

        public String getClassName() {
            return ((ClassObject) selectingObjects.get(0)).getClassName();
        }

        public String getCaseName() {
            return ((UseCaseObject) selectingObjects.get(0)).getCaseName();
        }

        public void changeObjectName(String name) {
            ((ClassObject) selectingObjects.get(0)).setClassName(name);
            repaint();
        }

        public void changeCaseName(String name) {
            ((UseCaseObject) selectingObjects.get(0)).setCaseName(name);
            repaint();
        }

        public void groupObject() {
            if (selectingObjects.size() < 2) {
                return;
            }

            Iterator<BasicObject> iter = basicObjects.iterator();
            HashSet<BasicObject> selectingObjs = new HashSet<BasicObject>(selectingObjects);
            while (iter.hasNext()) {
                if (selectingObjs.contains(iter.next())) {
                    iter.remove();
                }
            }

            GroupObject group = new GroupObject();
            group.makeGroup(selectingObjects);
            basicObjects.add(group);
            selectingObjects.add(group);
            repaint();
        }

        public void ungroupObject() {
            if (selectingObjects.size() == 1 && selectingObjects.get(0) instanceof GroupObject) {
                GroupObject group = (GroupObject) selectingObjects.get(0);
                clearSelectObject();
                group.decompose(basicObjects);

                Iterator<Line> iter = lineObjects.iterator();
                while (iter.hasNext()) {
                    Line l = iter.next();
                    if (l.getSource() == group || l.getDestination() == group) {
                        iter.remove();
                    }
                }
                repaint();
            }
        }

        private int ox;
        private int oy;
        private int lx;
        private int ly;
        private boolean dragging;
        private boolean selecting; // ???????????????????????????(???????????????????????????????????????)
        private Point originPoint;
        private Point offsetPoint;
        private Line creatingLine;
        private Color cleanBackground;
        private Color defaultBackground;
        private Color draggingBackground;
        private ArrayList<Line> lineObjects;
        private ArrayList<BasicObject> basicObjects;
        private ArrayList<BasicObject> selectingObjects;
    }
}
