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

    public abstract boolean include(int x, int y);

    public void Selected(boolean state) {
        beenSelected = state;
    }

    public boolean getState() {
        return beenSelected;
    }

    protected int depth;
    protected int width;
    protected int length;
    protected boolean beenSelected;
    protected Color defaultColor;
    protected Point upperLeftCoordinate;
    protected ArrayList<Integer> connectPorts;
}
