package Button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UseCaseButton extends BaseButton{
    public UseCaseButton(String text) {
        super(text, "icons/icons8-oval-80.png");
        this.RegisterClickedEvent();
    }

    private void useCaseMethod() {
        System.out.println("This is Select Button");
    }

    public void RegisterClickedEvent() {
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                useCaseMethod();
            }
        });
    }
}
