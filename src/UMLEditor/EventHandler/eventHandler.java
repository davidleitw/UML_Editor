package EventHandler;

import Component.Canvas;
import java.awt.event.MouseEvent;

public interface eventHandler {
    void handlePressedEvent(Canvas c, MouseEvent e);
    void handleDraggedEvent(Canvas c, MouseEvent e);
    void handleReleasedEvent(Canvas c, MouseEvent e);
}
