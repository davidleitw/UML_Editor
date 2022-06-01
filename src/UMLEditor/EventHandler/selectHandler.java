package EventHandler;

import Component.Canvas;
import java.awt.event.MouseEvent;

public class selectHandler implements eventHandler {
    public void handlePressedEvent(Canvas c, MouseEvent e) {
        c.strategy().selectMousePressed(e.getPoint());
    }
    public void handleDraggedEvent(Canvas c, MouseEvent e) {
        c.strategy().selectMouseDragged(e.getPoint());
    }
    public void handleReleasedEvent(Canvas c, MouseEvent e) {
        c.strategy().selectMouseReleased();
    }
}
