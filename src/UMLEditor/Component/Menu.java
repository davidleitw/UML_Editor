package Component;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JMenuBar {
    public Menu(Canvas c) {
        cvs = c;
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");

        JMenuItem changeClassItem = new JMenuItem("Change class name");
        changeClassItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cvs.strategy().canChangeClassName()) {
                    String newName = ChangeClassWindow(cvs.strategy().getClassName());
                    if (newName != "") {
                        cvs.strategy().changeObjectName(newName);
                    }
                }
            }
        });
        JMenuItem changeUseCaseItem = new JMenuItem("Change use case name");
        changeUseCaseItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cvs.strategy().canChangeCaseName()) {
                    String newName = ChangeCaseWindow(cvs.strategy().getCaseName());
                    if (newName != "") {
                        cvs.strategy().changeCaseName(newName);
                    }
                }
            }
        });

        JMenuItem groupItem = new JMenuItem("Group");
        groupItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cvs.strategy().groupObject();
            }
        });
        JMenuItem ungroupItem = new JMenuItem("Ungroup");
        ungroupItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cvs.strategy().ungroupObject();
            }
        });
        editMenu.add(changeClassItem);
        editMenu.add(changeUseCaseItem);
        editMenu.add(groupItem);
        editMenu.add(ungroupItem);

        this.add(fileMenu);
        this.add(editMenu);
    }

    private String ChangeClassWindow(String oldname) {
        return JOptionPane.showInputDialog(null, "New class name:", oldname);
    }

    private String ChangeCaseWindow(String oldname) {
        return JOptionPane.showInputDialog(null, "New case name:", oldname);
    }

    private Canvas cvs;
}
