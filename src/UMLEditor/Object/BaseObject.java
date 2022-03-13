package Object;

import java.awt.Point;
import java.awt.Graphics;

public class BaseObject {
    public BaseObject() {}
    public BaseObject(Point p, int depth) {
        centerCoordinate_ = p;
        depth_ = depth;
    }

    public Point getCenterCoordinate() {
        return centerCoordinate_;
    }

    public int getDepth() {
        return depth_;
    }

    public void draw(Graphics graph) {};

    protected int width;
    protected int length;
    protected int depth_ = 0;
    protected Point centerCoordinate_;
}
