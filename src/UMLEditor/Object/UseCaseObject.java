package Object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Graphics;

public class UseCaseObject extends BaseObject implements SelectableObject {
    public UseCaseObject(Point p, int depth) {
        super(p, depth);
        width = 160;
        length = 120;
    }

    @Override
    public void draw(Graphics graph) {
        defaultColor = graph.getColor();
        int lx = upperLeftCoordinate.x;
        int ly = upperLeftCoordinate.y;

        graph.drawOval(lx, ly, width, length);
        graph.setFont(new Font(useCaseText, Font.PLAIN, 25));
        graph.drawString(useCaseText, lx+25, ly+65);

        
        if (this.beenSelected) {
            int rx = lx + width;
            int ry = ly + length;

            graph.setColor(Color.RED);
            graph.fillRect((lx+rx)/2-10, ly-20, 20, 20);
            graph.fillRect((lx+rx)/2-10, ry, 20, 20);
            graph.fillRect(lx-20, (ly+ry)/2-10, 20, 20);
            graph.fillRect(rx, (ly+ry)/2-10, 20, 20);
            graph.setColor(defaultColor);
        }
    }

    public void calculateConnectPorts(Point p) {

    }
    
    public boolean include(int x, int y) {
        return false;
    } 

    private String useCaseText = "use case";
}
