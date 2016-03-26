package view;

import Controller.TaskManagerController;
import Model.Task;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.util.Date;


public class MainPanel extends JPanel  {
    private JTabbedPane tasksLists = new JTabbedPane();


    private static boolean fistList;
    JPanel serviseTaskPanel = new JPanel();
    private JPanel panellists = new JPanel();
    private JPanel buttonsPanel = new JPanel();
    private JButton deleteTaskButton = new JButton("delete");
    private JButton changeTaskButton = new JButton("edit");
    private JButton createTaskButton = new JButton("create task");
    private static JTextField notifyField = new JTextField();
    private JPanel notifyPanel = new JPanel();

    private JPanel taskParametres = TaskManagerController.taskPanel;

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
        if (fistList)
            tasksLists.setSelectedIndex(1);
        serviseTaskPanel.setLayout(new GridLayout(3, 1));
        serviseTaskPanel.add(buttonsPanel);
        serviseTaskPanel.add(taskParametres);
        serviseTaskPanel.add(notifyPanel);
        panellists.add(tasksLists);
        this.add(panellists);
        this.add(serviseTaskPanel);

    }

    public static void setFistList(boolean fistList) {
        MainPanel.fistList = fistList;
    }

    public static void notifyText(String text) {
        notifyField.setText(text);

    }

}



