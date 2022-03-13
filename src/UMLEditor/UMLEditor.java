import javax.swing.*;

import java.awt.Color;
import java.awt.BorderLayout;

import Button.*;
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

        toolBar_.registerBtn(new SelectButton("Select"));
        toolBar_.registerBtn(new AssociationButton("Association"));
        toolBar_.registerBtn(new GeneralizationButton("Generalization"));
        toolBar_.registerBtn(new CompositionButton("Composition"));
        toolBar_.registerBtn(new ClassButton("Class"));
        toolBar_.registerBtn(new UseCaseButton("Use case"));

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
