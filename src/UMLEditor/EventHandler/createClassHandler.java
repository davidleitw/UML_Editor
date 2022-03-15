package EventHandler;

import Component.Canvas;
import Object.ClassObject;
import java.awt.event.MouseEvent;

public class createClassHandler implements eventHandler {
    public void mousePressed(Canvas c, MouseEvent e) {}
    public void mouseDragged(Canvas c, MouseEvent e) {}
    public void mouseReleased(Canvas c, MouseEvent e) {
        c.getStrategy().addSelectableObject(new ClassObject(e.getPoint(), 0));
    }
}
