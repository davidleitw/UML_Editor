package EventHandler;

import Component.Canvas;
import java.awt.event.MouseEvent;

public interface eventHandler {
    void mousePressed(Canvas c, MouseEvent e);
    void mouseDragged(Canvas c, MouseEvent e);
    void mouseReleased(Canvas c, MouseEvent e);
}
