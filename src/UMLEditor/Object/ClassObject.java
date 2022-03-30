package Object;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

public class ClassObject extends BasicObject {
    public ClassObject(Point p, int depth) {
        super(p, depth);
        fieldnum = 3;
        fieldlength = 60;
        nameFieldlength = 80;
        setWidth(200);
        setLength(nameFieldlength + fieldnum * fieldlength);
        calculateDiagonal();
        calculateConnectPorts();
    }

    @Override
    public void draw(Graphics graph) {
        graph.setColor(nameFieldColor);
        graph.fillRoundRect(originX, originY, width, nameFieldlength, rectRound, rectRound);
        graph.setColor(defaultBackground);
        graph.drawRoundRect(originX, originY, width, nameFieldlength, rectRound, rectRound);
        graph.setFont(defaultFont);
        graph.drawString(className, originX + 20, originY + 40);

        for (int i = 0; i < fieldnum; i++) {
            graph.setColor(memberFieldColor);
            graph.fillRoundRect(originX, originY + nameFieldlength + i * fieldlength, width - 1, fieldlength - 1, rectRound,
                    rectRound);
            graph.setColor(defaultBackground);
            graph.drawRoundRect(originX, originY + nameFieldlength + i * fieldlength, width - 1, fieldlength - 1, rectRound,
                    rectRound);
        }

        if (this.IsSelected()) {
            graph.setColor(Color.RED);
            graph.drawRect((originX + acrossX) / 2 - 10, originY - 20, 20, 20);
            graph.drawRect(originX - 20, (originY + acrossY) / 2 - 10, 20, 20);
            graph.drawRect((originX + acrossX) / 2 - 10, acrossY, 20, 20);
            graph.drawRect(acrossX, (originY + acrossY) / 2 - 10, 20, 20);
            graph.setColor(defaultBackground);
        }
    }
    
    @Override
    public boolean contain(int x, int y) {
        return x > originX && x < originX + width
            && y > originY && y < originY + length;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String name) {
        className = name;
    }

    private int fieldnum;
    private int fieldlength;
    private int nameFieldlength;
    private String className = "Class name";
    private final Color nameFieldColor = new Color(169, 169, 169);
    private final Color memberFieldColor = new Color(177, 192, 213);
}
