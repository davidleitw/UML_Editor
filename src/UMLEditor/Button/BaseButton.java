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

    void mouseDragged(Canvas c, MouseEvent e);

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
        toolBar = toolbar;

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolBar.updateCurrentBtn((BaseButton) e.getSource());
            }
        });
    }

    private void registerEventHandler(eventHandler handler) {
        this.handler = handler;
    }

    public void mousePressed(Canvas c, MouseEvent e) {
        handler.mousePressed(c, e);
    }

    public void mouseReleased(Canvas c, MouseEvent e) {
        handler.mouseReleased(c, e);
    }

    public void mouseDragged(Canvas c, MouseEvent e) {
        handler.mouseDragged(c, e);
    }

    private eventHandler handler;
    private ButtonToolBar toolBar;
}
