package Component;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JToolBar;

import Button.BaseButton;

public class ButtonToolBar extends JToolBar {
    BaseButton currentBtn_ = null;

    public ButtonToolBar(String name, int orientation) {
        super(name, orientation);
        this.setFloatable(false);
        this.setLayout(new GridLayout(6, 1, 2, 2));
    }
    
    public void registerBtn(BaseButton btn) {
        this.add(btn);
        btn.linkToolBar(this);
    }

    public void updateCurrentBtn(BaseButton btn) {
        if (currentBtn_ == btn) {
            return;
        }

        if (currentBtn_ != null) {
            currentBtn_.setBackground(new Color(255, 255, 255));
        }

        setCurrentBtn(btn);
        currentBtn_.setBackground(new Color(255, 146, 36));
    }

    public BaseButton getCurrentBtn() {
        return currentBtn_;
    }

    private void setCurrentBtn(BaseButton btn) {
        currentBtn_ = btn;
    }
}
