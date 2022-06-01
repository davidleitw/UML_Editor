package Object;

import java.awt.Font;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

public abstract class BasicObject {
    public BasicObject() {
        connectionPorts = new Point[portNum];
    }

    public BasicObject(Point p, int d) {
        assert (p != null);
        assert (d >= 0);
        originX = p.x;
        originY = p.y;
        depth = d;
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
        originX += offsetx;
        originY += offsety;
        calculateDiagonal();
        calculateConnectPorts();
    }

    // 更新對角線座標
    public void calculateDiagonal() {
        acrossX = originX + width;
        acrossY = originY + length;
    }

    // 更新四個 connection ports 座標
    public void calculateConnectPorts() {
        connectionPorts[0] = new Point(acrossX, (originY + acrossY) / 2);
        connectionPorts[1] = new Point((originX + acrossX) / 2, acrossY);
        connectionPorts[2] = new Point(originX, (originY + acrossY) / 2);
        connectionPorts[3] = new Point((originX + acrossX) / 2, originY);
    }

    public int getClosestPortIndex(Point p) {
        int closestIndex = -1;
        int distance = Integer.MAX_VALUE;
        for (int i = 0; i < connectionPorts.length; i++) {
            int d = (int) p.distance(connectionPorts[i]);
            if (d < distance) {
                distance = d;
                closestIndex = i;
            }
        }
        return closestIndex;
    }

    public Point getPortPointByIndex(int index) {
        assert (index >= 0);
        assert (index < portNum);
        return connectionPorts[index];
    }

    public abstract void draw(Graphics graph);

    public abstract boolean contain(int x, int y);

    // 判斷物件是否在內
    public boolean contained(int x, int y, int w, int l) {
        return w > 0 && l > 0 && width > 0 && length > 0 && x < originX && y < originY && originX + width < x + w
                && originY + length < y + l;
    }

    protected int depth;
    protected int width;
    protected int length;
    protected boolean selected;
    protected int originX, originY;
    protected int acrossX, acrossY;
    protected final int portNum = 4;
    protected final int rectRound = 25;
    protected Point[] connectionPorts;
    protected final Color defaultBackground = new Color(16777216);
    protected final Font defaultFont = new Font("", Font.PLAIN, 25);
}
