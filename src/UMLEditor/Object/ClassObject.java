package Object;

import java.awt.Font;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

public class ClassObject extends BaseObject {
    public ClassObject(Point p, int depth) {
        super(p, depth);
        
        fieldnum = 3;
        fieldlength = 60;
        nameFieldlength = 80;
        setWidth(200);
        setLength(nameFieldlength + fieldnum * fieldlength);
        calculateLowerRight(width, length);
    }

    @Override
    public void draw(Graphics graph) {
        graph.setColor(nameFieldColor);
        graph.fillRoundRect(originx, originy, width, nameFieldlength, rectRound, rectRound);
        graph.setColor(defaultBackground);
        graph.drawRoundRect(originx, originy, width, nameFieldlength, rectRound, rectRound);
        graph.setFont(new Font(className, Font.PLAIN, 25));
        graph.drawString(className, originx + 20, originy + 40);

        for (int i = 0; i < fieldnum; i++) {
            graph.setColor(memberFieldColor);
            graph.fillRoundRect(originx, originy + nameFieldlength + i * fieldlength, width - 1, fieldlength - 1, rectRound,
                    rectRound);
            graph.setColor(defaultBackground);
            graph.drawRoundRect(originx, originy + nameFieldlength + i * fieldlength, width - 1, fieldlength - 1, rectRound,
                    rectRound);
        }

        if (this.IsSelected() == true) {
            int rx = originx + width;
            int ry = originy + nameFieldlength + fieldnum * fieldlength;

            graph.setColor(Color.RED);
            graph.drawRect((originx + rx) / 2 - 10, originy - 20, 20, 20);
            graph.drawRect(originx - 20, (originy + ry) / 2 - 10, 20, 20);
            graph.drawRect((originx + rx) / 2 - 10, ry, 20, 20);
            graph.drawRect(rx, (originy + ry) / 2 - 10, 20, 20);
            graph.setColor(defaultBackground);
        }
    }

    @Override
    protected void calculateLowerRight(int w, int l) {
        lowerRightCoordinate = new Point(originx + w, originy + l);
    }

    public void move(Point p) {
        originx = p.x;
        originy = p.y;
        calculateLowerRight(width, length);
    }

    // TODO
    public void move(int offsetx, int offsety) {
        // originx = ox + offsetx;
        // originy = oy + offsety;
        calculateLowerRight(width, length);
    }

    public boolean contain(Point p) {
        return p.x > originx && p.x < originx + width
                && p.y > originy && p.y < originy + length;
    }

    public boolean contain(Point p1, Point p2) {
        int originx = p1.x;
        int originy = p1.y;
        int rx = p2.x;
        int ry = p2.y;
        return between(originx, originx, rx) && between(originy, originy, ry)
                && between(lowerRightCoordinate.x, originx, rx) && between(lowerRightCoordinate.y, originy, ry);
    }

    public void setClassName(String name) {
        className = name;
    }

    public String getClassName() {
        return className;
    }

    private int fieldnum;
    private int fieldlength;
    private int nameFieldlength;
    private String className = "Class name";

    private final Color nameFieldColor = new Color(169, 169, 169);
    private final Color memberFieldColor = new Color(177, 192, 213);
}
