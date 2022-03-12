package Button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class GeneralizationButton extends BaseButton {
    public GeneralizationButton(String text) {
        super(text, "icons/icons8-arrow-80.png");
        this.RegisterClickedEvent();
    }

    private void generalizate() {
        System.out.println("This is Generalization Button.");
    }

    public void RegisterClickedEvent() {
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generalizate();
            }
        }); 
    }
}
