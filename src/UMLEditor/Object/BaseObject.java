package Object;

import java.util.ArrayList;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

public class BaseObject {
    public BaseObject() {}
    public BaseObject(Point p, int depth) {
        centerCoordinate_ = p;
        this.depth = depth;
    }

    public Point getCenterCoordinate() {
        return centerCoordinate_;
    }

    public int getDepth() {
        return this.depth;
    }

    public void draw(Graphics graph) {};

    public void calculateConnectPorts(Point p) {};

    protected int depth;
    protected int width;
    protected int length;
    protected Color defaultColor;
    protected Point centerCoordinate_;
    protected ArrayList<Integer> connectPorts;
}
