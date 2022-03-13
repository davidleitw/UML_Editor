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

interface ButtonMethod {
    void mousePressed(Canvas c, MouseEvent e);
    void mouseReleased(Canvas c, MouseEvent e);
}

public class BaseButton extends JButton implements ButtonMethod {
    public BaseButton(String buttonMode, String buttonIconPath) {
        super(buttonMode, new ImageIcon(buttonIconPath));
        setFocusable(false);
        setBorderPainted(false);
        setRolloverEnabled(true);
        setButtonMode(buttonMode);
        setButtonIconPath(buttonIconPath);
        setBackground(new Color(255, 255, 255));
        setVerticalTextPosition(JButton.BOTTOM);
        setHorizontalTextPosition(JButton.CENTER);
        setPreferredSize(new Dimension(150, 150));
        registerClickedEvent();
    }

    public void setButtonMode(String buttonMode) {
        buttonMode_ = buttonMode;
        System.out.println(buttonMode_);
    }

    public String getButtonMode() {
        return buttonMode_;
    }

    public void setButtonIconPath(String buttonIconPath) {
        buttonIconPath_ = buttonIconPath;
        System.out.println(buttonIconPath_);
    }

    public String getButtonIconPath() {
        return buttonIconPath_;
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

    private String buttonMode_;
    private String buttonIconPath_;
    private ButtonToolBar toolBar_;
}
