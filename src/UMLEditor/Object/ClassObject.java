package Object;

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
        calculateDiagonal();
    }

    @Override
    public void draw(Graphics graph) {
        graph.setColor(nameFieldColor);
        graph.fillRoundRect(originx, originy, width, nameFieldlength, rectRound, rectRound);
        graph.setColor(defaultBackground);
        graph.drawRoundRect(originx, originy, width, nameFieldlength, rectRound, rectRound);
        graph.setFont(defaultFont);
        graph.drawString(className, originx + 20, originy + 40);

        for (int i = 0; i < fieldnum; i++) {
            graph.setColor(memberFieldColor);
            graph.fillRoundRect(originx, originy + nameFieldlength + i * fieldlength, width - 1, fieldlength - 1, rectRound,
                    rectRound);
            graph.setColor(defaultBackground);
            graph.drawRoundRect(originx, originy + nameFieldlength + i * fieldlength, width - 1, fieldlength - 1, rectRound,
                    rectRound);
        }

        if (this.IsSelected()) {
            graph.setColor(Color.RED);
            graph.drawRect((originx + acrossx) / 2 - 10, originy - 20, 20, 20);
            graph.drawRect(originx - 20, (originy + acrossy) / 2 - 10, 20, 20);
            graph.drawRect((originx + acrossx) / 2 - 10, acrossy, 20, 20);
            graph.drawRect(acrossx, (originy + acrossy) / 2 - 10, 20, 20);
            graph.setColor(defaultBackground);
        }
    }
    
    @Override
    public boolean contain(Point p) {
        return p.x > originx && p.x < originx + width
                && p.y > originy && p.y < originy + length;
    }

    @Override
    public boolean contain(Point p1, Point p2) {
        return between(originx, p1.x, p2.x) && between(originy, p1.y, p2.y)
                && between(acrossx, p1.x, p2.x) && between(acrossy, p1.y, p2.y);
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
