package Button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class CompositionButton extends BaseButton{
    public CompositionButton(String text) {
        super(text, "icons/icons8-archers-arrow-80.png");
        this.RegisterClickedEvent();
    }

    private void composition() {
        System.out.println("This is Composition Button.");
    }

    public void RegisterClickedEvent() {
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                composition();
            }
        }); 
    }
}
