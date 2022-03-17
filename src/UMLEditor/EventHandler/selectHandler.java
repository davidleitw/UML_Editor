package EventHandler;

import Component.Canvas;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class selectHandler implements eventHandler {
    public void mousePressed(Canvas c, MouseEvent e) {
        origin = e.getPoint();
        c.strategy().clearSelectObject().overlapObject(origin);
    }

    public void mouseDragged(Canvas c, MouseEvent e) {
        c.strategy().mouseDragging(true).clearSelectObject().selectObjectByArea(origin, e.getPoint());
    }
    public void mouseReleased(Canvas c, MouseEvent e) {
        c.strategy().mouseDragging(false);
    }

    private Point origin;
}
