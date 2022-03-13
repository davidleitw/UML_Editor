package Button;

import java.awt.event.MouseEvent;

import Component.Canvas;
import Object.UseCaseObject;

public class UseCaseButton extends BaseButton{
    public UseCaseButton(String text) {
        super(text, "icons/icons8-oval-80.png");
    }

    @Override
    public void mousePressed(Canvas c, MouseEvent e) {
        System.out.printf("(%d, %d)\n", e.getX(), e.getY());
        return;
    }

    @Override
    public void mouseReleased(Canvas c, MouseEvent e) {
        c.addObject(new UseCaseObject(e.getPoint(), 0));
        c.repaint();
    }
}
