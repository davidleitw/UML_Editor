package Button;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.ImageIcon;


abstract class BaseButton extends JButton {
    public BaseButton(String text, String iconFileName) {
        super(text, new ImageIcon(iconFileName));
        setFocusable(false);
        setBorderPainted(false);
        setRolloverEnabled(true);
        setBackground(new Color(255, 255, 255));
        setVerticalTextPosition(JButton.BOTTOM);
        setHorizontalTextPosition(JButton.CENTER);
        setPreferredSize(new Dimension(150, 150));
    }
    protected String buttonName_;
    protected String buttonIconPath_;
    
    public void setButtonName(String buttonName) {
        buttonName_ = buttonName;
    }

    public void setButtonIconPath(String buttonIconPath) {
        buttonIconPath_ = buttonIconPath;
    }

    public abstract void RegisterClickedEvent();
}
