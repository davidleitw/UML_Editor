package Object;

import java.awt.Point;
import java.awt.Graphics;

public class ClassObject extends BaseObject {
    public ClassObject(Point p, int depth) {
        super(p, depth);
    }

    public void draw(Graphics graph) {
        graph.drawRect((int)centerCoordinate_.getX(), (int)centerCoordinate_.getY(), width, length);
    }

    private int width = 200;
    private int length = 200;
}
