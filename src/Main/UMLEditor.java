import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Button.*;

public class UMLEditor {
    private void createNewUMLEditor() {
        // JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("UML Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        JToolBar toolbar = new JToolBar("tools", JToolBar.VERTICAL);
        JButton selectButton = new SelectButton("Select");
        JButton associationButton = new AssociationButton("Association line");
        JButton generalicationButton = new GeneralizationButton("Generalization line");
        JButton compositionButton = new CompositionButton("Composition line  ");
        JButton classButton = new ClassButton("class");
        JButton caseButton = new UseCaseButton("use case");
        toolbar.add(selectButton);
        toolbar.add(associationButton);
        toolbar.add(generalicationButton);
        toolbar.add(compositionButton);
        toolbar.add(classButton);
        toolbar.add(caseButton);
        toolbar.addSeparator(new java.awt.Dimension(20, 20));

        frame.add(toolbar, BorderLayout.WEST);
        frame.pack();
        frame.setVisible(true);
    }

    public void CreateNewUMLEditor() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createNewUMLEditor();
            }
        });
    }
}
