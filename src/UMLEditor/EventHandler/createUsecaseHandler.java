package EventHandler;

import Component.Canvas;
import Object.UseCaseObject;
import java.awt.event.MouseEvent;

public class createUsecaseHandler implements eventHandler {
    public void mousePressed(Canvas c, MouseEvent e) {}
    public void mouseDragged(Canvas c, MouseEvent e) {}
    public void mouseReleased(Canvas c, MouseEvent e) {
        c.getStrategy().addSelectableObject(new UseCaseObject(e.getPoint(), 0));
    }
}
