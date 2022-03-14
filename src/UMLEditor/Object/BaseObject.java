package Object;

import java.util.ArrayList;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

public class BaseObject {
    public BaseObject() {}
    public BaseObject(Point p, int depth) {
        upperLeftCoordinate = p;
        this.depth = depth;
    }

    public Point getCenterCoordinate() {
        return upperLeftCoordinate;
    }

    public int getDepth() {
        return depth;
    }

    public void draw(Graphics graph) {};

    protected int depth;
    protected int width;
    protected int length;
    protected Color defaultColor;
    protected Point upperLeftCoordinate;
    // protected Point lowerRightCoordinate;
    protected ArrayList<Integer> connectPorts;
}
