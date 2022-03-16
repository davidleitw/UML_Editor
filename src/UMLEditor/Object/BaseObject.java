package Object;

import java.util.ArrayList;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

public abstract class BaseObject {
    public BaseObject() {}
    public BaseObject(Point p, int depth) {
        upperLeftCoordinate = p;
        this.depth = depth;
    }

    public Point getUpperLeftCoordinate() {
        return upperLeftCoordinate;
    }

    public int getDepth() {
        return depth;
    }

    public abstract void draw(Graphics graph);
    public abstract boolean contain(Point p);
    public abstract boolean contain(Point origin, Point offset);
    protected abstract void calculateLowerRight(int w, int l);

    public void select(boolean state) {
        beenSelected = state;
    }

    public boolean getState() {
        return beenSelected;
    }

    public static boolean between(int origin, int left, int right) {
        return origin >= left && origin <= right;
    }

    protected int depth;
    protected int width;
    protected int length;
    protected Color defaultColor;
    protected boolean beenSelected;
    protected Point upperLeftCoordinate;
    protected Point lowerRightCoordinate;
    protected ArrayList<Integer> connectPorts;

}
