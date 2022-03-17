package Object;

import java.util.ArrayList;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

public abstract class BaseObject {
    public BaseObject() {}
    public BaseObject(Point p, int dep) {
        originx = p.x;
        originy = p.y;
        rectRound = 25;
        defaultBackground = new Color(16777216);
        depth = dep;
    }

    public int getDepth() {
        return depth;
    }

    public void select(boolean state) {
        selected = state;
    }

    public boolean IsSelected() {
        return selected;
    }

    public static boolean between(int origin, int left, int right) {
        return origin >= left && origin <= right;
    }

    public abstract void move(Point p);
    public abstract void move(int offsetx, int offsety);
    public abstract void draw(Graphics graph);
    public abstract boolean contain(Point p);
    public abstract boolean contain(Point p1, Point p2);
    protected abstract void calculateLowerRight(int w, int l);
    
    protected int depth;
    protected Point lowerRightCoordinate;
    protected ArrayList<Integer> connectPorts;

    protected int width;
    protected int length;
    protected int originx;
    protected int originy;
    protected int rectRound;
    protected boolean selected;
    protected Color defaultBackground;
}
