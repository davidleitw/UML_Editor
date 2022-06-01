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
                graph.drawRoundRect(lx, ly, ox, oy, 20, 20);
                graph.setColor(draggingBackground);
                graph.fillRoundRect(lx, ly, ox, oy, 20, 20);
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
            // 這裡的 ox, oy 計算跟上一次的 offsetPoint 差多遠(計算出物件需要平移多少單位)
            ox = offset.x - offsetPoint.x;
            oy = offset.y - offsetPoint.y;
            setmouseDragging(true);
            setOffsetPoint(offset);
            // 移動物件的邏輯
            if (selecting) {
                moveSelectingObjects(ox, oy);
                repaint();
                return;
            }

            clearSelectObject();
            // 這裡的 ox, oy 則是算出新的 offsetPoint 與 originPoint 的差異
            ox = Math.abs(offsetPoint.x - originPoint.x);
            oy = Math.abs(offsetPoint.y - originPoint.y);
            // lx, ly 代表"左上角"的座標，因為拖拉的時候要畫出選擇框，需要左上角的座標
            lx = Math.min(originPoint.x, offsetPoint.x);
            ly = Math.min(originPoint.y, offsetPoint.y);
            for (BasicObject obj : BasicObjects) {
                if (obj.contained(lx, ly, ox, oy)) {
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
            if (source == null) {
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
            if (creatingLine == null || destination == null || creatingLine.getSource() == destination) {
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

        private int ox;
        private int oy;
        private int lx;
        private int ly;
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
