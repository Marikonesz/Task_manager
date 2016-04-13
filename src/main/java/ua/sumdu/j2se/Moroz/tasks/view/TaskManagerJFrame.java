package ua.sumdu.j2se.Moroz.tasks.view;

import ua.sumdu.j2se.Moroz.tasks.Controller.TaskManagerController;
import javax.swing.*;
import java.awt.*;

/**
 * frame of user interface
 */
public class TaskManagerJFrame extends JFrame {

public static TaskManagerController controller = new TaskManagerController();

    /**
     * create an instance of TaskManagerJFrame
     */
    public TaskManagerJFrame() {
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.add(new MainPanel());
        this.addWindowListener(controller.new CloseAll());
        this.setVisible(true);

    }



}
