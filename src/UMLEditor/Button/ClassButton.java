package Button;

import java.awt.event.MouseEvent;

import Component.Canvas;
import Object.ClassObject;

public class ClassButton extends BaseButton { 
    public ClassButton(String text) {
        super(text, "icons/icons8-drawer-80.png");
    }

    @Override
    public void mousePressed(Canvas c, MouseEvent e) {
        return;
    }

    @Override
    public void mouseReleased(Canvas c, MouseEvent e) {
        ClassObject obj = new ClassObject(e.getPoint(), 0);
        c.addObject(obj);
        c.repaint();
    }
}
