package Object;

import java.awt.Font;
import java.awt.Point;
import java.awt.Graphics;

public class UseCaseObject extends BaseObject implements SelectableObject {
    public UseCaseObject(Point p, int depth) {
        super(p, depth);
    }

    @Override
    public void draw(Graphics graph) {
        int x = (int)upperLeftCoordinate.getX();
        int y = (int)upperLeftCoordinate.getY();

        graph.drawOval(x, y, 160, 120);
        graph.setFont(new Font(useCaseText, Font.PLAIN, 25));
        graph.drawString(useCaseText, x+25, y+65);
    }

    public void calculateConnectPorts(Point p) {

    }
    
    private String useCaseText = "use case";
}
