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
        System.out.printf("(%d, %d)\n", e.getX(), e.getY());
        return;
    }

    @Override
    public void mouseReleased(Canvas c, MouseEvent e) {
        c.addObject(new ClassObject(e.getPoint(), 0));
        c.repaint();
    }
}
