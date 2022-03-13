package Component;

import Object.*;
import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

public class Canvas extends JPanel {
    public Canvas(ButtonToolBar toolBar) {
        
        adapter_ = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePressedHandler(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseReleasedHandler(e);
            }
        };
        this.addMouseListener(adapter_);

        toolBar_ = toolBar;
        BaseObjects = new ArrayList<BaseObject>();
    }

    private void mousePressedHandler(MouseEvent e) {
        if (toolBar_.getCurrentBtn() == null) {
            return;
        }

        toolBar_.getCurrentBtn().mousePressed(this, e);
    }

    private void mouseReleasedHandler(MouseEvent e) {
        if (toolBar_.getCurrentBtn() == null) {
            return;
        }

        toolBar_.getCurrentBtn().mouseReleased(this, e);
    }

    public void addObject(BaseObject obj) {
        BaseObjects.add(obj);
    }

    @Override
    public void paint(Graphics graph) {
        for (int i = 0; i < BaseObjects.size(); i++) {
            BaseObject obj = BaseObjects.get(i);
            obj.draw(graph);
        }
    }
    
    public MouseAdapter adapter_; 
    private ArrayList<BaseObject> BaseObjects;
    private ButtonToolBar toolBar_;
}
