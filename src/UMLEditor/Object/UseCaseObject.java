package Object;

import java.awt.Font;
import java.awt.Point;
import java.awt.Graphics;

public class UseCaseObject extends BaseObject {
    public UseCaseObject(Point p, int depth) {
        super(p, depth);
    }

    @Override
    public void draw(Graphics graph) {
        int x = (int)centerCoordinate_.getX();
        int y = (int)centerCoordinate_.getY();

        graph.drawOval(x, y, 160, 120);
        graph.setFont(new Font(useCaseText, Font.PLAIN, 25));
        graph.drawString(useCaseText, x+25, y+65);
    }

    private String useCaseText = "use case";
}
