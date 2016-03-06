package view;

import Controller.TaskManagerController;
import Model.Task;

import javax.swing.*;
import java.awt.*;


public class TaskManagerJFrame extends JFrame {
    private JPanel currentPanel = new JPanel();
    private GridBagConstraints gridbag = new GridBagConstraints(3, 2, 3, 4, 0.9, 0.9, GridBagConstraints.BASELINE, GridBagConstraints.ABOVE_BASELINE_TRAILING, new Insets(1, 1, 1, 1), 0, 0);
    public static JTextField noticatonField;


    public TaskManagerJFrame() {

        this.setSize(800, 600);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new TaskManagerController().new CloseAll());
        this.setVisible(true);
    }

//
//        this.createFrame();
//    }
//
//    private void createFrame(){
//
//JLabel welcome = new JLabel("welcome to task manager");
//        JLabel nextTaskLabel = new JLabel("next task");
//        noticatonField = new JTextField(50);
//        JButton seeButton = new JButton("see");
//        JButton backButton = new JButton("back");
//        noticatonField.setEditable(false);
//
//
//        this.setLocationRelativeTo(null);
//        this.setSize(800, 600);
//        this.setLayout(new GridBagLayout());
//
//
//         this.add(welcome, new GridBagConstraints(3, 0, 1, 4, 0.9, 0.9, GridBagConstraints.NORTH, GridBagConstraints.ABOVE_BASELINE_TRAILING, new Insets(1, 1, 1, 1), 0, 0));
//       this.setTitle("Task manager");

//        JPanel nextTaskNotify = new JPanel();
//        nextTaskNotify.add(nextTaskLabel);
//        nextTaskNotify.add(noticatonField);
//        nextTaskNotify.add(seeButton);
//        backButton.addActionListener(new TaskManagerController().new BackButtonListener());
//        seeButton.addActionListener(new TaskManagerController().new TaskListener());
//       this.add(backButton, new GridBagConstraints(3, 0, 1, 4, 0.9, 0.9, GridBagConstraints.NORTHEAST, GridBagConstraints.ABOVE_BASELINE_TRAILING, new Insets(1, 1, 1, 1), 0, 0));
//        this.add(nextTaskNotify, new GridBagConstraints(3, 5, 6, 1, 0.9, 0.9, GridBagConstraints.SOUTH, GridBagConstraints.ABOVE_BASELINE_TRAILING, new Insets(1, 1, 1, 1), 0, 0));
//
//

    public void paintPanel(JPanel panel) {

        this.remove(currentPanel);
        this.repaint();
        currentPanel = panel;
        this.add(currentPanel);
        this.revalidate();
    }
}
