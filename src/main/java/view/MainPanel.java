package view;

import Controller.TaskManagerController;

import javax.swing.*;
import java.awt.*;


public class MainPanel extends JPanel {
  private   JTabbedPane tasksLists = new JTabbedPane();
    JPanel serviseTaskPanel = new JPanel();
    private JPanel panellists = new JPanel();
  private   JPanel buttonsPanel = new JPanel();
  private    JButton deleteTaskButton = new JButton("delete");
  private   JButton changeTaskButton = new JButton("edit");
private   JButton createTaskButton = new JButton("create task");
    private JPanel taskParametres = new TaskPanel(TaskManagerController.task);
    public MainPanel() {

        deleteTaskButton.addActionListener(new TaskManagerController().new RemoveTaskButtonListener());
        changeTaskButton.addActionListener(new TaskManagerController().new ChangeTaskButtonListener());
        createTaskButton.addActionListener(new TaskManagerController().new CreateTaskListener());
buttonsPanel.add(deleteTaskButton);
        buttonsPanel.add(changeTaskButton);
       buttonsPanel.add(createTaskButton);



        this.setLayout(new GridLayout(1,2,1,1));
        this.setSize(400, 600);
        tasksLists.addTab("All Tasks", new AllTaskListPanel(TaskManagerController.model));
        tasksLists.addTab("on week", new CalendarPanel(TaskManagerController.model));
        serviseTaskPanel.setLayout(new GridLayout(3,1));
        serviseTaskPanel.add(buttonsPanel);
        serviseTaskPanel.add(taskParametres);

        panellists.add(tasksLists);
        this.add(panellists);
        this.add(serviseTaskPanel);

//        JButton createTaskButton = new JButton("create task");
//        createTaskButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        createTaskButton.addActionListener(new TaskManagerController().new CreateTaskListener());
//        JButton taskListButton = new JButton("see task list");
//        taskListButton.addActionListener(new TaskManagerController().new SeeTaskListListener());
//        this.add(taskListButton, BorderLayout.SOUTH);
//        this.add(createTaskButton);
//        JButton calendarButton = new JButton("tasks for a week");
//        calendarButton.addActionListener(new TaskManagerController().new CalendarListener());
//        taskListButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        calendarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        this.add(taskListButton);
//        this.add(createTaskButton);
//        this.add(calendarButton);
    }
}
