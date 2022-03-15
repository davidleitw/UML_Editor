package Button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import EventHandler.*;
import Component.Canvas;
import Component.ButtonToolBar;

interface MouseEventHandler {
    void mousePressed(Canvas c, MouseEvent e);
    void mouseReleased(Canvas c, MouseEvent e);
}

public class BaseButton extends JButton implements MouseEventHandler {
    public BaseButton(String buttonMode, String buttonIconPath, eventHandler handler) {
        super(buttonMode, new ImageIcon(buttonIconPath));
        setFocusable(false);
        setBorderPainted(false);
        setRolloverEnabled(true);
        setBackground(new Color(255, 255, 255));
        setVerticalTextPosition(JButton.BOTTOM);
        setHorizontalTextPosition(JButton.CENTER);
        setPreferredSize(new Dimension(150, 150));
        registerEventHandler(handler);
    }

    public void binding(ButtonToolBar toolbar) {
        toolBar_ = toolbar;
        
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolBar_.updateCurrentBtn((BaseButton)e.getSource());
            }
        });
    }

    private void registerEventHandler(eventHandler handler) {
        handler_ = handler;
    }

    public void mousePressed(Canvas c, MouseEvent e) {
        handler_.mousePressed(c, e);
    }

    public void mouseReleased(Canvas c, MouseEvent e) {
        handler_.mouseReleased(c, e);
    }

    public void mouseDragged(Canvas c, MouseEvent e) {
        handler_.mouseDragged(c, e);
    }

    private eventHandler handler_;
    private ButtonToolBar toolBar_;
}
