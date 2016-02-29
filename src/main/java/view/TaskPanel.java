package view;

import Controller.TaskManagerController;
import Model.Task;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.util.Date;


public class TaskPanel extends JPanel {

    private JLabel lableTitleTask;
    private static JTextField fieldTitleTask;
    private JLabel lableTimeTask;
    private static JDateChooser fieldTimeTask;
   private JLabel lableEndTask;
  private static JDateChooser fieldEndTask;
    private JLabel intervalLabel;
    private JPanel intervalPanel ;
    private static JTextField hours;
    private static JTextField minutes;
    public static String title;
    public static Date end;
    public static Duration interval = Duration.ofMillis(0);
    public static Date time;
    public static boolean active;

//    public TaskPanel() {
//        this.setLayout(new GridLayout(6, 2, 5, 30));
//        JLabel lableTitleTask = new JLabel("Task");
//        JTextField fieldTitleTask = new JTextField(30);
//
//        JLabel lableTimeTask = new JLabel("time");
//        JTextField fieldTimeTask = new JTextField(30);
//
//
//        JLabel lableEndTask = new JLabel("end");
//        JTextField fieldEndTask = new JTextField(30);
//
//        JLabel lableintervalTask = new JLabel("end");
//        JTextField fieldIntervalTask = new JTextField(20);
//        fieldIntervalTask.setEditable(false);
//
//        this.add(lableTitleTask);
//        this.add(fieldTitleTask);
//
//        this.add(lableTimeTask);
//        this.add(fieldTimeTask);
//        this.add(lableEndTask);
//        this.add(fieldEndTask);
//        this.add(lableintervalTask);
//        this.add(fieldIntervalTask);
//
//    }


    public TaskPanel(Task task) {
        this.setSize(200, 600);
        boolean onOff = task.isActive();
        boolean repeated = TaskManagerController.repeatedTask;
        this.setLayout(new GridLayout(5, 2, 0, 0));
        lableTitleTask = new JLabel("  Task");
        fieldTitleTask = new JTextField(20);

        fieldTitleTask.setText(task.getTitle());
        lableTimeTask = new JLabel("  begin");
        fieldTimeTask = new JDateChooser();

        fieldTimeTask.setDate(task.getTime());
//        JLabel intervalTask = new JLabel("start");
//        JTextField fieldStartTask = new JTextField(10);
//        fieldStartTask.setEditable(false);
//        fieldStartTask.setText(task.getStart().toString());
        lableEndTask = new JLabel("   end");
         fieldEndTask = new JDateChooser();

        fieldEndTask.setDate(task.getEnd());
        JLabel lableintervalTask = new JLabel("end");

        JButton deleteTaskButton = new JButton("delete");
        deleteTaskButton.addActionListener(new TaskManagerController().new RemoveTaskButtonListener());
        JButton changeTaskButton = new JButton("change");
        changeTaskButton.addActionListener(new TaskManagerController().new ChangeTaskButtonListener());
        intervalLabel = new JLabel("interval");
        intervalPanel =  new JPanel();
        hours = new JTextField(2);
        minutes = new JTextField(2);
        if (hours.getText().equals(""))
            hours.setText("0");
        if (minutes.getText().equals(""))
            minutes.setText("0");
        intervalPanel.add(hours);
        intervalPanel.add(new JLabel("hours"));
        intervalPanel.add(minutes);
        intervalPanel.add(new JLabel("minutes"));



        JCheckBox activCheck = new JCheckBox("active", onOff);
        JCheckBox repeatedCheck = new JCheckBox("repeated",repeated);
        repeatedCheck.addItemListener(new TaskManagerController().new TaskRepeatedListener());
        activCheck.addItemListener(new TaskManagerController().new TaskActiveListener());
    if (!repeatedCheck.isSelected()) {
        fieldEndTask.setEnabled(false);
        hours.setEnabled(false);
        minutes.setEnabled(false);
    }
        this.add(lableTitleTask);
        this.add(fieldTitleTask);

        this.add(lableTimeTask);
        this.add(fieldTimeTask);
        this.add(lableEndTask);
        this.add(fieldEndTask);
        this.add(intervalLabel);
        this.add(intervalPanel);
        this.add(activCheck);
        this.add(repeatedCheck);

    }

    public  static void initializeParemeters() {

        title = fieldTitleTask.getText();
        end = fieldEndTask.getDate();
        time = fieldTimeTask.getDate();
        time = fieldTimeTask.getDate();
        interval = Duration.ofHours(Long.parseLong(hours.getText())).plusMinutes(Long.parseLong(minutes.getText()));
        System.out.println(interval);

    }
}
//        this.add(lableStartTask);
//        this.add(fieldStartTask);
//this.add(deleteTaskButton);
// this.add(changeTaskButton);
//        this.add(lableintervalTask);
//        this.add(fieldIntervalTask);