package view;

import Controller.TaskManagerController;
import Model.Task;

import javax.swing.*;
import java.awt.*;


public class TaskPanel extends JPanel {
    public TaskPanel() {
        this.setLayout(new GridLayout(6, 2,5,30));
        JLabel lableTitleTask = new JLabel("Task");
        JTextField fieldTitleTask   = new JTextField(30);
        fieldTitleTask.setEditable(false);
        JLabel lableTimeTask = new JLabel("time");
        JTextField fieldTimeTask   = new JTextField(30);
        fieldTimeTask.setEditable(false);
        JLabel lableStartTask = new JLabel("start");
        JTextField fieldStartTask   = new JTextField(30);
        fieldStartTask.setEditable(false);
        JLabel lableEndTask = new JLabel("end");
        JTextField fieldEndTask   = new JTextField(30);
        fieldEndTask.setEditable(false);
        JLabel lableintervalTask = new JLabel("end");
        JTextField fieldIntervalTask   = new JTextField(20);
        fieldIntervalTask.setEditable(false);

        this.add(lableTitleTask);
        this.add(fieldTitleTask);
        this.add(lableStartTask);
        this.add(fieldStartTask);
        this.add(lableTimeTask);
        this.add(fieldTimeTask);
        this.add(lableEndTask);
        this.add(fieldEndTask);
        this.add(lableintervalTask);
        this.add(fieldIntervalTask);
//        this.add(deleteTaskButton);
//        this.add(changeTaskButton);
    }
    public TaskPanel(Task task) {
        this.setSize(200,600);
        boolean onOff = task.isActive();
        this.setLayout(new GridLayout(5, 2, 0, 0));
        JLabel lableTitleTask = new JLabel("  Task");
        JTextField fieldTitleTask   = new JTextField(20);
        fieldTitleTask.setEditable(false);
        fieldTitleTask.setText(task.getTitle());
        JLabel lableTimeTask = new JLabel("  begin");
        JTextField fieldTimeTask   = new JTextField(10);
        fieldTimeTask.setEditable(false);
        fieldTimeTask.setText(task.getTime().toString());
        JLabel lableStartTask = new JLabel("start");
        JTextField fieldStartTask   = new JTextField(10);
        fieldStartTask.setEditable(false);
        fieldStartTask.setText(task.getStart().toString());
        JLabel lableEndTask = new JLabel("   end");
        JTextField fieldEndTask   = new JTextField(30);
        fieldEndTask.setEditable(false);
        fieldEndTask.setText(task.getEnd().toString());
        JLabel lableintervalTask = new JLabel("end");
        JTextField fieldIntervalTask   = new JTextField(10);
        fieldIntervalTask.setEditable(false);
        fieldIntervalTask.setText(task.getInterval().toString());
        JButton deleteTaskButton = new JButton("delete");
        deleteTaskButton.addActionListener(new TaskManagerController().new RemoveTaskButtonListener());
        JButton changeTaskButton = new JButton("change");
        changeTaskButton.addActionListener(new TaskManagerController().new ChangeTaskButtonListener());
        JLabel activeLabel = new JLabel("Active");
        JCheckBox activCheck = new JCheckBox("active",onOff);
        JCheckBox repeatedCheck = new JCheckBox("repeated");
        activCheck.addItemListener(new TaskManagerController().new TaskActiveListener());
        this.add(lableTitleTask);
        this.add(fieldTitleTask);

        this.add(lableTimeTask);
        this.add(fieldTimeTask);
        this.add(lableEndTask);
        this.add(fieldEndTask);
       this.add(activCheck);
        this.add(repeatedCheck);

    }
}
//        this.add(lableStartTask);
//        this.add(fieldStartTask);
//this.add(deleteTaskButton);
// this.add(changeTaskButton);
//        this.add(lableintervalTask);
//        this.add(fieldIntervalTask);