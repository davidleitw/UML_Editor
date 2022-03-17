package EventHandler;

import Component.Canvas;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class selectHandler implements eventHandler {
    public void mousePressed(Canvas c, MouseEvent e) {
        origin = e.getPoint();
        c.strategy().selectMousePressed(origin);
    }

    public void mouseDragged(Canvas c, MouseEvent e) {
        c.strategy().selectMouseDragged(origin, e.getPoint());
    }
    public void mouseReleased(Canvas c, MouseEvent e) {
        c.strategy().selectMouseReleased();
    }

    private Point origin;
}
