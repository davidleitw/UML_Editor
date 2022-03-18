package Object;

import java.awt.Point;
import java.awt.Graphics;

public class Line {
    public Line(BasicObject src, int portIndex) {
        setSource(src, portIndex);
    }

    public void setSource(BasicObject src, int portIndex) {
        source = src;
        srcPortIndex = portIndex;
    }

    public void setDestination(BasicObject dst, int portIndex) {
        destination = dst;
        dstPortIndex = portIndex;
    }

    public void draw(Graphics graph) {
        Point srcPoint = source.getPortPointByIndex(srcPortIndex);
        Point dstPoint = destination.getPortPointByIndex(dstPortIndex);

        graph.drawLine(srcPoint.x, srcPoint.y, dstPoint.x, dstPoint.y);        
    }

    private int srcPortIndex;
    private int dstPortIndex;
    private BasicObject source;
    private BasicObject destination;
}
