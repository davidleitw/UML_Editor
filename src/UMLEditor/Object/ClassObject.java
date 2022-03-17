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
        calculateLowerRight(width, length+fieldnum*fieldlength);
    }

    @Override
    public void draw(Graphics graph) {
        defaultColor = graph.getColor();
        int lx = metaCoordinate.x;
        int ly = metaCoordinate.y;
        
        graph.setColor(new Color(169, 169, 169));
        graph.fillRoundRect(lx, ly, width, length, round, round);
        graph.setColor(defaultColor);
        graph.drawRoundRect(lx, ly, width, length, round, round);
        graph.setFont(new Font(className, Font.PLAIN, 25));
        graph.drawString(className, lx+20, ly+40);

        
        for (int i = 0; i < fieldnum; i++) {
            graph.setColor(new Color(177, 192, 213));
            graph.fillRoundRect(lx, ly+length+i*fieldlength, width-1, fieldlength-1, round, round);
            graph.setColor(defaultColor);
            graph.drawRoundRect(lx, ly+length+i*fieldlength, width-1, fieldlength-1, round, round);
        }

        if (this.isSelected() == true) {
            int rx = lx + width;
            int ry = ly + length + fieldnum*fieldlength;
            
            graph.setColor(Color.RED);
            graph.drawRect((lx+rx)/2-10, ly-20, 20, 20);
            graph.drawRect(lx-20, (ly+ry)/2-10, 20, 20);
            graph.drawRect((lx+rx)/2-10, ry, 20, 20);
            graph.drawRect(rx, (ly+ry)/2-10, 20, 20);
            graph.setColor(defaultColor);
        }
    }

    @Override
    protected void calculateLowerRight(int w, int l) {
        lowerRightCoordinate = new Point(metaCoordinate.x+w, metaCoordinate.y+l);
    }

    public void move(Point p) {
        metaCoordinate = p;
        calculateLowerRight(width, length+fieldnum*fieldlength);
    }

    public boolean contain(Point p) {
        int x = p.x;
        int y = p.y;
        return x > metaCoordinate.x && x < metaCoordinate.x + width
                && y > metaCoordinate.y && y < metaCoordinate.y + length + fieldnum*fieldlength;    
    }

    public boolean contain(Point origin, Point offset) {
        int lx = origin.x;
        int ly = origin.y;
        int rx = offset.x;
        int ry = offset.y;
        calculateLowerRight(width, length);
        return between(metaCoordinate.x, lx, rx) && between(metaCoordinate.y, ly, ry)
                && between(lowerRightCoordinate.x, lx, rx) && between(lowerRightCoordinate.y, ly, ry);
    }

    public void setClassName(String clsname) {
        className = clsname;
    }

    public String getClassName() {
        return className;
    }

    private int round = 25;
    private int fieldnum = 3;
    private int fieldlength = 60;
    private String className = "Class name";
}
