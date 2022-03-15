package EventHandler;

import Component.Canvas;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class selectHandler implements eventHandler {
    public void mousePressed(Canvas c, MouseEvent e) {
        origin = e.getPoint();
        c.getStrategy().selectObjectByPoint(origin);
    }

    public void mouseDragged(Canvas c, MouseEvent e) {
        c.getStrategy().setSelectArea(true);
        c.getStrategy().selectObjectByArea(origin, e.getPoint());
        
    }
    public void mouseReleased(Canvas c, MouseEvent e) {
        c.getStrategy().setSelectArea(false);
    }

    private Point origin;
}
