package view;

import Controller.TaskManagerController;
import Model.Task;

import javax.swing.*;
import java.awt.*;


public class TaskManagerJFrame extends JFrame {
    private JPanel currentPanel = new JPanel();

    public TaskManagerJFrame() {

        this.setSize(800, 600);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new TaskManagerController().new CloseAll());
        this.setVisible(true);
    }


    public void paintPanel(JPanel panel) {

        this.remove(currentPanel);
        this.repaint();
        currentPanel = panel;
        this.add(currentPanel);
        this.revalidate();
    }
}
