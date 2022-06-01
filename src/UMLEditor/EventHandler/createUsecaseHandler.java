package EventHandler;

import Component.Canvas;
import Object.UseCaseObject;
import java.awt.event.MouseEvent;

public class createUsecaseHandler implements eventHandler {
    public void handlePressedEvent(Canvas c, MouseEvent e) {}
    public void handleDraggedEvent(Canvas c, MouseEvent e) {}
    public void handleReleasedEvent(Canvas c, MouseEvent e) {
        c.strategy().addSelectableObject(new UseCaseObject(e.getPoint(), 0));
    }
}
