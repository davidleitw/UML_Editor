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

        JMenuItem changeNameItem = new JMenuItem("Change object name");
        changeNameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cvs.strategy().canChangeObjectName()) {
                    String newName = ChangeNameWindow(cvs.strategy().getChangeObjectName());
                    if (newName != "") {
                        cvs.strategy().changeObjectName(newName);
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
        editMenu.add(changeNameItem);
        editMenu.add(groupItem);
        editMenu.add(ungroupItem);

        this.add(fileMenu);
        this.add(editMenu);
    }

    private String ChangeNameWindow(String oldname) {
        return JOptionPane.showInputDialog(null, "New object name:", oldname);
    }

    private Canvas cvs;
}
