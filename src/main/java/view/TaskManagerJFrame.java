package view;

import Controller.TaskManagerController;
import Model.Task;

import javax.swing.*;
import java.awt.*;


public class TaskManagerJFrame extends JFrame implements ControllerInterface{
    private JPanel currentPanel = new JPanel();

    public TaskManagerJFrame() {

        this.setSize(800, 600);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(controller.new CloseAll());
        this.add(new MainPanel());
        this.setVisible(true);

    }



}
