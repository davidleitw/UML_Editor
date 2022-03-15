package EventHandler;

import Component.Canvas;
import java.awt.event.MouseEvent;

public class selectHandler implements eventHandler {
    public void mousePressed(Canvas c, MouseEvent e) {
        c.getStrategy().selectObjectByPoint(e.getPoint());
    }

    public void mouseDragged(Canvas c, MouseEvent e) {}
    public void mouseReleased(Canvas c, MouseEvent e) {}
}
