package view;

import Controller.TaskManagerController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by васыль on 05.02.2016.
 */
public class MainPanel extends JPanel {
    public MainPanel() {


        this.setLayout(new GridLayout(3, 1, 0, 40));
        this.setSize(400, 600);

        JButton createTaskButton = new JButton("create task");
        createTaskButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createTaskButton.addActionListener(new TaskManagerController().new CreateTaskListener());
        JButton taskListButton = new JButton("see task list");
        taskListButton.addActionListener(new TaskManagerController().new SeeTaskListListener());
        this.add(taskListButton, BorderLayout.SOUTH);
        this.add(createTaskButton);
        JButton calendarButton = new JButton("tasks for a week");
        calendarButton.addActionListener(new TaskManagerController().new CalendarListener());
        taskListButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        calendarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(taskListButton);
        this.add(createTaskButton);
        this.add(calendarButton);
    }
}
