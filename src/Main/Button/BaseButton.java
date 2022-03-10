package Button;
import javax.swing.JButton;

abstract class BaseButton extends JButton {
    public BaseButton(String text) {
        super(text);
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
