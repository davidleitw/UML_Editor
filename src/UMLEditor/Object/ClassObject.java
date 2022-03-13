package Object;

import java.awt.Font;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

public class ClassObject extends BaseObject {
    public ClassObject(Point p, int depth) {
        super(p, depth);
        width = 200;
        length = 80;

        calculateConnectPorts(p);
    }

    @Override
    public void draw(Graphics graph) {
        defaultColor = graph.getColor();

        int x = (int)centerCoordinate_.getX();
        int y = (int)centerCoordinate_.getY();
        
        graph.setColor(new Color(169, 169, 169));
        graph.fillRoundRect(x, y, width, length, round, round);
        graph.setColor(defaultColor);
        graph.drawRoundRect(x, y, width, length, round, round);
        graph.setFont(new Font(className, Font.PLAIN, 25));
        graph.drawString(className, x+20, y+40);

        y += length;
        for (int i = 0; i < fieldnum; i++) {
            graph.drawRoundRect(x, y, width, fieldlength, round, round);
            y += fieldlength;
        }
    }

    @Override
    public void calculateConnectPorts(Point p) {
        
    }

    public void setClassName(String clsname) {
        className = clsname;
    }

    private int round = 25;
    private int fieldnum = 3;
    private int fieldlength = 60;
    private String className = "Class name";
}
