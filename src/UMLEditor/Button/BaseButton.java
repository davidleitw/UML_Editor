package Button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import Component.Canvas;
import Component.ButtonToolBar;

interface MouseEventHandler {
    void mousePressed(Canvas c, MouseEvent e);
    void mouseReleased(Canvas c, MouseEvent e);
}

public class BaseButton extends JButton implements MouseEventHandler {
    public BaseButton(String buttonMode, String buttonIconPath) {
        super(buttonMode, new ImageIcon(buttonIconPath));
        setFocusable(false);
        setBorderPainted(false);
        setRolloverEnabled(true);
        setBackground(new Color(255, 255, 255));
        setVerticalTextPosition(JButton.BOTTOM);
        setHorizontalTextPosition(JButton.CENTER);
        setPreferredSize(new Dimension(150, 150));
        registerClickedEvent();
    }

    public void bindToolBar(ButtonToolBar toolbar) {
        toolBar_ = toolbar;
    }

    private void registerClickedEvent() {
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolBar_.updateCurrentBtn((BaseButton)e.getSource());
            }
        });
    };

    public void mousePressed(Canvas c, MouseEvent e) {
        System.out.println("BaseButton mousePressed");
    }

    public void mouseReleased(Canvas c, MouseEvent e) {
        System.out.println("BaseButton mouseReleased");
    }

    private ButtonToolBar toolBar_;
}
