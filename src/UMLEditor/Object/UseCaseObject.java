package Object;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

public class UseCaseObject extends BasicObject {
    public UseCaseObject(Point p, int depth) {
        super(p, depth);
        setWidth(160);
        setLength(120);
        calculateDiagonal();
        calculateConnectPorts();
    }

    @Override
    public void draw(Graphics graph) {
        graph.drawOval(originx, originy, width, length);
        graph.setFont(defaultFont);
        graph.drawString(useCaseText, originx + 25, originy + 65);

        if (this.IsSelected()) {
            graph.setColor(Color.RED);
            graph.drawRect((originx + acrossx) / 2 - 10, originy - 20, 20, 20);
            graph.drawRect((originx + acrossx) / 2 - 10, acrossy, 20, 20);
            graph.drawRect(originx - 20, (originy + acrossy) / 2 - 10, 20, 20);
            graph.drawRect(acrossx, (originy + acrossy) / 2 - 10, 20, 20);
            graph.setColor(defaultBackground);
        }
    }

    // https://imgur.com/a/qupUrYT
    @Override
    public boolean contain(Point p) {
        double rx = width / 2;
        double ry = length / 2;
        double tx = (p.x - (originx + rx)) / rx;
        double ty = (p.y - (originy + ry)) / ry;
        return tx * tx + ty * ty < 1.0;
    }

    @Override
    public boolean contain(Point p1, Point p2) {
        return between(originx, p1.x, p2.x) && between(originy, p1.y, p2.y) && between(acrossx, p1.x, p2.x)
                && between(acrossy, p1.y, p2.y);
    }

    private String useCaseText = "use case";
}
