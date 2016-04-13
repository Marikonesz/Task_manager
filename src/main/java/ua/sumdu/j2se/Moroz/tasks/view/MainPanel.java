package ua.sumdu.j2se.Moroz.tasks.view;

import ua.sumdu.j2se.Moroz.tasks.Controller.TaskManagerController;


import javax.swing.*;
import java.awt.*;

/**
 * the main panel of the application contains all of the elements of the user interface
 */
public class MainPanel extends JPanel  {

    private JTabbedPane tasksLists = new JTabbedPane();
    JPanel serviseTaskPanel = new JPanel();
    private JPanel panellists = new JPanel();
    private JPanel buttonsPanel = new JPanel();
    private JButton deleteTaskButton = new JButton("delete");
    private JButton changeTaskButton = new JButton("edit");
    private JButton createTaskButton = new JButton("create task");
    private static JTextField notifyField = new JTextField();
    private JPanel notifyPanel = new JPanel();

    private JPanel taskParametres = TaskManagerController.taskPanel;

    /**
     * create an instance of MainPanel with all elements of user interface
     */
    public MainPanel() {

        deleteTaskButton.addActionListener(TaskManagerJFrame.controller.new RemoveTaskButtonListener());
        changeTaskButton.addActionListener(TaskManagerJFrame.controller.new ChangeTaskButtonListener());
        createTaskButton.addActionListener(TaskManagerJFrame.controller.new CreateTaskListener());
        buttonsPanel.add(deleteTaskButton);
        buttonsPanel.add(changeTaskButton);
        buttonsPanel.add(createTaskButton);
        notifyField.setEditable(false);
        notifyPanel.setLayout(new BorderLayout());
        notifyPanel.add(notifyField, BorderLayout.SOUTH);

        this.setLayout(new GridLayout(1, 2, 1, 1));
        this.setSize(400, 600);
        tasksLists.addTab("All Tasks", new AllTaskListPanel(TaskManagerController.model));
        tasksLists.addTab("on week", new CalendarPanel(TaskManagerController.calendarModel));
        serviseTaskPanel.setLayout(new GridLayout(3, 1));
        serviseTaskPanel.add(buttonsPanel);
        serviseTaskPanel.add(taskParametres);
        serviseTaskPanel.add(notifyPanel);
        panellists.add(tasksLists);
        this.add(panellists);
        this.add(serviseTaskPanel);

    }

    /**
     * set text into notify field
     * @param text text to write into notify field
     */
    public static void notifyText(String text) {
        notifyField.setText(text);

    }

}



