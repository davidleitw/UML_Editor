package Object;

import java.awt.Graphics;

public class GeneralizationLine extends Line{
    public GeneralizationLine() {}

    @Override
    public void draw(Graphics graph) {
        getPoints();

        double h = 20;
		int dx = dstPoint.x - srcPoint.x, dy = dstPoint.y - srcPoint.y;
		double D = Math.sqrt(dx*dx + dy*dy);
		double px = dstPoint.x-h*dx/D;
		double py = dstPoint.y-h*dy/D;
		
		double sin = Math.sin(Math.PI/4), cos = Math.cos(Math.PI/4);
		double a1x = dstPoint.x + (cos*(px-dstPoint.x) - sin*(py-dstPoint.y));
		double a1y = dstPoint.y + (sin*(px-dstPoint.x) + cos*(py-dstPoint.y));
		
		sin = Math.sin(-1*Math.PI/4);
		cos = Math.cos(-1*Math.PI/4);
		double a2x = dstPoint.x + (cos*(px-dstPoint.x) - sin*(py-dstPoint.y));
		double a2y = dstPoint.y + (sin*(px-dstPoint.x) + cos*(py-dstPoint.y));
		
		graph.drawLine(dstPoint.x, dstPoint.y, (int)a1x, (int)a1y);
		graph.drawLine(dstPoint.x, dstPoint.y, (int)a2x, (int)a2y);
		graph.drawLine((int)a1x, (int)a1y, (int)a2x, (int)a2y);
		px = dstPoint.x-0.7*h*dx/D;
		py = dstPoint.y-0.7*h*dy/D;
		graph.drawLine(srcPoint.x, srcPoint.y, (int)px, (int)py);
    }
}
