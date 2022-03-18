package Object;

import java.util.ArrayList;

import java.awt.Font;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

public abstract class BaseObject {
    public BaseObject(Point p, int dep) {
        assert (p != null);
        assert (dep >= 0);
        originx = p.x;
        originy = p.y;
        depth = dep;
    }

    public void setWidth(int w) {
        assert (w > 0);
        width = w;
    }

    public void setLength(int l) {
        assert (l > 0);
        length = l;
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

    public void move(int offsetx, int offsety) {
        originx += offsetx;
        originy += offsety;
        calculateDiagonal();
    }

    public void calculateDiagonal() {
        acrossx = originx + width;
        acrossy = originy + length;
    }

    public abstract void draw(Graphics graph);

    public abstract boolean contain(Point p);

    public abstract boolean contain(Point p1, Point p2);

    protected int depth;
    protected int width;
    protected int length;
    protected int originx;
    protected int originy;
    protected int acrossx;
    protected int acrossy;
    protected boolean selected;
    protected final int rectRound = 25;
    protected ArrayList<Point> connectPorts;
    protected final Color defaultBackground = new Color(16777216);
    protected final Font defaultFont = new Font("", Font.PLAIN, 25);
}
