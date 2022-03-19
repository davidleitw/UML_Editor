package Component;

import Component.Canvas;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JMenuBar {
    public Menu(Canvas c) {
        cvs = c;
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");

        JMenuItem changeNameItem = new JMenuItem("Change object name");
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

    private Canvas cvs;
}
