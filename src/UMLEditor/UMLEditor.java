import javax.swing.*;

import java.awt.BorderLayout;

import Button.*;
import EventHandler.*;
import Component.Menu;
import Component.Canvas;
import Component.ButtonToolBar;


public class UMLEditor extends JFrame {
    public static void main(String[] args) {
        new UMLEditor();
    }

    public UMLEditor() {
        this.setTitle("UML Editor");
        this.setSize(1600, 1200);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());
        this.setToolBarLayout();
        this.setCanvasLayout();
        this.setMenuLayout();
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void setMenuLayout() {
        menu = new Menu(cvs);
        this.add(menu, BorderLayout.NORTH);
    }
    private void setToolBarLayout() {
        toolBar = new ButtonToolBar("tools", JToolBar.VERTICAL);

        toolBar.registerBtn(new BaseButton("Select", "icons/icons8-mouse-80.png", new selectHandler()));
        toolBar.registerBtn(new BaseButton("Association", "icons/icons8-arrow-64.png", new createAssociationHandler()));
        toolBar.registerBtn(new BaseButton("Generalization", "icons/icons8-arrow-80.png", new createGeneralizationHandler()));
        toolBar.registerBtn(new BaseButton("Composition", "icons/icons8-archers-arrow-80.png", new createCompositionHandler()));
        toolBar.registerBtn(new BaseButton("Class", "icons/icons8-drawer-80.png", new createClassHandler()));
        toolBar.registerBtn(new BaseButton("Use case", "icons/icons8-oval-80.png", new createUsecaseHandler()));

        this.add(toolBar, BorderLayout.WEST);
    }

    private void setCanvasLayout() {
        cvs = new Canvas(this.toolBar);
        this.add(cvs, BorderLayout.CENTER);
    }
    private Menu menu;
    private Canvas cvs;
    private ButtonToolBar toolBar;
}
