package Button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssociationButton extends BaseButton {
    public AssociationButton(String text) {
        super(text);
        this.RegisterClickedEvent();
    }

    private void associate() {
        System.out.println("This is Association Button.");
    }

    public void RegisterClickedEvent() {
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                associate();
            }
        });
    }
}
