package Button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectButton extends BaseButton {
    public SelectButton(String text) {
        super(text);
        this.RegisterClickedEvent();
    }

    private void select() {
        System.out.println("This is Select Button");
    }

    public void RegisterClickedEvent() {
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                select();
            }
        });
    }
}
