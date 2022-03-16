package EventHandler;

import Component.Canvas;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class selectHandler implements eventHandler {
    public void mousePressed(Canvas c, MouseEvent e) {
        if (c.strategy().clearSelectObject().overlapObject(origin)) {
            c.strategy().selectTopObject();
        } else {
            dragMode = true;
            origin = e.getPoint();
        }
    }

    public void mouseDragged(Canvas c, MouseEvent e) {
        if (dragMode) {
            c.strategy().setSelectArea(true).clearSelectObject().selectObjectByArea(origin, e.getPoint());
        }

    }
    public void mouseReleased(Canvas c, MouseEvent e) {
        dragMode = false;
        c.strategy().setSelectArea(false);
    }

    private Point origin;
    private boolean dragMode;
}
