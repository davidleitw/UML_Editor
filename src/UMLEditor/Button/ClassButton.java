package Button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class ClassButton extends BaseButton { 
    public ClassButton(String text) {
        super(text, "icons/icons8-drawer-80.png");
        this.RegisterClickedEvent();
    }

    private void classMethod() {
        System.out.println("This is Class Button.");
    }

    public void RegisterClickedEvent() {
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classMethod();
            }
        }); 
    }
}
