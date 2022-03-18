package Object;

import java.awt.Font;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

public abstract class BasicObject {
    public BasicObject(Point p, int dep) {
        assert (p != null);
        assert (dep >= 0);
        originx = p.x;
        originy = p.y;
        depth = dep;
        connectionPorts = new Point[portNum];
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
        calculateConnectPorts();
    }

    public void calculateDiagonal() {
        acrossx = originx + width;
        acrossy = originy + length;
    }

    public void calculateConnectPorts() {
        connectionPorts[0] = new Point(acrossx, (originy + acrossy) / 2);
        connectionPorts[1] = new Point((originx + acrossx) / 2, acrossy);
        connectionPorts[2] = new Point(originx, (originy + acrossy) / 2);
        connectionPorts[3] = new Point((originx + acrossx) / 2, originy);
    }

    public int getClosestPortIndex(Point p) {
        int closestIndex = -1;
        int distance = Integer.MAX_VALUE;
        for (int i = 0; i < connectionPorts.length; i++) {
            int d = (int)p.distance(connectionPorts[i]);
            if (d < distance) {
                distance = d;
                closestIndex = i;
            }
        }
        return closestIndex;
    }

    public Point getPortPointByIndex(int index) {
        assert(index >= 0);
        assert(index < portNum);
        return connectionPorts[index];
    }

    public abstract void draw(Graphics graph);

    public abstract boolean contain(Point p);

    public abstract boolean contain(Point p1, Point p2);

    protected int depth;
    protected int width;
    protected int length;
    protected boolean selected;
    protected int originx, originy;
    protected int acrossx, acrossy;
    protected final int portNum = 4;
    protected final int rectRound = 25;
    protected Point[] connectionPorts;
    protected final Color defaultBackground = new Color(16777216);
    protected final Font defaultFont = new Font("", Font.PLAIN, 25);
}
