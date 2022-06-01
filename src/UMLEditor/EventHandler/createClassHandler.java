package EventHandler;

import Component.Canvas;
import Object.ClassObject;
import java.awt.event.MouseEvent;

public class createClassHandler implements eventHandler {
    public void handlePressedEvent(Canvas c, MouseEvent e) {}
    public void handleDraggedEvent(Canvas c, MouseEvent e) {}
    public void handleReleasedEvent(Canvas c, MouseEvent e) {
        c.strategy().addSelectableObject(new ClassObject(e.getPoint(), 0));
    }
}
