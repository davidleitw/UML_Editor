package Object;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

import java.util.Iterator;
import java.util.ArrayList;

public class GroupObject extends BasicObject {
    public GroupObject() {
        super();
        includeObjects = new ArrayList<BasicObject>();
    }

    public void makeGroup(ArrayList<BasicObject> selectingObjs) {
        Iterator<BasicObject> iter = selectingObjs.iterator();
        originX = Integer.MAX_VALUE;
        originY = Integer.MAX_VALUE;
        acrossX = Integer.MIN_VALUE;
        acrossY = Integer.MIN_VALUE;
        while (iter.hasNext()) {
            BasicObject obj = iter.next();
            originX = Math.min(originX, obj.originX);
            originY = Math.min(originY, obj.originY);
            acrossX = Math.max(acrossX, obj.acrossX);
            acrossY = Math.max(acrossY, obj.acrossY);
            obj.select(false);
            includeObjects.add(obj);
            iter.remove();
        }

        setWidth(acrossX - originX);
        setLength(acrossY - originY);
        this.select(true);
        calculateDiagonal();
        calculateConnectPorts();
    }

    public void decompose(ArrayList<BasicObject> basicObjects) {
        for (BasicObject obj : includeObjects) {
            basicObjects.add(obj);
        }

        basicObjects.remove(this);
    }
    
    @Override
    public void move(int offsetx, int offsety) {
        originX += offsetx;
        originY += offsety;
        for (BasicObject obj: includeObjects) {
            obj.move(offsetx, offsety);
        }
        calculateDiagonal();
        calculateConnectPorts();
    }

    @Override
    public void draw(Graphics graph) {

        if (this.IsSelected()) {
            graph.drawRect(originX, originY, width, length);
            graph.setColor(Color.RED);
            graph.drawRect((originX + acrossX) / 2 - 10, originY - 20, 20, 20);
            graph.drawRect(originX - 20, (originY + acrossY) / 2 - 10, 20, 20);
            graph.drawRect((originX + acrossX) / 2 - 10, acrossY, 20, 20);
            graph.drawRect(acrossX, (originY + acrossY) / 2 - 10, 20, 20);
            graph.setColor(defaultBackground); 
        }
        for (BasicObject obj : includeObjects) {
            obj.draw(graph);
        }
    }

    @Override
    public boolean contain(Point p) {
        return p.x > originX && p.x < originX + width
                && p.y > originY && p.y < originY + length;
    }

    @Override
    public boolean contain(Point p1, Point p2) {
        return between(originX, p1.x, p2.x) && between(originY, p1.y, p2.y)
                && between(acrossX, p1.x, p2.x) && between(acrossY, p1.y, p2.y);
    }

    private ArrayList<BasicObject> includeObjects;
}
