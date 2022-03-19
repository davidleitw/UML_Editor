package Object;

import java.awt.Point;
import java.awt.Graphics;

public abstract class Line {
    public Line() {}

    public void setSource(BasicObject src, int portIndex) {
        source = src;
        srcPortIndex = portIndex;
    }

    public void setDestination(BasicObject dst, int portIndex) {
        destination = dst;
        dstPortIndex = portIndex;
    }

    protected void getPoints() {
        srcPoint = source.getPortPointByIndex(srcPortIndex);
        dstPoint = destination.getPortPointByIndex(dstPortIndex);
    }

    public abstract void draw(Graphics graph);

    protected Point srcPoint;
    protected Point dstPoint;
    protected int srcPortIndex;
    protected int dstPortIndex;
    protected BasicObject source;
    protected BasicObject destination;
}
