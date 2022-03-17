package Object;

import java.util.ArrayList;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

public abstract class BaseObject {
    public BaseObject() {}
    public BaseObject(Point p, int depth) {
        metaCoordinate = p;
        this.depth = depth;
    }

    public void move(Point p) {
        metaCoordinate = p;
    }

    public int getDepth() {
        return depth;
    }

    public void select(boolean state) {
        beenSelected = state;
    }

    public boolean isSelected() {
        return beenSelected;
    }

    public static boolean between(int origin, int left, int right) {
        return origin >= left && origin <= right;
    }

    public abstract void draw(Graphics graph);
    public abstract boolean contain(Point p);
    public abstract boolean contain(Point origin, Point offset);
    protected abstract void calculateLowerRight(int w, int l);
    
    protected int depth;
    protected int width;
    protected int length;
    protected Color defaultColor;
    protected boolean beenSelected;
    protected Point metaCoordinate;
    protected Point lowerRightCoordinate;
    protected ArrayList<Integer> connectPorts;

}
