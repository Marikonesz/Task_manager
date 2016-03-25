package view;

import Controller.TaskManagerController;
import javax.swing.*;
import java.awt.*;

public class TaskManagerJFrame extends JFrame {
public static TaskManagerController controller;
    public TaskManagerJFrame() {

        this.setSize(800, 600);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.add(new MainPanel());
        controller = new TaskManagerController();
        this.addWindowListener(controller.new CloseAll());
        this.setVisible(true);

    }



}
