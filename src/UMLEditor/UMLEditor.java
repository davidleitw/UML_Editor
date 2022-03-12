import javax.swing.*;
import java.awt.*;
import Button.*;

public class UMLEditor extends JFrame {
    public static void main(String[] args) {
        new UMLEditor();
    }

    public UMLEditor() {
        this.setTitle("UML Editor");
        this.setSize(1600, 1200);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());
        this.settoolBarLayout();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private JToolBar toolBar;
    private void settoolBarLayout() {
        toolBar = new JToolBar("tools", JToolBar.VERTICAL);
        toolBar.setLayout(new GridLayout(6, 1, 2, 2));
        toolBar.setFloatable(false);
        
        JButton selectButton = new SelectButton("Select");
        JButton associationButton = new AssociationButton("Association");
        JButton generalicationButton = new GeneralizationButton("Generalization");
        JButton compositionButton = new CompositionButton("Composition");
        JButton classButton = new ClassButton("Class");
        JButton caseButton = new UseCaseButton("Use case");

        toolBar.add(selectButton);
        toolBar.add(associationButton);
        toolBar.add(generalicationButton);
        toolBar.add(compositionButton);
        toolBar.add(classButton);
        toolBar.add(caseButton);

        this.add(toolBar, BorderLayout.WEST);
    }
}
