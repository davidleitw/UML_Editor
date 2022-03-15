import javax.swing.*;

import java.awt.Color;
import java.awt.BorderLayout;

import Button.*;
import EventHandler.*;
import Component.ButtonToolBar;
import Component.Canvas;

public class UMLEditor extends JFrame {
    public static void main(String[] args) {
        new UMLEditor();
    }

    public UMLEditor() {
        this.setTitle("UML Editor");
        this.setSize(1600, 1200);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());
        this.setMenuLayout();
        this.setToolBarLayout();
        this.setCanvasLayout();
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private ButtonToolBar toolBar_;
    private void setToolBarLayout() {
        toolBar_ = new ButtonToolBar("tools", JToolBar.VERTICAL);

        toolBar_.registerBtn(new BaseButton("Select", "icons/icons8-mouse-80.png", new selectHandler()));
        toolBar_.registerBtn(new BaseButton("Association", "icons/icons8-arrow-64.png", new createClassHandler()));
        toolBar_.registerBtn(new BaseButton("Generalization", "icons/icons8-arrow-80.png", new createClassHandler()));
        toolBar_.registerBtn(new BaseButton("Composition", "icons/icons8-archers-arrow-80.png", new createClassHandler()));
        toolBar_.registerBtn(new BaseButton("Class", "icons/icons8-drawer-80.png", new createClassHandler()));
        toolBar_.registerBtn(new BaseButton("Use case", "icons/icons8-oval-80.png", new createUsecaseHandler()));

        this.add(toolBar_, BorderLayout.WEST);
    }

    private void setCanvasLayout() {
        JPanel panel = new Canvas(this.toolBar_);
        panel.setBackground(new Color(255, 255, 255));
        this.add(panel, BorderLayout.CENTER);
    }

    private void setMenuLayout() {

    }
}
