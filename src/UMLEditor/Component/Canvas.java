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

        // 查看滑鼠單擊的位置是否跟物件重疊，如果重疊則回傳該物件
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
            // 這裡的 ox, oy 計算跟上一次的 offsetPoint 差多遠(計算出物件需要平移多少單位)
            ox = offset.x - offsetPoint.x;
            oy = offset.y - offsetPoint.y;
            setmouseDragging(true);
            setOffsetPoint(offset);
            // 移動物件的邏輯
            if (selecting) {
                moveselectingObjects(ox, oy);
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

            // @creatingLine == null 代表一開始滑鼠點擊沒有點選到物件
            // @destination == null 代表滑鼠放開的瞬間沒有在物件上面
            // @creatingLine.getSource() == destination 為特例，代表線自己連到自己身上，目前的設計是不允許的
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

        // 只有單選物件的時候能改變物件名稱
        public boolean canChangeClassName() {
            return (selectingObjects.size()) == 1 && (selectingObjects.get(0) instanceof ClassObject);
        }

        // 只有單選物件的時候能改變物件名稱
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
        private boolean selecting; // 判斷滑鼠拖拉的行為(拖動某個物件，或者範圍選取)
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
