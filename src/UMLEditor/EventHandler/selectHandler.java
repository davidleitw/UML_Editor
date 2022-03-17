package EventHandler;

import Component.Canvas;
import java.awt.event.MouseEvent;

public class selectHandler implements eventHandler {
    public void mousePressed(Canvas c, MouseEvent e) {
        c.strategy().selectMousePressed(e.getPoint());
    }
    public void mouseDragged(Canvas c, MouseEvent e) {
        c.strategy().selectMouseDragged(e.getPoint());
    }
    public void mouseReleased(Canvas c, MouseEvent e) {
        c.strategy().selectMouseReleased();
    }
}
