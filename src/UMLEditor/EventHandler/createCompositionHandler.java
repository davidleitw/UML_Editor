package EventHandler;

import Component.Canvas;
import Object.CompositionLine;

import java.awt.event.MouseEvent;

public class createCompositionHandler implements eventHandler {
    public void handlePressedEvent(Canvas c, MouseEvent e) {
        c.strategy().createLineMousePressed(e.getPoint(), new CompositionLine());
    }

    public void handleDraggedEvent(Canvas c, MouseEvent e) {
        c.strategy().createLineMouseDragged(e.getPoint());
    }

    public void handleReleasedEvent(Canvas c, MouseEvent e) {
        c.strategy().createLineMouseReleased(e.getPoint());
    }
}
