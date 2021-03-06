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
        graph.drawOval(originX, originY, width, length);
        graph.setFont(defaultFont);
        graph.drawString(userCaseName, originX + 25, originY + 65);

        if (this.IsSelected()) {
            graph.setColor(Color.RED);
            graph.drawRect((originX + acrossX) / 2 - 10, originY - 20, 20, 20);
            graph.drawRect((originX + acrossX) / 2 - 10, acrossY, 20, 20);
            graph.drawRect(originX - 20, (originY + acrossY) / 2 - 10, 20, 20);
            graph.drawRect(acrossX, (originY + acrossY) / 2 - 10, 20, 20);
            graph.setColor(defaultBackground);
        }
    }

    // https://imgur.com/a/qupUrYT
    // https://developer.classpath.org/doc/java/awt/geom/Ellipse2D-source.html
    @Override
    public boolean contain(int x, int y) {
        double rx = width / 2;
        double ry = length / 2;
        double tx = (x - (originX + rx)) / rx;
        double ty = (y - (originY + ry)) / ry;
        return tx * tx + ty * ty < 1.0;
    }

    public String getCaseName() {
        return userCaseName;
    }

    public void setCaseName(String name) {
        userCaseName = name;
    }

    private String userCaseName = "use case";
}
